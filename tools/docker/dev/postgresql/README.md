# PostgreSQL image
This image includes SQL scripts to create the necessary databases to execute local environment:
* [create_database.sql](conf%2Fcreate_database.sql)
* [set_wal_level.sql](conf%2Fset_wal_level.sql)
 
And a script to truncate/clean all tables in the public schema:
* [truncate_database_tables.sql](scripts%2Ftruncate_database_tables.sql)
This script is manly used by acceptance tests at [reset-db.sh](..%2F..%2F..%2F..%2Fapp%2Fsrc%2Ftest-acceptance%2Fresources%2Fdb%2Freset-db.sh)
