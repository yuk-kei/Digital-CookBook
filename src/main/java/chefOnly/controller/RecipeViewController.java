package chefOnly.controller;

import chefOnly.Main;
import chefOnly.model.Recipe;
import chefOnly.utils.RecipeDAO;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The controller of the Recipe view page
 *
 */
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
    private Label nameLabel;

    @FXML
    private Label flavourText;

    @FXML
    private TextArea preparationStepTextArea;

    @FXML
    private TextArea ingredientTextArea;

    private Recipe recipe;
    /**
     * Back to the search page.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void back(ActionEvent event) throws IOException {
        Alert backAlert = new Alert(Alert.AlertType.CONFIRMATION);
        backAlert.setTitle("Exit");
        backAlert.setHeaderText("Quiting the Recipe View?");

        Optional<ButtonType> result = backAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            backToSearch(event);
        } else {
            event.consume();
        }
    }

    /**
     * Auto detect the format of teh serve amount and change the quantities of ingredients.
     */
    @FXML
    void changeServeNumber() {
        try {
            String serveNumber = serveAmountText.getText();
            if(isPureDigital(serveNumber)){
                int newServeNumber = Integer.parseInt(serveNumber);
                recipe.calculateQuantity(newServeNumber);
                ingredientTextArea.setText(recipe.getFormattedIngredients());
                warningText.setText("");
            } else {
                warningText.setText("Serving amount must be positive integer!!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    /**
     * check whether the serve amount is positive integer.
     */
    private boolean isPureDigital(String string) {
        if (string == null || "".equals(string)){
            return false;
        }
        String regex = "^[1-9]\\d*$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regex);
        m = p.matcher(string);
        return m.matches();
    }

    /**
     * Delete this recipe .
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void delete(ActionEvent event) throws IOException {

        Alert backAlert = new Alert(Alert.AlertType.CONFIRMATION);
        backAlert.setTitle("Delete");
        backAlert.setHeaderText("Are you sure to delete?");

        Optional<ButtonType> result = backAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            RecipeDAO.deleteRecipe(recipe.getRecipeID());
            backToSearch(event);
        } else {
            event.consume();
        }
    }

    /**
     *  Open the Modify page to modify the content of recipe.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void modify(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddView.fxml"));
        Parent layout = loader.load();
        AddViewController controller = loader.getController();
        controller.setRecipe(recipe);
        controller.setActionSource("view");

        Scene scene = new Scene(layout);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setTitle("Recipe Modify Window");
        window.setOnCloseRequest(windowEvent -> Main.closeWindow(window,windowEvent,"Close the Modify Window","This would also close the cookbook, all changes would be lost. Are you sure?"));
        window.setScene(scene);
    }

    private void backToSearch(ActionEvent event) throws IOException {
        Parent layout = FXMLLoader.load(getClass().getResource("/views/SearchView.fxml"));
        Scene scene = new Scene(layout);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Search Window");
        window.setOnCloseRequest(windowEvent -> Main.closeWindow(window,windowEvent,"Close the Search Window", "This would also close the cookbook. Are you sure?"));
        window.setScene(scene);
    }


    /**
     *  Open Add recipe page.
     *
     * @param recipe the recipe
     */
    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;

        nameLabel.setText(recipe.getRecipeName());
        flavourText.setText(recipe.getFlavour());
        ingredientTextArea.setText(recipe.getFormattedIngredients());
        preparationStepTextArea.setText(recipe.getFormattedPreparationStep());

        serveAmountText.setText(String.valueOf(recipe.getServeNumber()));
        prepTimeText.setText(String.valueOf(recipe.getPrepTime()));
        cookTimeText.setText(String.valueOf(recipe.getCookTime()));

        imageView.fitWidthProperty().bind(imagePane.widthProperty());
        imageView.fitHeightProperty().bind(imagePane.heightProperty());
        imageView.setImage(new Image(recipe.getImagePath()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

}
