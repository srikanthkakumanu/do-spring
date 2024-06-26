DROP DATABASE IF EXISTS bookdb;
DROP USER IF EXISTS `bookadmin`@`%`;
DROP USER IF EXISTS `bookuser`@`%`;
CREATE DATABASE IF NOT EXISTS bookdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `bookadmin`@`%` IDENTIFIED BY 'bookadmin';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `bookdb`.* TO `bookadmin`@`%` WITH GRANT OPTION;
-- GRANT ALL ON `bookdb`.* TO `bookadmin`@`%` WITH GRANT OPTION;
CREATE USER IF NOT EXISTS `bookuser`@`%` IDENTIFIED BY 'bookuser';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `bookdb`.* TO `bookuser`@`%`;
-- GRANT ALL ON `bookdb`.* TO `bookuser`@`%` WITH GRANT OPTION;
FLUSH PRIVILEGES;