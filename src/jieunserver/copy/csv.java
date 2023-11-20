package jieunserver.copy;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class csv {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		
		//파일에 내용 입력
		try(FileWriter fw = new FileWriter("User.csv")){
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
