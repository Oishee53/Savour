import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Main main=new Main();
    FileHandling fileHandling=new FileHandling();
    static LoadData loadData= new LoadData();
    static UserManager userManager= new UserManager();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        String[] csvFiles={
                "UserFile.csv",
                "MaleExercise.csv",
                "FemaleExercise.csv",
                "WorkoutFile.csv",
        };
        for (String csvFile : csvFiles) {
            createCSVFile(csvFile);
        }
        loadData.LoadUserDetails(userManager);
        main.consoleApp();
      }



      



    public void consoleApp() throws IOException {
        System.out.println("Workout and Diet Tracker\n");
        System.out.println("Enter your choice:");
        System.out.println("1.Register as new member");
        System.out.println("2.Login");
        int initialChoice=scanner.nextInt();
        if(initialChoice==1){

            registration();

        } else if (initialChoice==2) {

            login();

        }


    }
    private static void createCSVFile (String fileName){
        File file = new File(fileName);
        try {
            // Create the file
            if (file.createNewFile()) {
                System.out.println("CSV file created: " + file.getName());
            } else {
                // System.out.println("CSV file already exists: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating: " + fileName);
            e.printStackTrace();
        }
    }
    public void registration(){
        System.out.println("Enter Name:");
        String name=scanner.next();
        System.out.println("Enter email:");
        String email= scanner.next();
        System.out.println("Enter Password");
        String password=scanner.next();
        System.out.println("Select Gender: \n1.Male\n2.Female");
        int selectGender = scanner.nextInt();
        String gender = null;
        while(selectGender!=1 && selectGender!=2){
            System.out.println("Invalid choice!");
            System.out.println("Select Gender: \n1.Male\n2.Female");
            selectGender = scanner.nextInt();
        }
        if(selectGender == 1){
            gender = "Male";
        }
        else if(selectGender == 2){
            gender = "Female";
        }
        System.out.println("Enter Age");
        int age=scanner.nextInt();
        System.out.println("Enter Weight:");
        int weight=scanner.nextInt();
        System.out.println("Enter Height in cm:");
        int height=scanner.nextInt();
        System.out.println("Select goal\n1.Strength Building\n2.Weightloss");
        int selectGoal = scanner.nextInt();
        String goal = null;
        while(selectGoal!=1 && selectGoal!=2){
            System.out.println("Invalid choice!");
            System.out.println("Select goal\n1.Strength Building\n2.Weightloss");
            selectGoal = scanner.nextInt();
        }
        if(selectGoal==1){
            goal = "StrengthBuilding";
        }
        if(selectGoal==2){
            goal = "WeightLoss";
        }
        User user= new User(name,email,password,gender,age,weight,height,goal);
        userManager.addUsers(user);
        fileHandling.writeUsers(userManager.getUserList(),false);

        System.out.println("Registered successfully!!");
    }
    public void login(){



    }
}