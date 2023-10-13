package tgpr.framework.extensions.java.time.LocalDateTime;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.time.LocalDateTime;

import static tgpr.framework.LocaleFormats.DATETIME_FORMATTER;
import static tgpr.framework.LocaleFormats.ISO_DATETIME_FORMATTER;

@Extension
public class LocalDateTimeExtension {
    /**
     * Transforme une date/heure en une chaîne de caractères en utilisant le format par défaut.
     *
     * @param ts la date/heure à transformer
     * @return la chaîne de caracètres obtenue
     */
    public static String asString(@This LocalDateTime ts) {
        return ts == null ? "" : DATETIME_FORMATTER.format(ts);
    }

    /**
     * Transforme une date/heure en une chaîne de caractères en utilisant le format ISO (YYYY-MM-DD HH:MM:SS).
     *
     * @param ts la date/heure à transformer
     * @return la chaîne de caracètres obtenue
     */
    public static String toIsoString(@This LocalDateTime ts) {
        return ts == null ? "" : ISO_DATETIME_FORMATTER.format(ts);
    }
}
