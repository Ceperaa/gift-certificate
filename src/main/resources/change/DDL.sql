-- quibase formatted sql

--changeset Sergey:1.3
CREATE TABLE IF NOT EXISTS public.gift_certificate
(
    id               BIGSERIAL PRIMARY KEY,
    name             varchar(50) NOT NULL,
    description      varchar(50) NOT NULL,
    price            decimal NOT NULL,
    duration         smallint    NOT NULL,
    create_date      date        NOT NULL,
    last_update_date date        NOT NULL
);

CREATE TABLE IF NOT EXISTS public.tag
(
    id   BIGSERIAL PRIMARY KEY,
    name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.gift_certificate_tag
(
    gift_certificate_id bigint,
    tag_id              bigint,
    CONSTRAINT gift_certificate FOREIGN KEY (gift_certificate_id)
        REFERENCES public.gift_certificate (id)
        ON UPDATE cascade
        ON DELETE cascade,
    CONSTRAINT tag FOREIGN KEY (tag_id)
        REFERENCES public.tag (id)
        ON UPDATE NO ACTION
        ON DELETE cascade
);

create table IF NOT EXISTS public.users
(
    id      bigserial   not null
        constraint user_pk
            primary key,
    name    varchar(30) not null,
    surname varchar(30) not null
);

create table IF NOT EXISTS public.orders
(
    id                  bigserial   not null
        constraint order_pk
            primary key,
    "create_date"       timestamp   not null,
    price               decimal not null,
    gift_certificate_id bigint      not null,
    user_id             bigint      not null,
    CONSTRAINT order_gift_certificate_id_fk FOREIGN KEY (gift_certificate_id)
        REFERENCES public.gift_certificate (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT order_user_id_fk FOREIGN KEY (user_id)
        REFERENCES public.users (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);