package net.toarupgm.skyblockertweaks.client.config.enums;

import net.minecraft.client.resource.language.I18n;

public enum InterpolationType {
    LINEAR,
    QUADRATIC,
    CUBIC;


    @Override
    public String toString() {
        return I18n.translate("sbtweaks.config.interpolation." + name());
    }
}
