import java.util.Scanner;

public class MemberMenu {
    
    private LoanManager loanManager;
    private FineManager fineManager;
    private Scanner scanner = new Scanner(System.in);
    private User currentUser; 

    public MemberMenu(LoanManager loanManager, FineManager fineManager, User currentUser) {
        this.loanManager = loanManager;
        this.fineManager = fineManager;
        this.currentUser = currentUser;
    }
    
    public void menu() {
        int choice = 0;

        do {
            System.out.println("\n====================================");
            System.out.println("             Loans & Fines      ");
            System.out.println("====================================");
            System.out.println("1. View Loans");
            System.out.println("2. View Fines");
            System.out.println("0. Back to User Menu");

            System.out.print("Selection: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1: loanManager.viewLoansByUser(currentUser.getId());break;
                case 2: fineManager.viewFinesByUser(currentUser.getId());  break;
                case 0: System.out.println("Logging out..."); break;
                default: System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }   
}
