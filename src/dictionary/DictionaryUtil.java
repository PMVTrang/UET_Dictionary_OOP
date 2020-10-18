/*
package dictionary;

import java.io.*;

*/
/**
 * implement what can this dictionary do
 * - Read data from given dictionary dataset
 * - Search a given word's meaning
 * - [opt] Add, edit, delete a word (word or meaning)
 * - ... ?
 *//*

public class DictionaryUtil {

    private static final String DATA_SPLITTING_PATTERN = "<html>";

    Dictionary dictionary = new Dictionary();

    */
/**
     * Read data from file with given path into the arrayList of words
     * @param filePath path of the read to read
     * @throws IOException
     *//*

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
        for(Word word : dictionary.dict) {
            if ((word.getWord()).equals(givenWord)) {
                return word;
            }
        }
        return null;
    }

    */
/**
     * To edit a word: change its meaning
     * ? how about add a new meaning to it
     * ? how bout add a new word? stuck on how to insert the new word in the right place (alphabetically)
     *//*

    */
/*public void editWordFromFile(String givenWord, String newMeaning, String filePath) throws IOException {
        Word word = findWordFromFile(givenWord, filePath);
        String oldWord = word.getWord() + word.getMeaning();
        word.setMeaning(DATA_SPLITTING_PATTERN + newMeaning + "</html>");
        String newWord = word.getWord() + word.getMeaning();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        //https://javaconceptoftheday.com/modify-replace-specific-string-in-text-file-in-java/
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        String oldContent = "";
        while ((line = reader.readLine()) != null) {
            oldContent += oldContent + line;
        }
        System.out.println("old: " + oldContent);
        String newContent = oldContent.replaceAll(oldWord, newWord);
        System.out.println("new: " + newContent);
        *//*
*/
/*writer.write(newContent);
        writer.flush();
        reader.close();
        writer.close();*//*
*/
/*
    }*//*




}
*/
