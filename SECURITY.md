# Security Policy

## Project status

TDOP is in Phase 0 and has no released software, running service, production environment or supported application version. Security requirements are being recorded before implementation.

## Reporting a vulnerability

Please do **not** publish exploit details, credentials, personal data or sensitive evidence in a public issue or pull request. Use GitHub’s private vulnerability reporting for this repository if the **Report a vulnerability** option is enabled. Its availability has not yet been configured or verified. If it is unavailable, do not disclose sensitive details publicly; contact the maintainer through a verified private channel after one is published.

Before public application use, maintainers must enable and test private reporting, publish a reliable security contact route, and document supported release versions and response expectations. No response-time commitment is made at this planning stage.

When reporting, include the affected component/version, impact, concise reproduction steps and suggested mitigation if known. Avoid including real user data or live secrets.

## Security principles

- Enforce authentication, role permissions and resource ownership in the backend.
- Hash passwords with a supported adaptive password encoder; never store plaintext credentials.
- Use short-lived access JWTs and rotating, revocable refresh credentials; store refresh-token hashes, not plaintext tokens.
- Validate input and uploads, minimize collected data, and avoid secrets or sensitive personal information in logs.
- Use least-privilege service accounts, environment-managed secrets, HTTPS in production and an explicit CORS allowlist.
- Audit sensitive administrative actions and test authorization boundaries.
- Review dependencies and container images, and maintain a patch and disclosure process before production.

These are design requirements, not evidence that a deployed system is secure. Security testing and production controls remain future phase acceptance criteria.
