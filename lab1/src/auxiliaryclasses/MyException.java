package auxiliaryclasses;

public class MyException extends Exception {

	private static final long serialVersionUID = 1L;

	public MyException(String message) {
        super(message);
    }
	
	public static void printMessage(String message) {
		System.out.print("#");
		for(int i = 0; i < message.length()+4; i++) {
			System.out.print("-");
		}
		System.out.println("#");
		System.out.println("|  " + message + "  |");
		System.out.print("#");
		for(int i = 0; i < message.length()+4; i++) {
			System.out.print("-");
		}
		System.out.println("#");
	}
  
}