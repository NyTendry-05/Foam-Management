CREATE SEQUENCE block_id_seq START 1;
CREATE SEQUENCE forme_usuelle_id_seq START 1;
CREATE SEQUENCE stock_id_seq START 1;
-- CREATE SEQUENCE block_histo_id_seq START 1;

ALTER SEQUENCE block_id_seq RESTART WITH 1;
ALTER SEQUENCE forme_usuelle_id_seq RESTART WITH 1;
ALTER SEQUENCE stock_id_seq RESTART WITH 1;
-- ALTER SEQUENCE block_histo_id_seq RESTART WITH 1;

CREATE TABLE block (
    id_block VARCHAR(10) PRIMARY KEY DEFAULT 'BLOC' || LPAD(nextval('block_id_seq')::text, 6, '0'),
    id_mere VARCHAR(10),
    id_origine VARCHAR(10),
    longueur DECIMAL,
    largeur DECIMAL,
    hauteur DECIMAL,
    prix_revient DECIMAL,
    date_insertion DATE,
    date_decoupe DATE
);


-- CREATE TABLE block_histo(
--     id_block_histo VARCHAR(10) ,
--     id_block VARCHAR(10),
--     old_price DECIMAL,
--     new_price DECIMAL,
--     date_changement date
-- );


CREATE TABLE forme_usuelle (
    id_forme_usuelle VARCHAR(10) PRIMARY KEY DEFAULT 'FORM' || LPAD(nextval('forme_usuelle_id_seq')::text, 6, '0'),
    nom VARCHAR(50),
    longueur DECIMAL,
    largeur DECIMAL,
    hauteur DECIMAL,
    prix_vente DECIMAL
);

CREATE TABLE stock (
    id_stock VARCHAR(10) PRIMARY KEY DEFAULT 'STOC' || LPAD(nextval('stock_id_seq')::text, 6, '0'),
    id_forme_usuelle VARCHAR(10) REFERENCES forme_usuelle(id_forme_usuelle),
    id_block VARCHAR(10) REFERENCES block(id_block),
    quantite DECIMAL,
    date_stock DATE
);

-- Insérer des blocs de mousse (éponge en gros) avec des prix logiques et la date actuelle
INSERT INTO block (id_mere, id_origine, longueur, largeur, hauteur, prix_revient, date_insertion)
VALUES
    (NULL, NULL, 20, 10, 5, 20, CURRENT_DATE),   -- Bloc 20x10x5 cm, prix revient 20 €
    (NULL, NULL, 30, 10, 4, 25, CURRENT_DATE),   -- Bloc 30x10x4 cm, prix revient 25 €
    (NULL, NULL, 25, 15, 6, 30, CURRENT_DATE);   -- Bloc 25x15x6 cm, prix revient 30 €

-- Insérer les formes usuelles avec les dimensions et prix en EUR
INSERT INTO forme_usuelle (nom, longueur, largeur, hauteur, prix_vente) 
VALUES 
    ('u1', 16, 4, 2, 20000),             -- Éponge de 0.1x0.1x0.1 cm, prix de vente 0.2 €
    ('u2', 10, 7, 1, 12000),      -- Matelas King
    ('u3', 5, 1, 1, 600),     -- Matelas Queen
