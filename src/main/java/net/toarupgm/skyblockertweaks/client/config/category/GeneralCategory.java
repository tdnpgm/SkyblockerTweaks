package net.toarupgm.skyblockertweaks.client.config.category;

import de.hysky.skyblocker.config.ConfigUtils;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.gui.controllers.string.StringController;
import net.minecraft.text.Text;
import net.toarupgm.skyblockertweaks.client.config.TweaksConfig;
import net.toarupgm.skyblockertweaks.client.config.enums.DetailedTooltip;
import net.toarupgm.skyblockertweaks.client.config.enums.InterpolationType;
import net.toarupgm.skyblockertweaks.client.config.enums.MessageResult;

public class GeneralCategory {
    public static ConfigCategory create(TweaksConfig defaults, TweaksConfig config) {
        return ConfigCategory.createBuilder()
                .name(Text.translatable("sbtweaks.config.general"))

                .option(Option.<String>createBuilder()
                        .name(Text.translatable("sbtweaks.config.hypixel_api_key"))
                        .binding(defaults.generalConfig.hypixelAPIKey,
                                () -> config.generalConfig.hypixelAPIKey,
                                newValue -> config.generalConfig.hypixelAPIKey = newValue)
                        .controller(StringControllerBuilder::create)
                        .build())
                .option(Option.<DetailedTooltip>createBuilder()
                        .name(Text.translatable("sbtweaks.config.detailed_tooltip"))
                        .binding(defaults.generalConfig.detailedTooltip,
                                () -> config.generalConfig.detailedTooltip,
                                newValue -> config.generalConfig.detailedTooltip = newValue)
                        .controller(ConfigUtils::createEnumCyclingListController)
                        .build())
                .option(Option.<MessageResult>createBuilder()
                        .name(Text.translatable("sbtweaks.config.death_message"))
                        .binding(defaults.generalConfig.deathMessage,
                                () -> config.generalConfig.deathMessage,
                                newValue -> config.generalConfig.deathMessage = newValue)
                        .controller(ConfigUtils::createEnumCyclingListController)
                        .build())
                .group(OptionGroup.createBuilder()
                        .name(Text.translatable("sbtweaks.config.group.smooth_aote"))

                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("sbtweaks.config.enable"))
                                .binding(defaults.generalConfig.smoothAote,
                                        () -> config.generalConfig.smoothAote,
                                        newValue -> config.generalConfig.smoothAote = newValue)
                                .controller(ConfigUtils::createBooleanController)
                                .build())
                        .option(Option.<Double>createBuilder()
                                .name(Text.translatable("sbtweaks.config.config.aote_animation_ticks"))
                                .binding(defaults.generalConfig.aoteAnimationTicks,
                                        () -> config.generalConfig.aoteAnimationTicks,
                                        newValue -> config.generalConfig.aoteAnimationTicks = newValue)
                                .controller(opt -> DoubleSliderControllerBuilder.create(opt).range(0.0,30.0).step(1.0))
                                .build())
                        .option(Option.<InterpolationType>createBuilder()
                                .name(Text.translatable("sbtweaks.config.config.aote_interpolation_type"))
                                .binding(defaults.generalConfig.aoteInterpolationType,
                                        () -> config.generalConfig.aoteInterpolationType,
                                        newValue -> config.generalConfig.aoteInterpolationType = newValue)
                                .controller(ConfigUtils::createEnumCyclingListController)
                                .build())

                        .build()
                )

                .group(OptionGroup.createBuilder()
                        .name(Text.translatable("sbtweaks.config.group.tune_view"))
                        .option(Option.<Float>createBuilder()
                                .name(Text.translatable("sbtweaks.config.target_pitch"))
                                .binding(defaults.generalConfig.targetPitch,
                                        () -> config.generalConfig.targetPitch,
                                        newValue -> config.generalConfig.targetPitch = newValue)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.0f,180.0f).step(0.01f))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Text.translatable("sbtweaks.config.target_yaw"))
                                .binding(defaults.generalConfig.targetYaw,
                                        () -> config.generalConfig.targetYaw,
                                        newValue -> config.generalConfig.targetYaw = newValue)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.0f,180.0f).step(0.01f))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Text.translatable("sbtweaks.config.tuneview_sensitivity_multiplier"))
                                .binding(defaults.generalConfig.tuneViewSensitivityMultiplier,
                                        () -> config.generalConfig.tuneViewSensitivityMultiplier,
                                        newValue -> config.generalConfig.tuneViewSensitivityMultiplier = newValue)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.0f,1.0f).step(0.01f))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Text.translatable("sbtweaks.config.tuneview_fov_multiplier"))
                                .binding(defaults.generalConfig.tuneViewFovMultiplier,
                                        () -> config.generalConfig.tuneViewFovMultiplier,
                                        newValue -> config.generalConfig.tuneViewFovMultiplier = newValue)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.0f,1.0f).step(0.01f))
                                .build())
                        .build())

                .build();

    }
}
