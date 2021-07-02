package chefOnly.utils;

import chefOnly.model.Ingredient;
import chefOnly.model.Recipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Methods related to Data base layer.
 *
 */
public class RecipeDAO {

    /**
     * Insert the recipe into the database
     *
     * @param recipe the recipe
     * @throws SQLException the sql exception
     */
    public static void addRecipe(Recipe recipe) throws SQLException {

        String sql = "insert into recipe values(null,?,?,?,?,?,?)";
        try (Connection c = ConnectionUtil.getConnection()) {
            assert c != null; // Suggested to do this by IDEA but don't know why.
            try (PreparedStatement preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                setRecipePreparedStatement(recipe, preparedStatement);
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    recipe.setRecipeID(id);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ConnectionUtil.getConnection() != null) {
                try {
                    ConnectionUtil.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        addIngredient(recipe);
        addPrepStep(recipe);
    }

    /**
     * Setting the preparedStatement for recipe. (Auto suggested to be extracted by idea)
     * @param recipe the recipe
     * @param preparedStatement the preparation steps
     * @throws SQLException the sql exception
     */
    private static void setRecipePreparedStatement(Recipe recipe, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, recipe.getRecipeName());
        preparedStatement.setInt(2, recipe.getServeNumber());
        preparedStatement.setInt(3, recipe.getPrepTime());
        preparedStatement.setInt(4, recipe.getCookTime());
        preparedStatement.setString(5, recipe.getImagePath());
        preparedStatement.setString(6, recipe.getFlavour());
    }

    /**
     * Inset ingredients to the database
     *
     * @param recipe the recipe
     * @throws SQLException the sql exception
     */
    private static void addIngredient(Recipe recipe) throws SQLException {
        String sql = "insert into ingredient values(?,?,?,?,?)";
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            try (Connection c = ConnectionUtil.getConnection()) {
                assert c != null; // Suggested to do this by IDEA but don't know why.
                try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {

                    preparedStatement.setInt(1, recipe.getRecipeID());
                    preparedStatement.setString(2, recipe.getIngredients().get(i).getIngredientName());
                    preparedStatement.setDouble(3, recipe.getIngredients().get(i).getQuantity());
                    preparedStatement.setString(4, recipe.getIngredients().get(i).getUnit());
                    preparedStatement.setString(5, recipe.getIngredients().get(i).getDescription());
                    preparedStatement.execute();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (ConnectionUtil.getConnection() != null) {
                        try {
                            ConnectionUtil.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /*
     * Insert the preparation steps to the INGREDIENT table
     */
    private static void addPrepStep(Recipe recipe) throws SQLException {

        String sql = "insert into preparationstep values(?,?,?)";

        for (int i = 0; i < recipe.getPreparationStep().size(); i++) {
            try (Connection c = ConnectionUtil.getConnection()) {
                assert c != null;
                try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {

                    preparedStatement.setInt(1, recipe.getRecipeID());
                    preparedStatement.setInt(2, i);
                    preparedStatement.setString(3, recipe.getPreparationStep().get(i));
                    preparedStatement.execute();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (ConnectionUtil.getConnection() != null) {
                        try {
                            ConnectionUtil.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * Delete recipe from the database.
     *
     * @param recipe_id the recipe id
     */
    public static void deleteRecipe(Integer recipe_id) {
        try (Connection c = ConnectionUtil.getConnection()) {
            assert c != null;
            try (Statement statement = c.createStatement()) {

                String sql = "delete from recipe where recipe_id = " + recipe_id;
                statement.execute(sql);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ConnectionUtil.getConnection() != null) {
                try {
                    ConnectionUtil.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Update recipe.
     *
     * @param recipe the recipe
     * @throws SQLException the sql exception
     */
    public static void updateRecipe(Recipe recipe) throws SQLException {

        String sql = "update recipe set name = ?, serveamount = ? , preparationtime = ?, cookingTime = ?, imagpath = ?, flavour = ? where recipe_id = ?";
        try (Connection c = ConnectionUtil.getConnection()) {
            assert c != null;
            try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {

                setRecipePreparedStatement(recipe, preparedStatement);
                preparedStatement.setInt(7, recipe.getRecipeID());
                preparedStatement.execute();

            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            if (ConnectionUtil.getConnection() != null) {
                try {
                    ConnectionUtil.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        updateIngredient(recipe);
        updatePrepStep(recipe);
    }

    /*
     * Update preparation steps to the PREPARATION table
     */
    private static void updatePrepStep(Recipe recipe) throws SQLException {

        String sql = "update preparationstep set  description = ? where  step = ? and recipe_id = ?";

        for (int i = 0; i < recipe.getPreparationStep().size(); i++) {
            try (Connection c = ConnectionUtil.getConnection()) {
                assert c != null;
                try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {

                    preparedStatement.setString(1, recipe.getPreparationStep().get(i));
                    preparedStatement.setInt(2, i);
                    preparedStatement.setInt(3, recipe.getRecipeID());
                    preparedStatement.execute();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (ConnectionUtil.getConnection() != null) {
                        try {
                            ConnectionUtil.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /*
     * Update ingredients to the INGREDIENT table
     */
    private static void updateIngredient(Recipe recipe) throws SQLException {
        String sql = "update ingredient set  quantity = ? , unit  = ?, description = ? where name = ? and recipe_id = ?";
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            try (Connection c = ConnectionUtil.getConnection()) {
                assert c != null;
                try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {

                    preparedStatement.setDouble(1, recipe.getIngredients().get(i).getQuantity());
                    preparedStatement.setString(2, recipe.getIngredients().get(i).getUnit());
                    preparedStatement.setString(3, recipe.getIngredients().get(i).getDescription());
                    preparedStatement.setString(4, recipe.getIngredients().get(i).getIngredientName());
                    preparedStatement.setInt(5, recipe.getRecipeID());
                    preparedStatement.execute();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (ConnectionUtil.getConnection() != null) {
                        try {
                            ConnectionUtil.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * Find recipe by name from the database and return a list
     *
     * @return the result list of recipes
     */
    public static List<Recipe> findRecipe(String name) {
        List<Recipe> recipes = new ArrayList<>();

        String recipeSQL = "select * from recipe where name like ? order by recipe_id desc limit ?,? ";
        String ingredientSQL = "select * from ingredient where recipe_id = ?";
        String instructionSQL = "select * from preparationStep where recipe_id = ?";

        try (Connection c = ConnectionUtil.getConnection()) {
            assert c != null;
            try (PreparedStatement preparedStatement = c.prepareStatement(recipeSQL)) {

                preparedStatement.setString(1, "%" + name + "%");
                preparedStatement.setInt(2, 0);
                preparedStatement.setInt(3, Short.MAX_VALUE);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Recipe recipe = new Recipe();
                    recipe.setRecipeID(resultSet.getInt(1));
                    recipe.setRecipeName(resultSet.getString("name"));
                    recipe.setServeNumber(resultSet.getInt(3));
                    recipe.setPrepTime(resultSet.getInt(4));
                    recipe.setCookTime(resultSet.getInt(5));
                    recipe.setImagePath(resultSet.getString(6));
                    recipe.setFlavour(resultSet.getString("flavour"));

                    recipe.setIngredients(loadIngredient(ingredientSQL, recipe.getRecipeID(), c));
                    recipe.setPreparationStep(loadPrepStep(instructionSQL, recipe.getRecipeID(), c));

                    recipes.add(recipe);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ConnectionUtil.getConnection() != null) {
                try {
                    ConnectionUtil.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return recipes;
    }

    /*
     * Select the preparation steps corresponding to the recipe.
     */
    private static ArrayList<String> loadPrepStep(String sql, int id, Connection c) {
        ArrayList<String> preparations = new ArrayList<>();

        try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                preparations.add(resultSet.getString(3));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparations;
    }

    /*
     * Select the ingredients corresponding to the recipe.
     */
    private static ArrayList<Ingredient> loadIngredient(String sql, int id, Connection c) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient(resultSet.getString(2), resultSet.getDouble(3), resultSet.getString(4), resultSet.getString(5));

                ingredients.add(ingredient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

}
