import static org.junit.Assert.*;
import java.util.ArrayList;
import java.io.*;
import org.junit.Test;


public class textBuddyTest {
	
	@Test
	public void testSort() {
		textBuddy.testFileContent.add("fox");
		textBuddy.testFileContent.add("cow");
		textBuddy.testFileContent.add("200 fishes");
		textBuddy.testFileContent.add("the hulk");
		textBuddy.testFileContent.add("foxy mama");
		textBuddy.copyIntoFileContent();
		String outputSORTED = textBuddy.sort();
		
		//output from sort function when completed
		assertEquals(outputSORTED, "The text in myText.txt has been sorted alphabetically.");
		
		//tests whether the content has been sorted
		textBuddy.copyFromFileContent();
		assertEquals("200 fishes", textBuddy.testFileContent.get(0));
		assertEquals("cow", textBuddy.testFileContent.get(1));
		assertEquals("fox", textBuddy.testFileContent.get(2));
		assertEquals("foxy mama", textBuddy.testFileContent.get(3));
		assertEquals("the hulk", textBuddy.testFileContent.get(4));
		
		textBuddy.testFileContent.clear();
		textBuddy.copyIntoFileContent();
		String outputFAILsort = textBuddy.sort();
		
		//output from sort function if no text
		assertEquals(output, "There is no text in myText.txt to sort!");
	}
	
	@Test
	public void testSearch() {
		textBuddy.testFileContent.add("fox");
		textBuddy.testFileContent.add("cow");
		textBuddy.testFileContent.add("200 fishes");
		textBuddy.testFileContent.add("the hulk");
		textBuddy.testFileContent.add("foxy mama");
		textBuddy.copyIntoFileContent();
		
		//output from search function if word can be found
		assertEquals(textBuddy.search("fox"), 
				"The word \"fox\" has been found these lines\n1. fox\n5. foxy mama");
		assertEquals(textBuddy.search("cow"), 
				"The word \"cow\" has been found these lines\n2. cow");
		assertEquals(textBuddy.search(" "), 
				"The word \" \" has been found these lines\n3. 200 fishes\n4. the hulk\n5. foxy mama");
		
		//output from the search function if word cannot be found
		assertEquals(textBuddy.search("wolf"), 
				"The word \"wolf\" cannot be found in myText.txt.");
		assertEquals(textBuddy.search("foxymama"), 
				"The word \"foxymama\" cannot be found in myText.txt.");
	}

}
