package owmii.powah.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;
import owmii.lib.config.IEnergyConfig;
import owmii.lib.item.EnergyItem;
import owmii.lib.logistics.energy.Energy;
import owmii.powah.api.energy.endernetwork.IEnderExtender;
import owmii.powah.block.Tier;
import owmii.powah.config.Configs;
import owmii.powah.config.item.BatteryConfig;

public class BatteryItem extends EnergyItem<Tier, BatteryConfig, BatteryItem> implements IEnderExtender {
    boolean hasGlint = false;
    boolean isCreative = false;
    public BatteryItem(Item.Properties properties, Tier variant) {
        super(properties, variant);

        isCreative = variant==Tier.CREATIVE;
        if (isCreative) hasGlint = true;
    }

    @Override
    public IEnergyConfig<Tier> getConfig() {
        return Configs.BATTERY;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        Energy.ifPresent(stack, storage -> {
            if (isCreative) Energy.receive(stack, Energy.MAX, false);

            if (entity instanceof PlayerEntity && isCharging(stack) && storage instanceof Energy)
                ((Energy)storage).chargeInventory((PlayerEntity) entity, stack1 -> !(stack1.getItem() instanceof BatteryItem));
        });
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            switchCharging(stack);
            return ActionResult.resultSuccess(stack);
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!isCreative) super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        IEnergyStorage energy = Energy.get(stack).orElse(Energy.Item.create(0));
        return !isCreative && energy.getEnergyStored() < energy.getMaxEnergyStored();
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        IEnergyStorage energy = Energy.get(stack).orElse(Energy.Item.create(0));
        return 1.0F - ((double) energy.getEnergyStored() / energy.getMaxEnergyStored());
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isCharging(stack) || hasGlint;
    }

    private void switchCharging(ItemStack stack) {
        setCharging(stack, !isCharging(stack));
    }

    private boolean isCharging(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("charging");
    }

    private void setCharging(ItemStack stack, boolean charging) {
        stack.getOrCreateTag().putBoolean("charging", charging);
    }

    @Override
    public long getExtendedCapacity(ItemStack stack) {
        return getConfig().getCapacity(getVariant());
    }

    @Override
    public long getExtendedEnergy(ItemStack stack) {
        return Energy.getStored(stack);
    }
}
