package chefOnly.controller;

import chefOnly.Main;
import chefOnly.model.Recipe;
import chefOnly.utils.RecipeDAO;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The controller for the Search page.
 *
 */
public class SearchViewController implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Recipe> recipeTable;

    @FXML
    private TableColumn<Recipe, Integer> recipeNameCol;

    @FXML
    private TableColumn<Recipe, Integer> flavourNameCol;

    @FXML
    private TableColumn<Recipe, Integer> cookTimeCol;

    @FXML
    private RadioButton byFlavour;

    @FXML
    private RadioButton byTime;

    @FXML
    private Button detailButton;

    @FXML
    private ChoiceBox<String> flavourCB;

    @FXML
    private TextField cookTimeText;

    @FXML
    private Label warningText;

    private List<Recipe> recipes;

    /**
     * Show the search result.
     *
     */
    @FXML
    void showSearchResult() {
        String recipeName = searchField.getText();
        recipes = RecipeDAO.findRecipe(recipeName);
        detailButton.setDisable(true);
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
            exception.printStackTrace();
        }
    }

    /**
     * Select the recipes by time.
     */
    private void timeFilter() {
        try {
            if (isPureDigital(cookTimeText.getText())) {
                int time = Integer.parseInt(cookTimeText.getText());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * check whether the cook time is positive integer.
     * @param userInput the input of user in the text field
     * @return whether the user input is positive number
     */
    private boolean isPureDigital(String userInput) {
        if (userInput == null || "".equals(userInput)){
            return false;
        }
        String regex = "^[1-9]\\d*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(userInput);;
        return m.matches();
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

        window.setTitle("Search Window");
        window.setOnCloseRequest(windowEvent -> Main.closeWindow(window,windowEvent,"Close the Search Window","This would also close the cookbook. Are you sure?"));
        window.setScene(scene);
    }

    /**
     * Open add Recipe Page.
     *
     * @param event the Mouse event
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

        window.setTitle("Recipe Add Window");
        window.setOnCloseRequest(windowEvent -> Main.closeWindow(window,windowEvent,"Close the Add Window","All changes would be lost, are you sure to continue?"));
        window.setScene(scene);
    }

    /**
     * Enable the View recipe Window by selecting recipe.
     *
     */
    @FXML
    void viewRecipeDetails() {
        detailButton.setDisable(false);
    }

    /**
     * Open the detail view of the corresponding recipe
     *
     * @param event the Mouse event
     */
    @FXML
    void showDetails(MouseEvent event){
        try {
            Recipe clickedRecipe = recipeTable.getSelectionModel().getSelectedItem();
            openViewPage(clickedRecipe,event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void addListenerForTable(){
//        recipeTable.getSelectionModel().selectedItemProperty().addListener((observableValue,) ->{
//            detailButton.setDisable(Selection != null);
//        } );
//    }

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
        window.setOnCloseRequest(windowEvent -> Main.closeWindow(window,windowEvent,"Close the View Page","This would also close the cookbook. Are you sure?"));

        window.setScene(scene);
    }

    /**
     * Initialize the search page by storing all recipes in the list.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        recipes = RecipeDAO.findRecipe("");
        showTable(recipes);
        setFlavour();
        detailButton.setDisable(true);
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
        flavourLists.add("");
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

