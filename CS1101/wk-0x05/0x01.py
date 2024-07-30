# Step 1: We Initialize the employee list
employee_list = ["John Doe", "Jane Smith", "Alice Johnson", "Mike Brown", "Emily Davis", 
                 "James Wilson", "Laura White", "Tom Clark", "Anna Hall", "Robert Lewis"]

# Step 2: Split the list into two sub-lists
subList1 = employee_list[:5]
subList2 = employee_list[5:]

# Step 3: Add a new employee to subList2
subList2.append("Kriti Brown")

# Step 4: Remove the second employee from subList1
del subList1[1]

# Step 5: Merge the sub-lists
merged_list = subList1 + subList2

# Assume the initial salary list for 10 employees
salary_list = [50000, 60000, 55000, 65000, 70000, 52000, 58000, 62000, 67000, 73000]

# Step 6: Increase each salary by 4%
updated_salary_list = [salary * 1.04 for salary in salary_list]

# Sort the updated salary list
sorted_salary_list = sorted(updated_salary_list)

# Get the top 3 salaries
top_3_salaries = sorted_salary_list[-3:]

# Output the results
print("Sublist 1:", subList1)
print("Sublist 2:", subList2)
print("Merged List:", merged_list)
print("Updated Salary List:", updated_salary_list)
print("Sorted Salary List:", sorted_salary_list)
print("Top 3 Salaries:", top_3_salaries)

