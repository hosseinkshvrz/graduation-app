package webapp;


import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.PersianCalendar;

public class Date {
    public static String getCurrentTimeAndDate() {
        PersianCalendar persianCalendar = new PersianCalendar(new java.util.Date());
        int year = persianCalendar.get(Calendar.YEAR);
        int month = persianCalendar.get(Calendar.MONTH) + 1;
        int day = persianCalendar.get(Calendar.DAY_OF_MONTH);
        String date = year + "-" + month + "-" + day;
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDateTime now = LocalDateTime.now();
//        String date = dtf.format(now);
        return date;
    }
}
