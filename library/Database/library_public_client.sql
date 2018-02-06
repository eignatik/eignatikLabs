CREATE TABLE public.client
(
    id integer DEFAULT nextval('client_id_seq'::regclass) PRIMARY KEY NOT NULL,
    first_name varchar(50),
    last_name varchar(50),
    passport varchar(20)
);
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (100, 'Mary', 'Nut', '237541264');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (112, 'Zavier', 'Baker', '124124143');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (114, 'Kenna', 'Ron', '239865874');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (113, 'Cameron', 'Rodrigez', '128746124');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (105, 'Josef', 'Clost', '182761214');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (110, 'Kristy', 'Millis', '129461294');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (111, 'Abraham', 'Kraviz', '937418924');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (103, 'Anna', 'Sanders', '128741278');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (106, 'Andrew', 'Shteider', '347816244');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (101, 'Bob', 'Weasel', '981264912');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (108, 'Quin', 'Van', '092749162');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (116, 'Lola', 'Reider', '866562379');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (109, 'Mary', 'Van', '198264125');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (107, 'Lesli', 'Boddy', '128746124');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (102, 'William', 'Liam', '273698127');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (104, 'Sam', 'Rigs', '146192749');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (115, 'Debra', 'Smith', '652398200');
INSERT INTO public.client (id, first_name, last_name, passport) VALUES (99, 'Biggy', 'Biggy', '12345678');