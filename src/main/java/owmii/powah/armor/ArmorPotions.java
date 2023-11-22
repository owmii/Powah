package owmii.powah.armor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import owmii.powah.Powah;

import static owmii.powah.armor.ArmorEffectsHandler.getArmorData;

import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Powah.MOD_ID)
public class ArmorPotions {
    private static final HashMap<String, Effect> POTIONS = new HashMap<>(); 
    static {
        POTIONS.put("Haste", Effects.HASTE);
        POTIONS.put("Resistance", Effects.RESISTANCE);
        POTIONS.put("JumpBoost", Effects.JUMP_BOOST);
        POTIONS.put("WaterBreathing", Effects.WATER_BREATHING);
        POTIONS.put("NightVision", Effects.NIGHT_VISION);

    }

    @SubscribeEvent
    public static void onPlayerDamage(LivingHurtEvent e) {
        if (!(e.getEntity() instanceof PlayerEntity)) return;

        PlayerEntity player = (PlayerEntity)e.getEntity();
        CompoundNBT armorData = getArmorData(player);
        if (armorData==null || e.getSource()!=DamageSource.LIGHTNING_BOLT || !armorData.getBoolean("isThunderImmune")) return;

        player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 60, 1, false, false));
        e.setCanceled(true);
    }
    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent e) {
        PlayerEntity player = e.player;
        CompoundNBT armorData = getArmorData(player);
        if (armorData==null) return;

        POTIONS.forEach((str, eff) -> {
            if (armorData.getBoolean("has"+str)) player.addPotionEffect(new EffectInstance(eff, 5, 0, true, false));
        });
    }
}
