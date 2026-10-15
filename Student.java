import java.time.LocalDate;

public class Student extends User {

    public Student(String id, String name, String password) {
        super(id, name, "STUDENT", password);
    }

    public Student(String id, String name, String password, LocalDate d) {
        super(id, name, "STUDENT", password, d);
    }

    public LocalDate getExpiryDate() {
        return registrationDate.plusYears(4);
    }

    @Override
    public int getMaxBorrowDays() {
        return 14;  // Students can borrow for 14 days
    }
}