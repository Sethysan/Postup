BEGIN TRANSACTION;

-- Insert mock users
-- Passwords are hashed using bcrypt with a cost factor of 8
-- user1:      password123
-- user2:      mypassword
-- moderator1: modpassword
-- admin1:     adminpassword
-- user3:      userpassword

INSERT INTO users (username, password_hash, role) VALUES
('user1', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER'),
('user2', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER'),
('moderator1', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_MODERATOR'),
('admin1', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_ADMIN'),
('user3', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER');

-- Insert mock forums
INSERT INTO forums (topic, description, author, time_of_creation) VALUES
('Tech Discussions', 'A forum to discuss all things tech.', 'user1', '2024-01-01 10:00:00'),
('Gaming', 'All things related to gaming news and discussion.', 'user2', '2024-01-02 11:00:00'),
('Programming Help', 'Ask for and provide programming help here.', 'moderator1', '2024-01-03 12:00:00'),
('Science & Space', 'Discussion about science and space exploration.', 'admin1', '2024-01-04 13:00:00');

-- Insert mock posts
INSERT INTO posts (title, description, image, author, forum_id, time_of_creation) VALUES
('What is your favorite programming language?', 'I personally enjoy Python for its simplicity.', NULL, 'user1', 1, '2024-01-05 14:00:00'),
('Top 10 games of 2023', 'Here’s a list of my favorite games from 2023.', 'https://newzoo.com/wp-content/uploads/2023/11/Top-10-bestselling-PC-and-console-game-releases-in-2023-from-January-through-October.png', 'user2', 2, '2024-01-06 15:00:00'),
('How to debug Java code?', 'I’m stuck on a bug and need help with debugging Java code.', NULL, 'moderator1', 3, '2024-01-07 16:00:00'),
('Latest SpaceX launch discussion', 'Let’s talk about the recent SpaceX mission and its impact.', 'https://nss.org/wp-content/uploads/2018/02/falcon-heavy-liftoff.jpg', 'admin1', 4, '2024-01-08 17:00:00'),
('What is the future of AI?', 'Discuss how AI is shaping the future of technology.', NULL, 'user3', 1, '2024-01-09 18:00:00');

-- Insert mock post upvotes
INSERT INTO post_upvote (post_id, user_id) VALUES
(1, 2),  -- user2 upvoted post 1
(2, 1),  -- user1 upvoted post 2
(3, 2),  -- user2 upvoted post 3
(4, 3);  -- user3 upvoted post 4

-- Insert mock post downvotes
INSERT INTO post_downvote (post_id, user_id) VALUES
(1, 3),  -- user3 downvoted post 1
(4, 2);  -- user2 downvoted post 4

-- Insert mock replies
INSERT INTO replies (description, post_id, user_id, time_of_creation) VALUES
('I love Python too! It’s my go-to language.', 1, 2, '2024-01-05 15:00:00'),
('I prefer JavaScript for web development.', 1, 3, '2024-01-05 15:30:00'),
('Great list! I agree with most of the games here.', 2, 1, '2024-01-06 16:00:00'),
('Try using the debugger tool in your IDE.', 3, 4, '2024-01-07 17:00:00'),
('AI is going to revolutionize every industry.', 5, 2, '2024-01-09 19:00:00');

-- Insert mock reply upvotes
INSERT INTO reply_upvote (reply_id, user_id) VALUES
(1, 1),  -- user1 upvoted reply 1
(2, 2),  -- user2 upvoted reply 2
(3, 1),  -- user1 upvoted reply 3
(4, 3);  -- user3 upvoted reply 4

-- Insert mock reply downvotes
INSERT INTO reply_downvote (reply_id, user_id) VALUES
(2, 1),  -- user1 downvoted reply 2
(5, 3);  -- user3 downvoted reply 5

-- Insert mock comment replies (threaded replies)
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(1, 2),  -- Replying to the first reply in the post "What is your favorite programming language?"
(3, 4);  -- Replying to the third reply in the post "Top 10 games of 2023"

-- Insert mock moderation roles
INSERT INTO moderation (forum_id, username, role) VALUES
(1, 'moderator1', 'ROLE_MODERATOR'),
(2, 'admin1', 'ROLE_ADMIN'),
(3, 'moderator1', 'ROLE_MODERATOR'),
(4, 'admin1', 'ROLE_ADMIN');

COMMIT TRANSACTION;
