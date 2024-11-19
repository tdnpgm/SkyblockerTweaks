package net.toarupgm.skyblockertweaks.featuers.base;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public interface TooltipFeature extends ListenerFeature {
    List<Text> tooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipType tooltipType, List<Text> list);
}
