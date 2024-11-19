package net.toarupgm.skyblockertweaks.client.config.enums;

import net.minecraft.client.resource.language.I18n;

public enum MessageResult {
    PASS,
    FILTER_ON_NORMAL_WORLD,
    FILTER;

    @Override
    public String toString() {
        return I18n.translate("sbtweaks.config.message." + name());
    }
}
