CREATE TYPE REPORT_TYPE AS ENUM (
    'SUCCESS_RATE',
    'LOSS_RATE'
    );

CREATE TABLE network_threshold (

    report_type     REPORT_TYPE                 NOT NULL,
    network         VARCHAR(2)                  NOT NULL,
    threshold       NUMERIC(3,2)                NOT NULL,
    updated_at      TIMESTAMP WITH TIME ZONE    DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (report_type, network),
    CONSTRAINT chk_threshold_range CHECK (threshold >= 0 AND threshold <= 1)
);
