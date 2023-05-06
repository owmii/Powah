package owmii.powah.lib.client.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface IHudItem {
    boolean renderHud(Level world, BlockPos pos, Player player, InteractionHand hand, Direction side, Vec3 hit);
}
