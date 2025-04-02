SELECT * FROM public.users
ORDER BY id ASC 

insert into users values(1,'Admin','admin123@questk2.com','Admin','password',9999988888,'admin');

SELECT pg_get_serial_sequence('users', 'id');

ALTER SEQUENCE users_id_seq RESTART WITH 2;

SELECT setval('users_id_seq', (SELECT MAX(id) FROM users) + 1);





