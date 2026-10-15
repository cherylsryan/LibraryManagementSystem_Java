import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FileHandler {

    private static final String STUDENT_FILE = "students.txt";
    private static final String FACULTY_FILE = "faculties.txt";
    private static final String PUBLIC_FILE = "public_member.txt";

    private static String getFileName(User user) {
        if (user instanceof Student) {
            return STUDENT_FILE;
        } else if (user instanceof Faculty) {
            return FACULTY_FILE;
        } else if (user instanceof PublicMember) {
            return PUBLIC_FILE;
        }
        return null;
    }
    
    // Get filename based on ID prefix
    private static String getFileNameById(String id) {
        if (id == null) return null;
        
        if (id.toUpperCase().startsWith("STU")) {
            return STUDENT_FILE;
        } else if (id.toUpperCase().startsWith("FAC")) {
            return FACULTY_FILE;
        } else if (id.toUpperCase().startsWith("PUB")) {
            return PUBLIC_FILE;
        }
        return null;
    }

    // Get all users from all files
    public static List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        list.addAll(getUsersFromFile(STUDENT_FILE, "STUDENT"));
        list.addAll(getUsersFromFile(FACULTY_FILE, "FACULTY"));
        list.addAll(getUsersFromFile(PUBLIC_FILE, "PUBLIC"));
        return list;
    }

    // Get users from a specific file
    private static List<User> getUsersFromFile(String filename, String userType) {
        List<User> list = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                
                switch (userType) {
                    case "STUDENT":
                        list.add(new Student(p[0], p[1], p[3], LocalDate.parse(p[4])));
                        break;
                    case "FACULTY":
                        list.add(new Faculty(p[0], p[1], p[3], LocalDate.parse(p[4])));
                        break;
                    case "PUBLIC":
                        list.add(new PublicMember(p[0], p[1], p[3], LocalDate.parse(p[4])));
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet - that's fine
        } catch (Exception e) {
            System.out.println("Error reading file: " + filename);
        }
        
        return list;
    }

    // Find user by ID from the correct file
    public static User findUserById(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }

        String filename = getFileNameById(id);
        if (filename == null) return null;

        String userType;
        if (id.toUpperCase().startsWith("STU")) {
            userType = "STUDENT";
        } else if (id.toUpperCase().startsWith("FAC")) {
            userType = "FACULTY";
        } else if (id.toUpperCase().startsWith("PUB")) {
            userType = "PUBLIC";
        } else {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p[0].equals(id)) {
                    switch (userType) {
                        case "STUDENT":
                            return new Student(p[0], p[1], p[3], LocalDate.parse(p[4]));
                        case "FACULTY":
                            return new Faculty(p[0], p[1], p[3], LocalDate.parse(p[4]));
                        case "PUBLIC":
                            return new PublicMember(p[0], p[1], p[3], LocalDate.parse(p[4]));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet
        } catch (Exception e) {
            System.out.println("Error finding user: " + id);
        }

        return null;
    }

    // Save user to the appropriate file based on their type
    public static void saveUser(User user) {
        String filename = getFileName(user);
        if (filename == null) {
            System.out.println("Error: Cannot determine file for user type");
            return;
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.write(user.toTxtLine());
            bw.newLine();
        } catch (Exception e) {
            System.out.println("Error saving user to " + filename);
        }
    }

    // Delete user from the appropriate file
    public static void deleteUser(String id) {
        String filename = getFileNameById(id);
        if (filename == null) return;
        
        List<User> users = getUsersFromFile(filename, getUserTypeFromId(id));
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (User u : users) {
                if (!u.getId().equals(id)) {
                    bw.write(u.toTxtLine());
                    bw.newLine();
                }
            }
        } catch (Exception e) {
            System.out.println("Error deleting user from " + filename);
        }
    }

    // Update password in the appropriate file
    public static void updatePassword(String id, String newPass) {
        String filename = getFileNameById(id);
        if (filename == null) return;
        
        List<User> users = getUsersFromFile(filename, getUserTypeFromId(id));
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (User u : users) {
                if (u.getId().equals(id)) {
                    u.updatePassword(newPass);
                }
                bw.write(u.toTxtLine());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error updating password in " + filename);
        }
    }
    
    // Helper method to get user type from ID
    private static String getUserTypeFromId(String id) {
        if 
           (id.toUpperCase().startsWith("STU")) return "STUDENT";
        if 
           (id.toUpperCase().startsWith("FAC")) return "FACULTY";
        if 
           (id.toUpperCase().startsWith("PUB")) return "PUBLIC";
        return "UNKNOWN";
    }
}