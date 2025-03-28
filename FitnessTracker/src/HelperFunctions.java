import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HelperFunctions {
    public static void LoadUserDetails(UserManager userManager) {//load data from file to array members
        String userFilePath = "UserFile.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(userFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");


                // Check the array length to prevent out-of-bounds errors
                if (details.length < 8) {
                    System.out.println("Skipping invalid member record: " + line);
                    continue;
                }

                try {
                    // Parse numeric values with error handling
                    int age = Integer.parseInt(details[4]);
                    int weight = Integer.parseInt(details[5]);
                    double height = Double.parseDouble(details[6]);

                    // Create the Member object
                    User user = new User(details[0], details[1], details[2],
                            details[3],age,weight,height, details[7]);

                    userManager.addUsers(user);

                } catch (NumberFormatException ex) {
                    System.out.println("Skipping user with invalid numeric data: " + line);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error reading user data file: " + ex.getMessage());
        }
    }

    public static boolean authenticateLogin(String email, String password, String filename) {
        String storedEmail = null;
        String storedPassword = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length < 6) {
                    continue;
                }
                storedEmail = details[1].trim();
                storedPassword = details[2].trim();



                if (storedEmail.equals(email) && storedPassword.equals(password)) {

                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }




}
