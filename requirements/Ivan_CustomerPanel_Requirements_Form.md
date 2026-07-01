# PANEL REQUIREMENTS FORM — LAUNDRY SERVICE POS

## 1. BASIC INFORMATION
- **Panel Name (file name):** CustomerPanel.java
- **Assigned Member:** Ivan Mathew Esclanda
- **Date Started:** June 30,2026
- **Target Completion Date:** July 5, 2026
- **Tables This Panel Touches:** Customers

## 2. PURPOSE
Allows staff to register new customers, search/view existing customers, edit their details, and deactivate records, so that accurate customer information is available when creating orders.

## 3. USER STORY
As a **cashier**, I want to **register, search, and update customer records** so that **I can quickly select an existing customer or add a new one when creating an order.**

## 4. FUNCTIONAL REQUIREMENTS
- FR-1. The system shall allow adding a new customer with name and phone number.
  Acceptance criteria: Filling in name and phone and clicking Save inserts a new row into `Customers` and clears the form.
- FR-2. The system shall reject duplicate phone numbers.
  Acceptance criteria: Entering a phone number that already exists shows a warning and does NOT save.
- FR-3. The system shall allow searching customers by name or phone.
  Acceptance criteria: Typing in the search field filters the table to matching rows only.
- FR-4. The system shall allow editing an existing customer's name, phone, or address.
  Acceptance criteria: Selecting a row, editing fields, and clicking Update modifies that exact row, no new row created.
- FR-5. The system shall allow deactivating a customer instead of deleting.
  Acceptance criteria: Clicking "Deactivate" sets `is_active = 0` for that customer; the row is not physically removed from `Customers`.

## 5. UI REQUIREMENTS
| Component | Type | Behavior |
|---|---|---|
| txtName | JTextField | Required, max 100 characters |
| txtPhone | JTextField | Required, numeric only |
| txtAddress | JTextField | Optional, max 255 characters |
| tblCustomers | JTable | Displays customer_id, name, phone, address, is_active, created_at |
| txtSearch | JTextField | Filters tblCustomers as user types or on Enter |
| btnSave | JButton | Disabled until name and phone are both filled |
| btnUpdate | JButton | Enabled only when a row is selected |
| btnDeactivate | JButton | Enabled only when a row is selected; confirms before action |
| lblStatus | JLabel | Shows success/error feedback messages |

**If a required field is left empty:** Save/Update button stays disabled, or clicking it shows a popup ("Name and phone are required") and does not save.

**After a successful save:** Form clears, table refreshes to show the new row, and a brief confirmation message appears.

**Error messages:**
- Duplicate phone → "This phone number is already registered."
- Empty required field → "Please fill in all required fields."
- DB connection failure → friendly generic message, not a raw Java exception.

## 6. DATA REQUIREMENTS (DATABASE INTERACTION)
| Action | SQL Type | Table |
|---|---|---|
| Add new customer | INSERT | Customers |
| Search/list customers | SELECT | Customers |
| Update customer details | UPDATE | Customers |
| Deactivate customer | UPDATE (`is_active = 0`) | Customers |
| Check phone uniqueness | SELECT | Customers |

**Columns read/written:** `customer_id`, `name`, `phone`, `address`, `is_active`, `created_at` — all already exist in the current schema (`init/init_db.sql`). No new columns needed.

## 7. VALIDATION RULES
- Name must not be empty.
- Phone must not be empty and must be unique (enforced by DB `UNIQUE` constraint + UI-level check).
- ⚠️ Flag to team: schema does not enforce a specific phone digit count (`phone` is `VARCHAR(20)`). If a fixed format (e.g., 11 digits) is required, it must be validated in code.
- Address optional, must not exceed 255 characters.

## 8. NON-FUNCTIONAL REQUIREMENTS
- **Performance:** Search results must appear within ~1 second.
- **Usability:** All buttons must have clear, readable labels; no icon-only buttons without tooltips.
- **Error Handling:** Database connection failure shows a friendly message, not a raw Java exception.
- **Consistency:** Follows team naming convention (`txt`, `btn`, `lbl`, `tbl` prefixes); reuses `DBConn.java` for the database connection.

## 9. DEPENDENCIES
- Needs `DBConn.java` for the database connection.
- Does not need another panel's data to function — this is a source panel; other panels (e.g., order creation) will instead depend on this one's customer data.
- Does not need a table or column that doesn't exist yet — `Customers` already has everything required.
- Does not depend on the logged-in employee's session data (`Customers` has no `employee_id` column).

## 10. OUT OF SCOPE
- This panel does not handle Orders creation or management.
- This panel does not handle payment processing or printing receipts.
- This panel does not permanently delete customer records — only deactivates, to preserve order history (`Orders.customer_id` has `ON DELETE SET NULL`).
