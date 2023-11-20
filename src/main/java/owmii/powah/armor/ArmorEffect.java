package owmii.powah.armor;

import java.util.function.Consumer;

import net.minecraft.entity.player.ServerPlayerEntity;

public class ArmorEffect {
    public Consumer<ServerPlayerEntity> equipEffect;
    public Consumer<ServerPlayerEntity> unequipEffect;

    public ArmorEffect(Consumer<ServerPlayerEntity> equipEffect, Consumer<ServerPlayerEntity> unequipEffect) {
        this.equipEffect = equipEffect;
        this.unequipEffect = unequipEffect;
    }
    public ArmorEffect() {
        this.equipEffect = player -> {};
        this.unequipEffect = player -> {};
    }
}
