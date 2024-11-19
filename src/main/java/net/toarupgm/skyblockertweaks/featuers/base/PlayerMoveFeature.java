package net.toarupgm.skyblockertweaks.featuers.base;

import net.minecraft.client.network.ClientPlayerEntity;

public interface PlayerMoveFeature extends ListenerFeature {
    void onPlayerMove(double x, double y, double z, double yaw, double pitch);
}
