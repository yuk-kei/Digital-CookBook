package chefOnly;

import chefOnly.view.CloseAlert;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    private static Stage stage;

    private static Scene mainScreenScene;
    private static Scene helpScreenScene;

    @Override
    public void start(Stage currentStage) throws Exception {
        stage = currentStage;

        Parent MainScreen = FXMLLoader.load(getClass().getResource("/views/HomeView.fxml"));
        mainScreenScene = new Scene(MainScreen);

        Parent HelpScreen = FXMLLoader.load(getClass().getResource("/views/Help.fxml"));

        helpScreenScene = new Scene(HelpScreen);
        CloseAlert closeAlert = new CloseAlert();
        currentStage.setOnCloseRequest(windowEvent -> closeAlert.popUp("Close Home Page", "Are you sure to quit the App? ", currentStage, windowEvent));
        currentStage.setTitle("Chef's Only");
        currentStage.setScene(mainScreenScene);
        currentStage.show();
    }

    /**
     * Set up the proper window to be display
     *
     * @param Screen the proper window's name in String
     */
    public static void changeScreen(String Screen) {
        switch (Screen) {
            case "MainScreen": {
                stage.setScene(mainScreenScene);
                break;
            }
            case "help":{
                stage.setScene(helpScreenScene);
                break;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}