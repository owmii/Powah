package owmii.powah.lib.logistics.inventory;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractContainer extends AbstractContainerMenu {
    public final Player player;
    public final Level world;

    public AbstractContainer(@Nullable MenuType<?> type, int id, Inventory inventory, FriendlyByteBuf buffer) {
        this(type, id, inventory);
    }

    public AbstractContainer(@Nullable MenuType<?> type, int id, Inventory inventory) {
        super(type, id);
        this.player = inventory.player;
        this.world = this.player.level;
        init(inventory);
    }

    protected void init(Inventory inventory) {
    }

    protected void addPlayerInventory(Inventory playerInventory, int x, int y, int yDif) {
        for (int l = 0; l < 3; ++l) {
            for (int k = 0; k < 9; ++k) {
                addSlot(new Slot(playerInventory, k + l * 9 + 9, x + k * 18, l * 18 + y));
            }
        }
        for (int i1 = 0; i1 < 9; ++i1) {
            addSlot(new Slot(playerInventory, i1, x + i1 * 18, y + 54 + yDif));
        }
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

    public static final AbstractContainerMenu DUMMY = new AbstractContainerMenu(null, 0) {
        @Override
        public boolean stillValid(Player playerIn) {
            return true;
        }

        @Override
        public ItemStack quickMoveStack(Player player, int index) {
            return ItemStack.EMPTY;
        }
    };
}
