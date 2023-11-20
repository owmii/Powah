package owmii.powah.armor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import owmii.powah.Powah;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Powah.MOD_ID)
public class ArmorEffectsHandler {
    private static LinkedHashMap<String, ArmorEffect> handlers = new LinkedHashMap<>();
    static {
        handlers.put("starter", new ArmorEffect(player -> {player.getPersistentData().putBoolean("isThunderImmune", true);}, player -> {player.getPersistentData().putBoolean("isThunderImmune", false);}));
        handlers.put("basic", new ArmorEffect());
        handlers.put("hardened", new ArmorEffect());
        handlers.put("blazing", new ArmorEffect());
        handlers.put("spirited", new ArmorEffect());
        handlers.put("niotic", new ArmorEffect());
        handlers.put("nitro", new ArmorEffect(player -> {player.getPersistentData().putBoolean("hasHealthBoost", true);}, player -> {player.getPersistentData().putBoolean("hasHealthBoost", false);}));
        handlers.put("overcharged", new ArmorEffect(player -> {player.abilities.allowFlying = true;}, player -> {player.abilities.allowFlying = false; player.abilities.isFlying = false;}));

    }

    @SubscribeEvent
    public static void onArmorChange(LivingEquipmentChangeEvent e) {
        if (!(e.getEntity() instanceof ServerPlayerEntity)) return;
        ServerPlayerEntity player = (ServerPlayerEntity)e.getEntity();

        ArrayList<String> tiers = new ArrayList<>(handlers.keySet());
        List<ArmorEffect> effects = handlers.values().stream().collect(Collectors.toList());
        System.out.println(tiers);
        Item fromItem = e.getFrom().getItem();
        String itemId = fromItem.getRegistryName().toString();
        if (Pattern.compile(Powah.MOD_ID+":energized_(helmet|chestplate|leggings|boots)_\\w+").matcher(itemId).matches()) {
            String tier = itemId.split("_")[2];
            if (tiers.contains(tier)) {
                for (int i = 0; i <= tiers.indexOf(tier); i++) effects.get(i).unequipEffect.accept(player);
                player.sendPlayerAbilities();
            }
        }

        Integer wearingIndex = null;
        for (int i = 0; i < tiers.size(); i++) {
            boolean wearingArmor = true;
            for (ItemStack a : player.getArmorInventoryList()) {
                if (a.isEmpty()) {
                    wearingArmor = false;
                    break;
                }
                
                for(int t = tiers.size()-1; t >= i; t--) {
                    if (Pattern.compile(Powah.MOD_ID+":energized_(helmet|chestplate|leggings|boots)_"+tiers.get(t)).matcher(a.getItem().getRegistryName().toString()).matches()) {
                        wearingArmor = true;
                        break;
                    }
                    else wearingArmor = false;
                }
            }
            if (wearingArmor) wearingIndex = i;
        }

        if (wearingIndex==null) return;
        for(int j = 0; j <= wearingIndex; j++) effects.get(j).equipEffect.accept(player);
        player.sendPlayerAbilities();
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onArmorTooltip(ItemTooltipEvent e) {
        String itemId = e.getItemStack().getItem().getRegistryName().toString();
        if (!Pattern.compile(Powah.MOD_ID+":energized_(helmet|chestplate|leggings|boots)_\\w+").matcher(itemId).matches()) return;

        String tier = itemId.split("_")[2];
        ArrayList<String> tiers = new ArrayList<>(handlers.keySet());

        if (!tiers.contains(tier)) return;
        if (!Screen.hasShiftDown()) {
            e.getToolTip().add(new StringTextComponent("[SHIFT]").mergeStyle(TextFormatting.GRAY));
            return;
        }

        e.getToolTip().add(new StringTextComponent("[SHIFT]").mergeStyle(TextFormatting.GREEN));
        e.getToolTip().add(new StringTextComponent("----------------------").mergeStyle(TextFormatting.GRAY));
        for (int i = 0; i <= tiers.indexOf(tier); i++) {
            String langKey = "armor_effect."+Powah.MOD_ID+"."+tiers.get(i);
            if (I18n.hasKey(langKey)) e.getToolTip().add(new StringTextComponent("- ").append(new TranslationTextComponent(langKey)).mergeStyle(TextFormatting.GRAY));
        }
    }
}
