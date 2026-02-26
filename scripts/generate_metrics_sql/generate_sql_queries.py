#!/usr/bin/env python3
import csv
from datetime import datetime
import uuid

def generate_sql_queries(csv_file_path, output_file_path):
    """
    Generate SQL INSERT queries from CSV file for hub_daily_metrics table.
    Splits output into separate files by country.
    
    Args:
        csv_file_path: Path to the input CSV file
        output_file_path: Base path for the output SQL files (will add country)
    """
    
    # Dictionary to store queries by country
    country_queries = {}
    
    try:
        # Read and process CSV
        with open(csv_file_path, 'r', encoding='utf-8') as file:
            reader = csv.DictReader(file)
            
            for row_num, row in enumerate(reader, 1):
                try:
                    # Get country for this row
                    country = row['Country'].strip() if 'Country' in row and row['Country'].strip() else 'UNKNOWN'
                    
                    # Initialize country data structures if not exists
                    if country not in country_queries:
                        country_queries[country] = []
                    
                    # Parse date - handle both separate Day/Month/Year and combined date formats
                    print(f"Row {row_num} keys: {list(row.keys())}")
                    if 'Day' in row and len(row['Day'].strip()) == 8:  # Format: YYYYMMDD
                        day_date = datetime.strptime(row['Day'].strip(), '%Y%m%d').date()
                    elif 'Day' in row and 'Month' in row and 'Year' in row:  # Format: separate Day, Month, Year
                        day_str = f"{row['Year']}-{row['Month']:02d}-{row['Day']:02d}"
                        day_date = datetime.strptime(day_str, '%Y-%m-%d').date()
                    else:
                        print(f"Error: Required date columns not found in row {row_num}")
                        print(f"Available columns: {list(row.keys())}")
                        if 'Day' in row:
                            print(f"Day value: '{row['Day']}'")
                        continue
                    
                    # Parse payment type
                    payment_type = 'PRE' if row['Pre_paid'].strip() == '1' else 'POST'
                    
                    # Parse movement type
                    movement_type = row['Movement_Type'].strip().upper()
                    if movement_type not in ['DD', 'PUS']:
                        print(f"Warning: Invalid movement_type '{movement_type}' in row {row_num}, skipping")
                        continue
                    
                    # Convert UUIDs (handle both string UUID and generate if needed)
                    try:
                        hub_sid = uuid.UUID(row['Hub_SID'].strip())
                    except:
                        print(f"Warning: Invalid Hub_SID in row {row_num}, generating UUID")
                        hub_sid = uuid.uuid4()
                    
                    try:
                        service_provider_sid = uuid.UUID(row['Service_Provider_SID'].strip())
                    except:
                        print(f"Warning: Invalid Service_Provider_SID in row {row_num}, generating UUID")
                        service_provider_sid = uuid.uuid4()
                    
                    # Parse numeric values (handle empty strings)
                    def safe_int(value, default=0):
                        return int(value) if value and value.strip() else default
                    
                    # Generate INSERT query
                    query = f"""INSERT INTO hub_daily_metrics (
    hub_sid, service_provider_sid, day, payment_type, movement_type,
    packages_delivered, packages_closed, packages_received, packages_lost_at_hub,
    packages_no_attempts_one_day, packages_no_attempts_two_days,
    packages_no_attempts_three_days, packages_no_attempts_over_three_days
) VALUES (
    '{hub_sid}', '{service_provider_sid}', '{day_date}', '{payment_type}', '{movement_type}',
    {safe_int(row['Nr_packages_delivered'])}, {safe_int(row['Nr_packages_closed'])}, 
    {safe_int(row['Nr_packages_received'])}, {safe_int(row['Nr_packages_lost_at_Hub'])},
    {safe_int(row['Nr_packages_no_attempt_1D'])}, {safe_int(row['Nr_packages_no_attempt_2D'])},
    {safe_int(row['Nr_packages_no_attempt_3D'])}, {safe_int(row['Nr_packages_no_attempt_+4D'])}
) ON CONFLICT (service_provider_sid, hub_sid, day, payment_type, movement_type) 
DO NOTHING;"""
                    
                    country_queries[country].append(query)
                    
                    if row_num % 1000 == 0:
                        print(f"Processed {row_num} rows...")
                        
                except Exception as e:
                    print(f"Error processing row {row_num}: {e}")
                    continue
        
        # Write queries to files for each country
        total_files = 0
        for country, queries in country_queries.items():
            if queries:
                write_queries_to_file(queries, f"{output_file_path}_{country}.sql", csv_file_path, country)
                total_files += 1
        
        print(f"Successfully generated {total_files} SQL files from {csv_file_path}, split by country")
        
    except Exception as e:
        print(f"Error: {e}")
        raise

def write_queries_to_file(queries, filename, csv_file_path, country):
    """Write queries to a single SQL file"""
    with open(filename, 'w', encoding='utf-8') as output_file:
        output_file.write(f"-- Generated SQL queries for hub_daily_metrics table\n")
        output_file.write(f"-- Generated from: {csv_file_path}\n")
        output_file.write(f"-- Country: {country}\n")
        output_file.write(f"-- Total queries in this file: {len(queries)}\n")
        output_file.write("\n")
        
        for i, query in enumerate(queries, 1):
            output_file.write(f"-- Query {i}\n")
            output_file.write(query)
            output_file.write("\n\n")
    
    print(f"Created {filename} with {len(queries)} queries for country {country}")

if __name__ == "__main__":
    # Input and output file paths
    csv_file = input("Enter CSV file path: ").strip()
    sql_file = input("Enter output SQL file path (default: hub_metrics_inserts.sql): ").strip()
    
    if not sql_file:
        sql_file = "hub_metrics_inserts.sql"
    
    # Generate SQL queries
    generate_sql_queries(csv_file, sql_file)
