package owmii.powah.client.compat.kjs;

import com.google.gson.JsonArray;

import dev.latvian.kubejs.recipe.RecipeJS;
import dev.latvian.kubejs.util.ListJS;

public class EnergizingJS extends RecipeJS {
    Long energy;

    @Override
    public void create(ListJS args) {
        outputItems.add(parseResultItem(args.get(0)));
        inputItems.addAll(parseIngredientItemList(args.get(1)));
        
        if (args.getLength()>2 && args.get(2) instanceof Double) energy = Math.round((Double)args.get(2));
        else energy = 100L;
    }

    @Override
    public void deserialize() {
        inputItems.addAll(parseIngredientItemList(json.get("ingredients")));
        outputItems.add(parseResultItem(json.get("result")));
        energy = json.get("energy").getAsLong();
    }
    @Override
    public void serialize() {
        JsonArray itemStackArray = new JsonArray();
        inputItems.forEach(i -> {itemStackArray.add(i.toJson());});

        json.add("ingredients", itemStackArray);
        json.add("result", outputItems.get(0).toJson());
        json.addProperty("energy", energy);
    }
}
