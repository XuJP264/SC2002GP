import java.util.ArrayList;
import java.util.HashMap;

public class Booking {
    private static HashMap<Project,HashMap<Applicant,String>> bookings = new HashMap<>();
    public static void addBooking(Project project, Applicant applicant, String status) {
        if (!bookings.containsKey(project)) {
            bookings.put(project, new HashMap<>());
        }
        bookings.get(project).put(applicant, status);
    }
    public static String getBookingType(Project project, Applicant applicant) {
        if (!bookings.containsKey(project)) {
            return "No Booking";
        }
        if (!bookings.get(project).containsKey(applicant)) {
            return "No Booking";
        }
        return bookings.get(project).get(applicant);
    }
    public static void removeBooking(Project project, Applicant applicant) {
        if (!bookings.containsKey(project)) {
            return;
        }
        bookings.get(project).remove(applicant);
    }
}
