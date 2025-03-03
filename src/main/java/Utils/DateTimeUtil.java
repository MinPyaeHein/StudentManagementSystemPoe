package Utils;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static String DateFormatter(LocalDateTime createdAt) {
        if (createdAt == null) {
            return "N/A";
        }
        try {
            return createdAt.format(formatter);
        } catch (RuntimeException e) {
            throw new DateTimeParseException("Invalid date format", createdAt.toString(), 0, e);
        }
    }
}
