SELECT * FROM public.user_role
ORDER BY id ASC 
insert into user_role values(1,'Admin',1)

SELECT pg_get_serial_sequence('user_role', 'id');

ALTER SEQUENCE public.user_role_id_seq RESTART WITH 2;

SELECT setval('user_role_id_seq', (SELECT MAX(id) FROM user_role) + 1);