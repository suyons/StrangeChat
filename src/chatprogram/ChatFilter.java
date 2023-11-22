package chatprogram;

import java.util.HashSet;
import java.util.Set;

public class ChatFilter {
    private Set<String> bannedWords;

    public ChatFilter() {
        bannedWords = new HashSet<>();
        // 금지어를 추가합니다.
        bannedWords.add("욕설");
        bannedWords.add("비속어");
        bannedWords.add("차별적인 발언");
        // ...
    }

    public boolean isBannedWord(String word) {
        return bannedWords.contains(word);
        
        
        
        
    }
    
}