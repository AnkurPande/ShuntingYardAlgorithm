
public class Stack {

	Node top = null;
	public Stack() {
		// TODO Auto-generated constructor stub
	}
	
	public void push(String data){
		Node newNode = new Node(null, data);
		if(top == null){
			top = newNode;
		}else {
			newNode.setNext(top);
			top = newNode;
		}
	}

	public String pop(){
		String tempData = null;		
		if(top==null){
			return null;
		}
		else {
			tempData = top.getData();
			top = top.getNext();
			return tempData;
		}
	}
	
	public void printStack(){
		Node ptr = top;
		System.out.println();
		while(ptr!=null){
			System.out.print(ptr.getData()+" ");
			ptr = ptr.getNext();
		}
	}
}
