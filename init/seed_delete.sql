-- -----------------------------------------------------------------------------
-- COMPLETE SEED WIPE SCRIPT
-- -----------------------------------------------------------------------------
-- Removes ALL data from Orders, Customers, and Employees.
-- Safe to run regardless of which seed file was used or if you added new 
-- customers during testing.
-- Does NOT touch the schema or the base Services table.

USE laundry_service_db;

-- 1. Temporarily disable foreign key checks so we can truncate cleanly
SET FOREIGN_KEY_CHECKS = 0;

-- 2. Delete all records and instantly reset AUTO_INCREMENT to 1
TRUNCATE TABLE Orders;
TRUNCATE TABLE Customers;
TRUNCATE TABLE Employees;

-- 3. Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- At this point, the database is completely empty (except for Services) 
-- and ready for fresh seeds or a clean production start.
