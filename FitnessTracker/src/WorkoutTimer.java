import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkoutTimer {
    Main main;
    private Thread timerThread;
    private boolean running = false;

    public int startTimer(int minutes) {
        AtomicInteger flag = new AtomicInteger();

        if (running) {
            System.out.println("Timer is already running!");
            return 0;
        }

        running = true;
        System.out.println("Workout timer started for " + minutes + " min...");

        // Timer thread
        timerThread = new Thread(() -> {
            try {
                for (int i = minutes; i > 0 && running; i--) {
                    System.out.println("Time left: " + i + " min");
                    Thread.sleep(60000); // sleep for 1 minute
                }

                if (running) {
                    System.out.println("Workout complete!");
                }

                running = false;
            } catch (InterruptedException e) {
                System.out.println("Timer interrupted.");
            }
        });

        timerThread.start();

        // Input thread to stop the timer
        // Input thread to stop the timer
        Thread inputThread = new Thread(() -> {
            System.out.println("Enter 1 to stop the timer:");
            Scanner scanner = new Scanner(System.in);  // Use Scanner, not BufferedReader
            while (running) {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    if (input.trim().equals("1")) {
                        int stopFlag = stopTimer();
                        flag.set(stopFlag);
                        break;
                    }
                }
            }
            // DO NOT close the scanner here
        });

        inputThread.start();

        try {
            // wait for both threads to finish
            timerThread.join();
            inputThread.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }

        return flag.get();
    }


    public int stopTimer() {
        int flag=0;
        if (running) {
            running = false;
            if (timerThread != null) {
                timerThread.interrupt();
                flag=1;
            }
            System.out.println("Workout timer stopped.");
        }
        return flag;
    }

}
