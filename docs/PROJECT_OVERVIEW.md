# TDOP Project Overview

**Status:** Phase 0 planning. No application implementation exists.

## Vision

The Tanzania Digital Opportunity Platform (TDOP) is a centralized, trusted service that helps people discover, assess, save, apply for and track opportunities that support education, employment, entrepreneurship and professional development. It connects opportunity seekers with legitimate organizations and gives administrators tools to protect platform quality.

The initial operating focus is Tanzania, with English and Swahili as product languages and an architecture that can support future regional and international expansion.

## Problem and objectives

Opportunity information is distributed across organizations and channels, making it difficult to assess legitimacy, identify relevant options and keep track of deadlines. TDOP aims to:

1. Centralize opportunity discovery and useful application details.
2. Improve access to educational and professional opportunities.
3. Give organizations a legitimate publishing and applicant-review workflow.
4. Build trust through organization verification, moderation and reporting.
5. Help seekers organize saved items, applications and deadlines.
6. Provide an accessible, responsive, bilingual service.

## Audiences

- Students, graduates, job seekers and professionals.
- Entrepreneurs, researchers, innovators and volunteers.
- Employers, educational institutions, NGOs and public/development institutions.
- Platform administrators and moderators.

## Opportunity types

The initial catalogue supports Jobs; Internships and Industrial Training; Scholarships and Educational Funding; Fellowships; Grants and Funding; Professional Training; Entrepreneurship and Business Incubation; Innovation Challenges; Hackathons and Competitions; Volunteering; Research; and Conferences and Events. Categories are data-driven so adding a category does not require a new application redesign.

## Recommended initial release boundary

The first public release is planned to include:

- Public opportunity listing, keyword search, filters, pagination and opportunity details.
- Seeker registration, profile, preferences, saved opportunities and internal application tracking.
- Organization registration/profile and verification; only verified organizations may submit opportunities for public publication.
- Administrator verification, opportunity moderation, reports and protected user/organization management.
- Both external application links and TDOP-managed applications. Internal applications use structured, profile-backed form data; file attachments are deferred.
- Core in-app notifications for important verification, application-status and deadline events.
- English and Swahili interface localization, responsive layouts and accessibility review.

The exact acceptance criteria are in [Requirements](REQUIREMENTS.md). The first release is not a promise that all advanced features will be delivered in one phase.

## Deferred capabilities

Personalized or machine-learning recommendations, external source integrations, duplicate detection, SMS/WhatsApp/push channels, advanced analytics, file attachments to internal applications, multi-tenant enterprise features and non-Tanzanian compliance/deployment tailoring are deferred until core workflows are reliable and their privacy, cost and operational needs are understood.

## Product principles

- **Trust first:** distinguish verified providers, moderated content and external sources accurately.
- **Useful over decorative:** every control must work and every statistic must come from real data.
- **Privacy by design:** collect only needed data, restrict access and define retention before production.
- **Inclusive by default:** mobile-first, keyboard accessible, readable and localized.
- **Incremental delivery:** modular monolith and explicit phase gates; avoid premature distributed systems.
- **Honest status:** documentation and dashboards must not imply unimplemented capabilities or fabricate metrics.

## Design direction

Use a clean, professional and trustworthy visual system. Initial tokens: deep navy `#0B1220`, primary blue `#2563EB`, electric blue `#3B82F6`, white `#FFFFFF`, light background `#F8FAFC`, main text `#0F172A`, secondary text `#64748B`, success `#16A34A`, warning `#F59E0B` and danger `#DC2626`. Use a consistent sans-serif type system such as Inter; verify contrast and WCAG 2.2 AA criteria during interface phases. Avoid overusing gradients, animation and shadows.

## Measures of success

Measure real service outcomes only after implementation and privacy review: valid published opportunities, search-to-detail engagement, application completion, deadline follow-through, organization verification turnaround, report resolution and accessibility defects. Set baselines and targets from observed data; do not display fabricated platform statistics.

## Current delivery status

The repository was empty at initial inspection. The Phase 0 planning baseline is now published to `main` and `develop`. Live Kanban board creation is owner-approved but pending GitHub authentication; no board or issue cards exist yet. Application code remains unimplemented.
