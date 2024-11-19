package net.toarupgm.skyblockertweaks.utils;

import net.minecraft.util.math.Vec3d;
import net.toarupgm.skyblockertweaks.CameraManager;

public class CameraUtils {
    private static Vec3d position = new Vec3d(0,0,0);
    private static boolean positionAttached = false;
    private static boolean rotationAttached = false;
    private static double fovMultiplier = 1.0f;
    private static float yaw = 0.0f;
    private static float pitch = 0.0f;

    public static void attachPosition() {
        positionAttached = true;
        CameraManager.INSTANCE.onPositionAttached();
    }

    public static void detachPosition()
    {
        positionAttached = false;
    }
    public static void attachRotation()
    {
        rotationAttached = true;
        CameraManager.INSTANCE.onRotationAttached();
    }

    public static void detachRotation()
    {
        rotationAttached = false;
    }

    public static Vec3d getPosition() {
        return new Vec3d(position.x,position.y,position.z);
    }

    public static void setPosition(Vec3d _position) {
        position = _position;
    }

    public static void lookAt(float _yaw, float _pitch) {
        yaw = _yaw;
        pitch = _pitch;
    }

    public static boolean isPositionAttached() {
        return positionAttached;
    }
    public static boolean isRotationAttached() {
        return rotationAttached;
    }

    public static double getFovMultiplier() {
        return fovMultiplier;
    }

    public static void setFovMultiplier(double fovMultiplier) {
        CameraUtils.fovMultiplier = fovMultiplier;
    }

    public static float getYaw() {
        return yaw;
    }

    public static float getPitch() {
        return pitch;
    }
}