# LAUNDRY SERVICE POS — FULL PROJECT CONTEXT

## 1. PROJECT OVERVIEW

TESDA NC III capstone project. A Laundry Service POS system built in Java (NetBeans, Swing JFrame/JPanel), Maven-managed, backed by MySQL via XAMPP/phpMyAdmin. Team of 7 students. The user (addressed here) is the team lead / Product Owner, acts as sole technical authority over architecture, database schema, and Git integration. Most teammates are non-technical or unenthusiastic; the user manages this via a "Hub and Spoke" / technical guardianship model rather than collaborative architecture decisions.

## 2. STACK

- IDE: NetBeans, GUI built with JFrame/JPanel forms (.form + .java pairs)
- Build tool: Maven (pom.xml manages dependencies, NOT Ant — this was corrected mid-project)
- Database: MySQL via XAMPP, administered through phpMyAdmin
- Driver: mysql-connector-j, added via Maven pom.xml dependency (groupId com.mysql, artifactId mysql-connector-j), NOT a manually linked .jar
- Version control: GitHub Desktop (no CLI), team pulls/pushes through GUI only
- DB connection string: jdbc:mysql://localhost:3306/laundry_service_db, user root, no password (local XAMPP only, no remote/shared DB — avoids single point of failure)

## 3. DATABASE EVOLUTION (decisions made, in order)

1. Started with price_per_kg flat rate.
2. Changed to a "Minimum Load" model: base_weight_kg, base_price, excess_price_per_kg (₱175 up to 7kg + ₱30/excess kg).
3. Simplified to a single flat fixed_price since every load is capped at exactly 7kg. weight_kg is still stored (for >7kg validation popup and future flexibility), but pricing itself is flat.
4. Login/Users table was originally cut entirely (team voted against it), then reinstated as an Employees table later in the project for accountability/audit purposes (who processed which order). There are no distinct roles (no admin vs cashier) — just a flat employee_id reference for tracking.
5. Currency fields upgraded from DECIMAL(10,2) to DECIMAL(19,4) for proper financial precision.
6. order_status ENUM evolved: started as (Pending, Ready, Claimed), team debated dropping 'Processing' as unnecessary friction for a small shop, ultimately kept all four: (Pending, Processing, Ready, Claimed), default Pending. Later, 'Cancelled' was added to handle soft deletion of mistaken orders to preserve the audit trail.
7. Added claim_number (auto-generated, e.g. "LS-260701-001") as a digital receipt/pickup verification code. Format is LS-YYMMDD-NNN where YY is the two-digit year, MM is month, DD is day, and NNN is the zero-padded daily sequence number — chosen instead of a physical thermal printer, since most small PH laundry shops use handwritten claim stubs, not printers.
8. Added ready_at, claimed_at, and cancelled_at TIMESTAMP columns to track workflow timing and error voiding precisely.
9. Added notes column (free text, e.g. "no fabric softener") for cashier remarks.
10. Added address (optional) and is_active (TINYINT, soft delete) to Customers — customers are deactivated, not hard-deleted, to preserve order history integrity.
11. Initially rejected price_at_order, then reinstated. Stores the fixed_price value from Services at the time the order was created, so old orders remain accurate even if the service price changes later.
12. Explicitly rejected: cash tendered / change calculation logic — user judged a desk calculator sufficient, out of scope for the system.

## 4. CURRENT FINAL SCHEMA (laundry_service_db)

- Employees: employee_id (PK), name, username (UNIQUE), password, created_at
- Services: service_id (PK), service_name (UNIQUE), fixed_price DECIMAL(19,4) — seeded: ('Full Service', 175.0000)
- Customers: customer_id (PK), name, phone (UNIQUE), is_active (default 1), address (nullable), created_at
- Orders: order_id (PK), claim_number (UNIQUE, nullable), customer_id (FK → Customers, ON DELETE SET NULL), employee_id (FK → Employees, ON DELETE SET NULL), service_id (FK → Services, ON DELETE RESTRICT), order_date (TIMESTAMP default now), ready_at (nullable TIMESTAMP), claimed_at (nullable TIMESTAMP), cancelled_at (nullable TIMESTAMP), weight_kg DECIMAL(10,2), price_at_order DECIMAL(19,4) NOT NULL (snapshot of Services.fixed_price at time of order), total_amount DECIMAL(19,4), payment_status ENUM(Unpaid, Paid) default Unpaid, order_status ENUM(Pending, Processing, Ready, Claimed, Cancelled) default Pending, notes (nullable)

Schema design principles confirmed by review: normalized, no redundant data, ON DELETE SET NULL on customer (preserves sales history), ON DELETE RESTRICT on service (prevents orphaned orders), ENUM enforces valid status values at the DB level, UNIQUE on phone prevents duplicate customers, INSERT IGNORE used for safe re-runnable seeding.

Known unresolved real-world limitations (acknowledged, accepted as out of scope for TESDA prototype): no price-change history, no multi-load-per-visit grouping, no cash/change handling, phone number is the only customer identifier (no merge logic for duplicate entries from formatting differences e.g. 09171234567 vs +639171234567), no recovery from mid-transaction power loss/crash (no autosave/draft).

init_db.sql rules: contains CREATE TABLE structure + mandatory default/lookup data only (default service, no test data). A separate optional seed_data.sql may be created for demo purposes. Script is written to be safely re-runnable (IF NOT EXISTS, INSERT IGNORE, conditional ALTER/ADD COLUMN, and a temporary stored procedure used once to safely add the employee_id FK without erroring if it already exists).

## 5. APPLICATION ARCHITECTURE

- Single MainJFrame window manages a CardLayout-based container (mainContainer) that swaps JPanel views in place, instead of opening multiple floating JFrame windows.
- Fixed navigation sidebar sits outside the CardLayout area, hidden via setVisible(false) until login succeeds, then shown with ORDER_LIST as the default "home" screen.
- Each panel is instantiated once at MainJFrame startup, with `this` (MainJFrame) passed into each panel's constructor, stored as a field, enabling each panel to call mainFrame.showCard("CARD_NAME") to navigate.
- MainJFrame holds currentEmployeeId (int, default -1) as shared session state across all panels, set by LoginPanel on success, read by other panels when inserting records, cleared on logout.
- Cross-panel data handoff (e.g. OrderListPanel → UpdateStatusPanel needing a specific order_id) is done via dedicated routing methods on MainJFrame (e.g. goToUpdateStatus(orderId)) acting as a central data broker, rather than direct panel-to-panel references.
- Stale data fix: every panel that reads from the DB must implement a public void refreshData() method; MainJFrame calls it whenever that panel's card is shown, so newly added data (e.g. a new customer) appears without an app restart.
- Logout clears currentEmployeeId, hides the sidebar, returns to LOGIN card.
- Panels must NOT contain their own "go to X" navigation buttons (navigation lives only in the shared sidebar); panels only contain task-specific buttons (Submit/Save/Cancel).
- Teammates must delete/remove any temporary standalone main() method used for solo preview/testing before merging their panel into MainJFrame.

## 6. ACTUAL PROJECT FILE STRUCTURE (confirmed from real repo)

```
com.mycompany.laundryservice/
├── MainJFrame.java / MainJFrame.form
├── database/
│   └── DBConnection.java
├── model/
│   ├── Customer.java
│   ├── Employee.java
│   ├── Order.java
│   └── Service.java
└── panels/
    ├── LoginPanel.java / .form
    ├── NewOrderPanel.java / .form
    ├── CustomerPanel.java / .form
    ├── OrderListPanel.java / .form
    ├── UpdateStatusPanel.java / .form
    └── ReportsPanel.java / .form
```

Note: actual folder names are singular `database/` and `model/` (not `db/`/`models/` as earlier discussed informally) — Java code references should match these exact package names.

## 7. MODEL LAYER (POJO conventions)

Each table has a matching POJO in model/: Customer, Employee, Order, Service. Rules: private fields only, public getters/setters as the sole access method, a no-arg constructor plus a full constructor for loading from a ResultSet, optional toString() for debugging. Money fields should use BigDecimal (not float/double) to avoid rounding errors. Models are UI-independent and DB-agnostic — panels interact with model objects, not raw SQL result sets, so a future UI change wouldn't require touching the model/db layers.

## 8. TEAM / PANEL OWNERSHIP (7 members)

1. LoginPanel — queries Employees by username/password, passes employee_id to MainJFrame on success.
2. NewOrderPanel — heaviest logic: select customer, generate claim_number, capture weight_kg, calculate total_amount at DECIMAL(19,4) precision, enforce hard 7kg cap (block save, not just warn), insert employee_id of logged-in user.
3. CustomerPanel — search by phone, register walk-ins, edit address, toggle is_active for soft delete (no hard DELETE).
4. OrderListPanel — JTable joining Orders + Customers + Services for a full order list view; entry point to UpdateStatusPanel via row selection.
5. UpdateStatusPanel — change order_status ENUM, capture ready_at/claimed_at timestamps exactly when those states are set.
6. ReportsPanel — aggregate queries (SUM, GROUP BY) for revenue and per-employee order counts.
7. MainJFrame — the user's own file: integration, CardLayout, sidebar, session state, routing. (Full requirements doc already written separately for this panel.)

## 9. TEAM WORKFLOW / GIT

- Pattern: Pull → code in isolated panel file → Commit → Push, all via GitHub Desktop GUI.
- Only the user touches init_db.sql, MainJFrame, and resolves merge conflicts; teammates work in isolated panel files to minimize conflict risk. If a teammate hits a Git conflict, the agreed fallback is to have them zip their folder and send it to the lead rather than teach conflict resolution under time pressure.
- .gitignore (confirmed sufficient) covers: target/, build/, dist/, nbproject/private/, nbbuild/, .nb-gradle/, NetBeans cache/config files, OS junk files (.DS_Store, Thumbs.db, etc.), and *.jar (safe to ignore since Maven fetches dependencies via pom.xml — no manually committed driver jar needed).
- Deadline-missed contingency rule proposed: if a panel isn't pushed by its agreed date, whoever finishes first picks it up, and that's recorded as reduced individual contribution for the original owner — meant to be pre-agreed by the team to avoid the lead having to make ad hoc judgment calls mid-project.

## 10. REQUIREMENTS DOCUMENTATION PROCESS

A standardized "Panel Requirements Form" was created (hybrid of IEEE 830/ISO 29148 SRS conventions, simplified for a 2-week student project) for every team member to fill out and submit before coding their panel. Sections: Basic Info, Purpose, User Story, Functional Requirements (with explicit acceptance criteria — this is treated as the most important section, verification over vague claims), UI Requirements, Data Requirements, Validation Rules, Non-Functional Requirements, Dependencies (cross-panel and schema dependencies must be flagged before coding), Out of Scope (to prevent ownership disputes), Sign-off Checklist. A plain-text (non-markdown) version was made specifically for pasting into the group chat. The MainJFrame panel's own requirements form has already been completed in full by the lead (covers all 11 sections: integration/session state ownership, no direct SQL, sidebar/CardLayout UI, refresh/routing functional requirements, dependency on all 6 other panels being compilable, explicit out-of-scope boundaries).

## 11. OUTSTANDING / NEXT STEPS AT TIME OF THIS SUMMARY

- Panel requirements forms from the other 6 teammates were due before 10PM June 30, 2026 — pending collection/review by the lead.
- MainJFrame requirements form is complete; MainJFrame.java integration code (CardLayout setup, panel instantiation, sidebar, routing methods, refreshData() calls, logout) is the next implementation step once teammates' panels are far enough along.
- DBConnection.java (in database/ package) needs to centralize the JDBC connection string/credentials so no panel hardcodes them individually.
- No memory persistence is enabled on the user's Claude account — this summary exists specifically to transfer full context manually into a new AI session.

END OF CONTEXT DUMP.
