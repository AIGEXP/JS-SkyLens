CREATE TYPE REPORT_TYPE AS ENUM (
    'SUCCESS_RATE',
    'LOSS_RATE'
    );

CREATE TABLE network_thresholds (

    report_type     REPORT_TYPE                 NOT NULL,
    network         VARCHAR(2)                  NOT NULL,
    value           NUMERIC(3,2)                NOT NULL,
    updated_at      TIMESTAMP WITH TIME ZONE    DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (report_type, network),
    CONSTRAINT chk_threshold_range CHECK (value >= 0 AND value <= 1)
);
