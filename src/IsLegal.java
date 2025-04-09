import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface IsLegal {
    static final String REGEX = "^[ST]\\d{7}[A-Za-z]$";
    public static boolean isLegal(String username) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
