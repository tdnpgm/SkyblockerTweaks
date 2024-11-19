package net.toarupgm.skyblockertweaks.api;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class SkyblockItemStack {
    private ItemStack itemStack;
    private String id;

    public SkyblockItemStack(ItemStack itemStack, String id) {
        this.itemStack = itemStack;
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public static SkyblockItemStack fromItemStack(ItemStack itemStack)
    {
        NbtCompound nbtCompound = itemStack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
        String id = nbtCompound.getString("id");

        return new SkyblockItemStack(itemStack, id);
    }
}
