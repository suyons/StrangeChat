package chatprogram;

import java.util.Scanner;

public class CommandTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("채팅 입력: ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("!도움말")) {
                // 명령어 처리 함수 호출
                printHelpMessage();
            } else {
                // 일반 채팅 처리
                System.out.println("사용자: " + input);
            }
        }
    }

    public static void printHelpMessage() {
        System.out.println("채팅 프로그램 사용 방법");
        System.out.println("- !도움말: 채팅 프로그램의 사용 방법을 안내합니다.");
        // ...
    }
}