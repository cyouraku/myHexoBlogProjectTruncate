--MySql

  CREATE TABLE `videos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `link` varchar(255) NOT NULL,
  `jpg` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert  into `videos`(`name`,`link`,`jpg`) values ('gear1','Gear1/prog_index.m3u8','images/Gear1.jpg');
insert  into `videos`(`name`,`link`,`jpg`) values ('gear2','Gear2/prog_index.m3u8','images/Gear2.jpg');
insert  into `videos`(`name`,`link`,`jpg`) values ('gear1','Gear1/prog_index.m3u8','images/Gear1.jpg');
insert  into `videos`(`name`,`link`,`jpg`) values ('gear2','Gear2/prog_index.m3u8','images/Gear2.jpg');


--postgreSql

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