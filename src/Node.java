
public class Node {

	private Node next = null;
	private String data = null;

	public Node(Node node, String str) {
		// TODO Auto-generated constructor stub
		next = node;
		data = str;
	}
	
	public String getData(){
		return data;
	}
	
	public Node getNext(){
		return next;
	}
	
	public void setNext(Node node){
		next = node;
	}
	
	public void setData(String data){
		this.data = data;
	}
}
