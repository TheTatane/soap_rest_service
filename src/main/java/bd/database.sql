CREATE TABLE IF NOT EXISTS Artist (
  id_artist INTEGER PRIMARY KEY AUTO_INCREMENT,
  name_artist VARCHAR (50)
);

CREATE TABLE IF NOT EXISTS Album (
  id_album INTEGER PRIMARY KEY AUTO_INCREMENT,
  title_album VARCHAR(50),
  id_artist INTEGER,
  year_release INTEGER,

  FOREIGN KEY (id_artist)
    REFERENCES Artist(id_artist)
);

CREATE TABLE IF NOT EXISTS Title (
  id_title INTEGER PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS AlbumContent (
  id_album INTEGER,
  id_title INTEGER,
  title_duration VARCHAR(6),

  FOREIGN KEY (id_album)
    REFERENCES Album(id_album),
  FOREIGN KEY (id_title)
    REFERENCES Title(id_title)
);

/* INSERT TUPLES TESTS */
INSERT INTO Artist (name_artist) VALUES ("VALD"); /* 1 */
INSERT INTO Artist (name_artist) VALUES ("Ariana Grande"); /* 2 */
INSERT INTO Artist (name_artist) VALUES ("U2"); /* 3 */
INSERT INTO Artist (name_artist) VALUES ("The Edge"); /* 4 */



INSERT INTO Album (title_album, id_artist, year_release) VALUES ("XEU", 1, 2018);
INSERT INTO Album (title_album, id_artist, year_release) VALUES ("Agartha", 1, 2017);
INSERT INTO Album (title_album, id_artist, year_release) VALUES ("Dangerous Woman", 2, 2016);
INSERT INTO Album (title_album, id_artist, year_release) VALUES ("Songs of Innoncence", 3, 2014);

INSERT INTO Title (title) VALUES ("Seum");
INSERT INTO Title (title) VALUES ("Gris");
INSERT INTO Title (title) VALUES ("Je t'aime");
INSERT INTO Title (title) VALUES ("Strip");
INSERT INTO Title (title) VALUES ("Into you");
INSERT INTO Title (title) VALUES ("Greedy");
INSERT INTO Title (title) VALUES ("Volcano");
INSERT INTO Title (title) VALUES ("Iris");

INSERT INTO AlbumContent (id_album, id_title, title_duration) VALUES (1,1,"1:30");
INSERT INTO AlbumContent (id_album, id_title, title_duration) VALUES (1,2,"1:30");
INSERT INTO AlbumContent (id_album, id_title, title_duration) VALUES (2,3,"1:30");
INSERT INTO AlbumContent (id_album, id_title, title_duration) VALUES (2,4,"1:30");
INSERT INTO AlbumContent (id_album, id_title, title_duration) VALUES (3,5,"1:30");
INSERT INTO AlbumContent (id_album, id_title, title_duration) VALUES (3,6,"1:30");
INSERT INTO AlbumContent (id_album, id_title, title_duration) VALUES (4,7,"1:30");
INSERT INTO AlbumContent (id_album, id_title, title_duration) VALUES (4,8,"1:30");