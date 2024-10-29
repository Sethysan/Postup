#!/bin/bash
#export PGPASSWORD='postgres1'
#BASEDIR=$(dirname $0)
#DATABASE=final_capstone
#psql -U postgres -f "$BASEDIR/dropdb.sql" &&
#createdb -U postgres $DATABASE &&
#psql -U postgres -d $DATABASE -f "$BASEDIR/schema.sql" &&
#psql -U postgres -d $DATABASE -f "$BASEDIR/data.sql" &&
#psql -U postgres -d $DATABASE -f "$BASEDIR/user.sql"

#!/bin/bash
export PGPASSWORD='ZBcPejPJFUyqqWVwbNWMZBqDZcqAHYzU'
BASEDIR=$(dirname $0)
DATABASE=railway
psql -U postgres -h junction.proxy.rlwy.net -p 36725 -f "$BASEDIR/dropdb.sql" &&
psql -U postgres -h junction.proxy.rlwy.net -p 36725 -d $DATABASE -f "$BASEDIR/schema.sql" &&
psql -U postgres -h junction.proxy.rlwy.net -p 36725 -d $DATABASE -f "$BASEDIR/data.sql" &&
psql -U postgres -h junction.proxy.rlwy.net -p 36725 -d $DATABASE -f "$BASEDIR/user.sql"