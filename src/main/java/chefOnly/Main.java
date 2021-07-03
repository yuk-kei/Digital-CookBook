package chefOnly;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * The entrance of the application.
 *
 */
public class Main extends Application {

    private static Stage stage;

    private static Scene mainScreenScene;
    private static Scene helpScreenScene;

    @Override
    public void start(Stage window) throws Exception {
        stage = window;

        Parent homeScreen = FXMLLoader.load(getClass().getResource("/views/HomeView.fxml"));
        mainScreenScene = new Scene(homeScreen);

        Parent HelpScreen = FXMLLoader.load(getClass().getResource("/views/Help.fxml"));
        helpScreenScene = new Scene(HelpScreen);

        window.setOnCloseRequest(windowEvent -> closeWindow(window,windowEvent,"Close the Application","Are you sure to exit"));
        window.setTitle("Chef's Only");
        window.setScene(mainScreenScene);
        window.show();
    }

    /**
     * Set up the proper window to be display
     *
     * @param Screen the proper window's name in String
     */
    public static void changeScreen(String Screen) {
        switch (Screen) {
            case "home": {
                stage.setScene(mainScreenScene);
                break;
            }
            case "help":{
                stage.setScene(helpScreenScene);
                break;
            }
        }
    }

    /**
     * Exit the window.
     *
     * @param event the event
     */

    public static void closeWindow (Stage window, Event event, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            window.close();
        } else {
            event.consume();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}