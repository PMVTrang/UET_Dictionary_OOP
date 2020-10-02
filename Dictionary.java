import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dictionary {
    public List<Word> dict = new ArrayList<Word>();

    Dictionary() {}

    public void printAll() {
        System.out.printf("%-5s| %-15s| %s%n", "STT" , "Tu", "Giai nghia");
        try {
            for(Word i : dict) {
                System.out.printf("%-5d| %-15s| %s%n", dict.indexOf(i), i.getWord(), i.getDefinition());
            }
        } catch (Exception e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        FileReader fis = new FileReader("F:\\edoc\\jvaa\\DictionaryProjectOOP\\dictionaryData\\E_V.txt");
        BufferedReader br = new BufferedReader(fis);
        Dictionary dictionary = new Dictionary();
        String line;
        int i = 0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("<html>");
            String word = parts[0];
            String definition = "<html>" + parts[1];
            System.out.println(word);
            System.out.println("==> " + definition);
            Word wordObj = new Word(word, definition);
            dictionary.dict.add(wordObj);
        }

    }
}
