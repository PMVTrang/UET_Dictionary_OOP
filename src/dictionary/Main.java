package dictionary;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static final String EV_DICT_DATA = "src\\data\\E_V.txt";
    private static final String VE_DICT_DATA = "src\\data\\V_E.txt";
    /*private static final String TEST_DATA1 = "src\\data\\TestData1.txt";
    private static final String TEST_DATA2 = "src\\data\\TestData2.txt";*/
    private static final double MAIN_PREF_WIDTH = 1000;
    private static final double MAIN_PREF_HEIGHT = 700;
    private static final double SMALL_ICON_WIDTH = 45;
    private static final double SMALL_ICON_HEIGHT = 30;

    Dictionary dictionary = new Dictionary();
    private ListView<String> listView;
    private WebView definitionView;
    BorderPane mainPane;

    Stage mainStage;

    /*
     * The abstract start() method of Application class must be implemented by its subclasses.
     * Is called when the javafx app is started.
     * Na ná SDL with setTitle method and show() method to allow the window to appear.
     * @param primaryStage just an auto param of type Stage. nothing more special than other stage objects
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        listView = new ListView<>();
        definitionView = new WebView();
        Scene sceneDictionary;

//        ObservableList<String> allKeys = FXCollections.observableArrayList();
        ObservableList<String> EVdata;
        ObservableList<String> VEdata;

        EVdata = dictionary.getObListKeySet(EV_DICT_DATA);
        VEdata = dictionary.getObListKeySet(VE_DICT_DATA);

        //Create the top bar
        HBox topBar = new HBox();
        MenuBar menuBar = new MenuBar();
        VBox leftBar = new VBox();
        VBox center = new VBox();
        HBox bottomBar = new HBox();

        Menu mDictionary = new Menu("Dictionary");
        RadioMenuItem miEVdict = new RadioMenuItem(
                "E-V",
                new ImageView(new Image("file:src\\graphic\\E-V_icon.png",
                        SMALL_ICON_WIDTH, SMALL_ICON_HEIGHT, true, true)));
        miEVdict.setOnAction(e -> {
            listView.setItems(EVdata);
            mainPane.setLeft(leftBar);
            mainPane.setCenter(center);
        });

        RadioMenuItem miVEdict = new RadioMenuItem(
                "V-E",
                new ImageView(new Image("file:src\\graphic\\V-E_icon.png",
                        SMALL_ICON_WIDTH, SMALL_ICON_HEIGHT, true, true)));
        miVEdict.setOnAction(e -> {
            listView.setItems(VEdata);
            mainPane.setLeft(leftBar);
            mainPane.setCenter(center);
        });

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().add(miEVdict);
        toggleGroup.getToggles().add(miVEdict);
        mDictionary.getItems().addAll(miEVdict, miVEdict);

//cant set on action on a menu
        Menu mTranslate = new Menu("Translate");
//        Menu mThesaurus = new Menu("Thesaurus");
//        Menu mOther = new Menu("Other");
//        Menu mLogin = new Menu("Login");
        MenuItem miGoogleTranslate = new MenuItem("Google Translate");
        mTranslate.getItems().add(miGoogleTranslate);

        menuBar.getMenus().addAll(mDictionary, mTranslate);

        topBar.getChildren().add(menuBar);

        //Create the left bar which show words and search bar
        leftBar.setPadding(new Insets(10, 0, 10, 0));
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");

        HBox searchBar = new HBox();
        searchBar.setSpacing(5);
//        Button backButton = new Button("Back");
//        Button nextButton = new Button("Next");
//        Button onScreenKeyboard = new Button("osk");
        searchBar.getChildren().addAll(searchField);

        HBox utilBar = new HBox(5);
        Button btnWordList = new Button("Words");
        Button btnSavedList = new Button("Saved");
        Button btnSearchedList = new Button("History");
        utilBar.getChildren().addAll(btnWordList, btnSavedList, btnSearchedList);
        btnWordList.setOnAction(event -> {
//            listView.setItems(dictionary.getObListKeySet(EV_DICT_DATA));
            if(miEVdict.isSelected()) {
                listView.setItems(EVdata);
            } else if (miVEdict.isSelected()) {
                listView.setItems(VEdata);
            }
        });
        btnSearchedList.setOnAction(event -> {
            listView.setItems(dictionary.getObListSearchedWordList());
        });
        btnSavedList.setOnAction(event -> {
            listView.setItems(dictionary.getObListSavedWordList());
        });

        leftBar.getChildren().addAll(searchBar, listView, utilBar);
        leftBar.setMargin(listView, new Insets(10));
        VBox.setVgrow(listView, Priority.ALWAYS);

//Create the center region showing the definition;
        HBox wordHBox = new HBox();
    //I'm really stuck on naming those stupid variables
        Label bicara = new Label();
        //bicara.setText(dictionary.getObListKeySet().toArray()[0].toString().toUpperCase());
        Image imgSpeaker = new Image("file:src\\graphic\\speaker_icon.png",
                SMALL_ICON_WIDTH, SMALL_ICON_HEIGHT, true, true);
        ImageView imgViewSpeaker = new ImageView(imgSpeaker);
//set action to bicara because imgSpeaker is now linked to bicara???
        bicara.setOnMouseClicked(event -> {
            API.speak(bicara.getText());
        });
        bicara.setGraphic(imgViewSpeaker);
//why they never say anything even when the photo url is incorrect???
        ImageView imgViewSaver = new ImageView(new Image("file:src\\graphic\\saveStar_icon.png",
                SMALL_ICON_WIDTH, SMALL_ICON_HEIGHT, true, true));
        imgViewSaver.setOnMouseClicked(event -> {
            String chosenWord = bicara.getText();
            dictionary.addToSavedList(chosenWord);
        });
        ImageView imgViewEditor = new ImageView(new Image("file:src\\graphic\\pencilEdit_icon.png",
                SMALL_ICON_WIDTH, SMALL_ICON_HEIGHT, true, true));
        imgViewEditor.setOnMouseClicked(event -> {
            editUtil(bicara.getText());
//            definitionView.getEngine().reload();
        });
        ImageView imgViewDeleter = new ImageView(new Image("file:src\\graphic\\delete_icon.png",
                SMALL_ICON_WIDTH, SMALL_ICON_HEIGHT, true, true));
        imgViewDeleter.setOnMouseClicked(e ->{
            deleteUtil(bicara.getText());
//this is cheating because i dont have time to work on reload the listView
//..after delete a word without effecting the data yet.
//..one solution might be changing the way listView is loaded
            if(miEVdict.isSelected()) {
                dictionary.saveToFile(EV_DICT_DATA);
                listView.setItems(dictionary.getObListKeySet(EV_DICT_DATA));
            } else if(miVEdict.isSelected()) {
                dictionary.saveToFile(VE_DICT_DATA);
                listView.setItems(dictionary.getObListKeySet(VE_DICT_DATA));
            }
            bicara.setText("");
            definitionView.getEngine().loadContent("");

        });
        ImageView imgViewAdder = new ImageView(new Image("file:src\\graphic\\add_icon.png",
                SMALL_ICON_WIDTH, SMALL_ICON_HEIGHT, true, true));
        imgViewAdder.setOnMouseClicked(e ->{
            String newWord = addWordUtil();
//checking which dictionary is one everytime something happens is frustrating
            if(miEVdict.isSelected()) {
                dictionary.saveToFile(EV_DICT_DATA);
                listView.setItems(dictionary.getObListKeySet(EV_DICT_DATA));
            } else if(miVEdict.isSelected()) {
                dictionary.saveToFile(VE_DICT_DATA);
                listView.setItems(dictionary.getObListKeySet(VE_DICT_DATA));
            }
            bicara.setText(newWord);
        });
        Region spacerHBox = new Region();
        HBox.setHgrow(spacerHBox, Priority.ALWAYS);
        wordHBox.getChildren().addAll(bicara, spacerHBox, imgViewAdder, imgViewDeleter, imgViewEditor, imgViewSaver);
        center.getChildren().addAll(wordHBox, definitionView);

        /*
         * update the data immediately when any change occurs without waiting for a button confirmation
         * observable: the property whose value has changed
         * oldValue: the previous value of the property
         * newValue: the new value of the property
         * This is a lambda expression to implement changeListener() - functional interface
         * (differ from invalidationListener() which needs only 1 arg: observable)
         */
        listView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    bicara.setText(newValue.trim());
                    String meaning = dictionary.getMeaningInWordForm(newValue.trim()).getMeaning();
                    definitionView.getEngine().loadContent(meaning, "text/html");
                    dictionary.addToSearchedList(newValue);
                });
        searchField.setOnAction(event -> {
            String requestedWord = searchField.getText().trim();
            bicara.setText(requestedWord);
            dictionary.addToSearchedList(requestedWord);
            String meaning = dictionary.getMeaningInWordForm(requestedWord).getMeaning();
            definitionView.getEngine().loadContent(meaning, "text/html");
        });

        //Create the bottom bar
        Label btm = new Label("A dictionary by MT");
        bottomBar.getChildren().add(btm);
        bottomBar.setAlignment(Pos.BASELINE_RIGHT);

        mainPane = new BorderPane();
        mainPane.setPrefSize(MAIN_PREF_WIDTH, MAIN_PREF_HEIGHT);
        mainPane.setTop(topBar);
        mainPane.setLeft(leftBar);
        mainPane.setCenter(center);

        mainPane.setBottom(bottomBar);
        sceneDictionary = new Scene(mainPane);

//create the scene for the translate mode

        TextArea textToTranslate = new TextArea();
        textToTranslate.setPromptText("Enter something here...");
        Button translateButton = new Button("Translate");
        TextArea textTranslated = new TextArea();
        ChoiceBox<String> translateFrom = new ChoiceBox<String>();
        translateFrom.getItems().addAll(API.languageCodes.keySet());
//        translateFrom.setValue((String) API.languageCodes.keySet().toArray()[0]);
        translateFrom.setValue("Detect Language");
        ChoiceBox<String> translateTo = new ChoiceBox<String>();
        translateTo.getItems().addAll(API.languageCodes.keySet());
        translateTo.setValue("Vietnamese");

        translateButton.setOnAction(event -> {
            try{
                String text = textToTranslate.getText();
                textTranslated.setText(API.translate(translateFrom.getValue(), translateTo.getValue(), text));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        VBox vBoxTranslate = new VBox(35, translateFrom, textToTranslate, translateButton, translateTo, textTranslated);
        vBoxTranslate.setPadding(new Insets(25));
//        sceneTranslate = new Scene(vBoxTranslate);
        miGoogleTranslate.setOnAction(event -> {
//            System.err.println("translate is clicked");
            mainPane.setLeft(null);
            mainPane.setCenter(vBoxTranslate);
        });

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            if(MessageBox.getConfirmation("Confirm Exit", null,"Do you want to save changes?")) {
                /*if(miEVdict.isSelected()) {
                    dictionary.saveToFile(EV_DICT_DATA);
                } else if(miVEdict.isSelected()) {
                    dictionary.saveToFile(VE_DICT_DATA);
                }*/
                primaryStage.close();
            }
        });
        primaryStage.setScene(sceneDictionary);
        primaryStage.show();

    }

    private void deleteUtil(String wordToDelete) {
        if(MessageBox.getConfirmation("Confirm delete", null,
                "Are you sure to delete this word?")) {
            dictionary.deleteWord(wordToDelete);
        }
    }

    //return the newly added word
    private String addWordUtil() {
        Pair<String, String> result = MessageBox.getInformation("Add a word", "New word",
                "","Its meaning", "");
        if(!result.getKey().equals("") && !result.getValue().equals("")) {
            dictionary.addWord(result.getKey(), result.getValue());
            definitionView.getEngine().loadContent(result.getValue(), "text/html");
            return result.getKey();
        } else {
            return null;
        }
    }

    private void editUtil(String wordToEdit) {
        Pair<String, String> result = MessageBox.getInformation("Edit a word", "Word",
                wordToEdit,"New meaning", "");
        if(!result.getKey().equals("") && !result.getValue().equals("")) {
            dictionary.editWord(result.getKey(), result.getValue());
            definitionView.getEngine().loadContent(result.getValue(), "text/html");
        } else {
            System.err.println("nothing happened");
        }
    }

}


