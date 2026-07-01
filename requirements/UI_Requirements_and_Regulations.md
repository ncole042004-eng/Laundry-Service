# UI Requirements and Regulations — Laundry Service POS
## Standard for All Team Members

This document defines the mandatory UI standards for every panel in this project. All team members must follow these rules before submitting their panel. **Read this from top to bottom before you write a single line of code.**

---

## Table of Contents

- [Section 0 — What You Are Building and Why](#0)
- [Section 1 — Look and Feel: FlatLaf](#1)
- [Section 2 — Panel Constructor Requirements](#2)
- [Section 3 — Resizing Behavior: GroupLayout Rules](#3)
- [Section 4 — Window and Size Standards](#4)
- [Section 5 — Visual and Styling Standards](#5)
- [Section 6 — Error Handling Standard](#6)
- [Section 7 — Pre-Merge Checklist](#7)
- [Section 8 — Creating Your Panel in NetBeans (Step by Step)](#8)
- [Section 9 — Recommended Panel Layout Template](#9)
- [Section 10 — What is `initComponents()` and the Generated Code Block](#10)
- [Section 11 — Connecting to the Database in Your Panel](#11)
- [Section 12 — Wrapping a JTable in a JScrollPane](#12)
- [Section 13 — Common Mistakes and How to Avoid Them](#13)

---

## Section 0 — What You Are Building and Why <a name="0"></a>

Before writing any code, understand the structure of the app you are contributing to.

### The Big Picture

This application has **one main window** (`MainJFrame`). Inside that window, only **one screen is shown at a time**. When the cashier clicks "New Order" in the sidebar, the window does not open a new popup or a new window — it simply swaps the visible screen inside the same window. This is called a **CardLayout**.

Think of it like a deck of cards:
- Each card is a `JPanel` — your panel file.
- `MainJFrame` is the hand holding the deck.
- Clicking a sidebar button tells the hand to flip to a different card.

### Your Job

You are responsible for building **one card** (one `JPanel`). Your file lives in the `panels/` folder. You design the form, write the database logic, and hand it in. The team lead wires it into the deck.

### The Structure of the Project

```
com.mycompany.laundryservice/
├── MainJFrame.java          ← The main window (team lead's file)
├── database/
│   └── DBConnection.java   ← Shared database connection (do not touch)
├── model/
│   ├── Customer.java        ← Data class for a customer
│   ├── Employee.java        ← Data class for an employee
│   ├── Order.java           ← Data class for an order
│   └── Service.java         ← Data class for a service
└── panels/                  ← YOUR FILE LIVES HERE
    ├── LoginPanel.java
    ├── NewOrderPanel.java
    ├── CustomerPanel.java
    ├── OrderListPanel.java
    ├── UpdateStatusPanel.java
    └── ReportsPanel.java
```

### The Screen Flow

```
App Starts → LoginPanel
    ↓ (successful login)
OrderListPanel  ←──────────────────────────────┐
    ├── [Sidebar: New Order]  → NewOrderPanel   │
    ├── [Sidebar: Customers]  → CustomerPanel   │
    ├── [Sidebar: Reports]    → ReportsPanel    │
    └── [Click Update Status] → UpdateStatusPanel
```

---

## Section 1 — Look and Feel: FlatLaf <a name="1"></a>

This project uses **FlatLaf** as its Look and Feel (L&F). FlatLaf gives all Swing components a modern, clean, flat appearance automatically.

### Rule: You do NOT set the Look and Feel yourself.
The L&F is initialized **once** in `MainJFrame.java` by the team lead before any window opens. Your panel inherits it automatically when it is loaded into the main app.

### Rule: Do NOT call `UIManager.setLookAndFeel()` in your panel.
If you set a different L&F inside your JPanel or your test `main()` method, it will conflict with the team's setup.

### What FlatLaf does for you automatically
- All `JButton`, `JTextField`, `JTable`, `JLabel`, and other components will look modern without extra code.
- Rounded corners on text fields and buttons.
- Clean, readable default fonts.
- Proper High-DPI (HiDPI) scaling on high-resolution screens.

### How to preview FlatLaf while testing your panel solo
Since your panel won't have FlatLaf activated during solo preview (you'll use your test `main()`), add this setup **only inside your temporary `main()` method** so your preview looks correct. Remove the entire `main()` method before merging.

```java
// Add this ONLY inside your temporary main() preview method.
// Remove the entire main() block before pushing to GitHub.
public static void main(String[] args) {
    try {
        com.formdev.flatlaf.FlatLightLaf.setup();
    } catch (Exception e) {
        e.printStackTrace();
    }
    javax.swing.SwingUtilities.invokeLater(() -> {
        javax.swing.JFrame f = new javax.swing.JFrame("Preview");
        f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        // Pass 'null' for mainFrame during solo preview — it compiles and shows your layout
        f.add(new YourPanelName(null)); // replace YourPanelName with your class name
        f.setSize(1024, 768);
        f.setVisible(true);
    });
}
```

**FlatLaf documentation:** https://www.formdev.com/flatlaf/

---

## Section 2 — Panel Constructor Requirements <a name="2"></a>

Every JPanel **must** follow this constructor signature. Without this, `MainJFrame` cannot wire your panel into the CardLayout.

```java
public class YourPanel extends javax.swing.JPanel {

    private com.mycompany.laundryservice.MainJFrame mainFrame;

    // Required constructor — receives the main window as a parameter
    public YourPanel(com.mycompany.laundryservice.MainJFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    // Required method — called by MainJFrame every time your panel is shown
    public void refreshData() {
        // Re-query the database and reload your table/components here
    }
}
```

### Why the `mainFrame` reference is needed
You use it to navigate: `mainFrame.showCard("ORDER_LIST")` to go to another panel. You also use it to get the logged-in employee: `mainFrame.getCurrentEmployeeId()` when creating a new order.

---

## Section 3 — Resizing Behavior: GroupLayout Rules (NetBeans Free Design) <a name="3"></a>

The app launches maximized to fill the user's screen. This means your panel will be shown at the screen's full size minus the 200px sidebar. Your layout must adapt to this.

NetBeans uses `GroupLayout` (also called "Free Design") by default. The key concept is that each component has a **minimum**, **preferred**, and **maximum** size. The `maximum` value controls whether a component stretches when the window grows.

### The Golden Rule
> `Short.MAX_VALUE` = can stretch to fill space.
> `-2` (Preferred Size) = stays at its designed size, never stretches.

### What to set for each component type

| Component | Horizontal | Vertical | Reason |
|---|---|---|---|
| `JScrollPane` + `JTable` | Stretch (`Short.MAX_VALUE`) | Stretch (`Short.MAX_VALUE`) | Tables should fill all available space |
| `JTextField`, `JPasswordField` | Stretch (`Short.MAX_VALUE`) | Fixed (`-2`) | Fields grow wider but not taller |
| `JTextArea` in `JScrollPane` | Stretch | Stretch | Same as table |
| `JButton` | Fixed (`-2`) | Fixed (`-2`) | Buttons never stretch |
| `JLabel` (title/header) | Fixed | Fixed | Labels do not resize |
| `JComboBox` | Stretch or Fixed | Fixed | Usually stretch horizontally |

### How to set this in the NetBeans GUI Builder
1. Right-click a component → **Properties**
2. Find the `maximumSize` property
3. To allow stretching: check "Same as preferred size" OFF and set a very large value, OR use the anchor/resizing handles in Free Design mode.

In Free Design (drag-and-drop) mode, after placing a component:
- Grab the **right edge anchor** of the component and drag it to the **right edge of the panel** — this makes it stretch horizontally.
- Grab the **bottom edge anchor** and drag it to the **bottom edge** — this makes it stretch vertically.
- If you do NOT drag an anchor to an edge, the component stays at a fixed size (this is correct for buttons and labels).

> **Tip:** Always test your panel by dragging the window to a larger size in your preview `main()`. If components don't grow, you need to anchor them to the panel edges.

---

## Section 4 — Window and Size Standards <a name="4"></a>

### Do NOT set a fixed `preferredSize` on your top-level panel
Do not call `this.setPreferredSize(new Dimension(800, 600))` or anything like it on the outermost JPanel of your file. `MainJFrame` controls the panel's size through the CardLayout container.

### Standard inner margin
All content inside your panel should have at least **16–20px** of padding from the edges of the panel. In the NetBeans builder, use `addContainerGap()` or drag components away from the panel's edge when placing them.

### Standard button size
Use a consistent button size of at minimum **120px wide × 35px tall**. In the NetBeans builder, right-click a button → Properties → set `minimumSize` to `[120, 35]`.

---

## Section 5 — Visual and Styling Standards <a name="5"></a>

### Colors
- Do NOT hardcode colors like `new Color(255, 0, 0)` for component backgrounds or text.
- If you need to color something for status indication (e.g., red for error text), use this pattern to stay compatible with FlatLaf:
```java
lblStatus.setForeground(java.awt.Color.RED); // Acceptable for error labels only
```
- Do NOT set custom background colors on your main JPanel — let FlatLaf handle it.

### Typography
FlatLaf applies a clean system font automatically. Follow these size guidelines:
- Panel title (`lblTitle`): **Bold, 18pt**
- Section labels or field labels: **13pt (default)**
- Table content: **12pt (default)**
- Error/status labels: **12pt, colored**

To set a font in code:
```java
lblTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
```

### Component Naming Prefix Convention
All team members must use these prefixes for component names so the codebase is consistent:

| Prefix | Component type |
|---|---|
| `lbl` | JLabel |
| `txt` | JTextField, JPasswordField |
| `btn` | JButton |
| `tbl` | JTable |
| `cbo` | JComboBox |
| `cmb` | JComboBox (alternate, also acceptable) |
| `pnl` | JPanel (sub-panels inside your form) |
| `scr` | JScrollPane |
| `txta` | JTextArea |

Example: `txtCustomerPhone`, `btnSave`, `tblOrders`, `lblStatus`

---

## Section 6 — Error Handling Standard <a name="6"></a>

All database errors must be caught and displayed to the user as a friendly dialog. Never let a raw exception stack trace reach the user.

```java
// Standard error dialog pattern:
try {
    // ... your database code here
} catch (java.sql.SQLException e) {
    javax.swing.JOptionPane.showMessageDialog(
        this,
        "Unable to connect to the database. Please check your connection and try again.",
        "Database Error",
        javax.swing.JOptionPane.ERROR_MESSAGE
    );
    e.printStackTrace(); // This still prints to the developer console for debugging
}
```

---

## Section 7 — Pre-Merge Checklist (Required for Everyone) <a name="7"></a>

Before pushing your panel to GitHub, verify every item below:

- `[ ]` No standalone `main()` method exists in your `.java` file
- `[ ]` No `UIManager.setLookAndFeel()` call exists in your panel
- `[ ]` Constructor accepts `MainJFrame mainFrame` as a parameter
- `[ ]` `refreshData()` method is implemented and re-queries the database
- `[ ]` All database operations are wrapped in try-catch with user-friendly error dialogs
- `[ ]` No hardcoded colors on panel backgrounds
- `[ ]` Component names follow the `txt`, `btn`, `lbl`, `tbl`, `cbo`, `pnl` prefix conventions
- `[ ]` JTable is inside a `JScrollPane`
- `[ ]` Action buttons (Save, Update, etc.) are disabled when no valid selection exists
- `[ ]` Uses `DBConnection.getConnection()` (not a hardcoded JDBC string in your panel)
- `[ ]` Committed with a message in the format: `[PanelName] description of what you did`

---

*Reference: FlatLaf Official Docs — https://www.formdev.com/flatlaf/*
*Reference: Oracle Swing Tutorials — https://docs.oracle.com/javase/tutorial/uiswing/*

---

## Section 8 — Creating Your Panel in NetBeans (Step by Step) <a name="8"></a>

This walks you through creating your panel file from scratch in NetBeans.

### Step 1: Create a New JPanel Form

1. In the **Projects** panel on the left, expand your project until you see the `panels` package: `com.mycompany.laundryservice.panels`.
2. **Right-click** on the `panels` package → **New** → **JPanel Form...**
   - If "JPanel Form" is not visible, click **Other...** → **Swing GUI Forms** → **JPanel Form**.
3. In the **Class Name** field, type your panel name exactly as assigned (e.g., `NewOrderPanel`).
4. Confirm the **Package** field shows `com.mycompany.laundryservice.panels`.
5. Click **Finish**.

NetBeans will create two files: `NewOrderPanel.java` and `NewOrderPanel.form`. The `.form` file is the visual designer. The `.java` file is the code. You will work in both.

### Step 2: First Things to Do After Creation

Before dragging any components, do these three things:

**A. Update the constructor.** NetBeans generates a no-arg constructor by default. You must modify it to accept `MainJFrame`. Click the **Source** tab at the top of the editor and find the constructor. Change it to match the required pattern from Section 2.

**B. Add the `refreshData()` method.** While in the Source tab, after the constructor, add an empty `refreshData()` method. You will fill it in later.

**C. Set the panel's background.** Switch to the **Design** tab. Right-click the empty panel → **Properties** → find `background`. Do NOT change it — leave it at default so FlatLaf controls it.

### Step 3: Start Designing in the Visual Editor

Click the **Design** tab to return to the visual editor. Use the **Palette** on the right to drag components onto your panel. Follow the layout template in Section 9.

### Step 4: Name Every Component Immediately After Adding It

When you drop a component onto the canvas:
1. Right-click it → **Change Variable Name...**
2. Type the correct name using the prefix conventions from Section 5 (e.g., `txtCustomerPhone`, `btnSave`, `tblOrders`).

Do this immediately. Renaming components later can cause layout code to break.

---

## Section 9 — Recommended Panel Layout Template <a name="9"></a>

All panels must follow this structure. It creates a consistent, professional look across the app.

```
┌─────────────────────────────────────────────────┐
│  lblTitle — "Panel Name" (Bold, 18pt)           │  ← Header Row
│  Thin separator line                            │
├─────────────────────────────────────────────────┤
│                                                 │
│   Form fields / JTable / main content           │  ← Content Area
│   (This area STRETCHES to fill all space)       │     (fills all remaining space)
│                                                 │
├─────────────────────────────────────────────────┤
│  lblStatus / lblError          [Cancel] [Save]  │  ← Footer / Button Row
└─────────────────────────────────────────────────┘
```

### How to Build This in NetBeans

**The outer panel** (your top-level JPanel file) should contain three nested JPanels:

1. **`pnlHeader`** — Fixed height, contains only `lblTitle`. Does NOT stretch vertically.
2. **`pnlContent`** — Contains all your form fields or your JTable. This is the panel that STRETCHES in both directions to fill the screen.
3. **`pnlFooter`** — Fixed height, contains `lblStatus` and your action buttons (Save, Cancel, etc.). Does NOT stretch vertically.

**Setting up the stretch in Free Design:**
- For `pnlContent`: After placing it, grab its **bottom anchor** and drag it to the bottom edge of the outer panel. Grab its **right anchor** and drag it to the right edge. This makes it fill all space between the header and footer.
- For `pnlHeader` and `pnlFooter`: Do NOT anchor their bottom or top to opposite edges. Let them stay at a fixed height.

### Panel Spacing Rules
- Minimum **16px gap** between the panel edge and the first component inside it.
- Minimum **8px gap** between components.
- Action buttons (`btnSave`, `btnCancel`) must be in the **bottom-right** corner of `pnlFooter`.
- Status/error labels (`lblStatus`) must be in the **bottom-left** corner of `pnlFooter`.

---

## Section 10 — What is `initComponents()` and the Generated Code Block <a name="10"></a>

When you work in the visual Design editor and drag components onto your panel, NetBeans automatically writes Java code to create those components. That code is placed inside a method called `initComponents()`.

### The Generated Code Region

In your `.java` file, you will see a block that looks like this:

```java
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
private void initComponents() {
    // ... NetBeans-generated code is here ...
}// </editor-fold>//GEN-END:initComponents
```

### The One Rule: Never Manually Edit Inside This Block

The text between `//GEN-BEGIN:initComponents` and `//GEN-END:initComponents` is **fully controlled by NetBeans**. Every time you make a change in the Design editor, NetBeans rewrites this block entirely.

**If you manually write code inside this block, NetBeans will delete it the next time you use the designer.**

**If you break the formatting of this block, the entire `.form` file may stop working.**

### Where to Put Your Own Code

Write all your own logic — button click handlers, database calls, validation — **outside** the generated code block. NetBeans creates stub event handler methods for you automatically when you double-click a button in the designer. Your code goes inside those stubs.

```java
// Generated stub — NetBeans creates this for you when you double-click a button
private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
    // ← Write YOUR code here. This is outside the GEN-BEGIN block.
    saveOrder();
}

// Your own private helper method — also outside the GEN-BEGIN block
private void saveOrder() {
    // database logic goes here
}
```

---

## Section 11 — Connecting to the Database in Your Panel <a name="11"></a>

All database access must go through the shared `DBConnection` class. Never hardcode a JDBC URL inside your panel.

### The Standard Pattern for Loading Data into a JTable

This is the complete pattern for querying the database and displaying results.

```java
import com.mycompany.laundryservice.database.DBConnection;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

// Call this inside refreshData() or a button's action handler
private void loadOrders() {
    // Step 1: Define the column headers for your table
    String[] columns = {"Order ID", "Customer", "Status", "Total"};
    DefaultTableModel model = new DefaultTableModel(columns, 0);

    // Step 2: Connect to the database and run a query
    String sql = "SELECT order_id, c.name, order_status, total_amount "
               + "FROM Orders o JOIN Customers c ON o.customer_id = c.customer_id "
               + "ORDER BY order_date DESC";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        // Step 3: Loop through results and add each row to the table model
        while (rs.next()) {
            Object[] row = {
                rs.getInt("order_id"),
                rs.getString("name"),
                rs.getString("order_status"),
                rs.getBigDecimal("total_amount")
            };
            model.addRow(row);
        }

    } catch (SQLException e) {
        // Step 4: Show a friendly error if the database fails
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Unable to load orders. Please check your connection.",
            "Database Error",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
        e.printStackTrace();
    }

    // Step 5: Apply the model to your JTable
    tblOrders.setModel(model);
}
```

### The Standard Pattern for Saving Data (INSERT)

```java
private void saveNewOrder() {
    String sql = "INSERT INTO Orders (customer_id, employee_id, service_id, weight_kg, "
               + "price_at_order, total_amount, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, selectedCustomerId);
        stmt.setInt(2, mainFrame.getCurrentEmployeeId()); // from session
        stmt.setInt(3, selectedServiceId);
        stmt.setBigDecimal(4, weightKg);
        stmt.setBigDecimal(5, priceAtOrder);
        stmt.setBigDecimal(6, totalAmount);
        stmt.setString(7, txtNotes.getText().trim());

        stmt.executeUpdate();

        javax.swing.JOptionPane.showMessageDialog(this, "Order saved successfully!");

    } catch (SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Failed to save order. Please try again.",
            "Database Error",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
        e.printStackTrace();
    }
}
```

> **Note:** Use `PreparedStatement` with `?` placeholders every time. Never concatenate user input directly into SQL strings — that is a SQL injection vulnerability.

---

## Section 12 — Wrapping a JTable in a JScrollPane <a name="12"></a>

A `JTable` must always be placed inside a `JScrollPane`. Without it, the table header may disappear and the user cannot scroll when there are more rows than the visible area can show.

### How to do it in the NetBeans visual editor

1. From the **Palette** on the right, drag a **Scroll Pane** (`JScrollPane`) onto your panel first — before the table.
2. Then, drag a **Table** (`JTable`) from the Palette **directly onto the Scroll Pane** you just placed. You should see it nest inside it.
3. Name the outer scroll pane `scrOrders` (or appropriate name) and the inner table `tblOrders`.
4. To make the table fill the available space, anchor the `JScrollPane` to all four edges of its container panel (see Section 3).

### If you already placed a JTable without a JScrollPane

1. Delete the JTable from the canvas.
2. Add a JScrollPane to the canvas.
3. Drag a new JTable onto the JScrollPane.

Do not try to drag a JTable into an existing JScrollPane after the fact — NetBeans does not handle this cleanly.

### Making the table read-only

By default, JTable cells are editable by the user (they can type into table cells). For a read-only display table, add this after your `tblOrders.setModel(model)` call:

```java
// Prevents the user from editing cells directly in the table
tblOrders.setDefaultEditor(Object.class, null);
```

---

## Section 13 — Common Mistakes and How to Avoid Them <a name="13"></a>

These are the most frequent mistakes that will either break the visual designer, cause compile errors, or break integration.

| ❌ Mistake | ✅ What to do instead |
|---|---|
| Editing code inside the `//GEN-BEGIN` block | Write all your logic outside the generated block, in your own methods |
| Using `new YourPanel()` no-arg constructor | Always use `new YourPanel(mainFrame)` — the constructor requires the `mainFrame` reference |
| Calling `UIManager.setLookAndFeel()` in your panel | Never. FlatLaf is set up once in `MainJFrame`. Your panel inherits it. |
| Setting `this.setPreferredSize(...)` on your top-level panel | Never. `MainJFrame`'s CardLayout controls your panel's size. |
| Hardcoding a JDBC URL like `"jdbc:mysql://localhost..."` in your panel | Use `DBConnection.getConnection()` always. |
| Putting a JTable directly on the panel without a JScrollPane | Always place JTable inside JScrollPane (see Section 12). |
| Leaving your preview `main()` method in the file before pushing | Delete the entire `main()` block before pushing to GitHub. |
| Not implementing `refreshData()` | Every panel must have this method. MainJFrame calls it every time your panel is shown. |
| Hardcoding a color like `new Color(255, 0, 0)` as the panel background | Leave panel backgrounds at default. FlatLaf handles it. |
| Naming components with generic NetBeans defaults like `jButton1`, `jTextField2` | Rename every component immediately after placing it using the prefix conventions. |
| Building navigation buttons inside your panel ("Go to Orders", "Go back") | Navigation belongs in `MainJFrame`'s sidebar only. Your panel only has task buttons (Save, Cancel, Update). |
| Using `double` or `float` for money values | Use `java.math.BigDecimal` for all money fields to avoid rounding errors. |

---

*Reference: FlatLaf Official Docs — https://www.formdev.com/flatlaf/*
*Reference: Oracle Swing Tutorials — https://docs.oracle.com/javase/tutorial/uiswing/*
*Reference: NetBeans GUI Builder Guide — https://netbeans.apache.org/tutorial/main/kb/docs/java/quickstart-gui/*
