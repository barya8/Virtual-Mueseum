CREATE DATABASE museum;

CREATE TABLE photos (
    photoId INT PRIMARY KEY AUTO_INCREMENT,
	exhibitionId INT,	
    fileName VARCHAR(255) NOT NULL,
    base64Data MEDIUMTEXT NOT NULL,
    description TEXT,
    uploadDate DATE,
    location VARCHAR(255),
    artist VARCHAR(255)
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    locked BOOLEAN NOT NULL
);


-- Insertion example with fake data
insert into photos (exhibitionId,fileName,base64Data,description,uploadDate,location,artist)
values (1,"test",1234,"nice photo!",'2019-01-01',"Tel Aviv", "Yakir");

insert into users (username,password,locked)
values ("barya","1234", 1);
