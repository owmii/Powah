package owmii.powah.config;

import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import owmii.powah.Powah;

public class StarterTierRecipeConfigChecker implements ICondition {
    public static final ResourceLocation ID = new ResourceLocation(Powah.MOD_ID, "starter");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test() {
        return Configs.GENERAL.enable_starter.get();
    }

    public static class Serializer implements IConditionSerializer<StarterTierRecipeConfigChecker> {
        @Override
        public void write(JsonObject json, StarterTierRecipeConfigChecker value) {}

        @Override
        public StarterTierRecipeConfigChecker read(JsonObject json) {
            return new StarterTierRecipeConfigChecker();
        }

        @Override
        public ResourceLocation getID() {
            return ID;
        }
        
    }
    
}
