package chefOnly.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This class is used to generated an alert window which contains alert message and cancel and confirm button.
 * 
 */
public class CloseAlert {
	Button cancel;
	Button confirm;
	Stage alertWin;

	/**
	 * define the handle functions of the two buttons. Any subclass of this class can change the handle function by override this function
	 */
	public void buttonReaction(Stage modifyStage, WindowEvent event) {
		cancel.setOnAction(e -> {alertWin.close(); event.consume();});
        confirm.setOnAction(e -> {alertWin.close(); modifyStage.close();});
        alertWin.setOnCloseRequest(e -> {alertWin.close(); event.consume();});
	}

	/**
	 * generate an alert window with handle functions of the buttons set automatically
	 * @param title the title of the alert window
	 * @param message the message that this alert window wants to present
	 */
	public void popUp(String title, String message, Stage primaryStage, WindowEvent event) {
		alertWin = new Stage();
        alertWin.initModality(Modality.APPLICATION_MODAL);
        alertWin.setTitle(title);
        
        cancel = new Button("Cancel");
        confirm = new Button("Confirm");
        cancel.setPrefWidth(65);
        confirm.setPrefWidth(65);
        cancel.setPrefHeight(30);
        confirm.setPrefHeight(30);

        buttonReaction(primaryStage, event);
        
        Label alertMessage = new Label(message);
        VBox.setMargin(alertMessage, new Insets(0,0,30,0));
        
        VBox vBox = new VBox(2);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefWidth(400);
        vBox.setPrefHeight(200);
        HBox hBox = new HBox(2);
        hBox.setAlignment(Pos.CENTER);
        HBox.setMargin(cancel,new Insets(10,10,0,0));
        HBox.setMargin(confirm,new Insets(10,0,0,10));
        vBox.getChildren().add(alertMessage);
        vBox.getChildren().add(hBox);
        hBox.getChildren().add(cancel);
        hBox.getChildren().add(confirm);
        
        alertWin.setScene(new Scene(vBox, 400, 200));
        alertWin.showAndWait();
	}

}
