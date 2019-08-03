import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/*
 * Problems:
 * 
 * HOW TO SUPPORT NEGATIVE NUMBERS?
 * SUPPORT DECIMALS
 * SUPPORT FACTORIALS 
 * HANDLING INVALID EXPRESSIONS *PROBABLY STILL BUGS*
 * MAKE RESULT DOUBLE -- FOR DIVISION HANDLING *CHECK*
 * IGNORE SPACES ON INPUT *CHECK*
 * ADD COMMENTS EXPLAINING THE METHODS
 * ROUND DOUBLE VALUES TO (5) AFTER THE DECIMAL
 * ALERTS FOR INVALID INPUTS
 * DISPLAY THE POSTFIX EXPRESSION
 */

public class Calculate {
	private String expression;
	private String postfix;
	private ArrayList<Token> tokens = new ArrayList<Token>();
	private boolean validExp = true;
	
	//For testing purposes, takes console output; uses public static void main in Calculate class
	public void receiveTokens() {
		Scanner input = new Scanner(System.in);
		String potentialTokens = input.nextLine().toLowerCase();
		StringBuilder s = new StringBuilder(potentialTokens.length());
		
		for (int i = 0; i < potentialTokens.length(); i++) {
			char c = potentialTokens.charAt(i);
			Token token = new Token(c);
			tokens.add(token);
			s.append(token.getToken());
		}
		
		expression = s.toString();
	}
	
	//Used to receive input from a JTextField
	public void receiveTokens(String input) {
		StringBuilder s = new StringBuilder(input.length());
		
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ') {
				Token token = new Token(c);
				tokens.add(token);
				s.append(token.getToken());
			}
		}
		//Expression was used for testing (console printing original expression. Not visible by the user.
		expression = s.toString();
	}
	
	//Converts the infix expression to a postfix for evaluation
	public void postfix(ArrayList<Token> tokens) { 
		StringBuilder s = new StringBuilder(tokens.size());
		Stack<Token> stack = new Stack<Token>();
		
		//Loop through all tokens
		for (int i = 0; i < tokens.size(); i++) {
			char c = tokens.get(i).getToken();
			Token token = tokens.get(i);
			
			//If token is a number
			if (token.getType() == Token.Type.NUMBER) {
				s.append(c); //append it to the postfix string
				//and while the following tokens are also numbers, 
				//append those too. Supports multi digit numbers.
				while (i + 1 != tokens.size() && tokens.get(i+1).getType() == Token.Type.NUMBER) {
					s.append(tokens.get(i+1).getToken());
					i++;
				}
				s.append(' '); //used for better display of postfix calculator
			} else if (c == '(') {
				stack.push(token);
			} else if (c == ')') {
				while (!stack.isEmpty() && stack.peek().getToken() != '(') {
					s.append(stack.pop().getToken());
					s.append(' ');
				}
				if (!stack.isEmpty() && stack.peek().getToken() != '(') {
					validExp = false;
				} else {
					stack.pop(); //gets rid of the parentheses
				}
			} else if (token.getType() == Token.Type.OPERATOR) {
				while (!stack.isEmpty() && (token.getPrecedence() <= stack.peek().getPrecedence())) {
					s.append(stack.pop().getToken());
					s.append(' ');
				}
				stack.push(token);
			} else {
				validExp = false;
				break;
			}
		}
		while (!stack.isEmpty()) {
			s.append(stack.pop().getToken());
			s.append(' ');
		}
		postfix = s.toString();
	}
	
	public double evaluate(String postfix) {
		String[] tokens = postfix.split(" ");
		Stack<Double> stack = new Stack<Double>();
		
		for (int i = 0; i < tokens.length; i++) {
			Token testToken = new Token(tokens[i].charAt(0));
			if (testToken.getType() == Token.Type.NUMBER) {
				int number = Integer.parseInt(tokens[i]);
				stack.push((double) number);
			} else if (testToken.getType() == Token.Type.OPERATOR) {
				if (stack.size() >= 2) {
					double num2 = stack.pop();
					double num1 = stack.pop();
					stack.push(calculate(num1, num2, testToken));
				} else {
					validExp = false;
				}
			}
		}
		if (validExp) {
			return stack.pop();
		} else {
			final JOptionPane invalid = new JOptionPane("Invalid Input.");
			final JDialog invalidD = invalid.createDialog("INVALID");
			invalidD.setLocation(10, 10);
			invalidD.setVisible(true);
			return 0;
		}
	}
	
	public double calculate(double num1, double num2, Token testToken) {
		double result = 0; 
		if (validExp) {
			switch (testToken.getToken()) {
				case '+':
					result = num1 + num2;
					break;
				case '-':
					result = num1 - num2;
					break;
				case '*': 
					result = num1 * num2;
					break;
				case '/':
					result = num1 / num2;
					break;
				case '^':
					result = Math.pow(num1, num2);
			}
			return result;
		} else {
			validExp = false;
			return 0;
		}
	}
	
	
	public ArrayList<Token> getTokens() { return tokens; }
	public String getPostfix() { return postfix; }
	public boolean getValidExp() { return validExp; }
	
	/*
	public static void main (String [] args) {
		Calculate Calculator = new Calculate();
		Calculator.receiveTokens();
		Calculator.postfix(Calculator.getTokens());
		
		System.out.print("Postfix: ");
		System.out.println(Calculator.getPostfix());
		System.out.print("Expression: ");
		System.out.println(Calculator.expression);
		System.out.print("Result: ");
		System.out.println(Calculator.evaluate(Calculator.getPostfix()));
	}
	*/
}
