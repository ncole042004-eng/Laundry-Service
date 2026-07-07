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
-- All seven accounts are currently Admin — no Cashier-role employee exists yet.
-- -----------------------------------------------
INSERT IGNORE INTO Employees (name, username, password, role) VALUES
('Cral',                  'Cral',    '$2a$10$w7gEhgS7nKASRjojx//jr.D.2pqEm9SaPb/A8Fofum.lOirkZNNJG', 'Admin'),
('Yochie',                'Yochie',  '$2a$10$jPMowHjo4SYkb/V6nfR2uelkzpzPtMzJo.GoE4f6Cs62VbIl8XmpO', 'Admin'),
('Imeaa',                 'Imeaa',   '$2a$10$R9D6J/OJZOXvdW/j3rexPOTWMzUXoDv2DISdNVDH3Q/jV3ov1yAbK', 'Admin'),
('Iyah',                  'Iyah',    '$2a$10$otTsFzZifd/RLv86cxvX1.Iy6znYIVCd6.dDlDIdVF0ZNJoC3I/.W', 'Admin'),
('Nics',                  'Nics',    '$2a$10$ILK9a.93YgwJTFLV.2NYbOZS1AKGDjx2.wmFSYZk0HMpFXzE29n1O', 'Admin'),
('Rodzkie',               'Rodzkie', '$2a$10$ZsG7ZV3iYbriNijOLgSydeqb.hG0XFgjxk.jFQ8Ib.qFA5BQWfPaq', 'Admin'),
('Jai',                   'Jai',     '$2a$10$b.PeO7oAVnaKgVeyG.4HdOXqPr6elQqeEXI5tJTAD5AZjQ/fGGYhK', 'Admin');

-- -----------------------------------------------
-- CUSTOMERS
-- Includes all happy path customers plus additional
-- -----------------------------------------------
INSERT IGNORE INTO Customers (name, phone, address, is_active) VALUES
('Maria Santos',      '09171234501', 'Brgy. Paulba, Ligao City, Albay',         1),
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
    4.00, 175.0000, 175.0000, 'Paid', 'Claimed', 'Handle with care'),

-- =================================================
-- JULY 7 – JULY 13, 2026
-- Different order count per day (4, 6, 3, 5, 7, 2, 8) so "today" stats
-- look different no matter which day within this range the app is run on.
-- Earlier days in this range skew toward Ready/Claimed (already processed);
-- later days skew toward Pending/Processing (recently dropped off) —
-- matching a realistic day-to-day operational cadence.
-- =================================================

-- ---------- JULY 7 (4 orders) ----------
('LS-260707-001',
    (SELECT customer_id FROM Customers WHERE phone = '09171234501'),
    (SELECT employee_id FROM Employees WHERE username = 'Cral'),
    1, '2026-07-07 07:45:00', '2026-07-07 13:00:00', '2026-07-07 17:30:00',
    4.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

('LS-260707-002',
    (SELECT customer_id FROM Customers WHERE phone = '09281234502'),
    (SELECT employee_id FROM Employees WHERE username = 'Yochie'),
    1, '2026-07-07 08:30:00', '2026-07-07 14:00:00', '2026-07-07 18:00:00',
    5.50, 175.0000, 175.0000, 'Paid', 'Claimed', 'Fold neatly'),

('LS-260707-003',
    (SELECT customer_id FROM Customers WHERE phone = '09391234503'),
    (SELECT employee_id FROM Employees WHERE username = 'Imeaa'),
    1, '2026-07-07 09:15:00', '2026-07-07 15:00:00', NULL,
    3.00, 175.0000, 175.0000, 'Paid', 'Ready', NULL),

('LS-260707-004',
    (SELECT customer_id FROM Customers WHERE phone = '09171234504'),
    (SELECT employee_id FROM Employees WHERE username = 'Iyah'),
    1, '2026-07-07 10:00:00', NULL, NULL,
    6.00, 175.0000, 175.0000, 'Unpaid', 'Processing', 'Handle with care'),

-- ---------- JULY 8 (6 orders) ----------
('LS-260708-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234505'),
    (SELECT employee_id FROM Employees WHERE username = 'Nics'),
    1, '2026-07-08 07:30:00', '2026-07-08 13:30:00', '2026-07-08 18:00:00',
    4.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

('LS-260708-002',
    (SELECT customer_id FROM Customers WHERE phone = '09391234506'),
    (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'),
    1, '2026-07-08 08:00:00', '2026-07-08 14:00:00', NULL,
    3.50, 175.0000, 175.0000, 'Paid', 'Ready', NULL),

('LS-260708-003',
    (SELECT customer_id FROM Customers WHERE phone = '09171234507'),
    (SELECT employee_id FROM Employees WHERE username = 'Jai'),
    1, '2026-07-08 08:45:00', '2026-07-08 14:30:00', '2026-07-08 19:00:00',
    5.00, 175.0000, 175.0000, 'Paid', 'Claimed', 'Separate colors'),

('LS-260708-004',
    (SELECT customer_id FROM Customers WHERE phone = '09281234508'),
    (SELECT employee_id FROM Employees WHERE username = 'Cral'),
    1, '2026-07-08 09:30:00', NULL, NULL,
    6.50, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),

('LS-260708-005',
    (SELECT customer_id FROM Customers WHERE phone = '09391234509'),
    (SELECT employee_id FROM Employees WHERE username = 'Yochie'),
    1, '2026-07-08 10:15:00', NULL, NULL,
    2.50, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

('LS-260708-006',
    (SELECT customer_id FROM Customers WHERE phone = '09171234510'),
    (SELECT employee_id FROM Employees WHERE username = 'Imeaa'),
    1, '2026-07-08 11:00:00', '2026-07-08 16:30:00', NULL,
    7.00, 175.0000, 175.0000, 'Unpaid', 'Ready', 'No fabric softener'),

-- ---------- JULY 9 (3 orders) ----------
('LS-260709-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234511'),
    (SELECT employee_id FROM Employees WHERE username = 'Iyah'),
    1, '2026-07-09 08:00:00', '2026-07-09 13:00:00', '2026-07-09 17:00:00',
    3.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

('LS-260709-002',
    (SELECT customer_id FROM Customers WHERE phone = '09391234512'),
    (SELECT employee_id FROM Employees WHERE username = 'Nics'),
    1, '2026-07-09 09:00:00', NULL, NULL,
    4.00, 175.0000, 175.0000, 'Unpaid', 'Processing', 'Delicate cycle only'),

('LS-260709-003',
    (SELECT customer_id FROM Customers WHERE phone = '09171234501'),
    (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'),
    1, '2026-07-09 10:30:00', NULL, NULL,
    5.50, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

-- ---------- JULY 10 (5 orders) ----------
('LS-260710-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234502'),
    (SELECT employee_id FROM Employees WHERE username = 'Jai'),
    1, '2026-07-10 07:45:00', '2026-07-10 13:00:00', NULL,
    3.50, 175.0000, 175.0000, 'Paid', 'Ready', NULL),

('LS-260710-002',
    (SELECT customer_id FROM Customers WHERE phone = '09391234503'),
    (SELECT employee_id FROM Employees WHERE username = 'Cral'),
    1, '2026-07-10 08:30:00', NULL, NULL,
    6.00, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),

('LS-260710-003',
    (SELECT customer_id FROM Customers WHERE phone = '09171234504'),
    (SELECT employee_id FROM Employees WHERE username = 'Yochie'),
    1, '2026-07-10 09:15:00', NULL, NULL,
    2.00, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

('LS-260710-004',
    (SELECT customer_id FROM Customers WHERE phone = '09281234505'),
    (SELECT employee_id FROM Employees WHERE username = 'Imeaa'),
    1, '2026-07-10 10:00:00', '2026-07-10 15:30:00', NULL,
    5.00, 175.0000, 225.0000, 'Paid', 'Ready', 'Hang dry, do not tumble — stain treatment (+P50)'),

('LS-260710-005',
    (SELECT customer_id FROM Customers WHERE phone = '09391234506'),
    (SELECT employee_id FROM Employees WHERE username = 'Iyah'),
    1, '2026-07-10 11:30:00', NULL, NULL,
    4.50, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

-- ---------- JULY 11 (7 orders) ----------
('LS-260711-001',
    (SELECT customer_id FROM Customers WHERE phone = '09171234507'),
    (SELECT employee_id FROM Employees WHERE username = 'Nics'),
    1, '2026-07-11 07:30:00', NULL, NULL,
    3.00, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),

('LS-260711-002',
    (SELECT customer_id FROM Customers WHERE phone = '09281234508'),
    (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'),
    1, '2026-07-11 08:00:00', NULL, NULL,
    5.50, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

('LS-260711-003',
    (SELECT customer_id FROM Customers WHERE phone = '09391234509'),
    (SELECT employee_id FROM Employees WHERE username = 'Jai'),
    1, '2026-07-11 08:45:00', '2026-07-11 14:00:00', NULL,
    6.50, 175.0000, 175.0000, 'Paid', 'Ready', NULL),

('LS-260711-004',
    (SELECT customer_id FROM Customers WHERE phone = '09171234510'),
    (SELECT employee_id FROM Employees WHERE username = 'Cral'),
    1, '2026-07-11 09:30:00', NULL, NULL,
    2.50, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

('LS-260711-005',
    (SELECT customer_id FROM Customers WHERE phone = '09281234511'),
    (SELECT employee_id FROM Employees WHERE username = 'Yochie'),
    1, '2026-07-11 10:15:00', NULL, NULL,
    4.00, 175.0000, 175.0000, 'Unpaid', 'Processing', 'No bleach'),

('LS-260711-006',
    (SELECT customer_id FROM Customers WHERE phone = '09391234512'),
    (SELECT employee_id FROM Employees WHERE username = 'Imeaa'),
    1, '2026-07-11 11:00:00', NULL, NULL,
    7.00, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

('LS-260711-007',
    (SELECT customer_id FROM Customers WHERE phone = '09171234501'),
    (SELECT employee_id FROM Employees WHERE username = 'Iyah'),
    1, '2026-07-11 11:45:00', '2026-07-11 17:00:00', NULL,
    3.50, 175.0000, 175.0000, 'Paid', 'Ready', 'Separate whites'),

-- ---------- JULY 12 (2 orders) ----------
('LS-260712-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234502'),
    (SELECT employee_id FROM Employees WHERE username = 'Nics'),
    1, '2026-07-12 08:00:00', NULL, NULL,
    5.00, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

('LS-260712-002',
    (SELECT customer_id FROM Customers WHERE phone = '09391234503'),
    (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'),
    1, '2026-07-12 09:30:00', NULL, NULL,
    4.50, 175.0000, 200.0000, 'Unpaid', 'Processing', 'Extra rinse cycle (+P25)'),

-- ---------- JULY 13 (8 orders) ----------
('LS-260713-001',
    (SELECT customer_id FROM Customers WHERE phone = '09171234504'),
    (SELECT employee_id FROM Employees WHERE username = 'Jai'),
    1, '2026-07-13 07:45:00', NULL, NULL,
    3.00, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

('LS-260713-002',
    (SELECT customer_id FROM Customers WHERE phone = '09281234505'),
    (SELECT employee_id FROM Employees WHERE username = 'Cral'),
    1, '2026-07-13 08:15:00', NULL, NULL,
    6.00, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

('LS-260713-003',
    (SELECT customer_id FROM Customers WHERE phone = '09391234506'),
    (SELECT employee_id FROM Employees WHERE username = 'Yochie'),
    1, '2026-07-13 09:00:00', NULL, NULL,
    4.00, 175.0000, 175.0000, 'Unpaid', 'Processing', 'Handle with care'),

('LS-260713-004',
    (SELECT customer_id FROM Customers WHERE phone = '09171234507'),
    (SELECT employee_id FROM Employees WHERE username = 'Imeaa'),
    1, '2026-07-13 09:45:00', NULL, NULL,
    5.50, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

('LS-260713-005',
    (SELECT customer_id FROM Customers WHERE phone = '09281234508'),
    (SELECT employee_id FROM Employees WHERE username = 'Iyah'),
    1, '2026-07-13 10:30:00', NULL, NULL,
    2.50, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

('LS-260713-006',
    (SELECT customer_id FROM Customers WHERE phone = '09391234509'),
    (SELECT employee_id FROM Employees WHERE username = 'Nics'),
    1, '2026-07-13 11:15:00', NULL, NULL,
    3.50, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

('LS-260713-007',
    (SELECT customer_id FROM Customers WHERE phone = '09171234510'),
    (SELECT employee_id FROM Employees WHERE username = 'Rodzkie'),
    1, '2026-07-13 11:45:00', NULL, NULL,
    4.00, 175.0000, 175.0000, 'Unpaid', 'Pending', 'Rush order'),

('LS-260713-008',
    (SELECT customer_id FROM Customers WHERE phone = '09281234511'),
    (SELECT employee_id FROM Employees WHERE username = 'Jai'),
    1, '2026-07-13 12:15:00', NULL, NULL,
    6.50, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL);