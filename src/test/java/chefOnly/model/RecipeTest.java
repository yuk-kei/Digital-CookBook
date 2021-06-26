package chefOnly.model;

import java.util.ArrayList;

class RecipeTest {

    private static Recipe recipe;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        recipe = new Recipe("Sugar", "sweet", 10,20, 30);
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        Ingredient ingredient1 = new Ingredient("onion", 20, "gram", "small thing");
        Ingredient ingredient2 = new Ingredient("butter", 1, "piece", "yellow");
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        recipe.setIngredients(ingredientList);

        ArrayList<String> preparationStep = new ArrayList<>();
        String step1 = "set aside";
        String step2 = "and go";
        preparationStep.add(step1);
        preparationStep.add(step2);
        recipe.setPreparationStep(preparationStep);
    }

    @org.junit.jupiter.api.Test

    void testToString() {
        System.out.println(recipe);
    }
}