import static org.junit.Assert.*;
import java.io.*;
import org.junit.Test;


public class textBuddyTest {
	private final InputStream originalI = System.in;

	@Test
	public void testSort() {
		//output from sort function when completed
		assertEquals(output, "The text in myText.txt has sorted alphabetically.");
		

		//output from sort function if no text
		assertEquals(output, "There is no text in myText.txt to sort!");
	}
	
	@Test
	public void testSearch() {
		
		//output from search function if word can be found
		assertEquals(textBuddy.search("fox"), 
				"The word \"fox\" has been found these line\n1. fox\n3. foxy mama");
		//output from the search function if word cannot be found
		assertEquals(textBuddy.search("cow"), 
				"The word \"cow\" cannot be found in myText.txt.");
	}

}
