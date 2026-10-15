import java.util.Scanner;

public class StaffMenu {
    
    private LoanManager loanManager;
    private FineManager fineManager;
    private Scanner scanner = new Scanner(System.in);

    public StaffMenu(LoanManager loanManager, FineManager fineManager) {
        this.loanManager = loanManager;
        this.fineManager = fineManager;
    }

    public void menu() {
    int choice = 0;

    do {
        System.out.println("\n=====================================");
        System.out.println("    LOANS & FINES MANAGEMENT MENU  ");
        System.out.println("=====================================");
        System.out.println("1. View Loans");
        System.out.println("2. View Fines");
        System.out.println("3. Pay Fine");
        System.out.println("4. Delete Loan");
        System.out.println("5. Delete Fine");
        System.out.println("6. Extend Due Date");
        System.out.println("0. Back to Admin Management Menu");

        System.out.print("Selection: ");

        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input!");
            scanner.nextLine();
            continue;
        }

        switch (choice) {
            case 1: loanManager.viewLoans(); break;
            case 2: fineManager.viewFines(); break;
            case 3: payFineInput(); break;
            case 4: deleteLoanInput(); break;
            case 5: deleteFineInput(); break;
            case 6: extendLoanInput(); break;
            case 0: System.out.println("Logging out..."); break;
            default: System.out.println("Invalid choice.");
        }

    } while (choice != 0);
}

    private void payFineInput() {
        
        System.out.println("\n===== Pay Fine =====");
        String fineId = validInput("Enter Fine ID: ");
        
        Fine fine = fineManager.getFineByID(fineId);

        if (fine == null) {
            System.out.println("Fine not found.");
            return;
        }

        if (fine.isPaid()) {
            System.out.println("Fine already paid.");
            return;
        }

        System.out.println("Fine Found:");
        System.out.println(fine);
        
         // Ask payment method
        System.out.println("Select Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. Online");
        System.out.println("3. Card");
        System.out.print("Selection: ");

        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
            return;
        }

        String method;
        switch (choice) {
            case 1: method = "Cash"; break;
            case 2: method = "Online"; break;
            case 3: method = "Card"; break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        if (!confirmAction("Confirm pay fine?")) {
            System.out.println("Payment cancelled.");
            return;
        }

        fineManager.payFine(fineId, method);
    }
    
    private void deleteLoanInput(){
        System.out.println("\n====== Delete Loan ======");
        
        // show loan before delete
        System.out.println("Current Loans:");
        loanManager.viewLoans(); 
        
        String loanId = validInput("Enter Loan ID: ");
        Loan loan = loanManager.getLoanByID(loanId);
        
        if (loan == null) {
            System.out.println("Loan not found.");
            return;
        }
        
        if (!confirmAction("Confirm delete loan?")) {
            System.out.println("Delete cancelled.");
            return;
        }

        loanManager.deleteLoan(loanId);
    }
    
    private void deleteFineInput() {
        System.out.println("\n===== Delete Fine =====");

        // Show fines before deleting
        System.out.println("Current Fines:");
        fineManager.viewFines(); 
       
        String fineId = validInput("Enter Fine ID to delete: ");
        Fine fine = fineManager.getFineByID(fineId);

        if (fine == null) {
            System.out.println("Fine not found.");
            return;
        }

        if (fine.isPaid()) {
            System.out.println("Cannot delete a paid fine.");
            return;
        }

        if (!confirmAction("Confirm delete fine?")) {
            System.out.println("Delete cancelled.");
            return;
        }

        fineManager.deleteFine(fineId);
    }
    
    private void extendLoanInput() {

        System.out.println("\n===== Extend Due Date =====");

        String loanId = validInput("Enter Loan ID: ");
        Loan loan = loanManager.getLoanByID(loanId);

        if (loan == null) {
            System.out.println("Loan not found.");
            return;
        }

        System.out.println("Loan Found:");
        System.out.println(loan);

        int days;

        while (true) {
            System.out.print("Enter number of days to extend (1–30): ");

            try {
                days = Integer.parseInt(scanner.nextLine());

                if (days < 1 || days > 30) {
                    System.out.println("Error: Must be between 1 and 30.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid positive integer.");
            }
        }

        if (!confirmAction("Confirm extend due date?")) {
            System.out.println("Operation cancelled.");
            return;
        }

        loanManager.extendLoan(loanId, days);
    }

    
    private String validInput(String message) {
        String input;
        
        while (true) {
            System.out.print(message);
            input = scanner.nextLine();

            if (!input.trim().isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty. Please try again.");
        }
    }
    
    private boolean confirmAction(String message) {
        String input;
        while (true) {
            System.out.print(message + " (Y/N): ");
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Y")) {
                return true; // user confirmed
            } else if (input.equalsIgnoreCase("N")) {
                return false; // user cancelled
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }
    }
    
}
