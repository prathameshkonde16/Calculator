package simpleCalculatorSwing;
	
	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;

	public class SimpleCalculatorSwing {
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            JFrame frame = new CalculatorFrame();
	            frame.setTitle("Simple Calculator");
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setVisible(true);
	        });
	    }
	}

	class CalculatorFrame extends JFrame {
	    private JTextField display;

	    private double firstOperand;
	    private String operator;
	    private boolean startNewInput = true;

	    public CalculatorFrame() {
	        display = new JTextField();
	        display.setEditable(false);
	        display.setHorizontalAlignment(JTextField.RIGHT);
	        display.setFont(new Font("Arial", Font.PLAIN, 20));

	        JPanel buttonPanel = new JPanel();
	        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));

	        String[] buttonLabels = {
	            "7", "8", "9", "/",
	            "4", "5", "6", "*",
	            "1", "2", "3", "-",
	            "0", ".", "=", "+"
	        };

	        for (String label : buttonLabels) {
	            JButton button = new JButton(label);
	            button.setFont(new Font("Arial", Font.PLAIN, 20));
	            button.addActionListener(new ButtonClickListener());
	            buttonPanel.add(button);
	        }

	        setLayout(new BorderLayout());
	        add(display, BorderLayout.NORTH);
	        add(buttonPanel, BorderLayout.CENTER);
	    }

	    private class ButtonClickListener implements ActionListener {
	        public void actionPerformed(ActionEvent event) {
	            String command = event.getActionCommand();

	            if (Character.isDigit(command.charAt(0))) {
	                if (startNewInput) {
	                    display.setText(command);
	                    startNewInput = false;
	                } else {
	                    display.setText(display.getText() + command);
	                }
	            } else if (command.equals(".")) {
	                if (startNewInput) {
	                    display.setText("0.");
	                    startNewInput = false;
	                } else if (display.getText().indexOf('.') == -1) {
	                    display.setText(display.getText() + ".");
	                }
	            } else if (command.equals("=")) {
	                if (!startNewInput) {
	                    double secondOperand = Double.parseDouble(display.getText());
	                    double result = performOperation(firstOperand, secondOperand, operator);
	                    display.setText(String.valueOf(result));
	                    startNewInput = true;
	                }
	            } else {
	                if (!startNewInput) {
	                    firstOperand = Double.parseDouble(display.getText());
	                    operator = command;
	                    startNewInput = true;
	                }
	            }
	        }

	        private double performOperation(double num1, double num2, String op) {
	            switch (op) {
	                case "+":
	                    return num1 + num2;
	                case "-":
	                    return num1 - num2;
	                case "*":
	                    return num1 * num2;
	                case "/":
	                    if (num2 != 0) {
	                        return num1 / num2;
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Error: Division by zero.", "Error", JOptionPane.ERROR_MESSAGE);
	                        return 0;
	                    }
	                default:
	                    return 0;
	            }
	        }
	    }
	}
