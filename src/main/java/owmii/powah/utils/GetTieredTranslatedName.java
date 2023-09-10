package owmii.powah.utils;

import java.util.Arrays;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;
import owmii.lib.registry.IVariant;
import owmii.powah.Powah;

public class GetTieredTranslatedName {
    @SuppressWarnings("rawtypes")
    public static IFormattableTextComponent getTieredTranslatedName(ForgeRegistryEntry entry, IVariant var) {
        String[] name = entry.getRegistryName().getPath().split("_");
        return new TranslationTextComponent("type.powah."+String.join("_", Arrays.copyOf(name, name.length-1))).appendString(" (").append(new TranslationTextComponent("tier."+Powah.MOD_ID+"."+var.getName()).appendString(")"));
    }
}
