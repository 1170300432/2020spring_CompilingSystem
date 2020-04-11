package adt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AutomaticMachine{
	
	private Closure productions;
	private List<Closure> closures;
	private Set<Transfer> transfers;
	
	public AutomaticMachine(String filename){
		this.productions = new Closure(filename);
		this.closures = new ArrayList<Closure>();
		Closure I0 = new Closure();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String currentString = reader.readLine();
			I0.addProduction(new Production(currentString), productions);
			this.closures.add(I0);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.transfers = new HashSet<Transfer>();
		this.expand(I0, productions, 0);
		int prev_closuresNumber = 0;
		int curr_closuresNumber = 0;
		do {
			curr_closuresNumber = closures.size();
			for (int i = prev_closuresNumber + 1; i < curr_closuresNumber; i++) {
				this.expand(closures.get(i), productions, i);
			}
			prev_closuresNumber = curr_closuresNumber - 1;
		} while (curr_closuresNumber != closures.size());
	}
	
	private void expand(Closure expandClosure, Closure auxiliaryClosure, int prev) {
		Set<String> acceptNotes = expandClosure.acceptNotes();
		Iterator<String> iter = acceptNotes.iterator();
		while(iter.hasNext()) {
			boolean flag = false;
			String string = iter.next();
			Set<Production> productions = expandClosure.move(string);
			for (int i = 0; i < this.closures.size(); i++) {
				if(this.closures.get(i).isIncluded(productions)) {
					this.transfers.add(new Transfer(prev, i, string));
					flag = true;
					break;
				}
			}
			if(flag == true) {
				continue;
			}
			Closure newClosure = new Closure();
			Iterator<Production> iter1 = productions.iterator();
			while(iter1.hasNext()) {
				Production production = iter1.next();
				newClosure.addProduction(production, auxiliaryClosure);
			}
			this.closures.add(newClosure);
			int next = this.closures.size() - 1;
			this.transfers.add(new Transfer(prev, next, string));
		}
	}
	
	public void print() {
		for (int i = 0; i < closures.size(); i++) {
			System.out.println("I" + i + ":");
			closures.get(i).printClosure();
			System.out.println("--------------");
		}
		System.out.println("Transfers:");
		Iterator<Transfer> iter = this.transfers.iterator();
		while(iter.hasNext()) {
			Transfer trans = iter.next();
			System.out.println(trans.toString());
		}
	}
	
}