--
-- PostgreSQL database dump
--

-- Dumped from database version 14.18 (Ubuntu 14.18-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.18 (Ubuntu 14.18-0ubuntu0.22.04.1)

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
-- Name: categoriefood; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.categoriefood AS ENUM (
    'LEGUMES',
    'FRUITS',
    'VIANDES',
    'CEREALES',
    'DESSERTS'
);


ALTER TYPE public.categoriefood OWNER TO postgres;

--
-- Name: typeimage; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.typeimage AS ENUM (
    'PRINCIPALE',
    'GALERIE',
    'MINIATURE'
);


ALTER TYPE public.typeimage OWNER TO postgres;

--
-- Name: typeingredient; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.typeingredient AS ENUM (
    'FRAIS',
    'SURGELE',
    'CONSERVE',
    'SEC'
);


ALTER TYPE public.typeingredient OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: food; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.food (
    id bigint NOT NULL,
    nom character varying(100) NOT NULL,
    description character varying(500),
    calories double precision,
    prix double precision,
    temps_preparation character varying(50),
    categorie public.categoriefood NOT NULL,
    date_creation timestamp without time zone NOT NULL,
    personne_id bigint NOT NULL
);


ALTER TABLE public.food OWNER TO postgres;

--
-- Name: food_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.food_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.food_id_seq OWNER TO postgres;

--
-- Name: food_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.food_id_seq OWNED BY public.food.id;


--
-- Name: food_ingredient; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.food_ingredient (
    food_id bigint NOT NULL,
    ingredient_id bigint NOT NULL,
    quantite_utilisee double precision NOT NULL,
    unite character varying(20) NOT NULL
);


ALTER TABLE public.food_ingredient OWNER TO postgres;

--
-- Name: food_ingredients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.food_ingredients (
    food_id bigint NOT NULL,
    ingredient_id bigint NOT NULL
);


ALTER TABLE public.food_ingredients OWNER TO postgres;

--
-- Name: foods; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.foods (
    id bigint NOT NULL,
    nom character varying(100) NOT NULL,
    description character varying(500),
    categorie character varying(255) NOT NULL,
    calories double precision,
    date_creation timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    personne_id bigint
);


ALTER TABLE public.foods OWNER TO postgres;

--
-- Name: foods_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.foods_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.foods_id_seq OWNER TO postgres;

--
-- Name: foods_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.foods_id_seq OWNED BY public.foods.id;


--
-- Name: image; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.image (
    id bigint NOT NULL,
    nom_fichier character varying(255) NOT NULL,
    chemin_fichier character varying(500) NOT NULL,
    taille_fichier bigint,
    type_image public.typeimage NOT NULL,
    date_upload timestamp without time zone NOT NULL,
    food_id bigint,
    ingredient_id bigint
);


ALTER TABLE public.image OWNER TO postgres;

--
-- Name: image_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.image_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.image_id_seq OWNER TO postgres;

--
-- Name: image_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.image_id_seq OWNED BY public.image.id;


--
-- Name: images; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.images (
    id bigint NOT NULL,
    nom_fichier character varying(255) NOT NULL,
    chemin character varying(500) NOT NULL,
    type_mime character varying(100),
    taille bigint,
    description character varying(500),
    date_upload timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    food_id bigint
);


ALTER TABLE public.images OWNER TO postgres;

--
-- Name: images_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.images_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.images_id_seq OWNER TO postgres;

--
-- Name: images_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.images_id_seq OWNED BY public.images.id;


--
-- Name: ingredient; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ingredient (
    id bigint NOT NULL,
    nom character varying(100) NOT NULL,
    description character varying(300),
    quantite double precision,
    unite character varying(20),
    type public.typeingredient NOT NULL,
    date_creation timestamp without time zone NOT NULL
);


ALTER TABLE public.ingredient OWNER TO postgres;

--
-- Name: ingredient_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ingredient_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ingredient_id_seq OWNER TO postgres;

--
-- Name: ingredient_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ingredient_id_seq OWNED BY public.ingredient.id;


--
-- Name: ingredients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ingredients (
    id bigint NOT NULL,
    nom character varying(100) NOT NULL,
    description character varying(300),
    type character varying(255),
    valeur_nutritive character varying(255)
);


ALTER TABLE public.ingredients OWNER TO postgres;

--
-- Name: ingredients_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ingredients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ingredients_id_seq OWNER TO postgres;

--
-- Name: ingredients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ingredients_id_seq OWNED BY public.ingredients.id;


--
-- Name: personne; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.personne (
    id bigint NOT NULL,
    nom character varying(100) NOT NULL,
    email character varying(150) NOT NULL,
    mot_de_passe character varying(255) NOT NULL,
    telephone character varying(20),
    date_creation timestamp without time zone NOT NULL
);


ALTER TABLE public.personne OWNER TO postgres;

--
-- Name: personne_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.personne_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.personne_id_seq OWNER TO postgres;

--
-- Name: personne_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.personne_id_seq OWNED BY public.personne.id;


--
-- Name: personnes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.personnes (
    id bigint NOT NULL,
    nom character varying(50) NOT NULL,
    prenom character varying(50) NOT NULL,
    email character varying(255),
    date_naissance date,
    telephone character varying(15)
);


ALTER TABLE public.personnes OWNER TO postgres;

--
-- Name: personnes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.personnes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.personnes_id_seq OWNER TO postgres;

--
-- Name: personnes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.personnes_id_seq OWNED BY public.personnes.id;


--
-- Name: food id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.food ALTER COLUMN id SET DEFAULT nextval('public.food_id_seq'::regclass);


--
-- Name: foods id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.foods ALTER COLUMN id SET DEFAULT nextval('public.foods_id_seq'::regclass);


--
-- Name: image id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.image ALTER COLUMN id SET DEFAULT nextval('public.image_id_seq'::regclass);


--
-- Name: images id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.images ALTER COLUMN id SET DEFAULT nextval('public.images_id_seq'::regclass);


--
-- Name: ingredient id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredient ALTER COLUMN id SET DEFAULT nextval('public.ingredient_id_seq'::regclass);


--
-- Name: ingredients id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredients ALTER COLUMN id SET DEFAULT nextval('public.ingredients_id_seq'::regclass);


--
-- Name: personne id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personne ALTER COLUMN id SET DEFAULT nextval('public.personne_id_seq'::regclass);


--
-- Name: personnes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personnes ALTER COLUMN id SET DEFAULT nextval('public.personnes_id_seq'::regclass);


--
-- Data for Name: food; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.food (id, nom, description, calories, prix, temps_preparation, categorie, date_creation, personne_id) FROM stdin;
\.


--
-- Data for Name: food_ingredient; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.food_ingredient (food_id, ingredient_id, quantite_utilisee, unite) FROM stdin;
\.


--
-- Data for Name: food_ingredients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.food_ingredients (food_id, ingredient_id) FROM stdin;
1	1
1	2
1	3
1	5
5	3
6	8
\.


--
-- Data for Name: foods; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.foods (id, nom, description, categorie, calories, date_creation, personne_id) FROM stdin;
1	Pain	Pain de mie blanc	CEREALES	250	2025-05-31 05:33:05.455202	1
2	Pomme	Pomme rouge du Cameroun	FRUITS	52	2025-05-31 05:33:05.455202	2
3	Banane	Banane plantain	FRUITS	89	2025-05-31 05:33:05.455202	1
4	Riz	Riz jasmin	CEREALES	130	2025-05-31 05:33:05.455202	3
5	Poulet	Blanc de poulet fermier	PROTEINES	165	2025-05-31 05:33:05.455202	4
6	Lait	Lait entier frais	PRODUITS_LAITIERS	61	2025-05-31 05:33:05.455202	2
7	Tomate	Tomate fraîche	LEGUMES	18	2025-05-31 05:33:05.455202	3
8	Carotte	Carotte bio	LEGUMES	25	2025-05-31 05:33:05.455202	1
\.


--
-- Data for Name: image; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.image (id, nom_fichier, chemin_fichier, taille_fichier, type_image, date_upload, food_id, ingredient_id) FROM stdin;
\.


--
-- Data for Name: images; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.images (id, nom_fichier, chemin, type_mime, taille, description, date_upload, food_id) FROM stdin;
1	pain.jpg	/images/foods/pain.jpg	image/jpeg	245760	Photo du pain de mie	2025-05-31 05:33:05.460406	1
2	pomme.png	/images/foods/pomme.png	image/png	156780	Photo de la pomme rouge	2025-05-31 05:33:05.460406	2
3	banane.jpg	/images/foods/banane.jpg	image/jpeg	198450	Photo de la banane plantain	2025-05-31 05:33:05.460406	3
4	riz.webp	/images/foods/riz.webp	image/webp	89340	Photo du riz jasmin	2025-05-31 05:33:05.460406	4
\.


--
-- Data for Name: ingredient; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ingredient (id, nom, description, quantite, unite, type, date_creation) FROM stdin;
\.


--
-- Data for Name: ingredients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ingredients (id, nom, description, type, valeur_nutritive) FROM stdin;
1	Farine	Farine de blé	NATUREL	\N
2	Sucre	Sucre blanc raffiné	NATUREL	\N
3	Sel	Sel de cuisine	NATUREL	\N
4	Huile	Huile de tournesol	NATUREL	\N
5	Levure	Levure de boulanger	NATUREL	\N
6	Vanille	Extrait de vanille	AROME	\N
7	Colorant rouge	Colorant alimentaire rouge	COLORANT	\N
8	Conservateur E200	Acide sorbique	CONSERVATEUR	\N
\.


--
-- Data for Name: personne; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.personne (id, nom, email, mot_de_passe, telephone, date_creation) FROM stdin;
\.


--
-- Data for Name: personnes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.personnes (id, nom, prenom, email, date_naissance, telephone) FROM stdin;
1	Doe	John	john.doe@email.com	\N	123456789
2	Smith	Jane	jane.smith@email.com	\N	987654321
3	Mbarga	Paul	paul.mbarga@email.com	\N	696123456
4	Ngono	Marie	marie.ngono@email.com	\N	677987654
\.


--
-- Name: food_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.food_id_seq', 1, false);


--
-- Name: foods_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.foods_id_seq', 8, true);


--
-- Name: image_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.image_id_seq', 1, false);


--
-- Name: images_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.images_id_seq', 4, true);


--
-- Name: ingredient_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ingredient_id_seq', 1, false);


--
-- Name: ingredients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ingredients_id_seq', 8, true);


--
-- Name: personne_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.personne_id_seq', 1, false);


--
-- Name: personnes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.personnes_id_seq', 4, true);


--
-- Name: food_ingredient food_ingredient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.food_ingredient
    ADD CONSTRAINT food_ingredient_pkey PRIMARY KEY (food_id, ingredient_id);


--
-- Name: food_ingredients food_ingredients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.food_ingredients
    ADD CONSTRAINT food_ingredients_pkey PRIMARY KEY (food_id, ingredient_id);


--
-- Name: food food_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.food
    ADD CONSTRAINT food_pkey PRIMARY KEY (id);


--
-- Name: foods foods_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.foods
    ADD CONSTRAINT foods_pkey PRIMARY KEY (id);


--
-- Name: image image_ingredient_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT image_ingredient_id_key UNIQUE (ingredient_id);


--
-- Name: image image_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT image_pkey PRIMARY KEY (id);


--
-- Name: images images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.images
    ADD CONSTRAINT images_pkey PRIMARY KEY (id);


--
-- Name: ingredient ingredient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredient
    ADD CONSTRAINT ingredient_pkey PRIMARY KEY (id);


--
-- Name: ingredients ingredients_nom_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredients
    ADD CONSTRAINT ingredients_nom_key UNIQUE (nom);


--
-- Name: ingredients ingredients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredients
    ADD CONSTRAINT ingredients_pkey PRIMARY KEY (id);


--
-- Name: personne personne_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personne
    ADD CONSTRAINT personne_email_key UNIQUE (email);


--
-- Name: personne personne_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personne
    ADD CONSTRAINT personne_pkey PRIMARY KEY (id);


--
-- Name: personnes personnes_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personnes
    ADD CONSTRAINT personnes_email_key UNIQUE (email);


--
-- Name: personnes personnes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personnes
    ADD CONSTRAINT personnes_pkey PRIMARY KEY (id);


--
-- Name: food_ingredient food_ingredient_food_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.food_ingredient
    ADD CONSTRAINT food_ingredient_food_id_fkey FOREIGN KEY (food_id) REFERENCES public.food(id);


--
-- Name: food_ingredient food_ingredient_ingredient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.food_ingredient
    ADD CONSTRAINT food_ingredient_ingredient_id_fkey FOREIGN KEY (ingredient_id) REFERENCES public.ingredient(id);


--
-- Name: food_ingredients food_ingredients_food_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.food_ingredients
    ADD CONSTRAINT food_ingredients_food_id_fkey FOREIGN KEY (food_id) REFERENCES public.foods(id) ON DELETE CASCADE;


--
-- Name: food_ingredients food_ingredients_ingredient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.food_ingredients
    ADD CONSTRAINT food_ingredients_ingredient_id_fkey FOREIGN KEY (ingredient_id) REFERENCES public.ingredients(id) ON DELETE CASCADE;


--
-- Name: food food_personne_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.food
    ADD CONSTRAINT food_personne_id_fkey FOREIGN KEY (personne_id) REFERENCES public.personne(id);


--
-- Name: foods foods_personne_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.foods
    ADD CONSTRAINT foods_personne_id_fkey FOREIGN KEY (personne_id) REFERENCES public.personnes(id);


--
-- Name: image image_food_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT image_food_id_fkey FOREIGN KEY (food_id) REFERENCES public.food(id);


--
-- Name: image image_ingredient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT image_ingredient_id_fkey FOREIGN KEY (ingredient_id) REFERENCES public.ingredient(id);


--
-- Name: images images_food_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.images
    ADD CONSTRAINT images_food_id_fkey FOREIGN KEY (food_id) REFERENCES public.foods(id);


--
-- PostgreSQL database dump complete
--

