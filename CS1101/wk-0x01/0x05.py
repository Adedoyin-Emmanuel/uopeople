"""
The circumference of a circle is calculated by 2πr, where π = 3.14159
(rounded to five decimal places). Write a function called print_circum
that takes an argument for the circle’s radius and prints the circle's circumference. 
"""



def print_circum(radius):
    PI =  3.14159 
    circumference = 2 * PI  * radius
    print(circumference)



print_circum(4)

print_circum(5)

print_circum(28)