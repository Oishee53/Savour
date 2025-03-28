import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandling {

    public void readFile(String filename)  {
        // Try-with-resources to ensure the file is closed automatically
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;

            // Read each line from the file until the end
            while ((line = bufferedReader.readLine()) != null) {
                // Process each line as needed; here, we just print it
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void writeUsers(ArrayList<User> users, boolean append) {
        append = false;
        FileWriter userFileWriter = null;
        try {
            // Open the file in append mode if specified
            userFileWriter = new FileWriter("UserFile.csv",append);


            // Write member details to the file
            for (User user : users) {
                userFileWriter.write(user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," +
                        user.getGender() + "," + user.getAge() + "," + user.getWeight() +
                        "," + user.getHeight() + "," + user.getGoal()+ "\n");
            }

            // Ensure data is written to the file
            userFileWriter.flush();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        } finally {

            try {
                if (userFileWriter != null) {
                    userFileWriter.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing the file writer: " + e.getMessage());
            }
        }
    }
}
