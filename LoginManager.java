import java.util.Scanner;

public class LoginManager {

    public void login(Scanner scanner, catalogue libraryCatalogue, LoanManager loanManager, FineManager fineManager) {
        while(true){
            System.out.println("\n====================================");
            System.out.println("              LOGIN");
            System.out.println("====================================");
            System.out.println(" (Enter 'b' to return to main menu)");
            System.out.println("====================================");
            System.out.print("Enter your ID: ");
            String id = scanner.nextLine();

            if (id.equalsIgnoreCase("b")) {
                   System.out.println("Returning to main menu...\n");
                    return;
                }
            
            id = id.toUpperCase();

            String userType = getUserTypeFromId(id);

            if (userType.equals("UNKNOWN")) {
                System.out.println("\nInvalid ID format!");
                System.out.println("   Student ID must start with STU (e.g., STU001)");
                System.out.println("   Faculty ID must start with FAC (e.g., FAC001)");
                System.out.println("   Public ID must start with PUB (e.g., PUB001)");
                continue;
            }

            User user = FileHandler.findUserById(id);

            if (user != null) {
                int maxAttempts = 3;
                int attempts = 0;
                boolean passwordCorrect = false;

                while (attempts < maxAttempts && !passwordCorrect) {
                    System.out.print("Enter Password (Attempt " + (attempts + 1) + "/" + maxAttempts + "): ");
                    String password = scanner.nextLine();
                    
                    if (password.equalsIgnoreCase("b")) {
                        System.out.println("Login cancelled. Returning to main menu...\n");
                        return;
                    }

                    if (user.validatePassword(password)) {
                        passwordCorrect = true;
                    } else {
                        attempts++;
                        if (attempts < maxAttempts) {
                            System.out.println("Incorrect password! " + (maxAttempts - attempts) + " attempt(s) left.\n");
                        }else{
                            System.out.println("\n++ ACCESS DENIED ++");
                            System.out.println("You have entered incorrect password " + maxAttempts + " times.");
                            System.out.println("If you've forgotten your password, contact faculty:");
                            System.out.println("   - Email: faculty@library.edu");
                            System.out.println("   - Phone: 03-1234-5678");
                            System.out.println("   - Visit: Library Administration Office\n");
                            return;

                        }
                    }
                }
                
                if (passwordCorrect) {
                    System.out.println("Login successful!");
                    showUserMenu(user, scanner, libraryCatalogue, loanManager, fineManager);
                    return;
                }
            }
            else {
                System.out.println("User not found!");
                System.out.println("Please check your ID or register first.");
                return;
            }
        }
    }
    
    private String getUserTypeFromId(String id) {
        if (id == null || id.isEmpty()) {
            return "UNKNOWN";
        }
        
        if (id.toUpperCase().startsWith("STU")) {
            return "STUDENT";
        } else if (id.toUpperCase().startsWith("FAC")) {
            return "FACULTY";
        } else if (id.toUpperCase().startsWith("PUB")) {
            return "PUBLIC";
        } else {
            return "UNKNOWN";
        }
    }
    
    private void showUserMenu(User user, Scanner scanner, catalogue libraryCatalogue, LoanManager loanManager, FineManager fineManager) {
        UserMenu userMenu = new UserMenu();
        userMenu.display(user, scanner, libraryCatalogue, loanManager, fineManager);
    }
    
}