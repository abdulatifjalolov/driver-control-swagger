CREATE SCHEMA IF NOT EXISTS test;

create table if not exists test.users(
    id bigserial primary key,
    email varchar,
    password varchar,
    role_enum_list varchar[],
    permission_enum_list varchar[]
);

create table if not exists test.cars(
    id bigserial primary key,
    mileage double precision,
    model varchar,
    model_year integer,
    name varchar,
    state_number varchar
);

create table if not exists test.trip_history_item(
    id bigserial primary key,
    arrival_address varchar,
    arival_date timestamp,
    departure_address varchar,
    departure_date timestamp
);