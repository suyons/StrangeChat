// 추후 추가기능 구현에 이용될 예정
// StringBuilder를 이용한 문자열 생성 예제 코드

package example;

import java.util.Random;

public class RandomString {
    public static String generate(int length) {
        // Define the character set from which the random string will be generated
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Create a StringBuilder to build the random string
        StringBuilder randomStringBuilder = new StringBuilder();

        // Create an instance of the Random class
        Random random = new Random();

        // Generate the random string by appending random characters from the characters
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomStringBuilder.append(randomChar);
        }

        return randomStringBuilder.toString();
    }
}
