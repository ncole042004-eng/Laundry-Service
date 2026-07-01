# PANEL REQUIREMENTS FORM — LAUNDRY SERVICE POS

## 1. Basic Information

* Panel name (file name): HomePanel.java (with HomePanel.form)
* Assigned member: Lloyd
* Date started: July 1, 2026
* Target completion date: TBD
* Tables this panel touches: Orders, Customers (Read-Only)

## 2. Purpose

Serves as the main dashboard and landing page immediately following a successful login. It gives the user an instant overview of business health (earnings, volume, backlog), shows the most recent orders, and provides quick-action shortcuts for the most common tasks.

## 3. User Story

As a manager or cashier, I want to see how much we've earned today and how much laundry is currently pending the moment I log in, so I have a clear picture of the day's workload. I also want big shortcut buttons so I can instantly create a new order without hunting through menus.

## 4. Functional Requirements

* FR-1. The system shall calculate and display 5 key business metrics upon loading the panel. Acceptance: The panel queries the database and correctly displays Earnings Today, Orders Today, Claimed Today, Active Laundry, and Ready for Pickup.
* FR-2. The system shall populate a table with the 10 most recent transactions. Acceptance: A JTable displays the latest 10 rows from the Orders table, ordered by date descending, including the customer's name joined from the Customers table.
* FR-3. The system shall provide shortcut navigation to common tasks. Acceptance: Clicking the "New Order" shortcut button switches the view directly to the NewOrderPanel via `mainFrame.showCard()`.
* FR-4. The system shall update its data every time it is viewed. Acceptance: The `refreshData()` method must re-run all 6 SQL queries (5 stats + 1 table) so the numbers are always fresh when returning to the Home screen.

## 5. UI Requirements

| Component Name | Component Type | Behavior |
| --- | --- | --- |
| lblTitle | JLabel | Displays "Dashboard" or "Welcome" |
| pnlStatsWrapper | JPanel | Uses GridLayout (1 row, 5 columns) to hold the stat cards evenly |
| lblEarningsToday | JLabel | Massive bold text showing today's total revenue |
| lblOrdersToday | JLabel | Massive bold text showing count of orders received today |
| lblClaimedToday | JLabel | Massive bold text showing count of orders picked up today |
| lblActiveLaundry | JLabel | Massive bold text showing count of processing backlog |
| lblReadyPickup | JLabel | Massive bold text showing count of ready backlog |
| btnQuickNewOrder | JButton | Large shortcut button; calls `mainFrame.showCard("NEW_ORDER")` |
| btnQuickOrderList | JButton | Large shortcut button; calls `mainFrame.showCard("ORDER_LIST")` |
| scrRecentOrders | JScrollPane | Wraps the tblRecentOrders component |
| tblRecentOrders | JTable | Read-only table showing the last 10 orders |

Empty field handling: Not applicable (read-only panel).
Error messages:
* Database failure: "Unable to load dashboard data. Please check your connection." — shown via JOptionPane if any SQL query fails.

## 6. Data Requirements (Database Interaction)

All operations on this panel are **SELECT only**.

**The 5 Metric Queries:**
1. Earnings Today: `SELECT SUM(total_amount) FROM Orders WHERE DATE(order_date) = CURDATE()`
2. Orders Received Today: `SELECT COUNT(*) FROM Orders WHERE DATE(order_date) = CURDATE()`
3. Orders Claimed Today: `SELECT COUNT(*) FROM Orders WHERE DATE(claimed_at) = CURDATE()`
4. Active Laundry (All-Time Backlog): `SELECT COUNT(*) FROM Orders WHERE order_status IN ('Pending', 'Processing')`
5. Ready for Pickup (All-Time Backlog): `SELECT COUNT(*) FROM Orders WHERE order_status = 'Ready'`

**The Recent Orders Query:**
```sql
SELECT claim_number, c.name, order_status, total_amount 
FROM Orders o 
JOIN Customers c ON o.customer_id = c.customer_id 
ORDER BY order_date DESC 
LIMIT 10
```

## 7. Validation Rules

* This panel is read-only. It must never execute `INSERT`, `UPDATE`, or `DELETE` statements.
* If a metric query returns `NULL` (e.g., Earnings Today when there are 0 orders), the code must handle it and display `₱0.00` instead of crashing.

## 8. Non-Functional Requirements

* Performance: All 6 queries must execute quickly enough that the panel loads seamlessly. (Consider running them in a single batch or keeping indexes healthy).
* Usability: The stat cards must be easily readable from a distance (use large typography).
* Error handling: SQLExceptions must be caught and shown as readable dialogs.
* Consistency: Follows the constructor pattern `HomePanel(MainJFrame mainFrame)`.

## 9. Dependencies

* Depends on `DBConnection.getConnection()`.
* Depends on `MainJFrame` exposing the constants for card names (e.g., `CARD_NEW_ORDER`) to allow the shortcut buttons to work.

## 10. Out of Scope

* This panel does not create, modify, or process orders.
* This panel does not allow editing of customer profiles.
* It strictly displays aggregated read-only data and provides navigation shortcuts.

## 11. Sign-off Checklist

* `[ ]` All 5 metrics query and display correctly.
* `[ ]` Earnings Today handles NULL gracefully (displays 0.00).
* `[ ]` Backlog metrics (Active, Ready) correctly count older orders, not just today's.
* `[ ]` Recent Orders table populates correctly and is wrapped in a JScrollPane.
* `[ ]` Recent Orders table is set to read-only (cells cannot be edited).
* `[ ]` Quick Action buttons correctly navigate to their respective panels.
* `[ ]` `refreshData()` method is fully implemented and updates everything.
* `[ ]` GridLayout used correctly so stat cards stretch evenly on window maximize.
