
"""
    Function 1
"""
def any_lowercase1(s):
    for c in s:
        if c.islower():
            return True
        else:
            return False


print(any_lowercase1("abc"))  # Output: True
print(any_lowercase1("Abc"))  # Output: False
print(any_lowercase1("aBc"))  # Output: True




"""
    Function 2
"""
def any_lowercase2(s):
    for c in s:
        if 'c'.islower():
            return 'True'
        else:
            return 'False'



print(any_lowercase2("abc"))  # Output: True
print(any_lowercase2("ABC"))  # Output: True
print(any_lowercase2("123"))  # Output: True
