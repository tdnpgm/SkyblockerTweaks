package net.toarupgm.skyblockertweaks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Arm;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ClientFakePlayerEntity extends LivingEntity {
    public ClientFakePlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return null;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot var1) {
        return new ItemStack(Items.AIR);
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Nullable
    @Override
    public StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            return player.getStatusEffect(effect);
        }
        return null;
    }
}