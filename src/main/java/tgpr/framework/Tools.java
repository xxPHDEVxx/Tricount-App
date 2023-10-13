package tgpr.framework;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Cette classe propose diverses méthodes utilitaires.
 */
public abstract class Tools {
    private static final String PREFIX_SALT = "vJemLnU3";
    private static final String SUFFIX_SALT = "QUaLtRs7";

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.ENGLISH);

    /**
     * Retourne la valeur du premier argument, sauf dans le cas où celle-ci vaut {@code null} et pour lequel on
     * retourne alors la valeur de {@code defaultValue}.
     *
     * @param value        la valeur à évaluer
     * @param defaultValue la valeur par défaut à retourner si {@code value} est {@code null}
     * @return value si différente de {@code null}, sinon {@code defaultValue}
     */
    public static <T> T ifNull(T value, T defaultValue) {
        if (value == null)
            return defaultValue;
        return value;
    }

    /**
     * Retourne une clé de hachage calculée, avec l'algorithme MD5
     * (voir <a href="https://fr.wikipedia.org/wiki/MD5">https://fr.wikipedia.org/wiki/MD5</a>),
     * à partir d'une chaîne de caractères passée en paramètre.
     *
     * @param s la chaîne à hacher
     * @return la clé de hachage calculée
     */
    public static String hash(String s) {
        String pwd = PREFIX_SALT + s + SUFFIX_SALT;
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(pwd.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash)
                sb.append(String.format("%02x", b & 0xff));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Transforme un double en une chaîne de caractères en utilisant le format par défaut.
     *
     * @param val le double à transformer
     * @return la chaîne de caractère obtenue
     */
    public static String toString(double val) {
        try {
            return NUMBER_FORMAT.format(val);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Retourne la valeur de la string passée en premier argument, sauf dans le cas où celle-ci vaut {@code null}
     * ou est vide ou ne contient que des espaces ("blanche"), et pour lequel on retourne alors la valeur
     * de {@code defaultValue}.
     *
     * @param str          la string à évaluer
     * @param defaultValue la string par défaut à retourner si {@code value} est {@code null} ou vide ou "blanche"
     * @return value si différente de {@code null}, sinon {@code defaultValue}
     */
    public static String ifNullOrBlank(String str, String defaultValue) {
        if (str == null || str.isBlank())
            return defaultValue;
        else
            return str;
    }
}
