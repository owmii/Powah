package owmii.powah.client.compat.kjs;

import dev.latvian.kubejs.recipe.RegisterRecipeHandlersEvent;
import net.minecraft.util.ResourceLocation;
import owmii.powah.Powah;

public class KJSPlugin extends dev.latvian.kubejs.KubeJSPlugin {
    @Override
    public void addRecipes(RegisterRecipeHandlersEvent event) {
        event.register(new ResourceLocation(Powah.MOD_ID, "energizing"), EnergizingJS::new);
    }
}
