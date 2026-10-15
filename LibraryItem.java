public class LibraryItem {
    private String title;
    private String itemID;
    private boolean isAvailable;
    
    public LibraryItem() {
        title = "";
        itemID = "";
        isAvailable = false;
    }
    
    public LibraryItem(String title, String itemID, boolean isAvailable) {
        this.title = title;
        this.itemID = itemID;
        this.isAvailable = true;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getItemID() {
        return itemID;
    }
    
    public boolean getAvailability() {
        return isAvailable;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
    
    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    public String toString() {
        String status = isAvailable ? "Available" : "On loan/Damaged";
        return String.format("%-20s %-5s %-15s", title, itemID, status);
    }
}
