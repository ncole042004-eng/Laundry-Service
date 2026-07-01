# PANEL REQUIREMENTS FORM — LAUNDRY SERVICE POS

## 1. Basic Information
- **Panel name (file name):** ReportsPanel.java (with ReportsPanel.form)
- **Assigned member:** Rod
- **Date started:** [fill in]
- **Target completion date:** [fill in]
- **Tables this panel touches:** Orders, Employees (read-only; no INSERT, UPDATE, or DELETE)

## 2. Purpose
Allows the shop owner/lead to view summarized business data — total revenue from paid orders, per-employee order counts, and order status breakdown — filtered by a selected date range, without manually reviewing individual order records.

## 3. User Story
As the **team lead / shop owner**, I want to **view a summary of revenue, employee performance, and order statuses over a chosen time period**, so that **I can monitor business performance and staff accountability at a glance**.

## 4. Functional Requirements

**FR-1.** The system shall calculate and display total revenue from paid orders within the selected date range.
Acceptance criteria: The displayed total matches the manual SUM of `total_amount` for all Orders where `payment_status = 'Paid'` and `order_date` falls within the selected range.

**FR-2.** The system shall display the number of orders processed per employee within the selected date range.
Acceptance criteria: A table shows each employee's name alongside their order count; the sum of all displayed counts equals the total number of orders in that date range.

**FR-3.** The system shall display a count of orders grouped by order status (Pending, Processing, Ready, Claimed) within the selected date range.
Acceptance criteria: The four status counts sum to the total number of orders shown for that date range.

**FR-4.** The system shall allow the user to filter all reports by a predefined date range selection.
Acceptance criteria: Changing the date filter (Today / This Week / This Month / All Time) updates all three reports (FR-1, FR-2, FR-3) to reflect only orders within that range.

**FR-5.** The system shall refresh report data automatically when the panel becomes visible.
Acceptance criteria: Navigating away to another panel, then returning to ReportsPanel, shows updated data without restarting the app — implemented via a public `refreshData()` method called by MainJFrame on card show, per architecture rule.

## 5. UI Requirements

| Component | Type | Behavior |
|---|---|---|
| cmbDateFilter | JComboBox | Options: Today, This Week, This Month, All Time; triggers report reload on selection change |
| lblTotalRevenue | JLabel | Displays total paid revenue for the selected range, formatted as ₱X,XXX.00 |
| tblEmployeeOrders | JTable | Lists each employee's name and order count, sorted descending by count |
| tblOrderStatus | JTable | Shows count per order status: Pending, Processing, Ready, Claimed |
| btnRefresh | JButton | Manually re-runs all queries in addition to the automatic refreshData() call |

**Additional behavior:**
- If no orders exist in the selected range, all values display as 0 / ₱0.00 — no error or crash.
- This panel is read-only. There is no Save action, so no confirmation dialogs are needed.
- No user-facing error messages under normal use since the only input (date filter) always has a valid default.

## 6. Data Requirements (Database Interaction)

| Action | SQL Type | Table(s) | Columns Used |
|---|---|---|---|
| Calculate total revenue (paid orders) | SELECT (SUM) | Orders | total_amount, payment_status, order_date |
| Count orders per employee | SELECT (COUNT, GROUP BY, JOIN) | Orders, Employees | Orders.employee_id, Orders.order_date, Employees.name |
| Count orders per status | SELECT (COUNT, GROUP BY) | Orders | order_status, order_date |
| Apply date range filter | WHERE clause | Orders | order_date |

**Columns read by this panel:**
`Orders`: order_date, total_amount, payment_status, order_status, employee_id
`Employees`: employee_id, name

**No new columns or tables are required.** All fields used exist in the current final schema. No schema flags needed.

Note: `price_at_order` is present in the Orders table but is not used by ReportsPanel — revenue is aggregated from `total_amount` which already reflects the correct price at time of order.

## 7. Validation Rules
- The date filter always has a valid default selection (All Time) — no empty or null filter state is possible.
- No free-text or numeric input fields exist on this panel, so no input validation is required.
- If a query returns no rows, the panel must display 0 / ₱0.00 rather than null, blank, or an exception.

## 8. Non-Functional Requirements
- **Performance:** All reports must load within 1 second for typical demo-scale data volumes.
- **Usability:** All labels must clearly identify what they display (e.g. "Total Revenue (This Week)"); no icon-only buttons without text labels.
- **Error Handling:** If the database connection fails, the panel shows a friendly message (e.g. "Unable to load reports. Please check your connection.") instead of a raw Java or SQL exception.
- **Consistency:** Follows team naming conventions — `txt`, `btn`, `lbl`, `cmb`, `tbl` prefixes for all components.

## 9. Dependencies
- **Hugh (LoginPanel) + Alliyah (NewOrderPanel):** Per-employee order counts depend on `employee_id` being correctly passed via session state and saved to every order. Accuracy of FR-2 depends on these two panels functioning correctly — to confirm before relying on this report in a demo.
- **Steph (UpdateStatusPanel):** Order status breakdown (FR-3) depends on `order_status` being correctly updated. No code dependency, but report accuracy relies on Steph's panel working.
- **Lloyd (MainJFrame):** ReportsPanel must be wired into the CardLayout/sidebar the same way as all other panels, with `refreshData()` called on card show — to confirm with Lloyd before integration.
- No new tables or columns required. No schema flags needed.

## 10. Out of Scope
- No export to CSV, PDF, or printed reports — on-screen viewing only.
- No per-service revenue breakdown — only one service currently exists in the schema ("Full Service"); reporting on a single-row table adds no value for the prototype.
- No charts or graphs — JTable and JLabel only, consistent with the team's Swing-only stack.
- No editing, inserting, or deleting of any data — this panel is strictly read-only.
- No real-time live-updating dashboard — data refreshes only on card show (via `refreshData()`) or manual button click.
- No payment processing or receipt handling — that belongs to NewOrderPanel and UpdateStatusPanel.

## 11. Sign-off Checklist
- [ ] All sections above reviewed and accurate
- [ ] Date started and target completion date filled in
- [ ] Dependencies confirmed with Hugh, Alliyah, Steph, and Lloyd
- [ ] No new schema changes required — confirmed against revised ProjectContext
- [ ] Ready to begin coding ReportsPanel.java
