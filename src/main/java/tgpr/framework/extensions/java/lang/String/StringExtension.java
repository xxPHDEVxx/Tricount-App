package tgpr.framework.extensions.java.lang.String;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;
import manifold.ext.rt.api.ThisClass;
import org.apache.commons.text.WordUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static tgpr.framework.LocaleFormats.*;

@Extension
public class StringExtension {
    /**
     * Transforme une chaîne de caractère en une chaîne multi-lignes en tenant compte d'une largeur maximale que peut
     * avoir chaque ligne.
     *
     * @param txt    le texte de départ
     * @param length la longueur maximale d'une ligne
     * @return le texte transformé
     */
    public static String wrap(@This String txt, int length) {
        return WordUtils.wrap(txt, length, null, true);
    }

    /**
     * Abrège une chaîne de caractère pour qu'elle ne dépasse pas une longueur maximale donnée. Le texte abrégé se
     * termine par {@code "..."} pour montrer qu'il a été abrégé.
     *
     * @param txt    le texte de départ
     * @param length la longueur maximale d'une ligne
     * @return le texte transformé
     */
    public static String abbreviate(@This String txt, int length) {
        return WordUtils.abbreviate(txt, length, length, "...");
    }

    /**
     * Vérifie si une chaîne de caractères correspond à une date valide.
     *
     * @param str la chaîne de caractères à vérifier
     * @return une valeur booléenne
     */
    public static boolean isValidDate(@This String str) {
        try {
            LocalDate.parse(str, INPUT_DATE_FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Vérifie si une chaîne de caractères correspond à une date/heure valide.
     *
     * @param str la chaîne de caractères à vérifier
     * @return une valeur booléenne
     */
    public static boolean isValidDateTime(@This String str) {
        try {
            LocalDate.parse(str, INPUT_DATETIME_FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Vérifie si une chaîne de caractères correspond à une valeur double valide.
     *
     * @param str la chaîne de caractères à vérifier
     * @return une valeur booléenne
     */
    public static boolean isValidDouble(@This String str) {
        try {
            var number = NUMBER_FORMAT.parse(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Vérifie si une chaîne de caractères correspond à une valeur entière valide.
     *
     * @param str la chaîne de caractères à vérifier
     * @return une valeur booléenne
     */
    public static boolean isValidInteger(@This String str) {
        try {
            var number = INTEGER_FORMAT.parse(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Transforme une chaîne de caractères en un double en utilisant le format par défaut.
     *
     * @param str la chaîne à transformer
     * @return le double obtenu ou {@code Double.NaN} si la chaîne ne correspond pas au format
     */
    public static Double toDouble(@This String str) {
        try {
            return NUMBER_FORMAT.parse(str).doubleValue();
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    /**
     * Transforme une chaîne de caractères en un double en utilisant le format par défaut.
     *
     * @param str la chaîne à transformer
     * @return le double obtenu ou {@code Double.NaN} si la chaîne ne correspond pas au format
     */
    public static Double toEuro(@This String str) {
        try {
            return CURRENCY_FORMAT.parse(str).doubleValue();
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    /**
     * Transforme un double en une chaîne de caractères en utilisant le format par défaut.
     *
     * @param val le double à transformer
     * @return la chaîne de caractères obtenue ou {@code null} si la chaîne ne correspond pas au format
     */
    public static @Self String asDecimal(@ThisClass Class<String> thisClass, double val) {
        try {
            return NUMBER_FORMAT.format(val);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Transforme un double en une chaîne de caractères représentant un montant en devise (ici l'euro)
     * en utilisant le format par défaut.
     *
     * @param val le double à transformer
     * @return la chaîne de caractères obtenue ou {@code null} si la chaîne ne correspond pas au format
     */
    public static @Self String asCurrency(@ThisClass Class<String> thisClass, double val) {
        try {
            return CURRENCY_FORMAT.format(val);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Transforme une chaîne de caractères en une date en utilisant le format par défaut.
     *
     * @param str la chaîne à transformer
     * @return la date obtenue ou {@code null} si la chaîne ne correspond pas au format
     */
    public static LocalDate toDate(@This String str) {
        try {
            LocalDate d = LocalDate.parse(str, INPUT_DATE_FORMATTER);
            if (d.getYear() < 100)
                d = d.plusYears(1900);
            return d;
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Transforme une chaîne de caractères en une date/heure en utilisant le format par défaut.
     *
     * @param str la chaîne à transformer
     * @return la date/heure obtenue ou {@code null} si la chaîne ne correspond pas au format
     */
    public static LocalDateTime toDateTime(@This String str) {
        try {
            return LocalDateTime.parse(str, INPUT_DATETIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}