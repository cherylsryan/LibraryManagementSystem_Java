import java.util.Scanner;

public class RegistrationManager {
    
    private StudentRegistration studentReg = new StudentRegistration();
    private FacultyRegistration facultyReg = new FacultyRegistration();
    private PublicRegistration publicReg = new PublicRegistration();

    public void register(Scanner scanner) {
        
        while (true) {

            System.out.println("\n====================================");
            System.out.println("         REGISTRATION MENU");
            System.out.println("====================================");
            System.out.println("1. Student");
            System.out.println("2. Faculty");
            System.out.println("3. Public");
            System.out.println("0. Back to Main Menu");
            System.out.println("====================================");
            System.out.print("Selection: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number (0-3 only).");
                continue;  // Stay in registration menu
            }

            switch (choice) {
                case 1:
                    studentReg.registerStudent(scanner);
                    return;
                case 2:
                    facultyReg.registerFaculty(scanner);
                    return;
                case 3:
                    publicReg.registerPublic(scanner);
                    return;
                case 0:
                    System.out.println("Returning to main menu...\n");
                    return;
                default:
                    System.out.println("Invalid input! Please enter a number (0-3 only).");
           }
        }
    }
}