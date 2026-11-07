-- V3: internal applications, engagement and in-app notifications.
-- Implements docs/DATA_MODEL.md section 3.5. Duplicate prevention per seeker/opportunity
-- is a database guarantee (D-007), not merely an application check.

create table application (
    id              uuid primary key default gen_random_uuid(),
    opportunity_id  uuid not null references opportunity (id) on delete cascade,
    seeker_user_id  uuid not null references app_user (id) on delete cascade,
    status          varchar(20) not null default 'SUBMITTED',
    cover_letter    text,
    form_responses  jsonb not null default '{}'::jsonb,
    submitted_at    timestamptz not null default now(),
    created_at      timestamptz not null default now(),
    updated_at      timestamptz,
    constraint uq_application_opportunity_seek unique (opportunity_id, seeker_user_id),
    constraint ck_application_status
        check (status in ('SUBMITTED', 'UNDER_REVIEW', 'SHORTLISTED', 'ACCEPTED', 'REJECTED', 'WITHDRAWN')),
    constraint ck_application_form_object check (jsonb_typeof(form_responses) = 'object')
);

create index ix_application_seek_status on application (seeker_user_id, status);
create index ix_application_opportunity_status on application (opportunity_id, status);

create table application_status_history (
    id                 uuid primary key default gen_random_uuid(),
    application_id     uuid not null references application (id) on delete cascade,
    from_status        varchar(20),
    to_status          varchar(20) not null,
    changed_by_user_id uuid references app_user (id) on delete set null,
    reason             varchar(500),
    created_at         timestamptz not null default now()
);

create index ix_application_status_history
    on application_status_history (application_id, created_at);

create table saved_opportunity (
    id             uuid primary key default gen_random_uuid(),
    user_id        uuid not null references app_user (id) on delete cascade,
    opportunity_id uuid not null references opportunity (id) on delete cascade,
    saved_at       timestamptz not null default now(),
    constraint uq_saved_opportunity unique (user_id, opportunity_id)
);

create index ix_saved_opportunity_user on saved_opportunity (user_id, saved_at desc);
create index ix_saved_opportunity_opportunity on saved_opportunity (opportunity_id);

create table notification (
    id        uuid primary key default gen_random_uuid(),
    user_id   uuid not null references app_user (id) on delete cascade,
    type      varchar(60) not null,
    title     varchar(200) not null,
    body      text,
    payload   jsonb not null default '{}'::jsonb,
    read_at   timestamptz,
    created_at timestamptz not null default now(),
    constraint ck_notification_payload_object check (jsonb_typeof(payload) = 'object')
);

create index ix_notification_unread on notification (user_id, created_at desc) where read_at is null;
create index ix_notification_user_created on notification (user_id, created_at desc);

create table notification_preference (
    id                uuid primary key default gen_random_uuid(),
    user_id           uuid not null references app_user (id) on delete cascade,
    notification_type varchar(60) not null,
    in_app_enabled    boolean not null default true,
    email_enabled     boolean not null default false,
    created_at        timestamptz not null default now(),
    updated_at        timestamptz,
    constraint uq_notification_preference unique (user_id, notification_type)
);
