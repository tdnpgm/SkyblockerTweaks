package net.toarupgm.skyblockertweaks.featuers.tune_view;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import de.hysky.skyblocker.utils.ApiUtils;
import de.hysky.skyblocker.utils.Http;
import de.hysky.skyblocker.utils.render.RenderHelper;
import de.hysky.skyblocker.utils.scheduler.Scheduler;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.network.RateLimitedConnection;
import net.minecraft.util.math.Vec3d;
import net.toarupgm.skyblockertweaks.SkyblockerTweaks;
import net.toarupgm.skyblockertweaks.client.ClientKeyBindings;
import net.toarupgm.skyblockertweaks.client.config.TweaksOptions;
import net.toarupgm.skyblockertweaks.featuers.base.ScheduledFeature;
import net.toarupgm.skyblockertweaks.utils.CameraUtils;
import net.toarupgm.skyblockertweaks.utils.MouseUtils;

import java.util.Objects;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class TuneViewFeature implements ScheduledFeature {

    public MinecraftClient client;


    @Override
    public void init()
    {
        this.client = MinecraftClient.getInstance();
        WorldRenderEvents.AFTER_TRANSLUCENT.register(this::renderAfterTranslucent);

        Scheduler.INSTANCE.scheduleCyclic(this::update,1,false);

        ClientCommandRegistrationCallback.EVENT.register(TuneViewCommand::registerCommand);
    }
    @Override
    public void update() {
        if(Objects.isNull(client.player)) return;
        if(ClientKeyBindings.TUNEVIEW_KEY.isPressed())
        {
            MouseUtils.setMouseSensMultiplier(TweaksOptions.config().generalConfig.tuneViewSensitivityMultiplier);
            CameraUtils.setFovMultiplier(TweaksOptions.config().generalConfig.tuneViewFovMultiplier);
        } else {
            MouseUtils.setMouseSensMultiplier(1.0);
            CameraUtils.setFovMultiplier(1.0f);
        }
    }
    public void renderAfterTranslucent(WorldRenderContext worldRenderContext)
    {
        if(ClientKeyBindings.TUNEVIEW_KEY.isPressed())
        {
            Vec3d cameraPos = worldRenderContext.camera().getPos();
            Vec3d point = cameraPos.add(Vec3d.fromPolar(TweaksOptions.config().generalConfig.targetPitch, TweaksOptions.config().generalConfig.targetYaw));

            RenderHelper.renderLineFromCursor(worldRenderContext, point, new float[]{0.0f,0.6f,0.8f},1f,3f);
        }
    }
}
