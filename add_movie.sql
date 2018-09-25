DELIMITER //
USE `moviedb`//
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_movie`(
mtitle varchar(100),
myear int,
mdirector varchar(100),
mstar varchar(100),
mgenre varchar(32)
)
BEGIN
DECLARE mid varchar(10) DEFAULT null;
DECLARE sid varchar(10) DEFAULT null;
DECLARE test varchar(10) DEFAULT null;
DECLARE rand INT DEFAULT null;
DECLARE gid INT DEFAULT null;
if not exists(
select m.id
from movies as m 
where m.title = mtitle and m.year = myear and m.director = mdirector)
THEN
	while mid is null do
		SET rand = LPAD(FLOOR(RAND() * 999999.99), 8, '0');
        SET test = CONCAT("tt", rand);
        if not exists(select m.id from movies as m where m.id = test)
		THEN
			SET mid = CONCAT("tt", rand);
		END IF;
	end while;    
	INSERT INTO movies(id, title, year, director) VALUES(mid, mtitle, myear, mdirector);
	select 'Movie didn\'t exist, so new entry in movie table was made.';

elseif exists(
select m.id
from movies as m 
where m.title = mtitle and m.year = myear and m.director = mdirector)
THEN
	SET mid = (select m.id from movies as m where m.title = mtitle and m.year = myear and m.director = mdirector);
	select 'Movie exists.';
END IF;

if exists (
select s.id
from stars as s
where s.name = mstar)
THEN 
	SET sid = (select s.id from stars as s where s.name = mstar);
	INSERT INTO stars_in_movies VALUES(sid ,mid);
    select 'Star existed in database. Stars in movies has been updated.';
elseif not exists(
select s.id
from stars as s
where s.name = mstar)
THEN
	while sid is null do
		SET rand = LPAD(FLOOR(RAND() * 999999.99), 8, '0');
        SET test = CONCAT("nm", rand);
        if not exists(select s.id from stars as s where s.id = test)
		THEN
			SET sid = CONCAT("nm", rand);
		END IF;
	end while;
	INSERT INTO stars VALUES(sid, mstar, null);
    INSERT INTO stars_in_movies VALUES(sid , mid);
    select 'Star did not exist in database. Stars and stars in movies have both been updated';
END IF;

if exists(
select g.id
from genres as g
where g.name = mgenre)
THEN
	SET gid = (select g.id from genres as g where g.name = mgenre);
    INSERT INTO genres_in_movies VALUES(gid, mid);
    select 'Genre exists in database. Genres in movies has been updated.';
    
elseif not exists(
select g.id
from genres as g
where g.name = mgenre)
THEN
	SET gid = (select max(id) + 1 from genres);
    INSERT INTO genres VALUES(gid, mgenre);
    INSERT INTO genres_in_movies VALUES(gid, mid);
    select 'Genre did not exist in database. Genres and genres in movies have both been updated.';
END IF;
END//

DELIMITER ;

