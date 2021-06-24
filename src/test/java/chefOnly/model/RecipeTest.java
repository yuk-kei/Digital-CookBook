package chefOnly.model;

import java.util.ArrayList;
import java.util.List;

class RecipeTest {

    private static Recipe recipe;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        recipe = new Recipe("Sugar", "sweet", 10,20, 30);
        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
        Ingredient ingredient1 = new Ingredient("onion", 20, "gram", "small thing");
        Ingredient ingredient2 = new Ingredient("butter", 1, "piece", "yellow");
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        recipe.setIngredients((ArrayList<Ingredient>) ingredientList);

        List<String> preparationStep = new ArrayList<String>();
        String step1 = "set aside";
        String step2 = "and go";
        preparationStep.add(step1);
        preparationStep.add(step2);
        recipe.setPreparationStep((ArrayList<String>) preparationStep);
    }

    @org.junit.jupiter.api.Test

    void testToString() {
        System.out.println(recipe);
    }
}