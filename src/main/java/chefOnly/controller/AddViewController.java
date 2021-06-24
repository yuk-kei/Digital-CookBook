package chefOnly.controller;

import chefOnly.model.Ingredient;
import chefOnly.model.PreparationStep;
import chefOnly.model.Recipe;
import chefOnly.view.CloseAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

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
    private TextField IngredientNameArea;

    @FXML
    private TextField amountArea;

    @FXML
    private TextField unitArea;

    @FXML
    private TextField descriptionArea;

    @FXML
    private TextArea prepTextArea;

    @FXML
    private TextField flavourText;

    @FXML
    private VBox prepSteps;

    @FXML
    private VBox ingredients;

    @FXML
    private TableView<PreparationStep> prepStepTable;

    @FXML
    private TableColumn<PreparationStep, Integer> prepNumCol;

    @FXML
    private TableColumn<PreparationStep, String> prepStepCol;



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
    private ObservableList<Ingredient> ingredientLists;
    private ObservableList<PreparationStep> preparationStepLists;
    private final String imagePath =  System.getProperty("user.dir") + "\\src\\main\\resources\\images\\";
            //this.getClass().getClassLoader().getResource("images") + "/";
    private String path = "default";

    @FXML
    void addIngredient(ActionEvent event) {

    }

    @FXML
    void addPreparation(ActionEvent event) {

    }

    @FXML
    void changePicture(ActionEvent event) {
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
            path = imagePath + file.getName();
            fileIn.close();

        } catch (NullPointerException | IOException e) {
            System.out.println("RMC: User doesn't choose a file");
        }
    }

    /**
     * check and save the picture selected by the user
     * @return the return value shows if saving is successful. ture: successful false: failed
     */
    public boolean saveImage() throws IOException {
        boolean saveState = true;
        if (!path.equals("default")) {
            if (!checkImageName(path)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("The name of the image already exists!");
                alert.setContentText("Please change the name and try again!");
                alert.showAndWait();
                saveState = false;

            } else {
                if (recipe.getImagePath().isEmpty()) {
                    recipe.setImagePath(path);
                } else {
                    deleteImage(path);
                    recipe.setImagePath(path);
                    storeImage();
                }
                saveState = true;
            }
        }
        return saveState;
    }

    /**
     * write the image file to the given directory
     */
    private void storeImage() throws IOException {
        BufferedImage bufferImage;
        File outputImage;

        outputImage = new File(path);
        FileOutputStream imageOut = new FileOutputStream(outputImage);
        bufferImage = SwingFXUtils.fromFXImage(image, null);

        ImageIO.write(bufferImage, file.getName().substring(file.getName().lastIndexOf(".") + 1), imageOut);
        imageOut.close();
    }

    private void deleteImage(String path) {
        file = new File(path);
        Path deletePath = Paths.get(path);
        if (file.exists() && !file.getName().equals("chef.png")) {
            try {
                Files.delete(deletePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // when the user delete the picture, root should be change to a default path, otherwise program will have an exception after save the recipe
            if (!this.file.exists()) {
                path = imagePath + "chef.jpg";
                file.renameTo(new File(path));
            }
        }
    }

    /**
     * change the format of serving number
     */
    @FXML
    void changeServeNumber(KeyEvent event) {
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
     */
    @FXML
    void checkCTFormatted(KeyEvent event) {
        String cookingTime = cookTime.getText();
        String index="[1-9][0-9]*";
        if(cookingTime.matches(index)){
            warningText3.setText("");
        }
        else{
            warningText3.setText("must be positive number !!");
        }
    }

    /**
     * check the format of preparation time
     */
    @FXML
    void checkPTFormatted(KeyEvent event) {
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
     * Check if there is file with the same name in the file system
     * @return true: no file with the same name; false: file with the same name already exists
     */
    public boolean checkImageName(String path){
        //create a file linked to the file chosen by user, if user doesn't choose a file, parameter path will be default value.
        File chosenFile = new File(path);
        File[] fileList = new File(imagePath).listFiles();
        assert fileList != null;
        for(File f:fileList) {

            if(f.getName().equals(chosenFile.getName())) {
                warningText1.setText("Same image name in current directory");
                return false;
            }
        }
        return true;
    }

    @FXML
    void deleteIngredient(ActionEvent event) {

    }

    @FXML
    void deletePreparation(ActionEvent event) {

    }

    @FXML
    void pressCancel(ActionEvent event) throws IOException {
        Alert backAlert = new Alert(Alert.AlertType.CONFIRMATION);

        backAlert.setTitle("Exit");
        backAlert.setHeaderText("All change would be lost, are you sure to quit?");
        Optional<ButtonType> result = backAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(source == "view") {
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Alert backAlert2 = new Alert(Alert.AlertType.CONFIRMATION);

                backAlert2.setTitle("Exit");
                backAlert2.setHeaderText("Quiting the Recipe View?");
                Optional<ButtonType> result2 = backAlert2.showAndWait();
                result.get();
                window.close();

            } else {
                Parent layout = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource)));
                Scene scene = new Scene(layout);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle(sourceTitle);
                CloseAlert closeAlert = new CloseAlert();
                window.setOnCloseRequest(windowEvent -> closeAlert.popUp(title, message, window, windowEvent));
                window.setScene(scene);
            }

        } else {
            event.consume();
        }
    }

    @FXML
    void pressSave(ActionEvent event) {
        recipe.setRecipeName(recipeNameText.getText());
        recipe.setFlavour(flavourText.getText());
        recipe.setCookTime(Integer.parseInt(cookTime.getText()));
        recipe.setPrepTime(Integer.parseInt(preparationTime.getText()));
        recipe.setImagePath(path);
        Window owner = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (checkRecipeBasic()){
            showAlert(Alert.AlertType.ERROR,owner,"Please fill up the recipe correctly.","Form Error!");
        } else {
            // Check if user selected a image
            if(file == null){
                showAlert(Alert.AlertType.ERROR,owner,"Please select an image.","Form Error!");
            }
        }
    }

    private boolean checkRecipeBasic() {
        return (recipeNameText.getText().isEmpty() || flavourText.getText().isEmpty()||cookTime.getText().isEmpty()
                || preparationTime.getText().isEmpty() || path.isEmpty());
    }

    public String getSource() {
        return source;
    }

    public void setActionSource(String source){
        this.source = source;
        switch (source){
            case "home" : {
                this.resource = "/views/HomeView.fxml";
                this.message = "Are you sure to quit the App? ";
                this.title = "Close Home Page";
                this.sourceTitle = "Chef's Only";
                break;
            }
            case "view":{
                this.resource = "/views/RecipeView.fxml";
                this.message = "This would also close the cookbook. Are you sure?";
                this.title = "Close View Page";
                this.sourceTitle = "Recipe View Page";
                break;
            }
            case "search": {
                this.resource = "/views/SearchView.fxml";
                this.message = "This would also close the cookbook. Are you sure?";
                this.title = "Close Search Page";
                this.sourceTitle = "Chef's Only";
                break;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        ingredientLists = FXCollections.observableArrayList(recipe.getIngredients());
        preparationStepLists = FXCollections.observableArrayList();
        for (int i = 0; i < recipe.getPreparationStep() . size();i++){
            preparationStepLists.add(new PreparationStep(i + 1,recipe.getPreparationStep().get(i)));
        }

        showIngredients();
        showPreparations();
        showRecipeBasic();
    }

    public void showIngredients(){

        ingredientQuantityCol.setCellValueFactory(new PropertyValueFactory<Ingredient,Double>("Quantity"));
        ingredientNameCol.setCellValueFactory(new PropertyValueFactory<Ingredient,String>("IngredientName"));
        ingredientUnitCol.setCellValueFactory(new PropertyValueFactory<Ingredient,String>("Unit"));
        ingredientDesCol.setCellValueFactory(new PropertyValueFactory<Ingredient,String>("Description"));
        ingredientTable.setItems(ingredientLists);
    }

    public void showPreparations(){
        prepNumCol.setCellValueFactory(new PropertyValueFactory<>("StepNumber"));
        prepStepCol.setCellValueFactory(new PropertyValueFactory<>("Content"));
        prepStepTable.setItems(preparationStepLists);

    }

    public void showRecipeBasic(){
        recipeNameText.setText(recipe.getRecipeName());
        flavourText.setText(recipe.getFlavour());

        serveNumber.setText(String.valueOf(recipe.getServeNumber()));
        preparationTime.setText(String.valueOf(recipe.getPrepTime()));
        cookTime.setText(String.valueOf(recipe.getCookTime()));
        path = recipe.getImagePath();

        imageView.fitWidthProperty().bind(imagePane.widthProperty());
        imageView.fitHeightProperty().bind(imagePane.heightProperty());
        imageView.setImage(new Image(path));
    }

    public static void showAlert(Alert.AlertType alertType, Window owner, String message, String title){
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(owner);
        alert.showAndWait();
    }


}

