--
-- PostgreSQL database dump
--

-- Dumped from database version 14.9 (Debian 14.9-1.pgdg120+1)
-- Dumped by pg_dump version 15.3

-- Started on 2023-09-11 00:09:18 EDT

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 16436)
-- Name: product; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA product;


ALTER SCHEMA product OWNER TO postgres;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16446)
-- Name: product_id_seq; Type: SEQUENCE; Schema: product; Owner: postgres
--

CREATE SEQUENCE product.product_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE product.product_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 16437)
-- Name: product; Type: TABLE; Schema: product; Owner: postgres
--

CREATE TABLE product.product (
                                 id bigint DEFAULT nextval('product.product_id_seq'::regclass) NOT NULL,
                                 name character varying(80) NOT NULL,
                                 description character varying(1024),
                                 is_in_stock boolean DEFAULT false NOT NULL,
                                 CONSTRAINT min_char_ CHECK ((length((name)::text) >= 4))
);


ALTER TABLE product.product OWNER TO postgres;

--
-- TOC entry 3365 (class 0 OID 0)
-- Dependencies: 210
-- Name: TABLE product; Type: COMMENT; Schema: product; Owner: postgres
--

COMMENT ON TABLE product.product IS 'A product has a numeric identifier, a name with at least 4 characters and no more than 80, an optional description and a flag indicating if it is in stock.
';


--
-- TOC entry 3366 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN product.name; Type: COMMENT; Schema: product; Owner: postgres
--

COMMENT ON COLUMN product.product.name IS 'name with at least 4 characters and no more than 80';


--
-- TOC entry 3367 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN product.description; Type: COMMENT; Schema: product; Owner: postgres
--

COMMENT ON COLUMN product.product.description IS 'optional description';


--
-- TOC entry 3368 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN product.is_in_stock; Type: COMMENT; Schema: product; Owner: postgres
--

COMMENT ON COLUMN product.product.is_in_stock IS 'flag indicating if it is in stock.';


--
-- TOC entry 212 (class 1259 OID 16447)
-- Name: product_warehouse; Type: TABLE; Schema: product; Owner: postgres
--

CREATE TABLE product.product_warehouse (
                                           product_id bigint NOT NULL,
                                           warehouse_id bigint NOT NULL
);


ALTER TABLE product.product_warehouse OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16455)
-- Name: warehouse_id_seq; Type: SEQUENCE; Schema: product; Owner: postgres
--

CREATE SEQUENCE product.warehouse_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE product.warehouse_id_seq OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16450)
-- Name: warehouse; Type: TABLE; Schema: product; Owner: postgres
--

CREATE TABLE product.warehouse (
                                   id bigint DEFAULT nextval('product.warehouse_id_seq'::regclass) NOT NULL,
                                   name character varying(80) NOT NULL,
                                   zip_code character varying(5) NOT NULL,
                                   CONSTRAINT min_char_name CHECK ((length((name)::text) >= 10)),
                                   CONSTRAINT min_char_zip CHECK ((length((zip_code)::text) = 5))
);


ALTER TABLE product.warehouse OWNER TO postgres;

--
-- TOC entry 3369 (class 0 OID 0)
-- Dependencies: 213
-- Name: TABLE warehouse; Type: COMMENT; Schema: product; Owner: postgres
--

COMMENT ON TABLE product.warehouse IS 'A warehouse has a numeric identifier, a name with at least 10 characters and no more than 80 and a zip code..
';


--
-- TOC entry 3370 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN warehouse.name; Type: COMMENT; Schema: product; Owner: postgres
--

COMMENT ON COLUMN product.warehouse.name IS 'name with at least 10 characters and no more than 80';


--
-- TOC entry 3371 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN warehouse.zip_code; Type: COMMENT; Schema: product; Owner: postgres
--

COMMENT ON COLUMN product.warehouse.zip_code IS 'US Five Character Zip Code.';


--
-- TOC entry 3372 (class 0 OID 0)
-- Dependencies: 211
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: product; Owner: postgres
--

SELECT pg_catalog.setval('product.product_id_seq', 14, true);


--
-- TOC entry 3373 (class 0 OID 0)
-- Dependencies: 214
-- Name: warehouse_id_seq; Type: SEQUENCE SET; Schema: product; Owner: postgres
--

SELECT pg_catalog.setval('product.warehouse_id_seq', 11, true);


--
-- TOC entry 3207 (class 2606 OID 16445)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: product; Owner: postgres
--

ALTER TABLE ONLY product.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 3209 (class 2606 OID 16459)
-- Name: product_warehouse pw_pk; Type: CONSTRAINT; Schema: product; Owner: postgres
--

ALTER TABLE ONLY product.product_warehouse
    ADD CONSTRAINT pw_pk PRIMARY KEY (product_id, warehouse_id);


--
-- TOC entry 3211 (class 2606 OID 16468)
-- Name: warehouse unique_warehouse_name; Type: CONSTRAINT; Schema: product; Owner: postgres
--

ALTER TABLE ONLY product.warehouse
    ADD CONSTRAINT unique_warehouse_name UNIQUE (name);


--
-- TOC entry 3213 (class 2606 OID 16461)
-- Name: warehouse warehouse_pkey; Type: CONSTRAINT; Schema: product; Owner: postgres
--

ALTER TABLE ONLY product.warehouse
    ADD CONSTRAINT warehouse_pkey PRIMARY KEY (id);


--
-- TOC entry 3214 (class 2606 OID 16462)
-- Name: product_warehouse warehouse_fk; Type: FK CONSTRAINT; Schema: product; Owner: postgres
--

ALTER TABLE ONLY product.product_warehouse
    ADD CONSTRAINT warehouse_fk FOREIGN KEY (warehouse_id) REFERENCES product.warehouse(id) NOT VALID;


--
-- TOC entry 3364 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2023-09-11 00:09:18 EDT

--
-- PostgreSQL database dump complete
--

