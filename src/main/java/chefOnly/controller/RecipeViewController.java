package chefOnly.controller;

import chefOnly.model.Recipe;
import chefOnly.utils.RecipeDAO;
import chefOnly.view.CloseAlert;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RecipeViewController implements Initializable {

    @FXML
    private Pane imagePane;

    @FXML
    private ImageView imageView;

    @FXML
    private Label warningText;

    @FXML
    private TextField serveAmountText;

    @FXML
    private Label prepTimeText;

    @FXML
    private Label cookTimeText;

    @FXML
    private Button modify;

    @FXML
    private Button delete;

    @FXML
    private Button back;

    @FXML
    private Label nameLabel;

    @FXML
    private Label flavourText;

    @FXML
    private Label preparationStepText;

    @FXML
    private Label ingredientText;

    @FXML
    Stage primaryStage;

    private Recipe recipe;
    private Image image;
    private StringProperty name;

    @FXML
    void back(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Alert backAlert = new Alert(Alert.AlertType.CONFIRMATION);

        backAlert.setTitle("Exit");
        backAlert.setHeaderText("Quiting the Recipe View?");
        Optional<ButtonType> result = backAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            window.close();
        } else {
            event.consume();
        }
        window.close();
    }

    @FXML
    void changeServeNumber() {
        try {
            String numberContent = serveAmountText.getText();
            int changeNumber;

            try {
                changeNumber = Integer.parseInt(numberContent);
            } catch (Exception e) {
                changeNumber = 1;
            }
            String indexString = "[1-9][0-9]*";
            if (numberContent.matches(indexString)) {
                recipe.changeQuantity(changeNumber);
                ingredientText.setText(recipe.getFormattedIngredients());
                warningText.setText("");

            } else {
                warningText.setText("Serving amount must be positive integer!!");
            }
        } catch (Exception ignored) {

        }
    }

    @FXML
    void delete(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Alert backAlert = new Alert(Alert.AlertType.CONFIRMATION);

        backAlert.setTitle("Exit");
        backAlert.setHeaderText("Quiting the Recipe View?");
        Optional<ButtonType> result = backAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            RecipeDAO.deleteRecipe(recipe.getRecipeID());
            window.close();
        } else {
            event.consume();
        }
        window.close();
    }

    @FXML
    void modify(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddView.fxml"));
        Parent root = loader.load();;
        Stage window;
        Scene fxmlFile;

        AddViewController controller = loader.getController();
        controller.setRecipe(recipe);
        controller.setActionSource("view");

        fxmlFile = new Scene(root);
        window = new Stage();
        window.setScene(fxmlFile);
        window.initModality(Modality.APPLICATION_MODAL);

        //window.setAlwaysOnTop(true);
        window.setIconified(false);
        // window.initStyle(StageStyle.UNDECORATED);
        window.setTitle("Recipe Modify Page");
        CloseAlert closeAlert = new CloseAlert();
        window.setOnCloseRequest(windowEvent -> closeAlert.popUp("Close View Page", "This would also close the cookbook. Are you sure?", window, windowEvent));
        window.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;

        nameLabel.setText(recipe.getRecipeName());
        flavourText.setText(recipe.getFlavour());
        ingredientText.setText(recipe.getFormattedIngredients());
        preparationStepText.setText(recipe.getFormattedPreparationStep());

        serveAmountText.setText(String.valueOf(recipe.getServeNumber()));
        prepTimeText.setText(String.valueOf(recipe.getPrepTime()));
        cookTimeText.setText(String.valueOf(recipe.getCookTime()));

        imageView.fitWidthProperty().bind(imagePane.widthProperty());
        imageView.fitHeightProperty().bind(imagePane.heightProperty());
        imageView.setImage(new Image(recipe.getImagePath()));
    }

}
