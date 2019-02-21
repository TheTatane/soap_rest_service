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
  title VARCHAR(50),
  title_duration VARCHAR(6),
  id_album INTEGER,

  FOREIGN KEY (id_album)
    REFERENCES Album(id_album)
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

INSERT INTO Title (title, id_album, title_duration) VALUES ("Seum",1,"1:30");
INSERT INTO Title (title, id_album, title_duration) VALUES ("Gris",1,"1:30");
INSERT INTO Title (title, id_album, title_duration) VALUES ("Je t'aime",2,"1:30");
INSERT INTO Title (title, id_album, title_duration) VALUES ("Strip",2,"1:30");
INSERT INTO Title (title, id_album, title_duration) VALUES ("Into you",3,"1:30");
INSERT INTO Title (title, id_album, title_duration) VALUES ("Greedy",3,"1:30");
INSERT INTO Title (title, id_album, title_duration) VALUES ("Volcano",4,"1:30");
INSERT INTO Title (title, id_album, title_duration) VALUES ("Iris",4,"1:30");