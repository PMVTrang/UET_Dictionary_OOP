import java.io.IOException;

public class DictionaryCommandLine {
    DictionaryManagement dm = new DictionaryManagement();
    public void dictionaryBasic() {
        dm.insertFromCommandline();
        dm.showAllWords();
    }

    /*public void dictionaryFile() {
        dm.insertFromFile();
//        dm.showAllWords();
    }*/
}
