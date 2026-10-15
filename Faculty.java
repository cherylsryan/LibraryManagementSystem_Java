import java.time.LocalDate;


public class Faculty extends User {

    public Faculty(String id, String name, String password) {
        super(id, name, "FACULTY", password);
    }

    public Faculty(String id, String name, String password, LocalDate d) {
        super(id, name, "FACULTY", password, d);
    }

    public LocalDate getExpiryDate() {
        return registrationDate.plusYears(10);
    }
    
    @Override
    public int getMaxBorrowDays() {
        return 30;  // Faculty can borrow for 30 days
    }

}