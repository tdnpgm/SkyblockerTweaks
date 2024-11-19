package net.toarupgm.skyblockertweaks.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ClientKeyBindings {
    public static KeyBinding TUNEVIEW_KEY;
    public static void register()
    {
        TUNEVIEW_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "sbtweaks.keybinding.tuneview_key",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                "category.sbtweaks.mod"
        ));
    }
}
