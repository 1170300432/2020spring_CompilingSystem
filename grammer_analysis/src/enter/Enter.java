package enter;

import adt.AutomaticMachine;

public class Enter{
	
	public static void main(String[] args) {
        AutomaticMachine am = new AutomaticMachine("txt/productions1.txt");
//		AutomaticMachine am = new AutomaticMachine("txt/productions2.txt");
//		AutomaticMachine am = new AutomaticMachine("txt/productions3.txt");
        am.print();
		
	}
	
}