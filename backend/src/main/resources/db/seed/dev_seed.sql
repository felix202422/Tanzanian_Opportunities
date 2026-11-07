-- Development seed data for TDOP (D-014: development seed data is separate from production).
--
-- This file is NOT a Flyway migration. It is executed only when the Spring profile
-- "seed" is active (see tz.tdop.seed.DevSeedRunner), so a production start can never
-- apply it and the Flyway schema history stays identical in every environment.
--
-- Everything here uses fixed UUIDs and ON CONFLICT DO NOTHING, so re-running is safe.
-- password_hash values are placeholders produced by no hashing algorithm that exists in
-- this project: they are not credentials and cannot log anyone in. Real hashing arrives
-- with Phase 4.

-- ---------------------------------------------------------------------------
-- 12 reference categories (docs/PROJECT_OVERVIEW.md)
-- Swahili labels are initial strings pending the Phase 14 localization review.
-- ---------------------------------------------------------------------------
insert into category (id, code, slug, name_en, name_sw, description_en, description_sw, display_order, is_active)
values
    ('00000000-0000-4000-8000-000000000001', 'JOBS', 'jobs', 'Jobs', 'Ajira',
     'Paid employment and career opportunities.', 'Ajira na fursa za kazi malipo.', 1, true),
    ('00000000-0000-4000-8000-000000000002', 'INTERNSHIPS', 'internships', 'Internships and Industrial Training',
     'Mafunzo na Kiungo cha Kazi',
     'Internships, industrial attachments and trainee roles.', 'Kiungo cha kazi na mafunzo ya kitumia.', 2, true),
    ('00000000-0000-4000-8000-000000000003', 'SCHOLARSHIPS', 'scholarships', 'Scholarships and Educational Funding',
     'Stipendi na Fedha za Elimu',
     'Scholarships, bursaries and study funding.', 'Stipendi na misaada ya masomo.', 3, true),
    ('00000000-0000-4000-8000-000000000004', 'FELLOWSHIPS', 'fellowships', 'Fellowships', 'Fellowship za Kitaaluma',
     'Fellowships for study, research and professional practice.', 'Fellowship za masomo na utafiti.', 4, true),
    ('00000000-0000-4000-8000-000000000005', 'GRANTS', 'grants', 'Grants and Funding', 'Misaada na Ufadhili',
     'Grants and funding for projects and organisations.', 'Misaada ya miradi na taasisi.', 5, true),
    ('00000000-0000-4000-8000-000000000006', 'TRAINING', 'professional-training', 'Professional Training',
     'Mafunzo ya Kitaaluma',
     'Certified and professional development courses.', 'Kozi za kitaaluma na ustadi.', 6, true),
    ('00000000-0000-4000-8000-000000000007', 'ENTREPRENEURSHIP', 'entrepreneurship',
     'Entrepreneurship and Business Incubation', 'Ujasiriamali na Uanzishaji wa Biashara',
     'Incubation, accelerators and enterprise support.', 'Uanzishaji biashara na msaada wa wajasiriamali.', 7, true),
    ('00000000-0000-4000-8000-000000000008', 'INNOVATION', 'innovation-challenges', 'Innovation Challenges',
     'Mashindano ya Uvumbuzi',
     'Challenge calls for new ideas and solutions.', 'Michuano ya uvumbuzi na suluhisho.', 8, true),
    ('00000000-0000-4000-8000-000000000009', 'HACKATHONS', 'hackathons', 'Hackathons and Competitions',
     'Hackathon na Mashindano',
     'Hackathons, competitions and contests.', 'Hackathon na mashindano ya kitaaluma.', 9, true),
    ('00000000-0000-4000-8000-00000000000a', 'VOLUNTEERING', 'volunteering', 'Volunteering', 'Ushiriki wa Kujitolea',
     'Voluntary and community service roles.', 'Kazi za kujitolea na jamii.', 10, true),
    ('00000000-0000-4000-8000-00000000000b', 'RESEARCH', 'research', 'Research', 'Utafiti',
     'Research positions, studentships and calls.', 'Nafasi za utafiti na wito wa utafiti.', 11, true),
    ('00000000-0000-4000-8000-00000000000c', 'EVENTS', 'conferences-events', 'Conferences and Events',
     'Mikutano na Matukio',
     'Conferences, workshops, seminars and events.', 'Mikutano, warsha na semina.', 12, true)
on conflict do nothing;

-- ---------------------------------------------------------------------------
-- Sample category attribute definitions (the versioned server-side schema, D-004)
-- ---------------------------------------------------------------------------
insert into category_attribute_definition
    (id, category_id, schema_version, attribute_key, label_en, label_sw, data_type,
     is_required, allowed_values, min_value, max_value, min_length, max_length, display_order)
values
    -- Jobs
    ('00000000-0000-4000-8000-000000000101', '00000000-0000-4000-8000-000000000001', 1,
     'salary_min', 'Minimum salary', 'Mshahara wa chini', 'DECIMAL', false, null, 0, null, null, null, 1),
    ('00000000-0000-4000-8000-000000000102', '00000000-0000-4000-8000-000000000001', 1,
     'salary_max', 'Maximum salary', 'Mshahara wa juu', 'DECIMAL', false, null, 0, null, null, null, 2),
    ('00000000-0000-4000-8000-000000000103', '00000000-0000-4000-8000-000000000001', 1,
     'salary_currency', 'Salary currency', 'Sarafu ya mshahara', 'ENUM', false,
     '["TZS", "USD", "EUR"]', null, null, null, null, 3),
    ('00000000-0000-4000-8000-000000000104', '00000000-0000-4000-8000-000000000001', 1,
     'experience_level', 'Experience level', 'Kiwango cha uzoefu', 'ENUM', true,
     '["ENTRY", "MID", "SENIOR", "EXECUTIVE"]', null, null, null, null, 4),
    ('00000000-0000-4000-8000-000000000105', '00000000-0000-4000-8000-000000000001', 1,
     'education_level', 'Minimum education level', 'Kiada cha elimu', 'ENUM', true,
     '["NONE", "CERTIFICATE", "DIPLOMA", "BACHELOR", "MASTER", "DOCTORATE"]', null, null, null, null, 5),
    -- Scholarships and Educational Funding
    ('00000000-0000-4000-8000-000000000106', '00000000-0000-4000-8000-000000000003', 1,
     'funding_type', 'Funding type', 'Aina ya ufadhili', 'ENUM', true,
     '["FULL", "PARTIAL", "TUITION_ONLY", "RESEARCH_ONLY"]', null, null, null, null, 1),
    ('00000000-0000-4000-8000-000000000107', '00000000-0000-4000-8000-000000000003', 1,
     'coverage_amount', 'Coverage amount', 'Kiasi kinachofunikwa', 'DECIMAL', false, null, 0, null, null, null, 2),
    ('00000000-0000-4000-8000-000000000108', '00000000-0000-4000-8000-000000000003', 1,
     'sponsor_name', 'Sponsor', 'Mdhamini', 'TEXT', false, null, null, null, 1, 200, 3),
    ('00000000-0000-4000-8000-000000000109', '00000000-0000-4000-8000-000000000003', 1,
     'eligible_study_levels', 'Eligible study levels', 'Viwango vya masomo', 'ENUM', true,
     '["SECONDARY", "UNDERGRADUATE", "POSTGRADUATE"]', null, null, null, null, 4),
    -- Hackathons and Competitions
    ('00000000-0000-4000-8000-00000000010a', '00000000-0000-4000-8000-000000000009', 1,
     'team_size_min', 'Minimum team size', 'Ukubwa mdogo wa timu', 'INTEGER', true, null, 1, null, null, null, 1),
    ('00000000-0000-4000-8000-00000000010b', '00000000-0000-4000-8000-000000000009', 1,
     'team_size_max', 'Maximum team size', 'Ukubwa mkubwa wa timu', 'INTEGER', false, null, null, null, null, null, 2),
    ('00000000-0000-4000-8000-00000000010c', '00000000-0000-4000-8000-000000000009', 1,
     'prize_pool', 'Prize pool', 'Zawadi', 'DECIMAL', false, null, 0, null, null, null, 3),
    ('00000000-0000-4000-8000-00000000010d', '00000000-0000-4000-8000-000000000009', 1,
     'theme', 'Theme', 'Mada', 'TEXT', false, null, null, null, 1, 300, 4),
    -- Internships (every attribute used by a seed opportunity is defined here)
    ('00000000-0000-4000-8000-000000000113', '00000000-0000-4000-8000-000000000002', 1,
     'duration_weeks', 'Duration in weeks', 'Muda (wiki)', 'INTEGER', true, null, 1, null, null, null, 1),
    ('00000000-0000-4000-8000-000000000114', '00000000-0000-4000-8000-000000000002', 1,
     'stipend_amount', 'Stipend amount', 'Kiasi cha posho', 'DECIMAL', false, null, 0, null, null, null, 2),
    ('00000000-0000-4000-8000-000000000115', '00000000-0000-4000-8000-000000000002', 1,
     'supervisor_title', 'Supervisor title', 'Cheo cha msemara', 'TEXT', false, null, null, null, 1, 160, 3),
    -- Conferences and Events
    ('00000000-0000-4000-8000-00000000010e', '00000000-0000-4000-8000-00000000000c', 1,
     'event_starts_on', 'Event start date', 'Tarehe ya kuanza', 'DATE', true, null, null, null, null, null, 1),
    ('00000000-0000-4000-8000-00000000010f', '00000000-0000-4000-8000-00000000000c', 1,
     'event_ends_on', 'Event end date', 'Tarehe ya kumalizia', 'DATE', true, null, null, null, null, null, 2),
    ('00000000-0000-4000-8000-000000000110', '00000000-0000-4000-8000-00000000000c', 1,
     'venue', 'Venue', 'Mahali', 'TEXT', false, null, null, null, 1, 250, 3),
    ('00000000-0000-4000-8000-000000000111', '00000000-0000-4000-8000-00000000000c', 1,
     'attendance_mode', 'Attendance mode', 'Njia ya kuhudhuria', 'ENUM', true,
     '["IN_PERSON", "ONLINE", "HYBRID"]', null, null, null, null, 4),
    ('00000000-0000-4000-8000-000000000112', '00000000-0000-4000-8000-00000000000c', 1,
     'registration_fee', 'Registration fee', 'Ada ya usajili', 'DECIMAL', false, null, 0, null, null, null, 5)
on conflict do nothing;

-- ---------------------------------------------------------------------------
-- Development actors
-- ---------------------------------------------------------------------------
insert into app_user (id, email, password_hash, display_name, system_role, status, locale, email_verified_at, created_at, updated_at)
values
    ('00000000-0000-4000-8000-000000000201', 'asha.seeker@example.org',
     'PLACEHOLDER-HASH-NO-LOGIN-POSSIBLE', 'Asha Juma', 'SEEKER', 'ACTIVE', 'sw', now(), now(), now()),
    ('00000000-0000-4000-8000-000000000202', 'dev.orgadmin@example.org',
     'PLACEHOLDER-HASH-NO-LOGIN-POSSIBLE', 'Dev Org Admin', 'ORGANIZATION_ADMIN', 'ACTIVE', 'en', now(), now(), now()),
    ('00000000-0000-4000-8000-000000000203', 'dev.admin@example.org',
     'PLACEHOLDER-HASH-NO-LOGIN-POSSIBLE', 'Dev Admin', 'ADMIN', 'ACTIVE', 'en', now(), now(), now())
on conflict do nothing;

insert into seeker_profile (id, user_id, first_name, last_name, phone, headline, bio, region, district,
                            profile_visibility, created_at, updated_at)
values ('00000000-0000-4000-8000-000000000211', '00000000-0000-4000-8000-000000000201',
        'Asha', 'Juma', '+255700000001', 'Computer science graduate, Dar es Salaam',
        'Development seed profile used for local manual testing only.',
        'Dar es Salaam', 'Ilala', 'PRIVATE', now(), now())
on conflict do nothing;

insert into seeker_education (id, profile_id, institution, qualification, field_of_study, start_date, end_date,
                              is_current, display_order, created_at, updated_at)
values ('00000000-0000-4000-8000-000000000221', '00000000-0000-4000-8000-000000000211',
        'University of Dar es Salaam', 'Bachelor Degree', 'Computer Science',
        '2019-11-01', '2023-06-30', false, 1, now(), now())
on conflict do nothing;

insert into seeker_skill (id, profile_id, name, level, display_order, created_at)
values
    ('00000000-0000-4000-8000-000000000231', '00000000-0000-4000-8000-000000000211', 'Java', 'INTERMEDIATE', 1, now()),
    ('00000000-0000-4000-8000-000000000232', '00000000-0000-4000-8000-000000000211', 'SQL', 'INTERMEDIATE', 2, now())
on conflict do nothing;

insert into seeker_interest (id, profile_id, category_id, created_at)
values
    ('00000000-0000-4000-8000-000000000241', '00000000-0000-4000-8000-000000000211',
     '00000000-0000-4000-8000-000000000002', now()),
    ('00000000-0000-4000-8000-000000000242', '00000000-0000-4000-8000-000000000211',
     '00000000-0000-4000-8000-000000000009', now())
on conflict do nothing;

-- ---------------------------------------------------------------------------
-- Development organization (already verified so that published seed rows are legal)
-- ---------------------------------------------------------------------------
insert into organization (id, owner_user_id, name, slug, description, website, contact_email, contact_phone,
                          region, district, founded_year, verification_status, created_at, updated_at)
values ('00000000-0000-4000-8000-000000000301', '00000000-0000-4000-8000-000000000202',
        'Tanzania Dev Hub', 'tanzania-dev-hub',
        'Development seed organisation. Not a real entity.',
        'https://example.org', 'hello@example.org', '+255700000000',
        'Dar es Salaam', 'Kinondoni', 2020, 'VERIFIED', now(), now())
on conflict do nothing;

insert into organization_member (id, organization_id, user_id, member_role, status, joined_at, created_at, updated_at)
values ('00000000-0000-4000-8000-000000000311', '00000000-0000-4000-8000-000000000301',
        '00000000-0000-4000-8000-000000000202', 'OWNER', 'ACTIVE', now(), now(), now())
on conflict do nothing;

insert into organization_verification_request (id, organization_id, status, submitted_at, decided_at,
                                               decided_by_user_id, notes, document_key, created_at)
values ('00000000-0000-4000-8000-000000000321', '00000000-0000-4000-8000-000000000301',
        'VERIFIED', now() - interval '10 days', now() - interval '8 days',
        '00000000-0000-4000-8000-000000000203', 'Development seed record.',
        'dev/seed/verification-placeholder-key', now())
on conflict do nothing;

-- ---------------------------------------------------------------------------
-- Development opportunities: one published internal, one published external, one draft
-- ---------------------------------------------------------------------------
insert into opportunity (id, organization_id, category_id, title, summary, description, region, district,
                         work_mode, opportunity_type, application_mode, external_url, source_name, source_url,
                         moderation_status, publication_status, opens_at, closes_at, published_at,
                         attributes_schema_version, attributes, created_by_user_id, created_at, updated_at)
values
    ('00000000-0000-4000-8000-000000000401', '00000000-0000-4000-8000-000000000301',
     '00000000-0000-4000-8000-000000000002', 'Backend Engineering Internship',
     'Twelve-week internship with a stipend. Development seed row.',
     'A development seed opportunity used to exercise internal applications. Apply through TDOP.',
     'Dar es Salaam', 'Kinondoni', 'HYBRID', null, 'INTERNAL', null,
     'Tanzania Dev Hub', null, 'APPROVED', 'PUBLISHED',
     now() - interval '5 days', now() + interval '30 days', now() - interval '3 days', 1,
     '{"duration_weeks": 12, "stipend_amount": 250000, "supervisor_title": "Senior Engineer"}',
     '00000000-0000-4000-8000-000000000202', now() - interval '5 days', now() - interval '3 days'),
    ('00000000-0000-4000-8000-000000000402', '00000000-0000-4000-8000-000000000301',
     '00000000-0000-4000-8000-000000000003', 'Undergraduate Scholarship 2026',
     'Full tuition scholarship hosted on the official destination. Development seed row.',
     'External-mode seed opportunity. The application is submitted on the provider''s own site, not through TDOP.',
     'Mbeya', 'Mbeya', 'ONSITE', 'FULL_TIME', 'EXTERNAL', 'https://example.org/scholarships/2026',
     'Tanzania Dev Hub', 'https://example.org/scholarships/2026', 'APPROVED', 'PUBLISHED',
     now() - interval '10 days', now() + interval '45 days', now() - interval '9 days', 1,
     '{"funding_type": "FULL", "coverage_amount": 5000000, "sponsor_name": "Dev Hub Foundation", "eligible_study_levels": "UNDERGRADUATE"}',
     '00000000-0000-4000-8000-000000000202', now() - interval '10 days', now() - interval '9 days'),
    ('00000000-0000-4000-8000-000000000403', '00000000-0000-4000-8000-000000000301',
     '00000000-0000-4000-8000-000000000009', 'Climate Tech Hackathon (draft)',
     'Unpublished draft so the moderation queue has something to show.',
     'Development seed row kept in DRAFT so tests and screens can exercise unpublished state.',
     'Dar es Salaam', 'Temeke', 'ONSITE', 'PROJECT', 'INTERNAL', null, null, null,
     'PENDING', 'UNPUBLISHED', now() + interval '7 days', now() + interval '37 days', null, 1,
     '{"team_size_min": 2, "team_size_max": 5, "prize_pool": 1000000, "theme": "Climate tech"}',
     '00000000-0000-4000-8000-000000000202', now() - interval '2 days', now() - interval '2 days')
on conflict do nothing;

insert into opportunity_status_history (id, opportunity_id, status_kind, from_status, to_status,
                                        changed_by_user_id, reason, created_at)
values
    ('00000000-0000-4000-8000-000000000411', '00000000-0000-4000-8000-000000000401', 'MODERATION',
     'DRAFT', 'APPROVED', '00000000-0000-4000-8000-000000000203', 'Development seed.', now() - interval '3 days'),
    ('00000000-0000-4000-8000-000000000412', '00000000-0000-4000-8000-000000000401', 'PUBLICATION',
     'UNPUBLISHED', 'PUBLISHED', '00000000-0000-4000-8000-000000000203', 'Development seed.', now() - interval '3 days'),
    ('00000000-0000-4000-8000-000000000413', '00000000-0000-4000-8000-000000000403', 'MODERATION',
     'DRAFT', 'PENDING', '00000000-0000-4000-8000-000000000202', 'Development seed.', now() - interval '2 days')
on conflict do nothing;

-- ---------------------------------------------------------------------------
-- One internal application, one saved item, one notification
-- ---------------------------------------------------------------------------
insert into application (id, opportunity_id, seeker_user_id, status, cover_letter, form_responses,
                         submitted_at, created_at, updated_at)
values ('00000000-0000-4000-8000-000000000501', '00000000-0000-4000-8000-000000000401',
        '00000000-0000-4000-8000-000000000201', 'SUBMITTED',
        'Development seed application written from the seeker profile.',
        '{"why_interesting": "I want to work on public-interest software.", "available_from": "2026-12-01"}',
        now() - interval '1 day', now() - interval '1 day', now() - interval '1 day')
on conflict do nothing;

insert into application_status_history (id, application_id, from_status, to_status, changed_by_user_id, reason, created_at)
values ('00000000-0000-4000-8000-000000000511', '00000000-0000-4000-8000-000000000501',
        null, 'SUBMITTED', '00000000-0000-4000-8000-000000000201', 'Development seed.', now() - interval '1 day')
on conflict do nothing;

insert into saved_opportunity (id, user_id, opportunity_id, saved_at)
values ('00000000-0000-4000-8000-000000000521', '00000000-0000-4000-8000-000000000201',
        '00000000-0000-4000-8000-000000000402', now() - interval '6 hours')
on conflict do nothing;

insert into notification (id, user_id, type, title, body, payload, read_at, created_at)
values ('00000000-0000-4000-8000-000000000531', '00000000-0000-4000-8000-000000000201',
        'APPLICATION_SUBMITTED', 'Application submitted',
        'Your application for Backend Engineering Internship was recorded.',
        '{"opportunity_id": "00000000-0000-4000-8000-000000000401"}', null, now() - interval '1 day')
on conflict do nothing;

insert into notification_preference (id, user_id, notification_type, in_app_enabled, email_enabled, created_at, updated_at)
values ('00000000-0000-4000-8000-000000000541', '00000000-0000-4000-8000-000000000201',
        'APPLICATION_SUBMITTED', true, false, now(), now())
on conflict do nothing;

insert into platform_setting (setting_key, value, description, updated_by_user_id, updated_at)
values ('seed_data_marker', '"development-seed"',
        'Marks this database as carrying development seed data. Never present in production.',
        '00000000-0000-4000-8000-000000000203', now())
on conflict do nothing;
