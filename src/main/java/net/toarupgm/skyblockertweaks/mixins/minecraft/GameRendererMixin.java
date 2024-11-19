package net.toarupgm.skyblockertweaks.mixins.minecraft;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.toarupgm.skyblockertweaks.CameraManager;
import net.toarupgm.skyblockertweaks.ClientFakePlayerEntity;
import net.toarupgm.skyblockertweaks.featuers.FeatureManager;
import net.toarupgm.skyblockertweaks.featuers.base.GameRenderingFeature;
import net.toarupgm.skyblockertweaks.utils.CameraUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "render", at = @At("HEAD"))
    public void render(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci)
    {
        FeatureManager.INSTANCE.forEach(GameRenderingFeature.class,gameRenderingFeature -> {
            gameRenderingFeature.gameRender(tickCounter, tick);
        });
    }


    @Inject(method = "renderWorld", at = @At("HEAD"))
    public void headRender(RenderTickCounter tickCounter, CallbackInfo ci)
    {
        CameraManager.INSTANCE.onRender(tickCounter);
    }
    @Inject(method = "renderWorld", at = @At("TAIL"))
    public void headRenderEnd(RenderTickCounter tickCounter, CallbackInfo ci)
    {
        CameraManager.INSTANCE.onRenderEnd(tickCounter);
    }
    @ModifyVariable(method = "getFov", at = @At(value = "STORE",ordinal = 1))
    private double getFov(double fov)
    {
        return fov * CameraUtils.getFovMultiplier();
    }
}
