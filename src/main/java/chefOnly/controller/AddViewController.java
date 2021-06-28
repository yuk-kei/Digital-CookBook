package chefOnly.controller;

import chefOnly.model.Ingredient;
import chefOnly.model.Recipe;
import chefOnly.utils.RecipeDAO;
import chefOnly.view.CloseAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller of the Add View Page.
 * This controller allow this page to change between Add mode and Modify mode corresponding to the source.
 *
 */
public class AddViewController implements Initializable {

    @FXML
    private Pane imagePane;

    @FXML
    private ImageView imageView;

    @FXML
    private Button changeImage;

    @FXML
    private Label warningText1;

    @FXML
    private TextField serveNumber;

    @FXML
    private Label warningText2;

    @FXML
    private TextField preparationTime;

    @FXML
    private Label warningText3;

    @FXML
    private TextField cookTime;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField recipeNameText;

    @FXML
    private TextField ingredientNameArea;

    @FXML
    private TextField quantityArea;

    @FXML
    private TextField unitArea;

    @FXML
    private TextField descriptionArea;

    @FXML
    private Button addIngredientButton;

    @FXML
    private Button deleteIngredientButton;

    @FXML
    private Button modifyIngredientButton;

    @FXML
    private TextArea prepTextArea;

    @FXML
    private Button addPrepButton;

    @FXML
    private Button deletePrepButton;

    @FXML
    private Button modifyPrepStepsButton;

    @FXML
    private TextField flavourText;

    @FXML
    private VBox prepSteps;

    @FXML
    private VBox ingredients;

    @FXML
    private ListView<String> preparationList;

    @FXML
    private TableView<Ingredient> ingredientTable;

    @FXML
    private TableColumn<Ingredient,Double> ingredientQuantityCol;

    @FXML
    private TableColumn<Ingredient, String> ingredientUnitCol;

    @FXML
    private TableColumn<Ingredient, String> ingredientNameCol;

    @FXML
    private TableColumn<Ingredient, String> ingredientDesCol;

    private String resource;
    private String message;
    private Recipe recipe;
    private String source;
    private String title;
    private String sourceTitle;
    private File file;
    private Image image;
    private boolean edit;

    private final String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\images\\";
    private String projectPath = System.getProperty("user.dir") +"\\target\\classes\\images\\";
                    //            this.getClass().getClassLoader().getResource("images") + "/";
    private String path = "default";

    /**
     * Sets recipe.
     *
     * @param recipe the recipe
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;

        showIngredients();
        showPreparations();
        showRecipeBasic();
    }

    /**
     * Show ingredients.
     */
    public void showIngredients(){
        ObservableList<Ingredient> ingredientLists = FXCollections.observableArrayList(recipe.getIngredients());
        ingredientQuantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        ingredientNameCol.setCellValueFactory(new PropertyValueFactory<>("IngredientName"));
        ingredientUnitCol.setCellValueFactory(new PropertyValueFactory<>("Unit"));
        ingredientDesCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        ingredientTable.setItems(ingredientLists);
    }

    /**
     * Show preparations.
     */
    public void showPreparations(){
        ObservableList<String> preparations = FXCollections.observableArrayList(recipe.getPreparationStep());
        preparationList.setItems(preparations);
    }

    /**
     * Show the basic information of recipe .
     */
    public void showRecipeBasic(){
        recipeNameText.setText(recipe.getRecipeName());
        flavourText.setText(recipe.getFlavour());

        serveNumber.setText(String.valueOf(recipe.getServeNumber()));
        preparationTime.setText(String.valueOf(recipe.getPrepTime()));
        cookTime.setText(String.valueOf(recipe.getCookTime()));

        imageView.fitWidthProperty().bind(imagePane.widthProperty());
        imageView.fitHeightProperty().bind(imagePane.heightProperty());
        imageView.setImage(new Image(recipe.getImagePath()));
    }

    /**
     * Open a file chooser to display the picture chosen by the user
     */
    @FXML
    void changePicture() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Picture");
            File chosenPicture = fileChooser.showOpenDialog(null);
            path = chosenPicture.getPath();
            file = new File(path);

            FileInputStream fileIn;

            imageView.fitWidthProperty().bind(imagePane.widthProperty());
            imageView.fitHeightProperty().bind(imagePane.heightProperty());
            image = new Image(fileIn = new FileInputStream(chosenPicture), imageView.getFitWidth(), imageView.getFitHeight(), true, true);
            imageView.setImage(image);
            imageView.setVisible(true);
            path = filePath + file.getName();

            fileIn.close();

        } catch (NullPointerException | IOException e) {
            System.out.println("User doesn't choose a file");
        }
    }

    /**
     * Check and save the picture selected by the user
     *
     * @return the statues of the saving process
     * @throws IOException the io exception
     * @param owner the current window
     */
    public boolean saveImage(Window owner) throws IOException {
        boolean saveState = true;
        if (!path.equals("default")) {
            if (checkImageName(path) && checkImageName(projectPath + file.getName())) {
                showAlert(Alert.AlertType.WARNING,owner,"Please change the name and try again!","The name of the image already exists!");
                saveState = false;
            } else {
                if (recipe.getImagePath().isEmpty()) {
                    recipe.setImagePath("images/" + file.getName());
                } else {
                    deleteImage(filePath + recipe.getImagePath().substring(recipe.getImagePath().lastIndexOf("/") + 1));
                    deleteImage(projectPath + recipe.getImagePath().substring(recipe.getImagePath().lastIndexOf("/") + 1));
                    recipe.setImagePath("images/" + file.getName());
                    storeImage();
                }
                saveState = true;
            }
        } else if (!edit){
            showAlert(Alert.AlertType.ERROR,owner,"Please select an image.","Form Error!");
            saveState = false;
        }
        return saveState;
    }

    /**
     * write the image file to the project image archive.
     *
     * @throws IOException the IO exception
     */
    private void storeImage() throws IOException {
        BufferedImage bufferImage;
        File outputImage;
        File projectOutputImage;

        outputImage = new File(path);
        projectOutputImage = new File(projectPath + file.getName());
        FileOutputStream imageOut = new FileOutputStream(outputImage);
        FileOutputStream projectOut = new FileOutputStream(projectOutputImage);
        bufferImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bufferImage, file.getName().substring(file.getName().lastIndexOf(".") + 1), imageOut);
        ImageIO.write(bufferImage, file.getName().substring(file.getName().lastIndexOf(".") + 1), projectOut);
        imageOut.close();
    }

    /**
     * Delete the duplicate image from the archive
     * @param path the path of image which need to be deleted
     */
    private void deleteImage(String path) {
        File file = new File(path);
        Path deletePath = Paths.get(path);
        if (file.exists() && !file.getName().equals("chef.png")) {
            try {
                Files.delete(deletePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // when the user delete the picture, root should be change to a default path, otherwise program will have an exception after save the recipe
            if (!this.file.exists()) {
                path = filePath + "chef.jpg";
                file.renameTo(new File(path));
            }
        }
    }

    /**
     * Check if there is file with the same name in the file system
     *
     * @param path the path
     * @return true : no file with the same name; false: file with the same name already exists
     */
    public boolean checkImageName(String path){
        //create a file linked to the file chosen by user, if user doesn't choose a file, parameter path will be default value.
        File chosenFile = new File(path);
        File[] fileList = new File(filePath).listFiles();

        assert fileList != null;
        for(File f:fileList) {

            if(f.getName().equals(chosenFile.getName())) {
                warningText1.setText("Same image name in current directory");
                return true;
            }
        }

        return false;
    }

    /**
     * Add ingredient.
     *
     * @param event the event
     */
    @FXML
    void addIngredient(ActionEvent event) {
        Ingredient add = new Ingredient(ingredientNameArea.getText(),Double.parseDouble(quantityArea.getText()),unitArea.getText(),descriptionArea.getText());

        recipe.getIngredients().add(add);
        showIngredients();
        ingredientTextClear();
    }

    /**
     * Delete ingredient.
     *
     * @param event the event
     */
    @FXML
    void deleteIngredient(ActionEvent event) {
        Ingredient delete = ingredientTable.getSelectionModel().getSelectedItem();

        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            if (delete == recipe.getIngredients().get(i)) {
                recipe.getIngredients().remove(i);
            }
        }
        showIngredients();
        ingredientTextClear();
    }

    /**
     * Modify ingredient.
     *
     * @param event the event
     */
    @FXML
    void modifyIngredient(ActionEvent event) {
        Ingredient modify = new Ingredient(ingredientNameArea.getText(),Double.parseDouble(quantityArea.getText()),unitArea.getText(),descriptionArea.getText());
        Ingredient before = ingredientTable.getSelectionModel().getSelectedItem();
        for (int i = 0; i < recipe.getIngredients().size(); i++){
            if(before == recipe.getIngredients().get(i)){
                recipe.getIngredients().set(i,modify);
            }
        }
        showIngredients();
        ingredientTextClear();
    }

    /**
     * Display ingredient.
     *
     * @param event the event
     */
    @FXML
    void displayIngredient(MouseEvent event) {
        Ingredient ingredient = ingredientTable.getSelectionModel().getSelectedItem();
        quantityArea.setText(String.valueOf(ingredient.getQuantity()));
        unitArea.setText(ingredient.getUnit());
        ingredientNameArea.setText(ingredient.getIngredientName());
        descriptionArea.setText(ingredient.getDescription());
    }

    private void ingredientTextClear() {
        quantityArea.clear();
        unitArea.clear();
        ingredientNameArea.clear();
        descriptionArea.clear();
    }

    /**
     * Add preparation.
     *
     * @param event the event
     */
    @FXML
    void addPreparation(ActionEvent event) {
        String add = prepTextArea.getText();

        recipe.getPreparationStep().add(add);
        showPreparations();
        prepTextArea.clear();
    }

    /**
     * Delete preparation.
     *
     * @param event the event
     */
    @FXML
    void deletePreparation(ActionEvent event) {
        String delete = preparationList.getSelectionModel().getSelectedItem();

        for (int i = 0; i < recipe.getPreparationStep().size(); i++){
            if (delete.equals(recipe.getPreparationStep().get(i))){
                recipe.getPreparationStep().remove(i);
            }
        }
        showPreparations();
        prepTextArea.clear();
    }

    /**
     * Modify preparation steps.
     *
     * @param event the event
     */
    @FXML
    void modifyPrepSteps(ActionEvent event) {
        String modify = preparationList.getSelectionModel().getSelectedItem();

        for (int i = 0; i < recipe.getPreparationStep().size() ;i++){
            if (modify.equals(recipe.getPreparationStep().get((i)))){
                recipe.getPreparationStep().set(i,prepTextArea.getText()) ;
            }
        }
        showPreparations();
        prepTextArea.clear();
    }

    /**
     * Display the preparation steps.
     */
    @FXML
    void displayPrepSteps() {
        preparationList.getSelectionModel().getSelectedItem();
        prepTextArea.setText(preparationList.getSelectionModel().getSelectedItem());
    }

    /**
     * check the format of serving amount
     *
     * @param event the event
     */
    @FXML
    void changeServeAmount(KeyEvent event) {
        String textContent = serveNumber.getText();
        String indexString = "[1-9][0-9]*";
        if (textContent.matches(indexString)){
            warningText1.setText("");
        }
        else {
            warningText1.setText("must be positive integer !!");
        }
    }

    /**
     * check the format of cooking time
     *
     * @param event the event
     */
    @FXML
    void checkCookTimeFormat(KeyEvent event) {
        String cookingTime = cookTime.getText();
        String index="[1-9][0-9]*";
        if(cookingTime.matches(index)){
            warningText2.setText("");
        }
        else{
            warningText2.setText("must be positive number !!");
        }
    }

    /**
     * check the format of preparation time
     *
     * @param event the event
     */
    @FXML
    void checkPrepareTimeFormat(KeyEvent event) {
        String prepTime = preparationTime.getText();
        String index="[1-9][0-9]*";
        if(prepTime.matches(index)){
            warningText3.setText("");
        }
        else{
            warningText3.setText("must be positive number!!");
        }
    }

    /**
     * Press cancel to quit the Add view.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void pressCancel(ActionEvent event) throws IOException {
        Alert backAlert = new Alert(Alert.AlertType.CONFIRMATION);

        backAlert.setTitle("Exit");
        backAlert.setHeaderText("All change would be lost, are you sure to quit?");
        Optional<ButtonType> result = backAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            back(event,false);
        } else {
            event.consume();
        }
    }

    /**
     * Save the information of the recipe and return it to the database for editing and adding
     *
     * @param event the event
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    @FXML
    void pressSave(ActionEvent event) throws IOException, SQLException {
        Window owner = ((Node) event.getSource()).getScene().getWindow();

        if (checkRecipeBasic()){
            showAlert(Alert.AlertType.ERROR,owner,"Please fill up the recipe correctly.","Form Error!");
        } else {
            recipe.setRecipeName(recipeNameText.getText());
            recipe.setFlavour(flavourText.getText().toLowerCase());
            recipe.setServeNumber(Integer.parseInt(serveNumber.getText()));
            recipe.setCookTime(Integer.parseInt(cookTime.getText()));
            recipe.setPrepTime(Integer.parseInt(preparationTime.getText()));

            // Check if user selected a image
            if(saveImage(owner)) {
                if (edit) {
                    RecipeDAO.deleteRecipe(recipe.getRecipeID());
                    RecipeDAO.addRecipe(recipe);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("The recipe has been successful modified!!");
                    alert.show();
                    System.out.println(recipe.getImagePath());
                } else {
                    RecipeDAO.addRecipe(recipe);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("The recipe has been successful added!!");
                    alert.show();
                }
                back(event, true);
            }
        }
    }

    private void back(ActionEvent event, boolean saveStatus) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent root = loader.load();
        if (source.equals("view")){
            RecipeViewController controller = loader.getController();
            if (saveStatus){
                controller.addRecipe(recipe);
            }
        }

        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle(sourceTitle);
        CloseAlert closeAlert = new CloseAlert();
        window.setOnCloseRequest(windowEvent -> closeAlert.popUp(title, message, window, windowEvent));
        window.setScene(scene);
    }

    /**
     * Check whether all basic textile has been filled.
     * @return whether the form is filled correctly
     */
    private boolean checkRecipeBasic() {
        return (recipeNameText.getText().isEmpty() || flavourText.getText().isEmpty()||cookTime.getText().isEmpty()
                || preparationTime.getText().isEmpty() || path.isEmpty());
    }

    /**
     * Set the action source.
     *
     * @param source the source
     */
    public void setActionSource(String source){
        this.source = source;
        switch (source){
            case "home" : {
                this.resource = "/views/HomeView.fxml";
                this.message = "Are you sure to quit the App? ";
                this.title = "Close Home Page";
                this.sourceTitle = "Chef's Only";
                this.edit = false;
                break;
            }
            case "view":{
                this.resource = "/views/RecipeView.fxml";
                this.message = "This would also close the cookbook. Are you sure?";
                this.title = "Close View Page";
                this.sourceTitle = "Recipe View Page";
                this.edit = true;
                break;
            }
            case "search": {
                this.resource = "/views/SearchView.fxml";
                this.message = "This would also close the cookbook. Are you sure?";
                this.title = "Close Search Page";
                this.sourceTitle = "Chef's Only";
                this.edit = false;
                break;
            }
        }
    }

    /**
     * Show the alert information.
     *
     * @param alertType the alert type
     * @param owner     the owner
     * @param message   the message
     * @param title     the title
     */
    public static void showAlert(Alert.AlertType alertType, Window owner, String message, String title){
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(owner);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!edit){
            recipe = new Recipe();
        }
    }

}

