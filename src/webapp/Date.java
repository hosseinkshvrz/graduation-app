package webapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Date {
    public static String getCurrentTimeAndDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
