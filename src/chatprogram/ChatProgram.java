package chatprogram;

import java.util.ArrayList;
import java.util.List;

public class ChatProgram {

    private List<String> bannedWords;

    public ChatProgram() {
        bannedWords = new ArrayList<>();
        bannedWords.add("욕설");
        bannedWords.add("비속어");
    }

    public void sendMessage(String message) {
        for (String bannedWord : bannedWords) {
            if (message.contains(bannedWord)) {
                System.out.println("금지어를 사용하셨습니다. 채팅을 종료합니다.");
                return;
            }
        }
        System.out.println(message);
    }

    public static void main(String[] args) {
        ChatProgram chatProgram = new ChatProgram();
        chatProgram.sendMessage("안녕하세요!");
        chatProgram.sendMessage("욕설");
    }
}