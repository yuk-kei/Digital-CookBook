package chefOnly.controller;

import chefOnly.model.Recipe;
import chefOnly.utils.RecipeDAO;
import chefOnly.view.CloseAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The controller for the Search page.
 *
 */
public class SearchViewController implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<Recipe> recipeTable;

    @FXML
    private TableColumn<Recipe, Integer> recipeNameCol;

    @FXML
    private TableColumn<Recipe, Integer> flavourNameCol;

    @FXML
    private TableColumn<Recipe, Integer> cookTimeCol;

    @FXML
    private HBox group;

    @FXML
    private RadioButton byFlavour;

    @FXML
    private RadioButton byTime;

    @FXML
    private ChoiceBox<String> flavourCB;

    @FXML
    private TextField cookTimeText;

    @FXML
    private Button addButton;

    @FXML
    private Label warningText;

    private List<Recipe> recipes;

    /**
     * Show the search result.
     *
     */
    @FXML
    void showSearchResult() {
        String recipeName = "%" + searchField.getText().substring(1) + "%";
        recipes = RecipeDAO.findRecipe(recipeName);
        if (byFlavour.isSelected()){
            flavourFilter();
        }
        if (byTime.isSelected()){
            timeFilter();
        }
        showTable(recipes);
    }

    /**
     * Select the recipes by its flavour.
     */
    private void flavourFilter() {
        try {
            if (!flavourCB.getSelectionModel().getSelectedItem().isEmpty()) {
                String flavour = flavourCB.getSelectionModel().getSelectedItem();
                List<Recipe> newRecipes = new ArrayList<>();
                for (Recipe recipe : recipes) {
                    if (recipe.getFlavour().equals(flavour)) {
                        newRecipes.add(recipe);
                    }
                }
                recipes = newRecipes;
            }
        } catch (NullPointerException exception){
            System.out.println("User didn't select any flavour!");
        }
    }

    /**
     * Select the recipes by time.
     */
    private void timeFilter() {
        try {
            String timeString = cookTimeText.getText();
            int time;

            try {
                time = Integer.parseInt(timeString);
            } catch (Exception e) {
                time = 1;
            }
            String indexString = "[1-9][0-9]*";
            if (timeString.matches(indexString)) {
                List<Recipe> newRecipes = new ArrayList<>();
                for (Recipe recipe: recipes){
                    if (recipe.getCookTime() <= time){
                        newRecipes.add(recipe);
                    }
                }
                recipes = newRecipes;
            } else {
                warningText.setText("the cook time must be positive integer !!");
            }
        } catch (Exception ignored) {

        }
    }

    /**
     * Back to the home page.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void backToMain(MouseEvent event) throws IOException {
        Parent layout = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/HomeView.fxml")));
        Scene scene = new Scene(layout);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CloseAlert closeAlert = new CloseAlert();
        window.setOnCloseRequest(windowEvent -> closeAlert.popUp("Close Home Page", "Are you sure to quit the App? ", window, windowEvent));
        window.setScene(scene);
    }

    /**
     * Open add Recipe Page.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void openAddView(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddView.fxml"));
        Parent layout = loader.load();
        AddViewController controller = loader.getController();
        controller.setActionSource("search");

        Scene scene = new Scene(layout);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Recipe Add Page");
        CloseAlert closeAlert = new CloseAlert();
        window.setOnCloseRequest(windowEvent -> closeAlert.popUp("Close the Search Page", "All changes would be lost, are you sure to continue?", window, windowEvent));
        window.setScene(scene);

    }

    /**
     * Open the View recipe page by double click the selected recipe.
     *
     * @param event the event
     */
    @FXML
    void viewRecipeDetails(MouseEvent event) {
        if (recipeTable.getSelectionModel().selectedItemProperty() != null){
            if (event.getClickCount() == 2) {
                Recipe clickedRecipe = recipeTable.getSelectionModel().getSelectedItem();
                try {
                    openViewPage(clickedRecipe,event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Open the recipe view page.
     * @throws IOException the io exception
     * @param recipe the recipe which need to be detailed
     * @param event the mouse event
     */
    private void openViewPage(Recipe recipe, MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipeView.fxml"));
        Parent root = loader.load();
        RecipeViewController controller = loader.getController();
        controller.addRecipe(recipe);

        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Recipe View Page");
        CloseAlert closeAlert = new CloseAlert();
        window.setOnCloseRequest(windowEvent -> closeAlert.popUp("Close View Page", "This would also close the cookbook. Are you sure?", window, windowEvent));
        window.setScene(scene);
    }

    /**
     * Initialize the search page by storing all recipes in the list.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        recipes = RecipeDAO.recipeList();
        showTable(recipes);
        setFlavour();
    }

    /**
     * Show the recipes on result table.
     *
     * @param recipes the recipes
     */
    public void showTable(List<Recipe> recipes){
        ObservableList<Recipe> list = FXCollections.observableArrayList(recipes);
        recipeNameCol.setCellValueFactory(new PropertyValueFactory<>("recipeName"));
        flavourNameCol.setCellValueFactory(new PropertyValueFactory<>("flavour"));
        cookTimeCol.setCellValueFactory(new PropertyValueFactory<>("cookTime"));
        recipeTable.setItems(list);
    }

    /**
     * Initiate teh flavour choice box.
     */
    private void setFlavour() {
        ObservableList<String> flavourLists = FXCollections.observableArrayList();
        flavourLists.add("sweet");
        flavourLists.add("spicy");
        flavourLists.add("salty");
        flavourLists.add("sour");
        flavourLists.add("bitter");
        for (String str: flavourLists){
            flavourCB.getItems().add(str);
        }
    }

}

