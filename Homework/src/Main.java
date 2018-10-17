import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class Main {

	private static HashMap<String, Integer> wordMap = new HashMap<>();

	public static void main(String[] args) {
		StringBuilder word = new StringBuilder();
		int ch;
		try {
			// read until end of file value
			while ((ch = System.in.read()) != -1) {
				// check read size, if greater or equal to 1000 break...
				if(word.length() >= 1000){
					break;
				} 
				// until press the enter, read values and process chars..
				else if (ch != '\n' && ch != '\r') {
					processChar(word, (char) ch);
				}
				// if enter pressed, then exit
				else{
					break;
				}
			}
			
			// convert all chars to lowercase
			String allSentence = word.toString().toLowerCase();
			
			// split with empty char ' '
			String[] wordsArray = allSentence.split(" ");
			
			// loop with each word and update word map
			for(int i=0; i<wordsArray.length; i++){
				updateWordMap(wordsArray[i]);
			}
			
			// output the map values
			for (Entry<String,Integer> entry : wordMap.entrySet()) {
				System.out.println("Key: [ " + entry.getKey() + " - Count: " + entry.getValue() + " ]");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void processChar(StringBuilder word, char c) {
		word.append(c);
	}

	private static void updateWordMap(String word) {
		// if word exist in map, then increase count +1
		if (wordMap.containsKey(word)) {
			wordMap.put(word, wordMap.get(word) + 1);
		}
		// if word not exist in map, then put with number 1 count
		else {
			wordMap.put(word, 1);
		}
	}
}
