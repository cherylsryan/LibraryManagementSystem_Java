import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class UserMenu {

    public void display(User user, Scanner scanner, catalogue libraryCatalogue, LoanManager loanManager, FineManager fineManager) {

        while (true) {
            System.out.println("\n====================================");            
            System.out.println("            USER MENU ");
            System.out.println("====================================");
            System.out.println("1. View Profile");
            System.out.println("2. Change Password");
            System.out.println("3. Delete Account");
            System.out.println("4. Browse Book, Borrow & Return Book");
            System.out.println("5. Loan & Fine");
            System.out.println("0. Logout");
            System.out.println("====================================");
            System.out.print("Selection: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter (0-5 only).");
                continue;
            }

            switch (choice) {
                case 1:
                    viewProfile(user);
                    break;

                case 2:
                    changePassword(user, scanner);
                    break;

                case 3:
                    while (true) {
                        System.out.print("\nAre you sure you want to delete your account? (y/n): ");
                        String confirm = scanner.nextLine().trim().toLowerCase();

                        if (confirm.equals("y")) {
                            FileHandler.deleteUser(user.getId());
                            System.out.println("Account deleted successfully!");
                            return;  // Exit user menu and go back to main menu
                        } else if (confirm.equals("n")) {
                            System.out.println("Account deletion cancelled.");
                            break;  // Exit the while loop, stay in user menu
                        } else {
                            System.out.println("Invalid input! Please enter 'y' for yes or 'n' for no.");
                            // Loop continues - ask again
                        }
                    }
                    break;

                case 4:
                    browseBook(libraryCatalogue, scanner);

                    break;

                case 5:
                    MemberMenu memberMenu = new MemberMenu(loanManager, fineManager, user);
                    memberMenu.menu();

                    break;

                case 0:
                    System.out.println("Logging out...");
                    return;
                    
                default:
                    System.out.println("Invalid choice! Please enter (0-5 only).");
            }
        }
    }
    

    // 1. VIEW PROFILE--------------------------------------------------------------------------------------------------
    private void viewProfile(User user) {
        LocalDate expiryDate = user.getExpiryDate();
        LocalDate today = LocalDate.now();
        
        // Calculate days until expiry
        long daysUntilExpiry = ChronoUnit.DAYS.between(today, expiryDate);
        
        // Determine membership status
        String status;
        if (daysUntilExpiry < 0) {
            status = "EXPIRED";
        } else if (daysUntilExpiry <= 30) {
            status = "EXPIRING SOON (" + daysUntilExpiry + " days left)";
        } else {
            status = "ACTIVE";
        }
        
        System.out.println("\n====================================");
        System.out.println("           PROFILE INFO");
        System.out.println("====================================");
        System.out.println("   Name            : " + user.getName());
        System.out.println("   ID              : " + user.getId());
        System.out.println("   Role            : " + user.getRole());
        System.out.println("   Max Borrow Days : " + user.getMaxBorrowDays() + " days");
        System.out.println("   Registration    : " + user.getRegistrationDate());
        System.out.println("   Expiry Date     : " + expiryDate);
        System.out.println("   Status          : " + status);
        System.out.println("====================================\n");
    }


    // 2. CHANGE PASSWORD-------------------------------------------------------------------------------------------------
    private void changePassword(User user, Scanner scanner) {
        while (true) {
            System.out.print("\nEnter current password (or 'b' to cancel): ");
            String oldPass = scanner.nextLine();
            
            if (oldPass.equalsIgnoreCase("b")) {
                System.out.println("Password change cancelled.\n");
                return;
            }
            
            if (!user.validatePassword(oldPass)) {
                System.out.println("Incorrect password! Please try again.");
                continue;
            }
            
            while (true) {
                System.out.print("Enter new password (min 6 chars, or 'b' to cancel): ");
                String newPass = scanner.nextLine();
                
                if (newPass.equalsIgnoreCase("b")) {
                    System.out.println("Password change cancelled.\n");
                    return;
                }
                
                if (newPass.length() < 6) {
                    System.out.println("Password must be at least 6 characters!\n");
                    continue;
                }
                
                System.out.print("Confirm new password: ");
                String confirmPass = scanner.nextLine();
                
                if (!newPass.equals(confirmPass)) {
                    System.out.println("Passwords do not match! Please try again.\n");
                    continue;
                }
                
                // Update password
                FileHandler.updatePassword(user.getId(), newPass);
                user.updatePassword(newPass);
                System.out.println("Password updated successfully!");
                return;
            }
        }
    }


    // 3. BROWSE BOOK -------------------------------------------------------------------------------------
    public void browseBook(catalogue libraryCatalogue, Scanner scanner){
        while(true){
            System.out.println("\n====================================");
            System.out.println("           BROWSE BOOKS");
            System.out.println("====================================");
            System.out.println("1. View All Books");
            System.out.println("2. Search Books");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return a Book");
            System.out.println("0. Back to User Menu");
            System.out.print("Selection: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } 
            catch (NumberFormatException e) {
                System.out.println("\n[!!!]Invalid input. Please enter(0-4). [!!!]\n");
                continue;
            }

            switch (choice) {
            case 1:
                libraryCatalogue.viewBook(); //
                break;
            case 2:
                System.out.print("\nEnter keyword (ID/Title/Author/Category): ");
                String keyword = scanner.nextLine();
                libraryCatalogue.searchBooks(keyword); //
                break;
            case 3:
                System.out.print("\nEnter Book ID to borrow: ");
                String borrowID = scanner.nextLine();
                libraryCatalogue.borrowBook(borrowID); //
                break;
            case 4:
                System.out.print("\nEnter Book ID to return: ");
                String returnID = scanner.nextLine();
                libraryCatalogue.returnBook(returnID); //
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice!");
            }
        }
    }
}