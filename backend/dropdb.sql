-- **************************************************************
-- This script destroys the database and associated users
-- **************************************************************

-- The following line terminates any active connections to the database so that it can be destroyed
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
--WHERE datname = 'final_capstone';
--DROP DATABASE final_capstone;

WHERE datname = 'PostUp';
DROP DATABASE PostUp;

DROP USER PostUp_owner;
DROP USER PostUp_app_user;
