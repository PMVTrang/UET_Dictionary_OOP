package dictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class TabTranslateController {
    @FXML
    private ToggleGroup languageFrom;

    @FXML
    private ToggleGroup languageTo;

    @FXML
    private TextField textToTranslate;

    @FXML
    private TextField textTranslated;

    @FXML
    private Button translateButton;

    @FXML
    void translateText(ActionEvent event) throws Exception {
        String sourceLang = ((RadioButton) languageFrom.getSelectedToggle()).getText();
        String targetLang = ((RadioButton) languageTo.getSelectedToggle()).getText();
        System.out.println("from " + sourceLang + " to " + targetLang);
        textTranslated.setText(GoogleTranslate.translate(sourceLang, targetLang, textToTranslate.getText()));
    }
}

