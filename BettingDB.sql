--create Bettingdb
CREATE DATABASE "BettingDB"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	


CREATE SCHEMA public
    AUTHORIZATION postgres;

COMMENT ON SCHEMA public
    IS 'standard public schema';

GRANT ALL ON SCHEMA public TO PUBLIC;

GRANT ALL ON SCHEMA public TO postgres;

--create daily_winner table
CREATE TABLE IF NOT EXISTS public.daily_winner
(
    week_id integer NOT NULL,
    winning_bet integer NOT NULL,
    date date NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE public.daily_winner
    OWNER to postgres;

--create password table
CREATE TABLE IF NOT EXISTS public.password
(
    pass text COLLATE pg_catalog."default" NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE public.password
    OWNER to postgres;
	
-- create users table
CREATE TABLE IF NOT EXISTS public.users
(
    user_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 1000000 CACHE 1 ),
    full_name text COLLATE pg_catalog."default" NOT NULL,
    daily_bet integer DEFAULT 0,
    weekly_bet integer DEFAULT 0,
    cohort_bet integer DEFAULT 0,
    profile_picture text COLLATE pg_catalog."default",
    CONSTRAINT "Users_pkey" PRIMARY KEY (user_id)
)

TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;

GRANT ALL ON TABLE public.users TO postgres;