package net.toarupgm.skyblockertweaks.featuers.tune_view;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.toarupgm.skyblockertweaks.SkyblockerTweaks;
import net.toarupgm.skyblockertweaks.client.config.TweaksOptions;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class TuneViewCommand {
    public static void registerCommand(CommandDispatcher<FabricClientCommandSource> commandDispatcher, CommandRegistryAccess registryAccess)
    {
        commandDispatcher.register(literal(SkyblockerTweaks.NAMESPACE)
                .then(literal("tuneview")
                        .then(literal("target_view")
                                .then(argument("yaw", FloatArgumentType.floatArg())
                                        .then(argument("pitch", FloatArgumentType.floatArg())
                                                .executes(context -> {
                                                    TweaksOptions.config().generalConfig.targetPitch = FloatArgumentType.getFloat(context,"pitch");
                                                    TweaksOptions.config().generalConfig.targetYaw = FloatArgumentType.getFloat(context,"yaw");
                                                    return Command.SINGLE_SUCCESS;
                                                })
                                        )
                                )
                        )
                        .then(literal("multiplier")
                                .then(argument("sensitivity", FloatArgumentType.floatArg())
                                        .then(argument("fov", FloatArgumentType.floatArg())
                                                .executes(context -> {
                                                    TweaksOptions.config().generalConfig.tuneViewSensitivityMultiplier = FloatArgumentType.getFloat(context,"sensitivity");
                                                    TweaksOptions.config().generalConfig.tuneViewFovMultiplier = FloatArgumentType.getFloat(context,"fov");
                                                    return Command.SINGLE_SUCCESS;
                                                })
                                        )
                                )
                        )
                )
        );
    }
}
