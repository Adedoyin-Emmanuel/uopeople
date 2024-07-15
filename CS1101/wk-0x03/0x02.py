"""
    Post condition violation example
"""


def square(n):
    result = n  # Incorrect: should be n * n
    return result

# Example usage and output
print(square(4))  # Expected: 16, Actual: 4
print(square(5))  # Expected: 25, Actual: 5