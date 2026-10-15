import java.util.Scanner;

public class PublicRegistration extends BaseRegistration {
    
    public void registerPublic(Scanner scanner) {
        
        // Get Name using parent method
        String name = getName(scanner, "PUBLIC MEMBER");
        if (name == null) return;
        
        // Get ID using parent method
        String id = null;
        while (id == null) {
            id = getId(scanner, "PUB");
            if (id == null) {
                return;
            }
        }
        
        // Get Password using parent method
        String password = null;
        while (password == null) {
            password = getPassword(scanner);
            if (password == null) {
                System.out.println("Please try again or enter 'b' to cancel.\n");
            }
        }
        
        // Confirm Password
        String confirmPassword = null;
        while (true) {
            System.out.print("Confirm Password: ");
            confirmPassword = scanner.nextLine();
            
            if (confirmPassword.equals("b")) {
                System.out.println("Registration cancelled.\n");
                return;
            }
            
            if (password.equals(confirmPassword)) {
                break;
            } else {
                System.out.println("Password does not match! Please try again.\n");
            }
        }
        
        if (FileHandler.findUserById(id) != null) {
            System.out.println("Error: Public Member ID " + id + " already exists!\n");
            return;
        }
        
        // Confirmation before saving
        while (true) {
            System.out.println("\n====================================");
            System.out.println("        REGISTRATION SUMMARY");
            System.out.println("====================================");
            System.out.println("   Name        : " + name);
            System.out.println("   Public Member ID  : " + id);
            System.out.println("   Password    : " + "*".repeat(password.length()));
            System.out.println("====================================");
            System.out.println("   1. Confirm Registration");
            System.out.println("   0. Cancel");
            System.out.println("====================================");
            System.out.print("Selection: ");

            String confirm = scanner.nextLine();

            if (confirm.equals("1")) {
                break;  // Exit loop and continue to save
            } else if (confirm.equals("0")) {
                System.out.println("Registration cancelled. Returning to main menu...");
                return;
            } else {
                System.out.println("Invalid choice! Please enter 1 to confirm or 0 to cancel.");
            }
        }
        
        try {
            User newUser = new PublicMember(id, name, password);
            FileHandler.saveUser(newUser);
            
            System.out.println("\n====================================");
            System.out.println("     REGISTRATION SUCCESSFUL!  ");
            System.out.println("====================================");
            System.out.println("   Name        : " + newUser.getName());
            System.out.println("   Public ID   : " + newUser.getId());
            System.out.println("   Expiry Date : " + newUser.getExpiryDate());
            System.out.println("====================================");
            System.out.println(" Please use your Public ID to login");
            System.out.println("====================================\n");
        } catch (Exception e) {
            System.out.println("Failed to save user data. Please try again.");
        }
    }
}