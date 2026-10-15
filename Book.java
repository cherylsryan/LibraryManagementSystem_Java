public class Book extends LibraryItem {
    private String author;
    private String category;
    private String publisher;
    private int publisherYear;
        
    public Book(String title, String itemID, String author, String category, String publisher, int publisherYear) {
        super(title, itemID,true);
        this.author = author;
        this.category = category;
        this.publisher = publisher;
        this.publisherYear = publisherYear;
    }
        
    public String getAuthor() {
        return author;
    }
        
    public String getCategory() {
        return category;
    }
        
    public String getPublisher() {
        return publisher;
    }
        
    public int getPublisherYear() {
        return publisherYear;
    }
        
    public void setAuthor(String author) {
        this.author = author;
    }
        
    public void setCategory(String category) {
        this.category = category;
    }
        
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
        
    public void setPublisherYear(int publisherYear) {
        this.publisherYear = publisherYear;
    }
        
    @Override
    public String toString() {
        return String.format("%-20s %-5s %-15s %-20s %-15s %-4d",
            getTitle(), getItemID(), getAvailability() ? "Available" : "On loan",
            author, category, publisherYear);
    }

    public String toFileFormat() {
        return String.format("%s|%s|%s|%s|%s|%d|%b", 
        getTitle(), getItemID(), getAuthor(), getCategory(), 
        getPublisher(), getPublisherYear(), getAvailability());
    }
        
    //Display the info
    public void displayInfo() {
        System.out.println("------------------------------------------------");
        System.out.printf("| Book ID        : %40s |\n", getItemID());
        System.out.printf("| Title          : %40s |\n", getTitle());
        System.out.printf("| Author         : %40s |\n", author);
        System.out.printf("| Category       : %40s |\n", category);
        System.out.printf("| Publisher      : %40s |\n", publisher);
        System.out.printf("| Publisher Year : %40d |\n", publisherYear);
        System.out.println("------------------------------------------------");
    }
        
    public void displayTable() {
        System.out.printf("%-8s | %-25s | %-15s | %-12s | %-15s | %-4d\n",
                getItemID(), getTitle(), author, category, publisher, publisherYear);
    }
    
}
