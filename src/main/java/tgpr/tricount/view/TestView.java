package tgpr.tricount.view;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import tgpr.framework.Margin;
import tgpr.framework.Spacing;
import tgpr.tricount.controller.TestController;
import tgpr.tricount.model.User;

import java.util.List;

public class TestView extends DialogWindow {
    private final TestController controller;
    // la première combo contient des objets de type User
    private final ComboBox<User> cbo1;
    // la seconde combo contient des strings car on y mélange des objets User et un string pour la première entrée
    private final ComboBox<String> cbo2;
    private final Label lbl;

    public TestView(TestController controller) {
        super("Test");
        setHints(List.of(Hint.EXPANDED));

        this.controller = controller;

        var root = Panel.gridPanel(1, Margin.of(1), Spacing.of(1));
        setComponent(root);

        cbo1 = new ComboBox<User>()
                .sizeTo(25)
                .addTo(root)
                // On ajoute un listener sous la forme d'une méthode lambda qui sera appelée lorsque la valeur
                // sélectionnée dans la combo change. Ce listener prend trois paramètres :
                // - newIndex : l'indice du nouvel élément sélectionné
                // - oldIndex : l'indice de l'ancien élément sélectionné
                // - byUser : un booléen qui indique si le changement de sélection a été déclenché par l'utilisateur (true)
                //            ou par du code (false)
                .addListener((newIndex, oldIndex, byUser) -> {
                    // lorsque la sélection change, on doit rafraîchir la deuxième combo pour n'y retrouver que les users
                    // différents du user sélectionné
                    refreshCbo2();
                });

        cbo2 = new ComboBox<String>()
                .sizeTo(25)
                .addTo(root)
                // on ajoute un listener suivant le même principe que pour la première combo
                .addListener((newIndex, oldIndex, byUser) -> {
                    // quand la sélection change, on doit rafraîchir le libellé
                    refreshLabel();
                });

        lbl = new Label("")
                .addTo(root);

        cbo1.takeFocus();

        // refresh initial
        refresh();
    }

    private void refresh() {
        refreshCbo1();
        refreshCbo2();
        refreshLabel();
    }

    private void refreshCbo1() {
        // on ajoute tous les users dans la première combo
        for (var user : controller.getUsers())
            cbo1.addItem(user);
    }

    private void refreshCbo2() {
        // on garde en mémoire l'élément couramment sélectionné
        var current = cbo2.getSelectedItem();

        // on vide puis on re-remplit la combo avec tous les users différents du user sélectionné dans cbo1
        // plus un string en première position
        cbo2.clearItems();
        cbo2.addItem("-- Please choose --");
        for (var user : controller.getUsers())
            if (!user.equals(cbo1.getSelectedItem()))
                cbo2.addItem(user.toString());

        // si un élément était sélectionné auparavant, on le re-sélectionne.
        if (current != null)
            cbo2.setSelectedItem(current);
    }

    private void refreshLabel() {
        var user = getSelectedSecondUser();
        if (user == null) {
            lbl.setText("Please choose two different users!");
            lbl.setForegroundColor(TextColor.ANSI.RED);
        } else {
            lbl.setText(cbo1.getSelectedItem().getFullName() + " - " + user.getFullName());
            lbl.setForegroundColor(TextColor.ANSI.BLUE);
        }
    }

    // Récupère le user sélectionné dans cbo2
    private User getSelectedSecondUser() {
        String fullName = cbo2.getSelectedItem();
        if (fullName == null) return null;
        return User.getByFullName(fullName);
    }
}
