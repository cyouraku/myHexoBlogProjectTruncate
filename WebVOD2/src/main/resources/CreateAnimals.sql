

--MySql


--postgreSql

  CREATE TABLE public.animals (
  id bigint NOT NULL,
  name varchar(255) NOT NULL,
  effect varchar(255) NOT NULL,
  speak varchar(255) NOT NULL,
  jpg varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  CONSTRAINT animals_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)

TABLESPACE pg_default;

ALTER TABLE public.animals
    OWNER to postgres;

insert  into animals (id,name,effect,speak,jpg,description) values (1,'baboon monkey','audio/animal/baboon_monkey.mp3','audio/animal_nm_en/baboon_monkey_en.wav','images/animal_pic/baboon_monkey.jpg','This is a baboon monkey.');
insert  into animals (id,name,effect,speak,jpg,description) values (2,'狒狒猴','audio/animal/baboon_monkey.mp3','audio/animal_nm_cn/baboon_monkey_cn.wav','images/animal_pic/baboon_monkey.jpg','这是一只狒狒猴。');
insert  into animals (id,name,effect,speak,jpg,description) values (3,'ヒヒ猿','audio/animal/baboon_monkey.mp3','audio/animal_nm_ja/baboon_monkey_ja.wav','images/animal_pic/baboon_monkey.jpg','これはヒヒ猿です。');



