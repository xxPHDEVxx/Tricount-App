package tgpr.framework.extensions.com.googlecode.lanterna.gui2.Component;

import com.googlecode.lanterna.gui2.Component;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

@Extension
public class ComponentExtension {
    public static <T extends Component> T findParentOfType(@This Component thiz, Class<T> targetType) {
        if (thiz == null) {
            return null;
        }

        if (targetType.isInstance(thiz)) {
            return targetType.cast(thiz);
        }

        return findParentOfType(thiz.getParent(), targetType);
    }
}