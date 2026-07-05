import random

dates = ['2026-07-06', '2026-07-07', '2026-07-08', '2026-07-10', '2026-07-11', '2026-07-12', '2026-07-13']
employees = ['Cral', 'Yochie', 'Imeaa', 'Iyah', 'Nics', 'Rodzkie', 'Jai']
customers = ['09171234501', '09281234502', '09391234503', '09171234504', '09281234505', '09391234506', '09171234507', '09281234508', '09391234509', '09171234510', '09281234511', '09391234512']
statuses = [('Pending', 'Unpaid'), ('Processing', 'Unpaid'), ('Ready', 'Paid'), ('Claimed', 'Paid')]

with open('output.sql', 'w') as f:
    for d in dates:
        num_orders = random.randint(10, 15)
        for i in range(1, num_orders + 1):
            claim_num = f'LS-{d.replace("-", "")[2:]}-{i:03d}'
            cust = random.choice(customers)
            emp = random.choice(employees)
            status, payment = random.choice(statuses)
            weight = round(random.uniform(2.0, 8.0), 2)
            
            h = random.randint(8, 16)
            order_time = f'{d} {h:02d}:00:00'
            ready_time = f'{d} {h+2:02d}:00:00' if status in ('Ready', 'Claimed') else 'NULL'
            claimed_time = f'{d} {h+3:02d}:00:00' if status == 'Claimed' else 'NULL'
            
            ready_str = f"'{ready_time}'" if ready_time != 'NULL' else 'NULL'
            claimed_str = f"'{claimed_time}'" if claimed_time != 'NULL' else 'NULL'
            
            line = f"('{claim_num}', (SELECT customer_id FROM Customers WHERE phone = '{cust}'), (SELECT employee_id FROM Employees WHERE username = '{emp}'), 1, '{order_time}', {ready_str}, {claimed_str}, {weight:.2f}, 175.0000, 175.0000, '{payment}', '{status}', NULL),\n"
            f.write(line)
