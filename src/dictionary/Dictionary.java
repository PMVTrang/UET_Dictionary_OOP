package dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * A dictionary is a set of Word instances. With available GUI:
 * A drop down box: containing languages to be translated to and from <...>: still working on it
 * A translate mode: translating a phrase, sentences...                     : havent done shit
 * Thesaurus:                                                               : haven't done shit
 * Login
 * Edit word list
 * Click on each word given in the list: the corresponding meaning would be shown in the center region  <done>
 * Search for a word in a search bar and show its meaning                   <done>
 * Optimizing search algorithm
 * Recommend word while typing in the search bar
 * Pronunciation
 * Color and stuff
 * animation with css, html, ...
 * SQL?
 * Another feature: search history?
 *
 * - ... ?
*/

public class Dictionary extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static final String EV_DICT_DATA = "src\\data\\E_V.txt";
    private static final String VE_DICT_DATA = "src\\data\\V_E.txt";
    private static final String TEST_DATA = "src\\data\\TestData.txt";
    private static final String SPLITTING_PATTERN = "<html>";
    private static final double MAIN_PREF_WIDTH = 1000;
    private static final double MAIN_PREF_HEIGHT = 700;



    private Map<String, Word> allWords = new HashMap<>();
    private ListView<String> listView;
    private WebView definitionView;
    private String currentLanguage;

    Stage mainStage;

    /*
     * The abstract start() method of Application class must be implemented by its subclasses.
     * Is called when the javafx app is started.
     * Na ná SDL with setTitle method and show() method to allow the window to appear.
     * @param primaryStage just an auto param of type Stage. nothing more special than other stage objects
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
//        definitionView = new WebView();

        //Create the top bar
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(10));
        ChoiceBox<String> transLanguage = new ChoiceBox<String>();
        transLanguage.getItems().addAll("E-V", "V-E");
        transLanguage.setValue("E-V");
//        currentLanguage = VE_DICT_DATA;
        loadDataFromFile(EV_DICT_DATA);

        Button translateMode = new Button("Translate");
        Button thesaurusMode = new Button("Thesaurus");
        Button loginButton = new Button("Login");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(transLanguage, translateMode, thesaurusMode, spacer, loginButton);

        //Create the left bar which show words and search bar
        VBox leftBar = new VBox();
        leftBar.setPadding(new Insets(10));
        leftBar.setPrefHeight(Double.MAX_VALUE);
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search...");
        searchBar.setOnAction(event -> {
            String requestedWord = searchBar.getText();
            for(String word : allWords.keySet()) {
                if(word.equals(requestedWord)) {
                    String meaning = allWords.get(word).getMeaning();
                    definitionView.getEngine().loadContent(meaning, "text/html");

                }
            }
        });

        listView = new ListView(loadIntoListView());
        leftBar.getChildren().addAll(searchBar, listView);



        /**
         * update the data immediately when any change occurs without waiting for a button confirmation
         * observable: the property whose value has changed
         * oldValue: the previous value of the property
         * newValue: the new value of the property
         */
        listView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    Word selectedWord = allWords.get(newValue.trim());
                    String meaning = selectedWord.getMeaning();
                    definitionView.getEngine().loadContent(meaning, "text/html");

                });

        //Create the center region showing the definition;
        definitionView = new WebView();
//        definitionView.getEngine().loadContent();
        VBox center = new VBox(definitionView);



        //Create the bottom bar
        HBox bottomBar = new HBox();


        BorderPane mainPane = new BorderPane();
        mainPane.setPrefSize(MAIN_PREF_WIDTH, MAIN_PREF_HEIGHT);
        mainPane.setTop(topBar);
        mainPane.setLeft(leftBar);
        mainPane.setCenter(center);
        mainPane.setBottom(bottomBar);
        Scene mainScene = new Scene(mainPane);

        primaryStage.setOnCloseRequest(event -> {
            if(MessageBox.getConfirmation("Confirm Exit", "Are you sure to exit?")) {
                primaryStage.close();
            }
        });
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }

    /**
     * To get data from given file into an observableList to pass to listView
     * @return an observableList
     */
    public ObservableList<String> loadIntoListView() {
        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll(allWords.keySet());
        FXCollections.sort(data);

        return data;
    }

    /**
     * To load data from give file into a set
     * @param filePath file path
     */
    public void loadDataFromFile(String filePath){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(SPLITTING_PATTERN);
                String word = parts[0];
                String definition = SPLITTING_PATTERN + parts[1];
                Word wordObj = new Word(word, definition);
                allWords.put(word, wordObj);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


