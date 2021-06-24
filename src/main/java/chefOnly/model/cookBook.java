package chefOnly.model;

import chefOnly.utils.RecipeDAO;
import javafx.beans.Observable;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class cookBook {

    private  ObservableList<Recipe> allRecipes;
    private List<Recipe> recipes = new ArrayList<Recipe>();

    public void addRecipe(Recipe r) throws SQLException {
        recipes.add(r);
        RecipeDAO.addRecipe(r);
    }

    /**
     * deleting recipe
     * @param r the recipe to be deleted
     */
    public void deleteRecipe(Recipe r){
        RecipeDAO.deleteRecipe(r.getRecipeID());
    }

    public List<Recipe> getRecipeList(){
        return recipes;
    }

    public void setRecipeList(List<Recipe> list){
        recipes.addAll(list);
    }

    public ObservableList<Recipe> findAllRecipes() {
        return RecipeDAO.getRecipeList();
    }

    public ObservableList<Recipe> findBook(String type, String value) {
        return RecipeDAO.findRecipe(type, value);
    }
}
