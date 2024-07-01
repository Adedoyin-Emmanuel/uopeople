"""
  Show what happens when a variable defined outside a function
  has the same name as a local variable inside a function. 
  Explain what happens to the value of each variable as the program runs.
"""


# Define a global variable
name = "Adedoyin Emmanuel Adeniyi"

def greet():
    # Define a local variable with the same name as the global variable
    name = "Adedoyin Emmanuel"
    print(name)


greet()
print(name)