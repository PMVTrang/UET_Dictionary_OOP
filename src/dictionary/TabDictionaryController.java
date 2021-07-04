package dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;


public class TabDictionaryController implements Initializable {
    private static final String EV_DICT_DATA = "src\\data\\E_V.txt";
    private static final String VE_DICT_DATA = "src\\data\\V_E.txt";
    private Dictionary dictionary;
    ObservableList<String> evData;
    ObservableList<String> veData;

    @FXML private TextField searchField;

    /**
     * To update the word list view whenever a character is ADDED to the search field.
     * Return to the original word list when the search field is empty.
     * Open issue: 1. Didn't update when deleting character, unless delete all of them
     * 2. only check if a word contains the key, doesn't sort by the level of matching -> implement a comparator?
     */
    @FXML
    public void filterWordList(KeyEvent event) {
        String input = searchField.getText().trim();
        System.out.println("input: " + input);
        if (!input.equals("")) {
            ObservableList<String> filterList = FXCollections.observableArrayList();
            wordListView.getItems().forEach(word -> {
                if (word.contains(input)) {
                    filterList.add(word);
                }
            });
            FXCollections.sort(filterList);
            wordListView.setItems(filterList);
        } else {
            updateWordList();
        }
    }

    @FXML private RadioButton veButton;

    @FXML private ToggleGroup languageGroup;

    @FXML private RadioButton evButton;

    @FXML
    public void updateWordList() {
        if (evButton.isSelected()) {
            wordListView.setItems(evData);
        }
        else if (veButton.isSelected()) {
            wordListView.setItems(veData);
        }
        System.out.println("update word list");
    }

    @FXML private ListView<String> wordListView;

    @FXML
    void showDefinition() {
        String selectedWord = wordListView.getSelectionModel().getSelectedItem();
        String meaning = dictionary.getMeaningInWordForm(selectedWord).getMeaning();
        System.out.println("Selected word: " + selectedWord + "\n\t" + meaning);
        definitionView.getEngine().loadContent(meaning, "text/html");
    }

    @FXML private ImageView dictionaryIcon;

    @FXML private ImageView searchedHistoryIcon;

    @FXML private ImageView savedHistoryIcon;

    @FXML private ImageView addIcon;

    @FXML private WebView definitionView;

    @FXML private ImageView editIcon;

    @FXML private ImageView deleteIcon;

    @FXML private ImageView saveIcon;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dictionary = new Dictionary();
        veData = dictionary.getObListKeySet(VE_DICT_DATA);
        evData = dictionary.getObListKeySet(EV_DICT_DATA);
        //cai ghi dau tien se bi loi??

        wordListView.setItems(evData);
    }
}
