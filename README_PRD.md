TDOP --- README_PRD.md

Tanzania Digital Opportunity Platform

«Master Product Requirements Document, Implementation Backlog, Developer
Ownership Guide, Architecture Reference and OpenCode Development
Instructions»

Project: TDOP --- Tanzania Digital Opportunity Platform Document:
"README_PRD.md" Status: Active Development Primary Branch: "main"
Developer 01 Branch: "features/developer-01" Developer 02 Branch:
"features/developer-02"

------------------------------------------------------------------------

1.  PURPOSE OF THIS DOCUMENT

This document is the master development reference for TDOP.

It exists to answer five questions:

1.  What is TDOP?
2.  What should TDOP become?
3.  What functionality must exist?
4.  Which developer owns each area?
5.  How should OpenCode or another coding agent implement the work
    without breaking existing functionality?

This document must be treated as a Product + Engineering Execution
Guide.

It is NOT permission to blindly rebuild the application.

The repository remains the technical source of truth.

------------------------------------------------------------------------

2.  CORE DEVELOPMENT PRINCIPLE

Every task must follow:

UNDERSTAND ↓ AUDIT ↓ PLAN ↓ IMPLEMENT ↓ TEST ↓ VERIFY ↓ HARDEN ↓
DOCUMENT

Never:

GUESS ↓ REWRITE ↓ BREAK ↓ PATCH

------------------------------------------------------------------------

3.  CRITICAL RULE --- AUDIT BEFORE IMPLEMENTATION

The feature list in this document represents the target state and master
backlog.

It does NOT automatically mean that every feature is missing.

Before implementing any feature, inspect the repository and classify it:

DONE PARTIAL MISSING BROKEN NEEDS VERIFICATION

Example

If this document says:

User Profile - Education - Skills - Experience - Career Goals

and the repository already contains Education and Skills, do NOT rebuild
them.

Instead:

Existing Education → DONE Existing Skills → DONE Experience → MISSING
Career Goals → PARTIAL

Then implement only the gaps.

------------------------------------------------------------------------

4.  TDOP PRODUCT DEFINITION

4.1 Product Name

TDOP --- Tanzania Digital Opportunity Platform

------------------------------------------------------------------------

4.2 Product Positioning

«A trusted opportunity discovery and progress platform for Tanzania.»

TDOP is not simply a job board.

TDOP is intended to become a trusted digital ecosystem where individuals
can discover, understand, prepare for, apply to, track and progress
through legitimate opportunities.

------------------------------------------------------------------------

5.  TDOP CORE JOURNEY

                  ┌─────────────┐
                  │   DISCOVER  │
                  └──────┬──────┘
                         ↓
                  ┌─────────────┐
                  │  UNDERSTAND │
                  └──────┬──────┘
                         ↓
                  ┌─────────────┐
                  │   PREPARE   │
                  └──────┬──────┘
                         ↓
                  ┌─────────────┐
                  │    APPLY    │
                  └──────┬──────┘
                         ↓
                  ┌─────────────┐
                  │    TRACK    │
                  └──────┬──────┘
                         ↓
                  ┌─────────────┐
                  │   PROGRESS  │
                  └─────────────┘

TDOP should not stop at:

«"Here is an opportunity."»

It should eventually help answer:

«"Is this right for me?" "What do I need?" "How do I prepare?" "How do I
apply?" "What happened to my application?" "What should I pursue next?"»

------------------------------------------------------------------------

6.  OPPORTUNITY ECOSYSTEM

TDOP should support multiple opportunity categories.

Employment

-   Jobs
-   Internships
-   Graduate opportunities
-   Apprenticeships
-   Part-time opportunities
-   Remote jobs
-   International jobs

Education

-   Scholarships
-   Fellowships
-   Training
-   Certifications
-   Courses
-   Exchange opportunities
-   Academic programs

Entrepreneurship & Finance

-   Grants
-   Loans
-   Financing
-   Investment
-   Entrepreneurship programs
-   Business competitions
-   Startup opportunities

Public / Development

-   Government programs
-   NGO opportunities
-   Development programs
-   Youth programs
-   Volunteer opportunities

Professional / Innovation

-   Research
-   Innovation challenges
-   Competitions
-   Conferences
-   Events
-   ICT / Digital opportunities
-   Tenders / procurement

The system must allow new opportunity categories without requiring a
major architectural rewrite.

------------------------------------------------------------------------

7.  TDOP PRODUCT MODEL

                          TDOP
                           │
         ┌─────────────────┼─────────────────┐
         │                 │                 │
         ▼                 ▼                 ▼

    INDIVIDUALS ORGANIZATIONS PLATFORM │ │ │ ▼ ▼ ▼ Discover Publish
    Govern Prepare Manage Verify Apply Candidates Moderate Track
    Analytics Protect Progress Team Analyze

------------------------------------------------------------------------

8.  PRIMARY USER TYPES

TDOP should support:

USER ORGANIZATION ORGANIZATION_ADMIN ORGANIZATION_MEMBER
VERIFICATION_OFFICER MODERATOR ADMIN SUPER_ADMIN

------------------------------------------------------------------------

9.  TWO-DEVELOPER OWNERSHIP

Developer 01

Branch:

features/developer-01

Primary responsibility:

«USER / SEEKER / DISCOVERY / PERSONAL EXPERIENCE»

Developer 01 owns the user-facing journey.

------------------------------------------------------------------------

Developer 02

Branch:

features/developer-02

Primary responsibility:

«ORGANIZATION / TRUST / MODERATION / ADMIN / PLATFORM OPERATIONS»

Developer 02 owns organization-facing and platform-governance
functionality.

------------------------------------------------------------------------

10. GIT ARCHITECTURE

                           main
                            │
              ┌─────────────┴─────────────┐
              │                           │
              ▼                           ▼

    features/developer-01 features/developer-02 │ │ ▼ ▼ USER / DISCOVERY
    ORG / TRUST / ADMIN │ │ └─────────────┬─────────────┘ ▼ Pull Request
    │ ▼ main

------------------------------------------------------------------------

11. GIT RULES

"main" must remain stable.

Developers must:

-   Work only on their assigned branch.
-   Pull latest changes regularly.
-   Make focused commits.
-   Push to their own branch.
-   Use Pull Requests.
-   Review changes before merging.
-   Resolve conflicts carefully.
-   Test before merge.

Never:

git push --force git reset --hard another developer's work delete
another developer's branch rewrite shared history overwrite another
developer's files blindly replace directories

------------------------------------------------------------------------

12. SHARED AREAS

Some systems cannot safely be independently redesigned by both
developers.

These require coordination:

-   Database schema
-   Database migrations
-   Authentication architecture
-   RBAC
-   User model
-   Organization model
-   Opportunity model
-   Application model
-   Notification model
-   Audit model
-   Core API contracts
-   Global routing
-   Shared UI components
-   Design system
-   Environment configuration
-   Security architecture

Shared change rule

If either developer needs to change a shared area:

1.  Inspect current implementation.
2.  Identify exact reason.
3.  Check whether another developer is modifying the same area.
4.  Make the smallest safe change.
5.  Inform the other developer.
6.  Test affected functionality.
7.  Document the change.

------------------------------------------------------------------------

13. PRIORITY SYSTEM

P0 = Core / Blocking P1 = Important Production P2 = Enhancement P3 =
Future / Progressive

P0

The platform cannot be considered functionally complete without it.

P1

Required for a serious production-ready platform.

P2

Important improvement but not necessarily blocking initial production.

P3

Future growth, advanced intelligence or expansion.

------------------------------------------------------------------------

14. DEVELOPER 01 --- MASTER RESPONSIBILITY

Branch:

features/developer-01

Developer 01 owns:

Authentication UX User Profile Education Skills Experience Interests
Career Goals Documents Opportunity Discovery Search Filters Saved
Opportunities Opportunity Comparison User Applications Application
Tracking Recommendations User Notifications User Dashboard Public
Landing Page User Mobile UX Accessibility Internationalization --- user
side SEO

------------------------------------------------------------------------

15. DEV 01 --- AUTHENTICATION

Priority: P0

Tasks

-   [ ] Audit current authentication.
-   [ ] Login.
-   [ ] Registration.
-   [ ] Logout.
-   [ ] Current user endpoint.
-   [ ] Session persistence.
-   [ ] Refresh token handling.
-   [ ] Access token expiration.
-   [ ] Forgot password.
-   [ ] Reset password.
-   [ ] Password validation.
-   [ ] Protected routes.
-   [ ] Unauthorized state.
-   [ ] Expired session handling.
-   [ ] Authentication error handling.
-   [ ] Authentication loading states.
-   [ ] Authentication rate-limit integration.
-   [ ] Security-aware logout.
-   [ ] Role-based redirect.

Acceptance Criteria

A user can:

Register ↓ Login ↓ Access own account ↓ Refresh session ↓ Logout

without exposing unauthorized resources.

------------------------------------------------------------------------

16. DEV 01 --- USER PROFILE

Priority: P0

Tasks

-   [ ] Personal information.
-   [ ] Education.
-   [ ] Skills.
-   [ ] Experience.
-   [ ] Interests.
-   [ ] Career goals.
-   [ ] Location.
-   [ ] Profile completion.
-   [ ] Profile visibility.
-   [ ] Profile editing.
-   [ ] Profile validation.
-   [ ] User preferences.

Profile structure

USER │ ├── Personal Information ├── Education ├── Skills ├── Experience
├── Interests ├── Career Goals ├── Location ├── Documents ├──
Preferences └── Activity

Important

Profile completion must come from actual stored data.

Never hardcode:

Profile = 80%

unless the backend calculation actually returns 80%.

------------------------------------------------------------------------

17. DEV 01 --- DOCUMENT MANAGEMENT

Priority: P0

Support:

-   [ ] CV
-   [ ] Certificates
-   [ ] Academic documents
-   [ ] Portfolio
-   [ ] Other application documents

Security

-   [ ] File type validation.
-   [ ] File size validation.
-   [ ] Private storage.
-   [ ] Authorization.
-   [ ] Secure download.
-   [ ] Signed URLs where applicable.
-   [ ] Secure deletion.
-   [ ] Access auditing where necessary.
-   [ ] Upload abuse protection.
-   [ ] Malware scanning strategy.

Never expose private documents through unrestricted public URLs.

------------------------------------------------------------------------

18. DEV 01 --- OPPORTUNITY DISCOVERY

Priority: P0

Users must be able to:

-   [ ] Browse opportunities.
-   [ ] Search.
-   [ ] Filter.
-   [ ] Sort.
-   [ ] Open details.
-   [ ] Understand eligibility.
-   [ ] View deadline.
-   [ ] View source.
-   [ ] View application method.
-   [ ] Save.
-   [ ] Apply.
-   [ ] Compare where supported.

------------------------------------------------------------------------

19. DEV 01 --- SEARCH & FILTERS

Priority: P0

Filters:

Keyword Category Location Deadline Education Skills Experience
Organization Remote Funding Opportunity type

Support:

-   [ ] Pagination.
-   [ ] Sorting.
-   [ ] Loading.
-   [ ] Empty state.
-   [ ] Error state.
-   [ ] Retry.
-   [ ] Clear filters.

Future:

-   [ ] Saved searches.
-   [ ] Search alerts.
-   [ ] Semantic search.
-   [ ] Natural language search.

------------------------------------------------------------------------

20. DEV 01 --- SAVED OPPORTUNITIES

Priority: P0

-   [ ] Save.
-   [ ] Unsave.
-   [ ] Saved opportunities page.
-   [ ] Pagination.
-   [ ] Deadline awareness.
-   [ ] Empty state.
-   [ ] Real database data.

------------------------------------------------------------------------

21. DEV 01 --- OPPORTUNITY COMPARISON

Priority: P2

Users may compare:

Eligibility Deadline Location Funding Skills Education Experience
Application method

Keep the first implementation simple.

------------------------------------------------------------------------

22. DEV 01 --- APPLICATION EXPERIENCE

Priority: P0

User side:

-   [ ] Start application.
-   [ ] Validate eligibility where possible.
-   [ ] Select required documents.
-   [ ] Submit.
-   [ ] Prevent duplicate application.
-   [ ] View status.
-   [ ] View history.
-   [ ] Withdraw where allowed.
-   [ ] Receive notifications.

Application statuses:

PREPARING SUBMITTED UNDER_REVIEW SHORTLISTED INTERVIEW ACCEPTED REJECTED
WITHDRAWN

------------------------------------------------------------------------

23. DEV 01 --- APPLICATION TIMELINE

Example:

Application Started │ ▼ Application Submitted │ ▼ Under Review │ ▼
Shortlisted │ ▼ Interview │ ├──────► Rejected │ └──────► Accepted

The UI must reflect the real backend history.

------------------------------------------------------------------------

24. DEV 01 --- RECOMMENDATION ENGINE

Priority: P1

Inputs:

-   Skills.
-   Education.
-   Experience.
-   Interests.
-   Career goals.
-   Location.
-   Eligibility.
-   Previous activity.

Start with explainable rules.

Example:

Eligibility 30% Skills match 25% Education match 15% Career goal 15%
Location 10% Interest 5%

Do not present a recommendation as AI-generated unless an actual AI
system generated it.

Every recommendation should eventually explain:

«"Why am I seeing this?"»

------------------------------------------------------------------------

25. DEV 01 --- USER NOTIFICATIONS

Priority: P0

User notifications include:

-   Application submitted.
-   Application status changed.
-   Deadline reminder.
-   Recommendation.
-   Verification result.
-   Organization response.
-   Security notification.

Required:

-   [ ] Notification center.
-   [ ] Read/unread.
-   [ ] History.
-   [ ] Preferences.
-   [ ] Deep links.
-   [ ] Empty state.

------------------------------------------------------------------------

26. DEV 01 --- USER DASHBOARD

Priority: P0

Dashboard should answer:

«What should I do next?»

Include:

-   Profile completion.
-   Recommended opportunities.
-   Saved opportunities.
-   Recent applications.
-   Application status.
-   Upcoming deadlines.
-   Notifications.
-   Skills.
-   Career goals.
-   Documents.
-   Verification state where relevant.
-   Quick actions.

Sketch

┌─────────────────────────────────────────────────────┐ │ Welcome back │
│ What should you do next? │ │ │ │ \[ Search
opportunities......................... \] │ │ │ │ Profile Completion │ │
███████████████░░░░ │ │ │ │ Recommended Opportunities │ │ │ │
┌─────────┐ ┌─────────┐ ┌─────────┐ │ │ │ Job │ │Scholar. │ │Training │
│ │ └─────────┘ └─────────┘ └─────────┘ │ │ │ │ My Applications │ │ │ │
Upcoming Deadlines │
└─────────────────────────────────────────────────────┘

------------------------------------------------------------------------

27. DEV 01 --- PUBLIC LANDING PAGE

Priority: P0

The landing page is the public front door of TDOP.

Do NOT replace existing application dashboards.

Flow:

LANDING │ ├── Search │ ↓ │ DISCOVERY │ ├── Get Started │ ↓ │ REGISTER │
└── Login ↓ ROLE DASHBOARD

Navigation

Home Fursa Mashirika Kuhusu TDOP Jinsi Inavyofanya Kazi Help EN \| SW
Login Get Started

------------------------------------------------------------------------

28. LANDING PAGE CONTENT

Suggested hero:

«Fursa za Kesho Ziko Hapa»

Supporting message:

«TDOP inakuunganisha na fursa za ajira, masomo, biashara, ufadhili,
mafunzo na maendeleo kutoka Tanzania na duniani.»

Sections:

Hero ↓ Search ↓ Trust indicators ↓ Opportunity categories ↓ Featured
opportunities ↓ How TDOP works ↓ Why TDOP ↓ Personalized discovery ↓
Application journey ↓ For organizations ↓ Trust & safety ↓ Final CTA ↓
Footer

Featured opportunities MUST come from real data.

If no data exists:

No featured opportunities available yet.

Never fabricate.

------------------------------------------------------------------------

29. DEV 01 --- MOBILE UX

Priority: P1

Support:

320px 375px 425px 768px 1024px 1280px 1440px 1920px

Priorities:

-   Mobile-first.
-   Touch-friendly.
-   Fast loading.
-   Low-data awareness.
-   Readable text.
-   Reliable forms.
-   Simple navigation.

------------------------------------------------------------------------

30. DEV 01 --- ACCESSIBILITY

Priority: P1

Target:

WCAG 2.2 AA where practical.

Required:

-   Semantic HTML.
-   Keyboard navigation.
-   Focus states.
-   Accessible labels.
-   Form errors.
-   Color contrast.
-   Alt text.
-   Screen-reader-friendly structure.
-   Reduced motion.

------------------------------------------------------------------------

31. DEV 01 --- INTERNATIONALIZATION

Languages:

English Kiswahili

Requirements:

-   [ ] Language switcher.
-   [ ] Consistent translations.
-   [ ] No unnecessary hardcoded UI strings.
-   [ ] Localized dates/numbers where applicable.
-   [ ] Preserve meaning.

------------------------------------------------------------------------

32. DEV 01 --- SEO

Public pages should include:

-   Title.
-   Meta description.
-   H1/H2 hierarchy.
-   Canonical URL.
-   Open Graph metadata.
-   Social previews.
-   Structured data where appropriate.
-   Semantic HTML.

Suggested title:

TDOP --- Tanzania Digital Opportunity Platform

------------------------------------------------------------------------

33. DEVELOPER 02 --- MASTER RESPONSIBILITY

Branch:

features/developer-02

Developer 02 owns:

RBAC Organizations Organization Profiles Organization Teams Permissions
Opportunity Management Opportunity Publishing Verification Candidate
Management Moderation Reports Anti-Fraud Trust Admin Super Admin
Analytics Audit Platform Notifications Deadline Engine Observability
Production Operations

------------------------------------------------------------------------

34. DEV 02 --- RBAC

Priority: P0

Roles:

USER ORGANIZATION ORGANIZATION_ADMIN ORGANIZATION_MEMBER
VERIFICATION_OFFICER MODERATOR ADMIN SUPER_ADMIN

Required:

-   [ ] Permission matrix.
-   [ ] Server-side authorization.
-   [ ] Ownership validation.
-   [ ] Organization membership validation.
-   [ ] Organization isolation.
-   [ ] Admin permissions.
-   [ ] Moderator permissions.
-   [ ] Verification officer permissions.
-   [ ] Super Admin permissions.
-   [ ] IDOR prevention.
-   [ ] Audit sensitive actions.

Frontend route protection is NOT sufficient.

------------------------------------------------------------------------

35. DEV 02 --- ORGANIZATION MANAGEMENT

Priority: P0

-   [ ] Create organization.
-   [ ] Organization profile.
-   [ ] Description.
-   [ ] Logo.
-   [ ] Website.
-   [ ] Contact details.
-   [ ] Organization type.
-   [ ] Location.
-   [ ] Verification status.
-   [ ] Opportunity list.
-   [ ] Organization activity.

------------------------------------------------------------------------

36. DEV 02 --- ORGANIZATION TEAM

Priority: P0

Support:

ORGANIZATION_ADMIN ORGANIZATION_MEMBER

Tasks:

-   [ ] Invite member.
-   [ ] Accept invitation.
-   [ ] Remove member.
-   [ ] Assign role.
-   [ ] Permission management.
-   [ ] Team activity.
-   [ ] Ownership checks.
-   [ ] Audit.

------------------------------------------------------------------------

37. DEV 02 --- OPPORTUNITY ENGINE

Priority: P0

Opportunity lifecycle:

DRAFT ↓ VALIDATION ↓ SUBMITTED ↓ VERIFICATION ↓ MODERATION ↓ APPROVED ↓
PUBLISHED ↓ CLOSING SOON ↓ EXPIRED ↓ ARCHIVED

Exceptional states:

REJECTED SUSPENDED WITHDRAWN

------------------------------------------------------------------------

38. OPPORTUNITY DATA MODEL

Opportunity should support:

-   Title.
-   Description.
-   Category.
-   Organization.
-   Source.
-   Source URL.
-   Official application URL.
-   Deadline.
-   Location.
-   Remote / On-site / Hybrid.
-   Education requirements.
-   Skills.
-   Experience.
-   Eligibility.
-   Funding.
-   Required documents.
-   Application method.
-   Verification status.
-   Moderation status.
-   Publication status.
-   Created date.
-   Updated date.
-   Last verified date.
-   Expiry.

------------------------------------------------------------------------

39. OPPORTUNITY TRUST

Every opportunity should eventually answer:

WHO published it? WHERE did it come from? IS the organization verified?
WHEN was it last checked? WHEN does it close? HOW do I apply? WHAT
evidence supports it?

Avoid unsupported claims such as:

100% scam-free

Use measurable trust signals instead.

------------------------------------------------------------------------

40. DEV 02 --- ORGANIZATION OPPORTUNITY CREATION

Priority: P0

Workflow:

Create Draft ↓ Complete Details ↓ Validate ↓ Preview ↓ Submit ↓
Verification ↓ Moderation ↓ Publish

Required:

-   [ ] Draft.
-   [ ] Validation.
-   [ ] Preview.
-   [ ] Submit.
-   [ ] Edit.
-   [ ] Verification.
-   [ ] Moderation.
-   [ ] Publish.
-   [ ] Suspend.
-   [ ] Archive.
-   [ ] Restore.
-   [ ] Expiry.

------------------------------------------------------------------------

41. DEV 02 --- CANDIDATE MANAGEMENT

Priority: P0

Organizations should be able to:

-   [ ] View authorized applicants.
-   [ ] Search candidates.
-   [ ] Filter candidates.
-   [ ] Review applications.
-   [ ] Shortlist.
-   [ ] Reject.
-   [ ] Add internal notes.
-   [ ] Track candidate status.
-   [ ] Communicate where supported.
-   [ ] View funnel analytics.

Organizations must never see applicants belonging to opportunities they
are not authorized to manage.

------------------------------------------------------------------------

42. DEV 02 --- ORGANIZATION VERIFICATION

Priority: P0

Workflow:

Organization ↓ Verification Request ↓ Documents ↓ Verification Queue ↓
Review ↓ ┌──────────┬──────────┬──────────────┐ │ APPROVE │ REJECT │
MORE INFO │ └──────────┴──────────┴──────────────┘

Required:

-   [ ] Document submission.
-   [ ] Review.
-   [ ] Approve.
-   [ ] Reject.
-   [ ] Request information.
-   [ ] Verification history.
-   [ ] Audit.
-   [ ] Reason capture.
-   [ ] Status.

------------------------------------------------------------------------

43. VERIFICATION OFFICER DASHBOARD

Priority: P0

Include:

-   Pending verification.
-   Verification queue.
-   Documents.
-   Review.
-   Approve.
-   Reject.
-   Request information.
-   Verification history.
-   Statistics.

------------------------------------------------------------------------

44. DEV 02 --- MODERATION

Priority: P0

Moderators review:

-   Pending opportunities.
-   Reported opportunities.
-   Suspicious opportunities.
-   Duplicate opportunities.
-   Unsafe links.
-   Misleading content.
-   Policy violations.

Actions:

APPROVE REJECT SUSPEND ARCHIVE RESTORE REQUEST INFORMATION

Every action should be auditable.

------------------------------------------------------------------------

45. DEV 02 --- REPORTING SYSTEM

Priority: P0

Users should be able to report:

Scam Fake organization Suspicious link Misleading information Duplicate
Expired Wrong eligibility Inappropriate content Other

Workflow:

REPORT ↓ QUEUE ↓ INVESTIGATION ↓ DECISION ↓ ACTION ↓ AUDIT

------------------------------------------------------------------------

46. DEV 02 --- ANTI-FRAUD

Priority: P1

Potential risk signals:

-   Duplicate opportunities.
-   Suspicious URLs.
-   Repeated reports.
-   Unverified organization.
-   Unusual publishing patterns.
-   Repeated rejected opportunities.
-   Deadline anomalies.
-   Source quality.
-   Suspicious application destinations.

Possible risk levels:

LOW MEDIUM HIGH

Risk scoring should support human review.

It should NOT automatically accuse a person or organization without
evidence.

------------------------------------------------------------------------

47. DEV 02 --- ADMIN DASHBOARD

Priority: P0

Dashboard:

Users Organizations Verified Organizations Pending Verification
Opportunities Pending Moderation Published Opportunities Suspended
Opportunities Reports Fraud Signals Applications Platform Activity Audit
Security

No hardcoded statistics.

------------------------------------------------------------------------

48. DEV 02 --- SUPER ADMIN

Priority: P1

Capabilities may include:

-   Full platform oversight.
-   Role management.
-   System configuration.
-   Security controls.
-   Category management.
-   Opportunity state configuration.
-   Notification configuration.
-   Trust configuration.
-   Moderation configuration.
-   Platform health.

All sensitive Super Admin actions must be audited.

------------------------------------------------------------------------

49. DEV 02 --- PLATFORM ANALYTICS

Priority: P1

Metrics:

Users Organizations Opportunities Applications Views Saves Searches
Recommendations Reports Verification activity Moderation activity
Successful Opportunity Outcomes

Never fabricate numbers.

No data:

No data available yet.

------------------------------------------------------------------------

50. DEV 02 --- ORGANIZATION ANALYTICS

Organization analytics may include:

Views Saves Applications Shortlisted Interviews Accepted Conversion
rates

Only calculate metrics from real records.

------------------------------------------------------------------------

51. DEV 02 --- DEADLINE ENGINE

Priority: P1

Opportunity states:

OPEN CLOSING SOON EXPIRED

Possible reminders:

30 days 14 days 7 days 3 days 1 day

Reminder periods must be configurable.

Expired opportunities should not remain presented as active
opportunities.

------------------------------------------------------------------------

52. DEV 02 --- AUDIT LOG

Priority: P0

Audit sensitive actions:

WHO? WHAT? WHEN? RESOURCE? WHAT CHANGED? SOURCE/IP where appropriate

Examples:

-   Role changes.
-   Organization changes.
-   Verification.
-   Moderation.
-   Opportunity publishing.
-   Application status changes.
-   Admin actions.
-   Security events.
-   Configuration changes.

Do not log sensitive personal information unnecessarily.

------------------------------------------------------------------------

53. DASHBOARD MATRIX

Dashboard\| Owner\| Priority User / Seeker\| Developer 01\| P0
Organization\| Developer 02\| P0 Organization Member\| Developer 02\| P0
Verification Officer\| Developer 02\| P0 Moderator\| Developer 02\| P0
Admin\| Developer 02\| P0 Super Admin\| Developer 02\| P1

------------------------------------------------------------------------

54. USER DASHBOARD

┌─────────────────────────────────────────────────────┐ │ TDOP
Notifications Profile │
├─────────────────────────────────────────────────────┤ │ │ │ Welcome
back │ │ What should you do next? │ │ │ │ \[ Search
opportunities......................... \] │ │ │ │ Profile Completion │ │
███████████████░░░░ │ │ │ │ Recommended Opportunities │ │ │ │
┌─────────┐ ┌─────────┐ ┌─────────┐ │ │ │ Job │ │Scholar. │ │Training │
│ │ └─────────┘ └─────────┘ └─────────┘ │ │ │ │ My Applications │ │ │ │
Upcoming Deadlines │ │ │ │ Saved Opportunities │
└─────────────────────────────────────────────────────┘

------------------------------------------------------------------------

55. ORGANIZATION DASHBOARD

┌─────────────────────────────────────────────────────┐ │ ORGANIZATION │
├─────────────────────────────────────────────────────┤ │ Verification:
VERIFIED │ │ │ │ Opportunities Applications Shortlisted Team │ │ 12 184
27 6 │ │ │ │ \[ Create Opportunity \] │ │ │ │ Opportunity Performance │
│ │ │ Views → Saves → Applications → Shortlist → Outcome│ │ │ │ Recent
Applicants │ │ │ │ Team Activity │
└─────────────────────────────────────────────────────┘

------------------------------------------------------------------------

56. ADMIN DASHBOARD

┌─────────────────────────────────────────────────────┐ │ TDOP ADMIN │
├─────────────────────────────────────────────────────┤ │ Users \|
Organizations \| Opportunities \| Reports │ │ │ │ Pending Verification │
│ Pending Moderation │ │ Suspicious Activity │ │ │ │ Platform Activity │
│ │ │ Recent Audit Events │
└─────────────────────────────────────────────────────┘

------------------------------------------------------------------------

57. APPLICATION SYSTEM --- SHARED CONTRACT

Application flow:

USER │ ▼ Opportunity │ ▼ Eligibility │ ▼ Prepare │ ▼ Submit │ ▼
Organization Review │ ├── Rejected │ ├── Shortlisted │ │ │ ▼ │ Interview
│ │ │ ├── Rejected │ └── Accepted │ ▼ Outcome

Developer 01 owns the user experience.

Developer 02 owns the organization/candidate management experience.

The underlying application model and status system are shared and must
be coordinated.

------------------------------------------------------------------------

58. NOTIFICATION ARCHITECTURE

EVENT ↓ Notification Service ↓ Preference Check ↓ Create Notification ↓
Delivery ├── In-App ├── Email ├── SMS └── Push

Not every channel must exist in the first release.

The architecture should allow future channels.

------------------------------------------------------------------------

59. TRUST ARCHITECTURE

SOURCE ↓ ORGANIZATION ↓ VERIFICATION ↓ OPPORTUNITY ↓ MODERATION ↓
PUBLISH ↓ REPORTS / SIGNALS ↓ RISK REVIEW ↓ ACTION

Trust is a continuous system, not a one-time badge.

------------------------------------------------------------------------

60. DATABASE REQUIREMENTS

Shared responsibility.

Database should support:

-   Users.
-   Roles.
-   Permissions.
-   Organizations.
-   Organization memberships.
-   Opportunities.
-   Opportunity status history.
-   Applications.
-   Application status history.
-   Education.
-   Skills.
-   Experience.
-   Career goals.
-   Interests.
-   Documents.
-   Notifications.
-   Reports.
-   Verification.
-   Moderation.
-   Audit logs.
-   Analytics/events where appropriate.

------------------------------------------------------------------------

61. DATABASE QUALITY

Required:

-   Foreign keys.
-   Unique constraints.
-   Useful indexes.
-   Referential integrity.
-   Proper timestamps.
-   Soft delete where appropriate.
-   Migration discipline.
-   Transaction handling.
-   Consistent status transitions.

Avoid duplicate business entities.

Example:

Do not create:

Opportunity JobOpportunity OpportunityPost JobPost

for the same concept unless there is a clear architectural reason.

------------------------------------------------------------------------

62. SECURITY

Shared responsibility.

Required:

-   Rate limiting.
-   Secure CORS.
-   Security headers.
-   JWT security.
-   Refresh-token security.
-   Input validation.
-   Authorization.
-   IDOR protection.
-   Secure file uploads.
-   Secret management.
-   Secure logging.
-   Password security.
-   Session security.
-   API abuse protection.
-   Database safety.
-   XSS protection.
-   CSRF strategy where applicable.

------------------------------------------------------------------------

63. PRIVATE DATA RULE

Sensitive information must never leak through:

-   Public API responses.
-   Logs.
-   Analytics.
-   Frontend state.
-   URLs.
-   Error messages.
-   Public search.
-   Organization access without authorization.

Particularly protect:

-   Passwords.
-   Tokens.
-   Private documents.
-   Personal contact details.
-   Sensitive application information.
-   Internal organization notes.

------------------------------------------------------------------------

64. PERFORMANCE

Target a fast experience on ordinary mobile networks.

Required:

-   Pagination.
-   Efficient database queries.
-   Database indexes.
-   Lazy loading.
-   Image optimization.
-   Code splitting where appropriate.
-   API efficiency.
-   Caching where useful.
-   Avoid unnecessary requests.
-   Avoid rendering thousands of records.

------------------------------------------------------------------------

65. CACHING

Use caching selectively.

Good candidates may include:

-   Public opportunity categories.
-   Public configuration.
-   Frequently accessed public data.
-   Search results where appropriate.

Do not cache sensitive user-specific information incorrectly.

Always consider cache invalidation.

------------------------------------------------------------------------

66. OBSERVABILITY

Developer 02 primary.

Required:

-   Structured logging.
-   Error tracking.
-   Health checks.
-   Performance monitoring.
-   Security monitoring.
-   Database monitoring.
-   Background job monitoring.
-   Backup monitoring.

------------------------------------------------------------------------

67. TESTING

Both developers own tests for their areas.

Unit

Business logic.

Integration

API + database + authorization.

Security

Test:

-   Unauthorized requests.
-   Role bypass.
-   IDOR.
-   Invalid tokens.
-   Expired sessions.
-   Organization isolation.
-   Document access.

E2E

User

Register ↓ Login ↓ Complete Profile ↓ Search ↓ View Opportunity ↓ Save ↓
Apply ↓ Track

Organization

Register ↓ Verification ↓ Create Opportunity ↓ Submit ↓ Approval ↓
Publish ↓ View Applicants ↓ Shortlist ↓ Update Status

Admin

Login ↓ Verification Queue ↓ Review ↓ Moderation ↓ Report Investigation
↓ Audit

------------------------------------------------------------------------

68. UI STATE REQUIREMENTS

Every major page must handle:

LOADING SUCCESS EMPTY ERROR RETRY UNAUTHORIZED FORBIDDEN NOT FOUND
EXPIRED

Never leave an empty white page.

------------------------------------------------------------------------

69. NO MOCK PRODUCTION DATA

This is mandatory.

Do not use fake:

Users Organizations Opportunities Applications Analytics Testimonials
Partners Investors Government endorsements Statistics

during production implementation.

Development seed data may exist only when explicitly identified as
development/test data.

------------------------------------------------------------------------

70. REAL DATA RULE

Dashboard:

Backend ↓ Database ↓ API ↓ Frontend ↓ Real metric

Never:

Frontend ↓ Hardcoded "2,548 Users"

------------------------------------------------------------------------

71. LANDING PAGE TRUST RULE

Do not invent:

-   Government partnership.
-   University partnership.
-   Investor.
-   Funding.
-   Number of users.
-   Number of organizations.
-   Success stories.
-   Testimonials.
-   Verification counts.

If the repository contains no real data, show an appropriate empty or
neutral state.

------------------------------------------------------------------------

72. DESIGN SYSTEM

Primary:

Deep Navy #0B1F3A Opportunity Blue #1565D8 Growth Green #16A34A Energy
Yellow #F5B700 Warning Amber #F59E0B Danger Red #DC2626 Intelligence
Purple #7C3AED Background #F7F9FC White #FFFFFF Primary Text #0B1F3A
Secondary Text #64748B Border #E2E8F0

Use colors semantically.

Do not use all colors simultaneously.

Avoid:

-   Excessive gradients.
-   Excessive glassmorphism.
-   Excessive rounded cards.
-   Random colorful dashboards.
-   Excessive animations.
-   Generic AI aesthetics.
-   Clutter.

------------------------------------------------------------------------

73. TANZANIA-FIRST EXPERIENCE

TDOP should feel:

-   Tanzanian.
-   African.
-   Modern.
-   Professional.
-   Trustworthy.
-   Inclusive.
-   Technology-driven.
-   Accessible.

The product should not look like a generic copied Western SaaS
dashboard.

However, avoid stereotypes.

------------------------------------------------------------------------

74. INTERNATIONALIZATION

Primary languages:

Kiswahili English

Architecture should allow future languages.

Do not duplicate entire pages for each language.

Use a proper translation architecture.

------------------------------------------------------------------------

75. SEO

Public opportunity pages should eventually be indexable where
appropriate.

SEO requirements:

-   Metadata.
-   Canonical.
-   Semantic HTML.
-   Open Graph.
-   Structured data.
-   Clean URLs.
-   Correct headings.
-   Public opportunity content.

------------------------------------------------------------------------

76. ANALYTICS & NORTH STAR

North Star:

«Successful Opportunity Outcomes»

The system should eventually understand:

DISCOVERY ↓ VIEW ↓ SAVE ↓ APPLY ↓ SHORTLIST ↓ INTERVIEW ↓ ACCEPTED ↓
SUCCESSFUL OUTCOME

Not every opportunity type will use exactly the same outcome.

The data model should remain extensible.

------------------------------------------------------------------------

77. DATA FRESHNESS

Opportunity freshness is a trust feature.

Track:

Created Updated Verified Last Checked Deadline Expired

Future:

-   Broken-link detection.
-   Stale opportunity detection.
-   Automatic expiry.
-   Source monitoring.
-   Duplicate detection.

------------------------------------------------------------------------

78. AI STRATEGY

AI should enhance TDOP, not become a single point of failure.

First:

Structured Data ↓ Rule-Based Intelligence ↓ Explainable Recommendations

Later:

Structured Data ↓ Rules ↓ ML / AI ↓ Explainable Intelligence

AI must never invent:

-   Deadlines.
-   Eligibility.
-   Requirements.
-   Organizations.
-   Funding.
-   Application URLs.
-   Opportunity facts.

If AI is unavailable, TDOP must still work.

------------------------------------------------------------------------

79. FUTURE AI FEATURES

Priority P3:

-   Semantic search.
-   AI recommendations.
-   Profile improvement.
-   Skill-gap analysis.
-   Opportunity matching.
-   Application preparation.
-   Career guidance.
-   Fraud detection.
-   Outcome prediction.

These should not block the core platform.

------------------------------------------------------------------------

80. HELP & SUPPORT

User-facing:

-   FAQ.
-   Help center.
-   Contact support.
-   Report opportunity.
-   Account help.
-   Application guidance.
-   Safety guidance.

Platform side:

-   Support queue.
-   Reports.
-   Investigation.
-   Resolution.
-   Audit.

------------------------------------------------------------------------

81. PRIVACY CONTROLS

Users should eventually manage:

-   Profile visibility.
-   Notification preferences.
-   Document visibility.
-   Account settings.
-   Communication preferences.
-   Data access.
-   Account deletion/request.

------------------------------------------------------------------------

82. OPPORTUNITY LIFECYCLE DIAGRAM

                 ┌─────────────┐
                 │    DRAFT    │
                 └──────┬──────┘
                        ↓
                 ┌─────────────┐
                 │ VALIDATION  │
                 └──────┬──────┘
                        ↓
                 ┌─────────────┐
                 │  SUBMITTED  │
                 └──────┬──────┘
                        ↓
             ┌─────────────────────┐
             │ VERIFICATION /      │
             │ MODERATION          │
             └──────────┬──────────┘
                        ↓
                  ┌───────────┐
                  │ APPROVED  │
                  └─────┬─────┘
                        ↓
                  ┌───────────┐
                  │ PUBLISHED │
                  └─────┬─────┘
                        ↓
                ┌──────────────┐
                │ CLOSING SOON │
                └──────┬───────┘
                       ↓
                 ┌──────────┐
                 │ EXPIRED  │
                 └────┬─────┘
                      ↓
                 ┌──────────┐
                 │ ARCHIVED │
                 └──────────┘

------------------------------------------------------------------------

83. ORGANIZATION VERIFICATION DIAGRAM

Organization │ ▼ Create Profile │ ▼ Submit Verification │ ▼ Upload
Evidence │ ▼ Verification Queue │ ▼ Review │ ┌───┼───────────────┐ │ │ │
▼ ▼ ▼ APPROVE REJECT MORE INFO │ │ ▼ └──────► Review Again VERIFIED

------------------------------------------------------------------------

84. REPORT / MODERATION DIAGRAM

User Report │ ▼ Report Queue │ ▼ Investigation │ ├───────────────┐ ▼ ▼
No Action Action │ ┌──────────┼──────────┐ ▼ ▼ ▼ Warning Suspend Archive
│ │ │ └──────────┴──────────┘ │ ▼ Audit

------------------------------------------------------------------------

85. TWO-DEVELOPER DELIVERY MAP

Domain\| Developer 01\| Developer 02 Auth UX\| Primary\| Security
support RBAC\| Support\| Primary User Profile\| Primary\| - Documents\|
Primary\| Security Search\| Primary\| Backend support Discovery\|
Primary\| Backend support Saved\| Primary\| API support Applications\|
User side\| Organization side Recommendations\| Primary\| Data support
Notifications\| User side\| Platform events Landing\| Primary\| API
support Organizations\| -\| Primary Organization Team\| -\| Primary
Opportunities\| Discovery\| Primary management Verification\| -\|
Primary Moderation\| -\| Primary Reports\| User report UI\|
Investigation Anti-Fraud\| -\| Primary Analytics\| User-facing\|
Platform Audit\| -\| Primary Admin\| -\| Primary Super Admin\| -\|
Primary Mobile\| Primary\| Admin support Accessibility\| Primary\| Admin
support SEO\| Primary\| Backend support Security\| Shared\| Primary
platform Database\| Shared\| Shared Testing\| User flows\| Platform
flows

------------------------------------------------------------------------

86. IMPLEMENTATION PHASES

PHASE 0 --- REPOSITORY AUDIT

Both developers.

No major coding before this.

Audit

-   Repository structure.
-   Frontend.
-   Backend.
-   Database.
-   Migrations.
-   Authentication.
-   Authorization.
-   Routes.
-   APIs.
-   Dashboards.
-   Components.
-   Tests.
-   Environment.
-   Existing documentation.
-   Git status.
-   Existing branches.

Deliverable

Each developer produces:

TDOP IMPLEMENTATION AUDIT

Developer: Branch:

Existing: Completed: Partial: Missing: Broken:

Relevant files: Existing APIs: Existing models: Existing UI:

Dependencies: Shared changes: Risks:

Recommended implementation order: Tests required:

------------------------------------------------------------------------

87. PHASE 1 --- FOUNDATION

Priority P0.

Database Auth RBAC Users Organizations Security Core API contracts

------------------------------------------------------------------------

88. PHASE 2 --- OPPORTUNITY CORE

Opportunity model Creation Validation Verification Moderation Publishing
Expiry Search Discovery

------------------------------------------------------------------------

89. PHASE 3 --- APPLICATION CORE

Apply Status Timeline Documents Candidate management Notifications

------------------------------------------------------------------------

90. PHASE 4 --- DASHBOARDS

User Organization Organization Member Verification Officer Moderator
Admin Super Admin

------------------------------------------------------------------------

91. PHASE 5 --- TRUST & INTELLIGENCE

Trust signals Reports Anti-fraud Recommendations Deadline intelligence
Analytics Outcome tracking

------------------------------------------------------------------------

92. PHASE 6 --- EXPERIENCE QUALITY

Landing Mobile Accessibility EN/SW SEO Performance Error handling Empty
states

------------------------------------------------------------------------

93. PHASE 7 --- PRODUCTION HARDENING

Security Testing Monitoring Logging Backups Migrations Observability
Deployment Rollback

------------------------------------------------------------------------

94. DEFINITION OF DONE

A feature is NOT complete simply because a page exists.

A feature is considered Done when applicable:

UI ↓ API ↓ Business Logic ↓ Database ↓ Validation ↓ Authorization ↓
Error Handling ↓ Loading State ↓ Empty State ↓ Audit ↓ Tests ↓ Security
Review ↓ Documentation

------------------------------------------------------------------------

95. FEATURE STATUS MATRIX

Use this table during development.

Feature\| Owner\| Backend\| API\| DB\| Frontend\| Auth\| Tests\| Status
Authentication\| Dev 01\| ☐\| ☐\| ☐\| ☐\| ☐\| ☐\| AUDIT RBAC\| Dev 02\|
☐\| ☐\| ☐\| ☐\| ☐\| ☐\| AUDIT User Profile\| Dev 01\| ☐\| ☐\| ☐\| ☐\|
☐\| ☐\| AUDIT Documents\| Dev 01\| ☐\| ☐\| ☐\| ☐\| ☐\| ☐\| AUDIT
Organizations\| Dev 02\| ☐\| ☐\| ☐\| ☐\| ☐\| ☐\| AUDIT Opportunities\|
Dev 02\| ☐\| ☐\| ☐\| ☐\| ☐\| ☐\| AUDIT Search\| Dev 01\| ☐\| ☐\| ☐\| ☐\|
☐\| ☐\| AUDIT Applications\| Both\| ☐\| ☐\| ☐\| ☐\| ☐\| ☐\| AUDIT
Verification\| Dev 02\| ☐\| ☐\| ☐\| ☐\| ☐\| ☐\| AUDIT Moderation\| Dev
02\| ☐\| ☐\| ☐\| ☐\| ☐\| ☐\| AUDIT Notifications\| Both\| ☐\| ☐\| ☐\|
☐\| ☐\| ☐\| AUDIT Recommendations\| Dev 01\| ☐\| ☐\| ☐\| ☐\| ☐\| ☐\|
AUDIT Analytics\| Dev 02\| ☐\| ☐\| ☐\| ☐\| ☐\| ☐\| AUDIT Audit Logs\|
Dev 02\| ☐\| ☐\| ☐\| ☐\| ☐\| ☐\| AUDIT Landing\| Dev 01\| ☐\| ☐\| ☐\|
☐\| ☐\| ☐\| AUDIT Security\| Both\| ☐\| ☐\| ☐\| ☐\| ☐\| ☐\| AUDIT
Testing\| Both\| ☐\| ☐\| ☐\| ☐\| ☐\| ☐\| AUDIT Production\| Both\| ☐\|
☐\| ☐\| ☐\| ☐\| ☐\| AUDIT

------------------------------------------------------------------------

96. OPENCODE INSTRUCTIONS

When this document is attached to OpenCode, OpenCode MUST understand:

«You are working inside an existing TDOP repository.»

Do not assume the repository is empty.

Do not rebuild the application from scratch.

------------------------------------------------------------------------

97. OPENCODE --- FIRST ACTION

Before coding:

1.  Inspect repository.
2.  Inspect Git status.
3.  Identify current branch.
4.  Inspect frontend.
5.  Inspect backend.
6.  Inspect database.
7.  Inspect migrations.
8.  Inspect authentication.
9.  Inspect authorization.
10. Inspect API routes.
11. Inspect dashboards.
12. Inspect shared components.
13. Inspect tests.
14. Inspect environment configuration.
15. Search for existing implementations.
16. Search for duplicate implementations.
17. Compare repository against this PRD.

Then produce an audit.

------------------------------------------------------------------------

98. OPENCODE --- DEVELOPER 01 MODE

If working on:

features/developer-01

OpenCode must prioritize:

USER PROFILE DOCUMENTS DISCOVERY SEARCH SAVED APPLICATIONS --- USER SIDE
RECOMMENDATIONS NOTIFICATIONS --- USER SIDE USER DASHBOARD LANDING
MOBILE USER EXPERIENCE ACCESSIBILITY SEO

Do not start implementing Developer 02 functionality unless explicitly
instructed.

------------------------------------------------------------------------

99. OPENCODE --- DEVELOPER 02 MODE

If working on:

features/developer-02

OpenCode must prioritize:

RBAC ORGANIZATIONS TEAM PERMISSIONS OPPORTUNITY MANAGEMENT VERIFICATION
MODERATION CANDIDATES REPORTS ANTI-FRAUD TRUST ADMIN SUPER ADMIN
ANALYTICS AUDIT PLATFORM NOTIFICATIONS OBSERVABILITY PRODUCTION
OPERATIONS

Do not start implementing Developer 01 functionality unless explicitly
instructed.

------------------------------------------------------------------------

100. OPENCODE --- EXISTING CODE RULE

If functionality already exists:

DO NOT REBUILD IT.

Instead:

Inspect ↓ Compare ↓ Identify Gap ↓ Improve ↓ Test

------------------------------------------------------------------------

101. OPENCODE --- NO DUPLICATES

Before creating:

-   API.
-   Route.
-   Model.
-   Service.
-   Controller.
-   Component.
-   Dashboard.
-   Hook.
-   Utility.

Search the repository first.

If an equivalent exists, reuse or improve it.

------------------------------------------------------------------------

102. OPENCODE --- NO FAKE IMPLEMENTATION

Do not create UI that pretends functionality exists when backend support
is missing.

Bad:

Application Status: 87

when there are no real records.

Bad:

Verified Organizations: 2,430

when the database has no such data.

Bad:

AI Match: 97%

when there is no recommendation engine.

Instead:

No data available yet.

or implement the real backend capability.

------------------------------------------------------------------------

103. OPENCODE --- API RULE

Before creating an API:

Search existing routes Search controllers Search services Search models
Search frontend API clients

Then determine whether to:

reuse extend fix or create

Only create a new API if necessary.

------------------------------------------------------------------------

104. OPENCODE --- DATABASE RULE

Before changing database:

Inspect current schema Inspect migrations Inspect relationships Inspect
indexes Inspect constraints Inspect existing data

Never casually delete or recreate the database.

Never rewrite migrations already applied in shared environments without
a safe migration strategy.

------------------------------------------------------------------------

105. OPENCODE --- SECURITY RULE

Never bypass:

Authentication Authorization Ownership Organization isolation Document
access

Frontend restrictions are not security.

Backend must enforce permissions.

------------------------------------------------------------------------

106. OPENCODE --- UI RULE

Use the existing design system where available.

Do not introduce:

-   Random colors.
-   Random fonts.
-   Unnecessary libraries.
-   Duplicate components.
-   Excessive animations.
-   Generic AI dashboard layouts.

Reuse existing:

-   Buttons.
-   Inputs.
-   Cards.
-   Tables.
-   Modals.
-   Navigation.
-   Icons.
-   Typography.
-   Spacing.
-   Theme.

------------------------------------------------------------------------

107. OPENCODE --- ERROR HANDLING

Every implementation must consider:

Loading Success Empty Error Retry Unauthorized Forbidden Not Found
Expired Validation failure Network failure

------------------------------------------------------------------------

108. OPENCODE --- FINAL REPORT

After completing a task, report:

TASK Owner: Branch:

AUDIT What already existed:

CHANGES Files created: Files modified: Files deleted:

BACKEND APIs: Business logic:

DATABASE Models: Migrations: Indexes/constraints:

FRONTEND Routes: Components: Pages:

AUTHORIZATION Permissions: Ownership checks:

TESTS Unit: Integration: E2E:

SECURITY Checks performed:

KNOWN LIMITATIONS ...

NEXT STEPS ...

Never say:

Completed successfully

unless the feature was actually implemented and tested.

------------------------------------------------------------------------

109. PRODUCTION CHECKLIST

Before TDOP is declared production-ready:

Core

-   [ ] Authentication.
-   [ ] RBAC.
-   [ ] User profile.
-   [ ] Organizations.
-   [ ] Verification.
-   [ ] Opportunity lifecycle.
-   [ ] Search.
-   [ ] Applications.
-   [ ] Notifications.
-   [ ] Dashboards.
-   [ ] Moderation.
-   [ ] Reports.
-   [ ] Audit.

Security

-   [ ] Authorization.
-   [ ] IDOR protection.
-   [ ] Rate limiting.
-   [ ] Secure tokens.
-   [ ] File security.
-   [ ] Secrets.
-   [ ] CORS.
-   [ ] Headers.
-   [ ] Input validation.

Quality

-   [ ] Unit tests.
-   [ ] Integration tests.
-   [ ] E2E tests.
-   [ ] Error handling.
-   [ ] Empty states.
-   [ ] Loading states.
-   [ ] Accessibility.

Experience

-   [ ] Mobile.
-   [ ] EN/SW.
-   [ ] SEO.
-   [ ] Performance.
-   [ ] Low-bandwidth consideration.

Operations

-   [ ] Logging.
-   [ ] Monitoring.
-   [ ] Error tracking.
-   [ ] Backups.
-   [ ] Migrations.
-   [ ] Deployment.
-   [ ] Rollback.

Data

-   [ ] No fake production metrics.
-   [ ] No duplicate applications.
-   [ ] No unauthorized data.
-   [ ] Proper indexes.
-   [ ] Proper constraints.
-   [ ] Proper lifecycle states.
-   [ ] Fresh opportunity data.

------------------------------------------------------------------------

110. FUTURE ROADMAP

These features should not block the core platform.

P3

Advanced AI Semantic Search AI Profile Assistant Skill Gap Intelligence
Advanced Fraud Detection PWA Native Mobile Apps Enterprise API Advanced
Integrations Opportunity Collections Advanced Monetization East Africa
Expansion

------------------------------------------------------------------------

111. LONG-TERM TDOP VISION

TDOP should eventually evolve from:

Opportunity Directory

into:

Opportunity Intelligence Platform

and eventually:

Opportunity Progress Ecosystem

The evolution:

DISCOVERY ↓ TRUST ↓ PERSONALIZATION ↓ ACTION ↓ TRACKING ↓ OUTCOMES ↓
INTELLIGENCE ↓ PROGRESS

------------------------------------------------------------------------

112. MASTER PRODUCT FLOW

                         TDOP
                          │
                          ▼
                 ┌─────────────────┐
                 │ TRUSTED DISCOVERY│
                 └────────┬────────┘
                          │
             ┌────────────┴────────────┐
             ▼                         ▼

     INDIVIDUALS ORGANIZATIONS │ │ ▼ ▼ PROFILE VERIFICATION │ │ ▼ ▼
     DISCOVERY OPPORTUNITY │ CREATION ▼ │ RECOMMENDATION ▼ │ MODERATION
     ▼ │ APPLY ▼ │ PUBLISH └─────────────┬───────────┘ ▼ TRACK │ ▼
     OUTCOME │ ▼ PROGRESS │ ▼ SUCCESSFUL OPPORTUNITY OUTCOME

------------------------------------------------------------------------

113. FINAL PRODUCT PRINCIPLES

TRUST

Users should understand why an opportunity is trustworthy.

CLARITY

Eligibility, deadlines and application steps should be clear.

ACTION

TDOP should help people act, not just browse.

PROGRESS

Applications and outcomes should be trackable.

PRIVACY

Personal information and documents must be protected.

ACCESSIBILITY

The platform should work for diverse users.

PERFORMANCE

The platform should work well on realistic mobile networks.

REAL DATA

Production interfaces must represent real data.

EXPLAINABILITY

Recommendations and trust signals should be understandable.

SCALABILITY

Architecture should support growth without unnecessary complexity.

------------------------------------------------------------------------

114. THE TDOP QUALITY STANDARD

TDOP must feel:

Professional Modern Trustworthy Fast Clear Accessible Tanzania-first
Production-ready Scalable

It must NOT feel like:

Student demo Generic job board Template SaaS Fake-data dashboard
AI-generated prototype Collection of disconnected pages

------------------------------------------------------------------------

115. FINAL RULE

The most important rule in this document is:

«Understand the existing system before changing it.»

The second:

«Do not duplicate what already works.»

The third:

«Never fabricate functionality or data.»

The fourth:

«Backend authorization is authoritative.»

The fifth:

«A feature is not complete until it works across the necessary UI, API,
database, security and testing layers.»

------------------------------------------------------------------------

116. STARTING POINT

The first implementation task is NOT:

Build everything.

The first task is:

AUDIT THE REPOSITORY

Then:

CLASSIFY DONE PARTIAL MISSING BROKEN

Then:

PRIORITIZE

Then:

IMPLEMENT

Then:

TEST

Then:

VERIFY

Then:

MERGE

------------------------------------------------------------------------

117. TDOP MASTER EQUATION

                 PRODUCT VISION
                       +
                 THIS PRD
                       +
               REPOSITORY AUDIT
                       +
                  REAL DATA
                       +
              SECURE ARCHITECTURE
                       +
                    TESTING
                       +
                 HUMAN REVIEW
                       =
              PRODUCTION TDOP

------------------------------------------------------------------------

118. FINAL TEAM STRUCTURE

                           TDOP
                            │
                            ▼
                          main
                            │
             ┌──────────────┴──────────────┐
             │                             │
             ▼                             ▼
         Developer 01                     Developer 02
             │                             │
             │                             │

     USER PLATFORM PLATFORM OPERATIONS DISCOVERY ORGANIZATIONS PROFILE
     OPPORTUNITIES APPLICATIONS VERIFICATION RECOMMENDATIONS MODERATION
     LANDING TRUST MOBILE ADMIN ACCESSIBILITY ANALYTICS SEO AUDIT
     SECURITY OBSERVABILITY │ │ └──────────────┬──────────────┘ ▼ CODE
     REVIEW / PR │ ▼ main

------------------------------------------------------------------------

119. TDOP FINAL VISION

                    FIND A REAL OPPORTUNITY
                              │
                              ▼
                      UNDERSTAND IT
                              │
                              ▼
                       CHECK ELIGIBILITY
                              │
                              ▼
                          PREPARE
                              │
                              ▼
                            APPLY
                              │
                              ▼
                           TRACK
                              │
                              ▼
                           OUTCOME
                              │
                              ▼
                          PROGRESS
                              │
                              ▼
                  FIND THE NEXT OPPORTUNITY

TDOP = Discovery + Trust + Intelligence + Action + Progress + Ecosystem

------------------------------------------------------------------------

120. DEVELOPER QUICK REFERENCE --- SHORT VERSION

Hii ni short reference ya kazi za kila developer. Developer anatakiwa
kusoma sehemu hii pamoja na sections za kina za "README_PRD.md".

------------------------------------------------------------------------

👨‍💻 DEVELOPER 01 --- USER / SEEKER EXPERIENCE

Branch:

features/developer-01

Main responsibility

Developer 01 anasimamia upande wa User/Seeker, discovery, application
experience na public/user-facing experience.

Kazi kuu

-   [ ] Audit existing user/auth implementation
-   [ ] Login
-   [ ] Register
-   [ ] Forgot password
-   [ ] Reset password
-   [ ] Refresh token UX/integration
-   [ ] Logout/session handling
-   [ ] Current user "/me"
-   [ ] User profile
-   [ ] Education
-   [ ] Skills
-   [ ] Experience
-   [ ] Interests
-   [ ] Career goals
-   [ ] Profile completion
-   [ ] Profile visibility
-   [ ] CV/document management
-   [ ] Certificates
-   [ ] Academic documents
-   [ ] Portfolio documents
-   [ ] Secure document access
-   [ ] Opportunity discovery
-   [ ] Opportunity listing
-   [ ] Opportunity details
-   [ ] Search
-   [ ] Filters
-   [ ] Sorting
-   [ ] Pagination
-   [ ] Saved opportunities
-   [ ] Opportunity comparison
-   [ ] Application submission
-   [ ] Application tracking
-   [ ] Application status
-   [ ] Application timeline
-   [ ] Application withdrawal
-   [ ] Duplicate application prevention UX
-   [ ] Recommended opportunities
-   [ ] Explainable recommendations
-   [ ] Deadline reminders UI
-   [ ] User notifications
-   [ ] User notification preferences
-   [ ] User dashboard
-   [ ] Public landing page
-   [ ] Public opportunity discovery
-   [ ] Mobile responsiveness
-   [ ] Accessibility
-   [ ] English/Kiswahili user experience
-   [ ] SEO for public pages
-   [ ] User-side loading/error/empty/success states

Developer 01 should NOT own

-   Organization RBAC implementation
-   Organization team permissions
-   Verification Officer workflows
-   Moderator workflows
-   Admin/Super Admin management
-   Organization candidate management
-   Organization opportunity approval
-   Platform-wide moderation
-   Platform audit infrastructure
-   Platform security policies

------------------------------------------------------------------------

👨‍💻 DEVELOPER 02 --- ORGANIZATION / ADMIN / TRUST

Branch:

features/developer-02

Main responsibility

Developer 02 anasimamia Organizations, RBAC, opportunity management,
verification, moderation, administration, trust na platform operations.

Kazi kuu

-   [ ] Audit existing authorization
-   [ ] RBAC
-   [ ] Role permissions
-   [ ] Organization roles
-   [ ] Organization Admin
-   [ ] Organization Member
-   [ ] Organization profile
-   [ ] Organization team
-   [ ] Team invitations
-   [ ] Team permissions
-   [ ] Organization ownership
-   [ ] Organization verification
-   [ ] Verification documents
-   [ ] Verification approval
-   [ ] Verification rejection
-   [ ] Request-more-information workflow
-   [ ] Verification history
-   [ ] Opportunity creation
-   [ ] Opportunity editing
-   [ ] Draft opportunities
-   [ ] Opportunity submission
-   [ ] Opportunity validation
-   [ ] Opportunity verification
-   [ ] Opportunity approval
-   [ ] Opportunity publishing
-   [ ] Opportunity suspension
-   [ ] Opportunity archival
-   [ ] Opportunity restoration
-   [ ] Opportunity expiry
-   [ ] Official source/provenance
-   [ ] Eligibility rules
-   [ ] Organization applicant management
-   [ ] Applicant search/filter
-   [ ] Shortlisting
-   [ ] Applicant notes
-   [ ] Applicant communication
-   [ ] Candidate funnel
-   [ ] Organization analytics
-   [ ] Verification Officer dashboard
-   [ ] Moderator dashboard
-   [ ] Admin dashboard
-   [ ] Super Admin dashboard
-   [ ] Reports
-   [ ] Report investigation
-   [ ] Anti-fraud
-   [ ] Duplicate detection
-   [ ] Suspicious opportunity detection
-   [ ] Suspicious organization detection
-   [ ] Trust/risk scoring
-   [ ] Platform analytics
-   [ ] Audit logs
-   [ ] Platform notification infrastructure
-   [ ] Deadline automation
-   [ ] Platform observability
-   [ ] Production operations
-   [ ] Security hardening

Developer 02 should NOT own

-   User profile UI implementation
-   User dashboard UI
-   User application UX
-   User discovery UX
-   User recommendation UI
-   Public landing page design
-   User-side document UX

------------------------------------------------------------------------

🤝 SHARED RESPONSIBILITIES

Baadhi ya vitu haviwezi kugawanywa completely kwa developer mmoja.

Developers wote wanapaswa kushirikiana kwenye:

-   [ ] Database schema
-   [ ] Database migrations
-   [ ] Core API contracts
-   [ ] Authentication architecture
-   [ ] RBAC contracts
-   [ ] User model
-   [ ] Organization model
-   [ ] Opportunity model
-   [ ] Application model
-   [ ] Notification model
-   [ ] Audit model
-   [ ] File/document architecture
-   [ ] Global routing
-   [ ] Shared UI components
-   [ ] Design system
-   [ ] Environment configuration
-   [ ] Security architecture
-   [ ] Error handling standards
-   [ ] Testing strategy
-   [ ] Production deployment
-   [ ] Performance
-   [ ] Caching
-   [ ] Monitoring
-   [ ] Backup/recovery

Shared rule

Developer mmoja akibadilisha shared architecture:

CHECK → DISCUSS → IMPLEMENT → TEST → DOCUMENT

Usibadilishe shared contract bila kuangalia impact kwa developer
mwingine.

------------------------------------------------------------------------

🧭 SIMPLE OWNERSHIP MAP

                    TDOP PLATFORM
                         │
        ┌────────────────┴────────────────┐
        │                                 │
        ▼                                 ▼

DEVELOPER 01 DEVELOPER 02 User / Seeker Organization / Admin │ │ ├──
Auth UX ├── RBAC ├── Profile ├── Organizations ├── Education ├── Teams
├── Skills ├── Permissions ├── Experience ├── Verification ├── Documents
├── Opportunities ├── Discovery ├── Applicants ├── Search ├── Moderation
├── Saved ├── Reports ├── Applications ├── Anti-Fraud ├──
Recommendations ├── Analytics ├── Notifications ├── Audit ├── User
Dashboard ├── Admin ├── Landing Page ├── Super Admin ├── Mobile UX ├──
Deadline Engine ├── Accessibility ├── Observability ├── i18n └──
Production └── SEO

------------------------------------------------------------------------

🔄 CORE TDOP FLOW

USER │ ▼ DISCOVER │ ▼ UNDERSTAND │ ▼ PREPARE │ ▼ APPLY │ ▼ TRACK │ ▼
SUCCESS

Backend/platform side:

ORGANIZATION │ ▼ CREATE OPPORTUNITY │ ▼ VALIDATE │ ▼ VERIFY / MODERATE │
▼ PUBLISH │ ▼ USER DISCOVERS │ ▼ APPLICATION │ ▼ REVIEW │ ▼ SHORTLIST /
DECISION │ ▼ OUTCOME

------------------------------------------------------------------------

🗂️ QUICK FEATURE OWNERSHIP TABLE

Feature\| Dev 01\| Dev 02\| Shared Authentication UX\| ✅\| \| User
Profile\| ✅\| \| Education/Skills\| ✅\| \| Experience\| ✅\| \|
Documents UX\| ✅\| \| Search\| ✅\| \| Discovery\| ✅\| \| Saved
Opportunities\| ✅\| \| Applications --- User Side\| ✅\| \|
Recommendations\| ✅\| \| User Notifications\| ✅\| \| User Dashboard\|
✅\| \| Landing Page\| ✅\| \| Mobile User UX\| ✅\| \| Accessibility\|
✅\| \| i18n User Side\| ✅\| \| SEO\| ✅\| \| RBAC\| \| ✅\| 🔗
Organizations\| \| ✅\| Organization Teams\| \| ✅\| Permissions\| \|
✅\| 🔗 Opportunity Management\| \| ✅\| 🔗 Opportunity Lifecycle\| \|
✅\| 🔗 Candidate Management\| \| ✅\| Organization Verification\| \|
✅\| Moderation\| \| ✅\| Admin\| \| ✅\| Super Admin\| \| ✅\|
Reports\| \| ✅\| Anti-Fraud\| \| ✅\| Platform Analytics\| \| ✅\|
Audit Logs\| \| ✅\| Deadline Engine\| \| ✅\| Platform Notifications\|
\| ✅\| 🔗 Database\| \| \| ✅ Core APIs\| \| \| ✅ Security
Architecture\| \| \| ✅ Testing Strategy\| \| \| ✅ Deployment\| \| \|
✅ Monitoring\| \| \| ✅

------------------------------------------------------------------------

🚦 PRIORITY ORDER

Kila developer afanye kazi kwa mpangilio huu isipokuwa kuna dependency
inayolazimisha tofauti.

P0 --- Foundation / Critical

Audit existing system ↓ Authentication ↓ Database integrity ↓ RBAC /
Authorization ↓ Core API contracts

P1 --- Core Product

User Profile Organization Opportunity Search Application Verification
Moderation Dashboards

P2 --- Intelligence & Trust

Recommendations Notifications Deadline Intelligence Reports Anti-Fraud
Analytics Audit

P3 --- Production Quality

Security Hardening Testing Performance Caching Accessibility i18n SEO
Observability Mobile

------------------------------------------------------------------------

🧪 EVERY FEATURE MUST PASS

Kabla developer kusema feature imekamilika:

\[ \] Existing implementation inspected \[ \] Requirement understood \[
\] API verified \[ \] Database verified \[ \] Authorization verified \[
\] Validation implemented \[ \] Error state handled \[ \] Loading state
handled \[ \] Empty state handled \[ \] Success state handled \[ \]
Mobile checked \[ \] Accessibility checked \[ \] Security checked \[ \]
Tests added/updated \[ \] Existing functionality still works \[ \] No
mock/fake production data \[ \] No duplicate implementation \[ \]
Documentation updated

------------------------------------------------------------------------

🚫 ABSOLUTE RULES

Developers MUST NOT:

-   [ ] Rewrite the whole project unnecessarily
-   [ ] Delete working features
-   [ ] Create duplicate APIs
-   [ ] Create duplicate models
-   [ ] Hardcode production statistics
-   [ ] Use fake opportunities
-   [ ] Use fake users/applications
-   [ ] Bypass backend authorization
-   [ ] Trust frontend permissions
-   [ ] Expose private documents publicly
-   [ ] Commit secrets
-   [ ] Modify another developer's work blindly
-   [ ] Force-push shared branches
-   [ ] Reset/rewrite another developer's commits
-   [ ] Change shared architecture without coordination
-   [ ] Claim a feature is complete without testing

------------------------------------------------------------------------

🤖 OPENCODE QUICK INSTRUCTION

When this repository is opened in OpenCode:

1.  Read README_PRD.md completely.
2.  Identify the active developer branch.
3.  Audit the existing repository.
4.  Compare implementation against README_PRD.md.
5.  Identify completed / partial / missing features.
6.  Work ONLY within the assigned developer scope.
7.  Reuse existing architecture where possible.
8.  Do not duplicate existing functionality.
9.  Implement incrementally.
10. Test every meaningful change.
11. Verify security and authorization.
12. Report exactly what was changed.
13. Report what remains.
14. Never claim completion without evidence.

Developer 01 mode

ACTIVE OWNER: Developer 01

BRANCH: features/developer-01

PRIMARY AREA: User / Seeker Experience

REFERENCE: README_PRD.md → Developer 01 sections

Developer 02 mode

ACTIVE OWNER: Developer 02

BRANCH: features/developer-02

PRIMARY AREA: Organization / Admin / Trust / Platform Operations

REFERENCE: README_PRD.md → Developer 02 sections

------------------------------------------------------------------------

🏁 FINAL TEAM RULE

TDOP is one product, not two separate projects.

Developer 01 + Developer 02 ↓ Shared Architecture ↓ One Backend ↓ One
Database ↓ One Design System ↓ One TDOP Platform

The goal is not:

«"Developer 01 amalize sehemu yake."»

or

«"Developer 02 amalize sehemu yake."»

The goal is:

«Build one secure, trusted, scalable and production-ready TDOP platform
without breaking each other's work.»

MASTER DEVELOPMENT PRINCIPLE

UNDERSTAND ↓ AUDIT ↓ PLAN ↓ IMPLEMENT ↓ TEST ↓ VERIFY ↓ HARDEN ↓
DOCUMENT

Never:

GUESS → REWRITE → BREAK → PATCH

TDOP standard:

«Real Data. Real Security. Real Workflows. Real Validation. Real
Testing. No Fake Completion.»
