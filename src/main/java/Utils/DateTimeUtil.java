package Utils;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public static String DateFormatter(LocalDateTime createdAt) {
      try{
          return createdAt.format(formatter);
      }catch (RuntimeException e){
          throw new DateTimeParseException("Invalid date format", createdAt.toString(), 0, e);
      }


    }

}
