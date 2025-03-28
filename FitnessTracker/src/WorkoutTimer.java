import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
public class WorkoutTimer {
    private Timer timer;
    private int timeLeft;
    private boolean running;




    public void startTimer(int hours,Scanner scanner) {
        if (running) {
            System.out.println("Timer is already running!");
            return;
        }

        this.timeLeft = hours;
        this.timer = new Timer();
        running = true;

        System.out.println("Workout timer started for " + hours + " hours...");

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (timeLeft > 0) {
                    System.out.println("Time left: " + timeLeft + " min");
                    timeLeft--;
                } else {
                    System.out.println("Workout complete!");
                    stopTimer(); // Auto-stop when the timer reaches 0
                }
            }
        };

        timer.scheduleAtFixedRate(task, 0, 1000*60*60);

        new Thread(() -> {
            while (running) {
                System.out.println("Enter 1 to stop the timer:");
                int stop = scanner.nextInt();
                if (stop == 1) {
                    stopTimer();
                }
            }
        }).start();
    }

    public synchronized void stopTimer() {
        if (running) {
            running = false;
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            System.out.println("Workout timer stopped.");
        }
    }

}
