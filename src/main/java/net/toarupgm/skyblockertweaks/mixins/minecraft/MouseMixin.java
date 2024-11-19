package net.toarupgm.skyblockertweaks.mixins.minecraft;

import net.minecraft.client.Mouse;
import net.toarupgm.skyblockertweaks.utils.MouseUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Mouse.class)
public class MouseMixin {
    @ModifyVariable(method = "updateMouse", at = @At("STORE"), ordinal = 1)
    private double injected(double x) {
        return x * MouseUtils.getMouseSensMultiplier();
    }
}
