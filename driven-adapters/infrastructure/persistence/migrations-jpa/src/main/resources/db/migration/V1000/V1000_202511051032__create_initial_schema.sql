CREATE TYPE STOP_TYPE AS ENUM (
    'FIRST_MILE',
    'LAST_MILE'
    );

CREATE TYPE PAYMENT_METHOD_TYPE AS ENUM (
    'PRPT',
    'PSPT'
    );

CREATE TYPE SERVICE_CODE AS ENUM (
    'FCP',
    'FCD',
    'RDD',
    'RDP',
    'FRD',
    'FDD',
    'FDP',
    'RCP',
    'RCD',
    'FRP',
    'FVD'
    );

CREATE TYPE "size" AS ENUM (
    'S',
    'M',
    'L'
    );

CREATE TABLE IF NOT EXISTS stops (
    id                  BIGSERIAL PRIMARY KEY,
    stop_id             VARCHAR(100) NOT NULL,
    stop_hash           VARCHAR(32)  NOT NULL,
    network             VARCHAR(100) NOT NULL,
    partner_sid         UUID         NOT NULL,
    node_sid            UUID         NOT NULL,
    node_name           VARCHAR(255) NOT NULL,
    driver_sid          UUID,
    driver_name         VARCHAR(255),
    service_code        SERVICE_CODE NOT NULL,
    payment_method_sid  UUID,
    payment_method_name VARCHAR(100),
    payment_method_type PAYMENT_METHOD_TYPE,
    zone_sid            UUID,
    zone_name           VARCHAR(255),
    size                SIZE,
    event_date          DATE         NOT NULL,
    stop_type           STOP_TYPE    NOT NULL,
    packages_by_size    JSONB,
    tracking_numbers    VARCHAR(255)[],
    address_details     JSONB,
    created_at          TIMESTAMPTZ  NOT NULL,
    updated_at          TIMESTAMPTZ  NOT NULL,
    published           BOOLEAN      NOT NULL
);

CREATE TABLE IF NOT EXISTS logistic_events (
    id          BIGSERIAL PRIMARY KEY,
    fk_stop     BIGINT REFERENCES stops(id) NOT NULL,
    network     VARCHAR(100)                NOT NULL,
    payload     JSONB                       NOT NULL,
    partner_sid UUID                        NOT NULL,
    created_at  TIMESTAMPTZ                 NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_stops_stop_hash ON stops(stop_hash);

CREATE INDEX idx_stops_event_date_unpublished ON stops(event_date) WHERE published = FALSE AND stop_type = 'LAST_MILE';

CREATE INDEX idx_stops_partner_event_date ON stops(partner_sid, event_date);

CREATE INDEX idx_stops_stop_id ON stops(stop_id);

CREATE INDEX idx_stops_tracking_numbers ON stops USING GIN(tracking_numbers);

CREATE INDEX idx_logistic_events_provider_date ON logistic_events(partner_sid, created_at);

CREATE INDEX idx_logistic_events_fk_stop ON logistic_events(fk_stop);
