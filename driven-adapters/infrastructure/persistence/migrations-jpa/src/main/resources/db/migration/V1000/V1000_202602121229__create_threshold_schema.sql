CREATE TABLE network_threshold (

    report_name     VARCHAR(255)                NOT NULL,
    network         VARCHAR(32)                 NOT NULL,
    threshold       NUMERIC(5,2)                NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE    DEFAULT NULL,

    PRIMARY KEY (report_name, network),
    CONSTRAINT chk_threshold_range CHECK (threshold >= 0 AND threshold <= 100)
);
