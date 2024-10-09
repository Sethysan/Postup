BEGIN TRANSACTION;

-- Insert mock users
-- Passwords are hashed using bcrypt with a cost factor of 8

-- Forrest: password123
-- Ash:     mypassword
-- Lilly:   modpassword
-- Mutex:   adminpassword
-- Daniel:  userpassword

INSERT INTO users (username, password_hash, role, user_image) VALUES
('removed', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_BANNED', 'https://sm.ign.com/ign_ap/cover/a/avatar-gen/avatar-generations_hugw.jpg'),
('Forrest', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', 'https://plus.unsplash.com/premium_photo-1683121366070-5ceb7e007a97?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('Ash', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', 'https://plus.unsplash.com/premium_photo-1664541336896-b3d5f7dec9a3?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('Lilly', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_MODERATOR', 'https://images.unsplash.com/photo-1586297135537-94bc9ba060aa?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
('Mutex', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_ADMIN', 'https://images.unsplash.com/photo-1678286742832-26543bb49959?q=80&w=1888&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D' ),
('Daniel', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', 'https://plus.unsplash.com/premium_photo-1661580702098-b1a081567ba3?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');

-- Insert mock forums
INSERT INTO forums (topic, description, author, time_of_creation) VALUES
('Tech Discussions', 'A forum to discuss all things tech.', 'Forrest', '2024-01-01 10:00:00'),
('Gaming', 'All things related to gaming news and discussion.', 'Ash', '2024-01-02 11:00:00'),
('Programming Help', 'Ask for and provide programming help here.', 'Mutex', '2024-01-03 12:00:00'),
('Science & Space', 'Discussion about science and space exploration.', 'Daniel', '2024-01-04 13:00:00');

-- Insert mock posts
INSERT INTO posts (title, description, image, author, forum_id, time_of_creation) VALUES
('Starfield: Bethesda’s Space RPG Breaks Records', 'Bethesda’s highly anticipated space RPG, Starfield, has broken multiple records, including over 6 million players at launch. Players explore an open-world galaxy, filled with planets, space stations, and a deep narrative to uncover.', 'https://cdn.akamai.steamstatic.com/steam/apps/1716740/capsule_616x353.jpg?t=1691779121', 'Lilly', 2, '2024-10-05 12:30:00'),
('Cyberpunk 2077: Phantom Liberty Expansion Review', 'Phantom Liberty, the much-anticipated expansion for Cyberpunk 2077, introduces Idris Elba’s character Solomon Reed, expanding on the world of Night City with brand-new areas and quests. Does this expansion redeem the game after its rocky launch?', 'https://cdn.akamai.steamstatic.com/steam/apps/1091500/header.jpg?t=1693568339', 'Forrest', 2, '2024-10-05 13:00:00'),
('Understanding JavaScript Closures', 'Can someone explain closures in JavaScript? I keep getting confused by the concept of inner functions having access to outer function variables.', 'https://edward-huang.com/images/what-is-really-so-special-about-javascript-closure-/Closure%20JS.png', 'Forrest', 3, '2024-10-05 14:00:00'),
('Help with Java ArrayList', 'I need help with adding and removing elements from an ArrayList in Java. I keep getting `IndexOutOfBoundsException`. Can someone tell me what I am doing wrong?', 'https://upload.wikimedia.org/wikipedia/en/3/30/Java_programming_language_logo.svg', 'Ash', 3, '2024-10-05 14:30:00'),
('Python vs Java for Web Development?', 'I am trying to decide between Python (using Django) and Java (using Spring) for web development. Which one should I choose?', 'https://preview.redd.it/rxezjyf4ojx41.png?width=1080&crop=smart&auto=webp&s=38b346ba426f740eefcde2c6dfb23c5e6e810bc9', 'Lilly', 3, '2024-10-05 15:00:00'),
('The Rise of Quantum Computing', 'Quantum computing is revolutionizing the tech industry. Companies like Google and IBM are making major strides in developing quantum processors. What are your thoughts on its potential to solve complex problems?', 'https://res.cloudinary.com/dthpnue1d/image/upload/v1714979675/advantages_of_quantum_computing_f11be1ee8b.jpg', 'Lilly', 1, '2024-10-05 16:00:00'),
('AI and the Future of Work', 'With advancements in artificial intelligence, we are starting to see AI systems take over tasks previously done by humans. Do you think AI will cause massive job losses or create new opportunities?', 'https://www.smart-academy.in/wp-content/uploads/2023/12/BLOG-Image-Future-Trends-in-the-Age-of-Artificial-Intelligence-1.jpg', 'Forrest', 1, '2024-10-05 16:30:00'),
('5G Technology: What Does the Future Hold?', 'As 5G networks become more widespread, we are on the verge of seeing faster, more reliable connections across the globe. What are your thoughts on the impact of 5G on industries like healthcare, IoT, and more?', 'https://imageio.forbes.com/specials-images/imageserve/5db28252c538200007815c25/What-Is-5G-Technology-And-How-Must-Businesses-Prepare-For-It-/960x0.jpg?format=jpg&width=1440', 'Ash', 1, '2024-10-05 17:00:00');

INSERT INTO posts (title, description, image, author, forum_id) VALUES
('What is your favorite programming language?', 'I personally enjoy Python for its simplicity.', 'https://miro.medium.com/v2/resize:fit:1100/format:webp/1*EcrlPFA5Sdno4YvxDw73FA.png', 'Forrest', 1),
('The James Webb Space Telescope was launched in 2021 and began collecting data in 2022, reshaping our understanding of the early universe while taking wondrous pictures of the cosmos.', 'A rarely seen prelude to a Supernova shows a super-bright, massive Wolf-Rayet star in a composite image taken by the James Webb Telescope. The star, WR 124, is 15,000 light-years away in the constellation Sagittarius and is 30 times the mass of our Sun.', 'https://www.reuters.com/resizer/v2/7BANAT2Q3FIK7BOTULOWAIWGBQ.jpg?auth=17a64f7408aab4b44df4f1bbd8dc98c043b37f367feeb6e957982f61d4fa34fb&width=1920&quality=80', 'Ash', 4),
('Space odyssey: Stunning images from the James Webb telescope', 'A look at Sun-like stars being born in this detailed close-up of Rho Ophiuchi, the closest-star-forming region to Earth in a composite image taken by the James Webb Telescope and released by NASA on July 12, 2023. Dark, dense dust cocoons still-forming protostars, while an emerging stellar newborn (top center) shoots out two huge jets of molecular hydrogen. NASA', 'https://www.reuters.com/resizer/v2/O2AKCAEW4VM45D73OPETSDJNBE.jpg?auth=64c3f02b23b72ae2675010b2765ed748a4ac2defcf334a136d3003b54b339de9&width=1920&quality=80', 'Forrest', 4),
('Top 10 games of 2023', 'Here’s a list of my favorite games from 2023.', 'https://newzoo.com/wp-content/uploads/2023/11/Top-10-bestselling-PC-and-console-game-releases-in-2023-from-January-through-October.png', 'Ash', 2 ),
('How to debug Java code?', 'I’m stuck on a bug and need help with debugging Java code.', 'https://i.redd.it/grzhl4arakw21.jpg', 'Mutex', 3),
('Latest SpaceX launch discussion', 'Let’s talk about the recent SpaceX mission and its impact.', 'https://nss.org/wp-content/uploads/2018/02/falcon-heavy-liftoff.jpg', 'Daniel', 4),
('What is the future of AI?', 'Discuss how AI is shaping the future of technology.', 'https://d2ds8yldqp7gxv.cloudfront.net/Blog+Explanatory+Images/Future+of+Artificial+Intelligence+1.webp', 'Lilly', 1),
('NEVER obsolete','I wonder how many people are still getting upgrades every 2 years for $99!', 'https://i.redd.it/p33nor0btfi91.jpg', 'Mutex', 1),
('Help', 'I need help with my computer. I feel like I''m aimlessly running in cirlces like a baby chick. I need some direction', 'https://as1.ftcdn.net/v2/jpg/05/63/32/30/1000_F_563323083_iD6EXOVpPblV1w3BcRzsMx4goaFGvW7U.jpg', 'Forrest', 1),
('NASA’s James Webb Telescope Captures the Fiery Remains of a Dying Star in Unprecedented Detail', 'More than a year after NASA’s James Webb Space Telescope brought us images of the Southern Ring Nebula, the world’s most powerful observer captured the remains of another celestial body with stunning clarity.', 'https://www.thisiscolossal.com/wp-content/uploads/2023/08/webb-1.jpg', 'Lilly', 4),
('The Top 10 Best-Selling Games of 2023', 'Here’s a breakdown of the best-selling games in 2023, featuring franchises like Elden Ring, The Legend of Zelda: Tears of the Kingdom, and Call of Duty: Modern Warfare II.', 'https://imageio.forbes.com/specials-images/imageserve/6579be889c4f517b9631ef97/chart1/960x0.jpg?format=jpg&width=1440', 'Ash', 2);

-- Insert mock post upvotes
INSERT INTO post_upvote (post_id, user_id)
VALUES
    (14, 2), (14, 3), (14, 4), (14, 5), (14, 6),
    (15, 2), (15, 3), (15, 4), (15, 5), (15, 6),
    (16, 2), (16, 3), (16, 4), (16, 5), (16, 6),
    (17, 2), (17, 3), (10, 4), (17, 5), ( 1, 6),
    (18, 2), (18, 3), (18, 4), (18, 5), (18, 6),
    ( 9, 2), ( 9, 3), ( 9, 4), ( 9, 5), ( 9, 6),
    ( 7, 2), ( 8, 3),  (8, 4), ( 8, 5), ( 7, 6),
    ( 4, 2),  (5, 3), ( 3, 4),  (7, 5), ( 6, 6),
    ( 5, 2),  (4, 3), (12, 4),  (3, 5), ( 5, 6),
    (10, 3), ( 2, 5), (19, 4), (10, 5), (10, 2),
    (10, 6), (11, 3), (11, 4), (11, 5), (11, 6);





-- Insert mock post downvotes
INSERT INTO post_downvote (post_id, user_id) VALUES
(1, 3),  -- Lilly   downvoted post 1
(4, 2),  -- Ash     downvoted post 4
(1, 4),  -- Mutex   downvoted post 1
(3, 1),  -- Forrest downvoted post 3
(2, 3),  -- Lilly   downvoted post 2
(1, 1),  -- Forrest downvoted post 1
(2, 1),  -- Forrest downvoted post 2
(2, 2);  -- Ash     downvoted post 2
-- Insert mock replies
INSERT INTO replies (description, post_id, user_id, time_of_creation) VALUES
('I love Python too! It’s my go-to language.', 1, 2, '2024-01-05 15:00:00'), --1
('I prefer JavaScript for web development.', 1, 3, '2024-01-05 15:30:00'), --2
('Great list! I agree with most of the games here.', 2, 1, '2024-01-06 16:00:00'),
('Try using the debugger tool in your IDE.', 3, 4, '2024-01-07 17:00:00'),
('AI is going to revolutionize every industry.', 5, 2, '2024-01-09 19:00:00'),
('These images are breathtaking! The James Webb Telescope is revolutionizing space exploration.', 1, 2, '2024-10-05 09:30:00'),
('I can’t believe how clear the details are. This is truly cutting-edge science.', 1, 3, '2024-10-05 09:45:00'),
('The Wolf-Rayet star is fascinating. The formation of cosmic dust plays such a critical role in star and planet formation.', 2, 1, '2024-10-05 10:30:00'),
('This is why the Webb telescope is such a valuable tool for astronomers.', 3, 2, '2024-10-05 11:30:00'),
('Elden Ring really deserves its spot at the top. The open-world experience was incredible.', 1, 3, '2024-10-05 12:15:00'), --10
('Tears of the Kingdom took everything great about Breath of the Wild and expanded it. I loved it.', 1, 2, '2024-10-05 12:20:00'),
('Starfield was everything I wanted from a space RPG. Bethesda nailed it!', 2, 1, '2024-10-05 12:45:00'),
('The game was a bit buggy at launch, but they’ve patched it up really well since then.', 2, 3, '2024-10-05 12:50:00'),
('Phantom Liberty is a great expansion, but the core issues with Cyberpunk 2077 are still there.', 3, 2, '2024-10-05 13:20:00'),
('Idris Elba’s character is a fantastic addition, but I still feel like the game needed more polish.', 3, 4, '2024-10-05 13:30:00'),
('Closures are created whenever a function is created inside another function, and they allow the inner function to access the outer function’s scope.', 1, 2, '2024-10-05 14:15:00'),
('Are you sure you are not trying to access an invalid index? Make sure the index exists before you remove an item from the ArrayList.', 2, 3, '2024-10-05 14:45:00'),
('Both are great for web development, but Python might be easier for a beginner. Java, on the other hand, is more widely used in enterprise solutions.', 3, 4, '2024-10-05 15:20:00'),
('If you are familiar with Java, I would recommend Spring. It’s more structured, though Django can be quicker to start with.', 3, 1, '2024-10-05 15:30:00'),
('Quantum computing could solve problems like cryptography and climate modeling that classical computers can’t handle.', 1, 2, '2024-10-05 16:15:00'),
('The potential of quantum computing is huge, but I’m worried about how long it will take to see real-world applications.', 1, 4, '2024-10-05 16:25:00'),
('AI will definitely change the workforce, but I think it will create new opportunities in fields we can’t even imagine yet.', 2, 3, '2024-10-05 16:45:00'),
('AI taking over repetitive tasks will free up humans to focus on creative and strategic work. It could lead to a more productive society.', 2, 1, '2024-10-05 16:50:00'),
('5G will be a game-changer, especially in industries that rely on real-time data processing, like healthcare and autonomous vehicles.', 3, 4, '2024-10-05 17:20:00'),
('I’m excited about 5G, but I’m also concerned about the environmental impact of rolling out all this new infrastructure.', 3, 3, '2024-10-05 17:30:00');

-- Insert mock reply upvotes
INSERT INTO reply_upvote (reply_id, user_id) VALUES
(1, 1),  -- Forrest upvoted reply 1
(2, 2),  -- Ash     upvoted reply 2
(3, 1),  -- Forrest upvoted reply 3
(4, 3),  -- Lilly   upvoted reply 4
(1, 2),  -- Ash     upvoted reply 1
(5, 3);  -- Lilly   upvoted reply 5

-- Insert mock reply downvotes
INSERT INTO reply_downvote (reply_id, user_id) VALUES
(2, 1),  -- Forrest downvoted reply 2
(5, 3),  -- Lilly   downvoted reply 5
(4, 3);  -- Lilly   downvoted reply 4

-- Insert mock comment replies (threaded replies)
INSERT INTO comment_replies (parent_id, reply_id) VALUES
(1, 2),  -- Comment on 'I love Python too!' by 'I prefer JavaScript for web development.'
(1, 7),  -- Another comment on 'I love Python too!' by 'I can’t believe how clear the details are.'
(2, 10), -- Comment on 'I prefer JavaScript for web development.' by 'Elden Ring really deserves its spot at the top.'
(3, 4),  -- Comment on 'Great list! I agree with most of the games here.' by 'Try using the debugger tool in your IDE.'
(3, 13), -- Another comment on 'Great list! I agree with most of the games here.' by 'The game was a bit buggy at launch.'
(5, 6),  -- Comment on 'AI is going to revolutionize every industry.' by 'These images are breathtaking!'
(5, 22), -- Another comment on 'AI is going to revolutionize every industry.' by 'AI will definitely change the workforce.'
(6, 8),  -- Comment on 'These images are breathtaking!' by 'The Wolf-Rayet star is fascinating.'
(7, 9),  -- Comment on 'I can’t believe how clear the details are.' by 'This is why the Webb telescope is such a valuable tool.'
(8, 12), -- Comment on 'The Wolf-Rayet star is fascinating.' by 'Starfield was everything I wanted from a space RPG.'
(9, 15), -- Comment on 'This is why the Webb telescope is such a valuable tool.' by 'Idris Elba’s character is a fantastic addition.'
(10, 11),-- Comment on 'Elden Ring really deserves its spot at the top.' by 'Tears of the Kingdom took everything great about Breath of the Wild.'
(13, 14),-- Comment on 'The game was a bit buggy...' by 'Phantom Liberty is a great expansion.'
(14, 16),-- Comment on 'Phantom Liberty...' by 'Closures are created whenever a function is created.'
(16, 17),-- Comment on 'Closures are created...' by 'Are you sure you are not trying to access an invalid index?'
(17, 18),-- Comment on 'Are you sure you are not trying to access an invalid index?' by 'Both are great for web development.'
(18, 19),-- Comment on 'Both are great for web development...' by 'If you are familiar with Java...'
(19, 20),-- Comment on 'If you are familiar with Java...' by 'Quantum computing could solve problems...'
(20, 21),-- Comment on 'Quantum computing...' by 'The potential of quantum computing is huge.'
(21, 23),-- Comment on 'The potential of quantum computing...' by 'AI taking over repetitive tasks...'
(22, 24),-- Comment on 'AI will definitely change the workforce...' by '5G will be a game-changer...'
(23, 25);-- Comment on 'AI taking over repetitive tasks...' by 'I’m excited about 5G...'

-- Insert mock moderation roles
INSERT INTO moderation (forum_id, username, role) VALUES
(1, 'Mutex', 'ROLE_MODERATOR'),
(2, 'Daniel', 'ROLE_ADMIN'),
(3, 'Mutex', 'ROLE_MODERATOR'),
(4, 'Daniel', 'ROLE_ADMIN');

COMMIT TRANSACTION;
