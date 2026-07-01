# PANEL REQUIREMENTS FORM — LAUNDRY SERVICE POS

## 1. Basic Information

* Panel name (file name): NewOrderPanel.java (with NewOrderPanel.form)
* Assigned member: Jairus Rivero
* Date started: June 30, 2026
* Target completion date: July 1, 2026
* Tables this panel touches: Customers, Services, Orders, Employees (via session only, no direct query)

## 2. Purpose

Allows the cashier to create a new laundry order for an existing customer by looking up their phone number, selecting a service, entering the weight and optional notes, and saving the order to the database with a generated claim number.

## 3. User Story

As a cashier, I want to look up a customer, fill in their order details, and save it so that the system generates a claim number and records the order under my employee ID.

## 4. Functional Requirements

* FR-1. The system shall reject any order where the entered weight exceeds 7kg. Acceptance: entering 7.5 into txtWeightKg and clicking btnSave shows a popup ("Weight exceeds maximum capacity of 7kg") and does NOT insert anything into the database.
* FR-2. The system shall auto-generate a unique claim number for every saved order. Acceptance: each saved order has a claim_number in the format LS-YYMMDD-NNN (e.g. LS-260701-001) that does not repeat across any other order.
* FR-3. The system shall record which employee created the order. Acceptance: the employee\_id of the currently logged-in user (from mainFrame.getCurrentEmployeeId()) is saved with every new order.
* FR-4. The system shall display the service's fixed price as the total when a service is selected. Acceptance: selecting a service from cboService reads its fixed\_price and updates lblTotalAmount immediately with proper currency formatting.
* FR-5. The system shall snapshot the service's fixed price into price\_at\_order at the time of saving. Acceptance: the value written to price\_at\_order in the Orders table matches the fixed\_price from the Services table at the exact moment the order was saved, regardless of any future price changes.

# 5. UI Requirements

| Component Name         | Component Type | Behavior                                                                                    |
| ---------------------- | -------------- | ------------------------------------------------------------------------------------------- |
| lblTitle               | JLabel         | Displays "New Order" as the panel title                                                     |
| txtCustomerPhone       | JTextField     | Accepts digits only, used to look up the customer                                           |
| btnLookupCustomer      | JButton        | Queries Customers by the phone number in txtCustomerPhone                                   |
| lblCustomerNameDisplay | JLabel         | Shows matched customer's name after lookup, or "Customer not found or inactive" if no match |
| cboService             | JComboBox      | Populated from the Services table on panel load, shows service names                        |
| txtWeightKg            | JTextField     | Accepts positive numbers up to 2 decimal places only                                        |
| txtNotes               | JTextField     | Optional free-text field for cashier remarks (e.g., "no fabric softener")                   |
| lblTotalAmount         | JLabel         | Updates automatically when a service is selected, shows the flat fixed price                |
| btnSave                | JButton        | Disabled by default until a valid customer is loaded and txtWeightKg is filled              |
| btnCancel              | JButton        | Clears the form and calls mainFrame.showCard("ORDER_LIST")                                  |

Empty field handling: btnSave stays disabled. If somehow triggered with missing data, an alert dialog names the specific empty field.

After successful save: a confirmation dialog shows "Order saved. Claim Number: \[generated number]". After the user dismisses it, all fields clear and dropdowns reset, leaving a blank form. The panel does not navigate away.

Error messages:

* Weight over limit: "Weight exceeds maximum capacity of 7kg" — on clicking Save
* Customer not found: "Customer not found or is inactive." — on clicking Lookup
* Database failure: "Unable to connect to the database. Please check your connection and try again." — on any failed DB call

## 6. Data Requirements (Database Interaction)

* Look up customer by phone, SELECT, Customers table
* Load available services into dropdown, SELECT, Services table
* Save new order, INSERT, Orders table

Columns read:

* Customers: customer\_id, name, phone, is\_active
* Services: service\_id, service\_name, fixed\_price

Columns written to Orders:

* claim\_number, customer\_id, employee\_id, service\_id, order\_date (auto via DB default), weight\_kg, price\_at\_order, total\_amount, payment\_status (default Unpaid), order\_status (default Pending), notes

## 7. Validation Rules

* Customer must exist in the database, have is\_active = 1, and be confirmed via lookup before Save is enabled
* weight\_kg must be a number greater than 0.00 and no greater than 7.00
* currentEmployeeId from MainJFrame must be a valid value (not -1) before any insert is allowed
* price\_at\_order must be populated from the selected service's fixed\_price before the INSERT runs, never null or zero

## 8. Non-Functional Requirements

* Performance: customer lookup must return a result within 1 second
* Usability: all buttons must have clear text labels, no icon-only buttons without tooltips
* Error handling: all SQLExceptions must be caught and shown as readable dialogs, never a raw stack trace
* Consistency: follows the team's naming convention (txt, btn, cbo, lbl prefixes) and constructor pattern NewOrderPanel(MainJFrame mainFrame)

## 9. Dependencies

* Depends on DBConnection.getConnection() from the database package
* Depends on mainFrame.getCurrentEmployeeId() returning a valid employee\_id (set by LoginPanel on login)
* Depends on CustomerPanel having already registered the customer — this panel only looks up existing customers, it does not create them
* Depends on the Services table having at least one active service seeded (covered by init\_db.sql INSERT IGNORE)
* No new schema columns needed, all columns used already exist in the current schema

## 10. Out of Scope

* This panel does not register, edit, or deactivate customers — that belongs to CustomerPanel
* This panel does not change order status after creation — that belongs to UpdateStatusPanel
* This panel does not change payment\_status beyond the default Unpaid set on insert
* This panel does not handle cash, change calculation, or payment processing
* This panel does not print physical receipts or generate any output to hardware

## 11. Sign-off Checklist

* All functional requirements implemented and tested
* price\_at\_order correctly snapshots fixed\_price from Services at time of save
* claim\_number generated correctly and confirmed unique
* employee\_id from session recorded on every saved order
* 7kg hard cap enforced, tested with edge case values (7.00 allowed, 7.01 blocked)
* btnSave disabled until customer lookup succeeds and weight is filled
* All error dialogs tested (bad phone, overweight, DB failure)
* Form clears correctly after successful save without navigating away
* No standalone main() method left in the file before merging
* refreshData() implemented to reload the Services dropdown on panel show
* Pushed to GitHub with a commit message in \[NewOrderPanel] description format
