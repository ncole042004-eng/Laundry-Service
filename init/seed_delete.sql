-- Removes all seed data inserted by seed_happy.sql and seed_full.sql
-- Safe to run regardless of which seed file was used
-- Does NOT touch the schema, the Services table, or any real data
-- Employees are not touched since they were not seeded via SQL

USE laundry_service_db;

-- Delete orders first (child table, references Customers via FK)
DELETE FROM Orders
WHERE customer_id IN (
    SELECT customer_id FROM Customers
    WHERE phone IN (
        '09171234501',
        '09281234502',
        '09391234503',
        '09171234504',
        '09281234505',
        '09391234506',
        '09171234507',
        '09281234508',
        '09391234509',
        '09171234510',
        '09281234511',
        '09391234512'
    )
);

-- Delete seed customers (parent table, safe to delete after orders are gone)
DELETE FROM Customers
WHERE phone IN (
    '09171234501',
    '09281234502',
    '09391234503',
    '09171234504',
    '09281234505',
    '09391234506',
    '09171234507',
    '09281234508',
    '09391234509',
    '09171234510',
    '09281234511',
    '09391234512'
);
