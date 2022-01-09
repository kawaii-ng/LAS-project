/*
 * 
 * @author: Ng Ka Wai 19019608D
 * 
 * MoreButtonPanel.java
 * 
 * */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MoreButtonPanel extends JPanel {
	
	Book book;
	JTextArea info;
	JTextArea sysMessage;
	MyTableModel model;
	MyLinkedList<Book> bookList;

	// define the buttons
	JButton borrowBtn = new JButton("Borrow");
	JButton returnBtn = new JButton("Return");
	JButton reserveBtn = new JButton("Reserve");
	JButton queueBtn = new JButton("Waiting Queue");
	JButton browseBtn = new JButton("Browse");
	
	// show image in the new panel
	JPanel buttonP = new JPanel();
	JPanel imageP = new JPanel();
	JLabel bookCover = new JLabel();
	
	public MoreButtonPanel() {
		
		this(new Book(), new JTextArea(), new JTextArea(), new MyLinkedList<Book> (), new MyTableModel());
		
	}
	
	public MoreButtonPanel(Book book, JTextArea info, JTextArea sysMessage, MyLinkedList<Book> bookList, MyTableModel model) {

		this.book = book;
		this.info = info;
		this.sysMessage = sysMessage;
		this.model = model;
		this.bookList = bookList;
				
		// set the status of the buttons
		borrowBtn.setEnabled(book.isAvailable());
		returnBtn.setEnabled(!book.isAvailable());
		reserveBtn.setEnabled(!book.isAvailable());
		queueBtn.setEnabled(!book.isAvailable());
			
		
		// image panel 
		buttonP.setLayout(new FlowLayout());
		buttonP.add(borrowBtn);
		buttonP.add(returnBtn);
		buttonP.add(reserveBtn);
		buttonP.add(queueBtn);
		buttonP.add(browseBtn);

		imageP.setLayout(new FlowLayout());
		bookCover.setPreferredSize(new Dimension(180, 300));
		imageP.add(bookCover);

		updateImage();
		System.out.println(book.getPath());
		
		// add button to the panel
		setLayout(new BorderLayout());
		add(buttonP, BorderLayout.NORTH);
		add(imageP, BorderLayout.CENTER);
		
		
		// add action listener to the buttons
		borrowBtn.addActionListener(new ListenerHandler());
		returnBtn.addActionListener(new ListenerHandler());
		reserveBtn.addActionListener(new ListenerHandler());
		queueBtn.addActionListener(new ListenerHandler());
		browseBtn.addActionListener(new ListenerHandler());
		
	}
	
	public void updateImage() {
		
		// set book cover's image 
		try {
			
			String path = book.getPath();			
			Image img = Toolkit.getDefaultToolkit().createImage(path);
			img = img.getScaledInstance(180, 300, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(img);
			bookCover.setIcon(icon);
						
		}catch(Exception e) {
			
			// no image have been loaded
			System.out.println("Error: " + e.toString());
			
		}
		
		
	}
	
	class ListenerHandler implements ActionListener  {
		
		public void actionPerformed(ActionEvent e) {
			
			MoreActionCollection moreActionCollection = new MoreActionCollection();
			
			if(e.getSource() == borrowBtn) {

				moreActionCollection.borrowBook(book, sysMessage);
				update();				
				
			}
			
			if(e.getSource() == returnBtn) {
				
				moreActionCollection.returnBook(book, sysMessage);
				update();
				
			}
			
			if(e.getSource() == reserveBtn) 				
				moreActionCollection.reserveBook(book, sysMessage);

			if(e.getSource() == queueBtn)
				moreActionCollection.showQueue(book, sysMessage);
			
			if(e.getSource() == browseBtn) {
				
				moreActionCollection.browseImage(book, bookCover, sysMessage);
				
			}
			
		}
		
		// the method to update the frame with the updated information
		public void update() {
			
			borrowBtn.setEnabled(book.isAvailable());
			returnBtn.setEnabled(!book.isAvailable());
			reserveBtn.setEnabled(!book.isAvailable());
			queueBtn.setEnabled(!book.isAvailable());
			
			info.setText("ISBN: " + book.getISBN() + "\n" + "Title: " + book.getTitle() + "\n" + "Available: " + book.isAvailable() + "\n");
			
			model.clearAll();
			model.showAll(bookList);
			
		}
		
	}
	
}
