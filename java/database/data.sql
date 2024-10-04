BEGIN TRANSACTION;

INSERT INTO users (username, password_hash, role) VALUES ('deleted', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_BANNED');
INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

-- Insert mock forums (subreddits)
INSERT INTO forums (topic, description, author, time_of_creation) VALUES
('Tech Discussions', 'A forum to discuss all things tech.', 'user1', '2024-01-01 10:00:00'),
('Gaming', 'All things related to gaming news and discussion.', 'user2', '2024-01-02 11:00:00'),
('Programming Help', 'Ask for and provide programming help here.', 'moderator1', '2024-01-03 12:00:00'),
('Science & Space', 'Discussion about science and space exploration.', 'admin1', '2024-01-04 13:00:00');

-- Insert mock posts with images
INSERT INTO posts (title, description, image, likes, dislikes, author, forum_id, time_of_creation) VALUES
('What is your favorite programming language?', 'I personally enjoy Python for its simplicity.', NULL, 100, 5, 'user1', 1, '2024-01-05 14:00:00'),
('Top 10 games of 2023', 'Here’s a list of my favorite games from 2023.', 'https://newzoo.com/wp-content/uploads/2023/11/Top-10-bestselling-PC-and-console-game-releases-in-2023-from-January-through-October.png', 250, 10, 'user2', 2, '2024-01-06 15:00:00'),
('How to debug Java code?', 'I’m stuck on a bug and need help with debugging Java code.', NULL, 50, 2, 'moderator1', 3, '2024-01-07 16:00:00'),
('Latest SpaceX launch discussion', 'Let’s talk about the recent SpaceX mission and its impact.', 'https://scontent.fpit1-1.fna.fbcdn.net/v/t39.30808-6/449230165_796195005827318_7823260124310910238_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=127cfc&_nc_ohc=H_wGzDEFsyMQ7kNvgFbyV_H&_nc_ht=scontent.fpit1-1.fna&_nc_gid=AA05dKuyDsRrJhyNQL5lC_x&oh=00_AYCCoVO0dIL6ytp1hyYo0Hdy_2ObJwXnIMFyWUI4joCuJg&oe=6705DFC8', 500, 20, 'admin1', 4, '2024-01-08 17:00:00');

-- Insert mock replies
INSERT INTO replies (description, post_id, user_id, likes, dislikes, time_of_creation) VALUES
('I love Python too! It’s my go-to language.', 1, 2, 25, 1, '2024-01-05 15:00:00'),
('I prefer JavaScript for web development.', 1, 3, 10, 2, '2024-01-05 15:30:00'),
('Great list! I agree with most of the games here.', 2, 1, 50, 0, '2024-01-06 16:00:00'),
('Try using the debugger tool in your IDE.', 3, 4, 15, 1, '2024-01-07 17:00:00');

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
