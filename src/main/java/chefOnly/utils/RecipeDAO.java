package chefOnly.utils;

import chefOnly.model.Ingredient;
import chefOnly.model.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO {

    private Connection connection = null;
    private Statement statement = null;


    /**
     * Insert the recipe into the database
     *
     * @param recipe
     * @throws SQLException
     */
    public static void addRecipe(Recipe recipe) throws SQLException {

        String sql = "insert into recipe values(null,?,?,?,?,?,?)";
        try (Connection c = ConnectionUtil.getConnection();) {
            assert c != null;
            try (PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {

                ps.setString(1, recipe.getRecipeName());
                ps.setInt(2, recipe.getServeNumber());
                ps.setInt(3, recipe.getPrepTime());
                ps.setInt(4, recipe.getCookTime());
                ps.setString(5, recipe.getImagePath());
                ps.setString(6, recipe.getFlavour());
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

    /*
     * Insert the ingredients list to the INGREDIENT table
     *
     * @param r
     * @throws SQLException
     */
    private static void addIngredient(Recipe recipe) throws SQLException {
        String sql = "insert into ingredient values(?,?,?,?,?)";
        for (int i = 0 ;i < recipe.getIngredients().size();i++) {
            try (Connection c = ConnectionUtil.getConnection();) {
                assert c != null;
                try (PreparedStatement ps = c.prepareStatement(sql);) {

                    ps.setInt(1, recipe.getRecipeID());
                    ps.setString(2, recipe.getIngredients().get(i).getIngredientName());
                    ps.setDouble(3, recipe.getIngredients().get(i).getQuantity());
                    ps.setString(4, recipe.getIngredients().get(i).getUnit());
                    ps.setString(5, recipe.getIngredients().get(i).getDescription());
                    ps.execute();

                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
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

    private static void addPrepStep(Recipe recipe) throws SQLException {

        String sql = "insert into preparationstep values(?,?,?)";

        for (int i = 0; i < recipe.getPreparationStep().size(); i++) {
            try (Connection c = ConnectionUtil.getConnection();) {
                assert c != null;
                try (PreparedStatement ps = c.prepareStatement(sql);) {

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

    public static void deleteRecipe(Integer recipe_id) {
        try (Connection c = ConnectionUtil.getConnection();) {
            assert c != null;
            try (Statement s = c.createStatement();) {

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

    public static void updateRecipe(Recipe recipe) throws SQLException {

        String sql = "update recipe set name = ?, serveamount = ? , preparationtime = ?, cookingTime = ?, imagpath = ?, flavour = ? where recipe_id = ?";
        try (Connection c = ConnectionUtil.getConnection();) {
            assert c != null;
            try (PreparedStatement ps = c.prepareStatement(sql);) {

                ps.setString(1, recipe.getRecipeName());
                ps.setString(2, recipe.getFlavour());
                ps.setInt(3, recipe.getServeNumber());
                ps.setInt(4, recipe.getPrepTime());
                ps.setInt(5, recipe.getCookTime());
                ps.setString(6, recipe.getImagePath());
                ps.setInt(7, recipe.getRecipeID());
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
        updateIngredient(recipe);
        updatePrepStep(recipe);
    }

    private static void updatePrepStep(Recipe recipe) throws SQLException {

        String sql = "update preparationstep set step = ?,  description = ? where recipe_id = ?";

        for (int i = 0; i < recipe.getPreparationStep().size(); i++) {
            try (Connection c = ConnectionUtil.getConnection();) {
                assert c != null;
                try (PreparedStatement ps = c.prepareStatement(sql);) {

                    ps.setInt(1, i);
                    ps.setString(2, recipe.getPreparationStep().get(i));
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

    private static void updateIngredient(Recipe recipe) throws SQLException {
        String sql = "update ingredient set name = ?,  quantity = ? , unit  = ? where recipe_id = ?";
        for (int i = 0 ;i < recipe.getIngredients().size();i++) {
            try (Connection c = ConnectionUtil.getConnection();) {
                assert c != null;
                try (PreparedStatement ps = c.prepareStatement(sql);) {

                    ps.setString(1, recipe.getIngredients().get(i).getIngredientName());
                    ps.setDouble(2, recipe.getIngredients().get(i).getQuantity());
                    ps.setString(3, recipe.getIngredients().get(i).getUnit());
                    ps.setString(4, recipe.getIngredients().get(i).getDescription());
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

    public static List<Recipe> recipeList() {
        return recipeList(0, Short.MAX_VALUE);
    }


    private static List<Recipe> recipeList(int start, int count) {
        List<Recipe> recipes = new ArrayList<Recipe>();

        String sql = "select * from recipe order by recipe_id desc limit ?,? ";
        String sql2 = "select * from ingredient where recipe_id = ?";
        String sql3 = "select * from preparationstep where recipe_id = ?";

        try (Connection c = ConnectionUtil.getConnection();) {
            assert c != null;
            try (PreparedStatement ps = c.prepareStatement(sql);) {

                ps.setInt(1, start);
                ps.setInt(2, count);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Recipe r  = new Recipe();
                    r.setRecipeID(rs.getInt(1)) ;
                    r.setRecipeName(rs.getString("name"))  ;
                    r.setServeNumber(rs.getInt(3));
                    r.setPrepTime(rs.getInt(4));
                    r.setCookTime(rs.getInt(5));
                    r.setImagePath(rs.getString(6));
                    r.setFlavour(rs.getString("flavour"));

                    r.setIngredients(loadIngredient(sql2,r.getRecipeID(),c));
                    r.setPreparationStep(loadPrepStep(sql3, r.getRecipeID(),c));

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

    private static ArrayList<String> loadPrepStep(String sql, int id, Connection c) {
        ArrayList<String> prep = new ArrayList<>();

        try (PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                prep.add(rs.getString(3));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return prep;
    }

    private static ArrayList<Ingredient> loadIngredient(String sql, int id,Connection c) {
        ArrayList<Ingredient> ingres = new ArrayList<>();

        try (PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ingredient ingre = new Ingredient(rs.getString(2),rs.getDouble(3),rs.getString(4),rs.getString(5));

                ingres.add(ingre);
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return ingres;
    }

    public static ObservableList<Recipe> getRecipeList() {
        ObservableList<Recipe> recipes = FXCollections.observableArrayList();

        String sql = "select * from recipe order by recipe_id desc ";
        String sql2 = "select * from ingredient where recipe_id = ?";
        String sql3 = "select * from preparationstep where recipe_id = ?";

        try (Connection c = ConnectionUtil.getConnection();) {
            assert c != null;
            try (PreparedStatement ps = c.prepareStatement(sql);) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Recipe r  = new Recipe();
                    r.setRecipeID(rs.getInt(1)) ;
                    r.setRecipeName(rs.getString("name"))  ;
                    r.setServeNumber(rs.getInt(3));
                    r.setPrepTime(rs.getInt(4));
                    r.setCookTime(rs.getInt("cookingTime"));
                    r.setImagePath(rs.getString(6));
                    r.setFlavour(rs.getString(7));

                    r.setIngredients(loadIngredient(sql2,r.getRecipeID(),c));
                    r.setPreparationStep(loadPrepStep(sql3, r.getRecipeID(),c));

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

    public static ObservableList<Recipe> findRecipe(String type, String value) {
        ObservableList<Recipe> recipes = FXCollections.observableArrayList();
        String sql = searchSQL(type);
        String sql2 = "select * from ingredient where recipe_id = ?";
        String sql3 = "select * from preparationstep where recipe_id = ?";

        try (Connection c = ConnectionUtil.getConnection();) {
            assert c != null;
            try (PreparedStatement ps = c.prepareStatement(sql);) {
                ps.setString(1, value);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Recipe r  = new Recipe();
                    r.setRecipeID(rs.getInt(1)) ;
                    r.setRecipeName(rs.getString("name"))  ;
                    r.setServeNumber(rs.getInt(3));
                    r.setPrepTime(rs.getInt(4));
                    r.setCookTime(rs.getInt("cookingTime"));
                    r.setImagePath(rs.getString(6));
                    r.setFlavour(rs.getString(7));

                    r.setIngredients(loadIngredient(sql2,r.getRecipeID(),c));
                    r.setPreparationStep(loadPrepStep(sql3, r.getRecipeID(),c));

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

    private static String searchSQL(String type){
        switch (type) {
            case "Name": {
                return "select * from recipe where name like ?";
            }
            default:
                return "select * from recipe order by recipe_id desc";
        }
    }

    public void connect() throws Exception {
        connection = ConnectionUtil.getConnection();

    }


    public void close() throws Exception {
        ConnectionUtil.disconnect();
    }
}
