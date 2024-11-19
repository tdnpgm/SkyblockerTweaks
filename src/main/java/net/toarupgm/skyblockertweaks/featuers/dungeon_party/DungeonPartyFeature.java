package net.toarupgm.skyblockertweaks.featuers.dungeon_party;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import de.hysky.skyblocker.utils.scheduler.Scheduler;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import net.minecraft.world.border.WorldBorderListener;
import net.toarupgm.skyblockertweaks.SkyblockerTweaks;
import net.toarupgm.skyblockertweaks.featuers.base.CommandFeature;
import net.toarupgm.skyblockertweaks.featuers.base.GameMessageFeature;
import net.toarupgm.skyblockertweaks.featuers.base.ListenerFeature;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class DungeonPartyFeature implements ListenerFeature, CommandFeature, GameMessageFeature {
    private static final Pattern PARTY_PLAYER = Pattern.compile("\\w+ ●");
    public ArrayList<String> playerNames;
    @Override
    public void init() {
        playerNames = new ArrayList<>();
    }

    @Override
    public void registerCommand(CommandDispatcher<FabricClientCommandSource> commandDispatcher, CommandRegistryAccess registryAccess) {
        commandDispatcher.register(literal("dparty")
                    .executes(commandContext -> showDungeonPartyGUI())
        );
    }

    public int showDungeonPartyGUI()
    {
        return Scheduler.queueOpenScreen(new DungeonPartyScreen());
    }

    @Override
    public boolean gameMessage(Text text, boolean overlay) {
        if(overlay) return true;
        String string = text.getString();
        SkyblockerTweaks.debug(String.format("[Skyblocker Tweaks Chat] %s", string));

        Matcher matcher = PARTY_PLAYER.matcher(string);

        boolean partyLeaderB = string.startsWith("Party Leader: ");
        boolean partyMemberB = string.startsWith("Party Members: ");


        if(partyLeaderB)
        {
            SkyblockerTweaks.debug("[Dungeon Party] Party Member Cleared");
            playerNames.clear();
        }
        if(partyLeaderB || partyMemberB) {
            while (matcher.find()) {
                String matchString = matcher.group(0);
                if (matchString.length() <= 3) continue;
                String playerName = matchString.substring(0, matchString.length() - 2);

                playerNames.add(playerName);
                SkyblockerTweaks.debug(String.format("[Dungeon Party] Joined %s", playerName));
            }
        }
        if(partyMemberB)
        {
            PartyUpdateEvent.EVENT.invoker().onPartyUpdate(playerNames);
            SkyblockerTweaks.debug(String.format("[Dungeon Party] Party: %s", playerNames.toString()));
        }
        return true;
    }
}
