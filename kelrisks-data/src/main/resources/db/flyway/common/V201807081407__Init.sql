create table if not exists adresse_commune
(
    code_insee  varchar(10),
    code_postal varchar(10),
    nom_commune varchar(255),
    id          bigserial         not null
        constraint commune_pk
            primary key,
    version     integer default 1 not null
);

alter table adresse_commune
    owner to postgres;

create index if not exists commune_code_insee_index
    on adresse_commune (code_insee);

create index if not exists commune_code_postal_index
    on adresse_commune (code_postal);

create unique index if not exists commune_id_uindex
    on adresse_commune (id);

create index if not exists commune_nom_commune_index
    on adresse_commune (nom_commune);

create table if not exists adresse_rue
(
    id                   bigserial not null
        constraint adresse_rue_pk
            primary key,
    version              integer default 1,
    nom_voie             varchar(255),
    nom_afnor            varchar(255),
    libelle_acheminement varchar(255),
    alias                varchar(255),
    adresse_commune_id   bigint
        constraint adresse_rue_commune_id_fkey
            references adresse_commune,
    id_fantoir           varchar(255)
);

alter table adresse_rue
    owner to postgres;

create unique index if not exists adresse_rue_id_uindex
    on adresse_rue (id);

create table if not exists ssp
(
    id          bigserial not null
        constraint ssp_pk
            primary key,
    nom         varchar(255),
    description varchar(255),
    geog        public.geometry,
    version     integer default 0
);

alter table ssp
    owner to postgres;

create index if not exists ssp_geog_index
    on ssp (geog);

create table if not exists short_url
(
    id       bigserial   not null
        constraint table_name_pk
            primary key,
    url      text,
    code     varchar(10) not null,
    version  integer default 1,
    date_maj date    default now()
);

alter table short_url
    owner to postgres;

create unique index if not exists table_name_code_uindex
    on short_url (code);

create table if not exists basias
(
    id              bigserial         not null
        constraint basias_pk
            primary key,
    identifiant     varchar(255),
    adresse         varchar(255),
    code_activite   varchar(255),
    geolocalisation varchar(255),
    geog            public.geometry,
    precision       varchar(255),
    version         integer default 0 not null,
    raison_sociale  text,
    commune         varchar(255),
    nom             varchar(255)
);

alter table basias
    owner to postgres;

create index if not exists basias_geog_index
    on basias (geog);

create unique index if not exists basias_id_uindex
    on basias (id);

create index if not exists basias_identifiant_idx
    on basias (identifiant);

create table if not exists basol
(
    numerosite        varchar(255),
    numerobasol       varchar(255),
    numeros3ic        varchar(255),
    identifiantbasias varchar(255),
    georeferencement  text,
    coordxlambertii   varchar(255),
    coordylambertii   varchar(255),
    precision         varchar(255),
    adresse           varchar(255),
    commune           varchar(255),
    codeinsee         varchar(255),
    codepostal        varchar(255),
    proprietaire      varchar(255),
    responsable       varchar(255),
    geog              public.geometry,
    id                bigserial not null
        constraint basol_pk
            primary key,
    version           integer
);

alter table basol
    owner to postgres;

create index if not exists basol_geog_index
    on basol (geog);

create table if not exists cadastre
(
    id        bigserial         not null
        constraint cadastre_pk
            primary key,
    code      varchar(255),
    type      varchar(255),
    commune   varchar(355),
    type_geom varchar(255),
    geog      public.geometry,
    prefixe   varchar(255),
    section   varchar(255),
    numero    varchar(255),
    version   integer default 0 not null
);

alter table cadastre
    owner to postgres;

create index if not exists cadastre_code_index
    on cadastre (code);

create index if not exists cadastre_commune_section_numero_idx
    on cadastre (commune, section, numero);

create index if not exists cadastre_geog_index
    on cadastre (geog);

create table if not exists s3ic
(
    id                 bigserial not null
        constraint s3ic_pkey
            primary key,
    code               varchar(255),
    nom                varchar(255),
    raison_sociale     varchar(255),
    etat_activite      varchar(255),
    regime             varchar(255),
    commune            varchar(255),
    code_insee         varchar(255),
    code_postal        varchar(255),
    adresse            varchar(255),
    complement_adresse varchar(255),
    departement        varchar(255),
    x                  real,
    y                  real,
    precision          varchar(255),
    geocoded_latitude  real,
    geocoded_longitude real,
    geocoded_score     real,
    geocoded_precision varchar(255),
    geocoded_label     text,
    geog               public.geometry,
    geocoded_geog      public.geometry,
    centroide_commune  boolean,
    version            integer
);

alter table s3ic
    owner to postgres;

create table if not exists sis
(
    id               bigserial       not null
        constraint sis_pkey
            primary key,
    id_sis           varchar(255),
    numero_affichage varchar(255),
    numero_basol     varchar(255),
    adresse          text,
    lieu_dit         varchar(255),
    code_insee       varchar(255),
    nom_commune      varchar(255),
    code_departement varchar(255),
    nom_departement  varchar(255),
    surface_m2       real,
    x                double precision,
    y                double precision,
    geog             public.geometry not null,
    geog_centroid    public.geometry not null,
    version          integer
);

alter table sis
    owner to postgres;

