package chefOnly.controller;

import chefOnly.Main;
import javafx.fxml.FXML;

/**
 * The controller of help window.
 *
 */
public class HelpViewController {

    /**
     * Back to the home page.
     */
    @FXML
    void backToMain() {
        Main.changeScreen("home");
    }

}
