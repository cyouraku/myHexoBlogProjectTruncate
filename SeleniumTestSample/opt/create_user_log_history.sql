--PostgreSql:
-- DROP TABLE public.user_log_history;
CREATE TABLE public.user_log_history
(
    log_id bigint NOT NULL DEFAULT nextval('user_log_history_log_id_seq'::regclass),
    user_id character varying(128) COLLATE pg_catalog."default",
    user_agent character varying(510) COLLATE pg_catalog."default",
    create_date timestamp without time zone NOT NULL,
    CONSTRAINT user_log_history_pkey PRIMARY KEY (log_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;
ALTER TABLE public.user_log_history
    OWNER to d2pdbusr;

--ユーザ毎のWarning件数
select DISTINCT user_id, count(log_id) as times,min(create_date),max(create_date) from user_log_history group by user_id order by user_id asc;

--ユーザ毎のUA件数
select distinct user_id, count(distinct user_agent), min(create_date),max(create_date) from user_log_history group by user_id offset 200;
--And offset 501 (1-500,5001-602)
