CREATE TABLE IF NOT EXISTS movies(
id varchar(10) NOT NULL,
title varchar(100) NOT NULL,
year int NOT NULL,
director varchar(100) NOT NULL,
primary key(id));

CREATE TABLE IF NOT EXISTS stars(
id varchar(10) NOT NULL,
name varchar(100) NOT NULL,
birthYear int,
primary key(id));

CREATE TABLE IF NOT EXISTS stars_in_movies(
starId varchar(10) NOT NULL,
movieId varchar(10) NOT NULL,
foreign key(starId) references stars(id),
foreign key(movieId) references movies(id));

CREATE TABLE IF NOT EXISTS genres(
id int NOT NULL auto_increment,
name varchar(32) NOT NULL,
primary key(id));

CREATE TABLE IF NOT EXISTS genres_in_movies(
genreId int NOT NULL,
movieId varchar(10) NOT NULL,
foreign key(genreId) references genres(id),
foreign key(movieId) references movies(id));

CREATE TABLE IF NOT EXISTS creditcards(
id varchar(20) NOT NULL,
firstName varchar(50) NOT NULL,
lastName varchar(50) NOT NULL,
expiration date NOT NULL,
primary key(id));

CREATE TABLE IF NOT EXISTS customers(
id int NOT NULL auto_increment,
firstName varchar(50) NOT NULL,
lastName varchar(50) NOT NULL,
ccId varchar(20) NOT NULL,
address varchar(200) NOT NULL,
email varchar(50) NOT NULL,
password varchar(20) NOT NULL,
primary key(id),
foreign key(ccId) references creditcards(id));

CREATE TABLE IF NOT EXISTS sales(
id int NOT NULL auto_increment,
customerId int NOT NULL,
movieId varchar(10) NOT NULL,
saleDate date NOT NULL,
primary key(id),
foreign key(customerId) references customers(id),
foreign key(movieId) references movies(id));

CREATE TABLE IF NOT EXISTS ratings(
movieId varchar(10) NOT NULL,
rating float NOT NULL,
numVotes int NOT NULL,
foreign key(movieId) references movies(id));
