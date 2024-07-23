
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



"""
    Function 3
"""

def any_lowercase3(s):
    for c in s:
        flag = c.islower()
    return flag


print(any_lowercase3("abc"))  # Output: True
print(any_lowercase3("ABC"))  # Output: False
print(any_lowercase3("Abc"))  # Output: True



"""
    Function 4
"""

def any_lowercase4(s):
    flag = False
    for c in s:
        flag = flag or c.islower()
    return flag


print(any_lowercase4("abc"))  # Output: True
print(any_lowercase4("ABC"))  # Output: False
print(any_lowercase4("Abc"))  # Output: True




"""
    Function 5
"""


def any_lowercase5(s):
    for c in s:
        if not c.islower():
            return False
    return True




print(any_lowercase5("abc"))  # Output: True
print(any_lowercase5("ABC"))  # Output: False
print(any_lowercase5("Abc"))  # Output: False
