package owmii.powah.armor;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import owmii.powah.Powah;
import owmii.powah.item.Itms;

import java.util.function.Supplier;

public enum ModArmorMaterial implements IArmorMaterial {

    ARMOR_STARTER("energized_armor_starter", -1, new int[] { 2, 4, 5, 2 }, 12,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> Ingredient.fromItems(Itms.ENERGIZED_STEEL)
    ),

    ARMOR_BASIC("energized_armor_basic", -1, new int[] { 2, 5, 6, 2}, 12,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> Ingredient.fromItems(Itms.ENERGIZED_STEEL)
    ),
    ARMOR_HARDENED("energized_armor_hardened", -1, new int[] { 2, 6, 6, 2 }, 12,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> Ingredient.fromItems(Itms.ENERGIZED_STEEL)
    ),

    ARMOR_BLAZING("energized_armor_blazing", -1, new int[] { 2, 7, 6, 2}, 12,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> Ingredient.fromItems(Itms.ENERGIZED_STEEL)
    ),
    ARMOR_NIOTIC("energized_armor_niotic", -1, new int[] { 3, 7, 6, 2 }, 12,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> Ingredient.fromItems(Itms.ENERGIZED_STEEL)
    ),

    ARMOR_SPIRITED("energized_armor_spirited", -1, new int[] { 3, 8, 6, 3}, 12,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> Ingredient.fromItems(Itms.ENERGIZED_STEEL)
    ),
    ARMOR_NITRO("energized_armor_nitro", -1, new int[] { 3, 8, 6, 3}, 12,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> Ingredient.fromItems(Itms.ENERGIZED_STEEL)
    ),
    ARMOR_OVERCHARGED("energized_armor_overcharged", -1, new int[] { 4, 9, 7, 4}, 12,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> Ingredient.fromItems(Itms.ENERGIZED_STEEL)
    );


    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairMaterial;

    ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability,
                             SoundEvent soundEvent, float toughness, float knockbackResistance,
                             Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }


    public int getDurability(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return Powah.MOD_ID + ":" + this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }


}