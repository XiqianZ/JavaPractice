package erica_empathy_player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class DataManager {

	static private final String DIRECTORY = "data/";
	static private final String FILE_NAME = "data.txt";
	static private final String TEST_FILE_NAME = "testFile.txt";

	public static void main(String args[]) {
//		writeDataPlus("a test data");
		readData_PrintToConsole();
	}

	static void writeData_Direct() throws IOException{

		BufferedWriter bw = new BufferedWriter(new FileWriter(DIRECTORY + TEST_FILE_NAME ,false));

		bw.write("test line");
		bw.newLine();
		bw.flush();
		bw.close();
	}

	static void writeDataPlus(String data) {
		File directory = new File(DIRECTORY);
		boolean isCreated = directory.mkdir();
		if(isCreated) {
			System.out.println("Directory does not exist, create the directory");
		}
		File file = new File(DIRECTORY + FILE_NAME);
		try {
			FileWriter fw = new FileWriter(file.getPath(), false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(data);
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void readData_PrintToConsole() {

		String line = "";

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(DIRECTORY + FILE_NAME), "UTF-8"));
			while((line = br.readLine()) != null){
				System.out.println(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
