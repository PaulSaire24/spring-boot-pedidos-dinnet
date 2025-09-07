--
-- PostgreSQL database dump
--

\restrict CfQyqvD8WLnBb5bRQQ614ZyDcosuXIHoduLzWOclV4YnAwbps9dvm2TJvFl3zDU

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: cargas_idempotencia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cargas_idempotencia (
    archivo_hash character varying(255),
    created_at timestamp without time zone,
    id uuid NOT NULL,
    idempotency_key character varying(255) NOT NULL
);


ALTER TABLE public.cargas_idempotencia OWNER TO postgres;

--
-- Name: clientes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clientes (
    id character varying(255) NOT NULL,
    activo boolean
);


ALTER TABLE public.clientes OWNER TO postgres;

--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- Name: pedidos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pedidos (
    numero_pedido character varying(255) NOT NULL,
    cliente_id character varying(255),
    zona_id character varying(255),
    estado character varying(255),
    requiere_refrigeracion boolean,
    id uuid NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    fecha_entrega date
);


ALTER TABLE public.pedidos OWNER TO postgres;

--
-- Name: zonas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.zonas (
    id character varying(255) NOT NULL,
    soporte_refrigeracion boolean
);


ALTER TABLE public.zonas OWNER TO postgres;

--
-- Data for Name: cargas_idempotencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cargas_idempotencia (archivo_hash, created_at, id, idempotency_key) FROM stdin;
q/fSILGgdqLecrNN9XI/vuzpfgahLeLxFj8tFJi1K/g=	2025-09-06 14:45:42.533547	ab9afd99-7d1b-4a31-b39f-3801536f9b07	key-123
BCwBJHS8LZjcanTO0vi2Osc2f1H+kGQsDVuGJVSpOt8=	2025-09-07 11:00:48.058091	a0b8890c-1b91-4fb1-a0bf-c81d9884aaf6	key-456
\.


--
-- Data for Name: clientes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clientes (id, activo) FROM stdin;
CLI-999	t
CLI-123	t
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	<< Flyway Baseline >>	BASELINE	<< Flyway Baseline >>	\N	postgres	2025-09-07 11:55:44.594759	0	t
\.


--
-- Data for Name: pedidos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pedidos (numero_pedido, cliente_id, zona_id, estado, requiere_refrigeracion, id, created_at, updated_at, fecha_entrega) FROM stdin;
P003	CLI-123	ZONA1	PENDIENTE	t	04c47ffc-1731-4645-83df-0c1a944a32c7	2025-09-06 14:45:42.374425	\N	2025-09-06
P004	CLI-999	ZONA5	ENTREGADO	f	5316cc6d-08e2-45d1-9a10-1b5b8731a093	2025-09-06 14:45:42.374425	\N	2025-09-06
P005	CLI-123	ZONA1	PENDIENTE	t	7cec0434-ce4c-4a9b-a00f-590ee5c3b1e4	2025-09-07 11:00:47.302671	\N	2025-09-07
P006	CLI-999	ZONA5	ENTREGADO	f	3a2dcdcc-05cb-44ad-af3d-4d66f5dc2b80	2025-09-07 11:00:47.302671	\N	2025-09-07
\.


--
-- Data for Name: zonas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.zonas (id, soporte_refrigeracion) FROM stdin;
ZONA5	t
ZONA1	t
\.


--
-- Name: cargas_idempotencia cargas_idempotencia_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cargas_idempotencia
    ADD CONSTRAINT cargas_idempotencia_pk PRIMARY KEY (id);


--
-- Name: cargas_idempotencia cargas_idempotencia_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cargas_idempotencia
    ADD CONSTRAINT cargas_idempotencia_unique UNIQUE (idempotency_key);


--
-- Name: clientes clientes_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_pk PRIMARY KEY (id);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: pedidos pedidos_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos
    ADD CONSTRAINT pedidos_pk PRIMARY KEY (id);


--
-- Name: pedidos pedidos_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos
    ADD CONSTRAINT pedidos_unique UNIQUE (numero_pedido);


--
-- Name: zonas zonas_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zonas
    ADD CONSTRAINT zonas_pk PRIMARY KEY (id);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: pedidos pedidos_clientes_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos
    ADD CONSTRAINT pedidos_clientes_fk FOREIGN KEY (cliente_id) REFERENCES public.clientes(id);


--
-- Name: pedidos pedidos_zonas_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos
    ADD CONSTRAINT pedidos_zonas_fk FOREIGN KEY (zona_id) REFERENCES public.zonas(id);


--
-- PostgreSQL database dump complete
--

\unrestrict CfQyqvD8WLnBb5bRQQ614ZyDcosuXIHoduLzWOclV4YnAwbps9dvm2TJvFl3zDU

