package aocDay1;

import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		
		try {
			FileReader reader = new FileReader("input.txt");
			
			HashMap<String, String> numbers = new HashMap<String, String>();
			HashMap<Integer, String> numstringIndices = new HashMap<Integer, String>();
			numbers.put("one", "1");
			numbers.put("two", "2");
			numbers.put("three", "3");
			numbers.put("four", "4");
			numbers.put("five", "5");
			numbers.put("six", "6");
			numbers.put("seven", "7");
			numbers.put("eight", "8");
			numbers.put("nine", "9");
			
			int data = reader.read();
			String line = "";
			String twoDigit = "";
			int lineValue = 0;
			int total = 0;
			int iteration = 0;
			
			while(data != -1) {
				line += (char)data;
				if ((char)data == '\n') {
					
					// GET INDICES OF ALL NUMBER STRINGS
					for (String numString : numbers.keySet()) {
						int startingIndex = 0;
						if (line.contains(numString)) {
							startingIndex = line.indexOf(numString);		
							// Store into HashMap
							numstringIndices.put(startingIndex, numString);
							// Check for duplicates starting from end of first number
							startingIndex = startingIndex + numString.length();
							startingIndex = line.indexOf(numString, startingIndex);
							while (startingIndex != -1) {
								// Add the duplicate to the HashMap
								numstringIndices.put(startingIndex, numString);
								// Check for more duplicates
								startingIndex = startingIndex + numString.length();
								startingIndex = line.indexOf(numString, startingIndex);
							}
						}
					}
					
					// ADD NUMBER TO LEFT OF ALL NUMBER STRINGS
					for (int index = 0; index < line.length(); index++) {
						if (numstringIndices.get(index) != null) {
							String digit = numbers.get(numstringIndices.get(index));
							if (index == 0) {
								line = digit + line;
							}
							else {
								String start = line.substring(0, index + iteration);
								String end = line.substring(index + iteration);
								line = start + digit + end;
							}
							iteration++;
						}
					}
					
					// REMOVE ALL NON NUMBERS
					for (int index = 0; index < line.length(); index++ ) {
						char stringChar = line.charAt(index);
						int asciiVal = stringChar;
						if (asciiVal >= 97 && asciiVal <= 122) {
							line = line.replace(stringChar, ' ');
						}
					}
					line = line.replaceAll("\\s", "");
					
					// GET LINE VALUE AND ADD TO TOTAL
					if (line.length() == 1) {
						for(int i = 0; i < 2; i++) {
							twoDigit += line;
						}
					}
					else {
						twoDigit += line.charAt(0);
						twoDigit += line.charAt(line.length()-1);
					}
					lineValue = Integer.parseInt(twoDigit);
					total += lineValue;
					
					// Reset variables and HashMap
					line = "";
					twoDigit = "";
					iteration = 0;
					numstringIndices.clear();
				}
				data = reader.read();
			}
			reader.close();
			System.out.print("Total: " + total);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
