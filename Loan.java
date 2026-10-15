import java.time.LocalDate;

// loan function (borrow book, return book)
public class Loan extends Record{
    
    private static int nextLoanID = 1;
    private String userID;
    private String bookID;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean returned;
    private int daysLate;
    private int maxBorrowDays;
    private int totalExtendedDays = 0;
    private static final int MAX_EXTENSION = 30;
   
    // constructor
    public Loan(User user, String bookID){
        this.id = "L" + String.format("%03d", nextLoanID++);
        this.userID = user.getId();
        this.bookID = bookID;
        this.borrowDate = LocalDate.now();
        this.maxBorrowDays = user.getMaxBorrowDays(); 
        this.dueDate = borrowDate.plusDays(maxBorrowDays);
        this.returned = false;
        this.daysLate = 0;
        
    }

    public Loan(String userID, String bookID) {
        this.id = "L" + String.format("%03d", nextLoanID++);
        this.userID = userID;
        this.bookID = bookID;
        this.borrowDate = LocalDate.now();
        this.maxBorrowDays = 7;  // default
        this.dueDate = borrowDate.plusDays(maxBorrowDays);
        this.returned = false;
        this.daysLate = 0;
    }
    
    // Method to return book
    public void returnBook(){
        if(!returned){
            this.returnDate = LocalDate.now();
            this.returned = true;
            this.daysLate = calculateDaysLate();
        }
    }
    
    // Calculate late days
    private int calculateDaysLate() {
        if (returnDate != null && returnDate.isAfter(dueDate)) {
            return (int)(returnDate.toEpochDay() - dueDate.toEpochDay());
        }
        return 0;
    }
    
    public boolean extendDueDate(int extraDays) {

        if (extraDays <= 0) {
            System.out.println("Invalid extension days.");
            return false;
        }

        if (totalExtendedDays >= MAX_EXTENSION) {
            System.out.println("Error: Max extension reached.");
            return false;
        }

        if (totalExtendedDays + extraDays > MAX_EXTENSION) {
            System.out.println("Error: Cannot exceed 30 days total extension.");
            return false;
        }

        this.dueDate = this.dueDate.plusDays(extraDays);
        totalExtendedDays += extraDays;

        System.out.println("Due date extended by " + extraDays + " days.");
        System.out.println("Remaining Extension Days: " + 
            Math.max(0, MAX_EXTENSION - totalExtendedDays));

        return true;
    }

    // Check overdue
    public boolean isOverdue(){
        return daysLate > 0;
    }
    
    // Getters
    public String getLoanID(){
        return id;
    }
    
    public String getUserID(){
        return userID;
    }
    
    public String getBookID(){
        return bookID;
    }
    public LocalDate getBorrowDate(){ 
        return borrowDate; 
    }
    
    public LocalDate getDueDate(){
        return dueDate;
    }
    
    public LocalDate getReturnDate(){
        return returnDate; 
    }
    
    public boolean isReturned(){
        return returned;
    }
    
    public int getDaysLate(){
        return daysLate; 
    }

    public int getMaxBorrowDays() {
        return maxBorrowDays;
    }

    public int getTotalExtendedDays() {
        return totalExtendedDays;
    }
    
    // Setters
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        this.returned = true;                  // mark as returned
        this.daysLate = calculateDaysLate();   // recalc late days
    }

    public void setMaxBorrowDays(int maxBorrowDays) {
        this.maxBorrowDays = maxBorrowDays;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setTotalExtendedDays(int days) {
        this.totalExtendedDays = days;
    }

    // Override (polymorphism)
    public String toString() {
        return String.format(
            "\n===== Loan Details =====\n" +
            "Loan ID   : %s\n" +
            "User ID   : %s\n" +
            "Book ID   : %s\n" +
            "Due Date  : %s\n" +
            "Status    : %s%s\n" +
            "-------------------------",
            id,
            userID,
            bookID,
            dueDate,
            (returned ? "Returned" : "Not Returned"),
            (returned ? "\nLate Days : " + daysLate : "") +
            "\nRemaining Extension Days: " + (30 - totalExtendedDays)
        );
    }
    public static void setNextId(int next) {
        nextLoanID = next + 1;
    }
}
