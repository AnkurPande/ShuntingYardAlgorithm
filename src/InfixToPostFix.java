
public class InfixToPostFix {
 			
	boolean checkOp (String op){	 
		
		if(op.equals("x") || op.equals("/") || op.equals("-") || op.equals("+")){
			//Return true if operator.
			return true;
		}
		else{
			//Return false if operand.
			return false;
		}
	}
	
	//Precedence order -:  ~,# > *,/  >  +,-  > (
	//~ is unary minus and # is unary plus.
	int calcPrecedence(String operator){
		
		if(operator.equals("(")){
			return 0;
		}
		else if(operator.equals("+") || operator.equals("-")){
			return 1;				
		}
		else if(operator.equals("x") || operator.equals("/")){
			return 2;
		}else if(operator.equals("~") || operator.equals("#")){
			return 3;
		}
		else return 0;
		
	}
		
	public void convertInfixToPostFix(Queue inputQueue, Queue outputQueue, Stack stack){
		//Write all conversion code
		
		Node temp =inputQueue.front;
		String tokenPrev = null, tokenCurrent = null;
			
		//reading input queue 
		while(temp!=null){			
			// dequeue the token.
			tokenCurrent = inputQueue.dequeue();
		    		
		    if(tokenCurrent.equals("(")){
		    	//push the left parenthesis to the stack directly.
		    	stack.push(tokenCurrent);
		    }
		    else if(tokenCurrent.equals(")")){
		    	//pop all the operators from stack until you get left parenthesis
		    	while(true){
		    		//pop the top token out.
		    		String tempToken = stack.pop();
		    		
		    		//Check if left parenthesis if true break the loop.
		    		if(tempToken.equals("(")) break;
		    		
		    		//else push it to the queue.
		    		outputQueue.enqueue(tempToken);
		    	}
		    }
		    else if(this.checkOp(tokenCurrent)){
		    	
		    	/*Code for unary operator :-
		    	 *  1 . It must be unary operator If its the first token in the input queue.
		    	 *  2.  It immediately follows second operator and left parenthesis */
		    	
		    	if(tokenPrev == null || checkOp(tokenPrev) || tokenPrev.equals("(")){
		    		tokenCurrent = (tokenCurrent.equals("-")) ? "~" : "#";
		    	}
		    		    	
		    	//Calculate precedence value of token.
		    	int prec1 = this.calcPrecedence(tokenCurrent);
		    	//Continue doing until stack is empty or lower precedence operator on top
		    	while (stack.top!=null){
		    		String opTop = stack.pop();
		    		int prec2 = this.calcPrecedence(opTop);
		    		if(prec2<prec1){
		    			stack.push(opTop);
		    			break;			//or else infinite loop
		    		}
		    		else{
		    			outputQueue.enqueue(opTop);
		    		}
		    	}// while loop ends.
		    	//push is executed every time whether your stack is empty or not.
		      	stack.push(tokenCurrent);
		    }
		    else{
		    	outputQueue.enqueue(tokenCurrent);
		    }
			temp = temp.getNext();
			tokenPrev = tokenCurrent;
		}// loop for input queue ends.
		
		//Pop out everything leftover in the stack and push into output queue
		while(stack.top!=null){
			outputQueue.enqueue(stack.pop());
		}
	}
	
	float calculate(Queue outputQueue, Stack stack){
		// write all calculation code
		
		Node tempPtr = outputQueue.front;
		String tokenCurrent = null;
		float result = 0f ;
		
		while(tempPtr!=null){
			tokenCurrent=outputQueue.dequeue();
			
			if( tokenCurrent.equals("~") || tokenCurrent.equals("#") ){ // we got a unary operator.
				String temptokenCurrent = (tokenCurrent.equals("~")) ?  "-" : "+";
				temptokenCurrent = temptokenCurrent + stack.pop();
				stack.push(temptokenCurrent);
			}
			else if( !this.checkOp(tokenCurrent ) ){			//we got an operand
				stack.push(tokenCurrent);
			}
			else{
				float op2 = Float.parseFloat(stack.pop());			//convert string to float
				float op1 = Float.parseFloat(stack.pop());			//will be first in calculation
				switch(tokenCurrent){
					case "+": result = op1 + op2; 
					break;
					case "-": result = op1 - op2;
					break;
					case "x": result = op1 * op2;
					break;
					case "/": result = op1 / op2;
					break;
				}
				stack.push(String.valueOf(result));
			}
			
			tempPtr= tempPtr.getNext();
		}
		System.out.println(result);
		return Float.parseFloat(stack.pop());
	}
	
	
	public static void main(String args[]){
		System.out.println("***Infix to Postfix Converter***");
		System.out.print("Input : ");			
				
		//****************Initialize instances**********************************//
		Queue inputQueue=new Queue();
		Queue outputQueue = new Queue();
		Stack stack = new Stack();
		InfixToPostFix object = new InfixToPostFix();
		
		//********Read the command line and Fill up input queue ****************//
		for(int i=0; i<args.length; i++){
			System.out.print(args[i]);
			inputQueue.enqueue(args[i]);
		}
		System.out.println("");
		
		//*****************Infix to post fix conversion************************//
		
		object.convertInfixToPostFix(inputQueue, outputQueue, stack);
		System.out.print("PostFix Expression : ");
		outputQueue.printQueue();
						
		//**********************Calculate*************************************//
		
		//System.out.println("Calculation result : ");
		//System.out.println(object.calculate(outputQueue, stack));
		float val = 22 / 7;

		System.out.println(val);
		}
}
