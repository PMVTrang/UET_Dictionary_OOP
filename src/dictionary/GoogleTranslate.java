package dictionary;

//https://stackoverflow.com/questions/8147284/how-to-use-google-translate-api-in-my-java-application

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class GoogleTranslate {

    public static Map<String, String> languageCodes = new HashMap<>();
    static {
        languageCodes.put("Detect Language", "");
        languageCodes.put("English", "en");
        languageCodes.put("Italian", "it");
        languageCodes.put("Thai", "th");
        languageCodes.put("Vietnamese", "vi");
    }

    public static String translate(String sourceLang, String targetLang, String text) throws Exception {
        String urlStr = "https://script.google.com/macros/s/AKfycbwHIar1jickjS5LTHsj43ZxRS9lSvMV6rCucVWrgAIRm5SgLwQ/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + languageCodes.get(targetLang) +
                "&source=" + languageCodes.get(sourceLang);
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        System.out.println();
        return response.toString();
    }

    //https://www.geeksforgeeks.org/converting-text-speech-java/
    //cai nay chi speak duoc the first word typed into the text field
    //chua implement duoc click vao thi noi
    public static void speak (String string) {
        try {
            System.setProperty(
                    "freetts.voices", "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");
            Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
//            synthesizer.resume();
            synthesizer.speakPlainText(string, null);
//            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
//            synthesizer.deallocate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        speak("Hello everybody i like to eat shit.");
        speak("Would you like to share some of your shit?");
    }

}
