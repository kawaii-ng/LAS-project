/*
 * 
 * @author: Ng Ka Wai 19019608D
 * 
 * MoreActionCollection.java
 * 
 * */

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MoreActionCollection {
	
	// method to borrow book 
	public void borrowBook(Book book, JTextArea sysMessage) {
		
		// if book is available, it can be borrowed
		if(book.isAvailable()) {
			
			book.setAvailable(false);
			sysMessage.setText("The book is borrowed");
			
		}
		
	}
	
	// method to return the book
	public void returnBook(Book book, JTextArea sysMessage) {
		
		String message = "The book is returned.\n";
		
		if(book.getReservedQueue().getSize() > 0) {
			
			String nextUser = book.getReservedQueue().getList().getFirst();
			message += "The book is now borrowed by " + nextUser + ".";
			book.getReservedQueue().dequeue();		
			
		}else {
			
			book.setAvailable(true);
			
		}	
		
		sysMessage.setText(message);
		
	}
	
	// method to reserve book
	public void reserveBook(Book book, JTextArea sysMessage) {
		
		// get the username by dialog
		String user = JOptionPane.showInputDialog("What's your name?");
		book.getReservedQueue().enqueue(user);
		sysMessage.setText("The book is reserved by " + user + ".");
		
	}
	
	// method to show the waiting queue
	public void showQueue(Book book, JTextArea sysMessage) {
		
		MyQueue<String> q = book.getReservedQueue();
		String message = "The waiting queue: \n";
		
		for (int i = 0; i < q.getSize(); i++) {
			
			String user = q.getList().get(i);
			message += user + "\n";
			
		}
		
		sysMessage.setText(message);
		System.out.println((book.getReservedQueue().toString()).substring(7));
		
	}
	
	// method to browse and add image to the book 
	public void browseImage(Book book, JLabel bookCover, JTextArea sysMessage) {
		
		try {
			
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(null);
			File file = chooser.getSelectedFile();
			String path = file.getAbsolutePath();
			String fileName = file.getName();
			String type = fileName.substring(fileName.length()-4, fileName.length());
			
			if(type.equalsIgnoreCase(".jpg")|| type.equalsIgnoreCase(".png") || type.equalsIgnoreCase(".jpeg")) {
				
				Image img = Toolkit.getDefaultToolkit().createImage(path);
				img = img.getScaledInstance(bookCover.getWidth(), bookCover.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(img);
				bookCover.setIcon(icon);
				
				book.setPath(path);
				
				sysMessage.setText("Book cover has been updated.");
				
			}else {
				
				JOptionPane.showMessageDialog(null, "Error: File type is not supported.");
				
			}
			
		}catch(Exception e) {
			
			System.out.println("width: " + bookCover.getWidth());
			System.out.println("height: " + bookCover.getHeight());
			sysMessage.setText(e.toString());
			
		}
		
	}
	
}
