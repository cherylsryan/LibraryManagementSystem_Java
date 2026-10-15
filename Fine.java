public class Fine extends Record {
    
    private String userID;
    private int daysLate;
    private double amount;
    private boolean paid;
    private String paymentMethod;

    // Fine rate per day (RM 1)SS
    private static final double FINE_RATE = 1.0; 

    private static int nextFineID = 1;
    
    // Constructor
    public Fine(User user, int daysLate){
        this.id = "F" + String.format("%03d", nextFineID++);
        this.userID = user.getId();
        this.daysLate = daysLate;
        this.amount = calculateFine();
        this.paid = false;
    }

    public Fine(String userID, int daysLate){
        this.id = "F" + String.format("%03d", nextFineID++);
        this.userID = userID;
        this.daysLate = daysLate;
        this.amount = calculateFine();
        this.paid = false;
    }

    // Calculate fine
    private double calculateFine(){
        return daysLate * FINE_RATE;
    }

    // Pay fine
    public void payFine(String method){
        if (!paid) {
            paid = true;
            this.paymentMethod = method;
            System.out.println("Fine " + id + " paid successfully via " + method + "!");
        } 
        else {
            System.out.println("Fine already paid.");
        }
    }

    // Getters 
    public String getFineID() {
        return id;
    }
    
    public String getUserID(){ 
        return userID; 
    }
    
    public int getDaysLate(){ 
        return daysLate; 
    }
    
    public double getAmount(){ 
        return amount; 
    }
    
    public String getPaymentMethod(){
        return paymentMethod;
    }
    
    public boolean isPaid(){ 
        return paid; 
    }
    
    
    // Override (polymorphism)
    public String toString() {
        return String.format(
            "\n===== Fine Details =====\n" +
            "Fine ID   : %s\n" +
            "User ID   : %s\n" +
            "Late Days : %d\n" +
            "Amount    : RM%.2f\n" +
            "Status    : %s\n" +
            "Payment   : %s\n" +
            "-------------------------",
            id,
            userID,
            daysLate,
            amount,
            (paid ? "Paid" : "Unpaid"),
            (paid ? paymentMethod : "N/A")
        );
    }
    
    public void setAsPaid(String method) {
        this.paid = true;
        this.paymentMethod = method;
    }
    
    public static void setNextId(int next) {
        nextFineID = next + 1;
    } 
}
    
