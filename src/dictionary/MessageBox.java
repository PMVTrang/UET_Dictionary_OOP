package dictionary;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MessageBox {
    private static final double WIDTH = 250;
    private static final double HEIGHT = 150;
    private static Stage stage = new Stage();
    private static boolean confirmedYes;
    public static void showWarning(String title, String message) {
        stage = new Stage();
        stage.setTitle(title);
        Label msg = new Label(message);
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            stage.close();
        });
        //using vbox.setAlignment instead di
        BorderPane bp = new BorderPane();
        bp.setPrefSize(250, 100);
        bp.setMinSize(200, 100);
        bp.setCenter(msg);
        bp.setBottom(okButton);
        BorderPane.setMargin(okButton, new Insets(0, 100, 10, 100));
        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.show();
    }

    public static boolean getConfirmation(String title, String message) {
        confirmedYes = false;
        stage.setTitle(title);
//        Label msg = new Label(message);
        Button yesButton = new Button("Yes");
        yesButton.setOnAction(event -> {
            confirmedYes = true;
            stage.close();
        });
        Button noButton = new Button("No");
        noButton.setOnAction(event -> {
            confirmedYes = false;
            stage.close();
        });
        HBox buttons = new HBox(20, yesButton, noButton);
        VBox vbox = new VBox(30, new Label(message), buttons);
        vbox.setMinSize(WIDTH, HEIGHT);
        Scene scene = new Scene(vbox);

        stage.setScene(scene);
        stage.showAndWait();    //this makes a hugh different from show()

        return confirmedYes;
    }

}
