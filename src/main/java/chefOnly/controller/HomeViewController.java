package chefOnly.controller;

import chefOnly.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The controller for the home page.
 *
 */
public class HomeViewController implements Initializable {

    /**
     * Open the Search page.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void searchButtonClicked(ActionEvent event) throws IOException {
        Parent layout = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/SearchView.fxml")));
        Scene scene = new Scene(layout);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setTitle("Search Window");
        window.setOnCloseRequest(windowEvent -> Main.closeWindow(window,windowEvent,"Close the Search Window","This would also close the cookbook. Are you sure?"));
        window.setScene(scene);
    }


    /**
     * Open the Add Recipe page.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void addButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddView.fxml"));
        Parent layout = loader.load();
        AddViewController controller = loader.getController();
        controller.setActionSource("home");

        Scene scene = new Scene(layout);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setTitle("Recipe Add Window");
        window.setOnCloseRequest(windowEvent -> Main.closeWindow(window,windowEvent,"Close the Add Window","All changes would be lost, are you sure to continue?"));
        window.setScene(scene);

    }

    /**
     * Exit the application.
     *
     * @param event the event
     */
    public void exitButtonClicked(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Main.closeWindow(window,event,"Exit","Are you sure to exit");
    }

    /**
     * This is just a pure static window, no need to mess around with controllers.
     */
    public void helpButtonClicked(){
        Main.changeScreen("help");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
