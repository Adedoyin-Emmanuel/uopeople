def factorial(n):
    if n == 0:
        return 1
    else:
        return n * factorial(n - 1)



# Test cases
print(factorial(0))  # Expected output: 1
print(factorial(5))  # Expected output: 120
print(factorial(7))  # Expected output: 5040
