CREATE TABLE users (
       ID                       VARCHAR(36) PRIMARY KEY NOT NULL,
       USERNAME                 VARCHAR(50) UNIQUE NOT NULL,
       IS_GUEST                 BOOLEAN DEFAULT true,
       EMAIL                    VARCHAR(150) UNIQUE,
       PASSWORD                 TEXT,
       TOTAL_ASSET              NUMERIC(38,2) DEFAULT 0,
       DATE_RESET_PREFERENCE    NUMERIC(2) DEFAULT 1,
       CREATED_AT               TIMESTAMP,
       CREATED_BY               TEXT,
       UPDATED_AT               TIMESTAMP,
       UPDATED_BY               TEXT
);