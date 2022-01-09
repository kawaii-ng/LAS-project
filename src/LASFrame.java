/*
 * 
 * @author: Ng Ka Wai 19019608D
 * 
 * LASFrame.java
 * 
 * */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class LASFrame extends JFrame {
	
	public LASFrame() {
		
		// define component 
		
		// header
		JTextArea header = new JTextArea("Student Name and ID: Ng Ka Wai (19019608d)\n" + new Date().toString() + "\n");
		
		// table 
		// define the column of the table
		JTable bookTable = new JTable();
		MyTableModel myTableModel = new MyTableModel();
		bookTable.setModel(myTableModel.model);
		JScrollPane scrollpane = new JScrollPane(bookTable);
		
		// define the button panel
		ButtonPanel buttonPanel = new ButtonPanel(myTableModel, header);
		
		setLayout(new BorderLayout());
		add(header, BorderLayout.NORTH);
		add(scrollpane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		// add listener to allow user select from table
		bookTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int row = bookTable.rowAtPoint(e.getPoint());
				if(row >= 0) {
				
					String selectedISBN = bookTable.getModel().getValueAt(row, 0).toString();
					String selectedTitle = bookTable.getModel().getValueAt(row, 1).toString();
					
					buttonPanel.isbnInput.setText(selectedISBN);
					buttonPanel.titleInput.setText(selectedTitle);
					
				}	
				
			}			
			
		});

	}
	
	public static void main(String[] args) {
		
		// create the window of LAS
		LASFrame frame = new LASFrame();
		frame.setTitle("Library Admin System");
		frame.setSize(1000, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
