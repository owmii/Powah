package owmii.powah.item;

import net.minecraft.item.ItemStack;
import owmii.lib.item.ItemBase;
import owmii.powah.block.Tier;

public class CapacitorItem extends ItemBase {
    boolean hasGlint = false;
    public CapacitorItem(Properties properties) {
        super(properties);
    }
    public CapacitorItem(Properties properties, Tier variant) {
        super(properties);
        if (variant==Tier.CREATIVE) hasGlint = true;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return hasGlint;
    }
}
