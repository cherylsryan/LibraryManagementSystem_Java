import java.io.*;
import java.util.ArrayList;

// Handle finr-related operations
// Use ArrayList to store Fine objects
public class FineManager {
    
    private ArrayList<Fine> fineList = new ArrayList<>();
    private LoanManager loanManager;
    
    // Create a new fine
    public void createFine(String userId, int daysLate) {
        
        Fine fine = new Fine(userId, daysLate);
        fineList.add(fine);

        System.out.println("Fine created successfully.");
        System.out.println(fine);
        saveFinesToFile();
    }
    
    public void viewFinesByUser(String userId) {
        boolean found = false;
        
        System.out.println("\n====================================");
        System.out.println("             MY FINES");
        System.out.println("====================================");
        
        for (Fine fine : fineList) {
            if (fine.getUserID().equals(userId)) {
                System.out.println(fine);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No fines found for this user.");
        }
    }

    // Display all fines
    public void viewFines(){

        if (fineList.isEmpty()){
            System.out.println("No fines found.");
            return;
        }

        for(Fine fine : fineList){
            System.out.println(fine);
        }
    }
    
    // Pay a fine
    public void payFine(String fineId, String method){

        for(Fine fine : fineList){
            if(fine.getFineID().equals(fineId)){
                fine.payFine(method);
                saveFinesToFile();
                return;
            }
        }
        System.out.println("Fine not found.");
    }
    
    // Delete fine by ID 
    public void deleteFine(String fineId) {
        Fine fine = getFineByID(fineId);

        if (fine != null) {
            fineList.remove(fine);
            System.out.println("Fine deleted successfully.");
            saveFinesToFile();
        } else {
            System.out.println("Fine not found.");
        }
    }
    
    public void saveFinesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("fines.txt"))) {
            for (Fine fine : fineList) {
                writer.println(
                    fine.getFineID() + "," +
                    fine.getUserID() + "," +
                    fine.getDaysLate() + "," +
                    fine.getAmount() + "," +
                    fine.isPaid() + "," +
                    fine.getPaymentMethod()
                );
            }
        } catch (IOException e) {
            System.out.println("Error saving fines.");
        }
    }
    
    public void loadFinesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("fines.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Fine fine = new Fine(data[1], Integer.parseInt(data[2]));

                if (Boolean.parseBoolean(data[4])) {
                    fine.setAsPaid(data[5]);
                }

                fineList.add(fine);
            }

        } catch (IOException e) {
            System.out.println("No fine file found.");
        }
    }
    
    public Fine getFineByID(String fineId) {
        for (Fine fine : fineList) {
            if (fine.getFineID().equals(fineId)) {
                return fine;
            }
        }
        return null;
    }
    
}
