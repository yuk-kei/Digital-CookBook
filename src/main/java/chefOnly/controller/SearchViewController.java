package chefOnly.controller;

import chefOnly.Main;
import chefOnly.model.Recipe;
import chefOnly.utils.RecipeDAO;
import chefOnly.view.CloseAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

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

    Scene fxmlFile;
    Parent root;

    Stage window;

    @FXML
    void backToMain(MouseEvent event) throws IOException {
        Parent layout = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/HomeView.fxml")));
        Scene scene = new Scene(layout);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CloseAlert closeAlert = new CloseAlert();
        window.setOnCloseRequest(windowEvent -> closeAlert.popUp("Close Home Page", "Are you sure to quit the App? ", window, windowEvent));
        window.setScene(scene);
    }

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

    @FXML
    void showSearchResult(ActionEvent event) {

    }

    @FXML
    void viewRecipeDetails(MouseEvent event) {
        if (recipeTable.getSelectionModel().selectedItemProperty() != null){
            if (event.getClickCount() == 2) {
                Recipe clickedRecipe = recipeTable.getSelectionModel().getSelectedItem();
                try {
                    openModelWindow("/views/RecipeView.fxml","Recipe View Page",clickedRecipe);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void openModelWindow(String resource, String title, Recipe recipe) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        root = loader.load();

        RecipeViewController controller = loader.getController();
        controller.addRecipe(recipe);
        fxmlFile = new Scene(root);
        window = new Stage();
        window.setScene(fxmlFile);
        window.initModality(Modality.APPLICATION_MODAL);

        //window.setAlwaysOnTop(true);
        window.setIconified(false);
        // window.initStyle(StageStyle.UNDECORATED);
        window.setTitle(title);
        CloseAlert closeAlert = new CloseAlert();
        window.setOnCloseRequest(windowEvent -> closeAlert.popUp("Close View Page", "This would also close the cookbook. Are you sure?", window, windowEvent));
        window.showAndWait();
    }


    public void showTable(){
        ObservableList<Recipe> list = RecipeDAO.getRecipeList();
        recipeNameCol.setCellValueFactory(new PropertyValueFactory<Recipe,Integer>("recipeName"));
        flavourNameCol.setCellValueFactory(new PropertyValueFactory<Recipe,Integer>("flavour"));
        cookTimeCol.setCellValueFactory(new PropertyValueFactory<Recipe,Integer>("cookTime"));
        recipeTable.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RecipeDAO.recipeList();
        showTable();
        setFlavour();

    }

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

