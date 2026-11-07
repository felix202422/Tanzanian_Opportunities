-- V2: opportunities and their moderation/publication history.
-- Implements docs/DATA_MODEL.md section 3.4. Common fields are typed columns;
-- category-specific fields live only in the bounded `attributes` JSONB extension (D-004).

create table opportunity (
    id                       uuid primary key default gen_random_uuid(),
    organization_id          uuid not null references organization (id) on delete restrict,
    category_id              uuid not null references category (id) on delete restrict,
    title                    varchar(200) not null,
    summary                  varchar(500),
    description              text not null,
    region                   varchar(80),
    district                 varchar(80),
    work_mode                varchar(20),
    opportunity_type         varchar(20),
    application_mode         varchar(20) not null,
    external_url             varchar(500),
    source_name              varchar(120),
    source_url               varchar(500),
    moderation_status        varchar(20) not null default 'DRAFT',
    publication_status       varchar(20) not null default 'UNPUBLISHED',
    opens_at                 timestamptz,
    closes_at                timestamptz,
    published_at             timestamptz,
    attributes_schema_version integer not null default 1,
    attributes               jsonb not null default '{}'::jsonb,
    created_by_user_id       uuid not null references app_user (id) on delete restrict,
    created_at               timestamptz not null default now(),
    updated_at               timestamptz,
    constraint ck_opportunity_work_mode
        check (work_mode is null or work_mode in ('ONSITE', 'HYBRID', 'REMOTE', 'UNSPECIFIED')),
    constraint ck_opportunity_type
        check (opportunity_type is null
               or opportunity_type in ('FULL_TIME', 'PART_TIME', 'CONTRACT', 'PROJECT', 'VOLUNTEER', 'OTHER')),
    constraint ck_opportunity_application_mode check (application_mode in ('INTERNAL', 'EXTERNAL')),
    constraint ck_opportunity_moderation
        check (moderation_status in ('DRAFT', 'PENDING', 'APPROVED', 'REJECTED')),
    constraint ck_opportunity_publication
        check (publication_status in ('UNPUBLISHED', 'PUBLISHED', 'EXPIRED', 'WITHDRAWN')),
    constraint ck_opportunity_dates check (closes_at is null or opens_at is null or closes_at > opens_at),
    constraint ck_opportunity_external_url check (application_mode <> 'EXTERNAL' or external_url is not null),
    -- Approval must precede publication (REQUIREMENTS.md: approval must not be bypassed).
    constraint ck_opportunity_published_approved
        check (publication_status <> 'PUBLISHED' or moderation_status = 'APPROVED'),
    constraint ck_opportunity_published_at
        check (publication_status <> 'PUBLISHED' or published_at is not null),
    constraint ck_opportunity_attributes_version check (attributes_schema_version >= 1),
    constraint ck_opportunity_attributes_object check (jsonb_typeof(attributes) = 'object')
);

create index ix_opportunity_organization on opportunity (organization_id);
create index ix_opportunity_category on opportunity (category_id);
create index ix_opportunity_region_district on opportunity (region, district);
create index ix_opportunity_type on opportunity (opportunity_type) where opportunity_type is not null;
-- Public listing: published items, newest first.
create index ix_opportunity_publication_published
    on opportunity (publication_status, published_at desc);
-- Deadline-driven filters and ordering.
create index ix_opportunity_publication_closing on opportunity (publication_status, closes_at);
-- Moderator queue: only the pending subset is ever read.
create index ix_opportunity_moderation_pending
    on opportunity (moderation_status) where moderation_status = 'PENDING';

create table opportunity_status_history (
    id                 uuid primary key default gen_random_uuid(),
    opportunity_id     uuid not null references opportunity (id) on delete cascade,
    status_kind        varchar(20) not null,
    from_status        varchar(20),
    to_status          varchar(20) not null,
    changed_by_user_id uuid references app_user (id) on delete set null,
    reason             varchar(500),
    created_at         timestamptz not null default now(),
    constraint ck_opportunity_status_history_kind check (status_kind in ('MODERATION', 'PUBLICATION'))
);

create index ix_opportunity_status_history
    on opportunity_status_history (opportunity_id, created_at);
