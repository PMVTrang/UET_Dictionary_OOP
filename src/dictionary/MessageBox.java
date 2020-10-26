package dictionary;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

//javafx alert

public class MessageBox {
    private static final double WIDTH = 250;
    private static final double HEIGHT = 150;
    private static Stage stage = new Stage();
    private static boolean confirmedYes;

    public static void showWarning(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * To receive user's confirmation (yes or no). Two buttons are OK (eqvl. to yes) and Cancel (eqvl. to no).
     * @param title the title of the dialog
     * @param message the message to show
     * @return true if button OK is clicked. Otherwise return no.
     * //java doesnt support function default parameter like in c++ (String header = null)
     */
    public static boolean getConfirmation(String title, String header, String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
//        return alert.getResult().toString();
        if(alert.getResult() == ButtonType.OK) {
            return true;
        }
        return false;

    }

    /**
     *Require user to fill information in given fields.
     * @param title The title of the dialog
     * @param field1
     * //Pair stores variable as a set of key and value. A map is a collection of pairs.
     * //GridPane helps create components that spans multiple rows and cols,
     * //stretch those components to fill in the vacancy
     */
    /* A gridPane layout
     *  .   0     |   1   |   2   |   3   |   4
     *  0 Field1  | a_box_hereeee
     *  1 Field2  | a_box_hereeee
     *  2
     *  3                  OkButton | CancelButton
     *  4
     */
    public static Pair<String, String> getInformation(String title, String field1, String data1, String field2, String data2) {
        Dialog dialog = new Dialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);

        ButtonType submitButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButton, cancelButton);

        GridPane infoGrid = new GridPane();
        infoGrid.setMinSize(WIDTH, HEIGHT);
        infoGrid.setHgap(10);
        infoGrid.setVgap(10);
        infoGrid.setPadding(new Insets(10));

        TextField textField1 = new TextField();
        if(data1.equals("")) {
            textField1.setPromptText("Enter " + field1);
        } else {
            textField1.setText(data1);
            textField1.setEditable(false);
        }
        infoGrid.addRow(0, new Label(field1 + ": "), textField1);
        TextField textField2 = new TextField();
        if(data2.equals("")) {
            textField2.setPromptText("Enter " + field2);
        } else {
            textField2.setText(data2);
            textField2.setEditable(false);
        }
        infoGrid.addRow(1, new Label(field2 + ": "), textField2);

        dialog.getDialogPane().setContent(infoGrid);

        /*Button submitButton = new Button("Submit");
        Button cancel = new Button("Cancel");

        submitButton.setOnAction(e -> {
            result.set(new Pair<String, String>(textField1.getText(), textField2.getText()));
        });
*/

        dialog.setResultConverter(clickedButton -> {
            if(clickedButton == submitButton) {
                return new Pair<String, String>(textField1.getText(), textField2.getText());
            } else {
            //create an empty pair because it would throw an exception if cancel or exit button is clicked
                return new Pair<String, String>("","");
            }
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        return result.get();
    }

    /*public static boolean getConfirmation(String title, String message) {
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
    }*/


}
