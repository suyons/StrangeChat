package chatprogram;

import java.util.Arrays;
import java.util.List;

public class ProfanityChecker {
    private static final List<String> PROFANITIES = Arrays.asList("욕설", "비속어", "저속어");

    public static boolean containsProfanity(String text) {
        String[] words = text.split(" ");
        for (String word : words) {
            if (PROFANITIES.contains(word)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String text = "저는 욕설을 사용하지 않습니다.";
        boolean containsProfanity = containsProfanity(text);
        System.out.println(containsProfanity); // false
    }
}          