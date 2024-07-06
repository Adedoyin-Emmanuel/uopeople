"""
    You are developing a program that performs a division operation
    on two numbers provided by the user. However, there is a situation
    where a runtime error can occur due to a division by zero. 
    To help junior developers learn about error handling in expressions and conditions,
    you want to create a program deliberately containing this error and guide them in diagnosing and fixing it.


    Instructions: 
        1. Create a Python program that prompts the user to enter two numbers. 
        2. Implement a division operation on the entered numbers. 
        3. Introduce a condition that raises a runtime error if the second number is zero. 
        4. Provide an error message that clearly indicates the cause of the error. 
        5. Guide the junior developers in identifying the error message and implementing error handling techniques to handle the division by zero scenario.

"""

def divide_numbers():
    try:
        num1 = float(input("Enter first number: "))
        num2 = float(input("Enter second number: "))

        result = num1 / num2
        print("Result:", result)

    except ValueError:
        print("Error: Invalid input. Please enter a valid number.")

    except ZeroDivisionError as e:
        print("Error:", str(e))



if __name__ == "__main__":
    divide_numbers()