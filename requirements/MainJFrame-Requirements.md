PANEL REQUIREMENTS FORM, LAUNDRY SERVICE POS

**1. Basic Information**

- Panel name (file name): MainJFrame.java (with MainJFrame.form)
- Assigned member: Lloyd Ariel B. Deputo
- Date started: June 30, 2026
- Target completion date: July 5, 2026
- Tables this panel touches: None directly, holds shared session state only (currentEmployeeId)

**2. Purpose**
MainJFrame is the main window that holds every panel and controls which one is showing. It's the integration point that ties all seven panels (LoginPanel, HomePanel, NewOrderPanel, CustomerPanel, OrderListPanel, UpdateStatusPanel, ReportsPanel) into one app.

**3. User Story**
As a cashier, I want to move between screens in one window so I don't have to deal with separate floating windows for each task.

**4. Functional Requirements**

- FR-1. Show exactly one panel at a time using CardLayout. Acceptance: clicking a nav button swaps the visible panel, nothing else stays underneath.
- FR-2. Instantiate every panel once at startup and pass MainJFrame into each constructor. Acceptance: any panel can call mainFrame.showCard("CARD_NAME") and switch screens.
- FR-3. Show LoginPanel first, sidebar hidden until login succeeds. Acceptance: on launch, only the login form is visible.
- FR-4. Store currentEmployeeId after login and expose it to all panels. Acceptance: after setCurrentEmployee(id), getCurrentEmployeeId() returns that id everywhere, and new orders save with it.
- FR-5. Trigger refreshData() on each panel when it becomes visible. Acceptance: a customer added in CustomerPanel shows up in NewOrderPanel's dropdown without restarting the app.
- FR-6. Provide logout that clears currentEmployeeId, hides the sidebar, returns to LoginPanel. Acceptance: after logout, nav buttons no longer reach any panel until a new login.
- FR-7. Provide a routing method so one panel can hand a record to another before switching. Acceptance: clicking Update in OrderListPanel calls goToUpdateStatus(orderId) and loads that order.
- FR-8. Provide one shared sidebar for navigation, separate from the CardLayout area. Acceptance: no panel builds its own "go to X" buttons.

**5. UI Requirements**

- sidebarPanel, JPanel, fixed left side panel, hidden until login succeeds
- btnNavNewOrder, JButton, switches to NEW_ORDER card
- btnNavCustomers, JButton, switches to CUSTOMER card
- btnNavHome, JButton, switches to HOME card (Dashboard), acts as the home screen
- btnNavOrderList, JButton, switches to ORDER_LIST card
- btnNavReports, JButton, switches to REPORTS card
- btnLogout, JButton, clears session, hides sidebar, returns to LOGIN card
- mainContainer, JPanel, CardLayout, holds all seven panels
- lblWelcome (optional), JLabel, shows logged-in employee's name

Empty field handling: not applicable, MainJFrame collects no input itself.
After successful action: sidebar appears, HOME (Dashboard) shows as the default landing screen after login.
Error messages: a database connection failure anywhere in the app should show one shared dialog ("Unable to connect to the database. Please check your connection and try again."), not a raw exception.

**6. Data Requirements**
MainJFrame runs no SQL directly. Its only data responsibility is holding currentEmployeeId in memory for the session, sourced from the Employees table through LoginPanel.

**7. Validation Rules**

- No panel besides LOGIN is reachable while currentEmployeeId is unset (-1)
- Logout always resets currentEmployeeId to -1 and locks the sidebar again
- Routing methods like goToUpdateStatus(orderId) should reject invalid ids instead of opening a blank panel

**8. Non-Functional Requirements**

- Performance: card switching should be instant, no flicker
- Usability: sidebar buttons are text-labeled, grouped clearly, and show which screen is active
- Error handling: a crash in one panel shouldn't take down the whole window
- Consistency: all panels follow the same constructor pattern, and card name strings are defined once and reused

**9. Dependencies**

- Needs all seven panels feature-complete enough to compile and instantiate
- Needs each panel's temporary standalone main() method removed before merging
- Needs every panel to implement refreshData() per the group's refresh rule
- Needs the Employees table through LoginPanel for currentEmployeeId
- No new schema needed, current init_db.sql already covers this panel

**10. Out of Scope**

- No business logic (order calculation, validation, status updates)
- No direct SQL queries
- No form validation for individual panels
- No claim number generation or timestamp handling, those belong to NewOrderPanel and UpdateStatusPanel
