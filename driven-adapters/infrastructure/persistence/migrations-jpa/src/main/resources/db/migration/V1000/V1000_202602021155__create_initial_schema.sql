CREATE TYPE PAYMENT_TYPE AS ENUM (
    'PRE',
    'POST'
    );

CREATE TYPE MOVEMENT_TYPE AS ENUM (
    'DD',
    'PUS'
    );

CREATE TABLE hub_daily_metrics (
    hub_sid                             UUID                     NOT NULL,
    service_provider_sid                UUID                     NOT NULL,
    day                                 DATE                     NOT NULL,
    payment_type                        PAYMENT_TYPE             NOT NULL,
    movement_type                       MOVEMENT_TYPE            NOT NULL,
    packages_delivered                  INTEGER                  NOT NULL DEFAULT 0,
    packages_closed                     INTEGER                  NOT NULL DEFAULT 0,
    packages_received                   INTEGER                  NOT NULL DEFAULT 0,
    packages_lost_at_hub                INTEGER                  NOT NULL DEFAULT 0,
    packages_no_attempts                INTEGER                  NOT NULL DEFAULT 0,
    packages_no_attempts_one_day        INTEGER                  NOT NULL DEFAULT 0,
    packages_no_attempts_two_days       INTEGER                  NOT NULL DEFAULT 0,
    packages_no_attempts_three_days     INTEGER                  NOT NULL DEFAULT 0,
    packages_no_attempts_four_days      INTEGER                  NOT NULL DEFAULT 0,
    packages_no_attempts_over_four_days INTEGER                  NOT NULL DEFAULT 0,
    created_at                          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (service_provider_sid, hub_sid, day, payment_type, movement_type)
);

CREATE INDEX idx_hub_daily_metrics_day ON hub_daily_metrics(day);

CREATE INDEX idx_hub_daily_metrics_service_provider_day ON hub_daily_metrics(service_provider_sid, day);

CREATE INDEX idx_hub_daily_metrics_service_provider_hub_day ON hub_daily_metrics(service_provider_sid, hub_sid, day);

CREATE INDEX idx_hub_daily_metrics_hub_day ON hub_daily_metrics(hub_sid, day);
