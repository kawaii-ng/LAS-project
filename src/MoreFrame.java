/*
 * 
 * @author: Ng Ka Wai 19019608D
 * 
 * MoreFrame.java
 * 
 * */

import javax.swing.*;
import java.awt.*;

public class MoreFrame extends JDialog {
	
	public MoreFrame() {
		
		this(new Book(), new MyLinkedList<Book>(), new MyTableModel());
		
	}
	
	public MoreFrame(Book book, MyLinkedList<Book> bookList, MyTableModel model) {
		
		// define component 

		// header
		JTextArea header = new JTextArea("ISBN: " + book.getISBN() + "\n" + "Title: " + book.getTitle() + "\n" + "Available: " + book.isAvailable() + "\n");
		
		// define the footer to show the system message
		JTextArea footer = new JTextArea();
		if(!book.isAvailable())
			footer.setText("The book is borrowed.");
		
		// JPanel to hold the button
		MoreButtonPanel p = new MoreButtonPanel(book, header, footer, bookList, model);
		
		setLayout(new BorderLayout());
		add(header, BorderLayout.NORTH);
		add(p, BorderLayout.CENTER);
		add(footer, BorderLayout.SOUTH);
		
		setTitle(book.getTitle());
		setSize(500, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

}
