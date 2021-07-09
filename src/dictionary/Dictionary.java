package dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;

public class Dictionary {

    private static final String SPLITTING_PATTERN = "<html>";

    private Map<String, Word> vocabulary;
    private final List<String> searchedWords = new ArrayList<>();
    private final List<String> savedWords = new ArrayList<>();

    public Map<String, Word> getVocabulary() {
        return vocabulary;
    }

    //can a collection (big big parent class) truyen vao trong ham getObListKeySet duoi dang parameter la 1 ObList khong
    //ObList la grand child class cua collection
    public Collection<String> getKeySet() {
        return vocabulary.keySet();
    }

    public Word getMeaningInWordForm(String word) {
        return vocabulary.get(word);
    }

    public Dictionary(String filePath) {
        loadDataFromFile(filePath);
    }

    /**
     * To load data from give file into a set
     * @param filePath file path
     */
    private void loadDataFromFile(String filePath){
        try {
            vocabulary = new HashMap<>();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(SPLITTING_PATTERN);
                String word = parts[0];
                String definition = SPLITTING_PATTERN + parts[1];
                Word wordObj = new Word(word, definition);
                vocabulary.put(word, wordObj);
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
    public ObservableList<String> getObListKeySet() {
        ObservableList<String> allKeys = FXCollections.observableArrayList();
        allKeys.addAll(vocabulary.keySet());
        FXCollections.sort(allKeys);
        return allKeys;
    }

    public void addToSearchedList(String strWord) {
        //remove duplicate: if list doesnt contain the word, nothing happens
        searchedWords.remove(strWord);
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
        if (savedWords.contains(strWord)) {
            System.out.println(strWord + " is already saved");
            MessageBox.showWarning("", "", "This word is already saved");

        } else {
            savedWords.add(strWord);
            System.out.println(strWord + " is added to saved list");
        }
        System.out.println("saved list "+ savedWords);

    }

    public ObservableList<String> getObListSavedWordList() {
        ObservableList<String> saved = FXCollections.observableArrayList();
        saved.addAll(savedWords);
        return saved;
    }

    public void deleteWord(String word) {
        if(vocabulary.containsKey(word)) {
            vocabulary.remove(word);
            savedWords.remove(word); //no error if it is not saved
            searchedWords.remove(word);
        } else {
            MessageBox.showWarning("Not found", null, "You can't delete a not-yet-existed word");
        }
    }

    public void addWord(String word, String meaning) {
        if(vocabulary.containsKey(word)) {
            MessageBox.showWarning("Duplicate", null, "You entered an already-existed word");
        } else {
            Word wordMeaning = new Word(word, SPLITTING_PATTERN + meaning);
            vocabulary.put(word, wordMeaning);
        }
    }

    public void editWord(String word, String newMeaning) {
        Word chosenWord = getMeaningInWordForm(word);
        chosenWord.setMeaning(newMeaning);
    }

    public void saveToFile(String filePath) {
        try {
            File file=new File(filePath);
            FileWriter fileWriter= new FileWriter(file);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            for(String strWord : vocabulary.keySet()) {
                bufferWriter.write(strWord + getMeaningInWordForm(strWord).getMeaning() + "\n");
            }
            bufferWriter.flush();
            bufferWriter.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void print() {
        for(String word : vocabulary.keySet()) {
            System.out.println("word: " + word + "\n\tmeaning: " + vocabulary.get(word));
        }
    }

}

