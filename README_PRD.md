# TDOP — README_PRD.md

## Tanzania Digital Opportunity Platform

**Master Product Requirements Document, Implementation Backlog, Developer Ownership Guide, Architecture Reference and OpenCode Development Instructions**

| | |
|---|---|
| **Project** | TDOP — Tanzania Digital Opportunity Platform |
| **Document** | `README_PRD.md` |
| **Status** | Active Development |
| **Primary Branch** | `main` |
| **Developer 01 Branch** | `features/developer-01` |
| **Developer 02 Branch** | `features/developer-02` |

---

## 1. Purpose of This Document

This document is the master development reference for TDOP.

It exists to answer five questions:

1. What is TDOP?
2. What should TDOP become?
3. What functionality must exist?
4. Which developer owns each area?
5. How should OpenCode or another coding agent implement the work without breaking existing functionality?

This document must be treated as a **Product + Engineering Execution Guide**.

> It is **NOT** permission to blindly rebuild the application.

**The repository remains the technical source of truth.**

---

## 2. Core Development Principle

Every task must follow:

```
UNDERSTAND
   ↓
 AUDIT
   ↓
 PLAN
   ↓
 IMPLEMENT
   ↓
 TEST
   ↓
 VERIFY
   ↓
 HARDEN
   ↓
 DOCUMENT
```

Never:

```
GUESS
  ↓
REWRITE
  ↓
BREAK
  ↓
PATCH
```

---

## 3. Critical Rule — Audit Before Implementation

The feature list in this document represents the **target state and master backlog**.

It does **NOT** automatically mean that every feature is missing.

Before implementing any feature, inspect the repository and classify it:

- `DONE`
- `PARTIAL`
- `MISSING`
- `BROKEN`
- `NEEDS VERIFICATION`

**Example**

If this document says:

```
User Profile
 ├── Education
 ├── Skills
 ├── Experience
 └── Career Goals
```

and the repository already contains Education and Skills, do **NOT** rebuild them.

Instead:

| Item | Status |
|---|---|
| Existing Education | DONE |
| Existing Skills | DONE |
| Experience | MISSING |
| Career Goals | PARTIAL |

Then implement only the gaps.

---

## 4. TDOP Product Definition

### 4.1 Product Name

**TDOP** — Tanzania Digital Opportunity Platform

### 4.2 Product Positioning

> "A trusted opportunity discovery and progress platform for Tanzania."

TDOP is not simply a job board.

TDOP is intended to become a trusted digital ecosystem where individuals can discover, understand, prepare for, apply to, track and progress through legitimate opportunities.

---

## 5. TDOP Core Journey

```
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
```

TDOP should not stop at:

> "Here is an opportunity."

It should eventually help answer:

> "Is this right for me?"
> "What do I need?"
> "How do I prepare?"
> "How do I apply?"
> "What happened to my application?"
> "What should I pursue next?"

---

## 6. Opportunity Ecosystem

TDOP should support multiple opportunity categories.

**Employment**
- Jobs
- Internships
- Graduate opportunities
- Apprenticeships
- Part-time opportunities
- Remote jobs
- International jobs

**Education**
- Scholarships
- Fellowships
- Training
- Certifications
- Courses
- Exchange opportunities
- Academic programs

**Entrepreneurship & Finance**
- Grants
- Loans
- Financing
- Investment
- Entrepreneurship programs
- Business competitions
- Startup opportunities

**Public / Development**
- Government programs
- NGO opportunities
- Development programs
- Youth programs
- Volunteer opportunities

**Professional / Innovation**
- Research
- Innovation challenges
- Competitions
- Conferences
- Events
- ICT / Digital opportunities
- Tenders / procurement

> The system must allow new opportunity categories without requiring a major architectural rewrite.

---

## 7. TDOP Product Model

```
                        TDOP
                          │
        ┌─────────────────┼─────────────────┐
        │                 │                 │
        ▼                 ▼                 ▼
   INDIVIDUALS      ORGANIZATIONS       PLATFORM
        │                 │                 │
        ▼                 ▼                 ▼
     Discover           Publish           Govern
     Prepare            Manage            Verify
     Apply              Candidates        Moderate
     Track              Analytics         Protect
     Progress           Team              Analyze
```

---

## 8. Primary User Types

TDOP should support:

- `USER`
- `ORGANIZATION`
- `ORGANIZATION_ADMIN`
- `ORGANIZATION_MEMBER`
- `VERIFICATION_OFFICER`
- `MODERATOR`
- `ADMIN`
- `SUPER_ADMIN`

---

## 9. Two-Developer Ownership

### Developer 01

**Branch:** `features/developer-01`

**Primary responsibility:** *USER / SEEKER / DISCOVERY / PERSONAL EXPERIENCE*

Developer 01 owns the user-facing journey.

### Developer 02

**Branch:** `features/developer-02`

**Primary responsibility:** *ORGANIZATION / TRUST / MODERATION / ADMIN / PLATFORM OPERATIONS*

Developer 02 owns organization-facing and platform-governance functionality.

---

## 10. Git Architecture

```
                          main
                            │
              ┌─────────────┴─────────────┐
              │                           │
              ▼                           ▼
  features/developer-01       features/developer-02
              │                           │
              ▼                           ▼
     USER / DISCOVERY          ORG / TRUST / ADMIN
              │                           │
              └─────────────┬─────────────┘
                             ▼
                        Pull Request
                             │
                             ▼
                            main
```

---

## 11. Git Rules

`main` must remain stable.

Developers must:

- Work only on their assigned branch.
- Pull latest changes regularly.
- Make focused commits.
- Push to their own branch.
- Use Pull Requests.
- Review changes before merging.
- Resolve conflicts carefully.
- Test before merge.

**Never:**

- `git push --force`
- `git reset --hard` another developer's work
- delete another developer's branch
- rewrite shared history
- overwrite another developer's files
- blindly replace directories

---

## 12. Shared Areas

Some systems cannot safely be independently redesigned by both developers.

These require coordination:

- Database schema
- Database migrations
- Authentication architecture
- RBAC
- User model
- Organization model
- Opportunity model
- Application model
- Notification model
- Audit model
- Core API contracts
- Global routing
- Shared UI components
- Design system
- Environment configuration
- Security architecture

**Shared change rule**

If either developer needs to change a shared area:

1. Inspect current implementation.
2. Identify exact reason.
3. Check whether another developer is modifying the same area.
4. Make the smallest safe change.
5. Inform the other developer.
6. Test affected functionality.
7. Document the change.

---

## 13. Priority System

- **P0** = Core / Blocking
- **P1** = Important Production
- **P2** = Enhancement
- **P3** = Future / Progressive

| Priority | Meaning |
|---|---|
| **P0** | The platform cannot be considered functionally complete without it. |
| **P1** | Required for a serious production-ready platform. |
| **P2** | Important improvement but not necessarily blocking initial production. |
| **P3** | Future growth, advanced intelligence or expansion. |

---

## 14. Developer 01 — Master Responsibility

**Branch:** `features/developer-01`

Developer 01 owns:

- Authentication UX
- User Profile
- Education
- Skills
- Experience
- Interests
- Career Goals
- Documents
- Opportunity Discovery
- Search
- Filters
- Saved Opportunities
- Opportunity Comparison
- User Applications
- Application Tracking
- Recommendations
- User Notifications
- User Dashboard
- Public Landing Page
- User Mobile UX
- Accessibility
- Internationalization — user side
- SEO

---

## 15. Dev 01 — Authentication

**Priority:** P0

**Tasks**

- [ ] Audit current authentication.
- [ ] Login.
- [ ] Registration.
- [ ] Logout.
- [ ] Current user endpoint.
- [ ] Session persistence.
- [ ] Refresh token handling.
- [ ] Access token expiration.
- [ ] Forgot password.
- [ ] Reset password.
- [ ] Password validation.
- [ ] Protected routes.
- [ ] Unauthorized state.
- [ ] Expired session handling.
- [ ] Authentication error handling.
- [ ] Authentication loading states.
- [ ] Authentication rate-limit integration.
- [ ] Security-aware logout.
- [ ] Role-based redirect.

**Acceptance Criteria**

A user can:

```
Register → Login → Access own account → Refresh session → Logout
```

without exposing unauthorized resources.

---

## 16. Dev 01 — User Profile

**Priority:** P0

**Tasks**

- [ ] Personal information.
- [ ] Education.
- [ ] Skills.
- [ ] Experience.
- [ ] Interests.
- [ ] Career goals.
- [ ] Location.
- [ ] Profile completion.
- [ ] Profile visibility.
- [ ] Profile editing.
- [ ] Profile validation.
- [ ] User preferences.

**Profile structure**

```
USER
 ├── Personal Information
 ├── Education
 ├── Skills
 ├── Experience
 ├── Interests
 ├── Career Goals
 ├── Location
 ├── Documents
 ├── Preferences
 └── Activity
```

**Important**

Profile completion must come from actual stored data.

Never hardcode:

```
Profile = 80%
```

unless the backend calculation actually returns 80%.

---

## 17. Dev 01 — Document Management

**Priority:** P0

Support:

- [ ] CV
- [ ] Certificates
- [ ] Academic documents
- [ ] Portfolio
- [ ] Other application documents

**Security**

- [ ] File type validation.
- [ ] File size validation.
- [ ] Private storage.
- [ ] Authorization.
- [ ] Secure download.
- [ ] Signed URLs where applicable.
- [ ] Secure deletion.
- [ ] Access auditing where necessary.
- [ ] Upload abuse protection.
- [ ] Malware scanning strategy.

> Never expose private documents through unrestricted public URLs.

---

## 18. Dev 01 — Opportunity Discovery

**Priority:** P0

Users must be able to:

- [ ] Browse opportunities.
- [ ] Search.
- [ ] Filter.
- [ ] Sort.
- [ ] Open details.
- [ ] Understand eligibility.
- [ ] View deadline.
- [ ] View source.
- [ ] View application method.
- [ ] Save.
- [ ] Apply.
- [ ] Compare where supported.

---

## 19. Dev 01 — Search & Filters

**Priority:** P0

**Filters:**

- Keyword
- Category
- Location
- Deadline
- Education
- Skills
- Experience
- Organization
- Remote
- Funding
- Opportunity type

**Support:**

- [ ] Pagination.
- [ ] Sorting.
- [ ] Loading.
- [ ] Empty state.
- [ ] Error state.
- [ ] Retry.
- [ ] Clear filters.

**Future:**

- [ ] Saved searches.
- [ ] Search alerts.
- [ ] Semantic search.
- [ ] Natural language search.

---

## 20. Dev 01 — Saved Opportunities

**Priority:** P0

- [ ] Save.
- [ ] Unsave.
- [ ] Saved opportunities page.
- [ ] Pagination.
- [ ] Deadline awareness.
- [ ] Empty state.
- [ ] Real database data.

---

## 21. Dev 01 — Opportunity Comparison

**Priority:** P2

Users may compare:

- Eligibility
- Deadline
- Location
- Funding
- Skills
- Education
- Experience
- Application method

> Keep the first implementation simple.

---

## 22. Dev 01 — Application Experience

**Priority:** P0

**User side:**

- [ ] Start application.
- [ ] Validate eligibility where possible.
- [ ] Select required documents.
- [ ] Submit.
- [ ] Prevent duplicate application.
- [ ] View status.
- [ ] View history.
- [ ] Withdraw where allowed.
- [ ] Receive notifications.

**Application statuses:**

```
PREPARING → SUBMITTED → UNDER_REVIEW → SHORTLISTED → INTERVIEW → ACCEPTED
                                                                → REJECTED
                                                                → WITHDRAWN
```

### Application Ownership & Access

**Ownership:**

- Application belongs to the applicant (USER)
- Organization can view applications for their opportunities
- Organization members see applications based on role

**Access Rules:**

| Actor | Access |
|---|---|
| Applicant | Own applications, withdraw |
| Organization Admin | All applications for org opportunities |
| Organization Member | Applications for assigned opportunities |
| Moderator | Any application for investigation |
| Admin | Any application for platform support |

**Candidate-Data Protection:**

- Applicant personal data visible only to authorized org members
- Internal notes never visible to applicant
- Document access requires explicit permission
- Application data retained per retention policy

### Application Timeline

**Status History:**

Every status change recorded with:

- Previous status
- New status
- Changed by (user ID)
- Timestamp
- Reason (optional, required for REJECTED)

**Timeline Display:**

```
Applied (Jan 15)
  → Under Review (Jan 16)
  → Shortlisted (Jan 20)
  → Interview Scheduled (Jan 25)
  → Accepted (Jan 28)
```

### Withdrawal Rules

- Applicant can withdraw from PREPARING, SUBMITTED, UNDER_REVIEW statuses
- Cannot withdraw after SHORTLISTED (organization has invested time)
- Cannot withdraw after ACCEPTED/REJECTED
- Withdrawal is immediate, no confirmation required
- Organization notified of withdrawal
- Application preserved in history with WITHDRAWN status

### Application Validation Rules

- Application requires: opportunity ID, applicant ID
- Cover letter: optional, max 5000 characters
- Resume URL: optional if user has CV on profile
- Duplicate prevention: one application per user per opportunity
- Opportunity must be PUBLISHED or CLOSING_SOON to apply
- Applicant must have active account (not banned/suspended)

---

## 23. Dev 01 — Application Timeline

Example:

```
Application Started
        │
        ▼
Application Submitted
        │
        ▼
   Under Review
        │
        ▼
   Shortlisted
        │
        ▼
    Interview
        │
        ├──────► Rejected
        │
        └──────► Accepted
```

> The UI must reflect the real backend history.

---

## 24. Dev 01 — Recommendation Engine

**Priority:** P1

**Inputs:**

- Skills.
- Education.
- Experience.
- Interests.
- Career goals.
- Location.
- Eligibility.
- Previous activity.

Start with explainable rules.

**Example:**

| Factor | Weight |
|---|---|
| Eligibility | 30% |
| Skills match | 25% |
| Education match | 15% |
| Career goal | 15% |
| Location | 10% |
| Interest | 5% |

> Do not present a recommendation as AI-generated unless an actual AI system generated it.

Every recommendation should eventually explain:

> "Why am I seeing this?"

### Matching Algorithm v1 (Rule-Based)

**Score Components:**

| Factor | Weight | Data Source | Calculation |
|---|---|---|---|
| Eligibility Match | 30% | Opportunity rules vs Profile | % rules satisfied |
| Skills Match | 25% | User skills vs Required skills | Overlap ratio |
| Education Match | 15% | User education vs Required level | Level comparison |
| Career Goal Alignment | 15% | User goals vs Category/type | Category match |
| Location Match | 10% | User location vs Opportunity location | Exact/remote match |
| Interest Match | 5% | User interests vs Tags | Overlap ratio |

**Scoring Rules:**

- Score range: 0-100
- Minimum threshold: 20 (below = not recommended)
- High relevance: 70+
- Medium relevance: 40-69
- Low relevance: 20-39

**Recommendation Set:**

- Maximum 50 recommendations per user
- Refresh frequency: daily (configurable)
- Recalculate on profile update
- Recalculate on opportunity publish

**Explainability:**

Every recommendation must include:

- Overall score
- Top 3 matching factors
- Missing factors (if any)
- Example: "85% match - Strong skills match, Meets education requirement, Location: Remote"

**Diversity Rules:**

- Maximum 3 opportunities from same category
- Mix of opportunity types (job, scholarship, etc.)
- Fresh opportunities prioritized (published < 30 days)

**Feedback Mechanism:**

- Users can mark recommendations as relevant/irrelevant
- Feedback improves future recommendations
- Track save/apply from recommendations

---

## 25. Dev 01 — User Notifications

**Priority:** P0

User notifications include:

- Application submitted.
- Application status changed.
- Deadline reminder.
- Recommendation.
- Verification result.
- Organization response.
- Security notification.

**Required:**

- [ ] Notification center.
- [ ] Read/unread.
- [ ] History.
- [ ] Preferences.
- [ ] Deep links.
- [ ] Empty state.

---

## 26. Dev 01 — User Dashboard

**Priority:** P0

Dashboard should answer:

> "What should I do next?"

Include:

- Profile completion.
- Recommended opportunities.
- Saved opportunities.
- Recent applications.
- Application status.
- Upcoming deadlines.
- Notifications.
- Skills.
- Career goals.
- Documents.
- Verification state where relevant.
- Quick actions.

**Sketch**

```
┌─────────────────────────────────────────────────────┐
│ Welcome back                                         │
│ What should you do next?                             │
│                                                       │
│ [ Search opportunities......................... ]    │
│                                                       │
│ Profile Completion                                   │
│ ███████████████░░░░                                  │
│                                                       │
│ Recommended Opportunities                            │
│                                                       │
│ ┌─────────┐ ┌─────────┐ ┌─────────┐                  │
│ │ Job     │ │Scholar. │ │Training │                  │
│ └─────────┘ └─────────┘ └─────────┘                  │
│                                                       │
│ My Applications                                      │
│                                                       │
│ Upcoming Deadlines                                   │
└─────────────────────────────────────────────────────┘
```

---

## 27. Dev 01 — Public Landing Page

**Priority:** P0

The landing page is the public front door of TDOP.

> Do NOT replace existing application dashboards.

**Flow:**

```
LANDING
   │
   ├── Search
   │      ↓
   │   DISCOVERY
   │
   ├── Get Started
   │      ↓
   │   REGISTER
   │
   └── Login
          ↓
     ROLE DASHBOARD
```

**Navigation**

- Home
- Fursa
- Mashirika
- Kuhusu TDOP
- Jinsi Inavyofanya Kazi
- Help
- EN | SW
- Login
- Get Started

---

## 28. Landing Page Content

Suggested hero:

> "Fursa za Kesho Ziko Hapa"

Supporting message:

> "TDOP inakuunganisha na fursa za ajira, masomo, biashara, ufadhili, mafunzo na maendeleo kutoka Tanzania na duniani."

**Sections:**

```
Hero
 ↓
Search
 ↓
Trust indicators
 ↓
Opportunity categories
 ↓
Featured opportunities
 ↓
How TDOP works
 ↓
Why TDOP
 ↓
Personalized discovery
 ↓
Application journey
 ↓
For organizations
 ↓
Trust & safety
 ↓
Final CTA
 ↓
Footer
```

> Featured opportunities MUST come from real data.

If no data exists:

```
No featured opportunities available yet.
```

**Never fabricate.**

---

## 29. Dev 01 — Mobile UX

**Priority:** P1

Support:

`320px` · `375px` · `425px` · `768px` · `1024px` · `1280px` · `1440px` · `1920px`

**Priorities:**

- Mobile-first.
- Touch-friendly.
- Fast loading.
- Low-data awareness.
- Readable text.
- Reliable forms.
- Simple navigation.

---

## 30. Dev 01 — Accessibility

**Priority:** P1

**Target:** WCAG 2.2 AA where practical.

**Required:**

- Semantic HTML.
- Keyboard navigation.
- Focus states.
- Accessible labels.
- Form errors.
- Color contrast.
- Alt text.
- Screen-reader-friendly structure.
- Reduced motion.

---

## 31. Dev 01 — Internationalization

**Languages:**

- English
- Kiswahili

**Requirements:**

- [ ] Language switcher.
- [ ] Consistent translations.
- [ ] No unnecessary hardcoded UI strings.
- [ ] Localized dates/numbers where applicable.
- [ ] Preserve meaning.

---

## 32. Dev 01 — SEO

Public pages should include:

- Title.
- Meta description.
- H1/H2 hierarchy.
- Canonical URL.
- Open Graph metadata.
- Social previews.
- Structured data where appropriate.
- Semantic HTML.

Suggested title:

> TDOP — Tanzania Digital Opportunity Platform

---

## 33. Developer 02 — Master Responsibility

**Branch:** `features/developer-02`

Developer 02 owns:

- RBAC
- Organizations
- Organization Profiles
- Organization Teams
- Permissions
- Opportunity Management
- Opportunity Publishing
- Verification
- Candidate Management
- Moderation
- Reports
- Anti-Fraud
- Trust
- Admin
- Super Admin
- Analytics
- Audit
- Platform Notifications
- Deadline Engine
- Observability
- Production Operations

---

## 34. Dev 02 — RBAC

**Priority:** P0

**Roles:**

`USER` · `ORGANIZATION` · `ORGANIZATION_ADMIN` · `ORGANIZATION_MEMBER` · `VERIFICATION_OFFICER` · `MODERATOR` · `ADMIN` · `SUPER_ADMIN`

**Required:**

- [ ] Permission matrix.
- [ ] Server-side authorization.
- [ ] Ownership validation.
- [ ] Organization membership validation.
- [ ] Organization isolation.
- [ ] Admin permissions.
- [ ] Moderator permissions.
- [ ] Verification officer permissions.
- [ ] Super Admin permissions.
- [ ] IDOR prevention.
- [ ] Audit sensitive actions.

> Frontend route protection is **NOT** sufficient.

---

## 35. Dev 02 — Organization Management

**Priority:** P0

- [ ] Create organization.
- [ ] Organization profile.
- [ ] Description.
- [ ] Logo.
- [ ] Website.
- [ ] Contact details.
- [ ] Organization type.
- [ ] Location.
- [ ] Verification status.
- [ ] Opportunity list.
- [ ] Organization activity.

---

## 36. Dev 02 — Organization Team

**Priority:** P0

**Support:** `ORGANIZATION_ADMIN` · `ORGANIZATION_MEMBER`

**Tasks:**

- [ ] Invite member.
- [ ] Accept invitation.
- [ ] Remove member.
- [ ] Assign role.
- [ ] Permission management.
- [ ] Team activity.
- [ ] Ownership checks.
- [ ] Audit.

### Role Definitions

| Role | Opportunity Access | Candidate Access | Team Management | Settings |
|---|---|---|---|---|
| ORG_ADMIN | Full | Full | Full | Full |
| ORG_MEMBER | Assigned only | Assigned only | View | View |

### Opportunity Access Rules

**ORG_ADMIN:**

- Create, edit, delete any opportunity
- Submit, publish, suspend, archive any opportunity
- View all applications for all opportunities

**ORG_MEMBER:**

- Create opportunities (draft only)
- Edit assigned opportunities
- View applications for assigned opportunities
- Cannot publish, suspend, or archive

### Candidate/Application Access Rules

**ORG_ADMIN:**

- View all candidates for all opportunities
- View all applications
- Update application status
- Add internal notes
- Export candidate data

**ORG_MEMBER:**

- View candidates for assigned opportunities only
- View applications for assigned opportunities only
- Update status with limits (cannot ACCEPT)
- Add internal notes

### Invitation Rules

**Who Can Invite:**

- ORG_ADMIN only

**Invitation Limits:**

- Maximum 50 pending invitations per organization
- Invitation expires after 7 days (configurable)
- Re-invitation allowed after expiry

**Invitation Process:**

1. Admin sends invitation (email required)
2. Invitee receives email with link
3. Invitee creates account or logs in
4. Invitee accepts invitation
5. Member added to organization

### Role Change Rules

**Who Can Change Roles:**

- ORG_ADMIN only
- Cannot change own role (prevent accidental demotion)
- Cannot promote above own role level

**Role Change Process:**

1. Admin selects member
2. Admin selects new role
3. Confirmation required
4. Role change recorded in audit
5. Member notified

### Removal/Revocation Rules

**Who Can Remove:**

- ORG_ADMIN only
- Cannot remove self (must transfer ownership first)

**Removal Process:**

1. Admin selects member
2. Confirmation required
3. Member removed immediately
4. Member's access revoked
5. Member notified
6. Audit recorded

**Revocation:**

- Invitations can be revoked before acceptance
- Revoked invitations marked as CANCELLED
- Audit recorded

---

## 37. Dev 02 — Opportunity Engine

**Priority:** P0

**Opportunity lifecycle:**

```
DRAFT → SUBMITTED → UNDER_REVIEW → VERIFIED → MODERATION
      → APPROVED → PUBLISHED → CLOSING SOON → EXPIRED → ARCHIVED
```

**Exceptional states:**

`REJECTED` · `SUSPENDED` · `WITHDRAWN`

### Transition Rules

| From | To | Allowed Actor | Condition |
|---|---|---|---|
| DRAFT | SUBMITTED | Organization Owner/Admin | Required fields complete |
| SUBMITTED | UNDER_REVIEW | Verification Officer | Auto-queue or manual |
| UNDER_REVIEW | VERIFIED | Verification Officer | Evidence approved |
| UNDER_REVIEW | REJECTED | Verification Officer | Reason required |
| VERIFIED | MODERATION | Moderator | Auto-queue or manual |
| MODERATION | APPROVED | Moderator | Content approved |
| MODERATION | REJECTED | Moderator | Reason required |
| APPROVED | PUBLISHED | Organization Owner/Admin | Deadline set |
| PUBLISHED | CLOSING_SOON | System | Deadline within threshold |
| CLOSING_SOON | EXPIRED | System | Deadline passed |
| PUBLISHED/EXPIRED | ARCHIVED | Organization/Admin/System | Manual or auto |
| Any active | SUSPENDED | Admin/Moderator | Policy violation |
| SUSPENDED | PUBLISHED | Admin | After review |
| ARCHIVED | PUBLISHED | Organization Admin | Re-activated |

### Publishing Rules

- Opportunity must be APPROVED before PUBLISHED
- Deadline must be set and in the future
- Organization should be verified (recommended, not blocking for P0)
- Title, description, and application method required

### Expiration Behavior

- System checks hourly (configurable)
- Expired opportunities: status → EXPIRED, removed from active listings
- Applications may still be processed for expired opportunities
- Organization can extend deadline before expiration

### Suspension Rules

- Admin/Moderator can suspend with reason
- Suspended opportunities: hidden from discovery, existing applications preserved
- Organization notified of suspension
- Suspension reason recorded in audit

### Restoration Rules

- Suspended opportunities can be restored to PUBLISHED
- Restoration requires admin approval
- Restoration reason recorded
- Organization notified of restoration

---

## 38. Opportunity Data Model

Opportunity should support:

- Title.
- Description.
- Category.
- Organization.
- Source.
- Source URL.
- Official application URL.
- Deadline.
- Location.
- Remote / On-site / Hybrid.
- Education requirements.
- Skills.
- Experience.
- Eligibility.
- Funding.
- Required documents.
- Application method.
- Verification status.
- Moderation status.
- Publication status.
- Created date.
- Updated date.
- Last verified date.
- Expiry.

### Source & Provenance Fields

| Field | Type | Required | Description |
|---|---|---|---|
| sourceType | ENUM | Yes | MANUAL, IMPORTED, API, RSS, PARTNER |
| sourceName | String | Yes | Original publisher name |
| sourceUrl | URL | No | Original source URL |
| sourceReferenceId | String | No | External ID from source |
| sourceVerified | Boolean | No | Whether source has been verified |
| sourceLastChecked | DateTime | No | When source was last verified |
| sourceCredibility | ENUM | No | UNVERIFIED, BASIC, VERIFIED, TRUSTED |
| importedAt | DateTime | No | When imported (if not manual) |
| importedBy | Reference | No | Who/what imported |

### Source Verification Rules

- Manual opportunities: sourceName required, sourceType = MANUAL
- Imported opportunities: sourceName, sourceUrl, importedAt required
- Source credibility updates based on organization verification status
- Stale sources flagged for re-verification

### Structured Eligibility Rules

**Rule Types:**

| Rule Type | Operator | Value | Example |
|---|---|---|---|
| EDUCATION | EQUALS, MINIMUM, ANY_OF | Education level | MINIMUM: DIPLOMA |
| SKILL | CONTAINS, ANY_OF | Skill names | ANY_OF: JavaScript, Python |
| EXPERIENCE | MINIMUM, RANGE | Years | MINIMUM: 2 |
| LOCATION | EQUALS, ANY_OF, NOT_IN | Country/Region | ANY_OF: Tanzania, Kenya |
| AGE | MINIMUM, MAXIMUM, RANGE | Years | MAXIMUM: 35 |
| RESIDENCY | EQUALS, ANY_OF | Nationality | ANY_OF: Tanzanian |
| DOCUMENT | REQUIRED, OPTIONAL | Document type | REQUIRED: CV, Certificate |
| CUSTOM | TEXT_MATCH | Freeform | "Must be female" (with reason) |

**Rule Storage:**

- Rules stored as JSON array on opportunity
- Example: `[{"type":"SKILL","operator":"ANY_OF","values":["Java","Python"]}]`

**Extensibility:**

- New rule types added via configuration
- Custom rules supported with text matching
- Rule evaluation engine pluggable

**Explainability:**

- Each recommendation shows which rules matched
- Users see: "You match 4 of 5 requirements"
- Missing requirements listed clearly
- Example: "Missing: 2+ years experience"

**Validation:**

- Backend validates eligibility on application
- Mismatches flagged but not blocking (warning)
- Organization can override eligibility check

---

## 39. Opportunity Trust

Every opportunity should eventually answer:

- WHO published it?
- WHERE did it come from?
- IS the organization verified?
- WHEN was it last checked?
- WHEN does it close?
- HOW do I apply?
- WHAT evidence supports it?

Avoid unsupported claims such as:

> "100% scam-free"

Use measurable trust signals instead.

---

## 40. Dev 02 — Organization Opportunity Creation

**Priority:** P0

**Workflow:**

```
Create Draft → Complete Details → Validate → Preview → Submit
             → Verification → Moderation → Publish
```

**Required:**

- [ ] Draft.
- [ ] Validation.
- [ ] Preview.
- [ ] Submit.
- [ ] Edit.
- [ ] Verification.
- [ ] Moderation.
- [ ] Publish.
- [ ] Suspend.
- [ ] Archive.
- [ ] Restore.
- [ ] Expiry.

---

## 41. Dev 02 — Candidate Management

**Priority:** P0

Organizations should be able to:

- [ ] View authorized applicants.
- [ ] Search candidates.
- [ ] Filter candidates.
- [ ] Review applications.
- [ ] Shortlist.
- [ ] Reject.
- [ ] Add internal notes.
- [ ] Track candidate status.
- [ ] Communicate where supported.
- [ ] View funnel analytics.

> Organizations must never see applicants belonging to opportunities they are not authorized to manage.

---

## 42. Dev 02 — Organization Verification

**Priority:** P0

**Workflow:**

```
Organization → Verification Request → Documents → Verification Queue → Review
                                                                          │
                                                ┌─────────────┬───────────┴──────────────┐
                                                ▼             ▼                          ▼
                                             APPROVE        REJECT                  MORE INFO
```

**Required:**

- [ ] Document submission.
- [ ] Review.
- [ ] Approve.
- [ ] Reject.
- [ ] Request information.
- [ ] Verification history.
- [ ] Audit.
- [ ] Reason capture.
- [ ] Status.

### Complete Verification Lifecycle

```
SUBMITTED → UNDER_REVIEW → APPROVED/REJECTED/REQUEST_INFO
               ↓
        RE_VERIFICATION
               ↓
        SUSPENDED/EXPIRED
               ↓
        RESTORED
```

**States:**

- SUBMITTED: Initial verification request
- UNDER_REVIEW: Assigned to verification officer
- APPROVED: Organization verified
- REJECTED: Verification denied (reason required)
- REQUEST_INFO: Additional information needed
- SUSPENDED: Verification revoked (violation)
- EXPIRED: Verification period lapsed
- RESTORED: Re-activated after suspension/expiry
- RE_VERIFICATION: Under review for renewal

### Evidence Requirements

**Required Documents:**

- Business registration certificate
- Tax identification number
- Proof of address (utility bill, bank statement)
- Organization description
- Contact information verification

**Optional/Recommended:**

- Website verification
- Social media presence
- Previous opportunities published
- References

**Document Validation:**

- File type: PDF, JPG, PNG
- File size: max 10MB
- Documents must be current (within 12 months)
- Expired documents trigger re-verification

### Reviewer Accountability

**Assignment:**

- Verification requests assigned to specific officer
- Officer cannot verify own organization
- Conflict of interest: reassign to different officer

**Review Requirements:**

- Decision required within 7 days (configurable)
- Reviewer must provide reason for REJECT
- Reviewer must provide specific info request for REQUEST_INFO
- Escalation path for complex cases

**Quality:**

- Random audit of verification decisions
- Appeal process for rejected organizations
- Reviewer performance metrics

### Reasons & Audit History

**Required Reason Fields:**

- REJECTED: reason (text, required)
- REQUEST_INFO: specific information needed (text, required)
- SUSPENDED: reason and evidence reference (text, required)

**Audit Trail:**

Every verification action records:

- WHO (reviewer ID)
- DID WHAT (action)
- TO WHICH ENTITY (organization ID)
- WHEN (timestamp)
- RESULT/CHANGE (status change)
- REASON/CONTEXT (reason text)

### Re-verification Rules

**Triggers:**

- Annual re-verification (configurable)
- Document expiry
- Organization profile changes
- Complaints/reports received
- Random audit selection

**Process:**

- Re-verification follows same review process
- Previous verification history visible to reviewer
- Fast-track for minor updates
- Full review for major changes

---

## 43. Verification Officer Dashboard

**Priority:** P0

Include:

- Pending verification.
- Verification queue.
- Documents.
- Review.
- Approve.
- Reject.
- Request information.
- Verification history.
- Statistics.

---

## 44. Dev 02 — Moderation

**Priority:** P0

**Moderators review:**

- Pending opportunities.
- Reported opportunities.
- Suspicious opportunities.
- Duplicate opportunities.
- Unsafe links.
- Misleading content.
- Policy violations.

**Actions:**

`APPROVE` · `REJECT` · `SUSPEND` · `ARCHIVE` · `RESTORE` · `REQUEST INFORMATION`

> Every action should be auditable.

### Spam & Abuse Detection

**Signals:**

- Multiple opportunities with identical/similar content
- Rapid opportunity creation (10+ in 24 hours)
- Copy-pasted descriptions from external sources
- URL patterns associated with spam
- Keyword patterns (excessive caps, suspicious phrases)

**Actions:**

- Auto-flag for review
- Rate limiting on opportunity creation
- Temporary suspension for repeat offenders
- Permanent ban for confirmed spam accounts

### Duplicate Content Detection

**Detection:**

- Hash-based exact duplicate detection
- Fuzzy matching for near-duplicates (>80% similarity)
- Cross-organization duplicate detection
- Source URL duplicate detection

**Handling:**

- First published = canonical
- Duplicates flagged for moderator review
- Moderator decides: keep, archive, or delete
- Audit trail for all decisions

---

## 45. Dev 02 — Reporting System

**Priority:** P0

Users should be able to report:

- Scam
- Fake organization
- Suspicious link
- Misleading information
- Duplicate
- Expired
- Wrong eligibility
- Inappropriate content
- Other

**Workflow:**

```
REPORT → QUEUE → INVESTIGATION → DECISION → ACTION → AUDIT
```

### Investigation Workflow

**Process:**

```
REPORT/DETECTION → QUEUE → ASSIGN → INVESTIGATE → DECIDE → ACTION → AUDIT
```

**Investigation Steps:**

1. Review report/detection details
2. Gather evidence (opportunity data, user history, organization history)
3. Interview parties if needed
4. Document findings
5. Make decision
6. Execute action
7. Record audit

**Evidence Gathering:**

- Opportunity history
- User account history
- Organization verification status
- Similar reports
- External source verification

### Escalation Rules

**Escalation Triggers:**

- High-risk fraud signals
- Multiple reports on same target
- Legal implications
- Public safety concerns
- Repeat offender

**Escalation Path:**

- Moderator → Senior Moderator → Admin → Super Admin
- Each level has 24-hour SLA (configurable)
- Escalation recorded in audit

### Resolution Tracking

**Resolution States:**

- PENDING: Under investigation
- RESOLVED_NO_ACTION: No violation found
- RESOLVED_WARNING: Warning issued
- RESOLVED_SUSPENDED: Account/opportunity suspended
- RESOLVED_BANNED: Account banned
- RESOLVED_ARCHIVED: Content archived
- ESCALATED: Moved to higher authority
- CLOSED: Investigation complete

**Resolution Details:**

- Action taken
- Reason for decision
- Evidence reference
- Follow-up required
- Appeal deadline

---

## 46. Dev 02 — Anti-Fraud

**Priority:** P1

**Potential risk signals:**

- Duplicate opportunities.
- Suspicious URLs.
- Repeated reports.
- Unverified organization.
- Unusual publishing patterns.
- Repeated rejected opportunities.
- Deadline anomalies.
- Source quality.
- Suspicious application destinations.

**Possible risk levels:** `LOW` · `MEDIUM` · `HIGH`

> Risk scoring should support human review. It should **NOT** automatically accuse a person or organization without evidence.

---

## 46A. Dev 02 — Duplicate Opportunity Control

**Priority:** P1

### Detection Signals

- Same title + same organization (exact match)
- Similar title + similar description (fuzzy match)
- Same source URL
- Same application URL
- Similar deadline + location + category

### Detection Timing

- On opportunity submission
- On opportunity update
- Scheduled batch detection (daily)

### Flagging Rules

- Exact matches: AUTO_FLAGGED
- Fuzzy matches above threshold: SUGGESTED_REVIEW
- Duplicates shown in moderation queue with similarity score

### Review Process

- Moderator reviews flagged duplicates
- Options: KEEP_BOTH, MERGE, ARCHIVE_DUPLICATE, RESTORE
- Decision recorded with reason

### Canonical/Primary Opportunity

- First published opportunity = canonical (default)
- Can be reassigned by moderator
- Canonical opportunity survives merge
- Non-canonical archived or deleted

### Merge Rules

- Applications merged to canonical
- Status history preserved from both
- Audit trail records merge
- Organizations notified

### Audit History

- All duplicate decisions logged
- WHO decided, WHAT action, WHEN, WHY
- Reverse action possible (unmerge within retention period)

---

## 47. Dev 02 — Admin Dashboard

**Priority:** P0

Dashboard:

- Users
- Organizations
- Verified Organizations
- Pending Verification
- Opportunities
- Pending Moderation
- Published Opportunities
- Suspended Opportunities
- Reports
- Fraud Signals
- Applications
- Platform Activity
- Audit
- Security

> No hardcoded statistics.

---

## 48. Dev 02 — Super Admin

**Priority:** P1

Capabilities may include:

- Full platform oversight.
- Role management.
- System configuration.
- Security controls.
- Category management.
- Opportunity state configuration.
- Notification configuration.
- Trust configuration.
- Moderation configuration.
- Platform health.

> All sensitive Super Admin actions must be audited.

---

## 49. Dev 02 — Platform Analytics

**Priority:** P1

**Metrics:**

- Users
- Organizations
- Opportunities
- Applications
- Views
- Saves
- Searches
- Recommendations
- Reports
- Verification activity
- Moderation activity
- Successful Opportunity Outcomes

> Never fabricate numbers.

No data:

```
No data available yet.
```

---

## 50. Dev 02 — Organization Analytics

Organization analytics may include:

- Views
- Saves
- Applications
- Shortlisted
- Interviews
- Accepted
- Conversion rates

> Only calculate metrics from real records.

---

## 51. Dev 02 — Deadline Engine

**Priority:** P1

**Opportunity states:** `OPEN` · `CLOSING SOON` · `EXPIRED`

**Possible reminders:** 30 days · 14 days · 7 days · 3 days · 1 day

> Reminder periods must be configurable.

Expired opportunities should not remain presented as active opportunities.

### Deadline Validation Rules

- Deadline must be in the future when publishing
- Deadline cannot be extended past 1 year from now
- Deadline changes after publishing require audit log entry
- Past deadlines prevent new applications

### Timezone Handling

- All deadlines stored in UTC
- Display in user's local timezone
- Organization deadline display in org timezone
- System checks use UTC

### Stale Opportunity Detection

- Opportunities not updated in 90 days flagged as potentially stale
- Opportunities with broken source URLs flagged
- Stale opportunities require re-verification
- Stale flag visible to users with explanation

### Re-verification Rules

- Verified opportunities re-verified annually (configurable)
- Source changes trigger re-verification
- Organization status changes trigger re-verification
- Re-verification failure → SUSPENDED with reason

---

## 52. Dev 02 — Audit Log

**Priority:** P0

**Audit sensitive actions:**

- WHO?
- WHAT?
- WHEN?
- RESOURCE?
- WHAT CHANGED?
- SOURCE/IP where appropriate

**Examples:**

- Role changes.
- Organization changes.
- Verification.
- Moderation.
- Opportunity publishing.
- Application status changes.
- Admin actions.
- Security events.
- Configuration changes.

> Do not log sensitive personal information unnecessarily.

### Audit Record Format

Every audit entry must capture:

| Field | Description | Example |
|---|---|---|
| WHO | Actor (user ID + role) | user:123 (ADMIN) |
| DID WHAT | Action performed | CREATED, UPDATED, DELETED, APPROVED, REJECTED |
| TO WHICH ENTITY | Target entity type + ID | opportunity:456 |
| WHEN | Timestamp (UTC) | 2025-01-15T10:30:00Z |
| RESULT/CHANGE | Before/after state | status: DRAFT → PUBLISHED |
| REASON/CONTEXT | Reason for action | "Content approved after review" |
| SOURCE/IP | Request source | IP: 192.168.1.1 |

### Audit Coverage Requirements

**Security:**

- Login attempts (success/failure)
- Password changes
- Role changes
- Permission changes
- Account lockouts

**Organizations:**

- Organization created/updated/deleted
- Verification submitted/approved/rejected
- Team member added/removed/role changed
- Settings changed

**Opportunities:**

- Opportunity created/updated/deleted
- Status changes (all transitions)
- Verification/moderation actions
- Publishing/suspension/archival
- Deadline changes

**Applications:**

- Application created/withdrawn
- Status changes
- Shortlisting
- Notes added
- Document access

**Verification:**

- Verification request submitted
- Documents uploaded
- Review assigned
- Decision made
- Re-verification triggered

**Moderation:**

- Report submitted
- Investigation assigned
- Decision made
- Action taken
- Escalation

**Administrative:**

- Configuration changes
- User bans/unbans
- Data exports
- System changes

### Audit Log Retention

- Security logs: 7 years
- Organization logs: 5 years
- Opportunity logs: 5 years
- Application logs: 5 years
- Verification logs: 5 years
- Moderation logs: 7 years
- Administrative logs: 7 years

### Audit Integrity

- Audit logs immutable (append-only)
- No deletion of audit records
- Audit logs stored separately from application data
- Backup audit logs to separate storage

---

## 53. Dashboard Matrix

| Dashboard | Owner | Priority |
|---|---|---|
| User / Seeker | Developer 01 | P0 |
| Organization | Developer 02 | P0 |
| Organization Member | Developer 02 | P0 |
| Verification Officer | Developer 02 | P0 |
| Moderator | Developer 02 | P0 |
| Admin | Developer 02 | P0 |
| Super Admin | Developer 02 | P1 |

---

## 54. User Dashboard

```
┌─────────────────────────────────────────────────────┐
│ TDOP                          Notifications  Profile │
├─────────────────────────────────────────────────────┤
│                                                       │
│ Welcome back                                         │
│ What should you do next?                             │
│                                                       │
│ [ Search opportunities......................... ]    │
│                                                       │
│ Profile Completion                                   │
│ ███████████████░░░░                                  │
│                                                       │
│ Recommended Opportunities                            │
│                                                       │
│ ┌─────────┐ ┌─────────┐ ┌─────────┐                  │
│ │ Job     │ │Scholar. │ │Training │                  │
│ └─────────┘ └─────────┘ └─────────┘                  │
│                                                       │
│ My Applications                                      │
│                                                       │
│ Upcoming Deadlines                                   │
│                                                       │
│ Saved Opportunities                                  │
└─────────────────────────────────────────────────────┘
```

---

## 55. Organization Dashboard

```
┌─────────────────────────────────────────────────────┐
│ ORGANIZATION                                         │
├─────────────────────────────────────────────────────┤
│ Verification: VERIFIED                               │
│                                                       │
│ Opportunities   Applications   Shortlisted   Team    │
│      12              184           27         6      │
│                                                       │
│ [ Create Opportunity ]                               │
│                                                       │
│ Opportunity Performance                              │
│                                                       │
│ Views → Saves → Applications → Shortlist → Outcome   │
│                                                       │
│ Recent Applicants                                    │
│                                                       │
│ Team Activity                                        │
└─────────────────────────────────────────────────────┘
```

---

## 56. Admin Dashboard

```
┌─────────────────────────────────────────────────────┐
│ TDOP ADMIN                                           │
├─────────────────────────────────────────────────────┤
│ Users | Organizations | Opportunities | Reports      │
│                                                       │
│ Pending Verification                                 │
│ Pending Moderation                                   │
│ Suspicious Activity                                  │
│                                                       │
│ Platform Activity                                    │
│                                                       │
│ Recent Audit Events                                  │
└─────────────────────────────────────────────────────┘
```

---

## 57. Application System — Shared Contract

**Application flow:**

```
USER
  │
  ▼
Opportunity
  │
  ▼
Eligibility
  │
  ▼
Prepare
  │
  ▼
Submit
  │
  ▼
Organization Review
  │
  ├── Rejected
  │
  ├── Shortlisted
  │       │
  │       ▼
  │   Interview
  │       │
  │       ├── Rejected
  │       └── Accepted
  │
  ▼
Outcome
```

Developer 01 owns the user experience.

Developer 02 owns the organization/candidate management experience.

> The underlying application model and status system are shared and must be coordinated.

---

## 58. Notification Architecture

```
EVENT
  ↓
Notification Service
  ↓
Preference Check
  ↓
Create Notification
  ↓
Delivery
  ├── In-App
  ├── Email
  ├── SMS
  └── Push
```

> Not every channel must exist in the first release. The architecture should allow future channels.

### Event-Driven Notifications

**Notification Events:**

| Event | Trigger | Channel | Audience |
|---|---|---|---|
| APPLICATION_SUBMITTED | Application created | In-App, Email | Applicant, Org |
| APPLICATION_STATUS_CHANGED | Status updated | In-App, Email | Applicant |
| DEADLINE_REMINDER | Scheduled | In-App, Email | Saved users, Applicants |
| OPPORTUNITY_PUBLISHED | Status → PUBLISHED | In-App | Subscribers |
| OPPORTUNITY_CLOSING_SOON | Status → CLOSING_SOON | In-App | Saved users |
| VERIFICATION_RESULT | Verification complete | In-App, Email | Organization |
| MODERATION_ACTION | Moderation decision | In-App | Organization |
| TEAM_INVITATION | Invitation sent | In-App, Email | Invitee |
| SECURITY_ALERT | Suspicious activity | In-App, Email | Affected user |

### Background Job Requirements

**Scheduled Jobs:**

- Deadline processing: hourly
- Stale opportunity detection: daily
- Re-verification checks: weekly
- Notification digest: configurable
- Analytics aggregation: daily

**Job Execution:**

- Jobs run independently, not blocking API
- Job failures logged, not silently swallowed
- Retry mechanism: 3 attempts with exponential backoff
- Job timeout: configurable per job type

### Retry & Failure Handling

**Notification Delivery:**

- In-App: immediate, retry on failure (3 attempts)
- Email: immediate, retry on failure (3 attempts)
- SMS: immediate, retry on failure (2 attempts)
- Push: immediate, no retry (best effort)

**Job Failures:**

- Failed jobs logged with error details
- Alert on repeated failures (3+ consecutive)
- Dead letter queue for permanently failed jobs
- Admin dashboard shows failed jobs

### Idempotency

**Rules:**

- Notification creation idempotent (same event = same notification)
- Use event ID to prevent duplicates
- Background jobs check if already processed
- Retry uses same job ID

### Job Monitoring

**Tracking:**

- Job name, start time, end time, status
- Success/failure counts
- Average execution time
- Failed job details

**Dashboard:**

- Active jobs
- Recent completions (last 24h)
- Failed jobs
- Job history (last 30 days)
- Average job duration

---

## 59. Trust Architecture

```
SOURCE → ORGANIZATION → VERIFICATION → OPPORTUNITY → MODERATION
       → PUBLISH → REPORTS / SIGNALS → RISK REVIEW → ACTION
```

> Trust is a continuous system, not a one-time badge.

---

## 60. Database Requirements

**Shared responsibility.**

Database should support:

- Users.
- Roles.
- Permissions.
- Organizations.
- Organization memberships.
- Opportunities.
- Opportunity status history.
- Applications.
- Application status history.
- Education.
- Skills.
- Experience.
- Career goals.
- Interests.
- Documents.
- Notifications.
- Reports.
- Verification.
- Moderation.
- Audit logs.
- Analytics/events where appropriate.

---

## 61. Database Quality

**Required:**

- Foreign keys.
- Unique constraints.
- Useful indexes.
- Referential integrity.
- Proper timestamps.
- Soft delete where appropriate.
- Migration discipline.
- Transaction handling.
- Consistent status transitions.

Avoid duplicate business entities.

**Example** — Do not create:

```
Opportunity
JobOpportunity
OpportunityPost
JobPost
```

for the same concept unless there is a clear architectural reason.

### Required Fields

**User:**

- email (unique, not null)
- passwordHash (not null)
- createdAt (not null)

**Organization:**

- orgName (not null)
- user_id (unique, not null)
- createdAt (not null)

**Opportunity:**

- title (not null)
- description (not null)
- deadline (not null)
- status (not null)
- created_by (not null)
- createdAt (not null)

**Application:**

- opportunity_id (not null)
- applicant_id (not null)
- status (not null)
- appliedAt (not null)

### Duplicate Prevention

**Unique Constraints:**

- User.email: unique
- Organization.user_id: unique
- Application.opportunity_id + applicant_id: unique (one application per opportunity)

**Business Rules:**

- No duplicate organization names per user
- No duplicate opportunities (same title + same org + same deadline)
- No duplicate applications

### Invalid State Prevention

**Database Level:**

- Status enums validated at DB level
- Foreign key constraints prevent orphaned records
- Check constraints for valid ranges (e.g., score 0-100)

**Application Level:**

- Status transitions validated in service layer
- Business rules enforced before state change
- Invalid transitions throw exceptions

### Stale/Invalid Data Handling

**Detection:**

- Opportunities with past deadlines but not EXPIRED
- Applications in PREPARING status for 30+ days
- Verification requests pending for 14+ days
- Organizations with expired verification

**Actions:**

- Stale opportunities: auto-expire
- Old applications: prompt user to withdraw or update
- Old verifications: notify admin
- Log all auto-corrections

### Safe Migrations

**Rules:**

- Migrations must be backward compatible
- Data migrations separate from schema migrations
- Migrations tested on copy of production data
- Rollback plan for every migration
- Migrations reviewed before deployment

**Process:**

1. Write migration
2. Test on staging
3. Get approval
4. Deploy to production
5. Verify migration success
6. Monitor for issues

### Integrity Validation

**Scheduled Validation:**

- Daily: check for orphaned records
- Weekly: check for invalid status transitions
- Monthly: full data integrity audit

**Validation Report:**

- Records checked
- Issues found
- Auto-corrections applied
- Manual review required

---

## 62. Security

**Shared responsibility.**

**Required:**

- Rate limiting.
- Secure CORS.
- Security headers.
- JWT security.
- Refresh-token security.
- Input validation.
- Authorization.
- IDOR protection.
- Secure file uploads.
- Secret management.
- Secure logging.
- Password security.
- Session security.
- API abuse protection.
- Database safety.
- XSS protection.
- CSRF strategy where applicable.

---

## 63. Private Data Rule

Sensitive information must never leak through:

- Public API responses.
- Logs.
- Analytics.
- Frontend state.
- URLs.
- Error messages.
- Public search.
- Organization access without authorization.

**Particularly protect:**

- Passwords.
- Tokens.
- Private documents.
- Personal contact details.
- Sensitive application information.
- Internal organization notes.

---

## 64. Performance

Target a fast experience on ordinary mobile networks.

**Required:**

- Pagination.
- Efficient database queries.
- Database indexes.
- Lazy loading.
- Image optimization.
- Code splitting where appropriate.
- API efficiency.
- Caching where useful.
- Avoid unnecessary requests.
- Avoid rendering thousands of records.

---

## 65. Caching

Use caching selectively.

**Good candidates may include:**

- Public opportunity categories.
- Public configuration.
- Frequently accessed public data.
- Search results where appropriate.

> Do not cache sensitive user-specific information incorrectly. Always consider cache invalidation.

---

## 66. Observability

**Developer 02 primary.**

**Required:**

- Structured logging.
- Error tracking.
- Health checks.
- Performance monitoring.
- Security monitoring.
- Database monitoring.
- Background job monitoring.
- Backup monitoring.

### Application/API Error Handling

**Error Response Format:**

```json
{
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Invalid request parameters",
    "details": [{"field": "email", "message": "Email is required"}],
    "timestamp": "2025-01-15T10:30:00Z",
    "requestId": "req-123"
  }
}
```

**Error Categories:**

- 400: Bad Request (validation errors)
- 401: Unauthorized (authentication required)
- 403: Forbidden (insufficient permissions)
- 404: Not Found (resource doesn't exist)
- 409: Conflict (duplicate, state conflict)
- 429: Rate Limited (too many requests)
- 500: Internal Server Error (system error)

**Error Logging:**

- All 4xx errors logged (level: WARN)
- All 5xx errors logged (level: ERROR)
- Sensitive data excluded from logs
- Request ID for correlation

### Structured Logging

**Log Format:**

```json
{
  "timestamp": "2025-01-15T10:30:00Z",
  "level": "INFO",
  "service": "opportunity-service",
  "message": "Opportunity published",
  "requestId": "req-123",
  "userId": "456",
  "opportunityId": "789",
  "duration": 45
}
```

**Required Fields:**

- timestamp (UTC, ISO 8601)
- level (INFO, WARN, ERROR, FATAL)
- service name
- message
- request ID (for correlation)

**Sensitive Data Rules:**

- Never log: passwords, tokens, API keys, credit cards
- Never log: full document contents
- Never log: personal contact details (in plain text)
- Mask: email (j***@***.com), phone (***-***-1234)

### Database Health Monitoring

**Checks:**

- Connection pool status
- Active connections
- Query performance (slow queries > 1s)
- Replication lag (if applicable)
- Disk usage
- Index usage

**Alerts:**

- Connection pool > 80% utilized
- Slow queries > 10 per minute
- Replication lag > 5 seconds
- Disk usage > 80%

### Background Job Monitoring

**Tracking:**

- Job name
- Start time
- End time
- Duration
- Status (SUCCESS, FAILED, RUNNING)
- Retry count
- Error message (if failed)

**Dashboard:**

- Active jobs
- Recent completions (last 24h)
- Failed jobs
- Job history (last 30 days)
- Average job duration

**Alerts:**

- Job failure rate > 5%
- Job duration > 2x average
- Job stuck (running > expected time)
- Dead letter queue not empty

### Performance Monitoring

**API Performance:**

- Response time (p50, p95, p99)
- Requests per second
- Error rate
- Slow endpoints (> 500ms)

**Database Performance:**

- Query time (p50, p95, p99)
- Slow query count
- Index hit rate
- Connection wait time

**Frontend Performance:**

- Page load time
- Time to interactive
- First contentful paint
- Largest contentful paint

### Health Checks

**Endpoints:**

- `GET /health` — Basic health check
- `GET /health/detailed` — Detailed health (requires auth)
- `GET /health/ready` — Readiness check (for load balancer)
- `GET /health/live` — Liveness check (for orchestrator)

**Health Check Response:**

```json
{
  "status": "UP",
  "components": {
    "database": {"status": "UP", "latency": 5},
    "cache": {"status": "UP", "latency": 2},
    "backgroundJobs": {"status": "UP", "active": 3}
  }
}
```

### Operational Alerts

**Alert Channels:**

- Email: critical alerts
- Slack/Teams: operational alerts
- PagerDuty: P0 incidents (if configured)

**Alert Rules:**

- API error rate > 5%: WARNING
- API error rate > 10%: CRITICAL
- Database connection failure: CRITICAL
- Background job failure > 10%: WARNING
- Disk usage > 90%: CRITICAL
- Memory usage > 85%: WARNING

### Incident Investigation

**Required Information:**

- Timestamp of incident
- Affected service(s)
- Error logs
- Request IDs
- User impact
- Root cause (if known)
- Resolution steps
- Prevention measures

**Post-Incident:**

- Incident report within 24 hours
- Root cause analysis
- Action items to prevent recurrence
- Update monitoring/alerts if needed

---

## 67. Testing

Both developers own tests for their areas.

**Unit** — Business logic.

**Integration** — API + database + authorization.

**Security** — Test:

- Unauthorized requests.
- Role bypass.
- IDOR.
- Invalid tokens.
- Expired sessions.
- Organization isolation.
- Document access.

**E2E**

*User*

```
Register → Login → Complete Profile → Search → View Opportunity
         → Save → Apply → Track
```

*Organization*

```
Register → Verification → Create Opportunity → Submit → Approval
         → Publish → View Applicants → Shortlist → Update Status
```

*Admin*

```
Login → Verification Queue → Review → Moderation
      → Report Investigation → Audit
```

---

## 68. UI State Requirements

Every major page must handle:

`LOADING` · `SUCCESS` · `EMPTY` · `ERROR` · `RETRY` · `UNAUTHORIZED` · `FORBIDDEN` · `NOT FOUND` · `EXPIRED`

> Never leave an empty white page.

---

## 69. No Mock Production Data

**This is mandatory.**

Do not use fake:

- Users
- Organizations
- Opportunities
- Applications
- Analytics
- Testimonials
- Partners
- Investors
- Government endorsements
- Statistics

during production implementation.

> Development seed data may exist only when explicitly identified as development/test data.

---

## 70. Real Data Rule

**Dashboard:**

```
Backend → Database → API → Frontend → Real metric
```

**Never:**

```
Frontend → Hardcoded "2,548 Users"
```

---

## 71. Landing Page Trust Rule

Do not invent:

- Government partnership.
- University partnership.
- Investor.
- Funding.
- Number of users.
- Number of organizations.
- Success stories.
- Testimonials.
- Verification counts.

> If the repository contains no real data, show an appropriate empty or neutral state.

---

## 72. Design System

**Primary:**

| Color Name | Hex |
|---|---|
| Deep Navy | `#0B1F3A` |
| Opportunity Blue | `#1565D8` |
| Growth Green | `#16A34A` |
| Energy Yellow | `#F5B700` |
| Warning Amber | `#F59E0B` |
| Danger Red | `#DC2626` |
| Intelligence Purple | `#7C3AED` |
| Background | `#F7F9FC` |
| White | `#FFFFFF` |
| Primary Text | `#0B1F3A` |
| Secondary Text | `#64748B` |
| Border | `#E2E8F0` |

Use colors semantically.

Do not use all colors simultaneously.

**Avoid:**

- Excessive gradients.
- Excessive glassmorphism.
- Excessive rounded cards.
- Random colorful dashboards.
- Excessive animations.
- Generic AI aesthetics.
- Clutter.

---

## 73. Tanzania-First Experience

TDOP should feel:

Tanzanian · African · Modern · Professional · Trustworthy · Inclusive · Technology-driven · Accessible

> The product should not look like a generic copied Western SaaS dashboard. However, avoid stereotypes.

---

## 74. Internationalization

**Primary languages:** Kiswahili · English

Architecture should allow future languages.

> Do not duplicate entire pages for each language. Use a proper translation architecture.

---

## 75. SEO

Public opportunity pages should eventually be indexable where appropriate.

**SEO requirements:**

- Metadata.
- Canonical.
- Semantic HTML.
- Open Graph.
- Structured data.
- Clean URLs.
- Correct headings.
- Public opportunity content.

---

## 76. Analytics & North Star

**North Star:** *"Successful Opportunity Outcomes"*

The system should eventually understand:

```
DISCOVERY → VIEW → SAVE → APPLY → SHORTLIST → INTERVIEW
          → ACCEPTED → SUCCESSFUL OUTCOME
```

> Not every opportunity type will use exactly the same outcome. The data model should remain extensible.

---

## 77. Data Freshness

Opportunity freshness is a trust feature.

**Track:** Created · Updated · Verified · Last Checked · Deadline · Expired

**Future:**

- Broken-link detection.
- Stale opportunity detection.
- Automatic expiry.
- Source monitoring.
- Duplicate detection.

---

## 78. AI Strategy

AI should enhance TDOP, not become a single point of failure.

**First:**

```
Structured Data → Rule-Based Intelligence → Explainable Recommendations
```

**Later:**

```
Structured Data → Rules → ML / AI → Explainable Intelligence
```

**AI must never invent:**

- Deadlines.
- Eligibility.
- Requirements.
- Organizations.
- Funding.
- Application URLs.
- Opportunity facts.

> If AI is unavailable, TDOP must still work.

---

## 79. Future AI Features

**Priority P3:**

- Semantic search.
- AI recommendations.
- Profile improvement.
- Skill-gap analysis.
- Opportunity matching.
- Application preparation.
- Career guidance.
- Fraud detection.
- Outcome prediction.

> These should not block the core platform.

---

## 80. Help & Support

**User-facing:**

- FAQ.
- Help center.
- Contact support.
- Report opportunity.
- Account help.
- Application guidance.
- Safety guidance.

**Platform side:**

- Support queue.
- Reports.
- Investigation.
- Resolution.
- Audit.

---

## 81. Privacy Controls

Users should eventually manage:

- Profile visibility.
- Notification preferences.
- Document visibility.
- Account settings.
- Communication preferences.
- Data access.
- Account deletion/request.

### Profile Visibility

**User Profile Visibility Options:**

- PUBLIC: Visible to all (name, basic info)
- ORGANIZATIONS_ONLY: Visible to verified organizations only
- PRIVATE: Visible only to self

**Field-Level Visibility:**

| Field | Public | Org Only | Private |
|---|---|---|---|
| Name | Yes | Yes | Self |
| Email | No | On application | Self |
| Phone | No | On application | Self |
| Education | Yes | Yes | Self |
| Skills | Yes | Yes | Self |
| Experience | Yes | Yes | Self |
| Documents | No | On application | Self |

### Sensitive/Private Data Access

**Sensitive Data:**

- Phone number
- Email address
- Date of birth
- National ID
- Financial information

**Access Rules:**

- Sensitive data visible only to:
  - User themselves
  - Organization (after application submitted)
  - Admin (for platform support only)
- Sensitive data never in public API responses
- Sensitive data never in logs
- Sensitive data never in analytics

### CV/Document Access

**Access Rules:**

- Documents visible only to:
  - User themselves
  - Organization (for opportunities they applied to)
  - Admin (for platform support)
- Documents never publicly accessible
- Document access logged for audit
- Signed URLs for temporary access (expire in 1 hour)

### Data Export

**User Data Export:**

- Users can request full data export
- Export includes: profile, applications, saved opportunities, notifications
- Export format: JSON or CSV
- Export delivered via secure download link
- Link expires after 7 days
- Export request logged in audit

**Organization Data Export:**

- Organization admins can export org data
- Export includes: opportunities, applications, team members
- Same delivery mechanism as user export

### Account Deletion/Deactivation

**Deactivation:**

- User can deactivate account
- Deactivated account: hidden from search, login blocked
- Data preserved for 30 days (recovery period)
- After 30 days: soft delete

**Deletion:**

- User can request permanent deletion
- Deletion requires confirmation (email + password)
- Applications preserved (anonymized) for analytics
- Documents permanently deleted
- Audit trail preserved (anonymized)

**Organization Deletion:**

- Only ORG_ADMIN can request
- All opportunities archived (not deleted)
- All applications preserved (anonymized)
- Team members notified

### Data Retention

| Data Type | Retention | Action |
|---|---|---|
| User accounts | Until deletion request | Soft delete after 30 days |
| Applications | 5 years | Anonymize |
| Opportunities | 5 years | Archive |
| Documents | Until deletion request | Permanent delete |
| Audit logs | 7 years | Archive |
| Notifications | 90 days | Delete |
| Analytics | Indefinite | Anonymize |

---

## 82. Opportunity Lifecycle Diagram

```
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
┌──────────────────────┐
│ VERIFICATION /        │
│ MODERATION            │
└──────────┬────────────┘
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
```

---

## 83. Organization Verification Diagram

```
Organization
     │
     ▼
Create Profile
     │
     ▼
Submit Verification
     │
     ▼
Upload Evidence
     │
     ▼
Verification Queue
     │
     ▼
   Review
     │
     ├───────────────┬───────────────┐
     ▼                ▼               ▼
  APPROVE          REJECT         MORE INFO
     │                                │
     ▼                                └──────► Review Again
  VERIFIED
```

---

## 84. Report / Moderation Diagram

```
User Report
     │
     ▼
Report Queue
     │
     ▼
Investigation
     │
     ├───────────────┐
     ▼                ▼
 No Action          Action
                       │
       ┌───────────────┼───────────────┐
       ▼                ▼               ▼
   Warning          Suspend          Archive
       │                │               │
       └────────────────┴───────────────┘
                         │
                         ▼
                       Audit
```

---

## 85. Two-Developer Delivery Map

| Domain | Developer 01 | Developer 02 |
|---|---|---|
| Auth UX | Primary | Security support |
| RBAC | Support | Primary |
| User Profile | Primary | - |
| Documents | Primary | Security |
| Search | Primary | Backend support |
| Discovery | Primary | Backend support |
| Saved | Primary | API support |
| Applications | User side | Organization side |
| Recommendations | Primary | Data support |
| Notifications | User side | Platform events |
| Landing | Primary | API support |
| Organizations | - | Primary |
| Organization Team | - | Primary |
| Opportunities | Discovery | Primary management |
| Verification | - | Primary |
| Moderation | - | Primary |
| Reports | User report UI | Investigation |
| Anti-Fraud | - | Primary |
| Analytics | User-facing | Platform |
| Audit | - | Primary |
| Admin | - | Primary |
| Super Admin | - | Primary |
| Mobile | Primary | Admin support |
| Accessibility | Primary | Admin support |
| SEO | Primary | Backend support |
| Security | Shared | Primary platform |
| Database | Shared | Shared |
| Testing | User flows | Platform flows |

---

## 86. Implementation Phases

### Phase 0 — Repository Audit

Both developers.

> No major coding before this.

**Audit:**

- Repository structure.
- Frontend.
- Backend.
- Database.
- Migrations.
- Authentication.
- Authorization.
- Routes.
- APIs.
- Dashboards.
- Components.
- Tests.
- Environment.
- Existing documentation.
- Git status.
- Existing branches.

**Deliverable**

Each developer produces:

```
TDOP IMPLEMENTATION AUDIT

Developer:
Branch:

Existing:
Completed:
Partial:
Missing:
Broken:

Relevant files:
Existing APIs:
Existing models:
Existing UI:

Dependencies:
Shared changes:
Risks:

Recommended implementation order:
Tests required:
```

---

## 87. Phase 1 — Foundation

**Priority P0.**

- Database
- Auth
- RBAC
- Users
- Organizations
- Security
- Core API contracts

---

## 88. Phase 2 — Opportunity Core

- Opportunity model
- Creation
- Validation
- Verification
- Moderation
- Publishing
- Expiry
- Search
- Discovery

---

## 89. Phase 3 — Application Core

- Apply
- Status
- Timeline
- Documents
- Candidate management
- Notifications

---

## 90. Phase 4 — Dashboards

- User
- Organization
- Organization Member
- Verification Officer
- Moderator
- Admin
- Super Admin

---

## 91. Phase 5 — Trust & Intelligence

- Trust signals
- Reports
- Anti-fraud
- Recommendations
- Deadline intelligence
- Analytics
- Outcome tracking

---

## 92. Phase 6 — Experience Quality

- Landing
- Mobile
- Accessibility
- EN/SW
- SEO
- Performance
- Error handling
- Empty states

---

## 93. Phase 7 — Production Hardening

- Security
- Testing
- Monitoring
- Logging
- Backups
- Migrations
- Observability
- Deployment
- Rollback

---

## 94. Definition of Done

A feature is **NOT** complete simply because a page exists.

A feature is considered Done when applicable:

```
UI → API → Business Logic → Database → Validation → Authorization
   → Error Handling → Loading State → Empty State → Audit → Tests
   → Security Review → Documentation
```

---

## 95. Feature Status Matrix

Use this table during development.

| Feature | Owner | Backend | API | DB | Frontend | Auth | Tests | Status |
|---|---|---|---|---|---|---|---|---|
| Authentication | Dev 01 | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| RBAC | Dev 02 | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| User Profile | Dev 01 | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Documents | Dev 01 | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Organizations | Dev 02 | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Opportunities | Dev 02 | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Search | Dev 01 | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Applications | Both | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Verification | Dev 02 | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Moderation | Dev 02 | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Notifications | Both | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Recommendations | Dev 01 | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Analytics | Dev 02 | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Audit Logs | Dev 02 | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Landing | Dev 01 | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Security | Both | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Testing | Both | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |
| Production | Both | ☐ | ☐ | ☐ | ☐ | ☐ | ☐ | AUDIT |

---

## 96. OpenCode Instructions

When this document is attached to OpenCode, OpenCode MUST understand:

> "You are working inside an existing TDOP repository."

Do not assume the repository is empty.

Do not rebuild the application from scratch.

---

## 97. OpenCode — First Action

Before coding:

1. Inspect repository.
2. Inspect Git status.
3. Identify current branch.
4. Inspect frontend.
5. Inspect backend.
6. Inspect database.
7. Inspect migrations.
8. Inspect authentication.
9. Inspect authorization.
10. Inspect API routes.
11. Inspect dashboards.
12. Inspect shared components.
13. Inspect tests.
14. Inspect environment configuration.
15. Search for existing implementations.
16. Search for duplicate implementations.
17. Compare repository against this PRD.

Then produce an audit.

---

## 98. OpenCode — Developer 01 Mode

If working on `features/developer-01`, OpenCode must prioritize:

- USER
- PROFILE
- DOCUMENTS
- DISCOVERY
- SEARCH
- SAVED
- APPLICATIONS — USER SIDE
- RECOMMENDATIONS
- NOTIFICATIONS — USER SIDE
- USER DASHBOARD
- LANDING
- MOBILE USER EXPERIENCE
- ACCESSIBILITY
- SEO

> Do not start implementing Developer 02 functionality unless explicitly instructed.

---

## 99. OpenCode — Developer 02 Mode

If working on `features/developer-02`, OpenCode must prioritize:

- RBAC
- ORGANIZATIONS
- TEAM
- PERMISSIONS
- OPPORTUNITY MANAGEMENT
- VERIFICATION
- MODERATION
- CANDIDATES
- REPORTS
- ANTI-FRAUD
- TRUST
- ADMIN
- SUPER ADMIN
- ANALYTICS
- AUDIT
- PLATFORM NOTIFICATIONS
- OBSERVABILITY
- PRODUCTION OPERATIONS

> Do not start implementing Developer 01 functionality unless explicitly instructed.

---

## 100. OpenCode — Existing Code Rule

If functionality already exists:

> **DO NOT REBUILD IT.**

Instead:

```
Inspect → Compare → Identify Gap → Improve → Test
```

---

## 101. OpenCode — No Duplicates

Before creating:

- API.
- Route.
- Model.
- Service.
- Controller.
- Component.
- Dashboard.
- Hook.
- Utility.

Search the repository first.

If an equivalent exists, reuse or improve it.

---

## 102. OpenCode — No Fake Implementation

Do not create UI that pretends functionality exists when backend support is missing.

**Bad:**

```
Application Status: 87
```

when there are no real records.

**Bad:**

```
Verified Organizations: 2,430
```

when the database has no such data.

**Bad:**

```
AI Match: 97%
```

when there is no recommendation engine.

**Instead:**

```
No data available yet.
```

or implement the real backend capability.

---

## 103. OpenCode — API Rule

Before creating an API:

- Search existing routes
- Search controllers
- Search services
- Search models
- Search frontend API clients

Then determine whether to:

`reuse` · `extend` · `fix` · `create`

Only create a new API if necessary.

---

## 104. OpenCode — Database Rule

Before changing database:

- Inspect current schema
- Inspect migrations
- Inspect relationships
- Inspect indexes
- Inspect constraints
- Inspect existing data

> Never casually delete or recreate the database.
> Never rewrite migrations already applied in shared environments without a safe migration strategy.

---

## 105. OpenCode — Security Rule

Never bypass:

- Authentication
- Authorization
- Ownership
- Organization isolation
- Document access

> Frontend restrictions are not security. Backend must enforce permissions.

---

## 106. OpenCode — UI Rule

Use the existing design system where available.

**Do not introduce:**

- Random colors.
- Random fonts.
- Unnecessary libraries.
- Duplicate components.
- Excessive animations.
- Generic AI dashboard layouts.

**Reuse existing:**

- Buttons.
- Inputs.
- Cards.
- Tables.
- Modals.
- Navigation.
- Icons.
- Typography.
- Spacing.
- Theme.

---

## 107. OpenCode — Error Handling

Every implementation must consider:

`Loading` · `Success` · `Empty` · `Error` · `Retry` · `Unauthorized` · `Forbidden` · `Not Found` · `Expired` · `Validation failure` · `Network failure`

---

## 108. OpenCode — Final Report

After completing a task, report:

```
TASK
Owner:
Branch:

AUDIT
What already existed:

CHANGES
Files created:
Files modified:
Files deleted:

BACKEND
APIs:
Business logic:

DATABASE
Models:
Migrations:
Indexes/constraints:

FRONTEND
Routes:
Components:
Pages:

AUTHORIZATION
Permissions:
Ownership checks:

TESTS
Unit:
Integration:
E2E:

SECURITY
Checks performed:

KNOWN LIMITATIONS
...

NEXT STEPS
...
```

Never say:

> "Completed successfully"

unless the feature was actually implemented and tested.

---

## 109. Production Checklist

Before TDOP is declared production-ready:

**Core**

- [ ] Authentication.
- [ ] RBAC.
- [ ] User profile.
- [ ] Organizations.
- [ ] Verification.
- [ ] Opportunity lifecycle.
- [ ] Search.
- [ ] Applications.
- [ ] Notifications.
- [ ] Dashboards.
- [ ] Moderation.
- [ ] Reports.
- [ ] Audit.

**Security**

- [ ] Authorization.
- [ ] IDOR protection.
- [ ] Rate limiting.
- [ ] Secure tokens.
- [ ] File security.
- [ ] Secrets.
- [ ] CORS.
- [ ] Headers.
- [ ] Input validation.

**Quality**

- [ ] Unit tests.
- [ ] Integration tests.
- [ ] E2E tests.
- [ ] Error handling.
- [ ] Empty states.
- [ ] Loading states.
- [ ] Accessibility.

**Experience**

- [ ] Mobile.
- [ ] EN/SW.
- [ ] SEO.
- [ ] Performance.
- [ ] Low-bandwidth consideration.

**Operations**

- [ ] Logging.
- [ ] Monitoring.
- [ ] Error tracking.
- [ ] Backups.
- [ ] Migrations.
- [ ] Deployment.
- [ ] Rollback.

**Data**

- [ ] No fake production metrics.
- [ ] No duplicate applications.
- [ ] No unauthorized data.
- [ ] Proper indexes.
- [ ] Proper constraints.
- [ ] Proper lifecycle states.
- [ ] Fresh opportunity data.

---

## 110. Future Roadmap

These features should not block the core platform.

**P3**

- Advanced AI
- Semantic Search
- AI Profile Assistant
- Skill Gap Intelligence
- Advanced Fraud Detection
- PWA
- Native Mobile Apps
- Enterprise API
- Advanced Integrations
- Opportunity Collections
- Advanced Monetization
- East Africa Expansion

---

## 111. Long-Term TDOP Vision

TDOP should eventually evolve from:

**Opportunity Directory**

into:

**Opportunity Intelligence Platform**

and eventually:

**Opportunity Progress Ecosystem**

**The evolution:**

```
DISCOVERY → TRUST → PERSONALIZATION → ACTION → TRACKING
          → OUTCOMES → INTELLIGENCE → PROGRESS
```

---

## 112. Master Product Flow

```
                          TDOP
                            │
                            ▼
                 ┌──────────────────┐
                 │ TRUSTED DISCOVERY │
                 └─────────┬─────────┘
                            │
             ┌──────────────┴──────────────┐
             ▼                             ▼
       INDIVIDUALS                    ORGANIZATIONS
             │                             │
             ▼                             ▼
         PROFILE                     VERIFICATION
             │                             │
             ▼                             ▼
        DISCOVERY                   OPPORTUNITY
             │                        CREATION
             ▼                             │
     RECOMMENDATION                        ▼
             │                       MODERATION
             ▼                             │
          APPLY                            ▼
             │                        PUBLISH
             └─────────────┬───────────────┘
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
              SUCCESSFUL OPPORTUNITY OUTCOME
```

---

## 113. Final Product Principles

**TRUST** — Users should understand why an opportunity is trustworthy.

**CLARITY** — Eligibility, deadlines and application steps should be clear.

**ACTION** — TDOP should help people act, not just browse.

**PROGRESS** — Applications and outcomes should be trackable.

**PRIVACY** — Personal information and documents must be protected.

**ACCESSIBILITY** — The platform should work for diverse users.

**PERFORMANCE** — The platform should work well on realistic mobile networks.

**REAL DATA** — Production interfaces must represent real data.

**EXPLAINABILITY** — Recommendations and trust signals should be understandable.

**SCALABILITY** — Architecture should support growth without unnecessary complexity.

---

## 114. The TDOP Quality Standard

TDOP must feel:

Professional · Modern · Trustworthy · Fast · Clear · Accessible · Tanzania-first · Production-ready · Scalable

It must **NOT** feel like:

Student demo · Generic job board · Template SaaS · Fake-data dashboard · AI-generated prototype · Collection of disconnected pages

---

## 115. Final Rule

The most important rule in this document is:

> "Understand the existing system before changing it."

The second:

> "Do not duplicate what already works."

The third:

> "Never fabricate functionality or data."

The fourth:

> "Backend authorization is authoritative."

The fifth:

> "A feature is not complete until it works across the necessary UI, API, database, security and testing layers."

---

## 116. Starting Point

The first implementation task is **NOT**:

> Build everything.

The first task is:

```
AUDIT THE REPOSITORY
        ↓
    CLASSIFY (DONE / PARTIAL / MISSING / BROKEN)
        ↓
    PRIORITIZE
        ↓
    IMPLEMENT
        ↓
    TEST
        ↓
    VERIFY
        ↓
    MERGE
```

---

## 117. TDOP Master Equation

```
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
```

---

## 118. Final Team Structure

```
                             TDOP
                               │
                               ▼
                             main
                               │
              ┌────────────────┴────────────────┐
              │                                 │
              ▼                                 ▼
       Developer 01                       Developer 02
              │                                 │
      USER PLATFORM                    PLATFORM OPERATIONS
      DISCOVERY                        ORGANIZATIONS
      PROFILE                          OPPORTUNITIES
      APPLICATIONS                     VERIFICATION
      RECOMMENDATIONS                  MODERATION
      LANDING                          TRUST
      MOBILE                           ADMIN
      ACCESSIBILITY                    ANALYTICS
      SEO                              AUDIT
                                        SECURITY
                                        OBSERVABILITY
              │                                 │
              └────────────────┬────────────────┘
                                ▼
                        CODE REVIEW / PR
                                │
                                ▼
                              main
```

---

## 119. TDOP Final Vision

```
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
```

**TDOP = Discovery + Trust + Intelligence + Action + Progress + Ecosystem**

---

## 120. Developer Quick Reference — Short Version

Hii ni short reference ya kazi za kila developer. Developer anatakiwa kusoma sehemu hii pamoja na sections za kina za `README_PRD.md`.

### 👨‍💻 Developer 01 — User / Seeker Experience

**Branch:** `features/developer-01`

**Main responsibility**

Developer 01 anasimamia upande wa User/Seeker, discovery, application experience na public/user-facing experience.

**Kazi kuu**

- [ ] Audit existing user/auth implementation
- [ ] Login
- [ ] Register
- [ ] Forgot password
- [ ] Reset password
- [ ] Refresh token UX/integration
- [ ] Logout/session handling
- [ ] Current user "/me"
- [ ] User profile
- [ ] Education
- [ ] Skills
- [ ] Experience
- [ ] Interests
- [ ] Career goals
- [ ] Profile completion
- [ ] Profile visibility
- [ ] CV/document management
- [ ] Certificates
- [ ] Academic documents
- [ ] Portfolio documents
- [ ] Secure document access
- [ ] Opportunity discovery
- [ ] Opportunity listing
- [ ] Opportunity details
- [ ] Search
- [ ] Filters
- [ ] Sorting
- [ ] Pagination
- [ ] Saved opportunities
- [ ] Opportunity comparison
- [ ] Application submission
- [ ] Application tracking
- [ ] Application status
- [ ] Application timeline
- [ ] Application withdrawal
- [ ] Duplicate application prevention UX
- [ ] Recommended opportunities
- [ ] Explainable recommendations
- [ ] Deadline reminders UI
- [ ] User notifications
- [ ] User notification preferences
- [ ] User dashboard
- [ ] Public landing page
- [ ] Public opportunity discovery
- [ ] Mobile responsiveness
- [ ] Accessibility
- [ ] English/Kiswahili user experience
- [ ] SEO for public pages
- [ ] User-side loading/error/empty/success states

**Developer 01 should NOT own**

- Organization RBAC implementation
- Organization team permissions
- Verification Officer workflows
- Moderator workflows
- Admin/Super Admin management
- Organization candidate management
- Organization opportunity approval
- Platform-wide moderation
- Platform audit infrastructure
- Platform security policies

---

### 👨‍💻 Developer 02 — Organization / Admin / Trust

**Branch:** `features/developer-02`

**Main responsibility**

Developer 02 anasimamia Organizations, RBAC, opportunity management, verification, moderation, administration, trust na platform operations.

**Kazi kuu**

- [ ] Audit existing authorization
- [ ] RBAC
- [ ] Role permissions
- [ ] Organization roles
- [ ] Organization Admin
- [ ] Organization Member
- [ ] Organization profile
- [ ] Organization team
- [ ] Team invitations
- [ ] Team permissions
- [ ] Organization ownership
- [ ] Organization verification
- [ ] Verification documents
- [ ] Verification approval
- [ ] Verification rejection
- [ ] Request-more-information workflow
- [ ] Verification history
- [ ] Opportunity creation
- [ ] Opportunity editing
- [ ] Draft opportunities
- [ ] Opportunity submission
- [ ] Opportunity validation
- [ ] Opportunity verification
- [ ] Opportunity approval
- [ ] Opportunity publishing
- [ ] Opportunity suspension
- [ ] Opportunity archival
- [ ] Opportunity restoration
- [ ] Opportunity expiry
- [ ] Official source/provenance
- [ ] Eligibility rules
- [ ] Organization applicant management
- [ ] Applicant search/filter
- [ ] Shortlisting
- [ ] Applicant notes
- [ ] Applicant communication
- [ ] Candidate funnel
- [ ] Organization analytics
- [ ] Verification Officer dashboard
- [ ] Moderator dashboard
- [ ] Admin dashboard
- [ ] Super Admin dashboard
- [ ] Reports
- [ ] Report investigation
- [ ] Anti-fraud
- [ ] Duplicate detection
- [ ] Suspicious opportunity detection
- [ ] Suspicious organization detection
- [ ] Trust/risk scoring
- [ ] Platform analytics
- [ ] Audit logs
- [ ] Platform notification infrastructure
- [ ] Deadline automation
- [ ] Platform observability
- [ ] Production operations
- [ ] Security hardening

**Developer 02 should NOT own**

- User profile UI implementation
- User dashboard UI
- User application UX
- User discovery UX
- User recommendation UI
- Public landing page design
- User-side document UX

---

### 🤝 Shared Responsibilities

Baadhi ya vitu haviwezi kugawanywa completely kwa developer mmoja.

Developers wote wanapaswa kushirikiana kwenye:

- [ ] Database schema
- [ ] Database migrations
- [ ] Core API contracts
- [ ] Authentication architecture
- [ ] RBAC contracts
- [ ] User model
- [ ] Organization model
- [ ] Opportunity model
- [ ] Application model
- [ ] Notification model
- [ ] Audit model
- [ ] File/document architecture
- [ ] Global routing
- [ ] Shared UI components
- [ ] Design system
- [ ] Environment configuration
- [ ] Security architecture
- [ ] Error handling standards
- [ ] Testing strategy
- [ ] Production deployment
- [ ] Performance
- [ ] Caching
- [ ] Monitoring
- [ ] Backup/recovery

**Shared rule**

Developer mmoja akibadilisha shared architecture:

```
CHECK → DISCUSS → IMPLEMENT → TEST → DOCUMENT
```

> Usibadilishe shared contract bila kuangalia impact kwa developer mwingine.

---

### 🧭 Simple Ownership Map

```
                       TDOP PLATFORM
                             │
              ┌──────────────┴──────────────┐
              │                             │
              ▼                             ▼
       DEVELOPER 01                   DEVELOPER 02
       User / Seeker              Organization / Admin
              │                             │
       ├── Auth UX                   ├── RBAC
       ├── Profile                   ├── Organizations
       ├── Education                 ├── Teams
       ├── Skills                    ├── Permissions
       ├── Experience                ├── Verification
       ├── Documents                 ├── Opportunities
       ├── Discovery                 ├── Applicants
       ├── Search                    ├── Moderation
       ├── Saved                     ├── Reports
       ├── Applications              ├── Anti-Fraud
       ├── Recommendations           ├── Analytics
       ├── Notifications             ├── Audit
       ├── User Dashboard            ├── Admin
       ├── Landing Page              ├── Super Admin
       ├── Mobile UX                 ├── Deadline Engine
       ├── Accessibility             ├── Observability
       ├── i18n                      └── Production
       └── SEO
```

---

### 🔄 Core TDOP Flow

```
USER
  │
  ▼
DISCOVER → UNDERSTAND → PREPARE → APPLY → TRACK → SUCCESS
```

**Backend/platform side:**

```
ORGANIZATION → CREATE OPPORTUNITY → VALIDATE → VERIFY / MODERATE
             → PUBLISH → USER DISCOVERS → APPLICATION → REVIEW
             → SHORTLIST / DECISION → OUTCOME
```

---

### 🗂️ Quick Feature Ownership Table

| Feature | Dev 01 | Dev 02 | Shared |
|---|---|---|---|
| Authentication UX | ✅ | | |
| User Profile | ✅ | | |
| Education/Skills | ✅ | | |
| Experience | ✅ | | |
| Documents UX | ✅ | | |
| Search | ✅ | | |
| Discovery | ✅ | | |
| Saved Opportunities | ✅ | | |
| Applications — User Side | ✅ | | |
| Recommendations | ✅ | | |
| User Notifications | ✅ | | |
| User Dashboard | ✅ | | |
| Landing Page | ✅ | | |
| Mobile User UX | ✅ | | |
| Accessibility | ✅ | | |
| i18n User Side | ✅ | | |
| SEO | ✅ | | |
| RBAC | | ✅ | 🔗 |
| Organizations | | ✅ | |
| Organization Teams | | ✅ | |
| Permissions | | ✅ | 🔗 |
| Opportunity Management | | ✅ | 🔗 |
| Opportunity Lifecycle | | ✅ | 🔗 |
| Candidate Management | | ✅ | |
| Organization Verification | | ✅ | |
| Moderation | | ✅ | |
| Admin | | ✅ | |
| Super Admin | | ✅ | |
| Reports | | ✅ | |
| Anti-Fraud | | ✅ | |
| Platform Analytics | | ✅ | |
| Audit Logs | | ✅ | |
| Deadline Engine | | ✅ | |
| Platform Notifications | | ✅ | 🔗 |
| Database | | | ✅ |
| Core APIs | | | ✅ |
| Security Architecture | | | ✅ |
| Testing Strategy | | | ✅ |
| Deployment | | | ✅ |
| Monitoring | | | ✅ |

---

### 🚦 Priority Order

Kila developer afanye kazi kwa mpangilio huu isipokuwa kuna dependency inayolazimisha tofauti.

**P0 — Foundation / Critical**

```
Audit existing system → Authentication → Database integrity
                       → RBAC / Authorization → Core API contracts
```

**P1 — Core Product**

- User Profile
- Organization
- Opportunity
- Search
- Application
- Verification
- Moderation
- Dashboards

**P2 — Intelligence & Trust**

- Recommendations
- Notifications
- Deadline Intelligence
- Reports
- Anti-Fraud
- Analytics
- Audit

**P3 — Production Quality**

- Security Hardening
- Testing
- Performance
- Caching
- Accessibility
- i18n
- SEO
- Observability
- Mobile

---

### 🧪 Every Feature Must Pass

Kabla developer kusema feature imekamilika:

- [ ] Existing implementation inspected
- [ ] Requirement understood
- [ ] API verified
- [ ] Database verified
- [ ] Authorization verified
- [ ] Validation implemented
- [ ] Error state handled
- [ ] Loading state handled
- [ ] Empty state handled
- [ ] Success state handled
- [ ] Mobile checked
- [ ] Accessibility checked
- [ ] Security checked
- [ ] Tests added/updated
- [ ] Existing functionality still works
- [ ] No mock/fake production data
- [ ] No duplicate implementation
- [ ] Documentation updated

---

### 🚫 Absolute Rules

Developers **MUST NOT**:

- [ ] Rewrite the whole project unnecessarily
- [ ] Delete working features
- [ ] Create duplicate APIs
- [ ] Create duplicate models
- [ ] Hardcode production statistics
- [ ] Use fake opportunities
- [ ] Use fake users/applications
- [ ] Bypass backend authorization
- [ ] Trust frontend permissions
- [ ] Expose private documents publicly
- [ ] Commit secrets
- [ ] Modify another developer's work blindly
- [ ] Force-push shared branches
- [ ] Reset/rewrite another developer's commits
- [ ] Change shared architecture without coordination
- [ ] Claim a feature is complete without testing

---

### 🤖 OpenCode Quick Instruction

When this repository is opened in OpenCode:

1. Read `README_PRD.md` completely.
2. Identify the active developer branch.
3. Audit the existing repository.
4. Compare implementation against `README_PRD.md`.
5. Identify completed / partial / missing features.
6. Work ONLY within the assigned developer scope.
7. Reuse existing architecture where possible.
8. Do not duplicate existing functionality.
9. Implement incrementally.
10. Test every meaningful change.
11. Verify security and authorization.
12. Report exactly what was changed.
13. Report what remains.
14. Never claim completion without evidence.

**Developer 01 mode**

```
ACTIVE OWNER: Developer 01
BRANCH: features/developer-01
PRIMARY AREA: User / Seeker Experience
REFERENCE: README_PRD.md → Developer 01 sections
```

**Developer 02 mode**

```
ACTIVE OWNER: Developer 02
BRANCH: features/developer-02
PRIMARY AREA: Organization / Admin / Trust / Platform Operations
REFERENCE: README_PRD.md → Developer 02 sections
```

---

### 🏁 Final Team Rule

TDOP is one product, not two separate projects.

```
Developer 01
     +
Developer 02
     ↓
Shared Architecture
     ↓
One Backend
     ↓
One Database
     ↓
One Design System
     ↓
One TDOP Platform
```

The goal is **not**:

> "Developer 01 amalize sehemu yake."

or

> "Developer 02 amalize sehemu yake."

The goal is:

> "Build one secure, trusted, scalable and production-ready TDOP platform without breaking each other's work."

**Master Development Principle**

```
UNDERSTAND → AUDIT → PLAN → IMPLEMENT → TEST → VERIFY → HARDEN → DOCUMENT
```

Never:

```
GUESS → REWRITE → BREAK → PATCH
```

**TDOP standard:**

> "Real Data. Real Security. Real Workflows. Real Validation. Real Testing. No Fake Completion."
