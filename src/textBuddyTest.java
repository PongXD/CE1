import static org.junit.Assert.*;

import java.io.*;

import org.junit.Test;


public class textBuddyTest {
	private final PrintStream originalO = System.out;
	private final InputStream originalI = System.in;
	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private ByteArrayInputStream inContent;
	
	public void setUpOutput(){
		System.setOut(new PrintStream(outContent));
	}
	
	public void enterInput(String testString){
		inContent = new ByteArrayInputStream(testString.getBytes());
		System.setIn(inContent);
	}
	
	public void clearOutputStream(){
		System.setOut(originalO);
	}
	
	public void resetFile() {
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("myText.txt"));
			bw.write("");
			bw.close();
		} 
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAdd() {
		// textBuddy temp = new textBuddy("myText.txt");
		setUpOutput();
		enterInput("add fox");
		// clearOutputStream();
		//System.out.println(outContent.toString());
		textBuddy.main(new String[]{"myText.txt"});
		assertEquals("\"fox\" has been added to myText.txt.\nEnter Command: ", outContent.toString());
		
		try{
			BufferedReader file = new BufferedReader(new FileReader("myText.txt"));
			String line = file.readLine();

			assertEquals("1. fox", line);
			file.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}	
		finally {
			resetFile();
		}
	}

	@Test
	public void testDelete() {
		new textBuddy("myText.txt");
		enterInput("add fox");
		enterInput("add wolf");
		setUpOutput();
		enterInput("delete 1");
		clearOutputStream();
		String expectedOutput = "\"fox\" has been deleted from myText.txt.";
		assertEquals(expectedOutput, outContent.toString());
		
		try{
			BufferedReader file = new BufferedReader(new FileReader("myText.txt"));
			String line = file.readLine();

			assertEquals("1. wolf", line);
			file.close();
		} 
		catch (Exception e){
			e.printStackTrace();
		}	
		finally {
			resetFile();
		}
	}

	@Test
	public void testClear() {
		new textBuddy("myText.txt");
		enterInput("add fox");
		enterInput("add wolf");
		setUpOutput();
		enterInput("clear");
		clearOutputStream();
		String expectedOutput = "All content has been deleted from myText.txt.";
		assertEquals(expectedOutput, outContent.toString());
		
		try{
			BufferedReader file = new BufferedReader(new FileReader("myText.txt"));
			assertTrue(file.readLine() == null);
			file.close();
		} 
		catch (Exception e){
			e.printStackTrace();
		} 
		finally {
			resetFile();
		}
	}

	@Test
	public void testDisplay() {
		new textBuddy("myText.txt");
		enterInput("add fox");
		enterInput("add wolf");
		setUpOutput();
		enterInput("display");
		clearOutputStream();
		
		String expectedOutput = "1. fox\n2. wolf";
		assertEquals(expectedOutput, outContent.toString());
		
		resetFile();
	}

	@Test
	public void testExit() {
		new textBuddy("myText.txt");
		setUpOutput();
		enterInput("exit");
		clearOutputStream();
		String expectedOutput = "Thank you for using TextBuddy :)";
		assertEquals(expectedOutput, outContent.toString());
		
		setUpOutput();
		enterInput("display");
		clearOutputStream();
		expectedOutput = "";
		assertEquals(expectedOutput, outContent.toString());
		
		resetFile();
	}

}
