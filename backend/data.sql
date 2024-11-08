BEGIN TRANSACTION;


INSERT INTO users (username, password_hash, role, user_image) VALUES
( '[deleted]', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_BANNED', '/images/avatars/removed.jpg'), -- for removed or deleted users
-- admins
('admin1', '$2a$10$KFSwhveWPagQOOdUX8LbGuuQ3Mxw2OTCsK0SGDnq88Lobm4lYEgRq', 'ROLE_ADMIN', ''),
('admin2', '$2a$10$KFSwhveWPagQOOdUX8LbGuuQ3Mxw2OTCsK0SGDnq88Lobm4lYEgRq', 'ROLE_ADMIN', ''),
('admin3', '$2a$10$KFSwhveWPagQOOdUX8LbGuuQ3Mxw2OTCsK0SGDnq88Lobm4lYEgRq', 'ROLE_ADMIN', ''),
-- people for the gardener forums
( 'forester1', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', 'https://plus.unsplash.com/premium_photo-1683121366070-5ceb7e007a97?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
( 'lily_lover', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', 'https://images.unsplash.com/photo-1586297135537-94bc9ba060aa?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
( 'plant_lady95', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', 'https://plus.unsplash.com/premium_photo-1664541336896-b3d5f7dec9a3?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
-- people for music forums
( 'emo_ito9', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', ''),
( 'rockstar79', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', ''),
( 'killer_jazz4u', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', ''),
( 'thehipthehop', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', ''),
-- for programming forums
( 'mutex', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_ADMIN', 'https://images.unsplash.com/photo-1678286742832-26543bb49959?q=80&w=1888&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
( 'java_jack', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', 'https://plus.unsplash.com/premium_photo-1661580702098-b1a081567ba3?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
( 'not_a_bot', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', ''),
( 'keanufromthematrix', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', 'https://pyxis.nymag.com/v1/imgs/9a6/5e8/5ce35b75ecec50d42b73374a56aa73b658-val-kilmer-interview-lede.2x.rsocial.w600.jpg'),
-- for space forums
( 'nasa_rocks', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', ''),
( 'spaceXisbetter', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', ''),
( '4explore', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', ''),
-- for movie forum
( 'spidernoir', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', ''),
( 'myers_sister38', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', ''),
( 'lordoftheposts8', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', ''),
-- for video games
( 'classicN64', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', ''),
( 'manpac7', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', ''),
( 'diablo99', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', ''),
( 'halo_finite', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', '');

-- Insert mock forums
INSERT INTO forums (topic, description, author, time_of_creation) VALUES
( 'Welcome To Post Up', 'A forum for all things post up. Feel free to say hi!', 'admin1', '2022-03-020 13:00:00'),
( 'Tech Discussions', 'A forum to discuss all things tech.', 'mutex', '2024-01-01 10:00:00'),
( 'Gaming', 'All things related to gaming news and discussion.', 'manpac7', '2022-04-02 11:00:00'), -- 3
( 'Programming Help', 'Ask for and provide programming help here.', 'mutex', '2024-08-03 12:00:00'),
( 'Science & Space', 'Discussion about science and space exploration.', '4explore', '2023-05-13 13:00:00'),
( 'Not So Secret Garden', 'Discussions and advice about gardening.', 'lily_lover', '2023-12-09 13:00:00'),
( 'Music Today', 'All things music. Feel free to ask for recommendations and express your music taste and opinions!', 'killer_jazz4u', '2022-09-05 13:00:00'),
( 'The Popcorn Flix Club', 'A somewhat chill place to drop your bold opinions about new releases or old classics.', 'lordoftheposts8', '2023-10-09 13:00:00');


-- Insert mock posts
INSERT INTO posts (title, description, image, author, forum_id, time_of_creation) VALUES
-- welcome forum
( 'Welcome New Users!', 'Drop in and make your reply on post up by saying hi!', 'https://imageio.forbes.com/specials-images/imageserve/6579be889c4f517b9631ef97/chart1/960x0.jpg?format=jpg&width=1440', 'admin1', 73, '2023-05-12 15:07:00.01799'),
-- gaming forum
( 'Starfield: Bethesda`s Space RPG Breaks Records', 'Bethesda`s highly anticipated space RPG, Starfield, has broken multiple records, including over 6 million players at launch. Players explore an open-world galaxy, filled with planets, space stations, and a deep narrative to uncover.', 'https://cdn.akamai.steamstatic.com/steam/apps/1716740/capsule_616x353.jpg?t=1691779121', 'halo_finite', 75, '2024-10-05 12:30:00'),
( 'Cyberpunk 2077: Phantom Liberty Expansion Review', 'Phantom Liberty, the much-anticipated expansion for Cyberpunk 2077, introduces Idris Elba`s character Solomon Reed, expanding on the world of Night City with brand-new areas and quests. Does this expansion redeem the game after its rocky launch?', 'https://cdn.akamai.steamstatic.com/steam/apps/1091500/header.jpg?t=1693568339', 'mutex', 75, '2024-10-05 13:00:00'),
( 'Top 10 games of 2023', 'Here`s a list of my favorite games from 2023.', 'https://newzoo.com/wp-content/uploads/2023/11/Top-10-bestselling-PC-and-console-game-releases-in-2023-from-January-through-October.png', 'diablo99', 75, '2024-10-05 15:00:00'),
( 'The Top 10 Best-Selling Games of 2023', 'Here`s a breakdown of the best-selling games in 2023, featuring franchises like Elden Ring, The Legend of Zelda: Tears of the Kingdom, and Call of Duty: Modern Warfare II.', 'https://imageio.forbes.com/specials-images/imageserve/6579be889c4f517b9631ef97/chart1/960x0.jpg?format=jpg&width=1440', 'mutex', 75, '2024-1-05 15:07:00.01799'),
-- programming help
( 'Understanding JavaScript Closures', 'Can someone explain closures in JavaScript? I keep getting confused by the concept of inner functions having access to outer function variables.', 'https://edward-huang.com/images/what-is-really-so-special-about-javascript-closure-/Closure%20JS.png', 'java_jack', 76, '2024-10-05 14:00:00'),
( 'Help with Java ArrayList', 'I need help with adding and removing elements from an ArrayList in Java. I keep getting `IndexOutOfBoundsException`. Can someone tell me what I am doing wrong?', 'https://upload.wikimedia.org/wikipedia/en/3/30/Java_programming_language_logo.svg', 'not_a_bot', 76, '2024-10-05 14:30:00'),
( 'Python vs Java for Web Development?', 'I am trying to decide between Python (using Django) and Java (using Spring) for web development. Which one should I choose?', 'https://preview.redd.it/rxezjyf4ojx41.png?width=1080&crop=smart&auto=webp&s=38b346ba426f740eefcde2c6dfb23c5e6e810bc9', 'keanufromthematrix', 76, '2024-10-05 15:00:00'),
( 'How to debug Java code?', 'I`m stuck on a bug and need help with debugging Java code.', 'https://i.redd.it/grzhl4arakw21.jpg', 'not_a_bot', 76, '2024-10-09 15:07:00.01799'),
-- tech forum
( 'The Rise of Quantum Computing', 'Quantum computing is revolutionizing the tech industry. Companies like Google and IBM are making major strides in developing quantum processors. What are your thoughts on its potential to solve complex problems?', 'https://res.cloudinary.com/dthpnue1d/image/upload/v1714979675/advantages_of_quantum_computing_f11be1ee8b.jpg', 'mutex', 74, '2024-10-05 16:00:00'),
( 'AI and the Future of Work', 'With advancements in artificial intelligence, we are starting to see AI systems take over tasks previously done by humans. Do you think AI will cause massive job losses or create new opportunities?', 'https://www.smart-academy.in/wp-content/uploads/2023/12/BLOG-Image-Future-Trends-in-the-Age-of-Artificial-Intelligence-1.jpg', 'mutex', 74, '2024-10-05 16:30:00'),
( '5G Technology: What Does the Future Hold?', 'As 5G networks become more widespread, we are on the verge of seeing faster, more reliable connections across the globe. What are your thoughts on the impact of 5G on industries like healthcare, IoT, and more?', 'https://imageio.forbes.com/specials-images/imageserve/5db28252c538200007815c25/What-Is-5G-Technology-And-How-Must-Businesses-Prepare-For-It-/960x0.jpg?format=jpg&width=1440', 'not_a_bot', 74, '2024-10-05 17:00:00'),
( 'What is your favorite programming language?', 'I personally enjoy Python for its simplicity.', 'https://miro.medium.com/v2/resize:fit:1100/format:webp/1*EcrlPFA5Sdno4YvxDw73FA.png', 'keanufromthematrix', 76, '2024-10-09 15:07:00.01799'),
( 'What is the future of AI?', 'Discuss how AI is shaping the future of technology.', 'https://d2ds8yldqp7gxv.cloudfront.net/Blog+Explanatory+Images/Future+of+Artificial+Intelligence+1.webp', 'mutex', 76, '2024-10-09 15:07:00.01799'),
( 'NEVER obsolete', 'I wonder how many people are still getting upgrades every 2 years for $99!', 'https://i.redd.it/p33nor0btfi91.jpg', 'mutex', 76, '2024-10-09 15:07:00.01799'),
( 'Help', 'I need help with my computer. I feel like I''m aimlessly running in cirlces like a baby chick. I need some direction', 'https://as1.ftcdn.net/v2/jpg/05/63/32/30/1000_F_563323083_iD6EXOVpPblV1w3BcRzsMx4goaFGvW7U.jpg', 'lily_lover', 76, '2024-10-09 15:07:00.01799'),
-- space forum
( 'The James Webb Space Telescope was launched in 2021 and began collecting data in 2022, reshaping our understanding of the early universe while taking wondrous pictures of the cosmos.', 'A rarely seen prelude to a Supernova shows a super-bright, massive Wolf-Rayet star in a composite image taken by the James Webb Telescope. The star, WR 124, is 15,000 light-years away in the constellation Sagittarius and is 30 times the mass of our Sun.', 'https://www.reuters.com/resizer/v2/7BANAT2Q3FIK7BOTULOWAIWGBQ.jpg?auth=17a64f7408aab4b44df4f1bbd8dc98c043b37f367feeb6e957982f61d4fa34fb&width=1920&quality=80', 'nasa_rocks', 77, '2024-10-09 15:07:00.01799'),
( 'Space odyssey: Stunning images from the James Webb telescope', 'A look at Sun-like stars being born in this detailed close-up of Rho Ophiuchi, the closest-star-forming region to Earth in a composite image taken by the James Webb Telescope and released by NASA on July 12, 2023. Dark, dense dust cocoons still-forming protostars, while an emerging stellar newborn (top center) shoots out two huge jets of molecular hydrogen. NASA', 'https://www.reuters.com/resizer/v2/O2AKCAEW4VM45D73OPETSDJNBE.jpg?auth=64c3f02b23b72ae2675010b2765ed748a4ac2defcf334a136d3003b54b339de9&width=1920&quality=80', 'nasa_rocks', 77, '2024-10-09 15:07:00.01799'),
( 'NASA`s James Webb Telescope Captures the Fiery Remains of a Dying Star in Unprecedented Detail', 'More than a year after NASA`s James Webb Space Telescope brought us images of the Southern Ring Nebula, the world`s most powerful observer captured the remains of another celestial body with stunning clarity.', 'https://www.thisiscolossal.com/wp-content/uploads/2023/08/webb-1.jpg', 'nasa_rocks', 77, '2024-10-09 15:07:00.01799'),
( 'Latest SpaceX launch discussion', 'Let`s talk about the recent SpaceX mission and its impact.', 'https://nss.org/wp-content/uploads/2018/02/falcon-heavy-liftoff.jpg', '4explore', 77, '2024-10-09 15:07:00.01799'),
-- garden forum
 ('How to Grow Tomatoes', 'Tips and tricks for growing healthy tomatoes.', '', 'lily_lover', 78, '2024-10-09 15:07:00.01799'),
 ('Best Tools for Gardening', 'A review of the best tools every gardener should have.', '', 'forester1', 78, '2024-10-09 15:07:00.01799'),
 ('Organic Gardening Methods', 'Learn about organic gardening practices.', '', 'plant_lady95', 78, '2024-10-09 15:07:00.01799'),
 -- music forum
 ('Top 10 Rock Bands of All Time', 'A discussion on the greatest rock bands in history.', '', 'rockstar79', 79, '2024-10-09 15:07:00.01799'),
 ('The Rise of Indie Music', 'Exploring the growth of indie artists and their impact.', '', 'thehipthehop', 79, '2024-10-09 15:07:00.01799'),
 ('Best Soundtracks in Movies', 'A list of movies with unforgettable soundtracks.', '', 'keanufromthematrix', 79, '2024-10-09 15:07:00.01799');

-- Populate post_upvote table
INSERT INTO post_upvote (post_id, user_id) VALUES
(79, 226), (79, 227), (79, 228),
(80, 226), (80, 229),
(81, 227), (81, 230), (81, 231),
(82, 228), (82, 232),
(83, 229), (83, 233), (83, 234),
(86, 230), (86, 235),
(87, 231), (87, 236),
(88, 232), (88, 237), (88, 238),
(89, 233), (89, 239),
(90, 234), (90, 240),
(91, 235), (91, 241), (91, 242),
(92, 233), (92, 243),
(93, 237), (93, 244),
(94, 238), (94, 245), (94, 246),
(95, 239), (95, 247),
(96, 240), (96, 248), (96, 249),
(97, 241), (97, 226),
(98, 242), (98, 227),
(99, 243), (99, 228),
(100, 244), (100, 229),
(101, 245), (101, 230),
(102, 246), (102, 231),
(103, 247), (103, 232),
(104, 248), (104, 233);


-- Populate post_downvote table
INSERT INTO post_downvote (post_id, user_id) VALUES
(79, 249), (79, 248),
(80, 245), (80, 244),
(80, 243),
(80, 242), (80, 241),
(81, 240), (81, 239),
(82, 238), (82, 237), (82, 236),
(83, 235), (83, 234),
(84, 233), (84, 232), (84, 231),
(85, 230), (85, 229),
(86, 228), (86, 227),
(87, 226), (87, 249),
(88, 248),
(89, 247), (89, 246),
(90, 245),
(91, 244), (91, 243),
(92, 242), (92, 241),
(93, 240),
(94, 239), (94, 238),
(95, 237), (95, 238),
(96, 235), (96, 234),
(97, 233),
(98, 232), (98, 231),
(99, 230), (99, 229),
(100, 228), (100, 227),
(101, 226), (101, 249),
(102, 248), (102, 247);

-- replies for the first post
INSERT INTO replies (description, post_id, user_id) VALUES
-- First level replies
('Hi everyone! Excited to be here!', 1, 2), --1
('Hello! I`m new here too!', 1, 3), --2
('Thanks for the welcome!', 1, 4), --3
('Looking forward to engaging with you all!', 1, 5), -- 4
('Nice to meet everyone!', 1, 6), -- 5
('Happy to join this community!', 1, 7), -- 6
('What a great place to learn!', 1, 8), -- 7
('Can’t wait to see what this forum has to offer!', 1, 9), -- 8
('Is there a specific topic focus here?', 1, 10), -- 9
('Excited to start my journey!', 1, 11), -- 10
('No, no specific topic at all!', 1, 11) ,-- 11
('I thought the topic was saying hi?', 1, 9), -- 12
('I do not count that as a topic', 1, 11), --13
('I know! I really have to start exploring forums', 1, 5), -- 14
('I would suggest looking at different forums', 1, 6); -- 15

INSERT INTO comment_replies (reply_id, parent_id) VALUES
(11, 9),
(12, 11),
(13, 12),
(14, 7),
( 15, 8);

-- Upvotes for each reply
INSERT INTO reply_upvote (reply_id, user_id) VALUES
-- Upvotes for first-level replies
(1, 2),  -- User 2 upvotes their own reply
(1, 3),  -- User 3 upvotes reply 1
(1, 4),  -- User 4 upvotes reply 1
(2, 2),  -- User 2 upvotes reply 2
(2, 4),  -- User 4 upvotes reply 2
(3, 5),  -- User 5 upvotes reply 3
(4, 6),  -- User 6 upvotes reply 4
(5, 7),  -- User 7 upvotes reply 5
(6, 8),  -- User 8 upvotes reply 6
(7, 9),  -- User 9 upvotes reply 7
(8, 10), -- User 10 upvotes reply 8
(9, 2),  -- User 2 upvotes reply 9
(10, 11),-- User 11 upvotes reply 10
(11, 12),-- User 12 upvotes reply 11
(12, 9), -- User 9 upvotes reply 12
(13, 11),-- User 11 upvotes reply 13
(14, 5), -- User 5 upvotes reply 14
(15, 6); -- User 6 upvotes reply 15

-- Downvotes for each reply
INSERT INTO reply_downvote (reply_id, user_id) VALUES
-- Downvotes for first-level replies
(1, 11), -- User 11 downvotes reply 1
(1, 10), -- User 10 downvotes reply 1
(2, 12), -- User 12 downvotes reply 2
(3, 13), -- User 13 downvotes reply 3
(4, 14), -- User 14 downvotes reply 4
(5, 15), -- User 15 downvotes reply 5
(6, 8),  -- User 8 downvotes reply 6
(7, 9),  -- User 9 downvotes reply 7
(8, 10), -- User 10 downvotes reply 8
(9, 12), -- User 12 downvotes reply 9
(10, 2), -- User 2 downvotes reply 10
(11, 6), -- User 6 downvotes reply 11
(12, 5), -- User 5 downvotes reply 12
(13, 4), -- User 4 downvotes reply 13
(14, 3), -- User 3 downvotes reply 14
(15, 1); -- User 1 downvotes reply 12

-- Replies for the post 'Starfield: Bethesda’s Space RPG Breaks Records'
INSERT INTO replies (description, post_id, user_id) VALUES
('I can`t believe how many players joined at launch!', 2, 2), -- 16
('The exploration mechanics are amazing!', 2, 3), --17
('Did anyone try the crafting system yet?', 2, 4), --18
('I love the visuals of the planets!', 2, 5), -- 19
('This game is a dream come true for space fans!', 2, 6), --20
('Bethesda really outdid themselves this time!', 2, 7), --21
('I`m intrigued by the storyline!', 2, 8), -- 22
('How does it compare to Skyrim in space?', 2, 9), --23
('Can`t wait for the modding community to get involved!', 2, 10), --24
('The music score is epic!', 2, 11), -- 25
('I did and I loved the mechanic!', 2, 11), -- 26
('Thanks!', 2, 4),
('Already started, lol!', 2, 10); -- 28

INSERT INTO comment_replies (reply_id, parent_id) VALUES
(26, 18),
(27, 26),
(28, 24);

-- Replies for the post 'Cyberpunk 2077: Phantom Liberty Expansion Review'
INSERT INTO replies (description, post_id, user_id) VALUES
('Idris Elba is a fantastic addition!', 3, 12), -- 29
('I`m loving the new quests!', 3, 13), -- 30
('This expansion definitely feels like a redemption!', 3, 14), -- 31
('Night City looks even more vibrant now!', 3, 15), -- 32
('I had some bugs, but it was worth it!', 3, 16), --33
('What do you think of the new vehicles?', 3, 17), --34
('The storytelling has improved a lot!', 3, 18), --35
('I miss the old gameplay style, though.', 3, 19), -- 36
('The atmosphere is incredible!', 3, 20), -- 37
('I hope they continue to build on this!', 3, 21), -- 38
('Me too!', 3, 8), -- 39
('The old gameplay style was fun, but I like the expansion gameplay, too', 3, 9); -- 40

INSERT INTO comment_replies (reply_id, parent_id) VALUES
(40, 36),
(40, 38);

-- Replies for the post 'Top 10 games of 2023'
INSERT INTO replies (description, post_id, user_id) VALUES
('I love your choices! Elden Ring is a masterpiece!', 4, 22),
('What about Hogwarts Legacy?', 4, 23),
('I`d add Resident Evil 4 remake to the list!', 4, 24),
('So many great games this year!', 4, 9),
('What criteria did you use for your list?', 4, 5),
('I can`t believe how good Tears of the Kingdom is!', 4, 20),
('I need to check out some of these games.', 4, 21),
('Did you include any indie games?', 4, 19),
('This list sparks so much nostalgia!', 4, 18),
('I`d love to hear more about your top picks!', 4, 17);

-- Replies for the post 'Top 10 games of 2023' (post_id 3)
INSERT INTO replies (description, post_id, user_id) VALUES
('I love your choices! Elden Ring is a masterpiece!', 5, 22),  -- 41
('What about Hogwarts Legacy?', 5, 23),  -- 42
('I`d add Resident Evil 4 remake to the list!', 5, 24),  -- 43
('So many great games this year!', 5, 5),  -- 44
('What criteria did you use for your list?', 5, 6),  -- 45
('I can`t believe how good Tears of the Kingdom is!', 5, 7),  -- 46
('I need to check out some of these games.', 5, 8),  -- 47
('Did you include any indie games?', 5, 25),  -- 48
('This list sparks so much nostalgia!', 5, 19),  -- 49
('I`d love to hear more about your top picks!', 5, 21), -- 50
('The world-building is incredible and the character development is top-notch.', 5, 23),-- 51
('I really enjoyed the story missions; they added so much depth to the game.', 5, 14), -- 52
('The lore is so rich, especially the backstories of the characters!', 5, 15), -- 53
('Yes! I`ve been playing it nonstop. Have you finished the main story yet?', 5, 23), -- 54
('The remake is fantastic! The graphics are stunning and the gameplay is smooth.', 5, 13), -- 55
('Absolutely! It`s hard to keep up with all the great releases!', 5, 22), -- 56
('I look for a mix of story, gameplay mechanics, and replayability.', 5, 23), -- 57
('The reveal of the floating islands was mind-blowing! What about you?', 5, 17), -- 58
('I loved Hollow Knight and Hades! They both offer such unique experiences.', 5, 19), -- 59
('Can you give specific game titles that really stood out to you?', 5, 13);

-- Threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(41, 51),  -- User 22 discusses what they love about Elden Ring
(42, 52),  -- User 23 mentions their favorite part of Hogwarts Legacy
(51, 53),  -- User 22 adds specifics about Elden Ring's lore
(52, 54),  -- User 23 asks if others have played Hogwarts Legacy recently
(43, 55),  -- User 24 talks about their experience with Resident Evil 4
(44, 56),  -- User 25 comments on the variety of games this year
(45, 57),  -- User 26 elaborates on their criteria for choosing games
(46, 58),  -- User 27 asks for others' favorite moments in Tears of the Kingdom
(48, 59),  -- User 29 shares their favorite indie games
(50, 60);  -- User 31 asks for more detailed recommendations


-- Replies for the post 'The Top 10 Best-Selling Games of 2023'
-- Replies for the post 'The Top 10 Best-Selling Games of 2023' (post_id 5)
INSERT INTO replies (description, post_id, user_id) VALUES
('Elden Ring definitely deserves the top spot!', 5, 17),  -- 61
('Call of Duty always sells well, huh?', 5, 13),  -- 62
('I was surprised to see how well Zelda did!', 5, 10),  -- 63
('Great analysis on the gaming market!', 5, 17),  -- 64
('Are you expecting any changes by year-end?', 5, 21),  -- 65
('I think some games will fall off the list soon.', 5, 22),  -- 66
('What’s your prediction for next year?', 5, 23),  -- 67
('I love seeing these stats!', 5, 19),  -- 68
('The gaming industry is booming right now!', 5, 6),  -- 69
('Let’s see if these trends continue!', 5, 18);  -- 70

-- Threaded replies
INSERT INTO replies (description, post_id, user_id) VALUES
('Absolutely! The games impact is undeniable.', 5, 17),  -- 71
('Yeah, they have such a loyal fan base!', 5, 13),  -- 72
('Zelda has really captured the hearts of gamers.', 5, 10),  -- 73
('Thank you! I love breaking down these trends.', 5, 17),  -- 74
('I think we might see some surprise hits in Q4!', 5, 21),  -- 75
('True! Some titles might not perform as expected.', 5, 22),  -- 76
('I’m expecting some indie titles to break through!', 5, 23),  -- 77
('It’s fascinating to watch the market evolve!', 5, 19),  -- 78
('For sure! The future looks bright for gaming.', 5, 6),  -- 79
('Definitely! Trends are always shifting!', 5, 18);  -- 80

-- Associating threaded replies with parent replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(61, 71),  -- Threaded reply to 'Elden Ring definitely deserves the top spot!'
(62, 72),  -- Threaded reply to 'Call of Duty always sells well, huh?'
(63, 73),  -- Threaded reply to 'I was surprised to see how well Zelda did!'
(64, 74),  -- Threaded reply to 'Great analysis on the gaming market!'
(65, 75),  -- Threaded reply to 'Are you expecting any changes by year-end?'
(66, 76),  -- Threaded reply to 'I think some games will fall off the list soon.'
(67, 77),  -- Threaded reply to 'What’s your prediction for next year?'
(68, 78),  -- Threaded reply to 'I love seeing these stats!'
(69, 79),  -- Threaded reply to 'The gaming industry is booming right now!'
(70, 80);  -- Threaded reply to 'Let’s see if these trends continue!'

-- Insert posts for each forum, starting post_id from 1
-- Now, insert replies for each post, with threaded replies included
-- Now, insert replies for each post, with threaded replies included

-- Replies for 'Understanding JavaScript Closures' (post_id 1)
INSERT INTO replies (description, post_id, user_id) VALUES
('Closures are a way to preserve the state of a function even after it has executed.', 6, 18),  -- 81
('Think of it as a function having access to variables from its parent scope.', 6, 11),  -- 82
('Can you give an example? I`m still confused.', 6, 12),  -- 83
('Here`s a simple example: a function that returns another function.', 6, 13),  -- 84
('This helped me understand closures better, thanks!', 6, 14);  -- 85

-- Threaded replies for 'Understanding JavaScript Closures'
INSERT INTO replies (description, post_id, user_id) VALUES
('Sure! Here’s a simple example with code.', 6, 19),  -- 86
('I`d love to see that example!', 6, 20),  -- 87
('Can you explain it in more detail?', 6, 21);  -- 88

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(83, 86),  -- Threaded reply to 'Can you give an example? I’m still confused.'
(84, 87),  -- Threaded reply to 'Here’s a simple example: a function that returns another function.'
(82, 88);  -- Threaded reply to 'Think of it as a function having access to variables from its parent scope.'

-- Replies for 'Help with Java ArrayList' (post_id 2)
INSERT INTO replies (description, post_id, user_id) VALUES
('Check if your index is within the bounds of the ArrayList!', 7, 15),  -- 89
('Are you sure you’re using the correct methods for adding/removing?', 7, 16),  -- 90
('You might want to use `add()` and `remove()` methods properly.', 7, 17),  -- 91
('IndexOutOfBoundsException often means you`re trying to access a non-existent index.', 7, 18),  -- 92
('Here`s a snippet that might help you:', 7, 19);  -- 93

-- Threaded replies for 'Help with Java ArrayList'
INSERT INTO replies (description, post_id, user_id) VALUES
('Great advice! Always check your indices.', 7, 20),  -- 94
('I had a similar issue before, and that fixed it!', 7, 21),  -- 95
('Can you share your code snippet?', 7, 22);  -- 96

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(90, 94),  -- Threaded reply to 'Are you sure you’re using the correct methods for adding/removing?'
(89, 95),  -- Threaded reply to 'Check if your index is within the bounds of the ArrayList!'
(93, 96);  -- Threaded reply to 'Here’s a snippet that might help you:'

-- Replies for 'Python vs Java for Web Development?' (post_id 3)
INSERT INTO replies (description, post_id, user_id) VALUES
('I personally prefer Python for its simplicity and readability.', 7, 20),  -- 97
('Java is great for large-scale applications, though.', 7, 21),  -- 98
('Django makes web development really fast and efficient.', 7, 22),  -- 99
('If you’re building something enterprise-level, Java might be the way to go.', 7, 23),  -- 100
('Both have their strengths; it depends on your project requirements.', 7, 21);  -- 101

-- Threaded replies for 'Python vs Java for Web Development?'
INSERT INTO replies (description, post_id, user_id) VALUES
('I find Django easier for prototyping.', 7, 20),  -- 102
('Java has a steeper learning curve but pays off.', 7, 16),  -- 103
('Both are powerful, but what do you need for your project?', 7, 22);  -- 104

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(97, 102),  -- Threaded reply to 'I personally prefer Python for its simplicity and readability.'
(98, 103),  -- Threaded reply to 'Java is great for large-scale applications, though.'
(99, 104);  -- Threaded reply to 'Django makes web development really fast and efficient.'

-- Replies for 'How to debug Java code?' (post_id 4)
INSERT INTO replies (description, post_id, user_id) VALUES
('Start by using print statements to check your variable values.', 8, 23),  -- 105
('Using a debugger can help you step through your code.', 8, 6),  -- 106
('Have you checked for common issues like null pointers?', 8, 7),  -- 107
('Reviewing your code carefully can often help find hidden bugs.', 8, 2),  -- 108
('Make sure your logic is sound; sometimes it`s just a simple mistake.', 8, 9);  -- 109

-- Threaded replies for 'How to debug Java code?'
INSERT INTO replies (description, post_id, user_id) VALUES
('Debugging tools are really helpful for tracking down issues.', 8, 11),  -- 110
('I always forget to check for null values; that`s a common mistake!', 8, 1),  -- 111
('Do you have any specific error messages you`re seeing?', 8, 2);  -- 112

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(106, 110),  -- Threaded reply to 'Using a debugger can help you step through your code.'
(107, 111),  -- Threaded reply to 'Have you checked for common issues like null pointers?'
(105, 112);  -- Threaded reply to 'Start by using print statements to check your variable values.'

-- Now, insert replies for each post, with threaded replies included

-- Replies for 'The Rise of Quantum Computing' (post_id 5)
INSERT INTO replies (description, post_id, user_id) VALUES
('Quantum computing has immense potential to solve problems we can`t currently tackle.', 9, 13),  -- 113
('I think it could change fields like cryptography significantly.', 9, 14),  -- 114
('What specific problems do you think quantum computing will solve first?', 9, 15),  -- 115
('The advancements by Google and IBM are quite impressive!', 9, 16),  -- 116
('I`m excited to see how it evolves in the next decade!', 9, 17);  -- 117

-- Threaded replies for 'The Rise of Quantum Computing'
INSERT INTO replies (description, post_id, user_id) VALUES
('I read that quantum computers can perform calculations much faster than classical ones.', 9, 8),  -- 118
('What about the limitations of quantum computing?', 9, 9),  -- 119
('Will we need new algorithms for these computers?', 9, 4);  -- 120

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(115, 118),  -- Threaded reply to 'What specific problems do you think quantum computing will solve first?'
(114, 119),  -- Threaded reply to 'I think it could change fields like cryptography significantly.'
(113, 120);  -- Threaded reply to 'Quantum computing has immense potential to solve problems we can’t currently tackle.'

-- Replies for 'AI and the Future of Work' (post_id 6)
INSERT INTO replies (description, post_id, user_id) VALUES
('AI will create new job opportunities in fields we haven`t imagined yet.', 10, 11),  -- 121
('I`m worried about the short-term job losses, though.', 6, 4),  -- 122
('It`s likely a mix of both; some jobs will vanish, while new ones will emerge.', 10, 13),  -- 123
('What skills should we focus on to adapt to this change?', 6, 4),  -- 124
('AI can enhance productivity, allowing humans to focus on more creative tasks.', 10, 5);  -- 125

-- Threaded replies for 'AI and the Future of Work'
INSERT INTO replies (description, post_id, user_id) VALUES
('I think reskilling will be crucial.', 10, 6),  -- 126
('Can you give examples of new jobs created by AI?', 10, 7),  -- 127
('Automation is already changing industries significantly.', 10, 8);  -- 128

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(123, 126),  -- Threaded reply to 'It’s likely a mix of both; some jobs will vanish, while new ones will emerge.'
(124, 127),  -- Threaded reply to 'What skills should we focus on to adapt to this change?'
(125, 128);  -- Threaded reply to 'AI can enhance productivity, allowing humans to focus on more creative tasks.'

-- Replies for '5G Technology: What Does the Future Hold?' (post_id 7)
INSERT INTO replies (description, post_id, user_id) VALUES
('5G will definitely improve IoT applications dramatically.', 11, 21),  -- 129
('Healthcare can benefit from faster data transmission.', 11, 15),  -- 130
('What are your thoughts on the security concerns with 5G?', 11, 11),  -- 131
('I`m excited for the new opportunities in remote work!', 11, 2),  -- 132
('The speed of 5G will allow for innovations we can`t yet foresee.', 11, 3);  -- 133

-- Threaded replies for '5G Technology: What Does the Future Hold?'
INSERT INTO replies (description, post_id, user_id) VALUES
('I hope it leads to advancements in smart cities.', 11, 14),  -- 134
('What industries do you think will be most affected by 5G?', 11, 24),  -- 135
('I read that 5G could help reduce latency in gaming.', 11, 6);  -- 136

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(130, 134),  -- Threaded reply to 'Healthcare can benefit from faster data transmission.'
(131, 135),  -- Threaded reply to 'What are your thoughts on the security concerns with 5G?'
(132, 136);  -- Threaded reply to 'I’m excited for the new opportunities in remote work!'

-- Replies for 'What is your favorite programming language?' (post_id 8)
INSERT INTO replies (description, post_id, user_id) VALUES
('I love Python too! It`s so user-friendly.', 12, 7),  -- 137
('Java has its own charm, especially for larger applications.', 12, 8),  -- 138
('JavaScript is great for web development!', 12, 9),  -- 139
('C++ is my go-to for performance-critical applications.', 12, 6),  -- 140
('I`m learning Rust, and it`s quite interesting!', 12, 11);  -- 141

-- Threaded replies for 'What is your favorite programming language?'
INSERT INTO replies (description, post_id, user_id) VALUES
('What features do you like most about Python?', 12, 19),  -- 142
('Rust`s ownership model is a game changer.', 12, 10),  -- 143
('I find it hard to pick just one favorite language!', 12, 4);  -- 144

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(137, 142),  -- Threaded reply to 'I love Python too! It’s so user-friendly.'
(141, 143),  -- Threaded reply to 'I’m learning Rust, and it’s quite interesting!'
(138, 144);  -- Threaded reply to 'Java has its own charm, especially for larger applications.'

-- Replies for 'What is the future of AI?' (post_id 9)
INSERT INTO replies (description, post_id, user_id) VALUES
('AI will definitely change the landscape of many industries.', 13, 15),  -- 145
('Ethics in AI is a huge concern that we need to address.', 13, 6),  -- 146
('I`m curious about the role of AI in education.', 13, 7),  -- 147
('AI can enhance decision-making in many fields.', 13, 8),  -- 148
('We need to ensure AI is developed responsibly.', 13, 9);  -- 149

-- Threaded replies for 'What is the future of AI?'
INSERT INTO replies (description, post_id, user_id) VALUES
('What are your thoughts on AI regulation?', 13, 7),  -- 150
('AI should be transparent and explainable.', 13, 1),  -- 151
('I hope AI will lead to better healthcare solutions.', 13, 2);  -- 152

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(146, 150),  -- Threaded reply to 'Ethics in AI is a huge concern that we need to address.'
(147, 151),  -- Threaded reply to 'I’m curious about the role of AI in education.'
(148, 152);  -- Threaded reply to 'AI can enhance decision-making in many fields.'


-- Now, insert replies for each post, with threaded replies included

-- Replies for 'The James Webb Space Telescope was launched in 2021...' (post_id 10)
INSERT INTO replies (description, post_id, user_id) VALUES
('The images from JWST are absolutely breathtaking!', 14, 3),  -- 153
('It`s amazing how far our technology has come.', 14, 14),  -- 154
('What discoveries do you think will be next?', 14, 5),  -- 155
('I love the details they capture; it`s like peering into the past.', 14, 7),  -- 156
('The data they’re collecting will change our understanding of the universe.', 14, 7);  -- 157

-- Threaded replies for 'The James Webb Space Telescope was launched in 2021...'
INSERT INTO replies (description, post_id, user_id) VALUES
('I hope they share more images soon!', 14, 8),  -- 158
('What`s the significance of the Wolf-Rayet star?', 14, 9),  -- 159
('Are there plans for future missions with JWST?', 14, 8);  -- 160

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(155, 158),  -- Threaded reply to 'What discoveries do you think will be next?'
(154, 159),  -- Threaded reply to 'It’s amazing how far our technology has come.'
(153, 160);  -- Threaded reply to 'The images from JWST are absolutely breathtaking!'

-- Replies for 'Space odyssey: Stunning images from the James Webb telescope' (post_id 11)
INSERT INTO replies (description, post_id, user_id) VALUES
('The detail in these images is mind-blowing!', 18, 8),  -- 161
('I never knew star formation could look so beautiful.', 18, 7),  -- 162
('What do you think is the most exciting aspect of this image?', 18, 3),  -- 163
('NASA has outdone themselves with the JWST!', 18, 14),  -- 164
('I can`t wait to see what else they discover!', 18, 15);  -- 165

-- Threaded replies for 'Space odyssey: Stunning images from the James Webb telescope'
INSERT INTO replies (description, post_id, user_id) VALUES
('I`m curious about how they captured such clarity.', 18, 6),  -- 166
('Will we get to see more images from Rho Ophiuchi?', 18, 7),  -- 167
('These images remind me of the wonders of space.', 18, 8);  -- 168

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(163, 166),  -- Threaded reply to 'What do you think is the most exciting aspect of this image?'
(165, 167),  -- Threaded reply to 'I can’t wait to see what else they discover!'
(162, 168);  -- Threaded reply to 'I never knew star formation could look so beautiful.'

-- Replies for 'NASA’s James Webb Telescope Captures the Fiery Remains...' (post_id 12)
INSERT INTO replies (description, post_id, user_id) VALUES
('The Southern Ring Nebula is one of my favorites!', 19, 9),  -- 169
('How does JWST compare to Hubble in terms of detail?', 19, 10),  -- 170
('I`m fascinated by the life cycle of stars.', 19, 17),  -- 171
('This telescope is a game changer for astronomy!', 19, 22),  -- 172
('What other nebulae do you think they will explore next?', 19, 23);  -- 173

-- Threaded replies for 'NASA’s James Webb Telescope Captures the Fiery Remains...'
INSERT INTO replies (description, post_id, user_id) VALUES
('I hope they keep releasing more images!', 19, 9),  -- 174
('What technology allows JWST to capture such clarity?', 19, 5),  -- 175
('The colors in these images are incredible!', 19, 6);  -- 176

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(171, 174),  -- Threaded reply to 'I’m fascinated by the life cycle of stars.'
(170, 175),  -- Threaded reply to 'How does JWST compare to Hubble in terms of detail?'
(172, 176);  -- Threaded reply to 'This telescope is a game changer for astronomy!'

-- Replies for 'Latest SpaceX launch discussion' (post_id 13)
INSERT INTO replies (description, post_id, user_id) VALUES
('SpaceX continues to push the boundaries of space travel!', 20, 7),  -- 177
('I`m excited about the potential for Mars missions.', 20, 9),  -- 178
('What do you think was the highlight of the latest launch?', 20, 19),  -- 179
('The technology they’re developing is fascinating!', 20, 12),  -- 180
('I`m curious about how they handle recovery of the rockets.', 20, 10);  -- 181

-- Threaded replies for 'Latest SpaceX launch discussion'
INSERT INTO replies (description, post_id, user_id) VALUES
('How do you think SpaceX will influence future space missions?', 20, 12),  -- 182
('The live streams of the launches are always so exciting!', 20, 15),  -- 183
('Can anyone explain how their Starship technology works?', 20, 14);  -- 184

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(179, 182),  -- Threaded reply to 'What do you think was the highlight of the latest launch?'
(178, 183),  -- Threaded reply to 'I’m excited about the potential for Mars missions.'
(177, 184);  -- Threaded reply to 'SpaceX continues to push the boundaries of space travel!'

-- Now, insert replies for each post, with threaded replies included

-- Replies for 'How to Grow Tomatoes' (post_id 14)
INSERT INTO replies (description, post_id, user_id) VALUES
('I`ve had great success with cherry tomatoes!', 21, 15),  -- 188
('What are your tips for preventing blight?', 21, 16),  -- 189
('Companion planting really helps with pests!', 21, 7),  -- 190
('I use raised beds for better drainage.', 21, 5),  -- 191
('What variety do you recommend for beginners?', 21, 4);  -- 192

-- Threaded replies for 'How to Grow Tomatoes'
INSERT INTO replies (description, post_id, user_id) VALUES
('I love the flavor of heirloom tomatoes!', 21, 11),  -- 193
('Do you have any tricks for pruning them?', 21, 12),  -- 194
('How often should I water them?', 21, 5);  -- 195

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(189, 193),  -- Threaded reply to 'What are your tips for preventing blight?'
(190, 194),  -- Threaded reply to 'Companion planting really helps with pests!'
(188, 195);  -- Threaded reply to 'I’ve had great success with cherry tomatoes!'

-- Replies for 'Best Tools for Gardening' (post_id 15)
INSERT INTO replies (description, post_id, user_id) VALUES
('A good trowel is essential for any gardener!', 22, 11),  -- 196
('I can`t live without my pruning shears!', 22, 4),  -- 197
('What do you think about electric garden tools?', 22, 5),  -- 198
('Hand tools always feel more reliable to me.', 22, 6),  -- 199
('I recommend investing in high-quality gloves!', 22, 7);  -- 200

-- Threaded replies for 'Best Tools for Gardening'
INSERT INTO replies (description, post_id, user_id) VALUES
('What`s your favorite brand for gardening tools?', 22, 8),  -- 201
('I find that lighter tools help with fatigue.', 22, 9),  -- 202
('Are there any tools that are worth splurging on?', 22, 10);  -- 203

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(198, 201),  -- Threaded reply to 'What do you think about electric garden tools?'
(196, 202),  -- Threaded reply to 'A good trowel is essential for any gardener!'
(197, 203);  -- Threaded reply to 'I can’t live without my pruning shears!'

-- Replies for 'Organic Gardening Methods' (post_id 16)
INSERT INTO replies (description, post_id, user_id) VALUES
('I love using compost for my garden!', 23, 21),  -- 204
('Mulching has made a huge difference for me.', 23, 12),  -- 205
('Have you tried companion planting for pest control?', 23, 13),  -- 206
('What`s your favorite organic pesticide?', 23, 12),  -- 207
('I`m interested in trying more natural fertilizers.', 23, 15);  -- 208

-- Threaded replies for 'Organic Gardening Methods'
INSERT INTO replies (description, post_id, user_id) VALUES
('I`ve heard good things about neem oil.', 23, 6),  -- 209
('Organic gardening takes patience but is so rewarding.', 23, 7),  -- 210
('How do you deal with weeds naturally?', 23, 8);  -- 211

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(206, 209),  -- Threaded reply to 'Have you tried companion planting for pest control?'
(205, 210),  -- Threaded reply to 'Mulching has made a huge difference for me.'
(204, 211);  -- Threaded reply to 'I love using compost for my garden!'


-- Now, insert replies for each post, with threaded replies included

-- Replies for 'Top 10 Rock Bands of All Time' (post_id 17)
INSERT INTO replies (description, post_id, user_id) VALUES
('The Beatles will always be at the top for me!', 24 ,19),  -- 212
('What about Led Zeppelin? They were incredible live', 24, 13),  -- 213
('I think Queen deserves a spot on that list!', 24, 11),  -- 214
('Nirvana changed the music scene forever!', 24, 12),  -- 215
('Can you include more recent bands as well?', 24, 13);  -- 216

-- Threaded replies for 'Top 10 Rock Bands of All Time'
INSERT INTO replies (description, post_id, user_id) VALUES
('I totally agree about Queen; their music is timeless.', 24, 14),  -- 217
('Nirvana`s influence is undeniable!', 24, 15),  -- 218
('What do you think about The Rolling Stones?', 24, 16);  -- 219

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(213, 217),  -- Threaded reply to 'What about Led Zeppelin? They were incredible live!'
(214, 218),  -- Threaded reply to 'I think Queen deserves a spot on that list!'
(212, 219);  -- Threaded reply to 'The Beatles will always be at the top for me!'

-- Replies for 'The Rise of Indie Music' (post_id 18)
INSERT INTO replies (description, post_id, user_id) VALUES
('Indie music has such a unique sound!', 25, 17),  -- 220
('I love discovering new indie artists!', 25, 18),  -- 221
('What do you think about the DIY approach in indie music?', 25, 19),  -- 222
('Indie artists are changing the game in music!', 25, 14),  -- 223
('It`s amazing how they connect with their fans!', 25, 11);  -- 224

-- Threaded replies for 'The Rise of Indie Music'
INSERT INTO replies (description, post_id, user_id) VALUES
('I think social media plays a huge role in their success.', 25, 12),  -- 225
('Live performances really showcase their talent.', 25, 14),  -- 226
('Which indie artists do you recommend?', 25, 10);  -- 227

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(222, 225),  -- Threaded reply to 'What do you think about the DIY approach in indie music?'
(220, 226),  -- Threaded reply to 'Indie music has such a unique sound!'
(221, 227);  -- Threaded reply to 'I love discovering new indie artists!'

-- Replies for 'Best Soundtracks in Movies' (post_id 19)
INSERT INTO replies (description, post_id, user_id) VALUES
('The soundtrack for "Inception" is amazing!', 26, 15),  -- 228
('I love the music from "The Lion King"!', 26, 16),  -- 229
('What about "Guardians of the Galaxy"? So much nostalgia!', 26, 22),  -- 230
('Hans Zimmer always delivers fantastic scores!', 26, 18),  -- 231
('I think soundtracks really enhance the movie experience.', 26, 19);  -- 232

-- Threaded replies for 'Best Soundtracks in Movies'
INSERT INTO replies (description, post_id, user_id) VALUES
('Do you prefer original scores or compiled soundtracks?', 26, 15),  -- 233
('What`s your all-time favorite soundtrack?', 26, 11),  -- 234
('I could listen to movie soundtracks all day!', 26, 12);  -- 235

-- Associating threaded replies
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(230, 233),  -- Threaded reply to 'What about "Guardians of the Galaxy"? So much nostalgia!'
(228, 234),  -- Threaded reply to 'The soundtrack for "Inception" is amazing!'
(229, 235);  -- Threaded reply to 'I love the music from "The Lion King"!'


-- Insert mock moderation roles
-- Assuming the usernames of the forum creators are:
-- Welcome Forum: admin1
-- Gaming Forum: halo_finite
-- Programming Help Forum: java_jack
-- Tech Forum: mutex
-- Space Forum: spaceXisbetter
-- Gardening Forum: gardener123
-- Music Forum: rockstar79

INSERT INTO moderation (forum_id, username) VALUES
-- Welcome Forum
(73, 'admin1'),  -- Forum Creator
-- Gaming Forum
(75, 'halo_finite'),  -- Forum Creator
-- Programming Help Forum
(76, 'java_jack'),  -- Forum Creator
-- Tech Forum
(74, 'mutex'),  -- Forum Creator
-- Space Forum
(77, 'spaceXisbetter'),  -- Forum Creator
-- Gardening Forum
(78, 'lily_lover'),  -- Forum Creator
-- Music Forum
(79, 'rockstar79');  -- Forum Creator


COMMIT TRANSACTION;
