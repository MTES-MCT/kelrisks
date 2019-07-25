DROP TABLE IF EXISTS kelrisks.basol;
DROP TABLE IF EXISTS kelrisks.basol_parcelle;

-- auto-generated definition
create table kelrisks.basol
(
    id                 bigserial not null
        constraint basol_pkey
            primary key,
    region             varchar(255),
    departement        varchar(255),
    num                integer,
    numerobasol        varchar(255),
    numerogidic        varchar(255),
    identifiantbasias  varchar(255),
    date_publication   date,
    sis                varchar(255),
    etat               text,
    georeferencement   text,
    coordxlambertii    double precision,
    coordylambertii    double precision,
    l2e_precision      text,
    coordxlambert93    double precision,
    coordylambert93    double precision,
    l93_precision      text,
    cadastre_multi     text,
    adresse            text,
    lieu_dit           text,
    commune            varchar(255),
    code_insee         varchar(255),
    arrondissement     varchar(255),
    proprietaire       text,
    version            integer,
    geocoded_latitude  real,
    geocoded_longitude real,
    geocoded_score     real,
    geocoded_precision varchar(255),
    geocoded_label     text,
    geocoded_geog      public.geometry,
    geog               public.geometry,
    precision          text
);

-- auto-generated definition
create table kelrisks.basol_parcelle
(
    id          bigserial not null
        constraint basol_parcelle_pkey
            primary key,
    version     integer,
    numerobasol varchar(255),
    commune     varchar(255),
    section     varchar(255),
    numero      varchar(255)
);

alter table kelrisks.basol_parcelle
    owner to postgres;

create index basol_parcelle_numerobasol
    on kelrisks.basol_parcelle (numerobasol);

