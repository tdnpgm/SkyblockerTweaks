package net.toarupgm.skyblockertweaks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyblockerTweaks implements ModInitializer {

    public static final String NAMESPACE = "sbtweaks";
    public static final String MOD_ID = "skyblockerTweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final boolean DEBUGGING = false;
    public static Gson GSON;
    public static void debug(String log)
    {
        SkyblockerTweaks.LOGGER.info(log);
    }
    @Override
    public void onInitialize() {
        GSON = (new GsonBuilder()).setPrettyPrinting().create();
    }
}
