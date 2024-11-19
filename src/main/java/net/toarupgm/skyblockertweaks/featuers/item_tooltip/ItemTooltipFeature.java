package net.toarupgm.skyblockertweaks.featuers.item_tooltip;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.toarupgm.skyblockertweaks.api.SkyblockItemStack;
import net.toarupgm.skyblockertweaks.client.config.TweaksOptions;
import net.toarupgm.skyblockertweaks.client.config.enums.DetailedTooltip;
import net.toarupgm.skyblockertweaks.featuers.base.ListenerFeature;
import net.toarupgm.skyblockertweaks.featuers.base.TooltipFeature;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ItemTooltipFeature implements ListenerFeature, TooltipFeature {
    private MinecraftClient client;
    @Override
    public void init() {
        client = MinecraftClient.getInstance();
    }

    @Override
    public List<Text> tooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipType tooltipType, List<Text> list) {
        if(TweaksOptions.config().generalConfig.detailedTooltip.equals(DetailedTooltip.HIDDEN)) return new ArrayList<>();
        if(TweaksOptions.config().generalConfig.detailedTooltip.equals(DetailedTooltip.VISIBLE_ON_SHIFT) &&
                GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) != GLFW.GLFW_PRESS)
        {
            return new ArrayList<>();
        }

        SkyblockItemStack sbItemStack = SkyblockItemStack.fromItemStack(itemStack);
        List<Text> textList = new ArrayList<>();

        textList.add(Text.literal(String.format("Skyblock ID: %s", sbItemStack.getID())).withColor(0x888888));

        return textList;
    }
}
