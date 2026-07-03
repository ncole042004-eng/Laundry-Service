# Panel Layout Logic — Laundry Service POS
## The Single Source of Truth for Panel Architecture

**Read this before writing a single line of code in your panel.**

This document answers every question about how panels are sized, integrated, and laid out in this project. It is cross-referenced with `UI_Requirements_and_Regulations.md`, `MainJFrame-Requirements.md`, and the Operations Overview `Review.md`.

---

## Table of Contents

- [Section 0 — The Big Picture (Architecture Diagram)](#0)
- [Section 1 — The Sidebar: Who Owns It, Where It Lives](#1)
- [Section 2 — The CardLayout: How Panel Swapping Works](#2)
- [Section 3 — Panel Sizing Rules (For ALL Panels)](#3)
- [Section A — "What size do I set my panel to?"](#a)
- [Section B — "How do I make elements resize like HomePanel?"](#b)
- [Section C — "What does MainJFrame handle vs. what do I handle?"](#c)
- [Section D — "Will following these rules work for any panel design?"](#d)
- [Section 4 — Standard 3-Zone Panel Template (Tier 2 Panels)](#4)
- [Section 5 — The Gold-Standard Exception: HomePanel & SidebarPanel (Tier 3)](#5)
- [Section 6 — The TopAppBar: Who Owns It](#6)
- [Section 7 — Full-Screen Resizing Checklist](#7)

---

## Section 0 — The Big Picture (Architecture Diagram) <a name="0"></a>

This is the entire application's structure. Every teammate's panel is one "card" in the center.

```
┌─────────────────────────────────────────────────────────────────┐
│                        MainJFrame (JFrame)                      │
│  Layout: BorderLayout                                           │
│                                                                 │
│  ┌─────────────┐  ┌───────────────────────────────────────────┐ │
│  │             │  │  pnlTopAppBar (NORTH slot)                │ │
│  │             │  │  White header, "Laundry Service Mgmt."    │ │
│  │             │  └───────────────────────────────────────────┘ │
│  │ Sidebar     │  ┌───────────────────────────────────────────┐ │
│  │ Panel       │  │  pnlContent (CENTER slot) — CardLayout   │ │
│  │ (WEST slot) │  │                                           │ │
│  │ 240px fixed │  │  ┌──────────────────────────────────────┐ │ │
│  │             │  │  │  Visible Panel (one at a time):      │ │ │
│  │ Logo        │  │  │  LoginPanel / HomePanel /            │ │ │
│  │ Nav Buttons │  │  │  NewOrderPanel / CustomerPanel /     │ │ │
│  │ Logout      │  │  │  OrderListPanel / UpdateStatusPanel / │ │ │
│  │             │  │  │  ReportsPanel                        │ │ │
│  │             │  │  └──────────────────────────────────────┘ │ │
│  │             │  │  (All other panels exist but are hidden)  │ │
│  └─────────────┘  └───────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

**Key point:** The sidebar and TopAppBar are permanent. They never change. Only the panel inside `pnlContent` changes.

---

## Section 1 — The Sidebar: Who Owns It, Where It Lives <a name="1"></a>

**The Sidebar belongs to `MainJFrame`. Your panel does NOT have a sidebar.**

The Sidebar (`SidebarPanel`) is placed in the **WEST** slot of `MainJFrame`'s `BorderLayout`. It is created once and stays on screen for the entire session (except during the Login screen, when it is hidden).

### Rules:
- ✅ Your panel may **call** `mainFrame.showCard("CARD_NAME")` to trigger navigation (e.g., a "View All Orders" shortcut button).
- ❌ Your panel must **never** build its own sidebar, its own nav panel, or its own "Go to X" buttons that duplicate what the sidebar does.
- ❌ Your panel must **never** create or manage a `SidebarPanel` object.

> *Reference: `MainJFrame-Requirements.md` FR-8 — "No panel builds its own 'go to X' buttons."*

---

## Section 2 — The CardLayout: How Panel Swapping Works <a name="2"></a>

Think of the center content area (`pnlContent`) as a **deck of cards**. All panels are loaded into the deck at startup and stacked on top of each other. Only one card is visible at a time.

When a sidebar button is clicked:
1. `MainJFrame` calls `showCard("HOME")`.
2. The `CardLayout` flips the deck to reveal `HomePanel`.
3. `MainJFrame` then calls `homePanel.refreshData()` so the data is always fresh.

**What this means for your panel:**
- Your panel always exists in memory, even when hidden.
- Your panel's size is **completely controlled by the CardLayout**. It will always fill 100% of the available center space.
- You never need to tell your panel how big to be.

---

## Section 3 — Panel Sizing Rules (For ALL Panels) <a name="3"></a>

The application launches **maximized** (`setExtendedState(JFrame.MAXIMIZED_BOTH)`). Your panel must handle any screen size gracefully. Follow these rules:

| Rule | Why |
|---|---|
| Never call `setPreferredSize()` on your top-level JPanel | CardLayout overrides it anyway, causing layout conflicts |
| Never call `setSize()` on your top-level JPanel | Same reason |
| Never call `setMaximumSize()` on your top-level JPanel | Same reason |
| Do anchor stretchable components to panel edges | This is how components grow with the window |
| Do leave buttons and labels at fixed sizes | They should never stretch |

---

## Section A — "What size do I set my panel to?" <a name="a"></a>

**You set NO size. Ever.**

`MainJFrame` forces your panel to fill the entire center area automatically through `CardLayout`. If you set a size on your panel, it will fight the `CardLayout` and break your layout.

### DO vs. DON'T:

```java
// ❌ WRONG — Never do this in your panel
public class MyPanel extends javax.swing.JPanel {
    public MyPanel(MainJFrame mainFrame) {
        initComponents();
        this.setPreferredSize(new java.awt.Dimension(800, 600)); // BREAKS LAYOUT
    }
}

// ✅ CORRECT — Let MainJFrame and CardLayout handle it
public class MyPanel extends javax.swing.JPanel {
    public MyPanel(MainJFrame mainFrame) {
        initComponents();
        // No size setting needed. It fills the window automatically.
    }
}
```

### The only exception:
`SidebarPanel` has a fixed width of `240px`. However, this size is set **by `MainJFrame`** on the container it places `SidebarPanel` into — not by `SidebarPanel` itself. `SidebarPanel.java` still sets no size on itself.

---

### Follow-Up: "Does 'set no size' mean I can design my panel however I want?"

**Yes — that assumption is exactly correct.**

When you design your panel in the NetBeans GUI Builder, you are designing it like a **fluid layout**, not a fixed picture frame. The actual pixel dimensions of the canvas you see in the designer during development do not matter — they are just a preview workspace. When your panel is loaded into `MainJFrame`'s `CardLayout`, it is **stretched or shrunk to fill whatever space is available** on the user's actual screen.

This means you can lay out your form fields, tables, buttons, and labels in any arrangement that fits your panel's purpose. `MainJFrame` will size the whole panel to fill the screen for you automatically. You design for your content; `MainJFrame` handles the frame.

### The one thing that bridges your design to the real screen

Your only responsibility regarding sizing is making sure your components **behave correctly** when the panel stretches:

- **Things that should grow** (tables, text areas, scroll panes) → anchor them to the panel edges in the GUI Builder so they stretch.
- **Things that should stay fixed** (buttons, labels, titles) → do NOT anchor them. They stay at their designed size.

If you do those two things correctly, your layout will look correct at any screen resolution.

### Typography still matters

Following the font sizing conventions from `UI_Requirements_and_Regulations.md` Section 5 (Panel title: Bold 18pt, field labels: 13pt, table content: 12pt) is what ensures **visual consistency** across all panels, regardless of what each panel's content is. Your panel may look completely different from another teammate's panel — but if both follow the same font and spacing conventions, the app will feel cohesive.

### In short:
> Design your panel for your content. `MainJFrame` handles the frame. You handle the contents. As long as your stretchable components are anchored and your fixed components are not, your layout will work on any screen.

---

## Section B — "How do I make elements resize like HomePanel?" <a name="b"></a>

There are two techniques depending on which layout manager your panel uses.

### Technique 1: GroupLayout (Free Design — the NetBeans default)

This is what NetBeans uses when you drag and drop components freely. Elements do **not** resize automatically by default — you must tell them to.

**In the NetBeans GUI Builder:**
1. Drop your component (e.g., a `JScrollPane` with a `JTable`) onto the panel.
2. Click the component to select it.
3. Grab the **right edge resize handle** (the small arrow on the right side) and drag it to the **right edge of the panel**. → The component now stretches horizontally.
4. Grab the **bottom edge resize handle** and drag it to the **bottom edge of the panel**. → The component now stretches vertically.

**The rule:** If you anchor it to an edge, it stretches. If you don't anchor it, it stays fixed.

**Component sizing reference table:**

| Component | Horizontal | Vertical | Why |
|---|---|---|---|
| `JScrollPane` + `JTable` | Stretch (anchor to right edge) | Stretch (anchor to bottom edge) | Tables must fill all available space |
| `JTextField`, `JPasswordField` | Stretch (anchor to right edge) | Fixed (do NOT anchor bottom) | Fields grow wider but not taller |
| `JTextArea` in `JScrollPane` | Stretch | Stretch | Same as table |
| `JButton` | Fixed | Fixed | Buttons never stretch |
| `JLabel` (title/header) | Fixed | Fixed | Labels do not resize |
| `JComboBox` | Stretch | Fixed | Usually grows wider |

### Technique 2: BorderLayout (HomePanel-style)

If your panel uses `BorderLayout` (set by right-clicking the panel in Navigator → Set Layout → Border Layout), resizing is automatic:

- Anything in the **CENTER** slot stretches **in all directions** to fill all remaining space. You never need to anchor it.
- Anything in **NORTH**, **SOUTH**, **WEST**, or **EAST** stays at its natural (preferred) size. It never grows or shrinks in its constrained direction.

```
NORTH  → stays at its natural height, stretches to full width
SOUTH  → stays at its natural height, stretches to full width
WEST   → stays at its natural width, stretches to full height
EAST   → stays at its natural width, stretches to full height
CENTER → stretches to fill ALL remaining space
```

This is exactly why `HomePanel` places the table in the CENTER — zero extra work needed to make it fill the screen.

---

## Section C — "What does MainJFrame handle vs. what do I handle?" <a name="c"></a>

This is the clearest line of responsibility in the entire project.

| `MainJFrame` Handles | Your Panel Handles |
|---|---|
| The sidebar (`SidebarPanel`) and all navigation buttons | All form fields, data tables, and task buttons (`btnSave`, `btnCancel`, `btnUpdate`) |
| Showing and hiding the sidebar on login/logout | Querying the database and populating your own components |
| Switching which panel is visible (`showCard()`) | Calling `mainFrame.showCard("X")` only when a shortcut action requires it |
| The TopAppBar (title bar with "Laundry Service Management" and user profile) | Its own internal layout and sub-panels |
| Maximizing the window to full-screen on startup | Anchoring your components to your own panel edges |
| Calling `refreshData()` on your panel every time it becomes visible | Implementing `refreshData()` to re-run all database queries |
| Passing itself into your constructor as `mainFrame` | Storing `mainFrame` as a private field and using it to call `showCard()` or `getCurrentEmployeeId()` |
| Instantiating every panel exactly once at startup | Accepting `mainFrame` in your constructor and NOT instantiating other panels |

**The simple rule:** If it involves the window frame itself, navigation, or the session — that's `MainJFrame`. If it involves your screen's data, forms, or buttons — that's yours.

---

## Section D — "Will following these rules work for any panel design?" <a name="d"></a>

Yes — but you need to understand the **Tier system**.

The rules in this project form a floor that every panel must pass. Above that floor, you choose the right template for your design.

### Tier 1 — Mandatory for ALL panels (no exceptions)

These rules apply to every single panel, no matter what it looks like:

- ✅ No `setPreferredSize()` / `setSize()` on your top-level JPanel
- ✅ Constructor must accept `MainJFrame mainFrame` as a parameter
- ✅ Must implement `refreshData()` method
- ✅ All database calls inside `try-catch` with user-friendly error dialogs
- ✅ Component names use prefix conventions (`btn`, `lbl`, `txt`, `tbl`, `pnl`, `scr`)
- ✅ No navigation buttons built inside your panel
- ✅ No standalone `main()` method before merging to GitHub
- ✅ All money values use `BigDecimal`, not `double` or `float`
- ✅ `JTable` always inside a `JScrollPane`

### Tier 2 — Standard 3-Zone Template (for form-style panels)

Use this template if your panel contains **input fields, a table, and action buttons (Save, Update, Cancel)**. This applies to:
- `NewOrderPanel`
- `CustomerPanel`
- `OrderListPanel`
- `UpdateStatusPanel`
- `ReportsPanel`

```
┌─────────────────────────────────────────┐
│  pnlHeader — Panel title (Bold, 18pt)  │  ← Fixed height, never stretches
├─────────────────────────────────────────┤
│                                         │
│  pnlContent — form fields / JTable     │  ← Stretches to fill ALL remaining space
│                                         │
├─────────────────────────────────────────┤
│  lblStatus          [Cancel]  [Save]   │  ← Fixed height, never stretches
└─────────────────────────────────────────┘
```

In NetBeans Free Design: anchor `pnlContent` to the right and bottom edges of the outer panel.

### Tier 3 — Custom Dashboard Layout (HomePanel & SidebarPanel)

Use this if your panel is a **dashboard-style screen** with statistics, icons, and a full-height table — not a form.

- `HomePanel` uses `BorderLayout` with NORTH for the stat grid and CENTER for the table. See `references/Operations Overview/Review.md` for the step-by-step NetBeans build guide.
- `SidebarPanel` uses `BoxLayout Y-Axis`. See `Review.md` for full instructions.
- **Tier 1 rules still apply 100%.** Tier 3 is NOT a bypass of Tier 1.

### Decision Tree

```
Does my panel have input fields + action buttons (Save/Cancel)?
    → YES → Use Tier 2 (3-zone template)
    → NO → Is it a full-screen dashboard with stats and a large table?
                → YES → Use Tier 3 (BorderLayout, see Review.md)
                → NO → Contact the team lead
```

---

## Section 4 — Standard 3-Zone Panel Template (Tier 2 Panels) <a name="4"></a>

All Tier 2 panels must follow this structure. It creates a consistent, professional look.

```
┌─────────────────────────────────────────┐
│  lblTitle — "Panel Name" (Bold, 18pt)  │  ← pnlHeader
│  Thin separator line                   │
├─────────────────────────────────────────┤
│                                         │
│   Form fields / JTable / main content  │  ← pnlContent (STRETCHES)
│                                         │
├─────────────────────────────────────────┤
│  lblStatus / lblError   [Cancel][Save] │  ← pnlFooter
└─────────────────────────────────────────┘
```

### How to build this in NetBeans (Free Design):

1. Drop three `JPanel`s onto your root panel. Name them `pnlHeader`, `pnlContent`, `pnlFooter`.
2. Place `pnlHeader` at the top. Do NOT anchor its bottom edge — it stays at a fixed height.
3. Place `pnlFooter` at the bottom. Do NOT anchor its top edge — it stays at a fixed height.
4. Place `pnlContent` in the middle. Anchor its **right edge** to the panel's right edge, and its **bottom edge** to the top of `pnlFooter`. This makes it fill all remaining space.

### Spacing rules:
- Minimum `16px` gap between panel edge and first component inside.
- Minimum `8px` gap between any two components.
- Action buttons (`btnSave`, `btnCancel`) must be in the **bottom-right** of `pnlFooter`.
- Status labels (`lblStatus`) must be in the **bottom-left** of `pnlFooter`.

---

## Section 5 — The Gold-Standard Exception: HomePanel & SidebarPanel <a name="5"></a>

`HomePanel` and `SidebarPanel` use a different layout from Tier 2. They are the app's primary interface.

**Full step-by-step NetBeans build instructions are in:**
`references/Operations Overview/Review.md`

### Summary of their layouts:

**`HomePanel`** uses `BorderLayout`:
- `NORTH`: A `JPanel` with `GridLayout(1, 5)` containing 5 stat card sub-panels.
- `CENTER`: A `JScrollPane` wrapping a `JTable` — automatically fills all remaining height.
- Background: `#f9f9f9` (`RGB [249, 249, 249]`).

**`SidebarPanel`** uses `BoxLayout Y-Axis`:
- Background: `#2b59c3` (`RGB [43, 89, 195]`).
- Contains: logo `JLabel`, "Laundry Service" title (Playfair Display font), location `JLabel`, nav `JButton`s styled with FlatLaf `arc: 12`.
- Active button background: `#3bd0fd` with foreground `#00566c`.

---

## Section 6 — The TopAppBar: Who Owns It <a name="6"></a>

The TopAppBar — the white horizontal bar at the top containing "Laundry Service Management", the green "System Operational" dot, and the user's name/avatar — belongs entirely to **`MainJFrame`**.

- It is placed in the **NORTH** slot of `MainJFrame`'s `BorderLayout`.
- It has a bottom border: `MatteBorder`, 1px bottom, color `#c3c6d7`.
- **No individual panel should recreate or include this bar.**

Your panel's content begins directly below the TopAppBar. You do not need to account for it in your layout — `BorderLayout` handles the spacing automatically.

---

## Section 7 — Full-Screen Resizing Checklist <a name="7"></a>

Run through this before committing your panel. If any answer is "No", fix it before pushing.

| Check | Expected Answer |
|---|---|
| Does your top-level JPanel call `setPreferredSize()`? | ❌ No |
| Does your top-level JPanel call `setSize()`? | ❌ No |
| Is every `JTable` inside a `JScrollPane`? | ✅ Yes |
| Does your `JScrollPane` anchor to the right edge of its container? | ✅ Yes |
| Does your `JScrollPane` anchor to the bottom edge of its container? | ✅ Yes |
| Do buttons have fixed sizes (not anchored to any edge)? | ✅ Yes |
| Do text fields anchor to the right edge (stretching wider)? | ✅ Yes |
| Does your panel look correct when the window is resized to a small size? | ✅ Yes |
| Does your panel look correct when the window is maximized? | ✅ Yes |
| Is the constructor accepting `MainJFrame mainFrame`? | ✅ Yes |
| Is `refreshData()` implemented? | ✅ Yes |

---

*Cross-references:*
*— `UI_Requirements_and_Regulations.md` (Component conventions, GroupLayout rules, 3-zone template)*
*— `MainJFrame-Requirements.md` (FR-3, FR-8, sidebar ownership)*
*— `references/Operations Overview/Review.md` (HomePanel and SidebarPanel step-by-step build guide)*
*— FlatLaf Documentation: https://www.formdev.com/flatlaf/*
