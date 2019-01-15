-- Table: public.videos

-- DROP TABLE public.videos;

CREATE TABLE public.videos
(
    id bigint NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    link text COLLATE pg_catalog."default" NOT NULL,
    jpg text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT video_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.videos
    OWNER to postgres;