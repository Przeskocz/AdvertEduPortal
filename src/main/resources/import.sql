INSERT INTO category values (1, 'Wszystko');
INSERT INTO category values (2, 'Sprzedam');
INSERT INTO category values (3, 'Kupię');
INSERT INTO category values (4, 'Oddam');
INSERT INTO category values (5, 'Wykonam');
INSERT INTO category values (6, 'Zlecę');
INSERT INTO category values (7, 'Korepetycje');
INSERT INTO category values (8, 'Miasto');
INSERT INTO category values (9, 'Uczelnia');

INSERT INTO user VALUES (1, 1, 'skoczp@gmail.com', 'Pawe\u0142 Skocz', '$2a$14$JNAyK4cNz6gYZu7p5fQiMezOLy/.SlxSK3.m6FaZqnQrrv6D7S..e');
INSERT INTO user VALUES (2, 1, 'zwykly@user.pl', 'Zwyk\u0142y Użytkownik', '$2a$14$MzxSJNyoH5KHSZCtQ6PwMe2eV3iCRXHDg5Lke5Ai2kSYH4mtak7Ui');


INSERT INTO role values (1, 'USER');
INSERT INTO role values (2, 'ADMIN');

INSERT INTO role_user values (1, 1);
INSERT INTO role_user values (1, 2);
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


INSERT INTO advertisement VALUES (1, 'Udzielę korepetycji z Javy na czas wakacji. Cena: 50 zł za 1 godz. Zapraszam! :)', '2020-08-30 00:00:00', 50.00, '2020-06-18 22:25:36.37', 'Korepetycje Java', 7, 1, 1, 1);
INSERT INTO advertisement VALUES (2, 'Laptop bardzo dobrze działa! Polecam', '2020-06-30 00:00:00', 450.00, '2020-06-19 19:00:52', 'Sprzedam laptopa', 2, 3, 7, 2);
INSERT INTO advertisement VALUES (3, 'Oddam stare książki lub zamienię na jakieś inne pozycje. Dostępne wszystkie co na zdjęciach!', '2020-08-25 00:00:00', 1.00, '2020-06-19 19:03:39', 'Oddam stare książki', 4, 2, 6, 2);

INSERT INTO image VALUES (1, 'Sprzedam laptopa', '/img/files/upload_cf80d584-ca4c-40c0-b94c-0fcec6bc10a5.jpg', NULL, '2020-06-19 19:00:52', 2);
INSERT INTO image VALUES (2, 'Oddam stare książki', '/img/files/upload_ca8cb078-3752-434b-8be4-aaa124cef403.jpg', NULL, '2020-06-19 19:03:39', 3);
INSERT INTO image VALUES (3, 'Oddam stare książki', '/img/files/upload_4614738b-a262-4267-bb78-d355b1476699.jpg', NULL, '2020-06-19 19:03:39', 3);
