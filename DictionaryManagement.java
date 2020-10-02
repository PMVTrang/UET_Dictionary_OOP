import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class DictionaryManagement {
    Dictionary dictionary = new Dictionary();
    public void insertFromCommandline() {
        Scanner scan = new Scanner(System.in);
        System.out.print("The number of words to insert: ");
        int numOfWord = scan.nextInt();
        scan.nextLine();    //to read the newline
        for (int i = 0; i < numOfWord; i++) {
            System.out.print("Word: ");
            String word = scan.nextLine();
            System.out.print("Meaning: ");
            String meaning = scan.nextLine();
            Word newWord = new Word(word, meaning);
            dictionary.dict.add(newWord);
        }
    }



    public void showAllWords() {
        System.out.println("Every word: ");
        //System.out.println(dictionary.dict[0].printPair());
        dictionary.printAll();
    }



}
