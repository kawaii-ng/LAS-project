/*
 * 
 * @author: Ng Ka Wai 19019608D
 * 
 * MyTableModel.java
 * 
 * */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {
	
	private String[] column = {"ISBN", "Title", "Available"};
	public DefaultTableModel model;
	
	public MyTableModel() {
		
		model = new DefaultTableModel();
		model.setColumnIdentifiers(column);
		
	}

	public void addRecord(Book newBook, int bookIndex) {
		
		if(bookIndex != -1) {
			
			JOptionPane.showMessageDialog(null, "ISBN has already existed.");
			
		}else {
			
			Object[] newRecord = {newBook.getISBN(), newBook.getTitle(), newBook.isAvailable()};
			model.addRow(newRecord);
			
		}
		
	}
	
	public void addRecord(Object[] data) {
		
		model.addRow(data);
		
	}
	
	public void updateRecord(String isbn, Object[] data) {
		
		for(int i = 0; i < this.getRow(); i++) {
			
			if(model.getValueAt(i, 0).equals(isbn)) {

				for(int j = 0; j < 3; j++) {
					model.setValueAt(data[j], i, j);
				}
				
			}
			
		}
		
	}
	
	public void deleteRecord(String isbn) {
		
		for(int i = 0; i < this.getRow(); i++) {
			
			if(model.getValueAt(i, 0).equals(isbn))
				model.removeRow(i);

			
		}
		
	}
	
	public int getRow() {
		
		return model.getRowCount();
		
	}
	
	public void clearAll() {
		
		model.setNumRows(0);
		
	}
	
	public void showAll(MyLinkedList<Book> bookList) {
		
		for(int i = 0; i < bookList.size(); i++) {
			
			Book currentBook = bookList.get(i);
			Object[] newRow = {currentBook.getISBN(), currentBook.getTitle(), currentBook.isAvailable()};
			boolean isShow = false;
			
			for(int j = 0; j < this.getRow(); j++) {
				
				if(model.getValueAt(j, 0).equals(currentBook.getISBN()))
					isShow = true;
				
			}
			if(!isShow)
				model.addRow(newRow);						

		}
		
	}
	
	public void showAll(Book[] bookList) {
		
		for(int i = 0; i < bookList.length; i++) {
			
			Book currentBook = bookList[i];
			Object[] newRow = {currentBook.getISBN(), currentBook.getTitle(), currentBook.isAvailable()};
			boolean isShow = false;
			
			for(int j = 0; j < this.getRow(); j++) {
				
				if(model.getValueAt(j, 0).equals(currentBook.getISBN()))
					isShow = true;
				
			}
			if(!isShow)
				model.addRow(newRow);						

		}
		
	}
	
}
