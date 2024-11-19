package net.toarupgm.skyblockertweaks.featuers;

import net.toarupgm.skyblockertweaks.featuers.base.ListenerFeature;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class FeatureManager {
    public static FeatureManager INSTANCE;

    private final ListenerFeature[] listenerFeatures;

    public FeatureManager(ListenerFeature[] listenerFeatures) {
        INSTANCE = this;

        this.listenerFeatures = listenerFeatures;
    }



    @SuppressWarnings("unchecked")
    public <E extends ListenerFeature> void forEach(Class<E> targetClass, Consumer<E> action)
    {
        for (ListenerFeature feature : this.listenerFeatures) {
//            if(targetClass.isInstance(feature))

            if(targetClass.isInstance(feature))
            {
                action.accept((E) feature);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <E extends ListenerFeature> boolean whileRun(Class<E> targetClass, Function<E, Boolean> action)
    {
        for (ListenerFeature feature : this.listenerFeatures) {
            if(targetClass.isInstance(feature))
            {
                boolean b = action.apply((E) feature);
                if(!b)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
