package chefOnly.controller;



import chefOnly.Main;
import chefOnly.view.CloseAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller for the home page.
 */
public class HomeViewController implements Initializable {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    public void searchButtonClicked(ActionEvent event) throws IOException {

        Parent layout = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/SearchView.fxml")));
        Scene scene = new Scene(layout);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CloseAlert closeAlert = new CloseAlert();
        window.setOnCloseRequest(windowEvent -> closeAlert.popUp("Close the Search Page", "This would also close the cookbook. Are you sure?", window, windowEvent));
        window.setScene(scene);

    }


    public void addButtonClicked(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddView.fxml"));
        Parent layout = loader.load();
        AddViewController controller = loader.getController();
        controller.setActionSource("home");

        Scene scene = new Scene(layout);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Recipe Add Page");
        CloseAlert closeAlert = new CloseAlert();
        window.setOnCloseRequest(windowEvent -> closeAlert.popUp("Close the Add Page", "All changes would be lost, are you sure to continue?", window, windowEvent));
        window.setScene(scene);

    }

    public void exitButtonClicked(ActionEvent event) {

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);

        alert2.setTitle("Exit");
        alert2.setHeaderText("Are you sure to exit");

        Optional<ButtonType> result = alert2.showAndWait();

        if (result.get() == ButtonType.OK){
            window.close();
        } else {
            event.consume();
        }

    }

    /**
     * This is just a pure static window so no need to mess around
     * with controllers.
     */
    public void helpButtonClicked() throws Exception{
        Main.changeScreen("help");
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
