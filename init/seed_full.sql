-- Comprehensive seed data for Laundry Service POS
-- Covers every order_status, every payment_status combination,
-- orders with and without notes, and correctly populated timestamps

USE laundry_service_db;

-- -----------------------------------------------
-- EMPLOYEES
-- Passwords are bcrypt-hashed. The plaintext password for ALL accounts is: 12345
-- These use the $2a$ prefix for jBCrypt (org.mindrot:jbcrypt:0.4) compatibility.
-- If BCrypt.checkpw() fails at runtime, regenerate hashes using:
--   BCrypt.hashpw("12345", BCrypt.gensalt())  in a one-off Java main() method.
-- -----------------------------------------------
INSERT IGNORE INTO Employees (name, username, password) VALUES
('Cral',                  'Cral',    '$2a$10$w7gEhgS7nKASRjojx//jr.D.2pqEm9SaPb/A8Fofum.lOirkZNNJG'),
('Yochie',                'Yochie',  '$2a$10$jPMowHjo4SYkb/V6nfR2uelkzpzPtMzJo.GoE4f6Cs62VbIl8XmpO'),
('Imeaa',                 'Imeaa',   '$2a$10$R9D6J/OJZOXvdW/j3rexPOTWMzUXoDv2DISdNVDH3Q/jV3ov1yAbK'),
('Iyah',                  'Iyah',    '$2a$10$otTsFzZifd/RLv86cxvX1.Iy6znYIVCd6.dDlDIdVF0ZNJoC3I/.W'),
('Nics',                  'Nics',    '$2a$10$ILK9a.93YgwJTFLV.2NYbOZS1AKGDjx2.wmFSYZk0HMpFXzE29n1O'),
('Rodzkie',               'Rodzkie', '$2a$10$ZsG7ZV3iYbriNijOLgSydeqb.hG0XFgjxk.jFQ8Ib.qFA5BQWfPaq'),
('Jai',                   'Jai',     '$2a$10$b.PeO7oAVnaKgVeyG.4HdOXqPr6elQqeEXI5tJTAD5AZjQ/fGGYhK');

-- -----------------------------------------------
-- CUSTOMERS
-- Includes all happy path customers plus additional
-- -----------------------------------------------
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

-- -----------------------------------------------
-- ORDERS
-- service_id 1 = Full Service (175.0000), seeded by init_db.sql
-- Covers all status/payment combinations with realistic timestamps
-- employee_id is looked up by username to be safe against auto-increment shifts
-- -----------------------------------------------
INSERT INTO Orders (claim_number, customer_id, employee_id, service_id, order_date, ready_at, claimed_at, weight_kg, price_at_order, total_amount, payment_status, order_status, notes)
VALUES

-- Pending / Unpaid — taken by Cral
('LS-260625-001',
    (SELECT customer_id FROM Customers WHERE phone = '09171234501'),
    (SELECT employee_id FROM Employees WHERE username = 'Cral'),
    1, '2026-06-25 08:00:00', NULL, NULL,
    3.50, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

-- Pending / Unpaid — taken by Yochie
('LS-260625-002',
    (SELECT customer_id FROM Customers WHERE phone = '09281234502'),
    (SELECT employee_id FROM Employees WHERE username = 'Yochie'),
    1, '2026-06-25 09:30:00', NULL, NULL,
    5.00, 175.0000, 175.0000, 'Unpaid', 'Pending', 'No bleach'),

-- Pending / Unpaid — taken by Imeaa
('LS-260625-003',
    (SELECT customer_id FROM Customers WHERE phone = '09171234507'),
    (SELECT employee_id FROM Employees WHERE username = 'Imeaa'),
    1, '2026-06-25 11:00:00', NULL, NULL,
    6.00, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

-- Processing / Unpaid — taken by Iyah
('LS-260626-001',
    (SELECT customer_id FROM Customers WHERE phone = '09391234503'),
    (SELECT employee_id FROM Employees WHERE username = 'Iyah'),
    1, '2026-06-26 08:15:00', NULL, NULL,
    4.00, 175.0000, 175.0000, 'Unpaid', 'Processing', 'No fabric softener'),

-- Processing / Unpaid — taken by Nics
('LS-260626-002',
    (SELECT customer_id FROM Customers WHERE phone = '09171234504'),
    (SELECT employee_id FROM Employees WHERE username = 'Nics'),
    1, '2026-06-26 10:00:00', NULL, NULL,
    6.50, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),

-- Processing / Unpaid — taken by Rodzkie
('LS-260626-003',
    (SELECT customer_id FROM Customers WHERE phone = '09281234508'),
    (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'),
    1, '2026-06-26 13:00:00', NULL, NULL,
    2.00, 175.0000, 175.0000, 'Unpaid', 'Processing', 'Delicate cycle only'),

-- Ready / Unpaid — taken by Jai
('LS-260627-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234505'),
    (SELECT employee_id FROM Employees WHERE username = 'Jai'),
    1, '2026-06-27 08:00:00', '2026-06-27 14:00:00', NULL,
    2.50, 175.0000, 175.0000, 'Unpaid', 'Ready', NULL),

-- Ready / Unpaid — taken by Cral
('LS-260627-002',
    (SELECT customer_id FROM Customers WHERE phone = '09391234509'),
    (SELECT employee_id FROM Employees WHERE username = 'Cral'),
    1, '2026-06-27 09:30:00', '2026-06-27 15:30:00', NULL,
    5.50, 175.0000, 175.0000, 'Unpaid', 'Ready', 'Hang dry, do not tumble'),

-- Ready / Paid — taken by Yochie
('LS-260627-003',
    (SELECT customer_id FROM Customers WHERE phone = '09391234506'),
    (SELECT employee_id FROM Employees WHERE username = 'Yochie'),
    1, '2026-06-27 09:00:00', '2026-06-27 15:00:00', NULL,
    7.00, 175.0000, 175.0000, 'Paid', 'Ready', 'Separate whites'),

-- Ready / Paid — taken by Imeaa
('LS-260627-004',
    (SELECT customer_id FROM Customers WHERE phone = '09171234510'),
    (SELECT employee_id FROM Employees WHERE username = 'Imeaa'),
    1, '2026-06-27 11:00:00', '2026-06-27 17:00:00', NULL,
    3.00, 175.0000, 175.0000, 'Paid', 'Ready', NULL),

-- Claimed / Paid — taken by Iyah
('LS-260628-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234511'),
    (SELECT employee_id FROM Employees WHERE username = 'Iyah'),
    1, '2026-06-28 07:30:00', '2026-06-28 12:30:00', '2026-06-28 18:00:00',
    4.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

-- Claimed / Paid — taken by Nics
('LS-260628-002',
    (SELECT customer_id FROM Customers WHERE phone = '09171234501'),
    (SELECT employee_id FROM Employees WHERE username = 'Nics'),
    1, '2026-06-28 08:00:00', '2026-06-28 13:00:00', '2026-06-28 17:00:00',
    4.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

-- Claimed / Paid — taken by Rodzkie
('LS-260628-003',
    (SELECT customer_id FROM Customers WHERE phone = '09281234502'),
    (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'),
    1, '2026-06-28 09:00:00', '2026-06-28 14:00:00', '2026-06-29 09:00:00',
    6.00, 175.0000, 175.0000, 'Paid', 'Claimed', 'Handle with care'),

-- Claimed / Paid — taken by Jai
('LS-260629-001',
    (SELECT customer_id FROM Customers WHERE phone = '09391234503'),
    (SELECT employee_id FROM Employees WHERE username = 'Jai'),
    1, '2026-06-29 08:00:00', '2026-06-29 13:30:00', '2026-06-29 16:00:00',
    3.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

-- Claimed / Paid — taken by Cral
('LS-260629-002',
    (SELECT customer_id FROM Customers WHERE phone = '09391234512'),
    (SELECT employee_id FROM Employees WHERE username = 'Cral'),
    1, '2026-06-29 09:00:00', '2026-06-29 14:30:00', '2026-06-30 10:00:00',
    3.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

-- Claimed / Paid — taken by Yochie
('LS-260629-003',
    (SELECT customer_id FROM Customers WHERE phone = '09171234504'),
    (SELECT employee_id FROM Employees WHERE username = 'Yochie'),
    1, '2026-06-29 10:00:00', '2026-06-29 16:00:00', '2026-06-30 11:00:00',
    7.00, 175.0000, 175.0000, 'Paid', 'Claimed', 'No fabric softener'),

-- Claimed / Paid — taken by Imeaa
('LS-260630-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234505'),
    (SELECT employee_id FROM Employees WHERE username = 'Imeaa'),
    1, '2026-06-30 07:45:00', '2026-06-30 13:00:00', '2026-06-30 15:30:00',
    5.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

-- Claimed / Paid — taken by Iyah
('LS-260630-002',
    (SELECT customer_id FROM Customers WHERE phone = '09171234507'),
    (SELECT employee_id FROM Employees WHERE username = 'Iyah'),
    1, '2026-06-30 08:30:00', '2026-06-30 14:00:00', '2026-06-30 17:00:00',
    2.50, 175.0000, 175.0000, 'Paid', 'Claimed', 'Separate darks'),

-- Claimed / Paid — taken by Nics
('LS-260630-003',
    (SELECT customer_id FROM Customers WHERE phone = '09281234508'),
    (SELECT employee_id FROM Employees WHERE username = 'Nics'),
    1, '2026-06-30 09:15:00', '2026-06-30 15:00:00', '2026-07-01 09:00:00',
    6.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

-- Claimed / Paid — taken by Rodzkie
('LS-260630-004',
    (SELECT customer_id FROM Customers WHERE phone = '09391234509'),
    (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'),
    1, '2026-06-30 10:00:00', '2026-06-30 16:00:00', '2026-07-01 10:30:00',
    4.00, 175.0000, 175.0000, 'Paid', 'Claimed', 'Handle with care');


-- -----------------------------------------------
-- HISTORICAL & FUTURE ORDERS (For testing analytics)
-- -----------------------------------------------
INSERT INTO Orders (claim_number, customer_id, employee_id, service_id, order_date, ready_at, claimed_at, weight_kg, price_at_order, total_amount, payment_status, order_status, notes)
VALUES

-- Historical Orders (1 per month from Jan 2024 to May 2026)
('LS-240115-001', (SELECT customer_id FROM Customers WHERE phone = '09171234501'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2024-01-15 09:00:00', '2024-01-15 15:00:00', '2024-01-16 10:00:00', 5.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-240215-001', (SELECT customer_id FROM Customers WHERE phone = '09281234502'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2024-02-15 09:00:00', '2024-02-15 15:00:00', '2024-02-16 10:00:00', 4.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-240315-001', (SELECT customer_id FROM Customers WHERE phone = '09391234503'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2024-03-15 09:00:00', '2024-03-15 15:00:00', '2024-03-16 10:00:00', 6.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-240415-001', (SELECT customer_id FROM Customers WHERE phone = '09171234504'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2024-04-15 09:00:00', '2024-04-15 15:00:00', '2024-04-16 10:00:00', 3.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-240515-001', (SELECT customer_id FROM Customers WHERE phone = '09281234505'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2024-05-15 09:00:00', '2024-05-15 15:00:00', '2024-05-16 10:00:00', 4.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-240615-001', (SELECT customer_id FROM Customers WHERE phone = '09391234506'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2024-06-15 09:00:00', '2024-06-15 15:00:00', '2024-06-16 10:00:00', 5.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-240715-001', (SELECT customer_id FROM Customers WHERE phone = '09171234507'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2024-07-15 09:00:00', '2024-07-15 15:00:00', '2024-07-16 10:00:00', 2.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-240815-001', (SELECT customer_id FROM Customers WHERE phone = '09281234508'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2024-08-15 09:00:00', '2024-08-15 15:00:00', '2024-08-16 10:00:00', 7.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-240915-001', (SELECT customer_id FROM Customers WHERE phone = '09391234509'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2024-09-15 09:00:00', '2024-09-15 15:00:00', '2024-09-16 10:00:00', 3.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-241015-001', (SELECT customer_id FROM Customers WHERE phone = '09171234510'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2024-10-15 09:00:00', '2024-10-15 15:00:00', '2024-10-16 10:00:00', 4.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-241115-001', (SELECT customer_id FROM Customers WHERE phone = '09281234511'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2024-11-15 09:00:00', '2024-11-15 15:00:00', '2024-11-16 10:00:00', 5.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-241215-001', (SELECT customer_id FROM Customers WHERE phone = '09391234512'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2024-12-15 09:00:00', '2024-12-15 15:00:00', '2024-12-16 10:00:00', 6.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-250115-001', (SELECT customer_id FROM Customers WHERE phone = '09171234501'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2025-01-15 09:00:00', '2025-01-15 15:00:00', '2025-01-16 10:00:00', 3.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-250215-001', (SELECT customer_id FROM Customers WHERE phone = '09281234502'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2025-02-15 09:00:00', '2025-02-15 15:00:00', '2025-02-16 10:00:00', 4.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-250315-001', (SELECT customer_id FROM Customers WHERE phone = '09391234503'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2025-03-15 09:00:00', '2025-03-15 15:00:00', '2025-03-16 10:00:00', 5.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-250415-001', (SELECT customer_id FROM Customers WHERE phone = '09171234504'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2025-04-15 09:00:00', '2025-04-15 15:00:00', '2025-04-16 10:00:00', 2.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-250515-001', (SELECT customer_id FROM Customers WHERE phone = '09281234505'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2025-05-15 09:00:00', '2025-05-15 15:00:00', '2025-05-16 10:00:00', 6.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-250615-001', (SELECT customer_id FROM Customers WHERE phone = '09391234506'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2025-06-15 09:00:00', '2025-06-15 15:00:00', '2025-06-16 10:00:00', 7.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-250715-001', (SELECT customer_id FROM Customers WHERE phone = '09171234507'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2025-07-15 09:00:00', '2025-07-15 15:00:00', '2025-07-16 10:00:00', 3.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-250815-001', (SELECT customer_id FROM Customers WHERE phone = '09281234508'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2025-08-15 09:00:00', '2025-08-15 15:00:00', '2025-08-16 10:00:00', 4.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-250915-001', (SELECT customer_id FROM Customers WHERE phone = '09391234509'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2025-09-15 09:00:00', '2025-09-15 15:00:00', '2025-09-16 10:00:00', 5.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-251015-001', (SELECT customer_id FROM Customers WHERE phone = '09171234510'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2025-10-15 09:00:00', '2025-10-15 15:00:00', '2025-10-16 10:00:00', 6.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-251115-001', (SELECT customer_id FROM Customers WHERE phone = '09281234511'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2025-11-15 09:00:00', '2025-11-15 15:00:00', '2025-11-16 10:00:00', 3.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-251215-001', (SELECT customer_id FROM Customers WHERE phone = '09391234512'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2025-12-15 09:00:00', '2025-12-15 15:00:00', '2025-12-16 10:00:00', 4.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-260115-001', (SELECT customer_id FROM Customers WHERE phone = '09171234501'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-01-15 09:00:00', '2026-01-15 15:00:00', '2026-01-16 10:00:00', 5.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-260215-001', (SELECT customer_id FROM Customers WHERE phone = '09281234502'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2026-02-15 09:00:00', '2026-02-15 15:00:00', '2026-02-16 10:00:00', 2.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-260315-001', (SELECT customer_id FROM Customers WHERE phone = '09391234503'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2026-03-15 09:00:00', '2026-03-15 15:00:00', '2026-03-16 10:00:00', 6.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-260415-001', (SELECT customer_id FROM Customers WHERE phone = '09171234504'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2026-04-15 09:00:00', '2026-04-15 15:00:00', '2026-04-16 10:00:00', 7.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-260515-001', (SELECT customer_id FROM Customers WHERE phone = '09281234505'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-05-15 09:00:00', '2026-05-15 15:00:00', '2026-05-16 10:00:00', 3.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL);

-- -----------------------------------------------
-- JULY 5-13, 2026 ORDERS
-- Every day has a distinct order count and distinct total revenue
-- (varied via realistic Philippine laundry add-on charges in notes),
-- so day-over-day trend percentages on HomePanel show real, varied movement.
-- -----------------------------------------------
INSERT INTO Orders (claim_number, customer_id, employee_id, service_id, order_date, ready_at, claimed_at, weight_kg, price_at_order, total_amount, payment_status, order_status, notes)
VALUES

-- 2026-07-05: 10 orders, total ₱1,906.00
('LS-260705-001', (SELECT customer_id FROM Customers WHERE phone = '09171234501'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-07-05 10:00:00', NULL, NULL, 5.77, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),
('LS-260705-002', (SELECT customer_id FROM Customers WHERE phone = '09281234502'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-05 15:00:00', '2026-07-05 17:00:00', '2026-07-05 20:00:00', 2.82, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-260705-003', (SELECT customer_id FROM Customers WHERE phone = '09391234503'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2026-07-05 15:00:00', NULL, NULL, 4.49, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),
('LS-260705-004', (SELECT customer_id FROM Customers WHERE phone = '09171234504'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-07-05 14:00:00', NULL, NULL, 5.55, 175.0000, 195.0000, 'Unpaid', 'Processing', 'Delicate Wash Handling Fee +₱20'),
('LS-260705-005', (SELECT customer_id FROM Customers WHERE phone = '09281234505'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2026-07-05 12:00:00', '2026-07-05 15:00:00', NULL, 5.48, 175.0000, 211.0000, 'Unpaid', 'Ready', 'Extra Fabric Conditioner (Downy Passion) +₱11; Stain Treatment (Grease/Oil) +₱25'),
('LS-260705-006', (SELECT customer_id FROM Customers WHERE phone = '09391234506'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2026-07-05 08:00:00', NULL, NULL, 2.92, 175.0000, 205.0000, 'Unpaid', 'Pending', 'Fragrance Booster (Surf Fresh) +₱15; Extra Rinse Cycle +₱15'),
('LS-260705-007', (SELECT customer_id FROM Customers WHERE phone = '09171234507'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2026-07-05 07:00:00', NULL, NULL, 4.12, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),
('LS-260705-008', (SELECT customer_id FROM Customers WHERE phone = '09281234508'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-07-05 11:00:00', NULL, NULL, 6.31, 175.0000, 215.0000, 'Unpaid', 'Pending', 'Comforter/Blanket Surcharge +₱40'),
('LS-260705-009', (SELECT customer_id FROM Customers WHERE phone = '09391234509'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-05 08:00:00', '2026-07-05 10:00:00', NULL, 6.89, 175.0000, 190.0000, 'Paid', 'Ready', 'Fragrance Booster (Surf Fresh) +₱15'),
('LS-260705-010', (SELECT customer_id FROM Customers WHERE phone = '09171234510'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2026-07-05 08:00:00', '2026-07-05 11:00:00', '2026-07-05 13:00:00', 5.90, 175.0000, 190.0000, 'Paid', 'Claimed', 'Extra Rinse Cycle +₱15'),

-- 2026-07-06: 10 orders, total ₱1,860.00
('LS-260706-001', (SELECT customer_id FROM Customers WHERE phone = '09281234511'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-07-06 11:00:00', NULL, NULL, 4.68, 175.0000, 215.0000, 'Unpaid', 'Processing', 'Stain Treatment (Grease/Oil) +₱25; Fragrance Booster (Surf Fresh) +₱15'),
('LS-260706-002', (SELECT customer_id FROM Customers WHERE phone = '09391234512'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2026-07-06 09:00:00', '2026-07-06 13:00:00', NULL, 6.14, 175.0000, 190.0000, 'Unpaid', 'Ready', 'Extra Rinse Cycle +₱15'),
('LS-260706-003', (SELECT customer_id FROM Customers WHERE phone = '09171234501'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2026-07-06 15:00:00', NULL, NULL, 6.30, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),
('LS-260706-004', (SELECT customer_id FROM Customers WHERE phone = '09281234502'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2026-07-06 07:00:00', '2026-07-06 09:00:00', '2026-07-06 10:00:00', 3.30, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-260706-005', (SELECT customer_id FROM Customers WHERE phone = '09391234503'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-07-06 16:00:00', NULL, NULL, 6.75, 175.0000, 190.0000, 'Unpaid', 'Processing', 'Extra Rinse Cycle +₱15'),
('LS-260706-006', (SELECT customer_id FROM Customers WHERE phone = '09171234504'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-06 13:00:00', '2026-07-06 17:00:00', NULL, 7.17, 175.0000, 175.0000, 'Unpaid', 'Ready', NULL),
('LS-260706-007', (SELECT customer_id FROM Customers WHERE phone = '09281234505'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2026-07-06 15:00:00', NULL, NULL, 4.71, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),
('LS-260706-008', (SELECT customer_id FROM Customers WHERE phone = '09391234506'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-07-06 13:00:00', '2026-07-06 16:00:00', '2026-07-06 17:00:00', 5.18, 175.0000, 215.0000, 'Paid', 'Claimed', 'Comforter/Blanket Surcharge +₱40'),
('LS-260706-009', (SELECT customer_id FROM Customers WHERE phone = '09171234507'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2026-07-06 07:00:00', '2026-07-06 09:00:00', NULL, 7.88, 175.0000, 175.0000, 'Paid', 'Ready', NULL),
('LS-260706-010', (SELECT customer_id FROM Customers WHERE phone = '09281234508'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2026-07-06 16:00:00', '2026-07-06 18:00:00', NULL, 2.90, 175.0000, 175.0000, 'Unpaid', 'Ready', NULL),

-- 2026-07-07: 11 orders, total ₱2,155.00
('LS-260707-001', (SELECT customer_id FROM Customers WHERE phone = '09391234509'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2026-07-07 15:00:00', '2026-07-07 17:00:00', NULL, 4.27, 175.0000, 205.0000, 'Paid', 'Ready', 'Heavy Load Surcharge (+2kg over limit) +₱30'),
('LS-260707-002', (SELECT customer_id FROM Customers WHERE phone = '09171234510'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-07-07 11:00:00', '2026-07-07 15:00:00', '2026-07-07 17:00:00', 6.01, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-260707-003', (SELECT customer_id FROM Customers WHERE phone = '09281234511'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-07 11:00:00', NULL, NULL, 2.66, 175.0000, 205.0000, 'Unpaid', 'Pending', 'Heavy Load Surcharge (+2kg over limit) +₱30'),
('LS-260707-004', (SELECT customer_id FROM Customers WHERE phone = '09391234512'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2026-07-07 11:00:00', '2026-07-07 15:00:00', '2026-07-07 18:00:00', 7.73, 175.0000, 195.0000, 'Paid', 'Claimed', 'Delicate Wash Handling Fee +₱20'),
('LS-260707-005', (SELECT customer_id FROM Customers WHERE phone = '09171234501'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-07-07 15:00:00', '2026-07-07 19:00:00', '2026-07-07 20:00:00', 5.59, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-260707-006', (SELECT customer_id FROM Customers WHERE phone = '09281234502'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2026-07-07 11:00:00', '2026-07-07 13:00:00', '2026-07-07 14:00:00', 5.53, 175.0000, 190.0000, 'Paid', 'Claimed', 'Extra Rinse Cycle +₱15'),
('LS-260707-007', (SELECT customer_id FROM Customers WHERE phone = '09391234503'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2026-07-07 14:00:00', NULL, NULL, 3.42, 175.0000, 190.0000, 'Unpaid', 'Pending', 'Extra Rinse Cycle +₱15'),
('LS-260707-008', (SELECT customer_id FROM Customers WHERE phone = '09171234504'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2026-07-07 14:00:00', NULL, NULL, 6.81, 175.0000, 220.0000, 'Unpaid', 'Pending', 'Delicate Wash Handling Fee +₱20; Stain Treatment (Grease/Oil) +₱25'),
('LS-260707-009', (SELECT customer_id FROM Customers WHERE phone = '09281234505'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-07-07 16:00:00', '2026-07-07 19:00:00', NULL, 7.59, 175.0000, 175.0000, 'Paid', 'Ready', NULL),
('LS-260707-010', (SELECT customer_id FROM Customers WHERE phone = '09391234506'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-07 12:00:00', NULL, NULL, 7.69, 175.0000, 235.0000, 'Unpaid', 'Processing', 'Rush Service (Same-Day) +₱50; Extra Bleach (For Whites) +₱10'),
('LS-260707-011', (SELECT customer_id FROM Customers WHERE phone = '09171234507'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2026-07-07 08:00:00', NULL, NULL, 4.58, 175.0000, 190.0000, 'Unpaid', 'Processing', 'Extra Rinse Cycle +₱15'),

-- 2026-07-08: 12 orders, total ₱2,363.00
('LS-260708-001', (SELECT customer_id FROM Customers WHERE phone = '09281234508'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-07-08 07:00:00', '2026-07-08 09:00:00', NULL, 3.99, 175.0000, 225.0000, 'Paid', 'Ready', 'Rush Service (Same-Day) +₱50'),
('LS-260708-002', (SELECT customer_id FROM Customers WHERE phone = '09391234509'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2026-07-08 07:00:00', NULL, NULL, 6.18, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),
('LS-260708-003', (SELECT customer_id FROM Customers WHERE phone = '09171234510'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2026-07-08 14:00:00', NULL, NULL, 7.07, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),
('LS-260708-004', (SELECT customer_id FROM Customers WHERE phone = '09281234511'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2026-07-08 16:00:00', '2026-07-08 20:00:00', '2026-07-08 21:00:00', 3.26, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-260708-005', (SELECT customer_id FROM Customers WHERE phone = '09391234512'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-07-08 08:00:00', NULL, NULL, 3.43, 175.0000, 215.0000, 'Unpaid', 'Pending', 'Comforter/Blanket Surcharge +₱40'),
('LS-260708-006', (SELECT customer_id FROM Customers WHERE phone = '09171234501'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-08 07:00:00', '2026-07-08 11:00:00', NULL, 5.89, 175.0000, 215.0000, 'Paid', 'Ready', 'Comforter/Blanket Surcharge +₱40'),
('LS-260708-007', (SELECT customer_id FROM Customers WHERE phone = '09281234502'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2026-07-08 12:00:00', NULL, NULL, 5.86, 175.0000, 186.0000, 'Unpaid', 'Processing', 'Extra Fabric Conditioner (Downy Passion) +₱11'),
('LS-260708-008', (SELECT customer_id FROM Customers WHERE phone = '09391234503'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-07-08 15:00:00', NULL, NULL, 6.72, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),
('LS-260708-009', (SELECT customer_id FROM Customers WHERE phone = '09171234504'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2026-07-08 08:00:00', '2026-07-08 11:00:00', NULL, 4.65, 175.0000, 185.0000, 'Paid', 'Ready', 'Extra Bleach (For Whites) +₱10'),
('LS-260708-010', (SELECT customer_id FROM Customers WHERE phone = '09281234505'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2026-07-08 15:00:00', '2026-07-08 17:00:00', NULL, 6.77, 175.0000, 201.0000, 'Unpaid', 'Ready', 'Extra Rinse Cycle +₱15; Extra Fabric Conditioner (Downy Passion) +₱11'),
('LS-260708-011', (SELECT customer_id FROM Customers WHERE phone = '09391234506'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2026-07-08 14:00:00', NULL, NULL, 7.72, 175.0000, 250.0000, 'Unpaid', 'Processing', 'Rush Service (Same-Day) +₱50; Stain Treatment (Grease/Oil) +₱25'),
('LS-260708-012', (SELECT customer_id FROM Customers WHERE phone = '09171234507'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-07-08 07:00:00', NULL, NULL, 3.26, 175.0000, 186.0000, 'Unpaid', 'Pending', 'Extra Fabric Conditioner (Downy Passion) +₱11'),

-- 2026-07-09: 5 orders, total ₱931.00
('LS-260709-001', (SELECT customer_id FROM Customers WHERE phone = '09281234508'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-09 14:00:00', '2026-07-09 17:00:00', '2026-07-09 19:00:00', 7.81, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-260709-002', (SELECT customer_id FROM Customers WHERE phone = '09391234509'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2026-07-09 14:00:00', '2026-07-09 16:00:00', NULL, 6.11, 175.0000, 195.0000, 'Unpaid', 'Ready', 'Delicate Wash Handling Fee +₱20'),
('LS-260709-003', (SELECT customer_id FROM Customers WHERE phone = '09171234510'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-07-09 15:00:00', NULL, NULL, 3.12, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),
('LS-260709-004', (SELECT customer_id FROM Customers WHERE phone = '09281234511'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2026-07-09 14:00:00', NULL, NULL, 2.36, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),
('LS-260709-005', (SELECT customer_id FROM Customers WHERE phone = '09391234512'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2026-07-09 08:00:00', '2026-07-09 10:00:00', '2026-07-09 11:00:00', 4.97, 175.0000, 211.0000, 'Paid', 'Claimed', 'Stain Treatment (Grease/Oil) +₱25; Extra Fabric Conditioner (Downy Passion) +₱11'),

-- 2026-07-10: 15 orders, total ₱2,832.00
('LS-260710-001', (SELECT customer_id FROM Customers WHERE phone = '09171234501'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2026-07-10 16:00:00', NULL, NULL, 5.51, 175.0000, 225.0000, 'Unpaid', 'Processing', 'Rush Service (Same-Day) +₱50'),
('LS-260710-002', (SELECT customer_id FROM Customers WHERE phone = '09281234502'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-07-10 16:00:00', NULL, NULL, 3.45, 175.0000, 190.0000, 'Unpaid', 'Pending', 'Additional Fold & Pack (Individual Bagging) +₱15'),
('LS-260710-003', (SELECT customer_id FROM Customers WHERE phone = '09391234503'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-10 12:00:00', NULL, NULL, 5.33, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),
('LS-260710-004', (SELECT customer_id FROM Customers WHERE phone = '09171234504'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2026-07-10 08:00:00', '2026-07-10 10:00:00', NULL, 3.41, 175.0000, 185.0000, 'Paid', 'Ready', 'Extra Bleach (For Whites) +₱10'),
('LS-260710-005', (SELECT customer_id FROM Customers WHERE phone = '09281234505'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-07-10 15:00:00', '2026-07-10 18:00:00', NULL, 4.70, 175.0000, 205.0000, 'Paid', 'Ready', 'Extra Rinse Cycle +₱15; Additional Fold & Pack (Individual Bagging) +₱15'),
('LS-260710-006', (SELECT customer_id FROM Customers WHERE phone = '09391234506'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2026-07-10 10:00:00', NULL, NULL, 2.78, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),
('LS-260710-007', (SELECT customer_id FROM Customers WHERE phone = '09171234507'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2026-07-10 11:00:00', '2026-07-10 15:00:00', '2026-07-10 18:00:00', 4.18, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-260710-008', (SELECT customer_id FROM Customers WHERE phone = '09281234508'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2026-07-10 08:00:00', NULL, NULL, 5.12, 175.0000, 195.0000, 'Unpaid', 'Processing', 'Delicate Wash Handling Fee +₱20'),
('LS-260710-009', (SELECT customer_id FROM Customers WHERE phone = '09391234509'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-07-10 08:00:00', NULL, NULL, 7.54, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),
('LS-260710-010', (SELECT customer_id FROM Customers WHERE phone = '09171234510'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-10 10:00:00', NULL, NULL, 6.38, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),
('LS-260710-011', (SELECT customer_id FROM Customers WHERE phone = '09281234511'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2026-07-10 11:00:00', '2026-07-10 15:00:00', NULL, 6.23, 175.0000, 175.0000, 'Unpaid', 'Ready', NULL),
('LS-260710-012', (SELECT customer_id FROM Customers WHERE phone = '09391234512'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-07-10 11:00:00', '2026-07-10 13:00:00', NULL, 4.88, 175.0000, 201.0000, 'Unpaid', 'Ready', 'Extra Fabric Conditioner (Downy Passion) +₱11; Extra Rinse Cycle +₱15'),
('LS-260710-013', (SELECT customer_id FROM Customers WHERE phone = '09171234501'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2026-07-10 14:00:00', NULL, NULL, 2.02, 175.0000, 185.0000, 'Unpaid', 'Pending', 'Extra Bleach (For Whites) +₱10'),
('LS-260710-014', (SELECT customer_id FROM Customers WHERE phone = '09281234502'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2026-07-10 09:00:00', NULL, NULL, 5.25, 175.0000, 186.0000, 'Unpaid', 'Pending', 'Extra Fabric Conditioner (Downy Passion) +₱11'),
('LS-260710-015', (SELECT customer_id FROM Customers WHERE phone = '09391234503'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2026-07-10 09:00:00', NULL, NULL, 5.22, 175.0000, 210.0000, 'Unpaid', 'Pending', 'Additional Fold & Pack (Individual Bagging) +₱15; Delicate Wash Handling Fee +₱20'),

-- 2026-07-11: 12 orders, total ₱2,431.00
('LS-260711-001', (SELECT customer_id FROM Customers WHERE phone = '09171234504'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-07-11 10:00:00', '2026-07-11 14:00:00', '2026-07-11 15:00:00', 2.25, 175.0000, 186.0000, 'Paid', 'Claimed', 'Extra Fabric Conditioner (Downy Passion) +₱11'),
('LS-260711-002', (SELECT customer_id FROM Customers WHERE phone = '09281234505'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-11 13:00:00', '2026-07-11 17:00:00', '2026-07-11 20:00:00', 5.93, 175.0000, 195.0000, 'Paid', 'Claimed', 'Delicate Wash Handling Fee +₱20'),
('LS-260711-003', (SELECT customer_id FROM Customers WHERE phone = '09391234506'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2026-07-11 07:00:00', '2026-07-11 09:00:00', '2026-07-11 12:00:00', 2.91, 175.0000, 215.0000, 'Paid', 'Claimed', 'Stain Treatment (Grease/Oil) +₱25; Additional Fold & Pack (Individual Bagging) +₱15'),
('LS-260711-004', (SELECT customer_id FROM Customers WHERE phone = '09171234507'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-07-11 10:00:00', '2026-07-11 13:00:00', '2026-07-11 14:00:00', 7.45, 175.0000, 215.0000, 'Paid', 'Claimed', 'Comforter/Blanket Surcharge +₱40'),
('LS-260711-005', (SELECT customer_id FROM Customers WHERE phone = '09281234508'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2026-07-11 14:00:00', '2026-07-11 16:00:00', '2026-07-11 17:00:00', 6.65, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-260711-006', (SELECT customer_id FROM Customers WHERE phone = '09391234509'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2026-07-11 10:00:00', '2026-07-11 12:00:00', '2026-07-11 13:00:00', 6.82, 175.0000, 185.0000, 'Paid', 'Claimed', 'Extra Bleach (For Whites) +₱10'),
('LS-260711-007', (SELECT customer_id FROM Customers WHERE phone = '09171234510'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2026-07-11 11:00:00', '2026-07-11 14:00:00', '2026-07-11 17:00:00', 5.89, 175.0000, 185.0000, 'Paid', 'Claimed', 'Extra Bleach (For Whites) +₱10'),
('LS-260711-008', (SELECT customer_id FROM Customers WHERE phone = '09281234511'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-07-11 07:00:00', NULL, NULL, 5.01, 175.0000, 195.0000, 'Unpaid', 'Processing', 'Delicate Wash Handling Fee +₱20'),
('LS-260711-009', (SELECT customer_id FROM Customers WHERE phone = '09391234512'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-11 16:00:00', NULL, NULL, 2.68, 175.0000, 210.0000, 'Unpaid', 'Pending', 'Stain Treatment (Grease/Oil) +₱25; Extra Bleach (For Whites) +₱10'),
('LS-260711-010', (SELECT customer_id FROM Customers WHERE phone = '09171234501'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2026-07-11 15:00:00', NULL, NULL, 4.56, 175.0000, 190.0000, 'Unpaid', 'Processing', 'Fragrance Booster (Surf Fresh) +₱15'),
('LS-260711-011', (SELECT customer_id FROM Customers WHERE phone = '09281234502'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-07-11 13:00:00', NULL, NULL, 2.68, 175.0000, 235.0000, 'Unpaid', 'Pending', 'Rush Service (Same-Day) +₱50; Extra Bleach (For Whites) +₱10'),
('LS-260711-012', (SELECT customer_id FROM Customers WHERE phone = '09391234503'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2026-07-11 08:00:00', NULL, NULL, 2.01, 175.0000, 245.0000, 'Unpaid', 'Processing', 'Delicate Wash Handling Fee +₱20; Rush Service (Same-Day) +₱50'),

-- 2026-07-12: 10 orders, total ₱1,875.00
('LS-260712-001', (SELECT customer_id FROM Customers WHERE phone = '09171234504'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2026-07-12 08:00:00', '2026-07-12 12:00:00', NULL, 7.60, 175.0000, 205.0000, 'Unpaid', 'Ready', 'Additional Fold & Pack (Individual Bagging) +₱15; Fragrance Booster (Surf Fresh) +₱15'),
('LS-260712-002', (SELECT customer_id FROM Customers WHERE phone = '09281234505'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2026-07-12 11:00:00', NULL, NULL, 7.31, 175.0000, 215.0000, 'Unpaid', 'Processing', 'Comforter/Blanket Surcharge +₱40'),
('LS-260712-003', (SELECT customer_id FROM Customers WHERE phone = '09391234506'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-07-12 13:00:00', '2026-07-12 17:00:00', NULL, 5.27, 175.0000, 175.0000, 'Unpaid', 'Ready', NULL),
('LS-260712-004', (SELECT customer_id FROM Customers WHERE phone = '09171234507'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-12 13:00:00', '2026-07-12 17:00:00', NULL, 6.41, 175.0000, 175.0000, 'Paid', 'Ready', NULL),
('LS-260712-005', (SELECT customer_id FROM Customers WHERE phone = '09281234508'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2026-07-12 16:00:00', NULL, NULL, 6.92, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),
('LS-260712-006', (SELECT customer_id FROM Customers WHERE phone = '09391234509'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-07-12 10:00:00', NULL, NULL, 5.58, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),
('LS-260712-007', (SELECT customer_id FROM Customers WHERE phone = '09171234510'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2026-07-12 11:00:00', '2026-07-12 15:00:00', NULL, 5.02, 175.0000, 200.0000, 'Unpaid', 'Ready', 'Stain Treatment (Grease/Oil) +₱25'),
('LS-260712-008', (SELECT customer_id FROM Customers WHERE phone = '09281234511'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2026-07-12 10:00:00', '2026-07-12 14:00:00', '2026-07-12 16:00:00', 5.92, 175.0000, 190.0000, 'Paid', 'Claimed', 'Extra Rinse Cycle +₱15'),
('LS-260712-009', (SELECT customer_id FROM Customers WHERE phone = '09391234512'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2026-07-12 10:00:00', NULL, NULL, 3.33, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),
('LS-260712-010', (SELECT customer_id FROM Customers WHERE phone = '09171234501'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-07-12 16:00:00', '2026-07-12 18:00:00', NULL, 7.81, 175.0000, 190.0000, 'Paid', 'Ready', 'Extra Rinse Cycle +₱15'),

-- 2026-07-13: 10 orders, total ₱1,966.00
('LS-260713-001', (SELECT customer_id FROM Customers WHERE phone = '09281234502'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-13 07:00:00', NULL, NULL, 6.24, 175.0000, 215.0000, 'Unpaid', 'Processing', 'Comforter/Blanket Surcharge +₱40'),
('LS-260713-002', (SELECT customer_id FROM Customers WHERE phone = '09391234503'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2026-07-13 15:00:00', NULL, NULL, 7.27, 175.0000, 230.0000, 'Unpaid', 'Processing', 'Extra Rinse Cycle +₱15; Comforter/Blanket Surcharge +₱40'),
('LS-260713-003', (SELECT customer_id FROM Customers WHERE phone = '09171234504'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-07-13 14:00:00', '2026-07-13 18:00:00', NULL, 4.74, 175.0000, 190.0000, 'Paid', 'Ready', 'Extra Rinse Cycle +₱15'),
('LS-260713-004', (SELECT customer_id FROM Customers WHERE phone = '09281234505'), (SELECT employee_id FROM Employees WHERE username = 'Nics'), 1, '2026-07-13 14:00:00', '2026-07-13 18:00:00', '2026-07-13 21:00:00', 5.13, 175.0000, 190.0000, 'Paid', 'Claimed', 'Fragrance Booster (Surf Fresh) +₱15'),
('LS-260713-005', (SELECT customer_id FROM Customers WHERE phone = '09391234506'), (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'), 1, '2026-07-13 09:00:00', NULL, NULL, 7.26, 175.0000, 195.0000, 'Unpaid', 'Processing', 'Delicate Wash Handling Fee +₱20'),
('LS-260713-006', (SELECT customer_id FROM Customers WHERE phone = '09171234507'), (SELECT employee_id FROM Employees WHERE username = 'Jai'), 1, '2026-07-13 11:00:00', '2026-07-13 15:00:00', '2026-07-13 17:00:00', 6.39, 175.0000, 185.0000, 'Paid', 'Claimed', 'Extra Bleach (For Whites) +₱10'),
('LS-260713-007', (SELECT customer_id FROM Customers WHERE phone = '09281234508'), (SELECT employee_id FROM Employees WHERE username = 'Cral'), 1, '2026-07-13 11:00:00', NULL, NULL, 5.70, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),
('LS-260713-008', (SELECT customer_id FROM Customers WHERE phone = '09391234509'), (SELECT employee_id FROM Employees WHERE username = 'Yochie'), 1, '2026-07-13 08:00:00', '2026-07-13 10:00:00', '2026-07-13 11:00:00', 3.38, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),
('LS-260713-009', (SELECT customer_id FROM Customers WHERE phone = '09171234510'), (SELECT employee_id FROM Employees WHERE username = 'Imeaa'), 1, '2026-07-13 13:00:00', NULL, NULL, 3.36, 175.0000, 225.0000, 'Unpaid', 'Pending', 'Rush Service (Same-Day) +₱50'),
('LS-260713-010', (SELECT customer_id FROM Customers WHERE phone = '09281234511'), (SELECT employee_id FROM Employees WHERE username = 'Iyah'), 1, '2026-07-13 13:00:00', NULL, NULL, 3.95, 175.0000, 186.0000, 'Unpaid', 'Processing', 'Extra Fabric Conditioner (Downy Passion) +₱11');