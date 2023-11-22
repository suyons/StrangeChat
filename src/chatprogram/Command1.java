package chatprogram;

import java.util.Scanner;

public class Command1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("대화 입력: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("/도움말")) {
                printHelp();
            } else if (input.equalsIgnoreCase("/종료")) {
                System.exit(0);
            } else if (input.equalsIgnoreCase("/회원가입")) {
                // 회원 가입을 처리하는 함수를 작성합니다.
            } else if (input.equalsIgnoreCase("/로그인")) {
                // 로그인을 처리하는 함수를 작성합니다.
            } else if (input.equalsIgnoreCase("/친구추가")) {
                // 친구 추가를 처리하는 함수를 작성합니다.
            } else if (input.equalsIgnoreCase("/채팅")) {
                // 채팅을 처리하는 함수를 작성합니다.
            } else if (input.equalsIgnoreCase("/정보")) {
                // 정보를 확인하는 함수를 작성합니다.
            } else if (input.equalsIgnoreCase("/설정")) {
                // 설정을 변경하는 함수를 작성합니다.
            } else if (input.equalsIgnoreCase("/나가기")) {
                System.exit(0);
            } else {
                System.out.println("입력하신 내용을 이해하지 못했습니다.");
            }
        }
    }
    
    void getUserInput(String content) {
    	if(content.equals("/종료"))
    	{
    	
    	}
    }

    public static void printHelp() {
        System.out.println("도움말:");
        System.out.println("- /도움말: 채팅 프로그램의 도움말을 출력합니다.");
        System.out.println("- /종료: 채팅 프로그램을 종료합니다.");
        System.out.println("- /회원가입: 채팅 프로그램에서 회원 가입을 할 수 있습니다.");
        System.out.println("- /로그인: 채팅 프로그램에서 로그인을 할 수 있습니다.");
        System.out.println("- /친구추가: 채팅 프로그램에서 친구를 추가할 수 있습니다.");
        System.out.println("- /채팅: 채팅 프로그램에서 다른 사용자와 채팅을 할 수 있습니다.");
        System.out.println("- /정보: 채팅 프로그램에서 자신의 정보를 확인할 수 있습니다.");
        System.out.println("- /설정: 채팅 프로그램의 설정을 변경할 수 있습니다.");
        System.out.println("- /나가기: 채팅 프로그램을 종료합니다.");
    }
}