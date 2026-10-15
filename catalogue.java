
import java.io.*;

public class catalogue {
    private Book[] books;
    private int bookCount;
    private final String FILE_NAME = "books.txt";
    
    // For book category
    private int fantasyCount = 0;
    private int historyCount = 0;
    private int actionCount = 0;
    private int programmingCount = 0;
    private int fictionCount = 0;
    private int scienceCount = 0;
    private int biographyCount = 0;
    private int generalCount = 0;
    
    public catalogue() {
        books = new Book[100];
        bookCount = 0;
    }

    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < bookCount; i++) {
                writer.println(books[i].toFileFormat());
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 7) {
                    Book book = new Book(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]));
                    book.setIsAvailable(Boolean.parseBoolean(parts[6]));
                    books[bookCount++] = book;

                    updateCategoryCount(book.getItemID(), book.getCategory());
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private void updateCategoryCount(String bookID, String category) {
    // Extract number from ID (e.g., F001 -> 1, F010 -> 10)
    int number = extractNumberFromID(bookID);
    
    switch (category.toLowerCase()) {
        case "fantasy":
            if (number > fantasyCount) fantasyCount = number;
            break;
        case "history":
            if (number > historyCount) historyCount = number;
            break;
        case "action":
            if (number > actionCount) actionCount = number;
            break;
        case "programming":
            if (number > programmingCount) programmingCount = number;
            break;
        case "fiction":
            if (number > fictionCount) fictionCount = number;
            break;
        case "science":
            if (number > scienceCount) scienceCount = number;
            break;
        case "biography":
            if (number > biographyCount) biographyCount = number;
            break;
        default:
            if (number > generalCount) generalCount = number;
            break;
    }
}

    private int extractNumberFromID(String bookID) {
        // Extract digits from ID (e.g., F001 -> 1, FI010 -> 10)
        String numberPart = bookID.replaceAll("[^0-9]", "");
        if (numberPart.isEmpty()) return 0;
        return Integer.parseInt(numberPart);
}

    private String generateBookID(String category) {
        String prefix;
        int nextNumber;
        
        switch (category.toLowerCase()) {
            case "fantasy":
                prefix = "F";
                fantasyCount++;
                nextNumber = fantasyCount;
                break;
            case "history":
                prefix = "H";
                historyCount++;
                nextNumber = historyCount;
                break;
            case "action":
                prefix = "A";
                actionCount++;
                nextNumber = actionCount;
                break;
            case "programming":
                prefix = "P";
                programmingCount++;
                nextNumber = programmingCount;
                break;
            case "fiction":
                prefix = "FI";
                fictionCount++;
                nextNumber = fictionCount;
                break;
            case "science":
                prefix = "S";
                scienceCount++;
                nextNumber = scienceCount;
                break;
            case "biography":
                prefix = "B";
                biographyCount++;
                nextNumber = biographyCount;
                break;
            default:
                prefix = "G";
                generalCount++;
                nextNumber = generalCount;
                break;
        }
        
        // Generate number (001, 002, 003...)
        String number = String.format("%03d", nextNumber);
        
        return prefix + number;
    }
    
    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
    

    // ADD BOOK -------------------------------------------------------------------------------------------------
    public void addBook(Book book) {
        if (bookCount < 100) {
            String newID = generateBookID(book.getCategory());
            
            Book newBook = new Book(book.getTitle(), newID, book.getAuthor(), 
                                    book.getCategory(), book.getPublisher(), book.getPublisherYear());
            
            books[bookCount] = newBook;
            bookCount++;
            saveToFile();
            
            System.out.println("Book added! ID: " + newID + " (Auto-generated from category: " + book.getCategory() + ")");
        } else {
            System.out.println("The catalog is full!");
        }
    }
    

    // UPDATE BOOK WITH NO ID CHANGE-----------------------------------------------------------------------------------------------------
    public void updateBookWithoutIDChange(String itemID, String newTitle, String newAuthor, String newCategory) {
        Book book = findBookID(itemID);
        if (book != null) {
            String oldCategory = book.getCategory();
            String oldID = book.getItemID();
            
            if (!newTitle.isEmpty()) book.setTitle(newTitle);
            if (!newAuthor.isEmpty()) book.setAuthor(newAuthor);
            if (!newCategory.isEmpty() && !newCategory.equalsIgnoreCase(oldCategory)) {
                book.setCategory(newCategory);
                
                String newID = generateBookID(newCategory);
                book.setItemID(newID);
                
                System.out.println("Category changed from '" + oldCategory + "' to '" + newCategory + "'");
                System.out.println("Book ID automatically updated from " + oldID + " to " + newID);
            } else if (!newCategory.isEmpty() && newCategory.equalsIgnoreCase(oldCategory)) {
                System.out.println("Category unchanged, keeping ID: " + oldID);
            }
            
            System.out.println("Book update has been successfully!");
        } else {
            System.out.println("The book ID has not been found");
        }
        saveToFile();
    }
    
    // Keep old updateBook method for compatibility UPDATE BOOK--------------------------------------------------------------------------------
    public void updateBook(String itemID, String newTitle, String newAuthor, String newCategory) {
        updateBookWithoutIDChange(itemID, newTitle, newAuthor, newCategory);
    }
    

    // FIND BOOK----------------------------------------------------------------------------------------------
    public Book findBookID(String itemID) {   
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getItemID().equalsIgnoreCase(itemID)) {
                return books[i];
            }
        }
        return null;
    }

    
    // VIEW BOOK-----------------------------------------------------------------------------------------------------
    public void viewBook() {
        System.out.println("\n------ ALL BOOKS -----");
        if (bookCount == 0) {
            System.out.println("There's no book anymore.");
            return;
        }
        
        System.out.println("+----------------------+----------+--------------+----------------------+-----------------+----------------------+--------+");
        System.out.printf("| %-20s | %-8s | %-12s | %-20s | %-15s | %-20s | %-6s |\n", 
                      "Title", "ID", "Status", "Author", "Category", "Publisher", "Year");
        System.out.println("+----------------------+----------+--------------+----------------------+-----------------+----------------------+--------+");
        
        for (int i = 0; i < bookCount; i++) {
        Book b = books[i];
        System.out.printf("| %-20s | %-8s | %-12s | %-20s | %-15s | %-20s | %-6d |\n",
            truncate(b.getTitle(), 20),
            b.getItemID(),
            b.getAvailability() ? "Available" : "On loan",
            truncate(b.getAuthor(), 20),
            truncate(b.getCategory(), 15),
            truncate(b.getPublisher(), 20),
            b.getPublisherYear());
        }
    
        System.out.println("+----------------------+----------+--------------+----------------------+-----------------+----------------------+--------+");
        System.out.println("Total books: " + bookCount);
    }

    
    // SEARCH BOOK--------------------------------------------------------------------------------------
    public void searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Please enter a keyword to search!");
            return;
        }
        
        boolean found = false;
        System.out.println("\n====== SEARCH RESULTS (Keyword: " + keyword + ") ======\n");
        
        for (int i = 0; i < bookCount; i++) {
            Book book = books[i];
            
            // Check if keyword matches ANY field
            if (book.getItemID().toLowerCase().contains(keyword.toLowerCase()) ||
                book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
                book.getCategory().toLowerCase().contains(keyword.toLowerCase())) {
                
                book.displayInfo();
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No books found matching: " + keyword);
            System.out.println("Try searching by ID, Title, Author, or Category.");
        }
    }
    

    // DELETE BOOK ----------------------------------------------------------------------------------------------------------
    public void deleteBook(String itemID) {
        int index = -1;
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getItemID().equalsIgnoreCase(itemID)) {
                index = i;
                break;
            }
        }
        
        if (index != -1) {
            for (int i = index; i < bookCount - 1; i++) {
                books[i] = books[i + 1];
            }
            bookCount--;
            saveToFile();

            System.out.println("The book has been deleted successfully!");
        } else {
            System.out.println("The book is not found!");
        }
    }
    


    // BORROW BOOK-----------------------------------------------------------------------------------------------------------
    public void borrowBook(String itemID) {
        Book book = findBookID(itemID);
        
        if (book != null) {
            if (book.getAvailability()) {
                book.setIsAvailable(false);
                System.out.println("Book borrowed: " + book.getTitle());
                saveToFile();  // Make sure this runs
                System.out.println("Book status saved to file."); // Debug
            } else {
                System.out.println("The book is not available to borrow!");
            }
        } else {
            System.out.println("The book has not been found!");
        }
    }
    

    // RETURN BOOK-------------------------------------------------------------------------------------------------
    public void returnBook(String itemID) {
        Book book = findBookID(itemID);
        
        if (book != null) {
            if (!book.getAvailability()) {
                book.setIsAvailable(true);
                System.out.println("Book returned: " + book.getTitle());
            } else {
                System.out.println("The book is already available!");
            }
        } else {
            System.out.println("The book is not found");
        }
        saveToFile();
    }
    
    public int getBookCount() {
        return bookCount;
    }
}
