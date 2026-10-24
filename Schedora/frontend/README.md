# Schedora — Full Stack Java Appointment Booking Platform

Schedora is a React + Vite frontend with a Java 17 / Spring Boot REST API and a relational database. It is designed as an FSJP learning project with separate customer and business-owner workflows.

## Main features
- Separate customer and business-owner registration/login.
- Passwords stored as BCrypt hashes (never plain text).
- Persistent user accounts, services and bookings.
- Business owners can publish/remove their own services and view/update bookings for their business.
- Customers can search/filter the service catalogue and view their own appointments.
- Booking availability endpoint shows booked slots for a selected service/date.
- Backend validates date format, rejects past dates and past same-day slots, validates time-slot choices, and checks for existing active bookings before saving.
- Owner dashboard includes appointment, pending, service and confirmed-booking summary cards.
- MySQL is the default database; PostgreSQL support is included for a hosted database such as Supabase.

## Requirements
- JDK 17+
- Maven 3.8+
- Node.js 18+ and npm
- Either local MySQL Server or a PostgreSQL database (for example, Supabase)

## 1. Configure the database

### Option A — Local MySQL (default)
1. Start the MySQL Server service.
2. Create a database named `schedora_db` (or let the configured URL create it if your MySQL user has permission).
3. Set `DB_USERNAME` and `DB_PASSWORD` environment variables, or edit `backend/src/main/resources/application.properties` for local development. Do not commit real passwords.

Example URL:
`jdbc:mysql://localhost:3306/schedora_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`

### Option B — Supabase PostgreSQL
You do not need a local MySQL server when using Supabase. In your Supabase project, open **Connect** and get the PostgreSQL connection details. Use the JDBC form for `DB_URL` (the exact host and connection method depend on your Supabase project):

`jdbc:postgresql://YOUR_HOST:5432/postgres?sslmode=require`

Set environment variables before starting Spring Boot:
- `DB_URL` = the PostgreSQL JDBC URL from your Supabase connection details
- `DB_USERNAME` = database username provided by Supabase
- `DB_PASSWORD` = database password for that database user

Use the PostgreSQL driver included in `pom.xml`. Keep database credentials private and never place them in React code. For a deployed backend, use your host's secret/environment-variable settings. The first run creates/updates JPA tables because `spring.jpa.hibernate.ddl-auto=update` is configured for this prototype; use migrations for production. Existing MySQL data is not automatically migrated to Supabase—you must export/import it if you need to preserve it.

**Authentication note:** this project currently uses its own Spring Boot registration/login endpoints and BCrypt password hashes. Adding a Supabase database does not automatically enable Supabase Auth. If you want Supabase Auth, the authentication flow must be separately migrated and JWTs must be verified by the backend.

## 2. Run the backend
Open a terminal in `Schedora/backend` and run:

```bash
mvn spring-boot:run
```

Check `http://localhost:8080/api/health`. It should return a JSON response with status `UP`.

On Windows, set environment variables in the same Command Prompt before running Maven, for example:

```bat
set DB_URL=jdbc:mysql://localhost:3306/schedora_db?createDatabaseIfNotExist=true^&useSSL=false^&allowPublicKeyRetrieval=true^&serverTimezone=UTC
set DB_USERNAME=root
set DB_PASSWORD=your_actual_password
mvn spring-boot:run
```

For Supabase, replace those values with the connection information from your project.

## 3. Run the frontend
Open a second terminal in `Schedora/frontend`:

```bash
npm install
npm run dev
```

Open the Vite URL printed in the terminal, usually `http://localhost:5173`. By default, the frontend calls `http://localhost:8080/api`. To point it at a deployed backend, copy `.env.example` to `.env` and set `VITE_API_URL` to the backend base URL (without `/api`). Restart Vite after changing environment variables.

## 4. Test the workflow
1. Register a **Business owner** account.
2. Publish a service.
3. Sign out and register a separate **User** account.
4. Select the service and date. The app loads availability from the backend.
5. Book a time slot and verify it appears in the customer dashboard.
6. Sign in as the owner to view the customer's booking details and update the status.
7. Attempt to book the same active slot again; the backend should reject it.

## API highlights
- `GET /api/health` — health check
- `GET /api/services` — public service catalogue
- `GET /api/services/{id}/availability?date=YYYY-MM-DD` — slot availability for a date
- `POST /api/auth/register`, `POST /api/auth/login`, `POST /api/auth/logout`
- `POST /api/bookings`, `GET /api/bookings`, `PUT /api/bookings/{id}/status`

## Important prototype limitations
- API tokens are currently held in backend memory. They expire when the backend restarts, and users must sign in again. Use Spring Security with signed/expiring JWTs or persistent sessions before public deployment.
- The booking endpoint checks conflicts before saving, but strict protection against simultaneous requests across multiple backend instances requires a database-level slot reservation/locking strategy.
- Email verification, password reset, automated email/SMS reminders, payment processing, and full business-hours configuration are not implemented yet.
- `ddl-auto=update` is convenient for development; production should use controlled migrations and backups.
- This project has not been deployed or security-audited. Test all workflows with non-sensitive test accounts before presenting.
