import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Main main = new Main();
    FileHandling fileHandling = new FileHandling();
    static HelperFunctions helperFunctions = new HelperFunctions();
    static UserManager userManager = new UserManager();
    FitnessGoal fitnessGoal= new FitnessGoal();
    TDEECalculator tdeeCalculator = new TDEECalculator();
    DashBoard dashboard= new DashBoard();
    Workout workout=new Workout();
    WorkoutTimer workoutTimer = new WorkoutTimer();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        String[] csvFiles = {
                "UserFile.csv",
                "MaleExercise.csv",
                "FemaleExercise.csv",
                "WorkoutFile.csv",
                "MetValueFile.csv"
        };
        for (String csvFile : csvFiles) {
            createCSVFile(csvFile);
        }
        helperFunctions.LoadUserDetails(userManager);
        main.consoleApp();
    }


    public void consoleApp() throws IOException {
        System.out.println("Workout and Diet Tracker\n");
        System.out.println("Enter your choice:");
        System.out.println("1.Register");
        System.out.println("2.Login");
        int initialChoice = scanner.nextInt();
        if (initialChoice == 1) {

            registration();

        } else if (initialChoice == 2) {

            login("UserFile.csv");

        }


    }

    private static void createCSVFile(String fileName) {
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

    public void registration() {
        System.out.println("Enter Name:");
        String name = scanner.next();
        System.out.println("Enter email:");
        String email = scanner.next();
        System.out.println("Enter Password");
        String password = scanner.next();
        System.out.println("Select Gender: \n1.Male\n2.Female");
        int selectGender = scanner.nextInt();
        String gender = null;
        while (selectGender != 1 && selectGender != 2) {
            System.out.println("Invalid choice!");
            System.out.println("Select Gender: \n1.Male\n2.Female");
            selectGender = scanner.nextInt();
        }
        if (selectGender == 1) {
            gender = "Male";
        } else if (selectGender == 2) {
            gender = "Female";
        }
        System.out.println("Enter Age");
        int age = scanner.nextInt();
        System.out.println("Enter Weight:");
        int weight = scanner.nextInt();
        System.out.println("Enter Height in cm:");
        int height = scanner.nextInt();
        System.out.println("Select goal\n1.Strength Building\n2.Weightloss");
        int selectGoal = scanner.nextInt();
        String goal = null;
        while (selectGoal != 1 && selectGoal != 2) {
            System.out.println("Invalid choice!");
            System.out.println("Select goal\n1.Strength Building\n2.Weightloss");
            selectGoal = scanner.nextInt();
        }
        if (selectGoal == 1) {
            goal = "StrengthBuilding";
        }
        if (selectGoal == 2) {
            goal = "WeightLoss";
        }
        User user = new User(name, email, password, gender, age, weight, height, goal);
        userManager.addUsers(user);
        fileHandling.writeUsers(userManager.getUserList(), false);

        System.out.println("Registered successfully!!");
    }

    public void login(String filename) throws IOException {
        System.out.print("Enter your email:\n");
        String loginEmail = scanner.next();
        System.out.print("Enter your password:\n");
        String loginPassword = scanner.next();
        boolean isAuthenticated = helperFunctions.authenticateLogin(loginEmail, loginPassword, filename);
        if (isAuthenticated) {
            System.out.println("Logged in successfully");
            MainFunctions(loginEmail,loginPassword);

        } else {
            System.out.println("Wrong email or password:");


        }
    }

    public void MainFunctions(String loginEmail, String loginPassword) throws IOException {
        System.out.println("1. View Your Details");
        System.out.println("2. View Suggestions Based On Your Goal");
        System.out.println("3..Dashboard ");
        System.out.println("4.Workouts");
        System.out.println("5.Set timer for workout");
        System.out.println("6. Logout");
        int Choice = scanner.nextInt();
        scanner.nextLine(); // Consume leftover newline
        if (Choice == 1) {

            fileHandling.readUserDetails(loginEmail, loginPassword);

        } else if (Choice == 2) {

            suggestionOnGoal(loginEmail);

        }


        else if (Choice==3) {
            DashBoardOnProfile(loginEmail);
        }
            else if (Choice == 4) {
                System.out.println("1.Post new workout");
                System.out.println("2.Past workouts");
                int WorkoutChoice = scanner.nextInt();
                if (WorkoutChoice == 1) {

                    //Post new workouts
                    postNewWorkout(loginEmail);

                }
                else if (WorkoutChoice == 2) {

                    //Past workouts
                    pastWorkout(loginEmail);

                }
            }
         else if (Choice==5) {
            System.out.println("Enter hours you wanna workout:");
            int time= scanner.nextInt();
            workoutTimer.startTimer(time,scanner);

        } else {
            main.consoleApp();
        }

        }


    public void suggestionOnGoal(String loginEmail){
        for (User user : userManager.getUserList()) {
            if (user.getEmail().equalsIgnoreCase(loginEmail)) {
                fitnessGoal.suggestion(user, tdeeCalculator);
                    return;
            }
            }
        }
        public void DashBoardOnProfile(String loginEmail){
        for (User user : userManager.getUserList()) {
                if (user.getEmail().equalsIgnoreCase(loginEmail)) {
                    dashboard.graph(user);
                }
            }
        }
        public void postNewWorkout(String loginEmail) throws FileNotFoundException {
            for (User user : userManager.getUserList()) {
                if (user.getEmail().equalsIgnoreCase(loginEmail)) {
                    workout.NewWorkout(user,scanner);
                }
            }
        }

        public void pastWorkout(String loginEmail){
            for (User user : userManager.getUserList()) {
                if (user.getEmail().equalsIgnoreCase(loginEmail)) {
                    workout.pastWorkout(user);
                }
            }


        }


    }

