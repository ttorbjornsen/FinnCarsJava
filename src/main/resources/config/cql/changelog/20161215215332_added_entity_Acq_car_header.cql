
CREATE TABLE IF NOT EXISTS acq_car_header (
    id uuid,
    url text,
    load_date text,
    load_time bigint,
    location text,
    year text,
    km text,
    price text,
    PRIMARY KEY(id)
);
