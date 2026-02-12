#!/usr/bin/env python3
import csv
import psycopg2
from psycopg2 import sql
from psycopg2.extras import UUID
from datetime import datetime
import uuid

def import_hub_metrics(csv_file_path, db_config):
    """
    Import hub daily metrics from CSV to PostgreSQL database.
    
    Args:
        csv_file_path: Path to the CSV file
        db_config: Database connection configuration
    """
    
    conn = None
    try:
        # Connect to database
        conn = psycopg2.connect(**db_config)
        cursor = conn.cursor()
        
        # Prepare insert statement
        insert_query = sql.SQL("""
            INSERT INTO hub_daily_metrics (
                hub_sid, service_provider_sid, day, payment_type, movement_type,
                packages_delivered, packages_closed, packages_received, packages_lost_at_hub,
                packages_no_attempts_one_day, packages_no_attempts_two_days,
                packages_no_attempts_three_days, packages_no_attempts_over_three_days
            ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
            ON CONFLICT (service_provider_sid, hub_sid, day, payment_type, movement_type) 
            DO UPDATE SET
                packages_delivered = EXCLUDED.packages_delivered,
                packages_closed = EXCLUDED.packages_closed,
                packages_received = EXCLUDED.packages_received,
                packages_lost_at_hub = EXCLUDED.packages_lost_at_hub,
                packages_no_attempts_one_day = EXCLUDED.packages_no_attempts_one_day,
                packages_no_attempts_two_days = EXCLUDED.packages_no_attempts_two_days,
                packages_no_attempts_three_days = EXCLUDED.packages_no_attempts_three_days,
                packages_no_attempts_over_three_days = EXCLUDED.packages_no_attempts_over_three_days
        """)
        
        # Read and process CSV
        with open(csv_file_path, 'r', encoding='utf-8') as file:
            reader = csv.DictReader(file)
            
            for row_num, row in enumerate(reader, 1):
                try:
                    # Parse date
                    day_str = f"{row['Year']}-{row['Month']:02d}-{row['Day']:02d}"
                    day = datetime.strptime(day_str, '%Y-%m-%d').date()
                    
                    # Parse payment type
                    payment_type = 'PRE' if row['Pre_paid'].strip().upper() == 'TRUE' else 'POST'
                    
                    # Parse movement type
                    movement_type = row['Movement_Type'].strip().upper()
                    if movement_type not in ['DD', 'PUS']:
                        print(f"Warning: Invalid movement_type '{movement_type}' in row {row_num}, skipping")
                        continue
                    
                    # Convert UUIDs (handle both string UUID and generate if needed)
                    try:
                        hub_sid = UUID(row['Hub_SID'].strip())
                    except:
                        print(f"Warning: Invalid Hub_SID in row {row_num}, generating UUID")
                        hub_sid = uuid.uuid4()
                    
                    try:
                        service_provider_sid = UUID(row['Service_Provider_SID'].strip())
                    except:
                        print(f"Warning: Invalid Service_Provider_SID in row {row_num}, generating UUID")
                        service_provider_sid = uuid.uuid4()
                    
                    # Parse numeric values (handle empty strings)
                    def safe_int(value, default=0):
                        return int(value) if value and value.strip() else default
                    
                    # Execute insert
                    cursor.execute(insert_query, (
                        hub_sid,
                        service_provider_sid,
                        day,
                        payment_type,
                        movement_type,
                        safe_int(row['Nr_packages_delivered']),
                        safe_int(row['Nr_packages_closed']),
                        safe_int(row['Nr_packages_received']),
                        safe_int(row['Nr_packages_lost_at_Hub']),
                        safe_int(row['Nr_packages_no_attempt_1D']),
                        safe_int(row['Nr_packages_no_attempt_2D']),
                        safe_int(row['Nr_packages_no_attempt_3D']),
                        safe_int(row['Nr_packages_no_attempt_+4D'])
                    ))
                    
                    if row_num % 1000 == 0:
                        print(f"Processed {row_num} rows...")
                        
                except Exception as e:
                    print(f"Error processing row {row_num}: {e}")
                    continue
        
        # Commit transaction
        conn.commit()
        print(f"Successfully imported data from {csv_file_path}")
        
    except Exception as e:
        if conn:
            conn.rollback()
        print(f"Error: {e}")
        raise
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    # Database configuration - modify as needed
    db_config = {
        'host': 'localhost',
        'port': 5432,
        'database': 'skylens',
        'user': 'postgres',
        'password': 'postgres'
    }
    
    # CSV file path
    csv_file = input("Enter CSV file path: ").strip()
    
    # Import data
    import_hub_metrics(csv_file, db_config)
