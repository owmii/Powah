package owmii.powah.client.compat.jei;

import com.google.common.collect.Lists;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import owmii.lib.util.Recipe;
import owmii.powah.Powah;
import owmii.powah.block.Blcks;
import owmii.powah.client.compat.jei.energizing.EnergizingCategory;
import owmii.powah.client.compat.jei.magmator.MagmatorCategory;
import owmii.powah.config.Configs;
import owmii.powah.item.Itms;
import owmii.powah.recipe.Recipes;

@JeiPlugin
public class PowahJEIPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new MagmatorCategory(helper));
        registration.addRecipeCategories(new CoolantCategory(helper));
        registration.addRecipeCategories(new SolidCoolantCategory(helper));
        registration.addRecipeCategories(new HeatSourceCategory(helper));
        registration.addRecipeCategories(new EnergizingCategory(helper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Blcks.ENERGIZING_ORB), EnergizingCategory.ID);
        Blcks.ENERGIZING_ROD.getAll().forEach(block -> registration.addRecipeCatalyst(new ItemStack(block), EnergizingCategory.ID));
        Blcks.MAGMATOR.getAll().forEach(block -> registration.addRecipeCatalyst(new ItemStack(block), MagmatorCategory.ID));
        Blcks.THERMO_GENERATOR.getAll().forEach(block -> {
            registration.addRecipeCatalyst(new ItemStack(block), HeatSourceCategory.ID);
            registration.addRecipeCatalyst(new ItemStack(block), CoolantCategory.ID);
        });
        Blcks.REACTOR.getAll().forEach(block -> {
            registration.addRecipeCatalyst(new ItemStack(block), SolidCoolantCategory.ID);
            registration.addRecipeCatalyst(new ItemStack(block), CoolantCategory.ID);
        });
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Minecraft instance = Minecraft.getInstance();
        registration.addRecipes(Recipe.getAll(instance.world, Recipes.ENERGIZING), EnergizingCategory.ID);
        registration.addRecipes(MagmatorCategory.Maker.getBucketRecipes(registration.getIngredientManager()), MagmatorCategory.ID);
        registration.addRecipes(CoolantCategory.Maker.getBucketRecipes(registration.getIngredientManager()), CoolantCategory.ID);
        registration.addRecipes(SolidCoolantCategory.Maker.getBucketRecipes(registration.getIngredientManager()), SolidCoolantCategory.ID);
        registration.addRecipes(HeatSourceCategory.Maker.getBucketRecipes(registration.getIngredientManager()), HeatSourceCategory.ID);

        if (Configs.GENERAL.player_aerial_pearl.get())
            registration.addIngredientInfo(new ItemStack(Itms.PLAYER_AERIAL_PEARL), VanillaTypes.ITEM, new TranslationTextComponent("jei.powah.player_aerial_pearl"));
        if (Configs.GENERAL.binding_card_dim.get())
            registration.addIngredientInfo(new ItemStack(Itms.BINDING_CARD_DIM), VanillaTypes.ITEM, new TranslationTextComponent("jei.powah.binding_card_dim"));
        if (Configs.GENERAL.lens_of_ender.get())
            registration.addIngredientInfo(new ItemStack(Itms.LENS_OF_ENDER), VanillaTypes.ITEM, new TranslationTextComponent("jei.powah.lens_of_ender"));
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime r) {
        if (Configs.GENERAL.enable_starter.get()) return;

        Itms.REG.forEach(i -> {
            if (i.getRegistryName().getNamespace().equals(Powah.MOD_ID) && i.getRegistryName().getPath().endsWith("_starter")) r.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM, Lists.newArrayList(i.getDefaultInstance()));
        });
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Powah.MOD_ID, "main");
    }
}
