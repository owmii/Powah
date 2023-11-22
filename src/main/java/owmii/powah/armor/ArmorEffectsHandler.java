package owmii.powah.armor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
    private static AttributeModifier armorHealthModifier = new AttributeModifier(UUID.randomUUID(), "powahArmorHealthModifier", 20, Operation.ADDITION);
    private static final LinkedHashMap<String, ArmorEffect> HANDLERS = new LinkedHashMap<>();
    static {
        HANDLERS.put("starter", new ArmorEffect(player -> {getArmorData(player).putBoolean("isThunderImmune", true);}, player -> {getArmorData(player).putBoolean("isThunderImmune", false);}));
        HANDLERS.put("basic", new ArmorEffect(player -> {getArmorData(player).putBoolean("hasHaste", true);}, player -> {getArmorData(player).putBoolean("hasHaste", false);}));
        HANDLERS.put("hardened", new ArmorEffect(player -> {getArmorData(player).putBoolean("hasResistance", true);}, player -> {getArmorData(player).putBoolean("hasResistance", false);}));
        HANDLERS.put("blazing", new ArmorEffect(player -> {getArmorData(player).putBoolean("hasJumpBoost", true);}, player -> {getArmorData(player).putBoolean("hasJumpBoost", false);}));
        HANDLERS.put("niotic", new ArmorEffect(player -> {getArmorData(player).putBoolean("hasWaterBreathing", true);}, player -> {getArmorData(player).putBoolean("hasWaterBreathing", false);}));
        HANDLERS.put("spirited", new ArmorEffect(player -> {getArmorData(player).putBoolean("hasNightVision", true);}, player -> {getArmorData(player).putBoolean("hasNightVision", false);}));
        HANDLERS.put("nitro", new ArmorEffect(player -> {
            ModifiableAttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            if (!maxHealth.hasModifier(armorHealthModifier)) maxHealth.applyNonPersistentModifier(armorHealthModifier);
        }, player -> {
            ModifiableAttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealth.hasModifier(armorHealthModifier)) maxHealth.removeModifier(armorHealthModifier);
        }));
        HANDLERS.put("overcharged", new ArmorEffect(player -> {player.abilities.allowFlying = true;}, player -> {player.abilities.allowFlying = false; player.abilities.isFlying = false;}));
    }

    @SubscribeEvent
    public static void onArmorChange(LivingEquipmentChangeEvent e) {
        if (!(e.getEntity() instanceof ServerPlayerEntity)) return;
        ServerPlayerEntity player = (ServerPlayerEntity)e.getEntity();

        ArrayList<String> tiers = new ArrayList<>(HANDLERS.keySet());
        List<ArmorEffect> effects = HANDLERS.values().stream().collect(Collectors.toList());
        
        Item fromItem = e.getFrom().getItem();
        String itemId = fromItem.getRegistryName().toString();
        if (Pattern.compile(Powah.MOD_ID+":energized_(helmet|chestplate|leggings|boots)_\\w+").matcher(itemId).matches()) {
            String tier = itemId.split("_")[2];
            if (tiers.contains(tier)) {
                for (int i = 0; i <= tiers.indexOf(tier); i++) effects.get(i).unequipEffect.accept(player);
                player.sendPlayerAbilities();
            }
        }

        if (!isEnergizedArmor(e.getTo().getItem().getRegistryName().toString())) return;

        CompoundNBT nbt = player.getPersistentData();
        if (!nbt.contains("powahArmorData")) {
            CompoundNBT armorData = new CompoundNBT();
            
            armorData.putBoolean("isThunderImmune", false);
            armorData.putBoolean("hasHaste", false);
            armorData.putBoolean("hasResistance", false);
            armorData.putBoolean("hasJumpBoost", false);
            armorData.putBoolean("hasWaterBreathing", false);
            armorData.putBoolean("hasNightVision", false);

            nbt.put("powahArmorData", armorData);
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
        if (!isEnergizedArmor(itemId)) return;

        String tier = itemId.split("_")[2];
        ArrayList<String> tiers = new ArrayList<>(HANDLERS.keySet());

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

    private static boolean isEnergizedArmor(String itemId) {
        return Pattern.compile(Powah.MOD_ID+":energized_(helmet|chestplate|leggings|boots)_\\w+").matcher(itemId).matches();
    }
    public static @Nullable CompoundNBT getArmorData(PlayerEntity player) {
        CompoundNBT nbt = player.getPersistentData();
        if (!nbt.contains("powahArmorData")) return null;
        return nbt.getCompound("powahArmorData");
    }
}
