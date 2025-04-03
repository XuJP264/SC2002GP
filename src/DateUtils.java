import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateUtils {
    // 支持 yyyy/M/d、yyyy/MM/dd、yyyy-M-d、yyyy-MM-dd 四种格式
    private static final DateTimeFormatter FORMATTER1 = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4)
            .appendLiteral('/')
            .appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, java.time.format.SignStyle.NOT_NEGATIVE)
            .appendLiteral('/')
            .appendValue(ChronoField.DAY_OF_MONTH, 1, 2, java.time.format.SignStyle.NOT_NEGATIVE)
            .toFormatter();

    private static final DateTimeFormatter FORMATTER2 = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4)
            .appendLiteral('-')
            .appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, java.time.format.SignStyle.NOT_NEGATIVE)
            .appendLiteral('-')
            .appendValue(ChronoField.DAY_OF_MONTH, 1, 2, java.time.format.SignStyle.NOT_NEGATIVE)
            .toFormatter();

    // 解析日期字符串
    private static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, FORMATTER1);
        } catch (Exception e1) {
            try {
                return LocalDate.parse(dateStr, FORMATTER2);
            } catch (Exception e2) {
                throw new IllegalArgumentException("Invalid date format: " + dateStr);
            }
        }
    }

    // 判断时间区间是否有重叠
    public static boolean isOverlapping(String start1, String end1, String start2, String end2) {
        LocalDate s1 = parseDate(start1);
        LocalDate e1 = parseDate(end1);
        LocalDate s2 = parseDate(start2);
        LocalDate e2 = parseDate(end2);

        return !(e1.isBefore(s2) || e2.isBefore(s1));
    }

    public static void main(String[] args) {
        System.out.println(isOverlapping("2024/3/20", "2024/3/25", "2024/03/23", "2024/03/30")); // true
        System.out.println(isOverlapping("2024-03-20", "2024-3-25", "2024-3-26", "2024-3-30")); // false
        System.out.println(isOverlapping("2024-1-1", "2024-12-31", "2024/6/15", "2024/7/15")); // true
    }
}
