import java.util.List;
import java.util.function.Predicate;
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
}
