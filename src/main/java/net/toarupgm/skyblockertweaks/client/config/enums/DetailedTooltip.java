package net.toarupgm.skyblockertweaks.client.config.enums;

import net.minecraft.client.resource.language.I18n;

public enum DetailedTooltip {
    ALWAYS_VISIBLE,
    VISIBLE_ON_SHIFT,
    HIDDEN;

    @Override
    public String toString() {
        return I18n.translate("sbtweaks.config.tooltip." + name());
    }
}
