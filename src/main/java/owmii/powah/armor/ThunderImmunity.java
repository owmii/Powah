package owmii.powah.armor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import owmii.powah.Powah;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Powah.MOD_ID)
public class ThunderImmunity {
    @SubscribeEvent
    public static void onPlayerDamage(LivingHurtEvent e) {
        if (!(e.getEntity() instanceof PlayerEntity)) return;

        PlayerEntity player = (PlayerEntity)e.getEntity();

        Boolean isNotWearingPowahArmor = false;
        for (ItemStack a : player.getArmorInventoryList()) {
            if (a.isEmpty() || !a.getItem().getRegistryName().getNamespace().equals(Powah.MOD_ID)) isNotWearingPowahArmor = true;
        }

        if (e.getSource()==DamageSource.LIGHTNING_BOLT && !isNotWearingPowahArmor) {
            player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 60, 1, false, false));
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void addToolTip(ItemTooltipEvent e) {
        if (e.getItemStack().getItem() instanceof ArmorItem && e.getItemStack().getItem().getRegistryName().getNamespace().equals(Powah.MOD_ID)) e.getToolTip().add(new TranslationTextComponent("tooltip.powah.armor_lightning_immunity").mergeStyle(TextFormatting.BLUE));
    }
}
