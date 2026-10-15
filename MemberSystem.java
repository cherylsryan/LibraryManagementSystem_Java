import java.util.Scanner;

public class MemberSystem {

    LoginManager loginManager = new LoginManager();
    RegistrationManager registrationManager = new RegistrationManager();

    public void showMemberInterface(Scanner scanner, catalogue libraryCatalogue, LoanManager loanManager, FineManager fineManager){
        
        int memberChoice = 4;
        while (memberChoice != 0) {
            System.out.println("\n=======================================");
            System.out.println("           MEMBER INTERFACE            ");
            System.out.println("=======================================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Back to Role Selection");
            System.out.print("Selection: ");

            
            try {
                memberChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number (0-2 only).");
                continue;
            }

            switch (memberChoice) {
                case 1:
                    loginManager.login(scanner, libraryCatalogue, loanManager, fineManager);
                    break;
                case 2:
                    registrationManager.register(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid input! Please enter a number (0-2 only).");
            }
        }

    }
}
