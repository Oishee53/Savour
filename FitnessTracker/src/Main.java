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
        System.out.println(" _   _       _        _ _____ _ _   \n" +
                "| \\ | |_   _| |_ _ __(_)  ___(_) |_ \n" +
                "|  \\| | | | | __| '__| | |_  | | __|\n" +
                "| |\\  | |_| | |_| |  | |  _| | | |_ \n" +
                "|_| \\_|\\__,_|\\__|_|  |_|_|   |_|\\__|");
            while(true) {
                System.out.println("Enter your choice:");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                int initialChoice = scanner.nextInt();
                if (initialChoice == 1) {
                    registration();
                } else if (initialChoice == 2) {
                    login("UserFile.csv");
                } else if (initialChoice == 3) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            }

    }

    private static void createCSVFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("CSV file created: " + file.getName());
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
        } else {
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
        } else {
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
            MainFunctions(loginEmail, loginPassword);
        } else {
            System.out.println("Wrong email or password:");
        }
    }

    public void MainFunctions(String loginEmail, String loginPassword) throws IOException {

            System.out.println("\nMain Menu:");
            System.out.println("1. View Your Details");
            System.out.println("2. View your customized plan");
            System.out.println("3. Dashboard");
            System.out.println("4. Workouts");
            System.out.println("5. Set timer for workout");
            System.out.println("6. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                fileHandling.readUserDetails(loginEmail, loginPassword);
                MainFunctions(loginEmail,loginPassword);
            } else if (choice == 2) {
                suggestionOnGoal(loginEmail);
                MainFunctions(loginEmail,loginPassword);
            } else if (choice == 3) {
                DashBoardOnProfile(loginEmail);
                MainFunctions(loginEmail,loginPassword);
            } else if (choice == 4) {
                handleWorkoutMenu(loginEmail);
                MainFunctions(loginEmail,loginPassword);
            } else if (choice == 5) {
                System.out.println("Enter number of minutes you want to workout:");
                int time = scanner.nextInt();
                scanner.nextLine(); // clear buffer
                int flag=workoutTimer.startTimer(time);
                if(flag==1)
                    MainFunctions(loginEmail,loginPassword);

            } else if (choice == 6) {
                System.out.println("Logged out successfully.\n");
            } else {
                System.out.println("Invalid choice, try again.");
            }

    }

    public void handleWorkoutMenu(String loginEmail) throws FileNotFoundException {
        while (true) {
            System.out.println("\nWorkout Menu:");
            System.out.println("1. Post new workout");
            System.out.println("2. Past workouts");
            System.out.println("3. Back to main menu");
            int workoutChoice = scanner.nextInt();
            if (workoutChoice == 1) {
                postNewWorkout(loginEmail);
            } else if (workoutChoice == 2) {
                pastWorkout(loginEmail);
            } else if (workoutChoice == 3) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    public void suggestionOnGoal(String loginEmail) {
        for (User user : userManager.getUserList()) {
            if (user.getEmail().equalsIgnoreCase(loginEmail)) {
                fitnessGoal.suggestion(user, tdeeCalculator);
                return;
            }
        }
    }

    public void DashBoardOnProfile(String loginEmail) {
        for (User user : userManager.getUserList()) {
            if (user.getEmail().equalsIgnoreCase(loginEmail)) {
                dashboard.graph(user);
            }
        }
    }

    public void postNewWorkout(String loginEmail) throws FileNotFoundException {
        for (User user : userManager.getUserList()) {
            if (user.getEmail().equalsIgnoreCase(loginEmail)) {
                workout.NewWorkout(user, scanner);
            }
        }
    }

    public void pastWorkout(String loginEmail) {
        for (User user : userManager.getUserList()) {
            if (user.getEmail().equalsIgnoreCase(loginEmail)) {
                workout.pastWorkout(user);
            }
        }
    }
}
