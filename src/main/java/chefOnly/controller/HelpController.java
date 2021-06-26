package chefOnly.controller;

import chefOnly.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;


/**
 * The controller of help page.
 *
 */
public class HelpController {

    @FXML
    private Button backButton;

    /**
     * Back to the home page.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void backToMain(ActionEvent event) throws IOException {
        Main.changeScreen("MainScreen");
    }

}
