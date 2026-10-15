import java.time.LocalDate;

public abstract class User {

    protected String id, name, role, password;
    protected LocalDate registrationDate;
    protected int loanDuration;

    public User(String id, String name, String role, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
        this.registrationDate = LocalDate.now();
    }

    public User(String id, String name, String role, String password, LocalDate date) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
        this.registrationDate = date;
    }

    // Getters
    public String getId() { 
        return id;
    }
    
    public String getName() { 
        return name;
    }
    
    public String getRole() { 
        return role;
    }
    
    public String getPassword() {
        return password;
    }
    
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
    
    public int getLoanDuration() {
        return loanDuration;
    }
    
    // Setters
    public void setId(String id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public void setLoanDuration(int loanDuration) {
        this.loanDuration = loanDuration;
    }

    public boolean validatePassword(String pass) {
        return password.equals(pass);
    }

    public void updatePassword(String newPass) {
        password = newPass;
    }

    // Abstract methods
    public abstract LocalDate getExpiryDate();
    public abstract int getMaxBorrowDays();

    public String toTxtLine() {
        return id + "|" + name + "|" + role + "|" + password + "|" + registrationDate;
    }
}  // ADD THIS CLOSING BRACE