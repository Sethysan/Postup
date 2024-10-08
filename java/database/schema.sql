BEGIN TRANSACTION;

DROP TABLE IF EXISTS comment_replies, reply_upvote, reply_downvote, replies, post_upvote, post_downvote, posts, moderation, forums, direct_message, users;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(25) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	user_image varchar(500),
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE direct_message (
    message_id serial,
    sent_to int,
    sent_from int,
    message varchar(500) NOT NULL,
    time_sent TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    has_read BOOL DEFAULT FALSE,
    CONSTRAINT PK_dm PRIMARY KEY (message_id),
    CONSTRAINT FK_sent_to_id FOREIGN KEY (sent_to) REFERENCES users(user_id),
    CONSTRAINT FK_received_from FOREIGN KEY (sent_from) REFERENCES users(user_id)
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

CREATE TABLE favorite_forums(
    user_id int,
    forum_id int,
    CONSTRAINT PK_favorite_forms PRIMARY KEY (forum_id, user_id),
    CONSTRAINT FK_forum_id FOREIGN KEY (forum_id) REFERENCES forums(forum_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- added a cascade effect, this should make it where all posts are deleted automatically when we delete a forum
CREATE TABLE posts (
    post_id SERIAL,
    title varchar(300),
    description varchar(20000),
    image varchar(500),
    author varchar(25) NOT NULL,
    forum_id int,
    time_of_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_post PRIMARY KEY (post_id),
    CONSTRAINT FK_posts_author FOREIGN KEY (author) REFERENCES users(username),
    CONSTRAINT FK_post_forum FOREIGN KEY (forum_id) REFERENCES forums(forum_id) ON DELETE CASCADE
);

CREATE TABLE post_upvote (
    post_id int,
    user_id int,
    time_of_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_post_upvote PRIMARY KEY (post_id, user_id),
    CONSTRAINT FK_post_id FOREIGN KEY (post_id) REFERENCES posts(post_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE post_downvote (
    post_id int,
    user_id int,
    time_of_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_post_downvote PRIMARY KEY (post_id, user_id),
    CONSTRAINT FK_post_id FOREIGN KEY (post_id) REFERENCES posts(post_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- changed replies to lowercase for constistency
-- I did not add cascade here as the replies might need to work a little different to immitate reddit properly
CREATE TABLE replies (
    reply_id SERIAL,
    description varchar(200),
    time_of_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    post_id int,
    user_id int,
    CONSTRAINT PK_reply PRIMARY KEY (reply_id),
    CONSTRAINT FK_replies_post FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE,
    CONSTRAINT FK_replies_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE reply_upvote (
    reply_id int,
    user_id int,
    CONSTRAINT PK_reply_upvote PRIMARY KEY (reply_id, user_id),
    CONSTRAINT FK_reply_id FOREIGN KEY (reply_id) REFERENCES replies(reply_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE reply_downvote (
    reply_id int,
    user_id int,
    CONSTRAINT PK_reply_downvote PRIMARY KEY (reply_id, user_id),
    CONSTRAINT FK_reply_id FOREIGN KEY (reply_id) REFERENCES replies(reply_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- replies to replies for threaded replies
CREATE TABLE comment_replies (
    parent_id INT,
    reply_id INT UNIQUE,
    time_of_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_primary_key PRIMARY KEY (reply_id, parent_id),
    CONSTRAINT FK_replies_child FOREIGN KEY (reply_id) REFERENCES replies(reply_id) ON DELETE CASCADE,
    CONSTRAINT FK_replies_parent FOREIGN KEY (parent_id) REFERENCES replies(reply_id)
);


CREATE TABLE moderation (
    forum_id int,
    username varchar(25),
    time_of_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role varchar(25) DEFAULT 'ROLE_MODERATOR',
    CONSTRAINT FK_moderation_forum_id FOREIGN KEY (forum_id) REFERENCES forums(forum_id),
    CONSTRAINT FK_moderation_username FOREIGN KEY (username) REFERENCES users(username)
);

COMMIT TRANSACTION;