package dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Đọc file fxml và vẽ giao diện.
            Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));

            primaryStage.setTitle("Dictionary");
            primaryStage.setScene(new Scene(root));
            primaryStage.setMinWidth(620);
            primaryStage.setMinHeight(465);

            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
