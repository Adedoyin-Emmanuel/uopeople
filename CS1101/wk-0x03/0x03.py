"""
    Return value misuse
"""

def add(a, b):
    return a + b

# Example usage and output
result = add(2, 3)
print(result)  # Expected: 5, Actual: 5

# Misuse of return value
total = result * 2  # Misunderstood the purpose
print(total)  # Expected: 10 if doubling the result is intended
