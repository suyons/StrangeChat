package chatprogram;

import java.util.ArrayList;
import java.util.List;

public class ProgramEx {

    private List<String> commands;

    public ProgramEx() {
        commands = new ArrayList<>();
        commands.add("안녕");
        commands.add("안녕하세요");
        commands.add("안녕하세요!");
        commands.add("종료");
        commands.add("나가기");
    }

    public void sendMessage(String message) {
        for (String command : commands) {
            if (message.equals(command)) {
                if (command.equals("안녕") || command.equals("안녕하세요") || command.equals("안녕하세요!")) {
                    System.out.println("안녕하세요!");
                } else if (command.equals("종료") || command.equals("나가기")) {
                    System.out.println("채팅을 종료합니다.");
                    return;
                }
            }
        }
        System.out.println(message);
    }

    public static void main(String[] args) {
        ProgramEx chatProgram = new ProgramEx();
        chatProgram.sendMessage("안녕하세요");
        chatProgram.sendMessage("종료합니다");
    }
}