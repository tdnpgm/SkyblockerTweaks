package net.toarupgm.skyblockertweaks.mixins.minecraft;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.toarupgm.skyblockertweaks.featuers.FeatureManager;
import net.toarupgm.skyblockertweaks.featuers.base.OnPlaySoundFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method = "playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZJ)V", at = @At("TAIL"))
    private void playSound(double x, double y, double z, SoundEvent event, SoundCategory category, float volume, float pitch, boolean useDistance, long seed, CallbackInfo ci)
    {
        FeatureManager.INSTANCE.forEach(OnPlaySoundFeature.class,onPlaySoundFeature -> {
            onPlaySoundFeature.onPlaySound(event);
        });
    }
}
