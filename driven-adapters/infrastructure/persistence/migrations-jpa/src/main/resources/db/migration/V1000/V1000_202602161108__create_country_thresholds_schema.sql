CREATE TABLE country_thresholds (

    report_type     REPORT_TYPE                 NOT NULL,
    country         VARCHAR(2)                  NOT NULL,
    value           NUMERIC(3,2)                NOT NULL,
    updated_at      TIMESTAMP WITH TIME ZONE    DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (report_type, country),
    CONSTRAINT chk_threshold_range CHECK (value >= 0 AND value <= 1)
);
