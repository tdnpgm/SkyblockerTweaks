package net.toarupgm.skyblockertweaks.featuers.base;

import net.minecraft.client.render.RenderTickCounter;

public interface GameRenderingFeature extends ListenerFeature {
    void gameRender(RenderTickCounter tickCounter, boolean tick);
}
