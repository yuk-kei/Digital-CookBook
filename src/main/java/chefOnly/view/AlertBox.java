package chefOnly.view;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	public static boolean buttonIsClicked = false;
	public static Label label = new Label();

    public void display(){
        
        Stage window = new Stage();
        window.setTitle("tip");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(300);
        window.setMinHeight(150);
        window.setResizable(false);
    

        Button button = new Button("yes");
        button.setOnAction(e -> {buttonIsClicked = true; window.close();});
        Button button1 = new Button("no");
        button1.setOnAction(e1 -> window.close());
    
        HBox a = new HBox(10);
        a.getChildren().addAll(button,button1);
        a.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label ,a);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}