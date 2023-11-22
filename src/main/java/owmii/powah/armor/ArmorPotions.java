package owmii.powah.armor;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import owmii.powah.Powah;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Powah.MOD_ID)
public class ArmorPotions {
    @SubscribeEvent
    public static void onPlayerDamage(LivingHurtEvent e) {
        if (!(e.getEntity() instanceof PlayerEntity)) return;

        PlayerEntity player = (PlayerEntity)e.getEntity();
        CompoundNBT nbt = player.getPersistentData();

        if (e.getSource()==DamageSource.LIGHTNING_BOLT && nbt.contains("isThunderImmune") && nbt.getBoolean("isThunderImmune")) {
            player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 60, 1, false, false));
            e.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent e) {
        PlayerEntity player = e.player;
        CompoundNBT nbt = player.getPersistentData();

        if (nbt.contains("hasHaste") && nbt.getBoolean("hasHaste")) {
            player.addPotionEffect(new EffectInstance(Effects.HASTE, 5, 0, true, false));
        }
        if (nbt.contains("hasResistance") && nbt.getBoolean("hasResistance")) {
            player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 5, 0, true, false));
        }
        if (nbt.contains("hasJumpBoost") && nbt.getBoolean("hasJumpBoost")) {
            player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 5, 0, true, false));
        }
        if (nbt.contains("hasWaterBreathing") && nbt.getBoolean("hasWaterBreathing")) {
            player.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 5, 0, true, false));
        }
        if (nbt.contains("hasNightVision") && nbt.getBoolean("hasNightVision")) {
            player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 5, 0, true, false));
        }
        if (nbt.contains("hasHealthBoost") && nbt.getBoolean("hasHealthBoost")) {
            player.addPotionEffect(new EffectInstance(Effects.HEALTH_BOOST, 40, 4, true, false));
        }
    }
}
