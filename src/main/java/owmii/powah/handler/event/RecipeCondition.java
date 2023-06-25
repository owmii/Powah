package owmii.powah.handler.event;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import owmii.powah.Powah;
import owmii.powah.config.StarterTierRecipeConfigChecker;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Powah.MOD_ID)
public class RecipeCondition {
    @SubscribeEvent
    public static void registerRecipeCondition(RegistryEvent<IRecipeSerializer<?>> e) {
        CraftingHelper.register(new StarterTierRecipeConfigChecker.Serializer());
    }
}
