INSERT INTO CATEGORY(id, name) VALUES (1, 'Eurogames');
INSERT INTO CATEGORY(id, name) VALUES (2, 'Ameritrash');
INSERT INTO CATEGORY(id, name) VALUES (3, 'Familiar');

INSERT INTO AUTHOR(id, name, nationality) VALUES (1, 'Alan R. Moon', 'US');
INSERT INTO AUTHOR(id, name, nationality) VALUES (2, 'Vital Lacerda', 'PT');
INSERT INTO AUTHOR(id, name, nationality) VALUES (3, 'Simone Luciani', 'IT');
INSERT INTO AUTHOR(id, name, nationality) VALUES (4, 'Perepau Llistosella', 'ES');
INSERT INTO AUTHOR(id, name, nationality) VALUES (5, 'Michael Kiesling', 'DE');
INSERT INTO AUTHOR(id, name, nationality) VALUES (6, 'Phil Walker-Harding', 'US');

INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (1, 'On Mars', '14', 1, 2);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (2, 'Aventureros al tren', '8', 3, 1);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (3, '1920: Wall Street', '12', 1, 4);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (4, 'Barrage', '14', 1, 3);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (5, 'Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (6, 'Azul', '8', 3, 5);

INSERT INTO CLIENTE(id, name) VALUES (1, 'Cliente 1');
INSERT INTO CLIENTE(id, name) VALUES (2, 'Cliente 2');
INSERT INTO CLIENTE(id, name) VALUES (3, 'Cliente 3');
INSERT INTO CLIENTE(id, name) VALUES (4, 'Cliente 4');
INSERT INTO CLIENTE(id, name) VALUES (5, 'Cliente 5');
INSERT INTO CLIENTE(id, name) VALUES (6, 'Cliente 6');
INSERT INTO CLIENTE(id, name) VALUES (7, 'Cliente 7');
INSERT INTO CLIENTE(id, name) VALUES (8, 'Cliente 8');
INSERT INTO CLIENTE(id, name) VALUES (9, 'Cliente 9');

INSERT INTO PRESTAMO(id, game_id, cliente_id, prest_date, devol_date) VALUES (1, 1, 1, '2022-01-11', '2022-03-12');
INSERT INTO PRESTAMO(id, game_id, cliente_id, prest_date, devol_date) VALUES (2, 2, 1, '2022-02-11', '2022-04-13');
INSERT INTO PRESTAMO(id, game_id, cliente_id, prest_date, devol_date) VALUES (3, 3, 2, '2022-03-12', '2022-05-14');
INSERT INTO PRESTAMO(id, game_id, cliente_id, prest_date, devol_date) VALUES (4, 4, 2, '2022-04-13', '2022-06-15');
INSERT INTO PRESTAMO(id, game_id, cliente_id, prest_date, devol_date) VALUES (5, 5, 3, '2022-05-14', '2022-10-16');
INSERT INTO PRESTAMO(id, game_id, cliente_id, prest_date, devol_date) VALUES (6, 6, 3, '2022-06-15', '2022-11-17');
INSERT INTO PRESTAMO(id, game_id, cliente_id, prest_date, devol_date) VALUES (7, 6, 2, '2022-07-16', '2022-12-18');