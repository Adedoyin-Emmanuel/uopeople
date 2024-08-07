"""
    Sample script for week-7 programming assignment.
    I will write the .docx file once I'm done with the script.
"""


# Original dictionary with students and their courses
students_courses = {
    'Stud1': ['CS1101', 'CS2402', 'CS2001'],
    'Stud2': ['CS2402', 'CS2001', 'CS1102']
}

print("Original Dictionary:")
print(students_courses)



# Function to invert the dictionary
def invert_dictionary(original_dict):
    inverted_dict = {}
    for student, courses in original_dict.items():
        for course in courses:
            if course not in inverted_dict:
                inverted_dict[course] = []
            inverted_dict[course].append(student)
    return inverted_dict

inverted_courses = invert_dictionary(students_courses)

print("\nInverted Dictionary:")
print(inverted_courses)
