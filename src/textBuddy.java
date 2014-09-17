import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
	
public class textBuddy {
	//Magic Numbers/Strings
	private static final String EMPTY_STRING = "";
	private static final int NOT_VALID_NUMBER = -1;
	
	//Display messages
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. \"%s\" is ready for use.";
	private static final String MESSAGE_ADD_ENTRY = "\"%s\" has been added to %s.";
	private static final String MESSAGE_DELETE_ENTRY = "\"%s\" has been deleted from %s.";
	private static final String MESSAGE_CLEAR = "All content has been deleted from %s.";
	private static final String MESSAGE_EXIT = "Thank you for using TextBuddy :)";
	
	//Display errors
	private static final String ERROR_FILE_NOT_FOUND = "The file \"%s\" you entered is either not found, "
														+ "or cannot be read, please try another file.";
	private static final String ERROR_WRONG_FORMAT = "\"%s\" is not a number, or is in the wrong format!";
	private static final String ERROR_CANNOT_DELETE = "There is no corresponding text to delete in %s.";
	private static final String ERROR_NO_DISPLAY = "There is no text in %s.";	
	private static final String ERROR_INVALID_CMD = "Invalid command \"%s\" entered! Please enter again.";
	private static final String ERROR_IO_EXCEPTION = "Cannot execute command \"%s\", IO Exception detected.";
	
	//Global variables
	private static Scanner scanner = new Scanner(System.in);
	private static BufferedReader file;
	private static ArrayList<String> fileContent;
	public static ArrayList<String> testFileContent; //public accessible tasklist, used for testing
	private static String fileName;
	private static boolean exitMarker = false;
	
	//Constructor which runs the main function with the fileName provided
	public textBuddy(String fileN){
		try{
			loadFile();
			String[] arguments = {fileN};
			main(arguments);
		} 
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//Main function
	public static void main(String args[]){
		try {
			//fileName = args[0];
			//loadFile();
			new textBuddy(args[0]);
			showToUser(String.format(MESSAGE_WELCOME, fileName));
			
			while(exitMarker == false){
				showToUser("Enter Command: ");
				String command = scanner.nextLine();
				String userCommand = command;
				String feedback = executeCommand(userCommand);
				showToUser(feedback);	
				reloadFile();
			}
		}
		catch(Exception e){
			showToUser(String.format(ERROR_FILE_NOT_FOUND, fileName));
		}
	}

/*---------------------------Active functions-----------------------------*/
	private static String executeCommand(String userCommand){
		try{ 
			String commandType = getFirstWord(userCommand);
			switch(commandType.toLowerCase()){
			case "add":
				String toAdd = getParam(userCommand);
				return add(toAdd);
			case "delete":
				String toDelete = getParam(userCommand);
				return delete(toDelete);
			case "clear":
				return clear();
			case "display":
				return display();
			case "exit":
				return exit();
			default: 
				return String.format(ERROR_INVALID_CMD, userCommand);
			}
		} 
		catch (Exception e){
			return String.format(ERROR_IO_EXCEPTION, userCommand);
		}
	}
	
	public static String add(String toAdd) throws Exception {
		fileContent.add(toAdd);
		writeToFile();
		return String.format(MESSAGE_ADD_ENTRY, toAdd, fileName);
	}
	
	public static String delete(String toDelete) throws Exception {
		int num = checkNum(toDelete);
		if(num == NOT_VALID_NUMBER){
			return String.format(ERROR_WRONG_FORMAT, toDelete);
		}
		else {
			String deletedText = findAndDelete(num);
			
			if (deletedText == EMPTY_STRING){
				return String.format(ERROR_CANNOT_DELETE, fileName);
			} 
			else {
				writeToFile();
				return String.format(MESSAGE_DELETE_ENTRY, deletedText, fileName);
			}
		}
	}
	
	public static String clear() throws Exception{
		fileContent.clear();
		writeToFile();
		return String.format(MESSAGE_CLEAR, fileName);
	} 
	
	public static String display() throws Exception {
		String allText = readFile();
		
		if(allText == EMPTY_STRING){
			return String.format(ERROR_NO_DISPLAY, fileName);
		} 
		else {
			return allText.trim();
		}
	}
	
	
	public static String exit(){
		exitMarker = true;
		return MESSAGE_EXIT;
	}
	
/*----------------------File writing/reading functions--------------------*/
		
	private static void loadFile() throws Exception{
		file = new BufferedReader(new FileReader(fileName));
		fileContent = new ArrayList<String>();
		loadFromFileToList();
	}
	
	private static void reloadFile(){
		try {
			file = new BufferedReader(new FileReader(fileName));
		} 
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private static String readFile() throws Exception{
		String line = null;
		String text = EMPTY_STRING;
		while((line = file.readLine()) != null){
			text = text + line + "\n";
		}
		return text;
	}
	
	
	private static void loadFromFileToList() throws Exception{
		String allText = readFile();
		String[] lines = allText.split("\n");
		for(int i = 0; i < lines.length; i++){
			String line = removeFirstWord(lines[i]);
			fileContent.add(line);
		}
		reloadFile();
	}
	
	private static void writeToFile(){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			bw.write(EMPTY_STRING);
			for(int i = 0; i < fileContent.size(); i++){
				String toWrite = fileContent.get(i);
				String indexNum = (i+1) + ". ";
				bw.write(indexNum + toWrite);
				bw.newLine();	
			}
			bw.close();
		} 
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
/*---------------------------Helper Functions----------------------------*/
	
	private static void showToUser(String text) {
		System.out.println(text);
	}
	
	private static String removeFirstWord(String line){
		return line.replace(getFirstWord(line), "").trim();
	}
	
	private static String getParam(String userCommand) {
		return removeFirstWord(userCommand);
	}

	private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}
	
	//checks whether the parameter is a number, and returns it if so
	private static int checkNum(String param){
		try{
			int num = Integer.parseInt(param);
			return num;
		}
		catch (NumberFormatException e){
			return NOT_VALID_NUMBER;
		}
	}
	
	//finds the line in the text to delete and returns the deleted text
	private static String findAndDelete(int toDelete){
		try{
			String deletedText = fileContent.remove(toDelete - 1);
			return deletedText;
		}
		catch(IndexOutOfBoundsException e){
			return EMPTY_STRING;
		}
	}

/*---------------------Testing functions----------------------*/
	
	public static void copyIntoFileContent(){
		fileContent = testFileContent;
	}
	
	public static void copyFromFileContent(){
		testFileContent = fileContent;
	}
}