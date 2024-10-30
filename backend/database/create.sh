
#!/bin/bash
export PGPASSWORD=$POSTGRES_PASSWORD
BASEDIR=$(dirname $0)
DATABASE=$DATABASE_NAME

psql -U postgres -h $DB_HOST -p $DB_PORT -f "$BASEDIR/dropdb.sql" &&
psql -U postgres -h $DB_HOST -p $DB_PORT -d $DATABASE -f "$BASEDIR/schema.sql" &&
psql -U postgres -h $DB_HOST -p $DB_PORT -d $DATABASE -f "$BASEDIR/data.sql" &&

# Replace placeholders with environment variables and execute the modified SQL
envsubst < "$BASEDIR/user.sql" | sed -e "s/PLACEHOLDER_OWNER_PASSWORD/$POSTUP_OWNER_PASSWORD/" \
                                     -e "s/PLACEHOLDER_APP_USER_PASSWORD/$POSTUP_APP_USER_PASSWORD/" \
| psql -U postgres -h $DB_HOST -p $DB_PORT -d $DATABASE
