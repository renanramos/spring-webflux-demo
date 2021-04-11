DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
  id VARCHAR(250) PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  departmentId VARCHAR(250)
);

CREATE TABLE department (
  id VARCHAR(250) PRIMARY KEY,
  name VARCHAR(250) NOT NULL
);

ALTER TABLE employee
ADD FOREIGN KEY (departmentId) REFERENCES department(id);