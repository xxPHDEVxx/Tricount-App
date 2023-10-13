package tgpr.framework.extensions.com.googlecode.lanterna.gui2.Panel;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;
import manifold.ext.rt.api.ThisClass;
import tgpr.framework.Margin;
import tgpr.framework.Spacing;

@Extension
public class PanelExtension {
    public static void helloWorld(@This Panel thiz) {
        System.out.println("hello world!");
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant verticalement.
     * Les composants enfants sont centrés horizontalement au sein du panel.
     *
     * @param components les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public static @Self Panel verticalPanel(@ThisClass Class<Panel> thisClass, Component... components) {
        return createPanel(LinearLayout.Alignment.Center, Direction.VERTICAL, 0, components);
    }

    public static Panel asVerticalPanel(@This Panel thiz, Component... components) {
        return configPanel(thiz, LinearLayout.Alignment.Center, Direction.VERTICAL, 0, components);
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant verticalement.
     * Les composants enfants sont centrés horizontalement au sein du panel.
     *
     * @param components les composants enfants à inclure dans le panel
     * @param spacing    l'espacement entre les composants enfants
     * @return le panel créé
     */
    public static @Self Panel verticalPanel(@ThisClass Class<Panel> thisClass, int spacing, Component... components) {
        return createPanel(LinearLayout.Alignment.Center, Direction.VERTICAL, spacing, components);
    }

    public static Panel asVerticalPanel(@This Panel thiz, int spacing, Component... components) {
        return configPanel(thiz, LinearLayout.Alignment.Center, Direction.VERTICAL, spacing, components);
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant verticalement.
     *
     * @param alignment  la direction d'alignement horizontal des composants au sein du panel
     * @param components les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public static @Self Panel verticalPanel(@ThisClass Class<Panel> thisClass, LinearLayout.Alignment alignment,
                                            Component... components) {
        return createPanel(alignment, Direction.VERTICAL, 0, components);
    }

    public static Panel asVerticalPanel(@This Panel thiz, LinearLayout.Alignment alignment, Component... components) {
        return configPanel(thiz, alignment, Direction.VERTICAL, 0, components);
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant horizontalement.
     * Les composants enfants sont centrés verticalement au sein du panel.
     *
     * @param components les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public static @Self Panel horizontalPanel(@ThisClass Class<Panel> thisClass, Component... components) {
        return createPanel(LinearLayout.Alignment.Center, Direction.HORIZONTAL, 1, components);
    }

    public static Panel asHorizontalPanel(@This Panel thiz, Component... components) {
        return configPanel(thiz, LinearLayout.Alignment.Center, Direction.HORIZONTAL, 1, components);
    }


    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant horizontalement.
     * Les composants enfants sont centrés verticalement au sein du panel.
     *
     * @param components les composants enfants à inclure dans le panel
     * @param spacing    l'espacement entre les composants enfants
     * @return le panel créé
     */
    public static @Self Panel horizontalPanel(@ThisClass Class<Panel> thisClass, int spacing, Component... components) {
        return createPanel(LinearLayout.Alignment.Center, Direction.HORIZONTAL, spacing, components);
    }

    public static Panel asHorizontalPanel(@This Panel thiz, int spacing, Component... components) {
        return configPanel(thiz, LinearLayout.Alignment.Center, Direction.HORIZONTAL, spacing, components);
    }

    /**
     * Crée un panel contenant les composants reçus en paramètre en les alignant horizontalement.
     *
     * @param alignment  la direction d'alignement vertical des composants au sein du panel
     * @param components les composants enfants à inclure dans le panel
     * @return le panel créé
     */
    public static @Self Panel horizontalPanel(@ThisClass Class<Panel> thisClass, LinearLayout.Alignment alignment,
                                              Component... components) {
        return createPanel(alignment, Direction.HORIZONTAL, 1, components);
    }

    public static Panel asHorizontalPanel(@This Panel thiz, LinearLayout.Alignment alignment, Component... components) {
        return configPanel(thiz, alignment, Direction.HORIZONTAL, 1, components);
    }

    private static Panel createPanel(LinearLayout.Alignment alignment, Direction direction, int spacing,
                                     Component... components) {
        return configPanel(new Panel(), alignment, direction, spacing, components);
    }

    private static Panel configPanel(Panel panel, LinearLayout.Alignment alignment, Direction direction, int spacing,
                                     Component... components) {
        var layout = LinearLayout.createLayoutData(alignment);
        panel.setLayoutManager(new LinearLayout(direction).setSpacing(spacing));
        for (var component : components)
            panel.addComponent(component, layout);
        return panel;
    }

    public static @Self Panel gridPanel(@ThisClass Class<Panel> thisClass, int numberOfColumns,
                                        Component... components) {
        return createGridPanel(numberOfColumns, Margin.getDefault(), Spacing.getDefault(), components);
    }

    public static Panel asGridPanel(@This Panel thiz, int numberOfColumns, Component... components) {
        return configGridPanel(thiz, numberOfColumns, Margin.getDefault(), Spacing.getDefault(), components);
    }

    public static @Self Panel gridPanel(@ThisClass Class<Panel> thisClass, int numberOfColumns, Margin margin,
                                        Component... components) {
        return createGridPanel(numberOfColumns, margin, Spacing.getDefault(), components);
    }

    public static Panel asGridPanel(@This Panel thiz, int numberOfColumns, Margin margin, Component... components) {
        return configGridPanel(thiz, numberOfColumns, margin, Spacing.getDefault(), components);
    }

    public static @Self Panel gridPanel(@ThisClass Class<Panel> thisClass, int numberOfColumns, Spacing spacing,
                                        Component... components) {
        return createGridPanel(numberOfColumns, Margin.getDefault(), spacing, components);
    }

    public static Panel asGridPanel(@This Panel thiz, int numberOfColumns, Spacing spacing, Component... components) {
        return configGridPanel(thiz, numberOfColumns, Margin.getDefault(), spacing, components);
    }

    public static @Self Panel gridPanel(@ThisClass Class<Panel> thisClass, int numberOfColumns, Margin margin,
                                        Spacing spacing, Component... components) {
        return createGridPanel(numberOfColumns, margin, spacing, components);
    }

    public static Panel asGridPanel(@This Panel thiz, int numberOfColumns, Margin margin, Spacing spacing,
                                    Component... components) {
        return configGridPanel(thiz, numberOfColumns, margin, spacing, components);
    }

    private static Panel createGridPanel(int numberOfColumns, Margin margin, Spacing spacing, Component... components) {
        return configGridPanel(new Panel(), numberOfColumns, margin, spacing, components);
    }

    private static Panel configGridPanel(Panel panel, int numberOfColumns, Margin margin, Spacing spacing,
                                         Component... components) {
        panel.setLayoutManager(new GridLayout(numberOfColumns)
                .setTopMarginSize(margin.getTop())
                .setBottomMarginSize(margin.getBottom())
                .setLeftMarginSize(margin.getLeft())
                .setRightMarginSize(margin.getRight())
                .setVerticalSpacing(spacing.getVertical())
                .setHorizontalSpacing(spacing.getHorizontal())
        );
        for (var component : components)
            component.addTo(panel);
        return panel;
    }

    public static Panel addEmpty(@This Panel panel) {
        return addEmpty(panel, TerminalSize.ONE);
    }

    public static Panel addEmpty(@This Panel panel, TerminalSize size) {
        new EmptySpace(size).addTo(panel);
        return panel;
    }
}