"""
    Chained conditionals vs Nested conditionals
"""

# Chained conditionals

temperature = 34

if(temperature > 35) :
    print("It is very hot")
elif(temperature >= 25 and temperature <= 35):
    print("It is warm")
elif(temperature >= 15 and temperature < 25):
    print("It is cool")
else:
    print("It is cold")


# Example of a nested conditional

score = 85

if score >= 50:
    if score >= 75:
        if score >= 90:
            print("Grade: A")
        else:
            print("Grade: B")
    else:
        print("Grade: C")
else:
    print("Grade: F")


# improve nested conditionals


# nested conditionals
age = 18
income = 120000

if age > 18:
    if income > 60000:
        print("You are eligible for a loan")
    else:
        print("You are not eligible for a loan")
else:
    print("You are not eligible due to age")



# simplified

if(age > 18 and income > 60000):
    print("You are eligible for a loan")
else:
    print("You are not eligible for a loan")