import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateUtils {
    // 定义灵活的日期格式，支持yyyy/M/d和yyyy/MM/dd
    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4)
            .appendLiteral('/')
            .appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, java.time.format.SignStyle.NOT_NEGATIVE)
            .appendLiteral('/')
            .appendValue(ChronoField.DAY_OF_MONTH, 1, 2, java.time.format.SignStyle.NOT_NEGATIVE)
            .toFormatter();

    // 静态方法判断两段时间是否有重合
    public static boolean isOverlapping(String start1, String end1, String start2, String end2) {
        // 解析字符串为 LocalDate
        LocalDate s1 = LocalDate.parse(start1, FORMATTER);
        LocalDate e1 = LocalDate.parse(end1, FORMATTER);
        LocalDate s2 = LocalDate.parse(start2, FORMATTER);
        LocalDate e2 = LocalDate.parse(end2, FORMATTER);

        // 判断是否有重叠（核心逻辑）
        return !(e1.isBefore(s2) || e2.isBefore(s1));
    }

    public static void main(String[] args) {
        // 测试案例
        System.out.println(isOverlapping("2024/3/20", "2024/3/25", "2024/03/23", "2024/03/30")); // true
        System.out.println(isOverlapping("2024/03/20", "2024/3/25", "2024/3/26", "2024/3/30")); // false
        System.out.println(isOverlapping("2024/1/1", "2024/12/31", "2024/6/15", "2024/7/15")); // true
    }
}