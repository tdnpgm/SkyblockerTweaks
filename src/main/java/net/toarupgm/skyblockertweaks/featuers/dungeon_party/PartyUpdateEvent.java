package net.toarupgm.skyblockertweaks.featuers.dungeon_party;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.ArrayList;

public interface PartyUpdateEvent
{
    Event<PartyUpdateEvent> EVENT = EventFactory.createArrayBacked(PartyUpdateEvent.class, (callbacks) -> {
        return (playerNames) -> {
            PartyUpdateEvent[] var2 = callbacks;
            int var3 = callbacks.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                PartyUpdateEvent event = var2[var4];
                event.onPartyUpdate(playerNames);
            }
        };
    });
    void onPartyUpdate(ArrayList<String> playerNames);
}
