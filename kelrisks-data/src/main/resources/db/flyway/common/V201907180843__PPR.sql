DROP TABLE IF EXISTS kelrisks.ppr;
DROP TABLE IF EXISTS kelrisks.ref_categorie_ppr;
DROP TABLE IF EXISTS kelrisks.ref_famille_ppr;

CREATE TABLE IF NOT EXISTS kelrisks.ref_famille_ppr
(
    id            BIGSERIAL         NOT NULL PRIMARY KEY,
    code          varchar(5),
    libelle_court varchar(50),
    version       INTEGER DEFAULT 1 NOT NULL
);

INSERT INTO kelrisks.ref_famille_ppr
VALUES (1, 'PPRN', 'Plan de prévention des risques naturels', 1),
       (2, 'PPRT', 'Plan de prévention des risques technologiques', 1),
       (3, 'PPRM', 'Plan de prévention des risques miniers', 1);

CREATE TABLE IF NOT EXISTS kelrisks.ref_categorie_ppr
(
    id          BIGSERIAL         NOT NULL PRIMARY KEY,
    code        varchar(5),
    libelle     varchar(25),
    description varchar(250),
    famille_ppr BIGINT REFERENCES kelrisks.ref_famille_ppr,
    version     INTEGER DEFAULT 1 NOT NULL
);

INSERT INTO kelrisks.ref_categorie_ppr
VALUES (1, 'INOND', 'Inondations', '', 1, 1),
       (2, 'SEISM', 'Séismes', '', 1, 1),
       (3, 'ERUPT', 'Éruptions volcaniques', '', 1, 1),
       (4, 'MOUVE', 'Mouvements de terrain',
        'Les affaissements et les effondrements liés aux cavités souterraines ; les éboulements et les chutes de pierres et de blocs ;  les glissements de terrain ;  le retrait-gonflement des sols argileux',
        1, 1),
       (5, 'AVALA', 'Avalanches', '', 1, 1),
       (6, 'INCEN', 'Incendies de forêt', '', 1, 1),
       (7, 'CYCLO', 'Cyclones', '', 1, 1),
       (8, 'TEMPE', 'Tempêtes', '', 1, 1);


CREATE TABLE IF NOT EXISTS kelrisks.ppr
(
    id            BIGSERIAL         NOT NULL PRIMARY KEY,
    geog          public.geometry,
    id_gaspar     varchar(25),
    date_validite date,
    libelle       text,
    titre         text,
    categorie_ppr BIGINT REFERENCES kelrisks.ref_categorie_ppr,
    version       INTEGER DEFAULT 1 NOT NULL
);