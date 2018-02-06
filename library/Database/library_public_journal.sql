CREATE TABLE public.journal
(
    id integer DEFAULT nextval('journal_id_seq'::regclass) PRIMARY KEY NOT NULL,
    book_id integer,
    client_id integer,
    date_start date,
    date_end date,
    date_return date,
    CONSTRAINT journal_book_id_fkey FOREIGN KEY (book_id) REFERENCES book (id),
    CONSTRAINT journal_client_id_fkey FOREIGN KEY (client_id) REFERENCES client (id)
);
INSERT INTO public.journal (id, book_id, client_id, date_start, date_end, date_return) VALUES (45, 81, 111, '2017-09-22', '2017-11-21', null);
INSERT INTO public.journal (id, book_id, client_id, date_start, date_end, date_return) VALUES (46, 82, 112, '2017-09-22', '2017-11-21', null);