BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE forums (
    forum_id SERIAL,
    topic varchar(200) NOT NULL,
    author varchar(50) NOT NULL,
    CONSTRAINT PK_forum PRIMARY KEY (forum_id),
    CONSTRAINT FK_forum_author FOREIGN KEY (author) REFERENCES users(username)
);

COMMIT TRANSACTION;
