package owmii.powah.inventory;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import owmii.lib.logistics.inventory.AbstractEnergyContainer;
import owmii.lib.logistics.inventory.slot.SlotBase;
import owmii.powah.block.furnator.FurnatorTile;

public class FurnatorContainer extends AbstractEnergyContainer<FurnatorTile> {
    public FurnatorContainer(int id, PlayerInventory inventory, PacketBuffer buffer) {
        super(Containers.FURNATOR, id, inventory, buffer);
    }

    public FurnatorContainer(int id, PlayerInventory inventory, FurnatorTile te) {
        super(Containers.FURNATOR, id, inventory, te);
    }

    public static FurnatorContainer create(int id, PlayerInventory inventory, PacketBuffer buffer) {
        return new FurnatorContainer(id, inventory, buffer);
    }

    @Override
    protected void init(PlayerInventory inventory, FurnatorTile te) {
        super.init(inventory, te);
        addSlot(new SlotBase(te.getInventory(), 0, 4, 54));
        addSlot(new SlotBase(te.getInventory(), 1, 87, 18));
        addPlayerInventory(inventory, 8, 84, 4);
    }
}
