# TDOP Production Deployment Checklist

## Pre-Deployment

### Environment Variables (Required)
```bash
# Database
POSTGRES_DB=tdop
POSTGRES_USER=tdop
POSTGRES_PASSWORD=<strong-password>

# Security
JWT_SECRET=<64-char-random-secret>

# Application
APP_URL=https://your-domain.com/api/v1
FRONTEND_URL=https://your-domain.com
CORS_ALLOWED_ORIGINS=https://your-domain.com

# Email (Optional - for notifications)
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=<email>
MAIL_PASSWORD=<app-password>

# Storage
STORAGE_DIR=uploads
MAX_FILE_SIZE=10485760
```

### Infrastructure
- [ ] PostgreSQL 16+ running and accessible
- [ ] Docker 20+ installed
- [ ] Docker Compose v2+ installed
- [ ] SSL certificate configured (for production domain)
- [ ] DNS configured for production domain

### Security
- [ ] JWT_SECRET is a strong random string (64+ chars)
- [ ] POSTGRES_PASSWORD is strong and unique
- [ ] No hardcoded secrets in codebase
- [ ] CORS_ALLOWED_ORIGINS set to production domain only
- [ ] Rate limiting enabled (RateLimitFilter)

## Deployment Steps

### 1. Clone and Configure
```bash
git clone https://github.com/felix202422/Tanzanian_Opportunities.git
cd Tanzanian_Opportunities/TDOP-infra
cp .env.example .env
# Edit .env with production values
```

### 2. Start Services
```bash
docker compose up -d
```

### 3. Verify Health
```bash
# Check backend health
curl http://localhost:8080/api/v1/public/health

# Check database
docker compose exec tdop-postgres pg_isready -U tdop

# Check all services
docker compose ps
```

### 4. Run Database Migrations
Flyway runs automatically on startup. Verify:
```bash
docker compose logs tdop-backend | grep -i flyway
```

### 5. Create Initial Admin
```bash
# Register first admin via API or use seed data
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@tdop.co.tz","password":"<secure>","fullName":"Admin","role":"SUPER_ADMIN"}'
```

## Post-Deployment Verification

### Authentication
- [ ] Registration works
- [ ] Login returns JWT tokens
- [ ] Token refresh works
- [ ] Logout invalidates session
- [ ] Password reset flow works

### Authorization
- [ ] SEEKER cannot access admin endpoints
- [ ] ORGANIZATION cannot access super admin endpoints
- [ ] ADMIN cannot access super admin governance
- [ ] Ownership checks prevent cross-user resource access

### Core Features
- [ ] Opportunity browse/search works
- [ ] Application submission works
- [ ] Organization profile management works
- [ ] Notification delivery works
- [ ] File upload/download works

### Admin Features
- [ ] User management works
- [ ] Organization verification works
- [ ] Opportunity moderation works
- [ ] Report investigation works
- [ ] Audit log viewing works

### Security Headers
```bash
curl -I http://localhost:8080/api/v1/public/health
# Verify: X-Frame-Options: DENY
# Verify: X-Content-Type-Options: nosniff
# Verify: X-XSS-Protection: 1; mode=block
# Verify: Referrer-Policy: strict-origin-when-cross-origin
```

## Rollback Procedure

### If backend fails to start:
```bash
docker compose logs tdop-backend
# Fix the issue, then:
docker compose restart tdop-backend
```

### If database migration fails:
```bash
# Flyway tracks applied migrations in flyway_schema_history
# Check logs for the failed migration
docker compose logs tdop-backend | grep -i flyway

# If needed, manually fix the database state
docker compose exec tdop-postgres psql -U tdop -d tdop
```

### Full rollback:
```bash
docker compose down
# Restore database backup
# Revert code changes
git checkout <previous-commit>
docker compose up -d
```

## Backup Strategy

### Database Backup
```bash
# Manual backup
docker compose exec tdop-postgres pg_dump -U tdop tdop > backup_$(date +%Y%m%d).sql

# Restore
docker compose exec -T tdop-postgres psql -U tdop tdop < backup_20240101.sql
```

### File Storage Backup
```bash
# Backup uploads directory
tar -czf uploads_backup_$(date +%Y%m%d).tar.gz uploads/
```

## Monitoring

### Health Checks
- Backend: `GET /api/v1/public/health`
- Database: `pg_isready -U tdop`
- Docker: `docker compose ps`

### Logs
```bash
# All services
docker compose logs -f

# Backend only
docker compose logs -f tdop-backend

# Database only
docker compose logs -f tdop-postgres
```

### Key Metrics to Monitor
- Response time (target: <500ms)
- Error rate (target: <1%)
- Database connections (target: <80% pool)
- Memory usage (target: <80%)
- Disk usage (target: <80%)
