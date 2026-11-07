-- V4: governance tables (reports, audit trail, platform settings).
-- Implements docs/DATA_MODEL.md section 3.6.

create table report (
    id               uuid primary key default gen_random_uuid(),
    reporter_user_id uuid not null references app_user (id) on delete cascade,
    target_type      varchar(20) not null,
    target_id        uuid not null,
    reason_code      varchar(40) not null,
    details          text,
    status           varchar(20) not null default 'OPEN',
    created_at       timestamptz not null default now(),
    resolved_at      timestamptz,
    -- target_id is polymorphic on purpose and therefore has no foreign key;
    -- see docs/DATA_MODEL.md section 3.6 for the accepted trade-off.
    constraint ck_report_target_type
        check (target_type in ('OPPORTUNITY', 'ORGANIZATION', 'APPLICATION', 'USER')),
    constraint ck_report_status check (status in ('OPEN', 'REVIEWING', 'RESOLVED', 'DISMISSED')),
    constraint ck_report_resolved
        check ((status in ('RESOLVED', 'DISMISSED')) = (resolved_at is not null))
);

create index ix_report_status on report (status, created_at desc);
create index ix_report_target on report (target_type, target_id);

create table audit_log (
    id            uuid primary key default gen_random_uuid(),
    actor_user_id uuid references app_user (id) on delete set null,
    action        varchar(80) not null,
    entity_type   varchar(60) not null,
    entity_id     uuid,
    details       jsonb not null default '{}'::jsonb,
    ip_address    inet,
    created_at    timestamptz not null default now(),
    constraint ck_audit_log_details_object check (jsonb_typeof(details) = 'object')
);

create index ix_audit_log_entity on audit_log (entity_type, entity_id, created_at desc);
create index ix_audit_log_actor on audit_log (actor_user_id, created_at desc);
create index ix_audit_log_created on audit_log (created_at desc);

create table platform_setting (
    setting_key         varchar(80) primary key,
    value               jsonb not null,
    description         varchar(255),
    updated_by_user_id  uuid references app_user (id) on delete set null,
    updated_at          timestamptz not null default now()
);
