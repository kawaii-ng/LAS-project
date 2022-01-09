/*
 * 
 * @author: Ng Ka Wai 19019608D
 * 
 * ButtonPanel.java
 * 
 * */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ButtonPanel extends JPanel {

	MyLinkedList<Book> bookList = new MyLinkedList<>();
	MyTableModel model;
	JTextArea header;
	public static String filePath = "";
	
	// textfield 		
	JTextField isbnInput = new JTextField(10);
	JTextField titleInput = new JTextField(10);
	
	// action button 
	JButton addBtn = new JButton("Add");
	JButton editBtn = new JButton("Edit");
	JButton saveBtn = new JButton("Save");
	JButton delBtn = new JButton("Delete");
	JButton searchBtn = new JButton("Search");
	JButton moreBtn = new JButton("More>>");
	JButton loadBtn = new JButton("Load Test Data");
	JButton displayAllBtn = new JButton("Display All");
	JButton displayIsbnBtn = new JButton("Display All by ISBN");
	JButton displayTitleBtn = new JButton("Display All by Title");
	JButton openfBtn = new JButton("Open File");
	JButton savefBtn = new JButton("Save File");
	JButton exitBtn = new JButton("Exit");
	
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	
	public ButtonPanel() {
		
		this(new MyTableModel(), new JTextArea());
		
	}
	
	public ButtonPanel(MyTableModel model, JTextArea header) {
		
		this.model = model;
		this.header = header;
		System.out.println(filePath);
		
		// first row of the main panel
		
		p1.setLayout(new FlowLayout());
		p1.add(new JLabel("ISBN: "));
		p1.add(isbnInput);
		p1.add(new JLabel("Title: "));
		p1.add(titleInput);
		
		// second row of the main panel
		
		p2.setLayout(new FlowLayout());
		p2.add(addBtn);
		p2.add(editBtn);
		p2.add(saveBtn);
		p2.add(delBtn);
		p2.add(searchBtn);
		p2.add(moreBtn);
		
		// third row of the main panel
		
		p3.setLayout(new FlowLayout());
		p3.add(loadBtn);
		p3.add(displayAllBtn);
		p3.add(displayIsbnBtn);
		p3.add(displayTitleBtn);
		p3.add(openfBtn);
		p3.add(savefBtn);
		p3.add(exitBtn);
		
		// define the main panel to display the component
		setLayout(new GridLayout(3, 1, 5, 5));
		add(p1);
		add(p2);
		add(p3);
		saveBtn.setEnabled(false);
		
		// add action listener to the buttons
		
		addBtn.addActionListener(new ListenerHandler());
		loadBtn.addActionListener(new ListenerHandler());
		editBtn.addActionListener(new ListenerHandler());
		saveBtn.addActionListener(new ListenerHandler());
		delBtn.addActionListener(new ListenerHandler());
		searchBtn.addActionListener(new ListenerHandler());
		displayAllBtn.addActionListener(new ListenerHandler());
		displayIsbnBtn.addActionListener(new ListenerHandler());
		displayTitleBtn.addActionListener(new ListenerHandler());
		exitBtn.addActionListener(new ListenerHandler());
		moreBtn.addActionListener(new ListenerHandler());
		openfBtn.addActionListener(new ListenerHandler());
		savefBtn.addActionListener(new ListenerHandler());
		
	}
	
	class ListenerHandler implements ActionListener  {
		
		public void actionPerformed(ActionEvent e) {
			
			ActionCollection actionCollection = new ActionCollection();
			
			String isbn = isbnInput.getText();
			String title = titleInput.getText();
			
			updateTime();
			
			if(e.getSource() == addBtn) 
				actionCollection.addBook(isbnInput, titleInput, model, bookList);			
			
			if(e.getSource() == loadBtn)
				actionCollection.loadBook(model, bookList);
				
			if(e.getSource() == editBtn) {
						
				if(actionCollection.editBook(isbn, titleInput, model, bookList)) {
					
					// disable all buttons except save button
					addBtn.setEnabled(false);
					editBtn.setEnabled(false);
					delBtn.setEnabled(false);
					saveBtn.setEnabled(true);
					searchBtn.setEnabled(false);
					moreBtn.setEnabled(false);
					loadBtn.setEnabled(false);
					displayAllBtn.setEnabled(false);
					displayIsbnBtn.setEnabled(false);
					displayTitleBtn.setEnabled(false);
					exitBtn.setEnabled(false);
					
				}
				
			}
			
			if(e.getSource() == saveBtn) {
				
				if(actionCollection.saveBook(isbn, title, model, bookList)) {
					
					// enable all button except save button
					addBtn.setEnabled(true);
					editBtn.setEnabled(true);
					saveBtn.setEnabled(false);
					delBtn.setEnabled(true);
					searchBtn.setEnabled(true);
					moreBtn.setEnabled(true);
					loadBtn.setEnabled(true);
					displayAllBtn.setEnabled(true);
					displayIsbnBtn.setEnabled(true);
					displayTitleBtn.setEnabled(true);
					exitBtn.setEnabled(true);
					
					isbnInput.setText("");
					titleInput.setText("");
					
				}
				
			}
			
			if(e.getSource() == delBtn)
				actionCollection.deleteBook(isbnInput, titleInput, model, bookList);
		
			if(e.getSource() == searchBtn) 
				actionCollection.searchBook(isbnInput, titleInput, model, bookList);
		
			if(e.getSource() == displayAllBtn)
				actionCollection.displayAll(model, bookList);
			
			if(e.getSource() == displayIsbnBtn)
				actionCollection.displayBy("ISBN", model, bookList);
			
			if(e.getSource() == displayTitleBtn)
				actionCollection.displayBy("Title", model, bookList);
			
			if(e.getSource() == exitBtn)
				System.exit(0);
			
			if(e.getSource() == moreBtn)
				actionCollection.more(isbn, model, bookList);
			
			if(e.getSource() == openfBtn) {
				
				filePath = actionCollection.openFile(filePath, model, bookList);
				
			}
			
			if(e.getSource() == savefBtn) {
				
				filePath = actionCollection.saveFile(filePath, bookList);
				
			}
			
		}
		
		public void updateTime () {
			
			header.setText("Student Name and ID: Ng Ka Wai (19019608d)\n" + new Date().toString() + "\n");
			System.out.println(filePath);
			
		}
		
	}
	
	
}
