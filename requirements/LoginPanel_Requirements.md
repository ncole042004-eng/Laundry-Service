# PANEL REQUIREMENTS FORM — LAUNDRY SERVICE POS

---

## 1. BASIC INFORMATION

| Field | Details |
|---|---|
| Panel Name (file name) | LoginPanel.java |
| Assigned Member | Hugh Benedict O. Abante |
| Date Started | June 30, 2026 |
| Target Completion Date | June 30, 2026 |
| Tables This Panel Touches | Employees |

---

## 2. PURPOSE

Allows an employee to log into the system using a username and password, so the system knows who is performing actions for accountability and audit purposes.

---

## 3. USER STORY

As an **employee**, I want to **log in with my username and password** so that **the system can track which actions I perform.**

---

## 4. FUNCTIONAL REQUIREMENTS

**FR-1: The system shall verify entered username and password against the Employees table using bcrypt hash comparison.**

Acceptance criteria: The system queries Employees by username only. The stored bcrypt hash is then compared against the entered password using `BCrypt.checkpw()` in Java. Correct credentials grant access to the main app. Incorrect credentials show an error and stay on the login screen. The plain password is never compared directly in SQL.

---

**FR-2: The system shall pass the employee_id to MainJFrame on successful login.**

Acceptance criteria: After a successful credential check, LoginPanel calls `mainFrame.onLoginSuccess(employeeId)` passing the matched employee's ID. MainJFrame's `currentEmployeeId` field is set to that value (not -1) for the rest of the session.

---

**FR-3: The system shall trigger MainJFrame to reveal the sidebar and navigate to ORDER_LIST on successful login.**

Acceptance criteria: After `mainFrame.onLoginSuccess(employeeId)` is called, the sidebar becomes visible and the Order List panel displays. LoginPanel does NOT implement the navigation itself — it only calls the method. The actual screen-switching logic lives in MainJFrame.

---

## 5. UI REQUIREMENTS

| Component | Type | Behavior |
|---|---|---|
| `lblTitle` | JLabel | Displays "Employee Login" as panel title, bold font |
| `txtUsername` | JTextField | Accepts text input; required field |
| `txtPassword` | JPasswordField | Masks input; required field |
| `btnLogin` | JButton | Triggers credential check; disabled until both fields are non-empty |
| `lblError` | JLabel | Hidden by default; displays error message in red on failed login |

### Empty Field Behavior
`btnLogin` stays disabled while either field is empty. If somehow triggered with empty fields, shows: `"Please enter both username and password."`

### After Successful Login
No save occurs. LoginPanel calls `mainFrame.onLoginSuccess(employeeId)`. MainJFrame handles navigation to ORDER_LIST and shows the sidebar. LoginPanel itself does nothing further.

### Error Messages

| Trigger | Message |
|---|---|
| Wrong username or password | `"Invalid username or password."` |
| Database connection failure | `"Unable to connect to database. Please check your connection and try again."` |
| Both fields empty on submit | `"Please enter both username and password."` |

---

## 6. DATA REQUIREMENTS (DATABASE INTERACTION)

**Action:** Verify login
**SQL Type:** SELECT
**Table:** Employees
**Columns Read:** `username`, `password` (hash), `employee_id`

> ⚠️ **Important:** The SELECT filters by `username` only. Password verification is done in Java using `BCrypt.checkpw(enteredPassword, storedHash)` — NOT in SQL. This is intentional and required for bcrypt to work correctly.

```sql
SELECT employee_id, password FROM Employees WHERE username = ?
```

---

## 7. VALIDATION RULES

- `txtUsername` must not be empty before login is attempted
- `txtPassword` must not be empty before login is attempted
- Retrieved bcrypt hash from DB must match entered password via `BCrypt.checkpw()` before access is granted
- If no Employees row matches the entered username, login is rejected immediately
- No case-insensitive or partial username matching

---

## 8. NON-FUNCTIONAL REQUIREMENTS

**Performance:**
Login check must return within 1 second on local XAMPP.

**Usability:**
- Password field must mask characters at all times
- Pressing Enter on either field should trigger login (same as clicking `btnLogin`)
- Error label must be clearly visible (red text)
- No icon-only buttons

**Security:**
- Passwords are stored as bcrypt hashes in the database — never as plain text
- Plain password entered by user is never stored, logged, or compared directly in SQL
- Uses jBCrypt library added via Maven `pom.xml` (groupId `org.mindrot`, artifactId `jbcrypt`, version `0.4`)

**Error Handling:**
- No raw Java exceptions or stack traces shown to the user
- All SQL exceptions caught and displayed as friendly messages

**Consistency:**
- Follows `txt`, `btn`, `lbl`, `cbo` naming convention agreed by the team
- Consistent font sizes, colors, and layout with other panels

---

## 9. DEPENDENCIES

- Depends on `DBConnection.java` for database connectivity
- Depends on `Employees` table existing with `username`, `password`, `employee_id` columns (already in schema)
- Depends on `MainJFrame` exposing an `onLoginSuccess(int employeeId)` method that sets `currentEmployeeId`, shows the sidebar, and navigates to `ORDER_LIST`
- Depends on jBCrypt being added to `pom.xml` by the team lead before coding begins
- Depends on seed employee records having **bcrypt-hashed** passwords in the database — plain text passwords will NOT work with this implementation

> ⚠️ **Note to Hugh:** Do not insert employee records with plain text passwords for testing. Use the provided `seed_data.sql` which will contain pre-hashed values, or ask the team lead for the correct hash to use.

---

## 10. OUT OF SCOPE

- This panel does **not** register or create employee accounts (no Employee creation UI exists in this project)
- This panel does **not** handle roles or permissions (flat `employee_id` only, no admin/cashier distinction)
- This panel does **not** handle "forgot password" or password reset flows
- This panel does **not** implement the sidebar visibility logic or CardLayout navigation — that belongs to `MainJFrame`
- This panel does **not** validate session state after login — session is managed entirely by `MainJFrame.currentEmployeeId`
- This panel does **not** interact with any table other than `Employees`

---

*Form prepared for: Laundry Service POS — TESDA NC III Capstone*
*Team Lead / Technical Authority: Lloyd*
