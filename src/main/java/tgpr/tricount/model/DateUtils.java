package tgpr.tricount.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    // Fonction pour convertir LocalDate en String
    public static String localDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }
}
