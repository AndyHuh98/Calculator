
public class Token {
	private char token;
	//private String token;
	private boolean valid = false;
	private int precedence;
	public enum Type {
		NUMBER, INVALID, OPERATOR, ORGANIZER;
	}
	private Type type;
	
	public Token() {
		token = ' ';
		valid = false;
		precedence = 0;
		type = Type.INVALID;
	}
	
	public Token(char c) {
		token = c;
		
		switch (c) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				type = Type.NUMBER;
				valid = true;
				precedence = -1;
				break;
			case '+': case '-': 
				type = Type.OPERATOR;
				valid = true;
				precedence = 0;
				break;
			case '*': case '/': 
				type = Type.OPERATOR;
				valid = true;
				precedence = 1;
				break;
			case '^':
				type = Type.OPERATOR;
				valid = true;
				precedence = 2;
				break;
			case '(': case ')': 
				type = Type.ORGANIZER;
				valid = true;
				precedence = -1;
				break;
			default:
				type = Type.INVALID;
				valid = false;
				precedence = -2;
				break;
		}
	}
	
	public Type getType() { return type; }
	
	public int getPrecedence() { return precedence; }
	
	public boolean getValidity() { return valid; }
	
	public char getToken() { return token; }
}
