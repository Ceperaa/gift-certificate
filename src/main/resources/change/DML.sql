-- quibase formatted sql


--changeset Sergey:2
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('APhone', 'desc', 10.00, 13, '2022-12-11', '2022-10-24');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('BPhone', 'desc', 10.00, 13, '2022-12-11', '2022-10-24');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('CPhone', 'desc', 10.00, 13, '2022-12-11', '2022-10-24');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('DPhone', 'desc', 10.00, 13, '2022-12-11', '2022-10-24');

--changeset Sergey:3.1
INSERT INTO public.tag (name)
VALUES ('name');
INSERT INTO public.tag (name)
VALUES ('description');
INSERT INTO public.tag (name)
VALUES ('price');
INSERT INTO public.tag (name)
VALUES ('create');
INSERT INTO public.tag (name)
VALUES ('date');
INSERT INTO public.tag (name)
VALUES ('last');
INSERT INTO public.tag (name)
VALUES ('update');
INSERT INTO public.tag (name)
VALUES ('date');

--changeset Sergey:4.1
INSERT INTO public.gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (1, 8);
INSERT INTO public.gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (2, 7);
INSERT INTO public.gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (3, 6);
INSERT INTO public.gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (4, 5);
INSERT INTO public.gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (1, 4);
INSERT INTO public.gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (2, 3);
INSERT INTO public.gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (3, 2);
INSERT INTO public.gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (4, 1);