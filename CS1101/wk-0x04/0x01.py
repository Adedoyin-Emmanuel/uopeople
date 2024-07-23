def any_lowercase1(s):
    for c in s:
        if c.islower():
            return True
        else:
            return False


print(any_lowercase1("abc"))  # Output: True
print(any_lowercase1("Abc"))  # Output: False
print(any_lowercase1("aBc"))  # Output: True


