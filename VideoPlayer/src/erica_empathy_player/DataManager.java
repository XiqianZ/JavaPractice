package erica_empathy_player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class DataManager {

	static private final String DIRECTORY = "EmpathyQuestionnaireData/";
	static private String fileName = "test.txt";
	
	static private Vector<QuestionnaireDataObject> data = new Vector<QuestionnaireDataObject>();
	
	
	static void addItem(QuestionnaireDataObject item) {
		data.add(item);
	}
	
	static void initialize() {
		data = new Vector<QuestionnaireDataObject>();
		String format = "yyyy-MM-dd-HH-mm";
		SimpleDateFormat df = new SimpleDateFormat(format);
		String dateString = df.format(new Date(System.currentTimeMillis()));
		fileName = dateString + ".txt";

		createFileIfNotExist(DIRECTORY + fileName);
	}
	
	static void initializeForDemo() {
		fileName = "test.txt";
		createFileIfNotExist(DIRECTORY + fileName);
	}
	
	static void createFileIfNotExist(String address){
		File file = new File(address);
		File parentFile = file.getParentFile();
		if(!parentFile.exists() && !parentFile.mkdir()){
			throw new IllegalStateException("Couldn't create dir: " + parentFile);
		} 
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	static void writeDataPlus(String data) {
		File directory = new File(DIRECTORY);
		boolean isCreated = directory.mkdir();
		if(isCreated) {
			System.out.println("Directory does not exist, create the directory");
		}
		File file = new File(DIRECTORY + fileName);
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
	
	static void writeDataToFile(EmpathyExpData data) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(DIRECTORY + fileName,false));
			bw.write("horror movie climax timing: " + data.getFearClimaxTiming()); bw.newLine(); 
			bw.write("heartful movie climax timing: " + data.getHeartClimaxTiming()); bw.newLine();
			bw.write("horror movie touch timing: " + data.getFearTouchTiming()); bw.newLine(); 
			bw.write("heartful movie touch timing: " + data.getHeartTouchTiming()); bw.newLine();
			bw.write("horror movie touch length: " + data.getFearTouchLength()); bw.newLine(); 
			bw.write("heartful movie touch length: " + data.getHeartTouchLength()); bw.newLine();
			
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	static void readData_PrintToConsole() {

		String line = "";

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(DIRECTORY + fileName), "UTF-8"));
			while((line = br.readLine()) != null){
				System.out.println("test: " + line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
