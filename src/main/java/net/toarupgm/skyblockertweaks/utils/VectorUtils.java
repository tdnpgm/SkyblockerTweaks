package net.toarupgm.skyblockertweaks.utils;

import net.minecraft.util.math.Vec3d;

public class VectorUtils {
    public static Vec3d clone(Vec3d vec)
    {
        return new Vec3d(vec.x,vec.y,vec.z);
    }
}
