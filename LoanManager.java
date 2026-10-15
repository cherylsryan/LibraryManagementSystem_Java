import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

// Handle loan-related operations
public class LoanManager {
    
    private ArrayList<Loan> loanList = new ArrayList<>();
    private FineManager fineManager;
    
    public LoanManager(FineManager fineManager) {
        this.fineManager = fineManager;
    }
    
    // Borrow book
    public void borrowBook(User user, String bookId) {
        Loan loan = new Loan(user, bookId);
        loanList.add(loan);

        System.out.println("\n====================================");
        System.out.println("          BOOK BORROWED");
        System.out.println("====================================");
        System.out.println("Loan ID     : " + loan.getLoanID());
        System.out.println("Book ID     : " + loan.getBookID());
        System.out.println("Borrow Date : " + loan.getBorrowDate());
        System.out.println("Due Date    : " + loan.getDueDate());
        System.out.println("Max Days    : " + loan.getMaxBorrowDays() + " days");
        System.out.println("====================================");
        
        saveLoansToFile();
    }

    // Return book
    public void returnBook(String loanId) {
        for (Loan loan : loanList) {
            if (loan.getLoanID().equals(loanId) && !loan.isReturned()) {
                loan.returnBook();
                System.out.println("\nBook returned successfully!");
                System.out.println(loan);
                
                // Create fine if overdue
                if (loan.isOverdue()) {
                    fineManager.createFine(loan.getUserID(), loan.getDaysLate());
                }
                saveLoansToFile();
                return;
            }
        }
        System.out.println("Loan not found or book already returned.");
    }

    // View loans for a specific user only
    public void viewLoansByUser(String userId) {
        boolean found = false;
        
        System.out.println("\n====================================");
        System.out.println("             MY LOANS");
        System.out.println("====================================");
        
        for (Loan loan : loanList) {
            if (loan.getUserID().equals(userId)) {
                System.out.println(loan);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No loans found for this user.");
        }
    }

    // View all loans
    public void viewLoans() {
        if (loanList.isEmpty()) {
            System.out.println("No loans found.");
            return;
        }
        
        System.out.println("\n====================================");
        System.out.println("           ALL LOANS");
        System.out.println("====================================");
        
        for (Loan loan : loanList) {
            System.out.println(loan);
        }
    }
    
    // Delete loan by id
    public void deleteLoan(String loanId) {
        Loan loan = getLoanByID(loanId);
        
        if (loan != null) {
            loanList.remove(loan);
            System.out.println("Loan deleted successfully.");
            saveLoansToFile();
        } else {
            System.out.println("Loan not found.");
        }
    }
    
    public void extendLoan(String loanId, int extraDays) {
        Loan loan = getLoanByID(loanId);

        if (loan == null) {
            System.out.println("Loan not found.");
            return;
        }

        if (loan.isReturned()) {
            System.out.println("Cannot extend due date for a returned book.");
            return;
        }

        if (!loan.extendDueDate(extraDays)) {
        System.out.println("Extension failed.");
        return;
    }

    saveLoansToFile();
}
    
    
    public void saveLoansToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("loans.txt"))) {
            for (Loan loan : loanList) {
                writer.println(
                    loan.getLoanID() + "," +
                    loan.getUserID() + "," +
                    loan.getBookID() + "," +
                    loan.getDueDate() + "," +
                    loan.isReturned() + "," +
                    loan.getDaysLate() + "," +
                    loan.getMaxBorrowDays() + "," +
                    loan.getTotalExtendedDays()
                );
            }
            System.out.println("Loans saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving loans.");
        }
    }
    
    public void loadLoansFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("loans.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                
                // Format: loanID, userID, bookID, dueDate, returned, daysLate, maxBorrowDays
                Loan loan = new Loan(data[1], data[2]);
                loan.setMaxBorrowDays(Integer.parseInt(data[6]));

                // Manually set the loan ID to match the saved one
                int loanNumber = Integer.parseInt(data[0].substring(1));
                Loan.setNextId(loanNumber - 1);
                
                // Set due date
                loan.setDueDate(LocalDate.parse(data[3]));
                if (data.length > 7) {
                    loan.setTotalExtendedDays(Integer.parseInt(data[7]));
                } else {
                    loan.setTotalExtendedDays(0);
                }

                // Manually adjust values if returned
                if (Boolean.parseBoolean(data[4])) {
                    loan.setReturnDate(loan.getDueDate().plusDays(Integer.parseInt(data[5])));
                }

                loanList.add(loan);
            }

        } catch (IOException e) {
            System.out.println("No loan file found.");
        }
    }
    
    public Loan getLoanByID(String loanId) {
        for (Loan loan : loanList) {
            if (loan.getLoanID().equals(loanId)) {
                return loan;
            }
        }
        return null;
    }
}