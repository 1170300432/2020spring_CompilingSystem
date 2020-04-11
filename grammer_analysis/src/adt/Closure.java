package adt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Closure{
	
	private Set<Production> productions;
	
	public Closure() {
		this.productions = new HashSet<Production>();
	}
	
	public Closure(String filename) {
		this.productions = new HashSet<Production>();
		File file = new File(filename);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String currentString = null;
			while((currentString = reader.readLine()) != null) {
				productions.add(new Production(currentString));
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param production
	 * @param closure This parameter must be the all productions, or will leads to error!
	 */
	public void addProduction(Production production, Closure closure) {
		this.productions.add(production);
		String string = production.toString();
		String regex = "^.*\\^\\s(.+?)\\s.+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		if(matcher.find()) {
			string = matcher.group(1);
//			System.out.println(string);
			Set<Production> set = closure.findProductions(string);
			Iterator<Production> iterator = set.iterator();
			while(iterator.hasNext()) {
				Production prod = iterator.next();
				if(this.productions.contains(prod)) {
					continue;
				} else {
					this.addProduction(prod, closure);						
				}
			}
		} else {
			regex = "^.*\\^\\s(.*)$";
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(string);
			if(matcher.find()) {
				string = matcher.group(1);
				Set<Production> set = closure.findProductions(string);
				Iterator<Production> iterator = set.iterator();
				while(iterator.hasNext()) {
					Production prod = iterator.next();
					if(this.productions.contains(prod)) {
						continue;
					} else {
						this.addProduction(prod, closure);						
					}
				}
			}
		}
	}
	
	/**
	 * This method should be used ONLY if the closure is included all productions!
	 * 
	 * @param nonterminal
	 * @return
	 */
	private Set<Production> findProductions(String nonterminal){
		Set<Production> result = new HashSet<Production>();
		Iterator<Production> iterator = this.productions.iterator();
		while(iterator.hasNext()) {
			Production production = iterator.next();
			String string = production.toString();
			if(String.valueOf(string.charAt(0)).equals(nonterminal)) {
				result.add(new Production(production.getProductionArray()));
			}
		}
		return result;
	}
	
	public Set<String> acceptNotes(){
		Set<String> resultSet = new HashSet<String>();
		Iterator<Production> iterator = this.productions.iterator();
		while(iterator.hasNext()) {
			Production production = iterator.next();
			String string = production.acceptNote();
			if(string != null) {
				resultSet.add(string);
			}
		}
		return resultSet;
	}
	
	public Set<Production> move(String note){
		Set<Production> result = new HashSet<Production>();
		Iterator<Production> iterator = this.productions.iterator();
		while(iterator.hasNext()) {
			Production production = iterator.next();
			if(production.acceptNote() == null) {
				continue;
			}
			if(production.acceptNote().equals(note)) {
				result.add(new Production(production.move()));
			}
		}
		return result;
	}
	
	public void printClosure() {
		Iterator<Production> iterator = this.productions.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next().toString());
		}
	}
	
	public boolean isIncluded(Set<Production> productions) {
		return this.productions.containsAll(productions);
	}
	
}