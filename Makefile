.DEFAULT_GOAL := help

.PHONY: help db-up db-down compose-config backend-run backend-run-seed backend-test frontend-install frontend-dev frontend-lint frontend-typecheck frontend-format frontend-format-check frontend-build check

help: ## Show available development tasks
	@grep -E '^[a-zA-Z0-9_-]+:.*## ' Makefile | sort | awk 'BEGIN {FS = ":.*## "} {printf "%-24s %s\n", $$1, $$2}'

db-up: ## Start the local PostgreSQL service
	docker compose up -d postgres

db-down: ## Stop local services (keeps the named database volume)
	docker compose down

compose-config: ## Validate Compose configuration
	docker compose config

backend-run: ## Run the Spring Boot backend
	set -a; if [ -f .env ]; then . ./.env; fi; set +a; cd backend && ./mvnw spring-boot:run

backend-test: ## Verify and test the backend (loads .env so the test database is reachable)
	set -a; if [ -f .env ]; then . ./.env; fi; set +a; cd backend && ./mvnw -B verify

backend-run-seed: ## Run the backend with development seed data (seed profile)
	set -a; if [ -f .env ]; then . ./.env; fi; set +a; cd backend && SPRING_PROFILES_ACTIVE=seed ./mvnw spring-boot:run

frontend-install: ## Install frontend dependencies from the lockfile
	npm ci --prefix frontend

frontend-dev: ## Start the Vite development server
	npm run dev --prefix frontend

frontend-lint: ## Lint frontend source
	npm run lint --prefix frontend

frontend-typecheck: ## Type-check frontend source
	npm run typecheck --prefix frontend

frontend-format: ## Format frontend source
	npm run format --prefix frontend

frontend-format-check: ## Check frontend formatting
	npm run format:check --prefix frontend

frontend-build: ## Build the frontend for production
	npm run build --prefix frontend

check: frontend-install backend-test frontend-lint frontend-typecheck frontend-format-check frontend-build ## Install dependencies and run non-Docker project checks
