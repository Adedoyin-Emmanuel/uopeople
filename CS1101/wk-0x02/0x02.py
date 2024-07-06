"""
    The following is the countdown function copied from Section 5.8 of your textbook. 
 
    def countdown(n): 
        if n <= 0: 
            print('Blastoff!') 
        else: 
            print(n) 
            countdown(n-1) 

    Write a new recursive function countup that expects a negative argument and counts “up” from that number. Output from running the function should look something like this: 

    >>> countup(-3) 
    -3 
    -2 
    -1 
    Blastoff! 
"""



def counttup(n):
    if(n  >= 0 ):
        print("Blastoff!")
    else:
        print(n)
        counttup(n  + 1)


counttup(-10)