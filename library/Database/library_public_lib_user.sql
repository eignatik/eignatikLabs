CREATE TABLE public.lib_user
(
    id integer DEFAULT nextval('user_id_seq'::regclass) PRIMARY KEY NOT NULL,
    username varchar(20),
    pass text
);
INSERT INTO public.lib_user (id, username, pass) VALUES (1, 'user', '1a1dc91c907325c69271ddf0c944bc72');