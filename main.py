# file: main.py

def show_main_menu():
    while True:
        print("\n" + "-" * 40)
        print("University Library Management System")
        print("-" * 40)
        print("1. Login as Student")
        print("2. Login as Employee")
        print("3. Login as Admin")
        print("4. Login as Guest")
        print("5. Exit")
        print("-" * 40)

        choice = input("Choose an option: ")

        if choice == "1":
            student_menu()
        elif choice == "2":
            employee_menu()
        elif choice == "3":
            admin_menu()
        elif choice == "4":
            guest_menu()
        elif choice == "5":
            print("Exiting system...")
            break
        else:
            print("Invalid input! Please try again.")


# ---------------- Student Menu ----------------
def student_menu():
    while True:
        print("\n--- Student Menu ---")
        print("1. Register")
        print("2. Login")
        print("3. Search Book")
        print("4. Request Book Loan")
        print("5. Return to Main Menu")

        choice = input("Choose an option: ")

        if choice == "5":
            break
        else:
            print("This feature is not available in the current version.")


# ---------------- Guest Menu ----------------
def guest_menu():
    while True:
        print("\n--- Guest Menu ---")
        print("1. View number of registered students")
        print("2. Search book by name")
        print("3. View general statistics")
        print("4. Return to Main Menu")

        choice = input("Choose an option: ")

        if choice == "4":
            break
        else:
            print("This feature is not available in the current version.")


# ---------------- Employee Menu ----------------
def employee_menu():
    while True:
        print("\n--- Employee Menu ---")
        print("1. Login")
        print("2. Change Password")
        print("3. Add Book Information")
        print("4. Search and Edit Book Information")
        print("5. Review and Approve Loan Requests")
        print("6. View Student Loan History")
        print("7. Activate/Deactivate Student")
        print("8. Register Book Return")
        print("9. Return to Main Menu")

        choice = input("Choose an option: ")

        if choice == "9":
            break
        else:
            print("This feature is not available in the current version.")


# ---------------- Admin Menu ----------------
def admin_menu():
    while True:
        print("\n--- Admin Menu ---")
        print("1. Add New Employee")
        print("2. View Employee Performance")
        print("3. View Book Loan Statistics")
        print("4. View Student Statistics")
        print("5. Return to Main Menu")

        choice = input("Choose an option: ")

        if choice == "5":
            break
        else:
            print("This feature is not available in the current version.")


# Run the program
if __name__ == "__main__":
    show_main_menu()
