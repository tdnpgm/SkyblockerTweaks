package net.toarupgm.skyblockertweaks.client.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import de.hysky.skyblocker.config.SkyblockerConfigManager;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.toarupgm.skyblockertweaks.SkyblockerTweaks;
import net.toarupgm.skyblockertweaks.client.config.category.GeneralCategory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class TweaksOptions {
    public static final int CONFIG_VERSION = 3;
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("skyblocker-tweaks.json");

    private static final ConfigClassHandler<TweaksConfig> HANDLER = ConfigClassHandler.createBuilder(TweaksConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(CONFIG_FILE)
                    .setJson5(false)
                    .appendGsonBuilder(builder -> builder
                            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                            .registerTypeHierarchyAdapter(Identifier.class, new Identifier.Serializer()))
                    .build())
            .build();

    public static void init()
    {
        HANDLER.load();
        ClientLifecycleEvents.CLIENT_STOPPING.register((_client) -> HANDLER.save());
    }

    public static TweaksConfig config() {
        return HANDLER.instance();
    }
    public static Screen createGui(Screen parent)
    {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            builder.title(Text.translatable("sbtweaks.config.title"))
                    .category(GeneralCategory.create(defaults, config));
            return builder;
        }).generateScreen(parent);
    }
}
