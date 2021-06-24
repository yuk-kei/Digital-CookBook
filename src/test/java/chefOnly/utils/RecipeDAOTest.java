package chefOnly.utils;

import chefOnly.model.InitRecipes;
import chefOnly.model.Recipe;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

class RecipeDAOTest {


    @org.junit.jupiter.api.BeforeEach
    void setUp() {

    }

    @Test
    void addRecipe() throws SQLException, IOException {
        RecipeDAO.addRecipe(InitRecipes.insertRecipe_01());
    }

    @Test
    void deleteRecipe() {
        RecipeDAO.deleteRecipe(9);
    }

    @Test
    void updateRecipe() {

    }

    @Test
    void recipeList() {
        List<Recipe> recipes = RecipeDAO.recipeList();
        for (Recipe recipe : recipes){
            System.out.println(recipe);
        }
    }

    @Test
    void findRecipe() {
    }

    @Test
    void testFindRecipe() {
        ObservableList<Recipe> recipes = RecipeDAO.findRecipe("Name","Spi%");
        for (Recipe recipe : recipes){
            System.out.println(recipe);
        }
    }
}