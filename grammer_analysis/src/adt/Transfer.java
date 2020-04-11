package adt;

public class Transfer{
	private int prev;
	private int next;
	private String condition;
	
	public Transfer(int prev, int next, String condition) {
		this.prev = prev;
		this.next = next;
		this.condition = condition;
	}
	
	public int getPrev() {
		return prev;
	}
	
	public int getNext() {
		return next;
	}
	
	public String getCondition() {
		return new String(condition);
	}
	
	@Override
	public String toString() {
		return new String("<" + prev + ", " + next + ", " + condition + ">");
	}
	
}