CREATE TABLE public.book_type
(
    id integer DEFAULT nextval('book_type_id_seq'::regclass) PRIMARY KEY NOT NULL,
    name varchar(50),
    fine integer,
    days_no_fine integer
);
INSERT INTO public.book_type (id, name, fine, days_no_fine) VALUES (1, 'USUAL', 10, 60);
INSERT INTO public.book_type (id, name, fine, days_no_fine) VALUES (2, 'RARE', 30, 21);
INSERT INTO public.book_type (id, name, fine, days_no_fine) VALUES (3, 'UNIQUE', 50, 7);