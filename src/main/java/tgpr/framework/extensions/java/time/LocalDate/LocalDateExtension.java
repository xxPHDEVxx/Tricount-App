package tgpr.framework.extensions.java.time.LocalDate;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.time.LocalDate;

import static tgpr.framework.LocaleFormats.DATE_FORMATTER;
import static tgpr.framework.LocaleFormats.ISO_DATE_FORMATTER;

@Extension
public class LocalDateExtension {
    /**
     * Transforme une date en une chaîne de caractères en utilisant le format par défaut.
     *
     * @param ts la date à transformer
     * @return la chaîne de caracètres obtenue
     */
    public static String asString(@This LocalDate ts) {
        return ts == null ? "" : DATE_FORMATTER.format(ts);
    }


    /**
     * Transforme une date en une chaîne de caractères en utilisant le format ISO (YYYY-MM-DD).
     *
     * @param ts la date à transformer
     * @return la chaîne de caracètres obtenue
     */
    public static String asIsoString(@This LocalDate ts) {
        return ts == null ? "" : ISO_DATE_FORMATTER.format(ts);
    }

}
