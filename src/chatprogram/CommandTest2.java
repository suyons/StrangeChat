package chatprogram;

import java.util.Scanner;

public class CommandTest2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("대화 입력: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("!도움말")) {
                printHelp();
            } else if (input.equalsIgnoreCase("!종료")) {
                System.exit(0);
            } else {
                System.out.println("입력하신 내용을 이해하지 못했습니다.");
            }
        }
    }

    public static void printHelp() {
        System.out.println("도움말:");
        System.out.println("- !도움말: 채팅 프로그램의 도움말을 출력합니다.");
        System.out.println("- !종료: 채팅 프로그램을 종료합니다.");
    }
}
