# PANEL REQUIREMENTS FORM — LAUNDRY SERVICE POS

## 1. Basic Information

- Panel name (file name): OrderListPanel.java (with OrderListPanel.form)
- Assigned member: Iyah
- Date started: June 30, 2026
- Target completion date: July 1, 2026
- Tables this panel touches: Orders (SELECT JOIN, DELETE), Customers (SELECT JOIN, read only), Services (SELECT JOIN, read only), Employees (SELECT JOIN, read only)

## 2. Purpose

Allows the cashier to view all laundry orders in one place, search and filter them, navigate to UpdateStatusPanel to change a specific order's status or payment, and delete an order when necessary. This is also the default home screen shown after login.

## 3. User Story

As a cashier, I want to see all orders in a list so that I can find a specific order quickly, navigate to it for a status or payment update, or delete it if needed.

## 4. Functional Requirements

- FR-1. The system shall display all orders in a table sorted by most recent order_date first. Acceptance: loading the panel shows all orders with columns for Order ID, Claim Number, Customer Name, Phone, Address, Total Amount, Order Status, Payment Status, and Processed By (employee name).
- FR-2. The system shall allow filtering orders by Customer Name, Claim Number, or Order Status. Acceptance: typing a term and selecting a filter type updates the table to show only matching rows. An empty search field shows all orders.
- FR-3. The system shall allow sorting the table by Order Date, Status, or Amount. Acceptance: selecting a sort option from cboSortBy and clicking btnSort reorders the table rows accordingly.
- FR-4. The system shall allow viewing full order details for a selected row. Acceptance: double-clicking a row or clicking btnViewDetails opens a read-only dialog showing all fields for that order.
- FR-5. The system shall route a selected order to UpdateStatusPanel when btnUpdateStatus is clicked. Acceptance: selecting a row and clicking btnUpdateStatus calls mainFrame.goToUpdateStatus(orderId) and navigates to UpdateStatusPanel with that order loaded.
- FR-6. The system shall implement refreshData() to reload all orders from the database whenever the panel is shown. Acceptance: a new order saved in NewOrderPanel appears in the table the next time this panel is opened, without restarting the app.
- FR-7. The system shall allow deleting a selected order after a confirmation prompt. Acceptance: selecting a row and clicking btnDeleteOrder shows a confirmation dialog ("Are you sure you want to delete this order? This cannot be undone."). Confirming runs a DELETE on the Orders table for that order_id, the table refreshes, and the deleted row is gone. Cancelling does nothing.

## 5. UI Requirements

| Component Name  | Component Type | Behavior                                                                                         |
| --------------- | -------------- | ------------------------------------------------------------------------------------------------ |
| lblTitle        | JLabel         | Displays "Order List" as the panel title                                                         |
| txtSearch       | JTextField     | Search input, used with cboSearchType to filter the table                                        |
| cboSearchType   | JComboBox      | Options: "Customer Name", "Claim Number", "Status". Defaults to "Customer Name"                  |
| btnSearch       | JButton        | Applies the filter using txtSearch and cboSearchType selection                                   |
| btnRefresh      | JButton        | Clears search input and reloads all orders                                                       |
| cboSortBy       | JComboBox      | Options: "Order Date", "Status", "Amount". Defaults to "Order Date"                              |
| btnSort         | JButton        | Reorders the table based on cboSortBy selection                                                  |
| tblOrders       | JTable         | Displays all order records, read-only, single row selection mode                                 |
| lblTotalOrders  | JLabel         | Shows count of currently displayed rows (e.g., "Total Orders: 25")                               |
| btnViewDetails  | JButton        | Opens a read-only details dialog for the selected row. Disabled until a row is selected          |
| btnUpdateStatus | JButton        | Calls mainFrame.goToUpdateStatus(orderId) for the selected row. Disabled until a row is selected |
| btnDeleteOrder  | JButton        | Opens a confirmation dialog before deleting the selected order. Disabled until a row is selected |

Empty field handling: txtSearch is optional, empty shows all orders. cboSearchType and cboSortBy always have a default selected value so neither can be empty.

After successful action:

- After search or sort: table updates immediately, no confirmation dialog needed
- After clicking btnUpdateStatus: navigates to UpdateStatusPanel, no changes made in this panel
- After btnRefresh: search input clears and full order list reloads
- After successful delete: confirmation dialog closes, table refreshes, deleted row is gone

Error messages:

- Database failure: "Unable to connect to the database. Please check your connection and try again." — on any failed DB call
- No results found: "No orders found matching your search criteria." — when filter returns zero rows
- No row selected: "Please select an order first." — if btnViewDetails, btnUpdateStatus, or btnDeleteOrder is clicked with no row selected
- Delete failed: "Failed to delete order. Please try again." — if the DELETE statement throws an exception

## 6. Data Requirements (Database Interaction)

- Load all orders, SELECT JOIN, Orders JOIN Customers JOIN Services JOIN Employees
- Filter by Customer Name, SELECT JOIN, Orders JOIN Customers
- Filter by Claim Number, SELECT, Orders
- Filter by Status, SELECT, Orders
- Sort by Order Date, Order Status, or Total Amount, SELECT, Orders (ORDER BY clause variation)
- Get full order details for dialog, SELECT JOIN, Orders JOIN Customers JOIN Services JOIN Employees
- Delete a specific order, DELETE, Orders (by order_id)

Columns read:

- Orders: order_id, claim_number, customer_id, employee_id, service_id, order_date, ready_at, claimed_at, weight_kg, price_at_order, total_amount, payment_status, order_status, notes
- Customers: name, phone, address
- Services: service_name
- Employees: name (displayed as "Processed By")

This panel performs no INSERT or UPDATE operations.

## 7. Validation Rules

- A row must be selected before btnViewDetails, btnUpdateStatus, or btnDeleteOrder is enabled
- A confirmation dialog must be acknowledged before any DELETE is executed, cancelling aborts with no change
- No status or payment changes are made in this panel, all update logic lives in UpdateStatusPanel
- If the database returns no rows for a search, show the "no results" message rather than an empty table with no explanation

## 8. Non-Functional Requirements

- Performance: table must load and filter without noticeable lag for up to 1,000 orders
- Usability: all buttons have clear text labels, no icon-only buttons without tooltips. Status and payment columns should use color coding for quick visual identification. Table rows should alternate in background color for readability. Columns must be resizable.
- Error handling: all SQLExceptions caught and shown as readable dialogs, no raw stack traces shown to the user
- Consistency: follows the team's naming convention (txt, btn, cbo, lbl, tbl prefixes), constructor pattern OrderListPanel(MainJFrame mainFrame), and does not contain its own sidebar navigation buttons

## 9. Dependencies

- Depends on DBConnection.getConnection() from the database package
- Depends on MainJFrame for the goToUpdateStatus(orderId) routing method
- Depends on Orders, Customers, Services, and Employees tables all existing and being populated (covered by init_db.sql and NewOrderPanel/LoginPanel respectively)
- Must implement refreshData() to be called by MainJFrame whenever this card is shown
- Does not depend on any other panel directly, reads all display data straight from the database

## 10. Out of Scope

- This panel does not create new orders — that belongs to NewOrderPanel
- This panel does not manage customers — that belongs to CustomerPanel
- This panel does not generate reports — that belongs to ReportsPanel
- This panel does not handle authentication — that belongs to LoginPanel
- This panel does not contain its own navigation buttons to other panels — navigation is managed by the MainJFrame sidebar

## 11. Sign-off Checklist

- All functional requirements implemented and tested
- JTable loads with correct JOIN columns and sorts by most recent order_date by default
- Search filters work for all three filter types (Customer Name, Claim Number, Status)
- Sort works for all three sort options
- btnViewDetails, btnUpdateStatus, and btnDeleteOrder all disabled until a row is selected
- btnUpdateStatus correctly calls mainFrame.goToUpdateStatus(orderId) with the right order_id
- btnDeleteOrder shows confirmation dialog before executing DELETE, cancelling aborts correctly
- DELETE removes the correct order and table refreshes immediately after
- Read-only details dialog shows all order fields correctly
- refreshData() implemented and reloads correctly when panel is shown
- All error dialogs tested (no results, no row selected, DB failure)
- No standalone main() method left in the file before merging
- Pushed to GitHub with a commit message in [OrderListPanel] description format
