
public class Queue {
	
   Node front = null, rear = null;
	
	public Queue() {
		// TODO Auto-generated constructor stub
	}

	public void enqueue(String data){
		
		Node newNode = new Node(null, data);
		if(rear==null){
			front = rear = newNode;
		}
		else{
			rear.setNext(newNode);
			rear = newNode;
		}
	}
	
	public String dequeue(){
		
		String tempData = null;
		if(front==null){
			System.out.println("Queue is Empty");
			return null;
		}
		else{
			tempData = front.getData();
			front = front.getNext();
			return tempData;
		}
	}
	
	public void printQueue(){
		String printExpression = " ";
		Node ptr = front;
		while(ptr!=null){
			printExpression = printExpression + ptr.getData()+" ";
			ptr = ptr.getNext();
		}
		
		printExpression = printExpression.replace("~", "-");
		printExpression = printExpression.replace("#", "+");
		System.out.print(printExpression);
		System.out.println();
	}
}