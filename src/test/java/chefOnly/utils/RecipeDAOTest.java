package chefOnly.utils;

import chefOnly.InitRecipes;
import chefOnly.model.Recipe;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

class RecipeDAOTest {
    List<Recipe>  recipeList;
    Recipe recipe;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        recipeList = RecipeDAO.findRecipe("%Slow%");
        recipe = recipeList.get(0);
        System.out.println(recipe);
    }

    @Test
    void testAddRecipe() throws SQLException {
        RecipeDAO.addRecipe(InitRecipes.insertRecipe_01());
    }

    @Test
    void testDeleteRecipe() {
        RecipeDAO.deleteRecipe(9);
    }

    @Test
    void recipeList() {
        List<Recipe> recipes = RecipeDAO.recipeList();
        for (Recipe recipe : recipes){
            System.out.println(recipe);
        }
    }

    @Test
    void testFindRecipe() {
        ObservableList<Recipe> recipes = RecipeDAO.findRecipe("Spi%");
        for (Recipe recipe : recipes){
            System.out.println(recipe);
        }
    }

    @Test
    void testUpdateRecipe() throws SQLException {
        RecipeDAO.updateRecipe(recipe);
    }
}