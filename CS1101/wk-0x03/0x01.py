"""
    Precondition violation example
"""

def divide(a, b):
    if b == 0:
        raise ValueError("Denominator cannot be zero")
    return a / b

# Example usage and output
try:
    result = divide(10, 0)
except ValueError as e:
    print(e)


