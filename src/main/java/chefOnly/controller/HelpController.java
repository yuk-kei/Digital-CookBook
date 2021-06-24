package chefOnly.controller;

import chefOnly.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;


public class HelpController {

    @FXML
    private Button backButton;

    @FXML
    void backToMain(ActionEvent event) throws IOException {
        Main.changeScreen("MainScreen");
    }

}
