import java.util.List;
public class ContainsName {
    public static boolean containsName(List<String> list, String target) {
        return list.stream().anyMatch(str -> str.contains(target));
    }
}
