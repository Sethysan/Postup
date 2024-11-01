#!/bin/bash

# Ensure the environment (development or production) is passed as an argument
ENVIRONMENT=$1
if [ -z "$ENVIRONMENT" ]; then
  echo "Please specify the environment (development or production)"
  exit 1
fi

# Load environment variables from the corresponding .env file
if [ "$ENVIRONMENT" == "development" ]; then
  echo "Setting up for development environment"
  source .env.development
elif [ "$ENVIRONMENT" == "production" ]; then
  echo "Setting up for production environment"
  source .env.production
else
  echo "Invalid environment specified. Use 'development' or 'production'."
  exit 1
fi

# Ensure required environment variables are set
if [ -z "$POSTGRES_PASSWORD" ] || [ -z "$DATABASE_NAME" ] || [ -z "$DB_HOST" ] || [ -z "$DB_PORT" ] || [ -z "$POSTUP_OWNER_PASSWORD" ] || [ -z "$POSTUP_APP_USER_PASSWORD" ]; then
  echo "One or more required environment variables are missing."
  exit 1
fi

export PGPASSWORD=$POSTGRES_PASSWORD
BASEDIR=$(dirname $0)
DATABASE=$DATABASE_NAME

# Run SQL scripts
psql -U postgres -h $DB_HOST -p $DB_PORT -f "$BASEDIR/dropdb.sql" &&
psql -U postgres -h $DB_HOST -p $DB_PORT -d $DATABASE -f "$BASEDIR/schema.sql" &&
psql -U postgres -h $DB_HOST -p $DB_PORT -d $DATABASE -f "$BASEDIR/data.sql" || { echo "Error running SQL scripts."; exit 1; }

# Replace placeholders and execute the modified SQL
envsubst < "$BASEDIR/user.sql" | sed -e "s/PLACEHOLDER_OWNER_PASSWORD/$POSTUP_OWNER_PASSWORD/" \
                                     -e "s/PLACEHOLDER_APP_USER_PASSWORD/$POSTUP_APP_USER_PASSWORD/" \
| psql -U postgres -h $DB_HOST -p $DB_PORT -d $DATABASE || { echo "Error executing user.sql."; exit 1; }

echo "Database setup completed successfully."
