package owmii.powah.armor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.TickEvent;
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

        if (nbt.contains("hasHealthBoost") && nbt.getBoolean("hasHealthBoost")) {
            player.addPotionEffect(new EffectInstance(Effects.HEALTH_BOOST, 40, 4, false, false));
        }
    }
}
