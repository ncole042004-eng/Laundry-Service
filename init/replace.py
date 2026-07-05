with open('seed_full.sql', 'r', encoding='utf-8') as f:
    content = f.read()

target = """-- Future Order (1 week from today: July 13, 2026)
('LS-260713-001', (SELECT customer_id FROM Customers WHERE phone = '09391234506'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-13 08:00:00', NULL, NULL, 5.00, 175.0000, 175.0000, 'Unpaid', 'Pending', 'Future scheduled dropoff');"""

with open('../output.sql', 'r', encoding='utf-8') as f:
    new_orders = f.read().strip()
    
new_orders = new_orders[:-1] + ';'

replacement = "-- Future Orders (7 days of high volume, excluding July 9)\n" + new_orders

content = content.replace(target, replacement)

with open('seed_full.sql', 'w', encoding='utf-8') as f:
    f.write(content)
