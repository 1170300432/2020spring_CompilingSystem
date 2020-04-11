package adt;

public class Transfer{
	private Integer prev;
	private Integer next;
	private String condition;
	
	public Transfer(Integer prev, Integer next, String condition) {
		this.prev = prev;
		this.next = next;
		this.condition = condition;
	}
	
	public Integer getPrev() {
		return prev;
	}
	
	public Integer getNext() {
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