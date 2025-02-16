import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main class that implements a simple clock using multiple threads
 * to handle time updates and display.
 */
public class Clock {
    private volatile boolean running = true;
    private final SimpleDateFormat formatter;
    private String currentTime;
    
    public Clock() {
        // Initialize the date formatter with the specified format
        formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        currentTime = formatter.format(new Date());
    }
    
    /**
     * Updates the current time in the background
     */
    private class TimeUpdater implements Runnable {
        @Override
        public void run() {
            try {
                while (running) {
                    currentTime = formatter.format(new Date());
                    Thread.sleep(100); // Update every 100ms for precision
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Time updater thread interrupted: " + e.getMessage());
            }
        }
    }
    
    /**
     * Displays the current time to the console
     */
    private class TimeDisplay implements Runnable {
        @Override
        public void run() {
            try {
                while (running) {
                    System.out.print("\r" + currentTime); // \r for carriage return to update in place
                    Thread.sleep(1000); // Display update every second
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Display thread interrupted: " + e.getMessage());
            }
        }
    }
    
    /**
     * Starts the clock with appropriate thread priorities
     */
    public void start() {
        // Create and configure the updater thread (background priority)
        Thread updaterThread = new Thread(new TimeUpdater(), "TimeUpdater");
        updaterThread.setPriority(Thread.NORM_PRIORITY);
        
        // Create and configure the display thread (higher priority)
        Thread displayThread = new Thread(new TimeDisplay(), "TimeDisplay");
        displayThread.setPriority(Thread.MAX_PRIORITY);
        
        // Start both threads
        updaterThread.start();
        displayThread.start();
    }
    
    /**
     * Stops the clock gracefully
     */
    public void stop() {
        running = false;
    }
    
    public static void main(String[] args) {
        Clock clock = new Clock();
        clock.start();
        
        // Run for 60 seconds then stop (for demonstration)
        try {
            Thread.sleep(60000);
            clock.stop();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }
    }
}