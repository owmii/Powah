package owmii.powah.handler.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import owmii.powah.Powah;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Powah.MOD_ID)
public class ArmorEquip {
    private static HashMap<String, Consumer<ServerPlayerEntity>> handlers = new HashMap<>();
    static {
        handlers.put("overcharged", player -> {player.abilities.allowFlying = true;});
    }

    @SubscribeEvent
    public static void onArmorEquip(LivingEquipmentChangeEvent e) {
        if (!(e.getEntity() instanceof ServerPlayerEntity)) return;
        ServerPlayerEntity player = (ServerPlayerEntity)e.getEntity();

        ArrayList<String> tiers = new ArrayList<>(handlers.keySet());
        List<Consumer<ServerPlayerEntity>> effects = handlers.values().stream().collect(Collectors.toList());

        for (int i = 0; i < tiers.size(); i++) {
            boolean wearingArmor = true;
            for (ItemStack a : player.getArmorInventoryList()) {
                if (a.isEmpty() || !a.getItem().getTags().contains(new ResourceLocation(Powah.MOD_ID, "armor/"+tiers.get(i)))) wearingArmor = false; // TODO: add tags for all armor tiers using providers
            }
            if (!wearingArmor) continue;

            for(int j = 0; j <= i; j++) effects.get(j).accept(player);
        }

        player.sendPlayerAbilities();
    }
}
