package net.toarupgm.skyblockertweaks.featuers.base;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;

public interface CommandFeature extends ListenerFeature {
    void registerCommand(CommandDispatcher<FabricClientCommandSource> commandDispatcher, CommandRegistryAccess registryAccess);
}
