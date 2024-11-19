package net.toarupgm.skyblockertweaks.featuers.dungeon_party;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.util.UndashedUuid;
import de.hysky.skyblocker.skyblock.profileviewer.utils.LevelFinder;
import de.hysky.skyblocker.utils.ApiUtils;
import de.hysky.skyblocker.utils.ProfileUtils;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.toarupgm.skyblockertweaks.SkyblockerTweaks;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class DungeonProfileWidget {
    private static final int CLASS_CAP = 50;
    private static final Identifier BAR_FILL = Identifier.of(SkyblockerTweaks.NAMESPACE, "bars/bar_fill");
    private static final Identifier BAR_BACK = Identifier.of(SkyblockerTweaks.NAMESPACE, "bars/bar_back");
    public LevelFinder.LevelInfo classLevel;
    public JsonObject playerProfile;
    public String playerName;
    public JsonObject profile;
    public String className;
    public int secrets;
    private DirectionalLayoutWidget layout = DirectionalLayoutWidget.vertical();
    private static final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    public DungeonProfileWidget(String playerName) {
        this.classLevel = new LevelFinder.LevelInfo(-1,-1);
        this.playerProfile = null;
        this.playerName = playerName;
        this.profile = null;
        this.className = "Class not found";

        fetchProfileData(playerName);
    }

    public void render(DrawContext context, int mouseX, int mouseY, int x, int y)
    {
        if (Objects.isNull(this.playerProfile)) {
            return;
        }

        try {
            SkyblockerTweaks.debug(String.format("[Dungeon Party Viewer] playerProfile: %s",this.playerProfile.asMap().toString()));
            JsonObject dungeonsObject = this.playerProfile.getAsJsonObject("dungeons");
            JsonObject classesObject = dungeonsObject.getAsJsonObject("player_classes");

            String selectedDungeonClass = dungeonsObject.get("selected_dungeon_class").getAsString();
            long experience = classesObject.getAsJsonObject(selectedDungeonClass).get("experience").getAsLong();

            this.secrets = dungeonsObject.get("secrets").getAsInt();
            classLevel = LevelFinder.getLevelInfo("Catacombs", experience);

            context.drawText(textRenderer,Text.literal(this.playerName).setStyle(Style.EMPTY.withBold(true)),x+30,y+10, Color.WHITE.getRGB(),false);
            context.drawText(textRenderer,this.className + " " + this.classLevel.level,x+30,y+10+textRenderer.fontHeight, Color.WHITE.getRGB(),false);
            context.drawText(textRenderer,"Secrets: " + secrets,x+30,y+10+textRenderer.fontHeight*2, Color.WHITE.getRGB(),false);

        } catch (Exception e) {
            SkyblockerTweaks.LOGGER.error("[Dungeon Party Viewer] Error while rendering DungeonPartyScreen",e);
        }

    }

    private void fetchProfileData(String username) {
        String uuidString = ApiUtils.name2Uuid(username);
        CompletableFuture<Void> profileFuture = ProfileUtils.fetchFullProfile(username).thenAccept(profiles -> {
            try {
                Optional<JsonObject> selectedProfile = profiles.getAsJsonArray("profiles").asList().stream()
                        .map(JsonElement::getAsJsonObject)
                        .filter(profile -> profile.getAsJsonPrimitive("selected").getAsBoolean())
                        .findFirst();

                if (selectedProfile.isPresent()) {
                    this.profile = selectedProfile.get();
                    this.playerProfile = profile.getAsJsonObject("members").getAsJsonObject(uuidString);
                } else {
                }
            } catch (Exception e) {
                SkyblockerTweaks.LOGGER.warn("[Dungeon Party Viewer] Error while looking for profile", e);
            }
        });

        CompletableFuture<Void> playerFuture = CompletableFuture.runAsync(() -> {

            UUID uuid = UndashedUuid.fromStringLenient(uuidString);

            SkullBlockEntity.fetchProfileByUuid(uuid).thenAccept(profile -> {
                if(profile.isEmpty()) return;
                this.playerName = profile.get().getName();
                MinecraftClient client = MinecraftClient.getInstance();
            }).exceptionally(ex -> {
                this.playerName = "User not found";
                return null;
            }).join();
        });

        CompletableFuture.allOf(profileFuture, playerFuture);
    }
}
