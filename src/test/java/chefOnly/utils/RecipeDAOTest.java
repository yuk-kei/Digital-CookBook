package chefOnly.utils;

import chefOnly.model.Recipe;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecipeDAOTest {
    List<Recipe>  recipes;
    Recipe recipe;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        recipe = new Recipe("Baby Yoda","sweet",100,20,200);
    }

    @Test
    void testFindRecipe() {
        recipes = RecipeDAO.findRecipe("pumpkin porridge");
        for (Recipe recipe : recipes){
            System.out.println(recipe);
        }
        assertEquals("sweet", recipes.get(0).getFlavour());
    }

    @Test
    void testAddRecipe() throws SQLException {
        RecipeDAO.addRecipe(recipe);
        recipe = RecipeDAO.findRecipe(recipe.getRecipeName()).get(0);
        assertEquals("Baby Yoda", recipe.getRecipeName());
    }

    @Test
    void testUpdateRecipe() throws SQLException {
        recipe = RecipeDAO.findRecipe(recipe.getRecipeName()).get(0);
        recipe.setRecipeName("Master Yoda");
        RecipeDAO.updateRecipe(recipe);
        assertEquals("Master Yoda", RecipeDAO.findRecipe(recipe.getRecipeName()).get(0).getRecipeName());
    }

    @Test
    void testDeleteRecipe() {
        RecipeDAO.deleteRecipe(66);
        assertTrue(RecipeDAO.findRecipe(recipe.getRecipeName()).isEmpty());
    }
}