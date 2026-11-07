-- V1: identity, reference categories and organizations.
-- Implements docs/DATA_MODEL.md sections 3.1-3.3. Flyway owns this schema (D-003);
-- Hibernate ddl-auto is never used to create or alter these objects.

create table app_user (
    id                uuid primary key default gen_random_uuid(),
    email             varchar(320) not null,
    password_hash     varchar(255) not null,
    display_name      varchar(120) not null,
    system_role       varchar(20) not null default 'SEEKER',
    status            varchar(20) not null default 'PENDING',
    locale            varchar(5) not null default 'en',
    email_verified_at timestamptz,
    last_login_at     timestamptz,
    created_at        timestamptz not null default now(),
    updated_at        timestamptz,
    constraint ck_app_user_role check (system_role in ('SEEKER', 'ORGANIZATION_ADMIN', 'ADMIN')),
    constraint ck_app_user_status check (status in ('PENDING', 'ACTIVE', 'SUSPENDED', 'DELETED')),
    constraint ck_app_user_locale check (locale in ('en', 'sw'))
);

-- Login must be case-insensitive, so uniqueness is on lower(email).
create unique index uq_app_user_lower_email on app_user (lower(email));
create index ix_app_user_role_status on app_user (system_role, status);

create table category (
    id             uuid primary key default gen_random_uuid(),
    code           varchar(40) not null,
    slug           varchar(60) not null,
    name_en        varchar(120) not null,
    name_sw        varchar(120) not null,
    description_en text,
    description_sw text,
    display_order  integer not null default 0,
    is_active      boolean not null default true,
    created_at     timestamptz not null default now(),
    updated_at     timestamptz,
    constraint uq_category_code unique (code),
    constraint uq_category_slug unique (slug)
);

create index ix_category_active_order on category (is_active, display_order);

-- Server-owned, versioned schema for category-specific attributes (D-004).
-- Rows are written by administrators and migrations only, never by a public client.
create table category_attribute_definition (
    id             uuid primary key default gen_random_uuid(),
    category_id    uuid not null references category (id) on delete restrict,
    schema_version integer not null default 1,
    attribute_key  varchar(60) not null,
    label_en       varchar(120) not null,
    label_sw       varchar(120) not null,
    data_type      varchar(20) not null,
    is_required    boolean not null default false,
    allowed_values jsonb,
    min_value      numeric,
    max_value      numeric,
    min_length     integer,
    max_length     integer,
    display_order  integer not null default 0,
    created_at     timestamptz not null default now(),
    constraint uq_category_attribute unique (category_id, schema_version, attribute_key),
    constraint ck_category_attribute_version check (schema_version >= 1),
    constraint ck_category_attribute_type
        check (data_type in ('TEXT', 'INTEGER', 'DECIMAL', 'DATE', 'BOOLEAN', 'ENUM')),
    -- jsonb_typeof is safe on any jsonb value; jsonb_array_length is deliberately not
    -- used here because it would raise an error instead of failing the constraint.
    constraint ck_category_attribute_values
        check (allowed_values is null or jsonb_typeof(allowed_values) = 'array'),
    constraint ck_category_attribute_enum
        check (data_type <> 'ENUM' or allowed_values is not null),
    constraint ck_category_attribute_range
        check (max_value is null or min_value is null or max_value >= min_value),
    constraint ck_category_attribute_length
        check (max_length is null or min_length is null or max_length >= min_length)
);

create index ix_category_attribute_category on category_attribute_definition (category_id, schema_version);

create table seeker_profile (
    id                  uuid primary key default gen_random_uuid(),
    user_id             uuid not null references app_user (id) on delete cascade,
    first_name          varchar(80) not null,
    last_name           varchar(80) not null,
    phone               varchar(32),
    headline            varchar(160),
    bio                 text,
    region              varchar(80),
    district            varchar(80),
    date_of_birth       date,
    profile_visibility  varchar(20) not null default 'PRIVATE',
    created_at          timestamptz not null default now(),
    updated_at          timestamptz,
    constraint uq_seeker_profile_user unique (user_id),
    constraint ck_seeker_profile_visibility check (profile_visibility in ('PRIVATE', 'PUBLIC'))
);

create index ix_seeker_profile_location on seeker_profile (region, district);

create table seeker_education (
    id           uuid primary key default gen_random_uuid(),
    profile_id   uuid not null references seeker_profile (id) on delete cascade,
    institution  varchar(160) not null,
    qualification varchar(160) not null,
    field_of_study varchar(160),
    start_date   date,
    end_date     date,
    is_current   boolean not null default false,
    display_order integer not null default 0,
    created_at   timestamptz not null default now(),
    updated_at   timestamptz,
    constraint ck_seeker_education_dates
        check (end_date is null or start_date is null or end_date >= start_date)
);

create index ix_seeker_education_profile on seeker_education (profile_id, display_order);

create table seeker_skill (
    id           uuid primary key default gen_random_uuid(),
    profile_id   uuid not null references seeker_profile (id) on delete cascade,
    name         varchar(80) not null,
    level        varchar(20) not null default 'BEGINNER',
    display_order integer not null default 0,
    created_at   timestamptz not null default now(),
    constraint ck_seeker_skill_level
        check (level in ('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'EXPERT'))
);

create unique index uq_seeker_skill_profile_name on seeker_skill (profile_id, lower(name));
create index ix_seeker_skill_profile on seeker_skill (profile_id, display_order);

create table seeker_experience (
    id           uuid primary key default gen_random_uuid(),
    profile_id   uuid not null references seeker_profile (id) on delete cascade,
    employer     varchar(160) not null,
    title        varchar(160) not null,
    location     varchar(120),
    start_date   date not null,
    end_date     date,
    is_current   boolean not null default false,
    description  text,
    display_order integer not null default 0,
    created_at   timestamptz not null default now(),
    updated_at   timestamptz,
    constraint ck_seeker_experience_dates
        check (is_current or end_date is null or end_date >= start_date)
);

create index ix_seeker_experience_profile on seeker_experience (profile_id, display_order);

create table seeker_interest (
    id          uuid primary key default gen_random_uuid(),
    profile_id  uuid not null references seeker_profile (id) on delete cascade,
    category_id uuid not null references category (id) on delete restrict,
    created_at  timestamptz not null default now(),
    constraint uq_seeker_interest unique (profile_id, category_id)
);

create index ix_seeker_interest_category on seeker_interest (category_id);

create table organization (
    id                  uuid primary key default gen_random_uuid(),
    owner_user_id       uuid not null references app_user (id) on delete restrict,
    name                varchar(160) not null,
    slug                varchar(80) not null,
    description         text,
    website             varchar(255),
    contact_email       varchar(320),
    contact_phone       varchar(32),
    region              varchar(80),
    district            varchar(80),
    founded_year        integer,
    verification_status varchar(20) not null default 'UNVERIFIED',
    created_at          timestamptz not null default now(),
    updated_at          timestamptz,
    constraint uq_organization_slug unique (slug),
    -- Vocabulary is provisional until Phase 6 confirms it (REQUIREMENTS.md).
    constraint ck_organization_verification
        check (verification_status in ('UNVERIFIED', 'SUBMITTED', 'PENDING', 'VERIFIED', 'REJECTED', 'SUSPENDED')),
    constraint ck_organization_founded check (founded_year is null or founded_year between 1900 and 2200)
);

create index ix_organization_owner on organization (owner_user_id);
create index ix_organization_verification on organization (verification_status);

create table organization_member (
    id              uuid primary key default gen_random_uuid(),
    organization_id uuid not null references organization (id) on delete cascade,
    user_id         uuid not null references app_user (id) on delete cascade,
    member_role     varchar(20) not null default 'MEMBER',
    status          varchar(20) not null default 'ACTIVE',
    joined_at       timestamptz not null default now(),
    created_at      timestamptz not null default now(),
    updated_at      timestamptz,
    constraint uq_organization_member unique (organization_id, user_id),
    constraint ck_organization_member_role check (member_role in ('OWNER', 'MANAGER', 'MEMBER')),
    constraint ck_organization_member_status check (status in ('INVITED', 'ACTIVE', 'REMOVED'))
);

create index ix_organization_member_user on organization_member (user_id);

create table organization_verification_request (
    id                  uuid primary key default gen_random_uuid(),
    organization_id     uuid not null references organization (id) on delete cascade,
    status              varchar(20) not null,
    submitted_at        timestamptz not null default now(),
    decided_at          timestamptz,
    decided_by_user_id  uuid references app_user (id) on delete set null,
    notes               text,
    document_key        varchar(255),
    created_at          timestamptz not null default now(),
    constraint ck_organization_verification_status
        check (status in ('SUBMITTED', 'PENDING', 'VERIFIED', 'REJECTED', 'SUSPENDED')),
    -- A decided outcome requires a decision time; an undecided one forbids it.
    constraint ck_organization_verification_decided
        check ((status in ('VERIFIED', 'REJECTED', 'SUSPENDED')) = (decided_at is not null))
);

create index ix_organization_verification_org
    on organization_verification_request (organization_id, submitted_at desc);
