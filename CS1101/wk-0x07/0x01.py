
nonExistentFile = 'non_existent_file.txt'
try:
    # Attempting to open a non-existent file
    with open(nonExistentFile, 'r') as file:
        content = file.read()
        print(content)
except FileNotFoundError as e:
    # Handling the specific file not found error
    print(f"Error: {e}")
