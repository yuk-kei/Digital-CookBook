package chefOnly.controller;

import chefOnly.Main;
import chefOnly.model.Ingredient;
import chefOnly.model.Recipe;
import chefOnly.utils.RecipeDAO;
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
import javafx.scene.layout.Pane;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private Label warningTextTop;

    @FXML
    private TextField serveNumber;

    @FXML
    private Label warningTextMiddle;

    @FXML
    private TextField preparationTime;

    @FXML
    private Label warningTextBottom;

    @FXML
    private TextField cookTime;

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
    private Button deleteIngredientButton;

    @FXML
    private Button modifyIngredientButton;

    @FXML
    private TextArea prepTextArea;

    @FXML
    private Button deletePrepButton;

    @FXML
    private Button modifyPrepStepsButton;

    @FXML
    private TextField flavourText;

    @FXML
    private ChoiceBox<String> flavourCB;

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
    private Recipe newRecipe;
    private Recipe originalRecipe;
    private String source;
    private String title;
    private String sourceTitle;
    private File file;
    private Image image;
    private boolean edit;

    private final String resourcePath = System.getProperty("user.dir") + "\\src\\main\\resources\\images\\";
    private final String compliedPath = System.getProperty("user.dir") +"\\target\\classes\\images\\";
                                      // URL complied archive path:  this.getClass().getClassLoader().getResource("images") + "/";
    private String path = "images/chef.png";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!edit){
            newRecipe = new Recipe();
            deleteIngredientButton.setDisable(true);
            modifyIngredientButton.setDisable(true);
            deletePrepButton.setDisable(true);
            modifyPrepStepsButton.setDisable(true);
        }
        setFlavour();
    }

    /**
     * Sets recipe.
     *
     * @param recipe the recipe
     */
    public void setRecipe(Recipe recipe) {
        this.newRecipe = recipe;
        this.originalRecipe = recipe;

        showIngredients();
        showPreparations();
        showRecipeBasic();

        deleteIngredientButton.setDisable(false);
        modifyIngredientButton.setDisable(false);
        deletePrepButton.setDisable(false);
        modifyPrepStepsButton.setDisable(false);
    }

    /**
     * Initiate teh flavour choice box.
     */
    private void setFlavour() {
        String [] flavours = new String[]{"sweet","spicy","salty","sour","bitter"};
        ObservableList<String> flavourLists = FXCollections.observableArrayList();
        flavourLists.add("sweet");
        flavourLists.add("spicy");
        flavourLists.add("salty");
        flavourLists.add("sour");
        flavourLists.add("bitter");
        for (String str: flavourLists){
            flavourCB.getItems().add(str);
        }
        flavourCB.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldNumber, newNumber) -> flavourText.setText(flavours[newNumber.intValue()]));
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
     * Show ingredients.
     */
    public void showIngredients(){
        ObservableList<Ingredient> ingredientLists = FXCollections.observableArrayList(newRecipe.getIngredients());
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
        ObservableList<String> preparations = FXCollections.observableArrayList(newRecipe.getPreparationStep());
        preparationList.setItems(preparations);
    }

    /**
     * Show the basic information of recipe .
     */
    public void showRecipeBasic(){
        recipeNameText.setText(newRecipe.getRecipeName());
        flavourText.setText(newRecipe.getFlavour());

        serveNumber.setText(String.valueOf(newRecipe.getServeNumber()));
        preparationTime.setText(String.valueOf(newRecipe.getPrepTime()));
        cookTime.setText(String.valueOf(newRecipe.getCookTime()));

        imageView.fitWidthProperty().bind(imagePane.widthProperty());
        imageView.fitHeightProperty().bind(imagePane.heightProperty());
        imageView.setImage(new Image(newRecipe.getImagePath()));
    }

    /**
     * Open a file chooser to display the picture chosen by the user
     */
    @FXML
    void changePicture() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Please select the image of recipe");
            File chosenPicture = fileChooser.showOpenDialog(null);
            path = chosenPicture.getPath();
            file = new File(path);

            FileInputStream fileIn;

            imageView.fitWidthProperty().bind(imagePane.widthProperty());
            imageView.fitHeightProperty().bind(imagePane.heightProperty());
            image = new Image(fileIn = new FileInputStream(chosenPicture), imageView.getFitWidth(), imageView.getFitHeight(), true, true);
            imageView.setImage(image);
            imageView.setVisible(true);
            path = resourcePath + file.getName();

            fileIn.close();

        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add ingredient.
     *
     */
    @FXML
    void addIngredient() {
        Ingredient add = new Ingredient(ingredientNameArea.getText(),Double.parseDouble(quantityArea.getText()),unitArea.getText(),descriptionArea.getText());
        deleteIngredientButton.setDisable(false);
        modifyIngredientButton.setDisable(false);

        newRecipe.getIngredients().add(add);
        showIngredients();
        ingredientTextClear();
    }

    /**
     * Delete ingredient.
     *
     */
    @FXML
    void deleteIngredient() {
        Ingredient delete = ingredientTable.getSelectionModel().getSelectedItem();

        for (int i = 0; i < newRecipe.getIngredients().size(); i++) {
            if (delete == newRecipe.getIngredients().get(i)) {
                newRecipe.getIngredients().remove(i);
            }
        }
        showIngredients();
        ingredientTextClear();
    }

    /**
     * Modify ingredient.
     *
     */
    @FXML
    void modifyIngredient() {
        Ingredient modify = new Ingredient(ingredientNameArea.getText(),Double.parseDouble(quantityArea.getText()),unitArea.getText(),descriptionArea.getText());
        Ingredient before = ingredientTable.getSelectionModel().getSelectedItem();

        for (int i = 0; i < newRecipe.getIngredients().size(); i++){
            if(before == newRecipe.getIngredients().get(i)){
                newRecipe.getIngredients().set(i,modify);
            }
        }
        showIngredients();
        ingredientTextClear();
    }

    /**
     * Display ingredient.
     *
     */
    @FXML
    void displayIngredient() {
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
     */
    @FXML
    void addPreparation() {
        String add = prepTextArea.getText();
        deletePrepButton.setDisable(false);
        modifyPrepStepsButton.setDisable(false);

        newRecipe.getPreparationStep().add(add);
        showPreparations();
        prepTextArea.clear();
    }

    /**
     * Delete preparation.
     *
     */
    @FXML
    void deletePreparation() {
        String delete = preparationList.getSelectionModel().getSelectedItem();

        for (int i = 0; i < newRecipe.getPreparationStep().size(); i++){
            if (delete.equals(newRecipe.getPreparationStep().get(i))){
                newRecipe.getPreparationStep().remove(i);
            }
        }
        showPreparations();
        prepTextArea.clear();
    }

    /**
     * Modify preparation steps.
     *
     */
    @FXML
    void modifyPrepSteps() {
        String modify = preparationList.getSelectionModel().getSelectedItem();

        for (int i = 0; i < newRecipe.getPreparationStep().size() ; i++){
            if (modify.equals(newRecipe.getPreparationStep().get((i)))){
                newRecipe.getPreparationStep().set(i,prepTextArea.getText()) ;
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
     */
    @FXML
    void checkServeAmount() {

        if (isPureDigital(serveNumber.getText())){
            warningTextTop.setText("");
        }
        else {
            warningTextTop.setText("must be positive integer !!");
        }
    }

    /**
     * check the format of preparation time
     *
     */
    @FXML
    void checkPrepareTimeFormat() {

        if(isPureDigital(preparationTime.getText())){
            warningTextMiddle.setText("");
        }
        else{
            warningTextMiddle.setText("must be positive number !!");
        }
    }

    /**
     * check the format of cooking time
     *
     */
    @FXML
    void checkCookTimeFormat() {

        if(isPureDigital(cookTime.getText())){
            warningTextBottom.setText("");
        }
        else{
            warningTextBottom.setText("must be positive number !!");
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
     * Press cancel to quit the Add view.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void cancelButtonClicked(ActionEvent event) throws IOException {
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
    void saveButtonClicked(ActionEvent event) throws IOException, SQLException {
        Window owner = ((Node) event.getSource()).getScene().getWindow();

        if (checkRecipeBasic()){
            showAlert(Alert.AlertType.ERROR,owner,"Please fill up the recipe correctly.","Form Error!");
        } else {
            newRecipe.setRecipeName(recipeNameText.getText());
            newRecipe.setFlavour(flavourText.getText().toLowerCase());
            newRecipe.setServeNumber(Integer.parseInt(serveNumber.getText()));
            newRecipe.setCookTime(Integer.parseInt(cookTime.getText()));
            newRecipe.setPrepTime(Integer.parseInt(preparationTime.getText()));

            // Check if user selected a image
            if(checkAndSaveImage(owner)) {
                if (edit) {
                    RecipeDAO.deleteRecipe(newRecipe.getRecipeID());
                    RecipeDAO.addRecipe(newRecipe);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("The recipe has been successful modified!!");
                    alert.show();
                    System.out.println(newRecipe.getImagePath());
                } else {
                    RecipeDAO.addRecipe(newRecipe);
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
                controller.addRecipe(originalRecipe);
            }else {
                controller.addRecipe(newRecipe);
            }
        }

        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle(sourceTitle);
        window.setOnCloseRequest(windowEvent -> Main.closeWindow(window,windowEvent,title,message));
        window.setScene(scene);
    }

    /**
     * Check whether all basic textile has been filled.
     * @return whether the form is filled correctly
     */
    private boolean checkRecipeBasic() {
        return (recipeNameText.getText().isEmpty() || flavourText.getText().isEmpty()||cookTime.getText().isEmpty()
                || preparationTime.getText().isEmpty() || path.isEmpty() || Integer.parseInt(serveNumber.getText()) <= 0 || Integer.parseInt(preparationTime.getText()) <= 0 || Integer.parseInt(cookTime.getText()) <= 0);
    }

    /**
     * Check and save the picture selected by the user
     *
     * @return the statues of the saving process
     * @throws IOException the io exception
     * @param owner the current window
     */
    public boolean checkAndSaveImage(Window owner) throws IOException {
        boolean savable = true;
        if (!path.equals("images/chef.png")) {
            if (checkImageName(path) && checkImageName(compliedPath + file.getName())) {
                showAlert(Alert.AlertType.WARNING,owner,"Please change the name and try again!","The name of the image already exists!");
                savable = false;
            } else {
                if (newRecipe.getImagePath().isEmpty()) {
                    newRecipe.setImagePath("images/" + file.getName());
                } else {
                    deleteImage(resourcePath + newRecipe.getImagePath().substring(newRecipe.getImagePath().lastIndexOf("/") + 1));
                    deleteImage(compliedPath + newRecipe.getImagePath().substring(newRecipe.getImagePath().lastIndexOf("/") + 1));
                    newRecipe.setImagePath("images/" + file.getName());
                    storeImage();
                }
                savable = true;
            }
        } else if (!edit){
            showAlert(Alert.AlertType.ERROR,owner,"Please select an image.","Form Error!");
            savable = false;
        }
        return savable;
    }

    /**
     * write the image file to the project image archive.
     *
     * @throws IOException the IO exception
     */
    private void storeImage() throws IOException {
        BufferedImage bufferImage;
        File outputImage;
        File outputToCompliedPathImage;

        outputImage = new File(path);
        outputToCompliedPathImage = new File(compliedPath + file.getName());

        FileOutputStream imageOut = new FileOutputStream(outputImage);
        FileOutputStream projectOut = new FileOutputStream(outputToCompliedPathImage);

        bufferImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bufferImage, file.getName().substring(file.getName().lastIndexOf(".") + 1), imageOut);
        ImageIO.write(bufferImage, file.getName().substring(file.getName().lastIndexOf(".") + 1), projectOut);
        imageOut.close();
    }

    /**
     * Delete the duplicate image from the archive
     * @param path the path of image which need to be deleted
     */
    private void deleteImage(String path){

        File file = new File(path);
        Path deletePath = Paths.get(path);
        if (file.exists() && !file.getName().equals("chef.png")) {
            try {
                Files.delete(deletePath);
            } catch (IOException e) {
                e.printStackTrace();
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

        File chosenFile = new File(path);
        File[] fileList = new File(resourcePath).listFiles();

        assert fileList != null;
        for(File f:fileList) {

            if(f.getName().equals(chosenFile.getName())) {
                warningTextTop.setText("Save failed, duplicate name in the archive!");
                return true;
            }
        }

        return false;
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


}

