package tgpr.framework;

import java.io.IOException;
import java.util.Properties;

/**
 * Permet de lire des valeurs de paramètres applicatifs stockés dans le fichier {@code /config.properties} des
 * ressources
 * du projet.
 */
public class Configuration {
    private static final Properties props = new Properties();

    static {
        try {
            props.load(Configuration.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Permet de récupérer dans un {@code String} la valeur du paramètre dont le nom est passé en argument.
     *
     * @param key le nom (la clef) du paramètre
     * @return la valeur du paramètre
     */
    public static String get(String key) {
        return get(key, "");
    }

    /**
     * Permet de récupérer dans un {@code String} la valeur du paramètre dont le nom est passé en argument
     * ou une valeur par défaut si le nom n'est pas trouvé dans le fichier de configuration.
     *
     * @param key le nom (la clef) du paramètre
     * @param defaultValue la valeur par défaut
     * @return la valeur du paramètre
     */
    public static String get(String key, String defaultValue) {
        var res = props.getProperty(key);
        return res == null ? defaultValue : res;
    }

    /**
     * Permet de récupérer dans un {@code int} la valeur du paramètre dont le nom est passé en argument.
     *
     * @param key le nom (la clef) du paramètre
     * @return la valeur du paramètre ou zéro si pas trouvé ou invalide
     */
    public static int getInt(String key) {
        return getInt(key, 0);
    }

    /**
     * Permet de récupérer dans un {@code int} la valeur du paramètre dont le nom est passé en argument
     * ou une valeur par défaut si le nom n'est pas trouvé dans le fichier de configuration.
     *
     * @param key le nom (la clef) du paramètre
     * @param defaultValue la valeur par défaut
     * @return la valeur du paramètre ou la valeur par défaut si pas trouvé ou invalide
     */
    public static int getInt(String key, int defaultValue) {
        var res = props.getProperty(key);
        try {
            return res == null ? defaultValue : Integer.parseInt(res);
        } catch(NumberFormatException e) {
            return defaultValue;
        }
    }
}
