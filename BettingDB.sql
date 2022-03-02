-- --create Bettingdb
-- CREATE DATABASE "BettingDB"
--     WITH 
--     OWNER = postgres
--     ENCODING = 'UTF8'
--     LC_COLLATE = 'English_United States.1252'
--     LC_CTYPE = 'English_United States.1252'
--     TABLESPACE = pg_default
--     CONNECTION LIMIT = -1;

DROP TABlE IF EXISTs public.daily_winner;
DROP TABlE IF EXISTs public.password;
DROP TABlE IF EXISTs public.users CASCADE;
DROP TABlE IF EXISTs public.user_daily_bets;


--create daily_winner table
CREATE TABLE IF NOT EXISTS public.daily_winner
(
    week_id integer NOT NULL,
    winning_bet integer NULL,
    date date NOT NULL,
    word text NOT NULL
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

INSERT INTO password (pass)
VALUES ('xxxxxxxxx');
	
-- create users table
CREATE TABLE IF NOT EXISTS public.users
(
    user_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 1000000 CACHE 1 ),
    full_name text COLLATE pg_catalog."default" NOT NULL,
    profile_picture text COLLATE pg_catalog."default",
    times_won integer NOT NULL DEFAULT 0,
    CONSTRAINT "Users_pkey" PRIMARY KEY (user_id)
);

--create user_daily_bets
CREATE TABLE IF NOT EXISTS public.user_daily_bets
(
    user_id integer NOT NULL,
    bet integer DEFAULT 0,
    date date NOT NULL,
    CONSTRAINT fk_user_daily_bets_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
)

TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;

GRANT ALL ON TABLE public.users TO postgres;