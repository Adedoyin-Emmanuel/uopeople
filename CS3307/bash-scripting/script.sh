#!/bin/bash
# Script: report_script.sh
# Purpose: Downloads a public CSV and organizes it to demonstrate the workflow.

# Download the sales report file
# Using a public link ensures curl gets the actual file and not a login page
curl -L -o sales_data.csv https://raw.githubusercontent.com/dataprofessor/data/master/delaney_solubility_with_descriptors.csv

# Create the storage folder
# The -p flag creates the folder only if it is missing
mkdir -p ./sales_reports

# Move the file to the new location
# We use the name defined in the -o flag above
mv sales_data.csv ./sales_reports/

# Notify the user
echo "Process complete. The file is now in the sales_reports folder."
