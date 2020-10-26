package dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.*;
import java.util.*;

public class Dictionary {

    private static final String TEST_DATA1 = "src\\data\\TestData1.txt";
    private static final String SPLITTING_PATTERN = "<html>";

    private Map<String, Word> allWords;
    private Stack<String> searchedWords = new Stack<String>();
//    private Vector<String> searchedWords = new Vector<String>();
    private Vector<String> savedWords = new Vector<String>();

    public Map<String, Word> getAllWords() {
        return allWords;
    }

    //can a collection (big big parent class) truyen vao trong ham getObListKeySet duoi dang parameter la 1 ObList khong
    //ObList la grand child class cua collection
    public Collection<String> getKeySet() {
        return allWords.keySet();
    }

    public Word getMeaningInWordForm(String word) {
        return allWords.get(word);
    }

    /**
     * To load data from give file into a set
     * @param filePath file path
     */
    public void loadDataFromFile(String filePath){
        try {
            allWords = new HashMap<>();
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

    /**
     * To get the keySet which is the word (the actual 'word' - its meaning is not included).
     * But why the data is all fucking messed up?
     * @return an ObservableList which can be added into a listView
     */
    public ObservableList<String> getObListKeySet(String filePath) {
        loadDataFromFile(filePath);
        ObservableList<String> allKeys = FXCollections.observableArrayList();
        allKeys.addAll(allWords.keySet());
        FXCollections.sort(allKeys);
        return allKeys;
    }

    public void addToSearchedList(String strWord) {
        searchedWords.add(strWord);
    }

    public ObservableList<String> getObListSearchedWordList() {
        ObservableList<String> searched = FXCollections.observableArrayList();
        searched.addAll(searchedWords);
        //show the latest added word
        FXCollections.reverse(searched);
        return searched;
    }

    public void addToSavedList(String strWord) {
        savedWords.add(strWord);
    }

    public ObservableList<String> getObListSavedWordList() {
        ObservableList<String> saved = FXCollections.observableArrayList();
        saved.addAll(savedWords);
        return saved;
    }

    public void deleteWord(String word) {
        if(allWords.containsKey(word)) {
            allWords.remove(word);
        } else {
            MessageBox.showWarning("Not found", null, "You can't delete a not-yet-existed word");
        }
    }

    public void addWord(String word, String meaning) {
        if(allWords.containsKey(word)) {
            MessageBox.showWarning("Duplicate", null, "You entered an already-existed word");
        } else {
            Word wordMeaning = new Word(word, SPLITTING_PATTERN + meaning);
            allWords.put(word, wordMeaning);
        }
    }

    public void editWord(String word, String newMeaning) {
        if(allWords.containsKey(word)) {
            Word chosenWord = getMeaningInWordForm(word);
            chosenWord.setMeaning(SPLITTING_PATTERN + newMeaning);
        } else {
            MessageBox.showWarning("Not found", null, "You entered an non-existed word");
        }
    }

    public void saveToFile(String filePath) {
        try {
            File file=new File(filePath);
            FileWriter fileWriter= new FileWriter(file);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            for(String strWord : allWords.keySet()) {
                bufferWriter.write(strWord + getMeaningInWordForm(strWord).getMeaning() + "\n");
            }
            bufferWriter.flush();
            bufferWriter.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void print() {
        for(String word : allWords.keySet()) {
            System.out.println("word: " + word + "\n\tmeaning: " + allWords.get(word));
        }
    }

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        dictionary.loadDataFromFile(TEST_DATA1);
        dictionary.editWord("Bullshit", "Vo van");
        dictionary.saveToFile(TEST_DATA1);
        dictionary.print();

    }
}

