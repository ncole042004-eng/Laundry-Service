-- Happy path seed data for Laundry Service POS
-- Covers the clean order lifecycle: Pending → Processing → Ready → Claimed

USE laundry_service_db;

-- -----------------------------------------------
-- EMPLOYEES
-- Passwords are bcrypt-hashed. The plaintext password for ALL accounts is: 12345
-- These use the $2a$ prefix for jBCrypt (org.mindrot:jbcrypt:0.4) compatibility.
-- If BCrypt.checkpw() fails at runtime, regenerate hashes using:
--   BCrypt.hashpw("12345", BCrypt.gensalt())  in a one-off Java main() method.
-- -----------------------------------------------
INSERT IGNORE INTO Employees (name, username, password) VALUES
('Cral',     'Cral',    '$2a$10$w7gEhgS7nKASRjojx//jr.D.2pqEm9SaPb/A8Fofum.lOirkZNNJG'),
('Yochie',   'Yochie',  '$2a$10$jPMowHjo4SYkb/V6nfR2uelkzpzPtMzJo.GoE4f6Cs62VbIl8XmpO'),
('Imeaa',    'Imeaa',   '$2a$10$R9D6J/OJZOXvdW/j3rexPOTWMzUXoDv2DISdNVDH3Q/jV3ov1yAbK'),
('Iyah',     'Iyah',    '$2a$10$otTsFzZifd/RLv86cxvX1.Iy6znYIVCd6.dDlDIdVF0ZNJoC3I/.W'),
('Nics',     'Nics',    '$2a$10$ILK9a.93YgwJTFLV.2NYbOZS1AKGDjx2.wmFSYZk0HMpFXzE29n1O'),
('Rodzkie',  'Rodzkie', '$2a$10$ZsG7ZV3iYbriNijOLgSydeqb.hG0XFgjxk.jFQ8Ib.qFA5BQWfPaq'),
('Jai',      'Jai',     '$2a$10$b.PeO7oAVnaKgVeyG.4HdOXqPr6elQqeEXI5tJTAD5AZjQ/fGGYhK');

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

-- -----------------------------------------------
-- ORDERS
-- employee_id mapping: 1=Cral, 2=Yochie, 3=Imeaa, 4=Iyah, 5=Nics, 6=Rodzkie, 7=Jai
-- service_id 1 = Full Service (175.0000), seeded by init_db.sql
-- Shows clean lifecycle across all four statuses
-- -----------------------------------------------
INSERT INTO Orders (claim_number, customer_id, employee_id, service_id, order_date, ready_at, claimed_at, weight_kg, price_at_order, total_amount, payment_status, order_status, notes)
VALUES

-- Pending / Unpaid — taken by Cral
('LS-260625-001',
    (SELECT customer_id FROM Customers WHERE phone = '09171234501'),
    1, 1, '2026-06-25 08:00:00', NULL, NULL,
    3.50, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

-- Pending / Unpaid — taken by Yochie
('LS-260625-002',
    (SELECT customer_id FROM Customers WHERE phone = '09281234502'),
    2, 1, '2026-06-25 09:30:00', NULL, NULL,
    5.00, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

-- Processing / Unpaid — taken by Imeaa
('LS-260626-001',
    (SELECT customer_id FROM Customers WHERE phone = '09391234503'),
    3, 1, '2026-06-26 08:15:00', NULL, NULL,
    4.00, 175.0000, 175.0000, 'Unpaid', 'Processing', 'No fabric softener'),

-- Processing / Unpaid — taken by Iyah
('LS-260626-002',
    (SELECT customer_id FROM Customers WHERE phone = '09171234504'),
    4, 1, '2026-06-26 10:00:00', NULL, NULL,
    6.50, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),

-- Ready / Unpaid — taken by Nics
('LS-260627-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234505'),
    5, 1, '2026-06-27 08:00:00', '2026-06-27 14:00:00', NULL,
    2.50, 175.0000, 175.0000, 'Unpaid', 'Ready', NULL),

-- Ready / Paid — taken by Rodzkie
('LS-260627-002',
    (SELECT customer_id FROM Customers WHERE phone = '09391234506'),
    6, 1, '2026-06-27 09:00:00', '2026-06-27 15:00:00', NULL,
    7.00, 175.0000, 175.0000, 'Paid', 'Ready', 'Separate whites'),

-- Claimed / Paid — taken by Cral
('LS-260628-001',
    (SELECT customer_id FROM Customers WHERE phone = '09171234501'),
    1, 1, '2026-06-28 08:00:00', '2026-06-28 13:00:00', '2026-06-28 17:00:00',
    4.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

-- Claimed / Paid — taken by Jai
('LS-260629-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234502'),
    7, 1, '2026-06-29 09:00:00', '2026-06-29 14:30:00', '2026-06-30 10:00:00',
    3.00, 175.0000, 175.0000, 'Paid', 'Claimed', 'Handle with care');

