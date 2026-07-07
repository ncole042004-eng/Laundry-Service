# Laundry Service POS

A desktop point-of-sale system for a small laundry shop, built with Java Swing and MySQL. A TESDA Programming (Java) NC III project.

The app covers the day-to-day flow of a laundry counter: logging in as staff, taking a new order, tracking it through wash and pickup, keeping a customer list, and pulling basic revenue reports.

<!-- screenshot: main dashboard / home screen -->

## Features

- **Login** — staff sign in with a username/password; passwords are hashed with bcrypt (jBCrypt), not stored in plain text.
<!-- screenshot: login screen -->

- **New Order** — pick a customer, weigh the load, and save an order. Each order gets an auto-generated claim number (`LS-YYMMDD-NNN`) that doubles as a pickup stub, since most small shops here hand out a written stub rather than a printed receipt.
<!-- screenshot: new order screen -->

- **Customers** — search by phone number, register walk-ins, and deactivate customers without deleting their order history.
<!-- screenshot: customers screen -->

- **Order List** — a table of every order (Orders joined with Customers and Services), used as the entry point for updating an order's status.
<!-- screenshot: order list screen -->

- **Update Status** — move an order through `Pending → Processing → Ready → Claimed`, one step at a time. An order can't be marked Claimed until it's marked Paid.
<!-- screenshot: update status screen -->

- **Home** — a dashboard of today's numbers: earnings, order count, and how many loads are currently active or ready for pickup.
<!-- screenshot: home dashboard, close-up on the metric cards -->

- **Reports** — revenue and per-employee order totals over a date range.
<!-- screenshot: reports screen -->

## Tech stack

| Layer | Choice |
|---|---|
| Language / UI | Java, Swing (JFrame/JPanel, built in NetBeans' GUI editor) |
| Build | Maven |
| Database | MySQL, run locally through XAMPP |
| DB driver | mysql-connector-j (Maven dependency, not a manually linked jar) |
| Look and feel | FlatLaf |
| Password hashing | jBCrypt |

## Database

MySQL, five tables: `Employees`, `Customers`, `Services`, `Orders`, and their relationships enforced with foreign keys (`ON DELETE SET NULL` for customer/employee references, `ON DELETE RESTRICT` for service, so an order never silently loses its history). A few things worth knowing if you're reading the schema:

- `price_at_order` stores a snapshot of the service's price at the time the order was placed, so old orders stay accurate even if prices change later.
- Customers are soft-deleted (`is_active` flag) instead of removed, so past orders still resolve to a name.
- `order_status` is an ENUM (`Pending`, `Processing`, `Ready`, `Claimed`, `Cancelled`) and `payment_status` is a separate ENUM (`Unpaid`, `Paid`) — these track two different things and aren't meant to collapse into one status field.

Full schema is in `init/init_db.sql`, written to be safely re-runnable (`IF NOT EXISTS`, `INSERT IGNORE`).

## Getting started

**Prerequisites**
- JDK 25
- Maven
- XAMPP (or any local MySQL server) with phpMyAdmin

**Setup**

1. Clone the repo and start MySQL through XAMPP.
2. Run `init/init_db.sql` in phpMyAdmin (or the MySQL CLI) to create `laundry_service_db` and its tables.
3. Optionally run `init/seed_full.sql` afterward for sample data to test with.
4. `DBConnection.java` points at `jdbc:mysql://localhost:3306/laundry_service_db` with user `root` and no password — the default XAMPP setup. Change those three constants if your local MySQL is configured differently.
5. Open the `LaundryService/` folder in NetBeans (or run `mvn compile exec:java` from that folder) to build and launch.

## License

A TESDA Programming (Java) NC III project, not published under an open-source license.
