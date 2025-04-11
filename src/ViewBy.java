import java.util.List;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ViewBy {/**
     Filters the given list using the provided predicate;
     @param items     The list of items to filter.
     @param predicate The condition for filtering.
     @param <T>       The type of items.
     @return A list containing only the items that match the predicate.
     **/
	
    public static <T> List<T> filter(List<T> items, Predicate<T> predicate) {
        return items.stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
    }
    
    /**
     * Filters and sorts the given list.
     *
     * @param items      The list of items to filter.
     * @param predicate  The condition for filtering.
     * @param comparator The comparator to sort the filtered items.
     * @param <T>        The type of items.
     * @return A sorted list of items that match the predicate.
     */
    public static <T> List<T> filterAndSort(List<T> items, Predicate<T> predicate, Comparator<T> comparator) {
        return items.stream()
                    .filter(predicate)
                    .sorted(comparator)
                    .collect(Collectors.toList());
    }
    
    /**
     * fuzzy match on each item so that we can have a query for case insensitive queries
     *
     * @param items     The list of items to search.
     * @param extractor A function that extracts a string from each item (e.g. projectName).
     * @param query     The user’s partial search text.
     * @return          A list of items where extractor(item) contains query (ignoring case).
     */
    public static <T> List<T> fuzzyFilter(
            List<T> items,
            Function<T, String> extractor,
            String query
    ) {
        if (query == null || query.isBlank()) {
            return List.of(); // return empty if no query
        }
        String lowerQuery = query.toLowerCase();

        return items.stream()
                .filter(item -> {
                    String text = extractor.apply(item);
                    if (text == null) return false;
                    return text.toLowerCase().contains(lowerQuery);
                })
                .collect(Collectors.toList());
    }

    /**
     * A combined fuzzy filter + sorting method if desired.
     * E.g., fuzzy search by project name, then sort alphabetically.
     */
    public static <T> List<T> fuzzyFilterAndSort(
            List<T> items,
            Function<T, String> extractor,
            String query,
            Comparator<T> comparator
    ) {
        return fuzzyFilter(items, extractor, query).stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
    
}
