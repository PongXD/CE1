import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;




public class TestingPurposes {
	private static final PrintStream original = System.out;
	private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	//private static ByteArrayInputStream inContent = new ByteArrayInputStream(null);
	
	public static void setUpOutput(){
		System.setOut(new PrintStream(outContent));
	}
	
	//public static void testInput(String testString){
		//inContent = new ByteArrayInputStream(testString.getBytes());
		//System.setIn(inContent);
	//}
	
	public static void clearOutputStream(){
		System.setOut(original);
	}

	private static String removeFirstWord(String line){
		return line.replace(getFirstWord(line), "").trim();
	}
	private static String getFirstWord(String commandString){
		String firstWord = commandString.trim().split("\\s+")[0];
		return firstWord;
	}
	private static String[] getParamADD(String commandString){
		String params = removeFirstWord(commandString);
		String[] paramArray = params.trim().split("\\s+");
		String[] fullParamArray = new String[4];
		
		for(int i = 2; i < paramArray.length; i++){
			String tag = "";
			String param = "1";
			switch(tag){
			case "NAME":
				fullParamArray[0] = param;
				break;
			case "COMPLETEION_TAG":
				fullParamArray[1] = param;
				break;
			case "DATE":
				fullParamArray[2] = param;
				break;
			case "WORKLOAD":
				fullParamArray[3] = param;
				break;
			case "DESCRIPTION":
				fullParamArray[4] = param;
				break;
			}
		}
		return fullParamArray;
	}
	public static void main(String[] args){
		setUpOutput();
		System.out.println("hello: ");
		//System.out.println("byebye    ");
		clearOutputStream();
		System.out.println(outContent.toString());
		 System.out.print("hello: ".equals(outContent.toString()));
		
	}

}
