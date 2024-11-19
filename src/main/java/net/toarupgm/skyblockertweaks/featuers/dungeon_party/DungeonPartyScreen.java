package net.toarupgm.skyblockertweaks.featuers.dungeon_party;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.util.UndashedUuid;
import de.hysky.skyblocker.skyblock.profileviewer.utils.LevelFinder;
import de.hysky.skyblocker.utils.ApiUtils;
import de.hysky.skyblocker.utils.ProfileUtils;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.toarupgm.skyblockertweaks.SkyblockerTweaks;
import net.toarupgm.skyblockertweaks.featuers.base.GameMessageFeature;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class DungeonPartyScreen extends Screen {
    public static Text TITLE = Text.of("Dungeon Party Viewer");
    protected static final Identifier BACKGROUND_TEXTURE = Identifier.ofVanilla("social_interactions/background");
    public static int GUI_WIDTH = 322;
    public static int GUI_HEIGHT = 180;
    public ArrayList<DungeonProfileWidget> dungeonProfileWidgets = new ArrayList<>();
    public static MinecraftClient instance = MinecraftClient.getInstance();

    public DungeonPartyScreen() {
        super(TITLE);

        if(Objects.isNull(instance.player)) return;

        instance.player.networkHandler.sendChatCommand("party list");
        PartyUpdateEvent.EVENT.register(playerNames -> {
            int playerNum = playerNames.size();
            this.dungeonProfileWidgets.clear();
            for (int i = 0; i < playerNum; i++) {
                String playerName = playerNames.get(i);

                DungeonProfileWidget profileWidget = new DungeonProfileWidget(playerName);
                dungeonProfileWidgets.add(profileWidget);
            }
        });

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int x = -GUI_WIDTH/2;
        int y = -GUI_HEIGHT/2;

        synchronized (this) {
            super.render(context, mouseX, mouseY, delta);
        }
        context.drawGuiTexture(BACKGROUND_TEXTURE, x, y, GUI_WIDTH, GUI_HEIGHT);

        for (int i = 0; i < dungeonProfileWidgets.size(); i++) {
            DungeonProfileWidget profileWidget = dungeonProfileWidgets.get(i);
            if(Objects.isNull(profileWidget)) return;
            profileWidget.render(context, mouseX, mouseY, x + i*20, y);
        }
    }
}
