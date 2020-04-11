package adt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dfa{
	private Integer state;
	private Set<Transfer> transfers = new HashSet<Transfer>();
	private String[] endings;
	
	/**
	 * Constructor: Create a finite automaton that can be used for lexical analysis.
	 */
	public Dfa(File file) {
		this.state = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String currentString = reader.readLine();
			endings = currentString.split(",");
			String regex = "^(\\d+)\\s(\\d+)\\s(.+)$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher;
			while((currentString = reader.readLine()) != null) {
				matcher = pattern.matcher(currentString);
				if(matcher.find()) {
				    Transfer tranfer = new Transfer(Integer.valueOf(matcher.group(1)), 
				    		Integer.valueOf(matcher.group(2)), matcher.group(3));
				    transfers.add(tranfer);
				}
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Reset the state as 0.
	 */
	public void reset() {
		this.state = 0;
	}
	
	/**
	 * Change the state according to the character.
	 * 
	 * @param c The inputting character.
	 * @return Return 0 if the state is changed successfully, else return -1.
	 */
	public int move(Character c) {
		String type = null;
		String character = c.toString();
		// Space
		String regex = "\\s+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(character);
		// Letter regex
		String regex1 = "[a-zA-Z]";
		Pattern pattern1 = Pattern.compile(regex1);
		Matcher matcher1 = pattern1.matcher(character);
		// Digit regex
		String regex2 = "[0-9]";
		Pattern pattern2 = Pattern.compile(regex2);
		Matcher matcher2 = pattern2.matcher(character);
		// Letter
		if (matcher1.find()) {
			if(character.equalsIgnoreCase("e")) {
				type = new String("e");
			} else {
				type = new String("letter");				
			}
		}
		// Digit
		else if (matcher2.find()) {
			type = new String("digit");
		}
		// Space
		else if (matcher.find()) {
			type = new String("space");
		}
		// Symbols
		else {
			if(character.equals("_")) {
				type = new String("_");
			} else if(character.equals(".")) {
				type = new String(".");
			} else if(character.equals("+")) {
				type = new String("+");
			} else if(character.equals("-")) {
				type = new String("-");
			} else if(character.equals(">")) {
				type = new String(">");
			} else if(character.equals("<")) {
				type = new String("<");
			} else if(character.equals("=")) {
				type = new String("=");
			} else if(character.equals("!")) {
				type = new String("!");
			} else if(character.equals("*")) {
				type = new String("*");
			} else if(character.equals("/")) {
				type = new String("/");
			} else if(character.equals("&")) {
				type = new String("&");
			} else if(character.equals("|")) {
				type = new String("|");
			} else if(character.equals("~")) {
				type = new String("~");
			} else if(character.equals(";")) {
				type = new String(";");
			} else if(character.equals("(")) {
				type = new String("(");
			} else if(character.equals(")")) {
				type = new String(")");
			} else if(character.equals("{")) {
				type = new String("{") ;
			} else if(character.equals("}")) {
				type = new String("}");
			} else if(character.equals("[")) {
				type = new String("[");
			} else if(character.equals("]")) {
				type = new String("]");
			} else if(character.equals("\"")) {
				type = new String("\"");
			} else if(character.equals(",")) {
				type = new String(",");
			}
		}
		Iterator<Transfer> iterator = transfers.iterator();
		while(iterator.hasNext()) {
			Transfer transfer = iterator.next();
			if((transfer.getPrev() == state) && (transfer.getCondition().equalsIgnoreCase(type))) {
				state = transfer.getNext();
//				System.out.println(state);
				return 0;
			}
			if(type.equals("*")) {
				if((transfer.getPrev() == state) && (transfer.getCondition().equalsIgnoreCase("ALL/*"))) {
					continue;
				}
			} else if(type.equals("\"")) {
				if((transfer.getPrev() == state) && (transfer.getCondition().equalsIgnoreCase("ALL/\""))) {
					continue;
				}
			} else if(type.equals("/")) {
				if((transfer.getPrev() == state) && (transfer.getCondition().equalsIgnoreCase("ALL/*/"))) {
					continue;
				}
			} else {
				if(transfer.getPrev() == state) {
					String regex3 = "all.*";
					Pattern pattern3 = Pattern.compile(regex3);
					Matcher matcher3 = pattern3.matcher(transfer.getCondition());
					if (matcher3.find()) {
						state = transfer.getNext();
//					System.out.println(state);
						return 0;
					}					
				}
			}
		}
		if(type.equals("space")) {
			return -2;
		}
		return -1;
	}
	
	/**
	 * @return
	 */
	public boolean isReceiving() {
		boolean result = false;
		String regex = "(\\d+):(.+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher;
		int length = endings.length;
		for(int i = 0; i < length; i++) {
			matcher = pattern.matcher(endings[i]);
			if(matcher.find()) {
			    if(matcher.group(1).equalsIgnoreCase(state.toString())) {
				    result = true;
				    break;
			    }
			}
		}
		return result;
	}
	
	public String getTypeCode() {
		if(isReceiving()) {
			String regex = "(\\d+):(.+)";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = null;
			int length = endings.length;
			for(int i = 0; i < length; i++) {
				matcher = pattern.matcher(endings[i]);
				if(matcher.find()) {
					if(matcher.group(1).equalsIgnoreCase(state.toString())) {
						break;
					}
				}
			}
			return matcher.group(2);
		} else {
			return null;
		}
	}
	
//	public void showTransfers() {
//		Iterator<Transfer> iter = transfers.iterator();
//		while(iter.hasNext()) {
//			System.out.println(iter.next().toString());
//		}
//	}
	
}