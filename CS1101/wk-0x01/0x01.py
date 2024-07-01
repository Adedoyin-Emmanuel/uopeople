"""
    Define a function that takes an argument. Call the function. 
    Identify what code is the argument and what code is the parameter.
"""



def introduce(name):
    # The name is the parameter which is a placeholder for the actual value
    print(f"Hello, my name is {name}. I'm schooling at UoPeople.")



"""
  Call your function from Example 1 three times with different kinds of arguments: 
  a value, a variable, and an expression. Identify which kind of argument is which. 
"""

# A value argument
introduce("Adedoyin Emmanuel") # Calling the function with a value argument



# A variable argument
myName = "Adedoyin Emmanuel"

introduce(myName) # Calling the function with a variable argument



# An expression argument
firstName = "Adedoyin"
lastName = "Emmanuel"

introduce(firstName + " " + lastName) # Calling the function with an expression argument