drop table IF EXISTS users CASCADE;
drop table IF EXISTS categories CASCADE;
drop table IF EXISTS events CASCADE;
drop table IF EXISTS locations CASCADE;
drop table IF EXISTS requests CASCADE;
drop table IF Exists events_compilations cascade;
drop table IF Exists compilations cascade;

create table if not exists users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(512) NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id),
  CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

create table if not exists categories (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY ,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT UQ_NAME_CATEGORY UNIQUE (name)
);

create table if not exists locations (
     id BIGINT GENERATED BY DEFAULT AS IDENTITY,
     lat float NOT NULL,
     lon float NOT NULL,
     CONSTRAINT loc_pk PRIMARY KEY (id)
);

create table if not exists events (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY not null constraint events_pk primary key ,
    annotation VARCHAR(2000) NOT NULL,
    title VARCHAR(512) NOT NULL,
    category_id bigint not null constraint event_category_id_fk references categories
        on update cascade on delete cascade,
    description  VARCHAR(7000) NOT NULL,
    paid boolean not null,
    created_on timestamp without time zone,
    event_date timestamp without time zone,
    user_id bigint not null constraint event_user_id_fk references users
        on update cascade on delete cascade,
    participant_limit INT NOT NULL,
    request_Moderation boolean NOT NULL,
    published_On timestamp without time zone,
    loc_id bigint not null constraint event_location_id_fk references locations
        on update cascade on delete cascade,
    state varchar(512) not null,
    views BIGINT
);

create table if not exists requests (
     id BIGINT GENERATED BY DEFAULT AS IDENTITY,
     event_id bigint not null constraint request_event_id_fk references events
        on update cascade on delete cascade,
     CONSTRAINT request_pk PRIMARY KEY (id),
     user_id bigint not null constraint request_user_id_fk references users
        on update cascade on delete cascade,
    status varchar(512) not null,
    created timestamp without time zone
);

create table if not exists compilations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    pinned boolean,
    title varchar(512) not null,
    CONSTRAINT compilation_pk PRIMARY KEY (id)
);

create table if not exists events_compilations (
    event_id BIGINT,
    compilation_id BIGINT  not null,
    primary key(event_id,compilation_id),
    CONSTRAINT event_compilation_fk FOREIGN KEY (event_id) REFERENCES events,
    CONSTRAINT event_com_compilation_fk FOREIGN KEY (compilation_id) REFERENCES compilations
);

