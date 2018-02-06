CREATE TABLE public.book
(
    id integer DEFAULT nextval('book_id_seq'::regclass) PRIMARY KEY NOT NULL,
    name varchar(50),
    amount integer,
    book_type_id integer,
    CONSTRAINT book_book_type_id_fkey FOREIGN KEY (book_type_id) REFERENCES book_type (id) ON DELETE SET NULL
);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (68, 'The Adventures of Huckleberry Finn', 10, 2);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (60, 'Harry Potter and the Philosopher''s Stone', 56, 1);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (69, 'The Scarlet Letter', 2, 2);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (61, 'Harry Potter and the Chamber of Secrets', 23, 1);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (63, 'Harry Potter and the Goblet of Fire ', 23, 1);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (62, 'Harry Potter and the Prisoner of Azkaban', 123, 1);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (67, 'It', 4, 1);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (64, 'Harry Potter and the Order of the Phoenix', 12, 1);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (66, 'Harry Potter and the Deathly Hallows', 16, 1);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (82, 'Independence Day', 20, 2);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (86, 'The Catcher in the Rye', 3, 2);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (85, 'The Colossus of Maroussi', 3, 3);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (81, 'The Grapes of Wrath', 1, 3);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (87, 'Some New Book', 123, 1);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (91, 'Glossary', 12, 2);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (92, 'Booky', 1, 3);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (65, 'Harry Potter and the Half-Blood Prince', 45, 1);
INSERT INTO public.book (id, name, amount, book_type_id) VALUES (70, 'The Call of the Wild', 10, 2);