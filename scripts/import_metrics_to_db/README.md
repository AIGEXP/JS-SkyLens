# Hub Daily Metrics Import Script

This script imports hub daily metrics data from a CSV file into the PostgreSQL database.

## Prerequisites

1. Install Python dependencies:
   ```bash
   pip install -r requirements.txt
   ```

2. Ensure PostgreSQL database is running and accessible.

## Usage

1. Update database configuration in `import_hub_metrics.py` if needed:
   ```python
   db_config = {
       'host': 'localhost',
       'port': 5432,
       'database': 'skylens',
       'user': 'postgres',
       'password': 'postgres'
   }
   ```

2. Run the script:
   ```bash
   python import_hub_metrics.py
   ```

3. Enter the CSV file path when prompted.

## CSV Structure

The CSV file must have the following columns:
- ID
- Country
- Hub_ID
- Hub_SID (UUID format preferred)
- Hub_Name
- Service_Provider
- Service_Provider_SID (UUID format preferred)
- 3PL
- Day
- Week
- Month
- Year
- Pre_paid (TRUE/FALSE)
- Movement_Type (DD/PUS)
- Nr_packages_delivered
- Nr_packages_closed
- Nr_packages_received
- Nr_packages_lost_at_Hub
- Nr_packages_no_attempt
- Nr_packages_no_attempt_1D
- Nr_packages_no_attempt_2D
- Nr_packages_no_attempt_3D
- Nr_packages_no_attempt_4D
- Nr_packages_no_attempt_+4D

## Features

- **UUID Handling**: Converts Hub_SID and Service_Provider_SID to UUIDs. Generates UUIDs if invalid.
- **Date Parsing**: Combines Day, Month, Year columns into proper date format.
- **Payment Type**: Converts Pre_paid TRUE/FALSE to PRE/POST enum values.
- **Movement Type**: Validates Movement_Type is either DD or PUS.
- **Upsert Logic**: Uses ON CONFLICT to update existing records instead of failing.
- **Error Handling**: Skips invalid rows with error messages.
- **Progress Tracking**: Shows progress every 1000 rows.

## Database Schema

The script maps CSV columns to the `hub_daily_metrics` table:

| CSV Column | Database Column | Notes |
|------------|-----------------|-------|
| Hub_SID | hub_sid | UUID |
| Service_Provider_SID | service_provider_sid | UUID |
| Day, Month, Year | day | Combined to date |
| Pre_paid | payment_type | TRUE→PRE, FALSE→POST |
| Movement_Type | movement_type | Must be DD or PUS |
| Nr_packages_delivered | packages_delivered | |
| Nr_packages_closed | packages_closed | |
| Nr_packages_received | packages_received | |
| Nr_packages_lost_at_Hub | packages_lost_at_hub | |
| Nr_packages_no_attempt_1D | packages_no_attempts_one_day | |
| Nr_packages_no_attempt_2D | packages_no_attempts_two_days | |
| Nr_packages_no_attempt_3D | packages_no_attempts_three_days | |
| Nr_packages_no_attempt_+4D | packages_no_attempts_over_three_days | |
