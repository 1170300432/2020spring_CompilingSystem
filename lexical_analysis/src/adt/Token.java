package adt;

public class Token{
	String typeCode;
	String value;
	
	public Token(String typeCode, String value) {
		if(typeCode.equalsIgnoreCase("IDN")) {
			// Keywords
			if(value.equalsIgnoreCase("proc") || value.equalsIgnoreCase("record") || value.equalsIgnoreCase("int")
					|| value.equalsIgnoreCase("real") || value.equalsIgnoreCase("if") || value.equalsIgnoreCase("then")
					|| value.equalsIgnoreCase("else") || value.equalsIgnoreCase("while") || value.equalsIgnoreCase("do")
					|| value.equalsIgnoreCase("or") || value.equalsIgnoreCase("and") || value.equalsIgnoreCase("not")
					|| value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false") || value.equalsIgnoreCase("call")) {
				this.typeCode = value.toUpperCase();								
				this.value = "- ";
			} 
			// IDNs
			else {
				this.typeCode = typeCode;								
				this.value = value;
			}
		}
		// CONSTs
		else if(typeCode.equalsIgnoreCase("CONST")) {
			this.typeCode = typeCode;								
			this.value = value;			
		}
		// Symbols
		else {
			this.typeCode = typeCode;								
			this.value = "- ";
		}
	}
	
	@Override
	public String toString() {
		return new String("<" + typeCode + ", " + value + ">");
	}
	
}