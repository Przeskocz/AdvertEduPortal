INSERT INTO category values (1, 'Wszystko');
INSERT INTO category values (2, 'Sprzedam');
INSERT INTO category values (3, 'Kupię');
INSERT INTO category values (4, 'Oddam');
INSERT INTO category values (5, 'Wykonam');
INSERT INTO category values (6, 'Zlecę');
INSERT INTO category values (7, 'Korepetycje');
INSERT INTO category values (8, 'Miasto');
INSERT INTO category values (9, 'Uczelnia');

INSERT INTO `user` VALUES (1, 1, 'skoczp@gmail.com', 'Paweł Skocz', '$2a$14$JNAyK4cNz6gYZu7p5fQiMezOLy/.SlxSK3.m6FaZqnQrrv6D7S..e');

INSERT INTO role values (1, 'USER');
INSERT INTO role values (2, 'ADMIN');

INSERT INTO role_user values (1, 1);
INSERT INTO role_user values (2, 1);

INSERT INTO city VALUES (1, 'Rzeszów');
INSERT INTO city VALUES(2, 'Kraków');
INSERT INTO city VALUES (3, 'Wrocław');
INSERT INTO city VALUES (4, 'Warszawa');

INSERT INTO university VALUES (1, 'Politechnika Rzeszowska im. Ignacego Łukasiewicza', 'Politechnika Rzeszowska', 1);
INSERT INTO university VALUES (2, 'Uniwersytet Rzeszowski', 'Uniwersytet Rzeszowski', 1);
INSERT INTO university VALUES (3, 'Wyższa Szkoła Prawa i Administracji Rzeszów', 'WSPiA Rzeszowska Szkoła Wyższa', 1);
INSERT INTO university VALUES (4, 'Wyższa Szkoła Informatyki i Zarządzania w Rzeszowie', 'WSIiZ w Rzeszowie', 1);
INSERT INTO university VALUES (5, 'Politechnika Krakowska im. Tadeusza Kościuszki', 'Politechnika Krakowska', 2);
INSERT INTO university VALUES (6, 'Uniwersytet Jagielloński', 'Uniwersytet Jagielloński', 2);
INSERT INTO university VALUES (7, 'Politechnika Wrocławska', 'Politechnika Wrocławska', 3);
INSERT INTO university VALUES (8, 'Politechnika Warszawska', 'Politechnika Warszawska', 4);