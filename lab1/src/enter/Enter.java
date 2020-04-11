package enter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adt.Dfa;
import adt.Token;
import auxiliaryclasses.MyException;

public class Enter{
	
	public static void main(String[] args) {
		File dfaFile = new File("txt/dfa.txt");
		Dfa dfa = new Dfa(dfaFile);
//		dfa.showTransfers();
		File codeFile = new File("txt/code.txt");
		File resultFile = new File("txt/result.txt");
		String sourceString = new String();
		try {
			FileWriter writer = new FileWriter(resultFile);
			BufferedReader reader = new BufferedReader(new FileReader(codeFile));
			String currentString = null;
			int lineNumber = 0;
			while((currentString = reader.readLine()) != null) {
//				writer.write(currentString + "\n");
				lineNumber++;
				// In order to deal with the final character for each line
				currentString = currentString + " ";
				char[] charArray = currentString.toCharArray();
//				System.out.println(charArray);
				int length = charArray.length;
				for(int i = 0; i < length; i++) {
//					System.out.println(sourceString);
					String regex = "^\\s+$";
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(sourceString);
					if (matcher.find()) {
						matcher = pattern.matcher(String.valueOf(charArray[i]));
						if(!matcher.find()) {
							sourceString = new String();
						}
					}
//					System.out.println(charArray[i]);
					int returnNumber = dfa.move(Character.valueOf(charArray[i]));
//					System.out.println(returnNumber);
					// Encounter an acceptable character
					if(returnNumber == 0) {
						// Add the character to the buffer string
						sourceString = sourceString + charArray[i];
					}
					// Encounter an unacceptable character
					else if(returnNumber == -1) {
//						System.out.println("================"+charArray[i]);
						if(dfa.isReceiving()) {
							String typeCode = dfa.getTypeCode();
							// Generate token
							Token token = new Token(typeCode, sourceString);
							System.out.println(sourceString + "\t===>\t" + token.toString());
							writer.write(lineNumber + " : " + token.toString() + "\n");
							// Reset the DFA and refresh the buffer string
							dfa.reset();
							sourceString = new String();
						} else {
							reader.close();
							writer.close();
							throw new MyException("An error has occured at line " + lineNumber + "!");
						}
						// Keep i not move
						i--;
					}
					// Encounter a space
					else {
						if(dfa.isReceiving()) {
							String typeCode = dfa.getTypeCode();
							// Generate token
							Token token = new Token(typeCode, sourceString);
							System.out.println(sourceString + "\t===>\t" + token.toString());
							writer.write(lineNumber + " : " + token.toString() + "\n");
							// Reset the DFA and refresh the buffer string
							dfa.reset();
							sourceString = new String();
						} else {
							reader.close();
							writer.close();
							throw new MyException("An error has occured at line " + lineNumber + "!");	
						}
						// To pass the space, i continue to move
					}
				}
				// When a comment start with '//', changing a row should be seen as encountering a space
				String regex = "//.*";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(String.valueOf(sourceString));
				if(matcher.find()) {
					if(dfa.isReceiving()) {
						String typeCode = dfa.getTypeCode();
						// Generate token
						Token token = new Token(typeCode, sourceString);
						System.out.println(sourceString + "\t===>\t" + token.toString());
						writer.write(lineNumber + " : " + token.toString() + "\n");
						// Reset the DFA and refresh the buffer string
						dfa.reset();
						sourceString = new String();
					} else {
						reader.close();
						writer.close();
						throw new MyException("An error has occured at line " + lineNumber + "!");
					}
				}
//				writer.write("\n");
			}
			// To deal with the last line
			if(dfa.isReceiving()) {
				String typeCode = dfa.getTypeCode();
				// Generate token
				Token token = new Token(typeCode, sourceString);
				System.out.println(sourceString + "\t===>\t" + token.toString());
				writer.write(lineNumber + " : " + token.toString() + "\n");
			}
			reader.close();
			writer.close();
		} catch (Exception e) {
			String message = e.getMessage();
			MyException.printMessage(message);
			e.printStackTrace();
		}
	}
}