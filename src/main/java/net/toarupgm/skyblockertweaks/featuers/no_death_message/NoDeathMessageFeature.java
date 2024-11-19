package net.toarupgm.skyblockertweaks.featuers.no_death_message;

import de.hysky.skyblocker.skyblock.dungeon.DungeonScore;
import de.hysky.skyblocker.utils.Utils;
import net.minecraft.text.Text;
import net.toarupgm.skyblockertweaks.client.config.TweaksOptions;
import net.toarupgm.skyblockertweaks.client.config.enums.MessageResult;
import net.toarupgm.skyblockertweaks.featuers.base.GameMessageFeature;
import net.toarupgm.skyblockertweaks.featuers.base.ListenerFeature;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoDeathMessageFeature implements ListenerFeature, GameMessageFeature {
    private static final Pattern noDeathMessagePattern = Pattern.compile("^ ☠ .+? ");
    @Override
    public void init() {

    }

    @Override
    public boolean gameMessage(Text text, boolean overlay) {
        if(overlay) return true;
        if(TweaksOptions.config().generalConfig.deathMessage.equals(MessageResult.PASS)) return true;

//        SkyblockerTweaks.LOGGER.info(String.format("{CHAT} %s",text.getString()));


        Matcher matcher = noDeathMessagePattern.matcher(text.getString());
        if(matcher.find())
        {
            if(TweaksOptions.config().generalConfig.deathMessage.equals(MessageResult.FILTER)) return false;
            if(TweaksOptions.config().generalConfig.deathMessage.equals(MessageResult.FILTER_ON_NORMAL_WORLD))
            {
                return Utils.isInDungeons() && DungeonScore.isDungeonStarted() && Utils.isInKuudra();
            }
        }

        return true;
    }

}
