"""
 Construct a function that takes an argument. 
 Give the function parameter a unique name. 
 Show what happens when you try to use that parameter name outside the function. 
 Explain the results.
"""


def introduce(name):
    print(f"Hello world, my name is {name}")


# Trying to use the introduce function parameter outside the function.
print(name)