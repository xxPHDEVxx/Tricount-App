package tgpr.framework.extensions.com.googlecode.lanterna.gui2.AbstractComponent;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.AbstractComponent;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.GridLayout;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import tgpr.framework.Layouts;

@Extension
public class AbstractComponentExtension {
    public static <T extends Component> T center(@This AbstractComponent<T> thiz) {
        return thiz.setLayoutData(Layouts.LINEAR_CENTER);
    }

    public static <T extends Component> T begin(@This AbstractComponent<T> thiz) {
        return thiz.setLayoutData(Layouts.LINEAR_BEGIN);
    }

    public static <T extends Component> T end(@This AbstractComponent<T> thiz) {
        return thiz.setLayoutData(Layouts.LINEAR_END);
    }

    public static <T extends Component> T fill(@This AbstractComponent<T> thiz) {
        return thiz.setLayoutData(Layouts.LINEAR_FILL);
    }

    public static <T extends Component> T topLeft(@This AbstractComponent<T> thiz) {
        return thiz.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING,
                GridLayout.Alignment.BEGINNING));
    }

    public static <T extends Component> T topCenter(@This AbstractComponent<T> thiz) {
        return thiz.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER,
                GridLayout.Alignment.BEGINNING));
    }

    public static <T extends Component> T topRight(@This AbstractComponent<T> thiz) {
        return thiz.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END,
                GridLayout.Alignment.BEGINNING));
    }

    public static <T extends Component> T centerLeft(@This AbstractComponent<T> thiz) {
        return thiz.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING,
                GridLayout.Alignment.CENTER));
    }

    public static <T extends Component> T centerCenter(@This AbstractComponent<T> thiz) {
        return thiz.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER));
    }

    public static <T extends Component> T centerCenter(@This AbstractComponent<T> thiz,
                                                       int colSpan) {
        return thiz.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER, true, false, colSpan, 1));
    }

    public static <T extends Component> T centerRight(@This AbstractComponent<T> thiz) {
        return thiz.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));
    }

    public static <T extends Component> T bottomLeft(@This AbstractComponent<T> thiz) {
        return thiz.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING,
                GridLayout.Alignment.END));
    }

    public static <T extends Component> T bottomCenter(@This AbstractComponent<T> thiz) {
        return thiz.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.END));
    }

    public static <T extends Component> T bottomRight(@This AbstractComponent<T> thiz) {
        return thiz.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.END));
    }

    public static <T extends Component> T sizeTo(@This AbstractComponent<T> thiz, int columns) {
        return thiz.setPreferredSize(new TerminalSize(columns, 1));
    }

    public static <T extends Component> T sizeTo(@This AbstractComponent<T> thiz, int columns, int rows) {
        return thiz.setPreferredSize(new TerminalSize(columns, rows));
    }
}