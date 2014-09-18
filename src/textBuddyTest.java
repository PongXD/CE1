import static org.junit.Assert.*;
import java.util.ArrayList;
import java.io.*;
import org.junit.Test;


public class textBuddyTest {
	
	private final String EMPTY_STRING = "";
	
	//to clean up the text file after testing
	private void resetFile(String fileName){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			bw.write(EMPTY_STRING);
			bw.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSort() {
		
		//sort, sorts the data in textBudd, then another function writes from
		//the data to the text file each time a command is called, so testing the change
		//in the data should be fine
		ArrayList<String> testArray = new ArrayList<String>();
		textBuddy.initialiseTest("myText.txt", testArray);
		
		//test output from sort function if no text
		String outputFAILsort = textBuddy.sort();
		assertEquals(outputFAILsort, "There is no text in myText.txt to sort!");
		
		//Adding elements to the data
		testArray.add("fox");
		testArray.add("cow");
		testArray.add("200 fishes");
		testArray.add("the hulk");
		testArray.add("foxy mama");
		textBuddy.initialiseTest("myText.txt", testArray);
		
		//test output from sort function when completed
		String outputSORTED = textBuddy.sort();
		assertEquals(outputSORTED, "The text in myText.txt has been sorted alphabetically.");
		
		//tests whether the content in the data has been sorted
		textBuddy.copyFromFileContent();
		assertEquals("200 fishes", textBuddy.testFileContent.get(0));
		assertEquals("cow", textBuddy.testFileContent.get(1));
		assertEquals("fox", textBuddy.testFileContent.get(2));
		assertEquals("foxy mama", textBuddy.testFileContent.get(3));
		assertEquals("the hulk", textBuddy.testFileContent.get(4));
		
		resetFile("myText.txt");
	}
	
	
	@Test
	public void testSearch() {
		
		//initialise the data with the following elements
		ArrayList<String> testArray = new ArrayList<String>();
		testArray.add("fox");
		testArray.add("cow");
		testArray.add("200 fishes");
		testArray.add("the hulk");
		testArray.add("foxy mama");
		textBuddy.initialiseTest("myText.txt", testArray);
		
		//test output from search function if word can be found
		assertEquals(textBuddy.search("fox"), 
				"The word \"fox\" has been found in these lines\n1. fox\n5. foxy mama");
		assertEquals(textBuddy.search("cow"), 
				"The word \"cow\" has been found in these lines\n2. cow");
		assertEquals(textBuddy.search(" "), 
				"The word \" \" has been found in these lines\n3. 200 fishes\n4. the hulk\n5. foxy mama");
		
		//test output from the search function if word cannot be found
		assertEquals(textBuddy.search("wolf"), 
				"The word \"wolf\" cannot be found in myText.txt.");
		assertEquals(textBuddy.search("foxymama"), 
				"The word \"foxymama\" cannot be found in myText.txt.");
		
		resetFile("myText.txt");
	}
	
}
