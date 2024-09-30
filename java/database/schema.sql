BEGIN TRANSACTION;

DROP TABLE IF EXISTS replies;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS forums;
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
    topic varchar(200) NOT NULL UNIQUE,
    author varchar(50) NOT NULL,
    time_of_creation DATE DEFAULT CURRENT_TIMESTAMP,
    time_of_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_forum PRIMARY KEY (forum_id),
    CONSTRAINT FK_forum_author FOREIGN KEY (author) REFERENCES users(username)
);

CREATE TABLE posts (
    post_id SERIAL,
    description varchar(1000) NOT NULL,
    image varchar(255),
    likes int,
    dislikes int,
    author varchar,
    forum_id int,
    CONSTRAINT PK_post PRIMARY KEY (post_id),
    CONSTRAINT FK_posts_author FOREIGN KEY (author) REFERENCES users(username),
    CONSTRAINT FK_post_forum FOREIGN KEY (forum_id) REFERENCES forums(forum_id)
);

CREATE TABLE Replies (
    reply_id SERIAL,
    description varchar(200),
    post_id int,
    CONSTRAINT PK_reply PRIMARY KEY (reply_id),
    CONSTRAINT FK_replies_post FOREIGN KEY (post_id) REFERENCES posts(post_id)
);

COMMIT TRANSACTION;