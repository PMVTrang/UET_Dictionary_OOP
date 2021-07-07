package dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

//for next update: check on the showDefinition and the addWord method.
//Add the functionality of editing a word

public class TabDictionaryController implements Initializable {
    private static final String EV_DICT_DATA = "src\\data\\E_V.txt";
    private static final String VE_DICT_DATA = "src\\data\\V_E.txt";
    private Dictionary evDict;
    private Dictionary veDict;
//    ObservableList<String> evData;
//    ObservableList<String> veData;

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
        wordListView.setItems(getCurrentDictionary().getObListKeySet());
        /*if (evButton.isSelected()) {
            wordListView.setItems(dictionary.getObListKeySet(EV_DICT_DATA));
        }
        else if (veButton.isSelected()) {
            wordListView.setItems(dictionary.getObListKeySet(VE_DICT_DATA));
        }*/
        System.out.println("update word list");
    }

    @FXML private ListView<String> wordListView;

    /**
     * ! Definition still shows even the word is not selected anymore.
     * Should make it shows blank instead.
     */
    @FXML
    void showDefinition() {
        String selectedString = wordListView.getSelectionModel().getSelectedItem();
        Word selectedWord = getCurrentDictionary().getMeaningInWordForm(selectedString);
        if (selectedWord != null) {
            String meaning = selectedWord.getMeaning();
            System.out.println("Selected word: " + selectedString + "\n\t" + meaning);
            definitionView.getEngine().loadContent(meaning, "text/html");
            getCurrentDictionary().addToSearchedList(selectedString);
        } else {
            MessageBox.showWarning("", "", "This word is no longer available.");
        }

    }

    @FXML private ImageView dictionaryIcon;

    @FXML private ImageView searchedHistoryIcon;

    @FXML private ImageView savedHistoryIcon;


    @FXML
    void showSavedHistory(MouseEvent event) {
        wordListView.setItems(getCurrentDictionary().getObListSavedWordList());

    }

    @FXML
    void showSearchedHistory(MouseEvent event) {
        wordListView.setItems(getCurrentDictionary().getObListSearchedWordList());

    }

    @FXML private ImageView addIcon;

    /**
     * ! Cannot highlight the newly added word on the listview.
     * Consider changing from listview to ListCell?
     * @param event event
     */
    @FXML
    void addWord(MouseEvent event) {
        Pair<String, String> newWord = MessageBox.getInformation("Add a word", "New word",
                "","Its meaning", "");
        if(!newWord.getKey().equals("") && !newWord.getValue().equals("")) {
            getCurrentDictionary().addWord(newWord.getKey(), newWord.getValue());
            updateWordList();
            definitionView.getEngine().loadContent(newWord.getValue(), "text/html");

        } else {
            MessageBox.showWarning("Warning", "You dumb ignorant shit", "Fill in all fields");
        }

    }

    @FXML private WebView definitionView;

    @FXML private ImageView editIcon;
    //consider using HTMLEditor instead of WebView for the definition view?

    @FXML private ImageView deleteIcon;

    @FXML
    void deleteWord(MouseEvent event) {
        if (MessageBox.getConfirmation("", "", "Are you sure to delete this word?")) {
            getCurrentDictionary().deleteWord(wordListView.getSelectionModel().getSelectedItem());
            updateWordList();
        }
    }

    @FXML private ImageView saveIcon;

    @FXML
    void saveWord(MouseEvent event) {
        String selectedWord = wordListView.getSelectionModel().getSelectedItem();
        if (MessageBox.getConfirmation("",
                "", "Are you sure to save this word '" + selectedWord + "'?")) {
            System.out.println(selectedWord);
            getCurrentDictionary().addToSavedList(selectedWord);
        }


    }
    private Dictionary getCurrentDictionary() {
        if (evButton.isSelected()) {
            return evDict;
        }
        else {
            return veDict;
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        evDict = new Dictionary(EV_DICT_DATA);
        veDict = new Dictionary(VE_DICT_DATA);

        wordListView.setItems(evDict.getObListKeySet());
    }
}
