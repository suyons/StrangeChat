package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import common.*;

public class ClientApp {
    public static void main(String[] args) {
        try (Socket socket = new Socket(Constants.SERVER_ADDR, Constants.SERVER_PORT);
            /*
            * BufferedReader 보조 스트림으로 InputStreamReader 기반 스트림 감싸기는 매우 권장된다.
            * InputStreamReader 단독으로도 UTF-8 인코딩을 지원하므로 한글 표현에 지장은 없으나
            * 기반 스트림은 바이트 단위로 읽는 것에 비해 Buffered 스트림은 8KB 단위로 읽어 효율적이다.
            */
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            System.out.println("서버에 연결되었습니다.");

            System.out.println(in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
