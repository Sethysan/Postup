-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER PostUp_owner
WITH PASSWORD  'PLACEHOLDER_OWNER_PASSWORD';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO PostUp_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO PostUp_owner;

CREATE USER postgres
WITH PASSWORD  'PLACEHOLDER_POSTGRES_PASSWORD';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO postgres;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO postgres;

CREATE USER PostUp_app_user
WITH PASSWORD 'PLACEHOLDER_APP_USER_PASSWORD';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO PostUp_app_user;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO PostUp_app_user;
