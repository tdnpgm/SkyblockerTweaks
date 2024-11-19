package net.toarupgm.skyblockertweaks.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.TypedActionResult;
import net.toarupgm.skyblockertweaks.CameraManager;
import net.toarupgm.skyblockertweaks.api.SkyblockItemStack;
import net.toarupgm.skyblockertweaks.client.config.TweaksOptions;
import net.toarupgm.skyblockertweaks.featuers.*;
import net.toarupgm.skyblockertweaks.featuers.base.*;
import net.toarupgm.skyblockertweaks.featuers.dungeon_party.DungeonPartyFeature;
import net.toarupgm.skyblockertweaks.featuers.item_tooltip.ItemTooltipFeature;
import net.toarupgm.skyblockertweaks.featuers.no_death_message.NoDeathMessageFeature;
import net.toarupgm.skyblockertweaks.featuers.smooth_aote.SmoothAoteFeature;
import net.toarupgm.skyblockertweaks.featuers.tune_view.TuneViewFeature;

import java.util.List;
import java.util.Objects;

public class SkyblockerTweaksClient implements ClientModInitializer {
    public MinecraftClient client;
    public static FeatureManager featureManager;
    public CameraManager cameraManager;
    @Override
    public void onInitializeClient() {
        featureManager = new FeatureManager(new ListenerFeature[]{
                new SmoothAoteFeature(),
                new TuneViewFeature(),
                new ItemTooltipFeature(),
                new NoDeathMessageFeature(),
                new DungeonPartyFeature()
        });


        this.client = MinecraftClient.getInstance();

        featureManager.forEach(ListenerFeature.class, ListenerFeature::init);



        UseItemCallback.EVENT.register((playerEntity, world, hand) -> {
            featureManager.forEach(OnUseItemFeature.class, useItemFeature -> {
                if(Objects.isNull(client.player)) return;
                SkyblockItemStack skyblockItemStack = SkyblockItemStack.fromItemStack(client.player.getInventory().getMainHandStack());

                useItemFeature.onUseSkyblockItem(skyblockItemStack);
            });
            return TypedActionResult.pass(ItemStack.EMPTY);
        });



        ClientTickEvents.START_CLIENT_TICK.register(minecraftClient -> {
            featureManager.forEach(OnStartTickFeature.class, OnStartTickFeature::onTickStart);
        });

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            featureManager.forEach(OnEndTickFeature.class, OnEndTickFeature::onTickEnd);
        });

        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            featureManager.forEach(TooltipFeature.class, tooltipFeature -> {
                List<Text> additionalTooltip = tooltipFeature.tooltip(itemStack, tooltipContext, tooltipType, list);
                list.addAll(additionalTooltip);
            });
        });

        ClientReceiveMessageEvents.ALLOW_GAME.register((text, b) -> {

            return featureManager.whileRun(GameMessageFeature.class, gameMessageFeature -> {
                return gameMessageFeature.gameMessage(text,b);
            });
        });

        TweaksOptions.init();
        cameraManager = new CameraManager();

        ClientCommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess) -> {
            featureManager.forEach(CommandFeature.class, commandFeature -> {
                commandFeature.registerCommand(commandDispatcher,commandRegistryAccess);
            });
        });

        ClientKeyBindings.register();
    }
}
