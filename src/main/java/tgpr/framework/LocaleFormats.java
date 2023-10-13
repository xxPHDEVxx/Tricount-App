package tgpr.framework;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocaleFormats {
    public static final DateTimeFormatter INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/y");
    public static final DateTimeFormatter INPUT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("d/M/y H:m[:s]");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter ISO_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.FRANCE);
    public static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.FRANCE);
    ;
    public static final NumberFormat INTEGER_FORMAT = NumberFormat.getIntegerInstance(Locale.FRANCE);

    static {
        DecimalFormatSymbols symbols = ((DecimalFormat) CURRENCY_FORMAT).getDecimalFormatSymbols();
        symbols.setMonetaryDecimalSeparator(',');
        symbols.setMonetaryGroupingSeparator(' ');
        symbols.setCurrencySymbol("€");
        ((DecimalFormat) CURRENCY_FORMAT).applyPattern("#,##0.00 ¤");
        ((DecimalFormat) CURRENCY_FORMAT).setDecimalFormatSymbols(symbols);

        symbols = ((DecimalFormat) NUMBER_FORMAT).getDecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        ((DecimalFormat) NUMBER_FORMAT).setDecimalFormatSymbols(symbols);
    }
}
