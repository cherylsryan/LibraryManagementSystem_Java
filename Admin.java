import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Admin {
    private String name;
    private String adminID;
    private String password;
    private double salary;
    private static int adminCount = 0;
    public static final String ADMIN_FILE = "admins.txt";
    
    public Admin(String name, String password, double salary){
        this.name = name;
        adminCount++;
        this.adminID = "ADM" + String.format("%03d", adminCount);
        this.password = password;
        this.salary = salary;
        
    }

    public Admin(String name, String adminID, String password, double salary) {
        this.name = name;
        this.adminID = adminID;
        this.password = password;
        this.salary = salary;
        // Logic to ensure adminCount matches the highest ID loaded
        int numericId = Integer.parseInt(adminID.replace("ADM", ""));
        if (numericId > adminCount) {
            adminCount = numericId;
        }
    }
    
    public String getName(){
        return name;
    }
    
    public String getAdminID(){
        return adminID;
    }

    public String getPassword(){
        return password;
    }
    
    public double getSalary(){
        return salary;
    }
    
    public static int getAdminCount(){
        return adminCount;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setAdminID(String adminID){
        this.adminID = adminID;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setSalary(double salary){
        this.salary = salary;
    }
    
    public boolean checkID(String input){
        return input.equals(adminID);
    }
    
    public boolean checkPassword(String input){
        return input.equals(password);
    }

    public String toString(){
        return String.format("%-20s | %-6s | %.2f", name, adminID, salary);
    }


    // DISPLAY PERSONAL DETAIL---------------------------------------------------------------------------------
    public void displayPersonalDetail(){
        System.out.println("\n==============================");
        System.out.println("        Personal Detail       "); 
        System.out.println("==============================");
        System.out.printf("%-12s: %s\n", "Name", name);
        System.out.printf("%-12s: %s\n", "Admin ID", adminID);
        System.out.printf("%-12s: %s\n", "Password", password); 
        System.out.printf("%-12s: RM %.2f\n", "Salary", salary);
        System.out.println("===============================");
    }

        
    // 2. SAVE THE NEW ADMIN REGISTERED--------------------------------------------------------------------------------
    public static void saveToFile(Admin[] adminList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ADMIN_FILE))) {
            for (int i = 0; i < adminCount; i++) {
                // Saved data separated by a delimiter "|"
                writer.println(adminList[i].name + "|" + 
                               adminList[i].adminID + "|" + 
                               adminList[i].password + "|" + 
                               adminList[i].salary);
            }

            System.out.println("Data saved to " + ADMIN_FILE);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }


    // LOAD THE EXISTING ADMIN----------------------------------------------------------------------
    public static void loadFromFile(Admin[] adminList) {
        File file = new File(ADMIN_FILE);

        if (!file.exists()) 
            return; // No file yet, nothing to load

        try (Scanner fileScanner = new Scanner(file)) {
            int index = 0;
            while (fileScanner.hasNextLine() && adminCount < adminList.length) {
                String line = fileScanner.nextLine();
                String[] data = line.split("\\|"); // Split by the pipe character
                
                String name = data[0];
                String id = data[1];
                String password = data[2];
                double salary = Double.parseDouble(data[3]);

                
                adminList[index] = new Admin(name, id, password, salary);
                index++;
            }
            //System.out.println("Data loaded successfully.");
        } 
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }


    // DISPLAY ALL EXISTING ADMIN------------------------------------------------------------------------------
    public static void viewAllAdmins(Admin[] adminList){

        if (adminCount == 0) {
        System.out.println("\n[!!!] No admins registered in the system [!!!]");
        return;
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.printf("%-5s | %-20s | %-6s | %-8s\n", "No.", "Name", "ID", "Salary");
        System.out.println("=".repeat(50));
   
        for (int i = 0; i < adminCount; i++) {
            if(adminList[i] != null){
                System.out.printf("%-5d | %s\n", i+1, adminList[i].toString());
            }
        }
        System.out.println("=".repeat(50));
    }


    // UPDATE ADMIN ---------------------------------------------------------------------------------------------
    public static void updateAdmin(Scanner scanner, Admin[] adminList){
        System.out.println("\n==============================");
        System.out.println("         UPDATE ADMIN         "); 
        System.out.println("==============================");
        System.out.println("Type 'd' to keep current value or 'b' to cancel update entirely.");
        System.out.print("Enter Admin ID to Update: ");
        String id = scanner.nextLine();

        if (id.equalsIgnoreCase("b")) {
            System.out.println("Process Canceled! Returning...");
            return;
        }

        for(int i = 0; i <adminCount; i++){
            if(adminList[i].checkID(id)){

                // update name
                String tempName;
                while(true){
                    System.out.println("\nCurrent Name [" + adminList[i].getName() + "]");
                    System.out.print("Enter New Name: ");
                    String new_name = scanner.nextLine();   
                
                    if (new_name.equalsIgnoreCase("b")) {
                    System.out.println("Process Canceled! Returning...");
                    return;
                    }

                    if (new_name.equalsIgnoreCase("d")) {
                        tempName = adminList[i].getName();
                        break;
                    }
                    if (!new_name.isEmpty()) {
                        tempName = new_name;
                        break;
                    } // if no break, then can put else
                System.out.println("[!!!] Error: Name cannot be empty [!!!]");
                }
                
                // update password
                String tempPassword;
                while(true){
                    System.out.print("Enter New Password: ");
                    String new_password = scanner.nextLine();

                    if (new_password.equalsIgnoreCase("b")) {
                    System.out.println("[!!] Process Canceled! Returning... [!!]");
                    return;
                    }
                    if (new_password.equalsIgnoreCase("d")) {
                        tempPassword = adminList[i].getPassword(); // Keep old password
                        break;
                    }
                    if (new_password.length() >= 6) {
                        tempPassword = new_password;
                        break;
                    }
                    System.out.println("Error: Password must be at least 6 characters!");
                }

                //update salary
                double tempSalary;
                while(true){
                    System.out.println("Current Salary [" + adminList[i].getSalary() + "]");
                    System.out.print("Enter New Salary: ");
                    String new_salary = scanner.nextLine().trim();  // so that can accept "b" or "d"

                    if (new_salary.equalsIgnoreCase("b")) {
                    System.out.println("Process Canceled! Returning...");
                    return;
                    }

                    if (new_salary.equalsIgnoreCase("d") || new_salary.isEmpty()) {
                        tempSalary = adminList[i].getSalary();
                        break;
                    }
                    try {
                        tempSalary = Double.parseDouble(new_salary);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Please enter a valid number or 'd'.");
                    }
                }
                
                // Confirmation
                System.out.println("\n--------Changes--------");
                System.out.println("New Name: " + tempName);
                System.out.println("New Password: " + tempPassword);
                System.out.println("New Salary: " + tempSalary);

                System.out.print("\nSave these changes? (Y/N): ");
                String confirm = scanner.nextLine().trim();

                if (confirm.equalsIgnoreCase("y")) {
                    adminList[i].setName(tempName);
                    adminList[i].setPassword(tempPassword);
                    adminList[i].setSalary(tempSalary);
                    
                    saveToFile(adminList);
                    System.out.println("Admin updated successfully!");
                } 
                else {
                    System.out.println("Update discarded.");
                }
                return; // Exit method after handling the specific admin
            
            }
        }
        System.out.println("Admin ID not found.");

    }


    // delete admin--------------------------------------------------------------------------
    public static void deleteAdmin(Scanner scanner, Admin[] adminList){
        System.out.print("Enter Admin ID to Delete: ");
        String id = scanner.nextLine();

        for(int i = 0; i < adminCount; i++){
            if(adminList[i].checkID(id)){
                System.out.print("Are you sure you want to delete " + adminList[i].getName() + "? (Y/N): ");
                String confirm = scanner.nextLine();

                if(confirm.equalsIgnoreCase("Y")){

                    for(int j = i; j < adminCount - 1; j++){
                        adminList[j] = adminList[j + 1];
                    }

                    adminList[adminCount - 1] = null;
                    adminCount--;

                    saveToFile(adminList);
                    System.out.println("Admin deleted successfully.");
                }
                else{
                    System.out.println("Deletion cancelled.");
                }
                return;
            }
        }
        System.out.println("Error: Admin ID not found.");

    }
    
    
}

