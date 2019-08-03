import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/*
 * Change to GridBagLayout
 */

public class Calculator implements ActionListener {
	private JTextField expressionInput = new JTextField();
	private JTextField expressionResult = new JTextField("Result = ");
	
	public Calculator() {
		JFrame frame = new JFrame("Andrew's Calculator v1.0");
		frame.setLayout(new GridLayout(3, 1));
		
		//Creating expression text field/instructions
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(3,1));
		JLabel expressionInputLabel = new JLabel("Type a valid numerical expression below: ");
		expressionInput.setSize(frame.getWidth(), 10);
		expressionResult.setEditable(false);
		
		topPanel.add(expressionInputLabel);
		topPanel.add(expressionInput);
		topPanel.add(expressionResult);
		
		//Creating the buttons, and adding them to panel
		JPanel buttonPanel = new JPanel();
		JButton plus = new JButton("+");
		JButton minus = new JButton("-");
		JButton multiply = new JButton("*");
		JButton divide = new JButton("/");
		JButton exponent = new JButton("^");
		
		//Creating button actions
		plus.addActionListener(this);
		plus.setActionCommand("add");
		minus.addActionListener(this);
		minus.setActionCommand("subtract");
		multiply.addActionListener(this);
		multiply.setActionCommand("multiply");
		divide.addActionListener(this);
		divide.setActionCommand("divide");
		exponent.addActionListener(this);
		exponent.setActionCommand("exponent");
		
		buttonPanel.add(plus);
		buttonPanel.add(minus);
		buttonPanel.add(multiply);
		buttonPanel.add(divide);
		buttonPanel.add(exponent);
		
		//Creating Submit Button
		JPanel bottomPanel = new JPanel();
		JButton submit = new JButton("Evaluate Expression");
		submit.addActionListener(this);
		submit.setActionCommand("evaluate");
		
		bottomPanel.add(submit);
		
		
		frame.add(topPanel);
		frame.add(buttonPanel);
		frame.add(bottomPanel);
		
		frame.getContentPane();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		String action = evt.getActionCommand();
		if (action.equals("add")) {
			expressionInput.setText(expressionInput.getText() + " + ");
		} else if (action.equals("subtract")) {
			expressionInput.setText(expressionInput.getText() + " - ");
		} else if (action.equals("multiply")) {
			expressionInput.setText(expressionInput.getText() + " * ");
		} else if (action.equals("divide")) {
			expressionInput.setText(expressionInput.getText() + " / ");
		} else if (action.equals("exponent")) {
			expressionInput.setText(expressionInput.getText() + " ^ ");
		} else if (action.equals("evaluate")) {
			Calculate calculate = new Calculate();
			calculate.receiveTokens(expressionInput.getText());
			calculate.postfix(calculate.getTokens());
			double result = calculate.evaluate(calculate.getPostfix());
			String displayResult = String.format("%.5f", result);
			expressionResult.setText(displayResult);
		}
	}
	
	//Main method
	public static void main(String[] args) {
		Calculator calculator = new Calculator();
	}
}
