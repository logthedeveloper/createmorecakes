package com.logthedeveloper.createmorecakes.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BlazeMilkCakeItem extends Item {
    public BlazeMilkCakeItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        // Let the base Item handle hunger restoration and shrinking the stack
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide) {
            // Clear all current effects, like milk
            entity.removeAllEffects();
            // Jump Boost II for 30 seconds (600 ticks). Amplifier 1 = level II.
            entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 600, 1));
            // Regeneration III for 30 seconds. Amplifier 2 = level III.
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 2));

            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.GENERIC_DRINK, entity.getSoundSource(), 1.0F, 1.0F);
        }

        return result;
    }
}