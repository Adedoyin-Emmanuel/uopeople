# Step 1: Define the file paths
input_file = 'original_dict.txt'
output_file = 'inverted_dict.txt'

# Step 2: Initialize an empty dictionary
original_dict = {}

# Step 3: Read the dictionary from the input file
try:
    with open(input_file, 'r') as infile:
        for line in infile:
            # Strip any whitespace and split the line into key and value
            key, value = line.strip().split(': ')
            original_dict[key] = value
except FileNotFoundError:
    print(f"Error: The file {input_file} was not found.")
except Exception as e:
    print(f"An unexpected error occurred: {e}")

# Step 4: Invert the dictionary
inverted_dict = {}
for key, value in original_dict.items():
    if value in inverted_dict:
        inverted_dict[value].append(key)
    else:
        inverted_dict[value] = [key]

# Convert lists back to strings
for key in inverted_dict:
    inverted_dict[key] = ', '.join(inverted_dict[key])

# Step 5: Write the inverted dictionary to the output file
try:
    with open(output_file, 'w') as outfile:
        for key, value in inverted_dict.items():
            outfile.write(f"{key}: {value}\n")
except Exception as e:
    print(f"An error occurred while writing to the file: {e}")
