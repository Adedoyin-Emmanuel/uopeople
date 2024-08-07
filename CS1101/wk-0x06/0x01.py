"""
    Using zip with Tuples
"""


# Example: Using zip to combine two lists into a tuple
names = ["Temi", "Emma", "Dara"]
ages = [20, 22, 19]

for name, age in zip(names, ages):
    print(f"{name} is {age} years old.")






# Example: Using enumerate to loop over a list with index
fruits = ["apple", "banana", "cherry"]

for index, fruit in enumerate(fruits):
    print(f"Fruit {index + 1}: {fruit}")




# Example: Using items to loop over a dictionary
student_scores = {"Alice": 85, "Bob": 92, "Charlie": 78}

for student, score in student_scores.items():
    print(f"{student} scored {score}")

