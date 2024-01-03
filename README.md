![](https://raw.githubusercontent.com/suyons/StrangeChat/Main/image_icon/Qmark_bubble.png)
# StrangeChat
## 01 개요
1. 로컬 네트워크 내에서 이용 가능한 채팅 앱
2. 진행 기간<br>시작: 2023-12-17<br>종료: 2023-12-22
3. GitHub Repository<br>https://github.com/suyons/StrangeChat
4. 참여 인원

    | 이름 | 프로필 | 역할 |
    | :-: | :-: | --- |
    | <b>김수영</b> | [@suyons](https://github.com/suyons) | 1️⃣ 서버, 클라이언트 기본 기능<br>2️⃣ 소켓, 멀티스레드, 스트림 |
    | 박지은 | [@parkje72](https://github.com/parkje72) | 1️⃣ 서버 파일 입출력 기능 |
    | 이수진 | [@WGCAT](https://github.com/WGCAT) | 1️⃣ 클라이언트 GUI 구현 |
    | 황태윤 | [@taeyounh](https://github.com/taeyounh) | 1️⃣ 클라이언트 명령어 기능 |

## 02 사용 기술

| 구분 | 항목 | 목적 |
| :-: | :-: | --- |
| 서버 | Java | 대화 내용 저장 및 모든 클라이언트에게 broadcast |
| 클라이언트 | Java<br>Swing | (GUI) 서버로부터 전달받은 메시지의 내용을 표시 |
| 협업 | GitHub | git 기본적인 이용 방법 학습<br>- Repository 생성 및 clone, commit & push 등 |

## 03 구성도
**UML Class Diagram**
```mermaid
classDiagram
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
            +JButton getButton1()
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
```

## 04 구현 기능


## 05 코드 소개

## 06 돌아보며