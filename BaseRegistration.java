import java.util.Scanner;

public abstract class BaseRegistration {
    
    protected String getName(Scanner scanner, String userType) {
        while (true) {
            System.out.println("\n====================================");
            System.out.println("       " + userType + " REGISTRATION");
            System.out.println("====================================");
            System.out.println(" (Enter 'b' at any time to cancel)");
            System.out.println("====================================");
            System.out.print("Enter Full Name: ");
            String name = scanner.nextLine();
            
            if (name.equalsIgnoreCase("b")) {
                System.out.println("Registration cancelled. Returning back...");
                break;
            }
            
            name = name.trim();
            
            if (name.trim().isEmpty()) {
                System.out.println("Name cannot be empty! Please try again.");
                continue;
            }
            
            return name;
        }
        return null;
    }
    
    protected String getId(Scanner scanner, String prefix) {
        while (true) {
            System.out.print("Enter ID (001 to 999): " + prefix);
            String input = scanner.nextLine();

            input = input.trim();

            if (input.equalsIgnoreCase("b")) {
                System.out.println("Registration cancelled. Returning back...");
                break;  // Break out of while loop
            }

            if (input.isEmpty()) {
                System.out.println("ID cannot be empty! Please enter a number (1-999).\n");
                continue;
            }

            if (input.length() != 3) {
                System.out.println("Invalid! Please enter exactly 3 digits.");
                System.out.println("Example: 001, 020, 145\n");
                continue;
            }

            try {
                int num = Integer.parseInt(input);
                if (num < 1 || num > 999) {
                    System.out.println("Invalid! Number must be between 001 and 999.\n");
                    continue;
                }
                return prefix + String.format("%03d", num);
            } catch (NumberFormatException e) {
                System.out.println("Invalid! Enter ONLY numbers.");
                System.out.println("Example: 001, 123, 999 or 'b' to cancel\n");
            }
        }
        return null;  // This runs when user types 'b'
    }
    
    protected String getPassword(Scanner scanner) {
        while (true) {
            System.out.print("Enter Password (min 6 chars): ");
            String password = scanner.nextLine();

            password = password.trim();

            if (password.equalsIgnoreCase("b")) {
                System.out.println("Registration cancelled. Returning to menu...\n");
                break;
            }

            if (password.isEmpty()) {
                System.out.println("Password cannot be empty!\n");
                continue;
            }

            if (password.length() < 6) {
                System.out.println("Password must be at least 6 characters!\n");
                continue;
            }
            return password;
        }
        return null;  // Add this - returns null when user types 'b'
    }
}