CREATE TYPE REPORT_TYPE AS ENUM (
    'SUCCESS_RATE',
    'LOSS_RATE'
    );

CREATE TABLE IF NOT EXISTS boundaries (
    country     VARCHAR(2)               NOT NULL,
    report_type REPORT_TYPE              NOT NULL,
    warning     DECIMAL(3, 2)            NOT NULL CHECK (warning BETWEEN 0 AND 1),
    critical    DECIMAL(3, 2)            NOT NULL CHECK (critical BETWEEN 0 AND 1),
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (country, report_type)
);
