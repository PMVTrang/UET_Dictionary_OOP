package dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * A dictionary is a set of Word instances. With available methods:
 * - Read data from given dictionary dataset
 * - Search a given word's meaning
 * - [opt] Add, edit, delete a word (word or meaning)
 * - ... ?
 */
public class Dictionary{
    private static final String EV_DICT_DATA = "src\\data\\E_V.txt";
    private static final String VE_DICT_DATA = "src\\data\\V_E.txt";
    List<Word> dict = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        DictionaryUtil util = new DictionaryUtil();
        util.readFromFile(VE_DICT_DATA);
        System.out.println(util.findWordFromFile("chim", EV_DICT_DATA));

    }



}

