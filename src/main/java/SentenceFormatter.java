import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceFormatter {
    public static String removeUrls(String sentence){
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sentence);
        int i = 0;
        while (m.find()) {
            sentence = sentence.replaceAll(m.group(i),"").trim();
            i++;
        }
        return sentence;
    }

    public static String removeMentions(String sentence){
        return sentence.replaceAll("@.*?\\s", "");
    }

    public static String trim(String sentence){
        return sentence.trim();
    }
}
