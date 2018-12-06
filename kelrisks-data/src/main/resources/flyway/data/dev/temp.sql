-- auto-generated definition
create table basias
(
  id              bigserial not null
    constraint basias_pk
      primary key,
  identifiant     varchar(255),
  adresse         varchar(255),
  code_activite   varchar(255),
  geolocalisation varchar(255),
  geog            kelrisks.geometry,
  precision       varchar(255)
);

alter table basias
  owner to postgres;

create unique index basias_id_uindex
  on basias (id);

-- auto-generated definition
create table cadastre
(
  id        bigserial not null
    constraint cadastre_pk
      primary key,
  code      varchar(255),
  type      varchar(255),
  commune   varchar(355),
  type_geom varchar(255),
  geog      kelrisks.geometry
);

alter table cadastre
  owner to postgres;

create unique index cadastre_id_uindex
  on cadastre (id);

--

CREATE EXTENSION postgis;

create function public.geomfromtext(wkt character varying, srid integer) returns kelrisks.geometry
  language sql
as
$$
SELECT public.ST_GeomFromText(WKT, srid)
$$;

alter function public.geomfromtext(varchar, integer) owner to postgres;

