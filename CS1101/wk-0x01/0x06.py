"""
Develop a catalog for a company. Assume that this company sells three different Items.
The seller can sell individual items or a combination of any two items.
A gift pack is a special combination that contains all three items.
Here are some special considerations:  

A. If a customer purchases individual items, he does not receive any discount.  
B. If a customer purchases a combo pack with two unique items, he gets a 10% discount.  
C. If the customer purchases a gift pack, he gets a 25% discount. 

Write a function for the above scenario. Perform the calculations in code wherever applicable.
The function should be your own creation, not copied from any other source.
The final output should look like: 
"""


# Let's define the items price list in a dict

item1 = {
     "item1" : 200.0
}

item2 = {
     "item2" : 400.0
}

item3 = {
     "item3" : 600.0
}


# define combo products
combo_items = {
    "item1": 500.0,
    "item2": 40.0,
}

combo_items2 = {
     
     "item2": 300.0,
     "item3": 600.0,
}

combo_items3 = {
     "item1" : 600.0,
     "item3": 120.0
}

# define gift pack
gift_pack = {
    "item1": 300.0,
    "item2": 300.0,
    "item3": 300.0,
}

total_items = len(item1) + len(item2) + len(item3) + len(combo_items) + len(gift_pack)


def calculate_price(items):
     """
        Calculate the total price based on the items purchased.
        
        Args:
        items (list): A list of items purchased. Each item is represented as a string ('item1', 'item2', 'item3').

        Returns:
        float: The total price after applying the appropriate discounts.
    """
     unique_items = set(items)
     total_price = sum(items[item] for item in items)


     if(len(unique_items) == 3):
          total_price *= 0.75 # applying the 25% discount
     elif(len(unique_items) == 2):
          total_price *= 0.90 # applying the 10% discount
     
     return total_price



print("Output\n")

print("Online Store\n")

print("-" * 25)


print(f"Product({total_items}) \t \t \t Price")

print(f"Item 1 \t \t \t \t ${calculate_price(item1)}")

print(f"Item 2 \t \t \t \t ${calculate_price(item2)}")

print(f"Item 3 \t \t \t \t ${calculate_price(item3)}")

print(f"Combo 1 (Item 1 + 2) \t \t ${calculate_price(combo_items)}")

print(f"Combo 2 (Item 2 + 3) \t \t ${calculate_price(combo_items2)}")

print(f"Combo 3 (Item 1 + 3) \t \t ${calculate_price(combo_items3)}")

print(f"Combo 4 (Item 1 + 2 + 3)\t  ${calculate_price(gift_pack)}")

print("\n")

print("-" * 25)

print("\n")

print("For delivery contact 98764678899")




