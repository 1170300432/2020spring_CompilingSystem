package adt;

public class Data{
	private int line;
	private String data;
	
	public Data(int line, String data) {
		this.line = line;
		this.data = data;
	}
	
	public String getData() {
		return new String(this.data);
	}
	
	public int getLine() {
		return this.line;
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	
}