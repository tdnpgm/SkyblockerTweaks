package net.toarupgm.skyblockertweaks.mixins.minecraft;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.text.Text;
import net.toarupgm.skyblockertweaks.client.SkyblockerTweaksClient;
import net.toarupgm.skyblockertweaks.featuers.base.PlayerMoveFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onPlayerPositionLook", at = @At("HEAD"))
    public void onPlayerPositionLook(PlayerPositionLookS2CPacket packet, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if(Objects.isNull(client.player)) return;
        double x = packet.getX();
        double y = packet.getY();
        double z = packet.getZ();
        double yaw = packet.getYaw();
        double pitch = packet.getPitch();

        SkyblockerTweaksClient.featureManager.forEach(PlayerMoveFeature.class, playerMoveFeature -> {
            playerMoveFeature.onPlayerMove(x, y, z, yaw, pitch);
        });
    }

    @Inject(method = "onEntityTrackerUpdate", at = @At("TAIL"))
    private void onEntityTrackerUpdate(EntityTrackerUpdateS2CPacket packet, CallbackInfo ci, @Local Entity entity) {
        if (!(entity instanceof ArmorStandEntity armorStand)) return;

        if (!armorStand.isInvisible() || !armorStand.hasCustomName() || !armorStand.isCustomNameVisible()) return;
        Text customName = armorStand.getCustomName();
        if (customName == null) return;
        String text = customName.getString();


    }
}
