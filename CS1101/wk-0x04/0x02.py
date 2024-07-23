# Define the name
name = "Adedoyin Emmanuel Adeniyi"

# 1. Display n characters from the left (Accept n as input from the user)
n = int(input("Enter the number of characters to display from the left: "))
print("First", n, "characters from the left are:", name[:n])

# 2. Count the number of vowels in the name
vowels = "aeiouAEIOU"
vowel_count = sum(1 for char in name if char in vowels)
print("Number of vowels in the name:", vowel_count)

# 3. Reverse the name
reversed_name = name[::-1]
print("Reversed name:", reversed_name)
