package net.toarupgm.skyblockertweaks.mixins.skyblocker;

import de.hysky.skyblocker.skyblock.calculators.SignCalculator;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SignCalculator.class, remap = false)
public class SignCalculatorMixin {
    @Inject(method = "render", at = @At("TAIL"), remap = false)
    private static void render(DrawContext context, String input, int renderX, int renderY, CallbackInfo ci) {
//        context.drawItem();
    }
}
