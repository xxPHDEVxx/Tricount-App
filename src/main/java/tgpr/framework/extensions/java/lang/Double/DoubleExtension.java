package tgpr.framework.extensions.java.lang.Double;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.ThisClass;

import static tgpr.framework.LocaleFormats.CURRENCY_FORMAT;
import static tgpr.framework.LocaleFormats.NUMBER_FORMAT;

@Extension
public class DoubleExtension {
    /**
     * Transforme une chaîne de caractères en un double en utilisant le format décimal par défaut.
     *
     * @param str la chaîne à transformer
     * @return le double obtenu ou {@code Double.NaN} si la chaîne ne correspond pas au format
     */
    public static @Self Double fromDecimal(@ThisClass Class<Double> thisClass, String str) {
        try {
            return NUMBER_FORMAT.parse(str).doubleValue();
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    /**
     * Transforme une chaîne de caractères en un double en utilisant le format monétaire par défaut.
     *
     * @param str la chaîne à transformer
     * @return le double obtenu ou {@code Double.NaN} si la chaîne ne correspond pas au format
     */
    public static @Self Double fromCurrency(@ThisClass Class<Double> thisClass, String str) {
        try {
            str = str.replaceFirst("\\s*€$", " €");
            return CURRENCY_FORMAT.parse(str).doubleValue();
        } catch (Exception e) {
            return Double.NaN;
        }
    }
}