package dictionary;

import java.io.*;

/**
 * implement what can this dictionary do
 * - Read data from given dictionary dataset
 * - Search a given word's meaning
 * - [opt] Add, edit, delete a word (word or meaning)
 * - ... ?
 */
public class DictionaryUtil {

    private static final String DATA_SPLITTING_PATTERN = "<html>";

    Dictionary dictionary = new Dictionary();

    /**
     * Read data from file with given path into the arrayList of words
     * @param filePath path of the read to read
     * @throws IOException
     */
    public void readFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while((line = reader.readLine()) != null) {
            String[] parts = line.split(DATA_SPLITTING_PATTERN);
            Word newWord = new Word(parts[0], DATA_SPLITTING_PATTERN + parts[1]);
            dictionary.dict.add(newWord);
        }
        reader.close();
    }

    public Word findWordFromFile(String givenWord, String filePath) throws IOException {
        BufferedWriter write = new BufferedWriter(new FileWriter(filePath));
        for(Word word : dictionary.dict) {
            if (word.getWord().equals(givenWord)) {
                return word;
            }
        }
        System.out.println("Not Found!");
        return null;
    }



}
