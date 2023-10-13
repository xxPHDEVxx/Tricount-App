package tgpr.framework.extensions.com.googlecode.lanterna.gui2.Window;

import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowListenerAdapter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

@Extension
public class WindowExtension {
    /**
     * Crée un raccourci clavier et l'associe à l'action par défaut d'un composant lanterna.
     *
     * @param window            la fenêtre contenant le composant
     * @param component         le composant
     * @param shortcutKeyStroke le raccourci clavier
     */
    public static void addShortcut(@This Window window, Interactable component, KeyStroke shortcutKeyStroke) {
        window.addWindowListener(new WindowListenerAdapter() {
            @Override
            public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
                if (keyStroke.equals(shortcutKeyStroke)) {
                    deliverEvent.set(false);
                    component.takeFocus();
                    component.handleInput(new KeyStroke(KeyType.Enter));
                }
            }
        });
    }

    /**
     * Ajoute un gestionnaire d'événement pour la frappe clavier d'un composant lorsqu'il a le focus.
     *
     * @param window    la fenêtre contenant le composant
     * @param component le composant
     * @param handler   le gestionnaire d'événement qui doit être une fonction recevant en paramètre
     *                  la frappe clavier (KeyStroke) et qui doit retourner un booléen indiquant si
     *                  l'événement doit continuer à être traité par d'autres gestionnaires d'événements éventuels.
     */
    public static void addKeyboardListener(@This Window window, Interactable component,
                                           Function<KeyStroke, Boolean> handler) {
        window.addWindowListener(new WindowListenerAdapter() {
            @Override
            public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
                if (component.isFocused()) {
                    deliverEvent.set(handler.apply(keyStroke));
                }
            }
        });
    }
}