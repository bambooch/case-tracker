# Case Tracker

Case Tracker is a full-stack case management demo built with React, Spring Boot, and PostgreSQL.

The repository is structured as a small monorepo:

- `frontend/`: React 19 + Vite UI
- `backend/`: Spring Boot 4 API
- `docker-compose.yml`: local PostgreSQL for development
- `docker-compose.prod.yml`: production stack with PostgreSQL, app, and Nginx
- `Makefile`: common local and production commands

## Stack

- Frontend: React, TypeScript, Vite, Vitest
- Backend: Spring Boot 4, Java 17, Maven, JPA
- Database: PostgreSQL 16
- Production runtime: Docker Compose + Nginx

## Repository Layout

```text
.
├── backend/
├── frontend/
├── docker-compose.yml
├── docker-compose.prod.yml
├── Makefile
└── DEPLOYMENT_SPRING.md
```

## Prerequisites

For local development you will typically want:

- Java 17
- Node.js 22
- Docker Desktop or Docker Engine

## Local Development

### 1. Start the database

From the repository root:

```bash
make db
```

This starts the local PostgreSQL container from `docker-compose.yml`.

Default local database settings:

- database: `case_tracker`
- user: `case_tracker`
- password: `case_tracker`
- port: `5432`

### 2. Run the backend

```bash
cd backend
./mvnw spring-boot:run
```

On Windows:

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

### 3. Run the frontend

In a second terminal:

```bash
cd frontend
npm install
npm run dev
```

The Vite dev server proxies `/api` requests to the backend on `localhost:8080`.

## Tests

### Backend tests

```bash
cd backend
./mvnw test
```

On Windows:

```powershell
cd backend
.\mvnw.cmd test
```

### Frontend tests

```bash
cd frontend
npm ci
npm test
```

### Frontend production build

```bash
cd frontend
npm run build
```

## Helpful Commands

From the repository root:

```bash
make help
make db
make build
make up
make down
make restart
make deploy
make logs
make ps
make backup
```

## Environment

For production, copy `.env.example` to `.env` and set real values:

```bash
cp .env.example .env
```

Important rules:

- `POSTGRES_PASSWORD` and `SPRING_DATASOURCE_PASSWORD` must match
- `SPRING_DATASOURCE_URL` must use `postgres` as the host inside Docker Compose

## Deployment

Production deployment uses Docker Compose with three services:

- `postgres`
- `app`
- `nginx`

Short version:

1. Prepare a VM with Docker and Git.
2. Clone this repository on the server, usually into `/opt/case-tracker`.
3. Create a server-side `.env` from `.env.example`.
4. Build and start the stack with:

```bash
make build
make up
```

5. Verify the stack:

```bash
make ps
curl http://localhost/api/cases
```

6. For updates:

```bash
make deploy
```

### GitHub Actions CD

This repository includes `/.github/workflows/deploy.yml`, which:

- runs backend tests
- runs frontend tests and build
- deploys to the server over SSH after checks pass

Required GitHub secrets:

- `DEPLOY_HOST`
- `DEPLOY_PORT`
- `DEPLOY_USER`
- `DEPLOY_APP_DIR`
- `DEPLOY_SSH_KEY`
- `DEPLOY_KNOWN_HOSTS`

The deploy workflow assumes the repository already exists on the server and that `.env` is already present there.

## Notes

- Production Nginx configuration is selected automatically depending on whether Let's Encrypt certificates exist for `PUBLIC_DOMAIN`.
- The backend health endpoint is `/actuator/health`.
- PostgreSQL and the Spring datasource passwords must stay in sync.