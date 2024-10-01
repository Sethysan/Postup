BEGIN TRANSACTION;

DROP TABLE IF EXISTS comment_replies;
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
    topic varchar(200) NOT NULL,
    description varchar(1000),
    author varchar(25) NOT NULL,
    time_of_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_forum PRIMARY KEY (forum_id),
    CONSTRAINT FK_forum_author FOREIGN KEY (author) REFERENCES users(username)
);

-- added a cascade effect, this should make it where all posts are deleted automatically when we delete a forum
CREATE TABLE posts (
    post_id SERIAL,
    title varchar(300),
    description varchar(20000),
    image varchar(255),
    likes int,
    dislikes int,
    author varchar(25) NOT NULL,
    forum_id int,
    time_of_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_post PRIMARY KEY (post_id),
    CONSTRAINT FK_posts_author FOREIGN KEY (author) REFERENCES users(username),
    CONSTRAINT FK_post_forum FOREIGN KEY (forum_id) REFERENCES forums(forum_id) ON DELETE CASCADE
);

-- changed replies to lowercase for constistency
-- I did not add cascade here as the replies might need to work a little different to immitate reddit properly
CREATE TABLE replies (
    reply_id SERIAL,
    description varchar(200),
    time_of_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    post_id int,
    user_id int,
    likes int,
    dislikes int,
    CONSTRAINT PK_reply PRIMARY KEY (reply_id),
    CONSTRAINT FK_replies_post FOREIGN KEY (post_id) REFERENCES posts(post_id),
    CONSTRAINT FK_replies_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- replies to replies for threaded replies
CREATE TABLE comment_replies (
    parent_id int,
    reply_id int UNIQUE,
    CONSTRAINT PK_primary_key PRIMARY KEY (reply_id, parent_id),
    CONSTRAINT FK_replies_child FOREIGN KEY (reply_id) REFERENCES replies(reply_id),
    CONSTRAINT FK_replies_parent FOREIGN KEY (parent_id) REFERENCES replies(reply_id)
);

COMMIT TRANSACTION;