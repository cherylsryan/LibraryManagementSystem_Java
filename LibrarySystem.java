import java.util.Scanner;


public class LibrarySystem {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        FineManager fineManager = new FineManager();
        LoanManager loanManager = new LoanManager(fineManager);

        catalogue libraryCatalogue = new catalogue(); // Existing book data

        fineManager.loadFinesFromFile();
        loanManager.loadLoansFromFile();

        libraryCatalogue.loadFromFile();

        Admin[] adminList = new Admin[10];
        Admin.loadFromFile(adminList);

        int choice1 = 4;

        while (choice1 != 0) {
            System.out.println("\n+++++++++++++++++++++++++++++++++++++++");
            System.out.println("              WELCOME TO               ");
            System.out.println("        Book Read, Smart Become     ");
            System.out.println("+++++++++++++++++++++++++++++++++++++++");
            System.out.println("1. Member");
            System.out.println("2. Admin");
            System.out.println("0. Exit");
            System.out.print("Selection: ");

            
            
            try {
                choice1 = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number (0-2 only).\n");
                continue;
            }

            switch (choice1) {
                case 1:
                    MemberSystem member = new MemberSystem();
                    member.showMemberInterface(scanner, libraryCatalogue, loanManager, fineManager);
                    break;
                case 2:
                    AdminManager.showAdminInterface(scanner, adminList, libraryCatalogue, loanManager, fineManager);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid input! Please enter a number (0-2 only).\n");
            }
        }

    }
}
