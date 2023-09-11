-- DROP SCHEMA product RESTRICT;

CREATE SCHEMA product;

--     A product has a numeric identifier,
--     a name with at least 4 characters and no more than 80,
--     an optional description and a
--     flag indicating if it is in stock.

-- DROP TABLE product.product;


CREATE TABLE product.product
(
    id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name        character varying(80) NOT NULL,
    description character varying(1024),
    is_in_stock boolean NOT NULL DEFAULT false,
    CONSTRAINT min_char_ CHECK (length(name) >= 4)
);



-- A warehouse has a numeric identifier,
-- a name with at least 10 characters and no more than 80 and
-- a zip code.
--DROP TABLE product.warehouse;

CREATE TABLE product.warehouse
(
    id       BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name     character varying(80) NOT NULL,
    zip_code character varying(5) NOT NULL,
    CONSTRAINT min_char_name CHECK (length(name) >= 10),
    CONSTRAINT min_char_zip CHECK (length(zip_code) = 5)

);

--    A product can only be sold from one warehouse at a time but
--    may be transferred from one warehouse to another.
--    In this case, there can only be one combination of product - warehouse
CREATE TABLE product.product_warehouse
(
    product_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    CONSTRAINT pw_unk UNIQUE (product_id, warehouse_id)
)