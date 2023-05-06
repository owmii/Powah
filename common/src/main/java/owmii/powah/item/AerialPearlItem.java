package owmii.powah.item;

import dev.architectury.hooks.item.ItemStackHooks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import owmii.powah.Powah;
import owmii.powah.lib.item.ItemBase;

public class AerialPearlItem extends ItemBase {
    public AerialPearlItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        if (Powah.config().general.player_aerial_pearl) {
            if (this == Itms.AERIAL_PEARL.get()) {
                if (target.getClass() == Zombie.class
                        || target.getClass() == ZombieVillager.class
                        || target.getClass() == Husk.class) {
                    if (!playerIn.level.isClientSide) {
                        ItemStack stack1 = playerIn.getItemInHand(hand);
                        ItemStackHooks.giveItem((ServerPlayer) playerIn, new ItemStack(Itms.PLAYER_AERIAL_PEARL.get()));
                        target.playSound(SoundEvents.ZOMBIE_DEATH, 0.5F, 1.0F);
                        target.remove(Entity.RemovalReason.KILLED);
                        if (!playerIn.isCreative()) {
                            stack1.shrink(1);
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.interactLivingEntity(stack, playerIn, target, hand);
    }
}
