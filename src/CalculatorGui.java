import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import acm.gui.DoubleField;
import acm.gui.TableLayout;
import acm.program.Program;


public class CalculatorGui extends Program implements ChangeListener {
	

	private static final long serialVersionUID = 1L;
	private static final int  BUTTON_SIZE = 50;
	private static final int  DISPLAY_SIZE = 30;
	private int tokenCounter;
	private String currentToken, prevToken, prevPrevToken;
	private int leftParenthesis;
	String[] buttonString = {"7", "8", "9", "+",
            				 "4", "5", "6", "-",
            				 "1", "2", "3", "x",
            				 "0", ".", "/", "C",
            				 "+/-","(",")","="};
	
	JButton[] button = null;
	JTextField displayInfix = null;
	DoubleField result = new DoubleField(0.0);
	JSlider slider = null;
	JTextField precision = null;
	
	Queue inputQueue=null;
	Queue outputQueue =null;
	Stack stack = null;
	InfixToPostFix in2pJ = null;
	
	public void initialize(){
		
		inputQueue=new Queue();
	    outputQueue=new Queue();
	    stack=new Stack();
	    in2pJ=new InfixToPostFix();
	        
	    tokenCounter =0;
	    currentToken = null;
	    prevToken = null;
	    prevPrevToken =null;
	    leftParenthesis = 0;
	    
	    displayInfix.setText("0");
		slider.setValue(6);
	}
	
	public void init(){
		
		setLayout(new TableLayout(8,4));
		setSize(250, 350);
		
		
		//Initialize UI elements.
		button = new JButton[20];
	    displayInfix = new JTextField("0");
		slider = new JSlider(JSlider.HORIZONTAL,0,12,6);
		precision = new JTextField(Integer.toString(slider.getValue()));
		
		//Initialize variables and constants.
		initialize();
		
		//Set layout of elements.
		displayInfix.setHorizontalAlignment(JTextField.RIGHT);
	    precision.setHorizontalAlignment(JTextField.RIGHT);
		add(displayInfix, "gridwidth= 4 height=" + DISPLAY_SIZE);
		add(result, "gridwidth= 4 height=" + DISPLAY_SIZE);
       	addButtons();
       	add(slider, "gridwidth= 2 height=" + 30);
       	add(precision, "gridwidth= 2 height=" +30);
       	
       	//Add listeners.
       	slider.addChangeListener(this);
       	precision.addActionListener(this);
       	addActionListeners();
	}
	
	public void addButtons(){
		for(int i = 0; i<20;i++){
			button[i] = new JButton();
			button[i].setText(buttonString[i]);
			add(button[i], "width=" + BUTTON_SIZE + " height=" + BUTTON_SIZE);
		}
	}
	
	public void actionPerformed(ActionEvent ae){
		String buttonString = ae.getActionCommand();
		
		//empty button string variable if precision value is provided.
		if(ae.getSource() == precision){
			buttonString = "";
		}
		
		//Increment the token counter.
		tokenCounter++;
		
		//Check if its the first token then update only current token.
		if(tokenCounter==1){
			currentToken = buttonString;
			if(!in2pJ.checkOp(currentToken) || currentToken.equals("(") ) 
				displayInfix.setText("");
		
		//If second token then update both current token and previous token.	
		}else if(tokenCounter==2){
			prevToken = currentToken;
			currentToken = buttonString;
		//If third token update all of them.	
		}else{
			prevPrevToken = prevToken;
			prevToken = currentToken;
			currentToken = buttonString;
		}
				
		switch(buttonString){
		case "0":
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "1":
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "2":
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "3":
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "4":
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "5":
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "6":
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "7":
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "8":
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "9":
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "+":
			//Check for previous tokens.
			if(!checkPrevOperators()){ 
				buttonString = "";
			}
			
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "-":
			//Check for previous tokens.
			if(!checkPrevOperators()){ 
				buttonString = "";
			}
			
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "x":
			//Check for previous tokens.
			if(!checkPrevOperators()){ 
				buttonString = "";
			}
			
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "/":
			//Check for previous tokens.
			if(!checkPrevOperators()){ 
				buttonString = "";
			}
			
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "C":
			initialize();	
			result.setValue(0.0);
			break;
		case "=":
			String temp = displayInfix.getText();
			buttonString = temp +buttonString;
			displayInfix.setText(buttonString);
			tokenization(temp);
			in2pJ.convertInfixToPostFix(inputQueue, outputQueue, stack);
			double truncatedVal = setPrecision(in2pJ.calculate(outputQueue, stack));
			result.setValue(truncatedVal);
			break;
		case ".":
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case "(":
			//Increment left parenthesis.
			leftParenthesis++;
			
			//Check for previous token.
			if(prevToken==null || in2pJ.checkOp(prevToken)){
				buttonString = "(";
			}	
			else{
				buttonString = "";
			}
			
			buttonString = displayInfix.getText() +buttonString;
			displayInfix.setText(buttonString);
			break;
		case ")":
			//Check for previous token.
			if(prevToken == null || leftParenthesis==0 ) {
				buttonString = "0";
				currentToken = null;
			}
			
			//Check for left parenthesis and print equal number of right parenthesis.
			if(leftParenthesis >0){
				//Add 0 in between two parenthesis.
				if(prevToken.equals("(")) 
					buttonString =  "0" + buttonString ;
											
				buttonString = displayInfix.getText() +buttonString;
				leftParenthesis--;
			}
			
			displayInfix.setText(buttonString);
			break;
		}
				
		if(ae.getSource()==precision){
			int typed = Integer.parseInt(precision.getText());
			slider.setValue(typed);
			double truncatedVal = setPrecision(result.getValue());
			result.setValue(truncatedVal);
		}
	}
	
	public boolean checkPrevOperators(){
		
		//If current token is not an operator return false.
		if(!in2pJ.checkOp(currentToken))
			return false;
		
		//If current token is an operator and its first token of infix expression return false.
		if(prevToken == null)
			return false;
		
		//If current token is x or / then return false if previous token is also an operator. 
		if( ( currentToken.equals("x") || currentToken.equals("/") ) && in2pJ.checkOp(prevToken) )
			return false;
		
		//If current token is + or - then return false if previous token and previous previous token both of them are also operators. 
		if( ( ( currentToken.equals("+") || currentToken.equals("-") ) && in2pJ.checkOp(prevToken) && in2pJ.checkOp(prevPrevToken) ) )
			return false;
	
		// At last the expression is valid hence return true finally.
		return true;
	}
	
	public double setPrecision(double d){
		double truncatedVal = BigDecimal.valueOf(d).setScale(slider.getValue(), RoundingMode.HALF_UP).doubleValue();
		return  truncatedVal;
	}
	
	public void tokenization(String str){
		
		StreamTokenizer r = new StreamTokenizer(new BufferedReader(new StringReader(str))); 
		r.ordinaryChar('/');
		r.ordinaryChar('x');
		r.ordinaryChar('-');
		try {
		boolean eof = false;
		do{
			int token;
			
				token = r.nextToken();
				switch(token){
				case StreamTokenizer.TT_EOF:
					eof = true;
					break;
				case StreamTokenizer.TT_EOL:
					System.out.println("End of Line encountered : ");
					break;
				case StreamTokenizer.TT_NUMBER:
					System.out.println("Number: "+r.nval);
					inputQueue.enqueue(Double.toString(r.nval));
					break;
				default:System.out.println((char)(token)); 
					inputQueue.enqueue(""+(char)(token));
				break;
			}
			
		}while(!eof);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		inputQueue.printQueue();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		precision.setText(Integer.toString(slider.getValue()));
		double truncatedVal = setPrecision(result.getValue());
		result.setValue(truncatedVal);
	}
		
}

