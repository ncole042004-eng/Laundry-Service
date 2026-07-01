# Panel Requirements Form — UpdateStatusPanel

## 1. Basic Information
- **Panel Name:** UpdateStatusPanel
- **Assigned Member:** Stephanie
- **Date Started:** June 30
- **Target Completion Date:** July 1 or later
- **Tables This Panel Touches:** Orders

## 2. Purpose
Allows an employee to update an order's status as it moves through the workflow (Pending → Processing → Ready → Claimed), recording the exact time an order becomes Ready or is Claimed by the customer.

## 3. User Story
As an employee, I want to update an order's status and capture the time it happened, so that the shop can track workflow progress and customers can be told when their laundry is ready or has been picked up.

## 4. Functional Requirements
- **FR-1.** The system shall let the user change an order's `order_status` to any of the four valid values (Pending, Processing, Ready, Claimed).
  Acceptance criteria: selecting a new status and clicking Update changes the value in the database for that order_id.
- **FR-2.** The system shall record `ready_at` the moment status is set to Ready.
  Acceptance criteria: setting status to Ready populates `ready_at` with the current timestamp; it is not overwritten if the status is changed again later.
- **FR-3.** The system shall record `claimed_at` the moment status is set to Claimed.
  Acceptance criteria: setting status to Claimed populates `claimed_at` with the current timestamp.
- **FR-4.** The panel shall load the correct order when navigated to from OrderListPanel.
  Acceptance criteria: selecting a row in OrderListPanel and clicking through opens UpdateStatusPanel pre-loaded with that order's claim number, customer, service, and current status.

## 5. UI Requirements
| Component | Type | Behavior |
|---|---|---|
| `lblClaimNumber` | JLabel | Displays the order's claim_number (read-only). |
| `lblOrderDetails` | JLabel | Displays customer name, service, weight, total (read-only, for confirmation context). |
| `cmbStatus` | JComboBox | Populated with Pending/Processing/Ready/Claimed; defaults to the order's current status. |
| `btnUpdate` | JButton | Disabled if the selected status is unchanged from current status. |
| `btnCancel` | JButton | Returns to OrderListPanel without saving. |

**Empty required fields:** N/A — no free-text entry, only a dropdown, so this shouldn't occur if an order was properly loaded.

**After a successful update:** Show a confirmation message, then return to OrderListPanel (per the "no in-panel navigation buttons" rule, this counts as task completion, not sidebar navigation).

**Error messages:** If the DB update fails, show a friendly message like "Could not update order status — please try again," not a raw exception.

## 6. Data Requirements
| Action | SQL Type | Table | Columns |
|---|---|---|---|
| Load order details | SELECT | Orders (joined with Customers/Services for display) | claim_number, order_status, customer name, service name, weight_kg, total_amount |
| Update status | UPDATE | Orders | order_status, and conditionally ready_at or claimed_at |

## 7. Validation Rules
- A status must be selected that differs from the current status before Update is enabled.
- `ready_at` is only written the first time status becomes Ready (don't overwrite on later edits, if edits are even allowed backward — flag to the team: can status move backward, e.g. Ready → Processing? Recommend disallowing it unless there's a business reason).
- `claimed_at` is only written the first time status becomes Claimed.

## 8. Non-Functional Requirements
- **Performance:** Order load and status update should complete within 1 second on local XAMPP.
- **Usability:** All buttons labeled clearly (Update, Cancel), no icon-only buttons.
- **Error Handling:** DB failures show a friendly message, not a stack trace.
- **Consistency:** Follows txt/btn/lbl/cmb naming convention.

## 9. Dependencies
- Depends on OrderListPanel to supply the order_id via MainJFrame's routing method (e.g. `goToUpdateStatus(orderId)`).
- Does not depend on `currentEmployeeId` — this panel doesn't record who changed the status (only NewOrderPanel records employee_id, per current schema). Flag to team if audit-of-status-changer is later wanted — it isn't in the current schema.
- No missing tables/columns — Orders already has order_status, ready_at, claimed_at.

## 10. Out of Scope
- Does not create new orders (NewOrderPanel).
- Does not handle payment status (payment_status is a separate field — confirm with team whether this panel or another owns it; currently unassigned).
- Does not print or regenerate claim numbers.
- Does not handle customer notification (SMS/call) — manual process outside the system.
