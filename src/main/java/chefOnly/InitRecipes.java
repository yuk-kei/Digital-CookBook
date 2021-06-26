package chefOnly;

import chefOnly.model.Ingredient;
import chefOnly.model.Recipe;

/**
 * Loading all the recipes.
 *
 */
public class InitRecipes {
    public static Recipe insertRecipe_01()  {
        Recipe recipe = new Recipe("Slow cooked Beef Short Ribs", "salty",2, 5, 180);
        recipe.addIngredient(new Ingredient("beef short ribs", 1.0, "kg","  "));
        recipe.addIngredient(new Ingredient("black pepper salt", 1, "tablespoon"," "));
        recipe.addIngredient(new Ingredient("olive oil", 3, "tablespoons"," "));
        recipe.addIngredient(new Ingredient("Garlic", 1, " ","important flavor"));
        recipe.addIngredient(new Ingredient("red wine", 300, "ml","cheap is enough"));
        recipe.addIngredient(new Ingredient("beef stock", 300, "ml","main"));

        recipe.addPreparationStep("Wash the beef and then slice it alongside the bone.");
        recipe.addPreparationStep("Get your roasting tray on the heat and make sure it’s hot. Then pour olive oil into it");
        recipe.addPreparationStep("Season the beef with black pepper salt.");
        recipe.addPreparationStep("Put the beef into your tray. Make sure that you give the beef a really nice sear and brown it off. This step is only supposed to color the stark.");
        recipe.addPreparationStep("Cut the garlic half slide and then put it into the roasting tray.");
        recipe.addPreparationStep("Stir in the tomato puree");

        return recipe;
    }
}
