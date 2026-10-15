import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminManager {

    // 1. Register New Admin------------------------------------------------------------------------------------------------------
    public static void registerAdmin(Scanner scanner, Admin[] adminList){
        int currentCount = Admin.getAdminCount();

        if(currentCount < adminList.length){
            System.out.println("\n--------Register New Admin--------");

            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            
            String pw;
            while(true){
                    System.out.print("Enter Password: ");
                    pw = scanner.nextLine();
                    if(pw.length() < 6){
                        System.out.println("Error: Password too short! Please Enter atleast 6 characters!\n");
                    }
                    else
                        break;
                }

                double sal;
                while(true){
                    System.out.print("Enter Salary (Press ENTER for default): ");
                    String tempSal = scanner.nextLine();

                    if(tempSal.trim().isEmpty()){
                        sal = 1700.00;
                        break;
                    }
                    else{
                        try {
                            sal = Double.parseDouble(tempSal);
                            break;
                        } 
                        catch (NumberFormatException e) {
                            System.out.println("[!!!] Invalid number! Please enter valid values or \"d\" [!!!]");
                        }
                    }
                }

            adminList[currentCount] = new Admin(name, pw, sal);
            System.out.println("Admin registered! ID is: " + adminList[currentCount].getAdminID());
            Admin.saveToFile(adminList);
        }
    }

    // Login Into Existing account--------------------------------------------------------------------------------------------------
    public static Admin loginAdmin(Scanner scanner, Admin[] adminList){
        int attempts = 0;

        System.out.println("\n--------Admin Login--------");

        while(attempts < 3){
            System.out.print("Enter Admin ID: ");
            String inputID = scanner.nextLine();
            System.out.print("Enter Password: ");
            String inputPW = scanner.nextLine();

            for(int i = 0; i < Admin.getAdminCount(); i++){
                if(adminList[i] != null && adminList[i].checkID(inputID) && adminList[i].checkPassword(inputPW)){
                    System.out.println("\nLogin Successful! Welcome, " + adminList[i].getName());
                    return adminList[i];
                }
            }
            attempts++;
            System.out.println("Invalid ID or Password. " + (3 - attempts) + " attempts remaining.");
        }
        return null;
    }

    public static void showAdminInterface(Scanner scanner, Admin[] adminList, catalogue libraryCatalogue, LoanManager loanManager, FineManager fineManager){
        int choice = 4;
        
        do{
            System.out.println("\n===============================");
            System.out.println("        ADMIN INTERFACE        ");
            System.out.println("===============================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Back to role selection");
            System.out.print("Selection: ");
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 
            } 
            catch (InputMismatchException e) {
                System.out.println("[!!!] Error: Please enter a valid number (1-3). [!!!]\n");
                scanner.nextLine();
                choice = 4;
                continue;
            }
            
            switch (choice){
                case 1:
                    Admin user = AdminManager.loginAdmin(scanner, adminList);
                    if (user != null) {
                        AdminManager.showAdminMenu(scanner, adminList, user, libraryCatalogue, loanManager, fineManager);
                    }
                    break;
                    
                case 2:
                    
                    AdminManager.registerAdmin(scanner, adminList);
                    break;
                    
                case 0:
                    System.out.print("Terminating... ");
                    Admin.saveToFile(adminList);
                    break;
                    
                default:
                    System.out.println("Please select a valid type (0-2)\n");
                    break;
            }

        }while(choice != 0);
    }


    // MANAGE BOOKS------------------------------------------------------------------------------------------
    private static void manageBooksMenu(Scanner scanner, catalogue libraryCatalogue) {
        int bookChoice = -1;
        while (bookChoice != 0) {
            System.out.println("\n==============================");
            System.out.println("    BOOK MANAGEMENT SYSTEM    ");
            System.out.println("==============================");
            System.out.println("1. View All Books");
            System.out.println("2. Add New Book");
            System.out.println("3. Search Books");
            System.out.println("4. Update Book Info");
            System.out.println("5. Delete Book");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Selection: ");

            try {
                bookChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }

            switch (bookChoice) {
                case 1:
                    libraryCatalogue.viewBook(); //
                    break;
                case 2:
                    System.out.println("\n----- ADD BOOK -----");
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    
                    System.out.print("Book ID: ");
                    String id = scanner.nextLine();
                    
                    System.out.print("Author: ");
                    String author = scanner.nextLine();
                    
                    System.out.print("Category: ");
                    String category = scanner.nextLine();
                    
                    System.out.print("Publisher: ");
                    String publisher = scanner.nextLine();
                    
                    System.out.print("Year publish: ");
                    int publisherYear = scanner.nextInt();
                    scanner.nextLine();
                    
                    Book newBook = new Book(title, id, author, category, publisher, publisherYear);
                    libraryCatalogue.addBook(newBook);
                    
                    break;
                case 3:
                    System.out.print("Enter keyword to search: ");
                    libraryCatalogue.searchBooks(scanner.nextLine()); //
                    break;

                case 4:
                    libraryCatalogue.viewBook();
                    System.out.print("\nEnter Book ID to update: ");
                    String updateID = scanner.nextLine();
                    
                    Book book = libraryCatalogue.findBookID(updateID);
                    if (book == null) {
                        System.out.println("Book not found!");
                    } else {
                        System.out.println("\nCurrent Book Details: ");
                        book.displayInfo();
                        
                        System.out.println("\nEnter the new details (press Enter to skip)");
                        System.out.print("New Title (" + book.getTitle() + "): ");
                        String newTitle = scanner.nextLine();
                        
                        System.out.print("New Author (" + book.getAuthor() + "): ");
                        String newAuthor = scanner.nextLine();
                        
                        System.out.print("New Category (" + book.getCategory() + "): ");
                        String newCategory = scanner.nextLine();
                        
                        libraryCatalogue.updateBook(updateID, newTitle, newAuthor, newCategory);
                    }
                    break;
                case 5:
                    libraryCatalogue.viewBook();
                    System.out.print("\nEnter the Book ID to delete: ");
                    String deleteID = scanner.nextLine();
                    
                    System.out.print("Are you sure? (y/n): ");
                    String confirm = scanner.nextLine();
                    
                    if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("Y")) {
                        libraryCatalogue.deleteBook(deleteID);
                    } else {
                        System.out.println("Delete cancelled");
                    }

                    break;

                    case 0:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }



    // Display the admin Menu-----------------------------------------------------------------------------------------
    public static void showAdminMenu(Scanner scanner, Admin[] adminList, Admin currentAdmin, catalogue libraryCatalogue, LoanManager loanManager, FineManager fineManager){
        int subChoices;

        do{
            System.out.println("\n======= ADMIN MANAGEMENT MENU =======");
            System.out.println("Logged in as: " + currentAdmin.getName());
            System.out.println("1. View All Admins");
            System.out.println("2. View Personal Detail");
            System.out.println("3. Update Admin");
            System.out.println("4. Delete Admin");
            System.out.println("5. Manage Books");
            System.out.println("6. Manage Loans & Fines");
            System.out.println("0. Logout");
            System.out.print("Selection: ");

            try {
                subChoices = scanner.nextInt();
                scanner.nextLine(); // Clear buffer after valid number
            } catch (InputMismatchException e) {
                // Addition 3: Handle the error
                System.out.println("Error: Please enter a valid number (1-3).");
                scanner.nextLine(); // CRITICAL: Clear the "asd" out of the buffer
                subChoices = 8; // Reset choice to trigger the loop again
                continue;   // Jump back to the start of the loop
            }

            switch(subChoices){
                case 1:

                    Admin.viewAllAdmins(adminList);
                    break;
                case 2:
                    currentAdmin.displayPersonalDetail();
                    break;
                case 3: 
                    Admin.updateAdmin(scanner, adminList); 
                    break;

                case 4: 
                    Admin.deleteAdmin(scanner, adminList); 
                    break;
                
                case 5:
                    manageBooksMenu(scanner, libraryCatalogue);
                    break;

                case 6:
                    StaffMenu staffMenu = new StaffMenu(loanManager, fineManager);
                    staffMenu.menu();
                    break;

                case 0:
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid choice");

            }
    
        }while(subChoices != 0);
    }

}
