package net.toarupgm.skyblockertweaks.featuers.smooth_aote;

import de.hysky.skyblocker.utils.render.RenderHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.math.Vec3d;
import net.toarupgm.skyblockertweaks.CameraManager;
import net.toarupgm.skyblockertweaks.SkyblockerTweaks;
import net.toarupgm.skyblockertweaks.api.SkyblockItemStack;
import net.toarupgm.skyblockertweaks.client.SkyblockerTweaksClient;
import net.toarupgm.skyblockertweaks.client.config.TweaksOptions;
import net.toarupgm.skyblockertweaks.featuers.base.GameRenderingFeature;
import net.toarupgm.skyblockertweaks.featuers.base.OnUseItemFeature;
import net.toarupgm.skyblockertweaks.featuers.base.PlayerMoveFeature;
import net.toarupgm.skyblockertweaks.utils.CameraUtils;
import net.toarupgm.skyblockertweaks.utils.VectorUtils;

import java.util.List;
import java.util.Objects;

public class SmoothAoteFeature implements OnUseItemFeature, GameRenderingFeature, PlayerMoveFeature {
    public static List<String> warpItems = List.of(
            "ASPECT_OF_THE_END"
    );
    public MinecraftClient client;
    public Vec3d warpStartPos;
    public Vec3d position;
    public Vec3d lastPosition;
    public double teleportationTick;
    public double warpTick;
    public static double warpingTicks = 20;

    @Override
    public void init() {

        this.client = MinecraftClient.getInstance();
        this.warpStartPos = new Vec3d(0,0,0);
        this.position = new Vec3d(0,0,0);
        this.lastPosition = new Vec3d(0,0,0);
        this.teleportationTick = 0;
        this.warpTick = 0;
        WorldRenderEvents.AFTER_TRANSLUCENT.register(this::renderAfterTranslucent);
    }

    @Override
    public void onUseSkyblockItem(SkyblockItemStack skyblockItemStack) {
        ClientPlayerEntity player = client.player;
        if(Objects.isNull(client.world)) return;
        if(Objects.isNull(player)) return;
        if(!TweaksOptions.config().generalConfig.smoothAote) return;

        String id = skyblockItemStack.getID();

        if (warpItems.contains(id)) {
            this.teleportationTick += warpingTicks;
        }
    }

    @Override
    public void gameRender(RenderTickCounter tickCounter, boolean tick) {
        if(Objects.isNull(client.player)) return;

        float tickDelta = tickCounter.getTickDelta(false);
        if(this.teleportationTick > 0) {
            this.teleportationTick -= tickDelta;
        }
        if(this.warpTick > 0) {
            warpTick -= tickDelta;
        }
        if(this.teleportationTick < 0) this.teleportationTick = 0;
        if(this.warpTick < 0) this.warpTick = 0;

        if(this.warpTick <= 0 && CameraUtils.isPositionAttached()) {
            CameraUtils.detachPosition();
        }

        if(this.warpTick > 0)
        {
            updateCameraPos();
        }
    }
    public void updateCameraPos()
    {
        if(Objects.isNull(client.player)) return;
        double aoteAnimationTicks = TweaksOptions.config().generalConfig.aoteAnimationTicks;

        Vec3d vec = client.player.getPos()
                .subtract(this.warpStartPos);
        double t = (aoteAnimationTicks - this.warpTick) / aoteAnimationTicks;

        switch (TweaksOptions.config().generalConfig.aoteInterpolationType)
        {
            case LINEAR -> {
                position = this.warpStartPos.add(
                        vec.multiply(t)
                );
            }
            case QUADRATIC -> {
                position = this.warpStartPos.add(
                        vec.multiply(t * t)
                );
            }
            case CUBIC -> {
                position = this.warpStartPos.add(
                        vec.multiply(t * t * t)
                );
            }
        }
        CameraUtils.setPosition(position);
    }

    public void renderAfterTranslucent(WorldRenderContext worldRenderContext)
    {
        if(!SkyblockerTweaks.DEBUGGING) return;

        if(Objects.isNull(client.player)) return;
        RenderHelper.renderLineFromCursor(worldRenderContext,warpStartPos,new float[]{0.2f,0.2f,1f},1f,5f);
        RenderHelper.renderLineFromCursor(worldRenderContext,position,new float[]{1f,0.2f,0.2f},1f,5f);
        RenderHelper.renderLineFromCursor(worldRenderContext,lastPosition,new float[]{0.2f,1f,0.2f},1f,5f);
        RenderHelper.renderLineFromCursor(worldRenderContext,client.player.getPos(),new float[]{0.2f,0.2f,0.2f},1f,5f);
    }
    @Override
    public void onPlayerMove(double x, double y, double z, double yaw, double pitch) {
        if(Objects.isNull(client.player)) return;
        if(!TweaksOptions.config().generalConfig.smoothAote) return;
        double aoteAnimationTicks = TweaksOptions.config().generalConfig.aoteAnimationTicks;



        if(this.teleportationTick > 0)
        {
            this.warpStartPos = client.player.getPos();

            this.teleportationTick = Math.max(0,this.teleportationTick-warpingTicks);
            if(this.warpTick > 0)
            {
                this.warpStartPos = VectorUtils.clone(position);
                this.lastPosition = VectorUtils.clone(position);
            }
            this.warpTick = aoteAnimationTicks;
            updateCameraPos();
            CameraUtils.attachPosition();
        }
    }
}
