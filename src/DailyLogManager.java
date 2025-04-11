import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;

public class DailyLogManager {
	/*
	 * static HashMap, with the outer map being keyed by Manager and the inner map being keyed by the LocalDate which helps maintain the log.
	 * Now we can log the entries as well as retrieve them. 
	 */
    private static final HashMap<Manager, HashMap<LocalDate, List<String>>> managerLogs = new HashMap<>();

    public static void addLogEntry(Manager manager, String entry) { // new log 
        LocalDate today = LocalDate.now();

        managerLogs.putIfAbsent(manager, new HashMap<>()); // makes sure it exists first
        managerLogs.get(manager).putIfAbsent(today, new ArrayList<>());

        managerLogs.get(manager).get(today).add(entry);
    }

    public static List<String> getLogEntries(Manager manager, LocalDate date) { //log retrieval
        if (!managerLogs.containsKey(manager)) {
            return Collections.emptyList();
        }
        if (!managerLogs.get(manager).containsKey(date)) {
            return Collections.emptyList();
        }
        return managerLogs.get(manager).get(date);
    }

    // public static void removeLogEntry(Manager manager, LocalDate date, String entry) { ... }
    // public static void clearLogs(Manager manager, LocalDate date) { ... }
}
