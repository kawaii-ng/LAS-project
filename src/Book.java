/*
 * 
 * @author: Ng Ka Wai 19019608D
 * 
 * Book.java
 * 
 * */

public class Book {

	private String title; // store the title of the book
	private String ISBN; // store the ISBN of the book
	private boolean available; // keep the status of whether the book is available;
	private String path;
	
	private MyQueue<String> reservedQueue; // store the queue of waiting list
	
	public Book() {
		
		available = true;
		reservedQueue = new MyQueue<>();
		
	}
	
	public Book(String title, String ISBN, boolean available, String path, MyQueue<String> q) {
		
		this.title = title;
		this.ISBN = ISBN;
		this.available = available;
		this.path = path;
		this.reservedQueue = q;
		
	}
	
	public void setISBN(String s) {
		
		ISBN = s;
		
	}
	
	public String getISBN() {
		
		return ISBN;
		
	}
	
	public void setTitle(String s) {
		
		title = s;
		
	}
	
	public String getTitle() {
		
		return title;
		
	}
	
	public void setAvailable(boolean b) {
		
		available = b;
		
	}
	
	public boolean isAvailable() {
		
		return available;
		
	}
	
	public MyQueue<String> getReservedQueue() {
		
		return reservedQueue;
		
	}
	
	public void setPath(String p) {
		
		path = p;
		
	}
	
	public String getPath() {
		
		return path;
		
	}
	
}
