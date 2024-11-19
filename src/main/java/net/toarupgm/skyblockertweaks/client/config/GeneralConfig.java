package net.toarupgm.skyblockertweaks.client.config;

import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.toarupgm.skyblockertweaks.client.config.enums.DetailedTooltip;
import net.toarupgm.skyblockertweaks.client.config.enums.InterpolationType;
import net.toarupgm.skyblockertweaks.client.config.enums.MessageResult;

public class GeneralConfig {
    @SerialEntry
    public String hypixelAPIKey = "";
    @SerialEntry
    public float targetPitch = 0;
    @SerialEntry
    public float targetYaw = 0;
    @SerialEntry
    public float tuneViewSensitivityMultiplier = 0.5f;
    @SerialEntry
    public float tuneViewFovMultiplier = 0.75f;
    @SerialEntry
    public DetailedTooltip detailedTooltip = DetailedTooltip.VISIBLE_ON_SHIFT;
    @SerialEntry
    public MessageResult deathMessage = MessageResult.FILTER;
    @SerialEntry
    public boolean smoothAote = false;
    @SerialEntry
    public double aoteAnimationTicks = 7;
    @SerialEntry
    public InterpolationType aoteInterpolationType = InterpolationType.QUADRATIC;
}
