DROP TABLE IF EXISTS members;

CREATE TABLE members (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  value VARCHAR(100) NOT NULL,
  fullinput VARCHAR(8000) NOT NULL,
  jobid varchar(100) not null
);
