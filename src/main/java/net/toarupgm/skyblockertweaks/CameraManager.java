package net.toarupgm.skyblockertweaks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.toarupgm.skyblockertweaks.utils.CameraUtils;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;

public class CameraManager {
    public static CameraManager INSTANCE;
    private ClientFakePlayerEntity fakePlayer;

    public CameraManager() {
        INSTANCE = this;
    }
    public void onRender(RenderTickCounter tickCounter)
    {
        MinecraftClient client = MinecraftClient.getInstance();

        ClientPlayerEntity player = client.player;
        if(Objects.isNull(player)) return;
        if(Objects.isNull(fakePlayer)) return;

        boolean positionAttached = CameraUtils.isPositionAttached();
        boolean rotationAttached = CameraUtils.isRotationAttached();

        if(positionAttached || rotationAttached)
        {
            float yaw = client.player.getYaw();
            float pitch = client.player.getPitch();
            float headYaw = client.player.getHeadYaw();
            Vec3d position = client.player.getPos();

            if(rotationAttached)
            {
                yaw = CameraUtils.getYaw();
                pitch = CameraUtils.getPitch();
            }
            if(positionAttached)
            {
                position = CameraUtils.getPosition();
            }

            fakePlayer.setPosition(position.x, position.y ,position.z);
            fakePlayer.setYaw(yaw);
            fakePlayer.setPitch(pitch);
            fakePlayer.setHeadYaw(headYaw);

            fakePlayer.prevYaw = yaw;
            fakePlayer.prevHeadYaw = headYaw;
            fakePlayer.prevPitch = pitch;
            fakePlayer.prevX = position.x;
            fakePlayer.prevY = position.y;
            fakePlayer.prevZ = position.z;
            client.setCameraEntity(fakePlayer);
        }
    }
    public void onRenderEnd(RenderTickCounter tickCounter)
    {
        MinecraftClient client = MinecraftClient.getInstance();

        ClientPlayerEntity player = client.player;
        if(Objects.isNull(player)) return;
        client.setCameraEntity(player);
    }
    public void onPositionAttached()
    {
        MinecraftClient client = MinecraftClient.getInstance();

        ClientPlayerEntity player = client.player;
        if(Objects.isNull(player)) return;

        if(Objects.isNull(fakePlayer))
        {
            fakePlayer = new ClientFakePlayerEntity(EntityType.PLAYER, client.world);
            fakePlayer.copyFrom(player);
        }

        fakePlayer.prevX = player.getX();
        fakePlayer.prevY = player.getY();
        fakePlayer.prevZ = player.getZ();
    }
    public void onRotationAttached()
    {
        MinecraftClient client = MinecraftClient.getInstance();

        ClientPlayerEntity player = client.player;
        if(Objects.isNull(player)) return;

        if(Objects.isNull(fakePlayer))
        {
            fakePlayer = new ClientFakePlayerEntity(EntityType.PLAYER, client.world);
            fakePlayer.copyFrom(player);
        }

        fakePlayer.prevPitch = player.getPitch();
        fakePlayer.prevHeadYaw = player.getHeadYaw();
        fakePlayer.prevYaw = player.getYaw();

    }
}
