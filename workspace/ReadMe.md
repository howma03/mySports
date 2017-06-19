create database mysports;

CREATE USER 'adminsports'@'localhost' IDENTIFIED BY 'S0uthern';

GRANT ALL PRIVILEGES ON mysports.* TO 'adminsports'@'localhost';

