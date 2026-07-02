-- Comprehensive seed data for Laundry Service POS
-- Covers every order_status, every payment_status combination,
-- orders with and without notes, and correctly populated timestamps

USE laundry_service_db;

-- EMPLOYEES
-- Passwords are bcrypt-hashed. The plaintext password for ALL accounts is: 12345
-- These use the $2a$ prefix for jBCrypt (org.mindrot:jbcrypt:0.4) compatibility.
-- If BCrypt.checkpw() fails at runtime, regenerate hashes using:
--   BCrypt.hashpw("12345", BCrypt.gensalt())  in a one-off Java main() method.
INSERT IGNORE INTO Employees (name, username, password) VALUES
('Lloyd Ariel B. Deputo', 'Cral',    '$2a$10$w7gEhgS7nKASRjojx//jr.D.2pqEm9SaPb/A8Fofum.lOirkZNNJG'),
('Yochie',                'Yochie',  '$2a$10$jPMowHjo4SYkb/V6nfR2uelkzpzPtMzJo.GoE4f6Cs62VbIl8XmpO'),
('Imeaa',                 'Imeaa',   '$2a$10$R9D6J/OJZOXvdW/j3rexPOTWMzUXoDv2DISdNVDH3Q/jV3ov1yAbK'),
('Iyah',                  'Iyah',    '$2a$10$otTsFzZifd/RLv86cxvX1.Iy6znYIVCd6.dDlDIdVF0ZNJoC3I/.W'),
('Nics',                  'Nics',    '$2a$10$ILK9a.93YgwJTFLV.2NYbOZS1AKGDjx2.wmFSYZk0HMpFXzE29n1O'),
('Rod',                   'Rodzkie', '$2a$10$ZsG7ZV3iYbriNijOLgSydeqb.hG0XFgjxk.jFQ8Ib.qFA5BQWfPaq'),
('Jairus',                'Jai',     '$2a$10$b.PeO7oAVnaKgVeyG.4HdOXqPr6elQqeEXI5tJTAD5AZjQ/fGGYhK');

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
-- employee_id 1 = Lloyd (Cral), seeded above
-- service_id 1 = Full Service (175.0000), seeded by init_db.sql
-- Covers all status/payment combinations with realistic timestamps
INSERT INTO Orders (claim_number, customer_id, employee_id, service_id, order_date, ready_at, claimed_at, weight_kg, price_at_order, total_amount, payment_status, order_status, notes)
VALUES

-- Pending / Unpaid (fresh orders, no timestamps beyond order_date)
('LS-260625-001',
    (SELECT customer_id FROM Customers WHERE phone = '09171234501'),
    1, 1, '2026-06-25 08:00:00', NULL, NULL,
    3.50, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

('LS-260625-002',
    (SELECT customer_id FROM Customers WHERE phone = '09281234502'),
    1, 1, '2026-06-25 09:30:00', NULL, NULL,
    5.00, 175.0000, 175.0000, 'Unpaid', 'Pending', 'No bleach'),

('LS-260625-003',
    (SELECT customer_id FROM Customers WHERE phone = '09171234507'),
    1, 1, '2026-06-25 11:00:00', NULL, NULL,
    6.00, 175.0000, 175.0000, 'Unpaid', 'Pending', NULL),

-- Processing / Unpaid (being washed, no ready_at yet)
('LS-260626-001',
    (SELECT customer_id FROM Customers WHERE phone = '09391234503'),
    1, 1, '2026-06-26 08:15:00', NULL, NULL,
    4.00, 175.0000, 175.0000, 'Unpaid', 'Processing', 'No fabric softener'),

('LS-260626-002',
    (SELECT customer_id FROM Customers WHERE phone = '09171234504'),
    1, 1, '2026-06-26 10:00:00', NULL, NULL,
    6.50, 175.0000, 175.0000, 'Unpaid', 'Processing', NULL),

('LS-260626-003',
    (SELECT customer_id FROM Customers WHERE phone = '09281234508'),
    1, 1, '2026-06-26 13:00:00', NULL, NULL,
    2.00, 175.0000, 175.0000, 'Unpaid', 'Processing', 'Delicate cycle only'),

-- Ready / Unpaid (done but not yet paid or claimed)
('LS-260627-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234505'),
    1, 1, '2026-06-27 08:00:00', '2026-06-27 14:00:00', NULL,
    2.50, 175.0000, 175.0000, 'Unpaid', 'Ready', NULL),

('LS-260627-002',
    (SELECT customer_id FROM Customers WHERE phone = '09391234509'),
    1, 1, '2026-06-27 09:30:00', '2026-06-27 15:30:00', NULL,
    5.50, 175.0000, 175.0000, 'Unpaid', 'Ready', 'Hang dry, do not tumble'),

-- Ready / Paid (done and paid, waiting for pickup)
('LS-260627-003',
    (SELECT customer_id FROM Customers WHERE phone = '09391234506'),
    1, 1, '2026-06-27 09:00:00', '2026-06-27 15:00:00', NULL,
    7.00, 175.0000, 175.0000, 'Paid', 'Ready', 'Separate whites'),

('LS-260627-004',
    (SELECT customer_id FROM Customers WHERE phone = '09171234510'),
    1, 1, '2026-06-27 11:00:00', '2026-06-27 17:00:00', NULL,
    3.00, 175.0000, 175.0000, 'Paid', 'Ready', NULL),

-- Claimed / Paid (completed order, business rule: Claimed always sets Paid)
('LS-260628-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234511'),
    1, 1, '2026-06-28 07:30:00', '2026-06-28 12:30:00', '2026-06-28 18:00:00',
    4.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

-- Claimed / Paid (fully complete orders)
('LS-260628-002',
    (SELECT customer_id FROM Customers WHERE phone = '09171234501'),
    1, 1, '2026-06-28 08:00:00', '2026-06-28 13:00:00', '2026-06-28 17:00:00',
    4.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

('LS-260628-003',
    (SELECT customer_id FROM Customers WHERE phone = '09281234502'),
    1, 1, '2026-06-28 09:00:00', '2026-06-28 14:00:00', '2026-06-29 09:00:00',
    6.00, 175.0000, 175.0000, 'Paid', 'Claimed', 'Handle with care'),

('LS-260629-001',
    (SELECT customer_id FROM Customers WHERE phone = '09391234503'),
    1, 1, '2026-06-29 08:00:00', '2026-06-29 13:30:00', '2026-06-29 16:00:00',
    3.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

('LS-260629-002',
    (SELECT customer_id FROM Customers WHERE phone = '09391234512'),
    1, 1, '2026-06-29 09:00:00', '2026-06-29 14:30:00', '2026-06-30 10:00:00',
    3.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

('LS-260629-003',
    (SELECT customer_id FROM Customers WHERE phone = '09171234504'),
    1, 1, '2026-06-29 10:00:00', '2026-06-29 16:00:00', '2026-06-30 11:00:00',
    7.00, 175.0000, 175.0000, 'Paid', 'Claimed', 'No fabric softener'),

('LS-260630-001',
    (SELECT customer_id FROM Customers WHERE phone = '09281234505'),
    1, 1, '2026-06-30 07:45:00', '2026-06-30 13:00:00', '2026-06-30 15:30:00',
    5.00, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

('LS-260630-002',
    (SELECT customer_id FROM Customers WHERE phone = '09171234507'),
    1, 1, '2026-06-30 08:30:00', '2026-06-30 14:00:00', '2026-06-30 17:00:00',
    2.50, 175.0000, 175.0000, 'Paid', 'Claimed', 'Separate darks'),

('LS-260630-003',
    (SELECT customer_id FROM Customers WHERE phone = '09281234508'),
    1, 1, '2026-06-30 09:15:00', '2026-06-30 15:00:00', '2026-07-01 09:00:00',
    6.50, 175.0000, 175.0000, 'Paid', 'Claimed', NULL),

('LS-260630-004',
    (SELECT customer_id FROM Customers WHERE phone = '09391234509'),
    1, 1, '2026-06-30 10:00:00', '2026-06-30 16:00:00', '2026-07-01 10:30:00',
    4.00, 175.0000, 175.0000, 'Paid', 'Claimed', 'Handle with care');