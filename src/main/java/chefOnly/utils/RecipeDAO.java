package chefOnly.utils;

import chefOnly.model.Ingredient;
import chefOnly.model.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
            assert c != null;
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                setRecipePreparedStatement(recipe, ps);
                ps.execute();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
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
     * Setting the preparedStatement for recipe.
     */
    private static void setRecipePreparedStatement(Recipe recipe, PreparedStatement ps) throws SQLException {
        ps.setString(1, recipe.getRecipeName());
        ps.setInt(2, recipe.getServeNumber());
        ps.setInt(3, recipe.getPrepTime());
        ps.setInt(4, recipe.getCookTime());
        ps.setString(5, recipe.getImagePath());
        ps.setString(6, recipe.getFlavour());
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
                assert c != null;
                try (PreparedStatement ps = c.prepareStatement(sql)) {

                    ps.setInt(1, recipe.getRecipeID());
                    ps.setString(2, recipe.getIngredients().get(i).getIngredientName());
                    ps.setDouble(3, recipe.getIngredients().get(i).getQuantity());
                    ps.setString(4, recipe.getIngredients().get(i).getUnit());
                    ps.setString(5, recipe.getIngredients().get(i).getDescription());
                    ps.execute();

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
                try (PreparedStatement ps = c.prepareStatement(sql)) {

                    ps.setInt(1, recipe.getRecipeID());
                    ps.setInt(2, i);
                    ps.setString(3, recipe.getPreparationStep().get(i));
                    ps.execute();

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
            try (Statement s = c.createStatement()) {

                String sql = "delete from recipe where recipe_id = " + recipe_id;
                s.execute(sql);

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
            try (PreparedStatement ps = c.prepareStatement(sql)) {

                setRecipePreparedStatement(recipe, ps);
                ps.setInt(7, recipe.getRecipeID());
                ps.execute();

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
                try (PreparedStatement ps = c.prepareStatement(sql)) {

                    ps.setString(1, recipe.getPreparationStep().get(i));
                    ps.setInt(2, i);
                    ps.setInt(3, recipe.getRecipeID());
                    ps.execute();

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
                try (PreparedStatement ps = c.prepareStatement(sql)) {

                    ps.setDouble(1, recipe.getIngredients().get(i).getQuantity());
                    ps.setString(2, recipe.getIngredients().get(i).getUnit());
                    ps.setString(3, recipe.getIngredients().get(i).getDescription());
                    ps.setString(4, recipe.getIngredients().get(i).getIngredientName());
                    ps.setInt(5, recipe.getRecipeID());
                    ps.execute();

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
     * Get all recipes from the database to a list
     *
     * @return the list of all recipes
     */
    public static List<Recipe> recipeList() {
        List<Recipe> recipes = new ArrayList<>();

        String sql = "select * from recipe order by recipe_id desc limit ?,? ";
        String sql2 = "select * from ingredient where recipe_id = ?";
        String sql3 = "select * from preparationstep where recipe_id = ?";

        try (Connection c = ConnectionUtil.getConnection()) {
            assert c != null;
            try (PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setInt(1, 0);
                ps.setInt(2, Short.MAX_VALUE);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Recipe r = new Recipe();
                    r.setRecipeID(rs.getInt(1));
                    r.setRecipeName(rs.getString("name"));
                    r.setServeNumber(rs.getInt(3));
                    r.setPrepTime(rs.getInt(4));
                    r.setCookTime(rs.getInt(5));
                    r.setImagePath(rs.getString(6));
                    r.setFlavour(rs.getString("flavour"));

                    r.setIngredients(loadIngredient(sql2, r.getRecipeID(), c));
                    r.setPreparationStep(loadPrepStep(sql3, r.getRecipeID(), c));

                    recipes.add(r);
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
        ArrayList<String> prep = new ArrayList<>();

        try (PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                prep.add(rs.getString(3));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prep;
    }

    /*
     * Select the ingredients corresponding to the recipe.
     */
    private static ArrayList<Ingredient> loadIngredient(String sql, int id, Connection c) {
        ArrayList<Ingredient> ingres = new ArrayList<>();

        try (PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ingredient ingredient = new Ingredient(rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5));

                ingres.add(ingredient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingres;
    }

    /**
     * Find recipe by name to a observable list.
     *
     * @param value the value
     * @return the observable list
     */
    public static ObservableList<Recipe> findRecipe(String value) {
        ObservableList<Recipe> recipes = FXCollections.observableArrayList();
        String sql = "select * from recipe where name like ?";
        String sql2 = "select * from ingredient where recipe_id = ?";
        String sql3 = "select * from preparationstep where recipe_id = ?";

        try (Connection c = ConnectionUtil.getConnection()) {
            assert c != null;
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, value);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Recipe r = new Recipe();
                    r.setRecipeID(rs.getInt(1));
                    r.setRecipeName(rs.getString("name"));
                    r.setServeNumber(rs.getInt(3));
                    r.setPrepTime(rs.getInt(4));
                    r.setCookTime(rs.getInt("cookingTime"));
                    r.setImagePath(rs.getString(6));
                    r.setFlavour(rs.getString(7));

                    r.setIngredients(loadIngredient(sql2, r.getRecipeID(), c));
                    r.setPreparationStep(loadPrepStep(sql3, r.getRecipeID(), c));

                    recipes.add(r);
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
}
