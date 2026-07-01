-- Happy path seed data for Laundry Service POS
-- Covers the clean order lifecycle: Pending → Processing → Ready → Claimed
-- Employees not included, add manually via phpMyAdmin

USE laundry_service_db;

-- -----------------------------------------------
-- CUSTOMERS
-- -----------------------------------------------
INSERT IGNORE INTO Customers (name, phone, address, is_active) VALUES
('Maria Santos',      '09171234501', 'Brgy. Paulba, Ligao City, Albay',        1),
('Juan dela Cruz',    '09281234502', 'Brgy. Tinago, Ligao City, Albay',         1),
('Ana Reyes',         '09391234503', 'Brgy. Sta. Cruz, Ligao City, Albay',      1),
('Pedro Villanueva',  '09171234504', 'Brgy. San Francisco, Ligao City, Albay',  1),
('Rosa Mendoza',      '09281234505', 'Brgy. Capucnasan, Ligao City, Albay',     1),
('Carlo Bautista',    '09391234506', 'Brgy. Paulba, Ligao City, Albay',         1);

-- ORDERS
-- employee_id is NULL until employees are seeded
-- service_id 1 = Full Service (175.0000), seeded by init_db.sql
-- Shows clean lifecycle across all four statuses
INSERT INTO Orders (claim_number, customer_id, employee_id, service_id, order_date, ready_at, claimed_at, weight_kg, price_at_order, total_amount, payment_status, order_status, notes)
VALUES

-- Pending / Unpaid
('LS-20260625-001',
    (SELECT customer_id FROM Customers WHERE phone = '09171234501'),
    NULL, 1, '2026-06-25 08:00:00', NULL, NULL,
    3.50, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

('LS-20260625-002',
    (SELECT customer_id FROM Customers WHERE phone = '09281234502'),
    NULL, 1, '2026-06-25 09:30:00', NULL, NULL,
    5.00, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

-- Processing / Unpaid
('LS-20260626-001',
    (SELECT customer_id FROM Customers WHERE phone = '09391234503'),
    NULL, 1, '2026-06-26 08:15:00', NULL, NULL,
    4.00, 175.0000, 175.0000, 'Unpaid', 'Processing', 'No fabric softener'),

('LS-20260626-002',
    (SELECT customer_id FROM Customers WHERE phone = '09171234504'),
    NULL, 1, '2026-06-26 10:00:00', NULL, NULL,
    6.50, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),

-- Ready / Unpaid
('LS-20260627-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234505'),
    NULL, 1, '2026-06-27 08:00:00', '2026-06-27 14:00:00', NULL,
    2.50, 175.0000, 175.0000, 'Unpaid', 'Ready', NULL),

-- Ready / Paid
('LS-20260627-002',
    (SELECT customer_id FROM Customers WHERE phone = '09391234506'),
    NULL, 1, '2026-06-27 09:00:00', '2026-06-27 15:00:00', NULL,
    7.00, 175.0000, 175.0000, 'Paid', 'Ready', 'Separate whites'),

-- Claimed / Paid
('LS-20260628-001',
    (SELECT customer_id FROM Customers WHERE phone = '09171234501'),
    NULL, 1, '2026-06-28 08:00:00', '2026-06-28 13:00:00', '2026-06-28 17:00:00',
    4.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

('LS-20260629-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234502'),
    NULL, 1, '2026-06-29 09:00:00', '2026-06-29 14:30:00', '2026-06-30 10:00:00',
    3.00, 175.0000, 175.0000, 'Paid', 'Claimed', 'Handle with care');
