package net.toarupgm.skyblockertweaks.featuers.base;

import net.minecraft.sound.SoundEvent;

public interface OnPlaySoundFeature extends ListenerFeature {
    void onPlaySound(SoundEvent e);
}
