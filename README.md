![](https://raw.githubusercontent.com/suyons/StrangeChat/Main/image_icon/Qmark_bubble.png)
# StrangeChat
## 01 개요
1. 로컬 네트워크 내에서 이용 가능한 채팅 앱
2. 진행 기간
   * 시작: 2023-11-17
   * 종료: 2023-11-22
3. Blog
   * https://velog.io/@syoungs/project-1
4. 참여 인원

    | 이름 | 프로필 | 역할 |
    | :-: | :-: | --- |
    | <b>김수영</b> | [@suyons](https://github.com/suyons) | 1️⃣ 서버, 클라이언트 기본 기능<br>2️⃣ 소켓, 멀티스레드, 스트림 |
    | 박지은 | [@parkje72](https://github.com/parkje72) | 1️⃣ 서버 파일 입출력 기능 |
    | 이수진 | [@WGCAT](https://github.com/WGCAT) | 1️⃣ 클라이언트 GUI 구현 |
    | 황태윤 | [@taeyounh](https://github.com/taeyounh) | 1️⃣ 클라이언트 명령어 기능 |

5. 개발 목적
   * Java 기본 이론 학습 이후 적용 훈련
   * 수업에서 학습한 내용
     * 컬렉션 프레임워크: HashMap, List
     * 스트림: BufferedReader, FileReader
     * 다형성: Overloading, Overriding
   * 추가로 학습한 내용
     * Git: clone, pull, commit, push
     * GUI: Swing library
     * TCP/IP 소켓 통신: ServerSocket

## 02 사용 기술

| 구분 | 항목 | 목적 |
| :-: | :-: | --- |
| 서버 | Java | 클라이언트에서 작성한 대화 내용 입출력, 전송 |
| 클라이언트 | Java<br>Swing | GUI, 전송받은 메시지 표시 및 새 메시지 작성 |
| 협업 | GitHub | 개인별 Branch를 생성하여 개별 작업 내역 관리 |

## 03 구성도
**UML Class Diagram**
![](https://mermaid.ink/img/pako:eNqNVm1v2jAQ_itW1A9Uo0j7SqdJFJhGRRgitNMmpMpNDrCa2JHtwKqq_Pb5JQk2SbrlQ3y55-5yOd898VsQswSCYRCnWIgJwTuOsw0d77FEXwYDFAE_AF_vOeDEUWvBQq4yelwpM617EMBb_Gu1639Wnv3HjAqJqRQ2yJHQ3TglQOUozy9R7X-p8_Lzkc6XGHMbv7JpxvIQx6UW0WDw1XPxEb8eFGcgchwDilmWMYreNhSpy2yGiVJp9PUpZXSHJFE-Eme5A0SSqwqpIFSqGjmADtG7blruWcFDQgsJPT9oi61kVuhdI-eydu9uvmYj35oBSD5KEt7UF8p-oSrgIDpEz3O7bnnReeNa3hZNV4_T1dNoMlldOSihsoKWP1brqxbHX9F6Gj4tRuG0BR1NwtnCBVVG794Omv70d9BvXDfbm_slppCiXN_HCgfeAUaskHsXW8Mf-Y1AmiBZSS58V0ipOunZLJ8vHUeq8YyfFlwwijlLU_1eJGqxaXCHeYkryYEjFr-ARMIsjv6u2G6BQ1JODS9Hr8aXqrjyJyfq-9HRLA54YCRBAqStj9vHNWSq43W4QXAsCaNL4FvGM0h6I_M8PegdgpY4NAlBCLwDL1RVyx1IK_loXU-FV3IzFZOkrkoTyjBRITv6SRimaDJC5OnNR4w4x69zIuTJpZcTkmbVgNPQN9-x2Ic4P9nG7iO7nsw4Kn2b6Zy5hrHKwjc8dWWgK1MnUX-qO1fK4qHkgYvBd-OfjXUJxpbnRG_uc5frMVMdgyXjJneTSKXx0jjZdkkSwzyGwXQduk28HKua1FTW6mdI2JC5rly3ic_EdeyS1T0_E-2IiTS83EAoHC3rfFBSm5CxumeEqiH5L1thUm2vQmem1nUO246EPHZ3O8hr85JjbDD7cPsvqrn9kGtctExsPJ9NF-un2dJtVSejXksWjcHmBW2y1TNnOImxkKHYOe3QIKM9Oy45HAgrhLZs_QHWx5C3Bt3gA5gZEYcWLlKg7uIL0OtwjX08B1X4Rku3bOPFb6-D94J-kAFX6kSdRI35JpB7UEeDYKjEBPOXTWDtcKHOI680DoZbnAroB0WeYAnlyfVCO02IGvhgKHmhdGCewvK8q5d-oH6xvxmr_N7_AqGhf3c?type=png)

## 04 구현 기능
### 클라이언트 GUI
![](https://raw.githubusercontent.com/suyons/StrangeChat/Main/image_icon/client_screenshot.png)

### 서버 CSV 파일 입출력
![](https://raw.githubusercontent.com/suyons/StrangeChat/Main/image_icon/screenshot_server_csv.png)

<!-- Mermaid class diagram codes below -->
<!-- ```mermaid
classDiagram
Chat <.. ServerThread
Chat <.. ChatServer
Chat <.. CSVReader
User <.. ServerThread
User <.. ChatServer
User <.. CSVReader
Constants <.. SwingClientApp
Constants <.. User
Constants <.. ChatServer
Constants <.. CSVReader
Constants <.. ServerApp
CSVReader <.. ChatServer
CSVReader <.. ServerApp
ServerApp ..> ChatServer
ServerApp ..> ServerThread
namespace common {
    class Chat {
        +long timestamp
        +String content
        +Chat()
        +String hourMinute(long timestamp)
        +String toString()            
    }
    class User {
        +String ipAddr
        +String userName
        +User(String ipAddr)
    }
    class Constants {
        +String SERVER_ADDR$
        +int SERVER_PORT$
        +String SYSTEM_NAME$
        +String ADMIN_NAME$
    }
}
namespace client {
    class SwingClientApp {
        -JPanel panelCenter
        -JPanel panelSouth
        -JTextField textField
        -JButton button1
        -JTextArea textArea
        -JScrollPane scrollPane
        -JScrollBar scrollBar
        -Socket socket
        -BufferedReader reader
        -PrintWriter writer
        -void setCenter()
        -void setSouth()
        +void actionPerformed(ActionEvent e)
        -void sendMessage()
        +JButton getButton()
        +JTextArea getTextArea()
        +void setSocket()
        +void main()$
    }
}
namespace server {
    class ChatServer {
        -ArrayList~ServerThread~ threadList$
        -HashMap~String, String~ userMap$
        -HashMap~Long, String~ chatMap$
        ~ArrayList~ServerThread~ getThreadList()$
        +String getUserName(String ipAddr)$
        ~String getChatContents(Long timestamp)$
        ~Iterator~Long~ getIterator()$
        ~void addUser(User user)$
        ~void addUser(String ipAddr, String userName)$
        ~void addChat(Chat chat)$
        ~void addChat(long timestamp, String content)$
        ~Chat waiting()$
        ~Chat newClient(String ipAddr)$
        ~Chat clientJoined(String ipAddr)$
        ~Chat clientsChat(String ipAddr, String content)$
        ~Chat clientLeft(String ipAddr)$
    }
    class ServerThread {
        -Socket clientSocket;
        -BufferedReader reader;
        -PrintWriter writer;
        -String CLIENT_IP
        +ServerThread(Socket clientSocket)
        +void run()
        -void broadcastMsg(Chat chat)
        -void showPreviousMsg()
    }
    class CSVReader {
        +void saveChatCsv()
        +void saveUserCsv()
        ~void addUserCsv(User user)$
        ~void addChatCsv(Chat chat)$
    }
    class ServerApp {
        +void main()$
    }
}
``` -->