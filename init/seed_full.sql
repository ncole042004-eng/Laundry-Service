-- Comprehensive seed data for Laundry Service POS
-- Covers every order_status, every payment_status combination,
-- orders with and without notes, and correctly populated timestamps
-- Employees not included, add manually via phpMyAdmin

USE laundry_service_db;

-- CUSTOMERS
-- Includes all happy path customers plus additional
INSERT IGNORE INTO Customers (name, phone, address, is_active) VALUES
('Maria Santos',      '09171234501', 'Brgy. Paulba, Ligao City, Albay',        1),
('Juan dela Cruz',    '09281234502', 'Brgy. Tinago, Ligao City, Albay',         1),
('Ana Reyes',         '09391234503', 'Brgy. Sta. Cruz, Ligao City, Albay',      1),
('Pedro Villanueva',  '09171234504', 'Brgy. San Francisco, Ligao City, Albay',  1),
('Rosa Mendoza',      '09281234505', 'Brgy. Capucnasan, Ligao City, Albay',     1),
('Carlo Bautista',    '09391234506', 'Brgy. Paulba, Ligao City, Albay',         1),
('Lita Flores',       '09171234507', 'Brgy. Tinago, Ligao City, Albay',         1),
('Ramon Garcia',      '09281234508', 'Brgy. Sta. Cruz, Ligao City, Albay',      1),
('Elena Torres',      '09391234509', 'Brgy. San Francisco, Ligao City, Albay',  1),
('Bong Ramos',        '09171234510', 'Brgy. Capucnasan, Ligao City, Albay',     1),
('Nena Castillo',     '09281234511', 'Brgy. Paulba, Ligao City, Albay',         1),
('Felix Morales',     '09391234512', 'Brgy. Tinago, Ligao City, Albay',         1);

-- ORDERS
-- employee_id is NULL until employees are seeded
-- service_id 1 = Full Service (175.0000), seeded by init_db.sql
-- Covers all status/payment combinations with realistic timestamps
INSERT INTO Orders (claim_number, customer_id, employee_id, service_id, order_date, ready_at, claimed_at, weight_kg, price_at_order, total_amount, payment_status, order_status, notes)
VALUES

-- Pending / Unpaid (fresh orders, no timestamps beyond order_date)
('LS-20260625-001',
    (SELECT customer_id FROM Customers WHERE phone = '09171234501'),
    NULL, 1, '2026-06-25 08:00:00', NULL, NULL,
    3.50, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

('LS-20260625-002',
    (SELECT customer_id FROM Customers WHERE phone = '09281234502'),
    NULL, 1, '2026-06-25 09:30:00', NULL, NULL,
    5.00, 175.0000, 175.0000, 'Unpaid', 'Pending', 'No bleach'),

('LS-20260625-003',
    (SELECT customer_id FROM Customers WHERE phone = '09171234507'),
    NULL, 1, '2026-06-25 11:00:00', NULL, NULL,
    6.00, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

-- Processing / Unpaid (being washed, no ready_at yet)
('LS-20260626-001',
    (SELECT customer_id FROM Customers WHERE phone = '09391234503'),
    NULL, 1, '2026-06-26 08:15:00', NULL, NULL,
    4.00, 175.0000, 175.0000, 'Unpaid', 'Processing', 'No fabric softener'),

('LS-20260626-002',
    (SELECT customer_id FROM Customers WHERE phone = '09171234504'),
    NULL, 1, '2026-06-26 10:00:00', NULL, NULL,
    6.50, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),

('LS-20260626-003',
    (SELECT customer_id FROM Customers WHERE phone = '09281234508'),
    NULL, 1, '2026-06-26 13:00:00', NULL, NULL,
    2.00, 175.0000, 175.0000, 'Unpaid', 'Processing', 'Delicate cycle only'),

-- Ready / Unpaid (done but not yet paid or claimed)
('LS-20260627-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234505'),
    NULL, 1, '2026-06-27 08:00:00', '2026-06-27 14:00:00', NULL,
    2.50, 175.0000, 175.0000, 'Unpaid', 'Ready', NULL),

('LS-20260627-002',
    (SELECT customer_id FROM Customers WHERE phone = '09391234509'),
    NULL, 1, '2026-06-27 09:30:00', '2026-06-27 15:30:00', NULL,
    5.50, 175.0000, 175.0000, 'Unpaid', 'Ready', 'Hang dry, do not tumble'),

-- Ready / Paid (done and paid, waiting for pickup)
('LS-20260627-003',
    (SELECT customer_id FROM Customers WHERE phone = '09391234506'),
    NULL, 1, '2026-06-27 09:00:00', '2026-06-27 15:00:00', NULL,
    7.00, 175.0000, 175.0000, 'Paid', 'Ready', 'Separate whites'),

('LS-20260627-004',
    (SELECT customer_id FROM Customers WHERE phone = '09171234510'),
    NULL, 1, '2026-06-27 11:00:00', '2026-06-27 17:00:00', NULL,
    3.00, 175.0000, 175.0000, 'Paid', 'Ready', NULL),

-- Claimed / Unpaid (edge case, picked up before payment recorded)
('LS-20260628-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234511'),
    NULL, 1, '2026-06-28 07:30:00', '2026-06-28 12:30:00', '2026-06-28 18:00:00',
    4.00, 175.0000, 175.0000, 'Unpaid', 'Claimed', NULL),

-- Claimed / Paid (fully complete orders)
('LS-20260628-002',
    (SELECT customer_id FROM Customers WHERE phone = '09171234501'),
    NULL, 1, '2026-06-28 08:00:00', '2026-06-28 13:00:00', '2026-06-28 17:00:00',
    4.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

('LS-20260628-003',
    (SELECT customer_id FROM Customers WHERE phone = '09281234502'),
    NULL, 1, '2026-06-28 09:00:00', '2026-06-28 14:00:00', '2026-06-29 09:00:00',
    6.00, 175.0000, 175.0000, 'Paid', 'Claimed', 'Handle with care'),

('LS-20260629-001',
    (SELECT customer_id FROM Customers WHERE phone = '09391234503'),
    NULL, 1, '2026-06-29 08:00:00', '2026-06-29 13:30:00', '2026-06-29 16:00:00',
    3.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

('LS-20260629-002',
    (SELECT customer_id FROM Customers WHERE phone = '09391234512'),
    NULL, 1, '2026-06-29 09:00:00', '2026-06-29 14:30:00', '2026-06-30 10:00:00',
    3.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

('LS-20260629-003',
    (SELECT customer_id FROM Customers WHERE phone = '09171234504'),
    NULL, 1, '2026-06-29 10:00:00', '2026-06-29 16:00:00', '2026-06-30 11:00:00',
    7.00, 175.0000, 175.0000, 'Paid', 'Claimed', 'No fabric softener'),

('LS-20260630-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234505'),
    NULL, 1, '2026-06-30 07:45:00', '2026-06-30 13:00:00', '2026-06-30 15:30:00',
    5.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

('LS-20260630-002',
    (SELECT customer_id FROM Customers WHERE phone = '09171234507'),
    NULL, 1, '2026-06-30 08:30:00', '2026-06-30 14:00:00', '2026-06-30 17:00:00',
    2.50, 175.0000, 175.0000, 'Paid', 'Claimed', 'Separate darks'),

('LS-20260630-003',
    (SELECT customer_id FROM Customers WHERE phone = '09281234508'),
    NULL, 1, '2026-06-30 09:15:00', '2026-06-30 15:00:00', '2026-07-01 09:00:00',
    6.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

('LS-20260630-004',
    (SELECT customer_id FROM Customers WHERE phone = '09391234509'),
    NULL, 1, '2026-06-30 10:00:00', '2026-06-30 16:00:00', '2026-07-01 10:30:00',
    4.00, 175.0000, 175.0000, 'Paid', 'Claimed', 'Handle with care');