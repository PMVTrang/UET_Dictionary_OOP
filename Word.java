public class Word {
    private String wordTarget;
    private String wordExplain;

    Word() {
/*        wordExplain = "";
        wordTarget = "";*/
    }

    Word(String word, String meaning) {
        wordTarget = word;
        wordExplain = meaning;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordTarget(String word) {
        wordTarget = word;
    }

    public void setWordExplain(String word) {
        wordExplain = word;
    }


}
