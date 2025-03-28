import java.io.*;
import java.util.Scanner;

public class Workout {
    public void NewWorkout(User user,Scanner scanner){
        String workout=null;
        String exercise = null;
        String filename=null;
        System.out.println("Enter Todays date\n");
        String date =scanner.next();

        System.out.println("Enter new workout:\n");

        if(user.getGender().equalsIgnoreCase("male"))
            filename="MaleExercise.csv";
        else filename="FemaleExercise.csv";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;

            // Read each line from the file until the end
            while ((line = bufferedReader.readLine()) != null) {
                // Split the line into data columns
                String[] data = line.split(",");

                // Check if the member's specific goal matches the first column (goal)
                if (user.getGoal().equalsIgnoreCase(data[0])) {
                    // Print the entire line if the goal matches
                    System.out.println("1."+data[1]+"\n2."+data[2]+"\n3."+data[3]+"\n");
                    int WorkoutChoice=scanner.nextInt();
                    if(WorkoutChoice==1) {
                        workout=data[1];
                    }
                    else if (WorkoutChoice==2) {
                        workout= data[2];
                    }
                    else if (WorkoutChoice==3) {
                        workout= data[3];
                    }
                    System.out.println("Enter the duration in hours");
                    int duration=scanner.nextInt();
                    double burnedCalorie=BurnedCalorieCalculation(workout, user.getGender(), duration, user.getWeight());

                    FileWriter WorkoutFilewriter = null;
                    try{
                        WorkoutFilewriter = new FileWriter("WorkoutFile.csv",true);
                        WorkoutFilewriter.write(user.getEmail() + "," +date+","+ exercise + "," +
                                duration + "," + burnedCalorie + "\n");
                        WorkoutFilewriter.flush();
                    }
                    catch (IOException e) {
                        System.out.println("An error occurred while writing to the file: " + e.getMessage());
                    }
                    finally {
                        try {
                            if (WorkoutFilewriter != null) {
                                WorkoutFilewriter.close();
                            }
                        } catch (IOException e) {
                            System.out.println("Error closing the file writer: " + e.getMessage());
                        }
                    }
                    //break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }





    }
    public double BurnedCalorieCalculation(String workout,String gender,int duration,int weight) throws FileNotFoundException {
        double metValue = 0;
        double number = 0;
        double burnedCalorie = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("MetValueFile.csv"))) {
            String line;

            // Read each line from the file until the end
            while ((line = bufferedReader.readLine()) != null) {
                // Split the line into data columns
                String[] data = line.split(",");
                if(workout.equalsIgnoreCase(data[0]) && gender.equalsIgnoreCase("male")){
                    number= Double.parseDouble(data[1]);
                    metValue=number;}
                else if (workout.equalsIgnoreCase(data[0]) && gender.equalsIgnoreCase("female")) {
                    number= Double.parseDouble(data[2]);
                    metValue=number;
                }
                burnedCalorie= weight*duration*metValue;


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return burnedCalorie;
    }







    public void pastWorkout(User user){
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("WorkoutFile.csv"))) {
            String line;

            // Read each line from the file until the end
            while ((line = bufferedReader.readLine()) != null) {
                // Split the line into data columns
                String[] data = line.split(",");

                // Check if the member's specific goal matches the first column (goal)
                if (user.getEmail().equalsIgnoreCase(data[0])) {
                    // Print the entire line if the goal matches
                    System.err.println("Date:"+data[1]+",Exercise:"+data[2]+",Duration:"+data[3]+" h"+",BurnedCalories:"+data[4]);

                }

            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

    }

}
