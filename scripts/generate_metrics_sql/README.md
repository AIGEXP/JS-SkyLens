# SQL Queries Generator for Hub Daily Metrics

This script generates SQL INSERT queries from a CSV file for the hub_daily_metrics table without requiring database connection.

## Usage

1. Run the script:
   ```bash
   python generate_sql_queries.py
   ```

2. Enter the CSV file path when prompted.

3. Enter the output SQL file path (press Enter for default: `hub_metrics_inserts.sql`).

## Features

- **No Database Connection**: Generates SQL queries offline
- **UUID Handling**: Converts Hub_SID and Service_Provider_SID to UUIDs (generates if invalid)
- **Date Parsing**: Combines Day/Month/Year into proper date format
- **Enum Mapping**: Pre_paid → PRE/POST, Movement_Type → DD/PUS
- **Upsert Logic**: Uses ON CONFLICT to update existing records
- **Error Handling**: Skips invalid rows with detailed error messages
- **Progress Tracking**: Shows progress every 1000 rows
- **Formatted Output**: Well-formatted SQL with comments

## Output Format

The generated SQL file contains:
- Header with metadata
- Numbered queries with comments
- Properly formatted INSERT statements with ON CONFLICT handling

## Example Output

```sql
-- Generated SQL queries for hub_daily_metrics table
-- Generated from: data.csv
-- Total queries: 1500

-- Query 1
INSERT INTO hub_daily_metrics (
    hub_sid, service_provider_sid, day, payment_type, movement_type,
    packages_delivered, packages_closed, packages_received, packages_lost_at_hub,
    packages_no_attempts_one_day, packages_no_attempts_two_days,
    packages_no_attempts_three_days, packages_no_attempts_over_three_days
) VALUES (
    '123e4567-e89b-12d3-a456-426614174000', '987e6543-e21b-45d6-b789-123456789abc', '2024-01-15', 'PRE', 'DD',
    150, 145, 160, 2, 5, 3, 1, 0
) ON CONFLICT (service_provider_sid, hub_sid, day, payment_type, movement_type) 
DO UPDATE SET
    packages_delivered = EXCLUDED.packages_delivered,
    packages_closed = EXCLUDED.packages_closed,
    packages_received = EXCLUDED.packages_received,
    packages_lost_at_hub = EXCLUDED.packages_lost_at_hub,
    packages_no_attempts_one_day = EXCLUDED.packages_no_attempts_one_day,
    packages_no_attempts_two_days = EXCLUDED.packages_no_attempts_two_days,
    packages_no_attempts_three_days = EXCLUDED.packages_no_attempts_three_days,
    packages_no_attempts_over_three_days = EXCLUDED.packages_no_attempts_over_three_days;
```

## Running the Generated SQL

After generating the SQL file, you can execute it using:
```bash
psql -h localhost -U postgres -d skylens -f hub_metrics_inserts.sql
```

Or any other PostgreSQL client of your choice.
