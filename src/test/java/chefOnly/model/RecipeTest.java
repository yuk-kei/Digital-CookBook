package chefOnly.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RecipeTest {

    private static Recipe recipe;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        recipe = new Recipe("slow cooked beef short ribs", "salty", 2,5, 180);
        ArrayList<Ingredient> ingredientList = new ArrayList<>();

        ingredientList.add(new Ingredient("Garlic", 1, "", ""));
        ingredientList.add(new Ingredient("Beef short ribs", 1, "kg", ""));
        ingredientList.add(new Ingredient("Beef stock", 300, "ml", ""));

        recipe.setIngredients(ingredientList);

        ArrayList<String> preparationStep = new ArrayList<>();

        preparationStep.add("Wash the beef and then slice it alongside the bone.");
        preparationStep.add("Get your roasting tray on the heat and make sure it’s hot. Then pour olive oil into it");
        preparationStep.add("Season the beef with black pepper salt.");
        preparationStep.add("Put the beef into your tray. Make sure that you give the beef a really nice sear and brown it off. This step is only supposed to color the stark！");
        preparationStep.add(" Cut the garlic half slide and then put it into the roasting tray.");
        preparationStep.add("Stir in the tomato puree");

        recipe.setPreparationStep(preparationStep);
    }

    @Test
    void calculateQuantity() {
        recipe.calculateQuantity(4);
        System.out.println(recipe.getIngredients().get(0).getQuantity());
        assertEquals(2, recipe.getIngredients().get(0).getQuantity());
        assertEquals(2, recipe.getIngredients().get(1).getQuantity());
        assertEquals(600, recipe.getIngredients().get(2).getQuantity());
    }

    @Test
    void getFormattedIngredients() {
        int ingredientNumber = 0;
        for (Ingredient ingredient : recipe.getIngredients()){
            System.out.println(ingredient);
            ingredientNumber++;
        }
        assertEquals(ingredientNumber,3);
    }

    @Test
    void getFormattedPreparationStep() {
        int preparationStep = 0;
        for (String preparation : recipe.getPreparationStep()){
            System.out.println(preparation);
            preparationStep++;
        }
        assertEquals(preparationStep,6);
    }

    @Test
    void testToString() {
        System.out.println(recipe);
    }
}