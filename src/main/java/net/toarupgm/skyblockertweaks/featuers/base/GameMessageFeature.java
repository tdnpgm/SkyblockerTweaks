package net.toarupgm.skyblockertweaks.featuers.base;

import net.minecraft.text.Text;

public interface GameMessageFeature extends ListenerFeature {
    boolean gameMessage(Text text, boolean overlay);
}
