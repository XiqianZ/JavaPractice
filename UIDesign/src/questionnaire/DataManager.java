package questionnaire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Vector;

public class DataManager {
	
	static private String DATA_FOLDER = "TouchQuestionnaireData/";
	static private String dataFileName = "a.txt";
	static private String tsvFileName = "a.tsv";
	
	static private Vector<SevenScaleData> data = new Vector<SevenScaleData>();
	
	public static void main(String args[]) {
		String fileName = "female12";
		try {
			data = loadDataFromFile(fileName + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			arrangeDataForTsvFile();
			writeDataToTsv(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void initialize() {
		data = new Vector<SevenScaleData>();
		dataFileName = Long.toString(System.currentTimeMillis()) + ".txt";
		tsvFileName = Long.toString(System.currentTimeMillis()) + "_ForExcelCopy.tsv";
		createFileIfNotExist(DATA_FOLDER + dataFileName);
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
	
	private static Vector<SevenScaleData> loadDataFromFile(String dataName) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(DATA_FOLDER + dataName), "UTF-8"));
		
		String line = "";
		Vector<SevenScaleData> data = new Vector<SevenScaleData>();
		
		while((line = br.readLine()) != null){
			String[] item = line.split(",");
			data.add(new SevenScaleData(item[0], item[1], item[2], item[3], item[4]));
//			DebugTools.print(item + item[0] + item[1] + item[2] + item[3] + item[4]);
		}
		br.close();
		return data;
	}
	
	static void writeData() throws IOException{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FOLDER + dataFileName,false));
		
		for(SevenScaleData item: data){			
			bw.write(item.getAll()); bw.newLine(); 
		}
		
		bw.flush();
		bw.close();
	}
	
	static void addItem(SevenScaleData item) {
		data.add(item);
	}
	
	static void writeDataToTsv(String fileName) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FOLDER + fileName + ".tsv",false));
		
		bw.write(arrangeDataForTsvFile());
		
		bw.flush();
		bw.close();
	}
	
	private static String arrangeDataForTsvFile() {
		Vector<String> enjoyS_Vec = createVecForSave();
		Vector<String> disgustS_Vec = createVecForSave();
		Vector<String> angerS_Vec = createVecForSave();
		Vector<String> fearS_Vec = createVecForSave();
		Vector<String> sadS_Vec = createVecForSave();
		Vector<String> surpriseS_Vec = createVecForSave();
		
		Vector<String> enjoyN_Vec = createVecForSave();
		Vector<String> disgustN_Vec = createVecForSave();
		Vector<String> angerN_Vec = createVecForSave();
		Vector<String> fearN_Vec = createVecForSave();
		Vector<String> sadN_Vec = createVecForSave();
		Vector<String> surpriseN_Vec = createVecForSave();
		
		Vector<String> enjoyF_Vec = createVecForSave();
		Vector<String> disgustF_Vec = createVecForSave();
		Vector<String> angerF_Vec = createVecForSave();
		Vector<String> fearF_Vec = createVecForSave();
		Vector<String> sadF_Vec = createVecForSave();
		Vector<String> surpriseF_Vec = createVecForSave();
		
		for(SevenScaleData item : data) {
			switch(item.getEmotion()) {
			case "Šy‚µ‚¢": addItemAccordingToTouchPattern(enjoyS_Vec, enjoyN_Vec, enjoyF_Vec, item); break;
			case "‚¤‚ñ‚´‚è‚µ‚Ü‚·": addItemAccordingToTouchPattern(disgustS_Vec, disgustN_Vec, disgustF_Vec, item); break;
			case "• —§‚¿": addItemAccordingToTouchPattern(angerS_Vec, angerN_Vec, angerF_Vec, item); break;
			case "•|‚¢": addItemAccordingToTouchPattern(fearS_Vec, fearN_Vec, fearF_Vec, item); break;
			case "”ß‚µ‚¢": addItemAccordingToTouchPattern(sadS_Vec, sadN_Vec, sadF_Vec, item); break;
			case "‚Ñ‚Á‚­‚è‚µ‚Ü‚·": addItemAccordingToTouchPattern(surpriseS_Vec, surpriseN_Vec, surpriseF_Vec, item); break;
			default: break;
			}
		}
		
		String tsvDataString = "";
		for(String item : enjoyS_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : disgustS_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : angerS_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : fearS_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : sadS_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : surpriseS_Vec) tsvDataString = tsvDataString + item + "\t"; 
		
		for(String item : enjoyN_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : disgustN_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : angerN_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : fearN_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : sadN_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : surpriseN_Vec) tsvDataString = tsvDataString + item + "\t"; 
		
		for(String item : enjoyF_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : disgustF_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : angerF_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : fearF_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : sadF_Vec) tsvDataString = tsvDataString + item + "\t"; 
		for(String item : surpriseF_Vec) tsvDataString = tsvDataString + item + "\t"; 
		
		return tsvDataString;
	}
	
	private static Vector<String> createVecForSave(){
		Vector<String> vec = new Vector<String>();
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");
		vec.add("");	
		return vec;
	}
	
	private static void addItemAccordingToTouchPattern(Vector<String> strengthVec, Vector<String> naturenessVec, Vector<String> friendlinessVec, SevenScaleData item) {
		switch(item.getTouchPattern()) {
		case "A_HandTouch_S": strengthVec.set(0,item.getStrength()); naturenessVec.set(0,item.getNatureness()); friendlinessVec.set(0, item.getFriendliness()); break;
		case "A_HandTouch_L": strengthVec.set(1,item.getStrength()); naturenessVec.set(1, item.getNatureness()); friendlinessVec.set(1, item.getFriendliness()); break;
		case "A1_HandFinger_S": strengthVec.set(2,item.getStrength()); naturenessVec.set(2, item.getNatureness()); friendlinessVec.set(2, item.getFriendliness()); break;
		case "A1_HandFinger_L": strengthVec.set(3,item.getStrength()); naturenessVec.set(3, item.getNatureness()); friendlinessVec.set(3, item.getFriendliness()); break;
		case "A2_HandPad_S": strengthVec.set(4,item.getStrength()); naturenessVec.set(4, item.getNatureness()); friendlinessVec.set(4, item.getFriendliness()); break;
		case "A2_HandPad_L": strengthVec.set(5,item.getStrength()); naturenessVec.set(5, item.getNatureness()); friendlinessVec.set(5, item.getFriendliness()); break;
		case "A3_HandFingerPad_S": strengthVec.set(6,item.getStrength()); naturenessVec.set(6, item.getNatureness()); friendlinessVec.set(6, item.getFriendliness()); break;
		case "A3_HandFingerPad_L": strengthVec.set(7,item.getStrength()); naturenessVec.set(7, item.getNatureness()); friendlinessVec.set(7, item.getFriendliness()); break;
		case "A4_HandGrip": strengthVec.set(8,item.getStrength()); naturenessVec.set(8, item.getNatureness()); friendlinessVec.set(8, item.getFriendliness()); break;
		case "A5_HandStroke": strengthVec.set(9, item.getStrength()); naturenessVec.set(9, item.getNatureness()); friendlinessVec.set(9, item.getFriendliness()); break;
		case "B_ForearmTouch_S": strengthVec.set(10, item.getStrength()); naturenessVec.set(10, item.getNatureness()); friendlinessVec.set(10, item.getFriendliness()); break;
		case "B_ForearmTouch_L": strengthVec.set(11, item.getStrength()); naturenessVec.set(11, item.getNatureness()); friendlinessVec.set(11, item.getFriendliness()); break;
		case "B1_ForearmFinger_S": strengthVec.set(12, item.getStrength()); naturenessVec.set(12, item.getNatureness()); friendlinessVec.set(12, item.getFriendliness()); break;
		case "B1_ForearmFinger_L": strengthVec.set(13, item.getStrength()); naturenessVec.set(13, item.getNatureness()); friendlinessVec.set(13, item.getFriendliness()); break;
		case "B2_ForearmPad_S": strengthVec.set(14, item.getStrength()); naturenessVec.set(14, item.getNatureness()); friendlinessVec.set(14, item.getFriendliness()); break;
		case "B2_ForearmPad_L": strengthVec.set(15, item.getStrength()); naturenessVec.set(15, item.getNatureness()); friendlinessVec.set(15, item.getFriendliness()); break;
		case "B3_ForearmFingerPad_S": strengthVec.set(16, item.getStrength()); naturenessVec.set(16, item.getNatureness()); friendlinessVec.set(16, item.getFriendliness()); break;
		case "B3_ForearmFingerPad_L": strengthVec.set(17, item.getStrength()); naturenessVec.set(17, item.getNatureness()); friendlinessVec.set(17, item.getFriendliness()); break;
		case "B4_ForearmGrip": strengthVec.set(18, item.getStrength()); naturenessVec.set(18, item.getNatureness()); friendlinessVec.set(18, item.getFriendliness()); break;
		case "B5_ForearmStroke": strengthVec.set(19, item.getStrength()); naturenessVec.set(19, item.getNatureness()); friendlinessVec.set(19, item.getFriendliness()); break;
		}
	}
	
}

