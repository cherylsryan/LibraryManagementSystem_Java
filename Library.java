///// FOR TESTING ONLY
public class Library {
    public static void main(String[] args) {
        String[] books = {"A","B", "C", "D", "E"};
        int bookCount = 5;
        int index = 2;
        
        for(int i = index; i < bookCount - 1; i++) {
            books[i] = books[i + 1];
        }
        
        bookCount--;
        
        for(int i = 0; i < bookCount; i++) {
            System.out.println(books[i]);
        }
    }
}
