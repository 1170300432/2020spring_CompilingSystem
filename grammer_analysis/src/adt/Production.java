package adt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Production{
	
	private List<String> production;
	
	public Production(List<String> production) {
		this.production = new ArrayList<String>(production);
	}
	
	public Production(String productionString) {
		String regex = "^(.*\\s)::=\\s(.*)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(productionString);
		if(matcher.find()) {
			if(matcher.group(2).equals("ε")) {
				productionString = matcher.group(1) + "^";
				this.production = Arrays.asList(productionString.split(" "));
			} else {
				productionString = matcher.group(1) + "^ " + matcher.group(2);
				this.production = Arrays.asList(productionString.split(" "));
			}
		}
	}
	
	public List<String> getProductionArray() {
		return new ArrayList<String>(this.production);
	}
	
	@Override
	public boolean equals(Object obj) {
		String string1 = this.toString();
		String string2 = ((Production)obj).toString();
		if(string1.equals(string2)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		int count = 0;
		for (int i = 0; i < this.production.size(); i++) {
			char[] charArray = this.production.get(i).toCharArray();
			int count1 = 0;
			for (int j = 0; j < charArray.length; j++) {
				count1 = count1 + (int)charArray[j];
			}
			count = count + i * count1;
		}
		return count;
	}
	
	public String acceptNote() {
		int length = this.production.size();
		for(int i = 0; i < length; i++) {
			if(this.production.get(i).equalsIgnoreCase("^") && (i < length - 1)) {
				return this.production.get(i+1);
			}
		}
		return null;
	}
	
	public List<String> move() {
		List<String> result = new ArrayList<String>();
		int length = this.production.size();
		for(int i = 0; i < length; i++) {
			if(this.production.get(i).equalsIgnoreCase("^") && (i < length - 1)) {
				result.add(this.production.get(i+1));
				result.add(this.production.get(i));
				i++;
			} else {
				result.add(this.production.get(i));				
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		String result = new String();
		int length = this.production.size();
		for(int i = 0; i < length; i++) {
			if(i != length - 1) {
				result = result + this.production.get(i) + " ";				
			} else {
				result = result + this.production.get(i);
			}
		}
		return result;
	}
	
}