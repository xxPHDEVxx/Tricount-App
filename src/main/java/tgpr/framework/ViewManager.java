package tgpr.framework;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ViewManager {
    private static MultiWindowTextGUI gui;
    private static Window currentWindow;

    static {
        try {
            DefaultTerminalFactory factory = new DefaultTerminalFactory();
            factory.setInitialTerminalSize(new TerminalSize(Configuration.getInt("terminal.width"),
                    Configuration.getInt("terminal.height")));
            factory.setTerminalEmulatorFontConfiguration(
                    SwingTerminalFontConfiguration.newInstance(new Font(getFontName(), Font.PLAIN,
                            Configuration.getInt("fontsize"))));
            SwingTerminalFrame terminal = (SwingTerminalFrame) factory.createTerminal();
            terminal.setResizable(false);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            terminal.setLocation((screenSize.width - terminal.getWidth()) / 2,
                    (screenSize.height - terminal.getHeight()) / 2);
            terminal.setTitle(Configuration.get("title"));
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(),
                    new EmptySpace(TextColor.ANSI.valueOf(Configuration.get("backgroundColor"))));
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    private static String getFontName() {
        String fontName = null;
        switch (OSType.DETECTED) {
            case Windows:
                fontName = Configuration.get("fontname.windows");
                break;
            case Linux:
                fontName = Configuration.get("fontname.linux");
                break;
            case MacOS:
                fontName = Configuration.get("fontname.mac");
                break;
            case Other:
                fontName = Configuration.get("fontname.other");
            default:
                throw new RuntimeException("Unsupported OS");
        }
        return fontName;
    }

    public static TerminalSize getTerminalSize() {
        return gui.getScreen().getTerminalSize();
    }

    public static int getTerminalColumns() {
        return getTerminalSize().getColumns();
    }

    public static int getTerminalRows() {
        return getTerminalSize().getRows();
    }

    /**
     * Permet d'afficher une boîte de dialogue modale présentant les erreurs reçues en paramètre.
     * Les erreurs sont représentées par des instances de {@link Error}.
     *
     * @param errors la liste des erreurs à afficher
     */
    static void showErrors(List<Error> errors) {
        showErrors(errors, "Error");
    }

    /**
     * Permet d'afficher une boîte de dialogue modale présentant une erreur reçue en paramètre.
     * L'erreur est représentée par une instances de {@link Error}.
     *
     * @param error l'erreur à afficher
     */
    static void showError(Error error) {
        showErrors(List.of(error));
    }

    static void showErrors(List<Error> errors, String title) {
        var errorsText = errors.stream().map(Error::getMessage).collect(Collectors.toList());
        if (!errors.isEmpty())
            MessageDialog.showMessageDialog(gui, title, String.join("\n", errorsText), MessageDialogButton.OK);
    }

    static MessageDialogButton showMessage(String message, String title, MessageDialogButton... buttons) {
        return MessageDialog.showMessageDialog(gui, title, message, buttons);
    }

    static void navigateTo(Window window) {
        if (window == null)
            return;
        if (currentWindow != null) {
            // on fait ça sinon le menu reste ouvert quand on ferme la fenêtre avec logout
            if (gui.getActiveWindow() != null && gui.getActiveWindow() != currentWindow)
                gui.getActiveWindow().close();
            currentWindow.close();
            gui.removeWindow(currentWindow);
        }
        currentWindow = window;
        gui.addWindowAndWait(window);
    }

    static void showModal(DialogWindow window) {
        window.showDialog(gui);
    }
}
