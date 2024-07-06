"""
    Write a Python program that gets a number using keyboard input.
    (Remember to use input for Python 3 but raw_input for Python 2.) 

    If the number is positive, the program should call countdown.
    If the number is negative, the program should call countup. 
    Choose for yourself which function to call(countdown or countup) for input of zero. 
"""

# Define utils functions

def countdown(n):
    if(n <= 0):
        print("Blastoff!")
    else:
        print(n)
        countdown(n - 1)


def countup(n):
    if(n >= 0):
        print("Blastoff!")
    else:
        print(n)
        countup(n + 1)


# Get input number

try:
    number = int(input("Enter a number: "))

    if(number > 0):
        countdown(number)
    elif(number < 0):
        countup(number)
    elif(number == 0): 
        countdown(number)

except(ValueError):
    print("Invalid input. Please enter a number.")
    exit()