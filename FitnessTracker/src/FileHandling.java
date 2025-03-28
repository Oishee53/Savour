import java.io.*;
import java.util.ArrayList;

public class FileHandling {

    public void readUserDetails(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("UserFile.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                String storedEmail = details[1];
                String storedPassword = details[2];
                if (storedEmail.equals(email) && storedPassword.equals(password)) {
                    String[][] table = {
                            {"Name", details[0]},
                            {"Email", details[1]},
                            {"Gender", details[3]},
                            {"Age", details[4]},
                            {"Weight", details[5]},
                            {"Height", details[6]},
                            {"Goal", details[7]},

                    };
                    // Calculate column widths
                    int fieldWidth = 0, valueWidth = 0;
                    for (String[] row : table) {
                        fieldWidth = Math.max(fieldWidth, row[0].length());
                        valueWidth = Math.max(valueWidth, row[1].length());
                    }

                   // Print the table with proper alignment
                    String format = "| %-" + fieldWidth + "s | %-" + valueWidth + "s |%n";
                    System.out.println("-".repeat(fieldWidth + valueWidth + 7));
                    for (String[] row : table) {
                        System.out.printf(format, row[0], row[1]);
                        System.out.println("-".repeat(fieldWidth + valueWidth + 7));
                    }

                }
            }
                }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





        public void writeUsers (ArrayList < User > users,boolean append){
            append = false;
            FileWriter userFileWriter = null;
            try {
                // Open the file in append mode if specified
                userFileWriter = new FileWriter("UserFile.csv", append);


                // Write member details to the file
                for (User user : users) {
                    userFileWriter.write(user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," +
                            user.getGender() + "," + user.getAge() + "," + user.getWeight() +
                            "," + user.getHeight() + "," + user.getGoal() + "\n");
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
        public String[] readCSVFilesBySplitting(String filename) {
            String[] data = new String[0];
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
                String line;

                // Read each line from the file until the end
                while ((line = bufferedReader.readLine()) != null) {
                    // Split the line into data columns
                    data = line.split(",");
                    return data;

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return data;
        }



}