import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Log{

    private static final String LOG_FILE = "log.log";

    public static void start() {
        File logFile = new File(LOG_FILE);
        if (logFile.exists()) {
            logFile.delete(); // Delete the previous log file if it exists
        }
    }

    // This method will log the given message into the log file
    public static void log(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(LocalDateTime.now() + " - " + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
}
