import java.time.LocalDate;

public class PublicMember extends User {

    public PublicMember(String id, String name, String password) {
        super(id, name, "PUBLIC", password);
    }

    public PublicMember(String id, String name, String password, LocalDate d) {
        super(id, name, "PUBLIC", password, d);
    }

    public LocalDate getExpiryDate() {
        return registrationDate.plusYears(1);
    }
    
    @Override
    public int getMaxBorrowDays() {
        return 7;   // Public members borrow for 7 days
    }
}