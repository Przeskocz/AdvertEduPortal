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