package com.teamaurora.fruitful.core.other;

import com.minecraftabnormals.abnormals_core.core.util.TradeUtil;
import com.teamaurora.fruitful.core.Fruitful;
import com.teamaurora.fruitful.core.registry.FruitfulBlocks;
import com.teamaurora.fruitful.core.registry.FruitfulEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fruitful.MODID)
public class FruitfulEvents {

    @SubscribeEvent
    public static void livingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        ItemStack item = event.getItem();

        LivingEntity entity = event.getEntityLiving();

        // SUSTAINING //
        if (entity.isPotionActive(FruitfulEffects.SUSTAINING.get())) {
            int amplifier = entity.getActivePotionEffect(FruitfulEffects.SUSTAINING.get()).getAmplifier();
            if (item.isFood()) {
                int foodToAdd = 2 * (amplifier + 1);
                if (entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                    player.getFoodStats().addStats(foodToAdd, 0);
                }
            }
        }

        // APPLES GIVE SUSTAINING //
        if (item.getItem() == Items.APPLE) {
            if (entity.isPotionActive(FruitfulEffects.SUSTAINING.get())) {
                int ticksRemaining = entity.getActivePotionEffect(FruitfulEffects.SUSTAINING.get()).getDuration();
                entity.addPotionEffect(new EffectInstance(FruitfulEffects.SUSTAINING.get(), Math.max(200, ticksRemaining), 0, false, false, true));
            } else {
                entity.addPotionEffect(new EffectInstance(FruitfulEffects.SUSTAINING.get(), 200, 0, false, false, true));
            }
        }
        if (item.getItem().getRegistryName().equals(new ResourceLocation("environmental","apple_pie"))) {
            if (entity.isPotionActive(FruitfulEffects.SUSTAINING.get())) {
                int ticksRemaining = entity.getActivePotionEffect(FruitfulEffects.SUSTAINING.get()).getDuration();
                entity.addPotionEffect(new EffectInstance(FruitfulEffects.SUSTAINING.get(), Math.max(400, ticksRemaining), 0, false, false, true));
            } else {
                entity.addPotionEffect(new EffectInstance(FruitfulEffects.SUSTAINING.get(), 400, 0, false, false, true));
            }
        }
    }

    @SubscribeEvent
    public static void onWandererTradesEvent(WandererTradesEvent event) {
        TradeUtil.addWandererTrades(event,
                new TradeUtil.AbnormalsTrade(5, FruitfulBlocks.FLOWERING_OAK_SAPLING.get().asItem(), 1, 8, 1)
        );
    }
}
