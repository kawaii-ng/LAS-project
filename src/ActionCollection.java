/*
 * 
 * @author: Ng Ka Wai 19019608D
 * 
 * ActionCollection.java
 * 
 * */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class ActionCollection {
	
	private static int editIndex = 0;
	private static boolean isIsbnAsc = true;
	private static boolean isTitleAsc = true;
	
	// method to add book
	public void addBook(JTextField isbnInput, JTextField titleInput, MyTableModel model, MyLinkedList<Book> bookList) {
		
		// add record or show error message on the screen
		String isbn = isbnInput.getText();
		String title = titleInput.getText();
		addBook(isbn, title, model, bookList);
		isbnInput.setText("");
		titleInput.setText("");
		
	}
	
	public void addBook(String isbn, String title, MyTableModel model, MyLinkedList<Book> bookList) {
		
		// add record or show error message on the screen
		
		try {
			
			int isbnInt = Integer.parseInt(isbn);
			
			if(!(bookIndex(isbn, bookList) == -1))
				JOptionPane.showMessageDialog(null, "Error: book ISBN " + isbn + " is already in the database.");
			else if(title.isBlank())
				JOptionPane.showMessageDialog(null, "Error: Title can not be blank.");
			else {
				
				Book newBook = new Book();
				newBook.setISBN(isbn);
				newBook.setTitle(title);
				model.addRecord(newBook, bookIndex(newBook.getISBN(), bookList));
				bookList.add(newBook); 
				
			}	
			
		}catch(Exception e){
			
			if(isbn.isBlank())
				JOptionPane.showMessageDialog(null, "Error: ISBN can not be blank.");
			else
				JOptionPane.showMessageDialog(null, "Error: ISBN can contains number only.");
			
		}
		
	}
	
	// method to load the sample data into the system
	public void loadBook(MyTableModel model, MyLinkedList<Book> bookList) {
		
		Book newBook1 = new Book();
		newBook1.setISBN("0131450913");
		newBook1.setTitle("HTML How to Program");
		addBook(newBook1.getISBN(), newBook1.getTitle(), model, bookList);
		
		Book newBook2 = new Book();
		newBook2.setISBN("0131857576");
		newBook2.setTitle("C++ How to Program");
		addBook(newBook2.getISBN(), newBook2.getTitle(), model, bookList);
		
		Book newBook3 = new Book();
		newBook3.setISBN("0132222205");
		newBook3.setTitle("Java How to Program");
		addBook(newBook3.getISBN(), newBook3.getTitle(), model, bookList);
		
	}
	
	// method to edit book 
	public boolean editBook(String isbn, JTextField titleInput, MyTableModel model, MyLinkedList<Book> bookList) {

		String title = titleInput.getText();
		editIndex = bookIndex(isbn, bookList);

		if(bookList.size > 0) {
		
			if(editIndex != -1) {
				
				if(title.isBlank()) {
					// fill in the blank if title is empty
					String bookTitle = bookList.get(editIndex).getTitle();
					titleInput.setText(bookTitle);
					
				}
				
				return true;
					
				
			}else {
				
				if(isbn.isBlank())
					JOptionPane.showMessageDialog(null, "Error: ISBN can not be blank.");
				else
					JOptionPane.showMessageDialog(null, "Error: book ISBN is not in the database.");// error message to show isbn is not existed
				
			}
		
		}else {
		
			JOptionPane.showMessageDialog(null, "Error: Database is empty.");
			
		}
		
		return false;
		
	}
	
	public boolean saveBook(String isbn, String title, MyTableModel model, MyLinkedList<Book> bookList) {
		
		int index = bookIndex(isbn, bookList);
		
		// check whether isbn is duplicated or it has not change
		try {
			
			int isbnInt = Integer.parseInt(isbn);
					
			if((index != editIndex && index == -1) || index == editIndex) {
				
				// update bookList with the changes
				bookList.get(index).setTitle(isbn);
				bookList.get(index).setTitle(title);
				
				// store the changes & data into data array
				Object[] data = {
					isbn,
					title,
					bookList.get(editIndex).isAvailable()
				};
				
				// update the table info
				model.updateRecord(bookList.get(editIndex).getISBN(), data);
				
				return true;
				
			}else {
				
				// show error message to show the isbn is existed.
				JOptionPane.showMessageDialog(null, "Error: book ISBN exists in the current database.");
				
			}
			
		}catch(Exception e){
			
			JOptionPane.showMessageDialog(null, "Error: ISBN can contains number only.");
			
		}
		
		
		
		return false;
		
	}
	
	public void deleteBook(JTextField isbnInput, JTextField titleInput, MyTableModel model, MyLinkedList<Book> bookList) {
		
		String isbn = isbnInput.getText();
		String title = titleInput.getText();
		
		int index = bookIndex(isbn, bookList);
		
		if(!(isbn.isBlank() || index == -1)){
						
			model.deleteRecord(isbn);
			bookList.remove(index);
			isbnInput.setText("");
			titleInput.setText("");
			
		}else {
			
			// show error message to show the isbn is not existed or empty
			if(isbn.isBlank())
				JOptionPane.showMessageDialog(null, "Error: book ISBN should not be empty.");
			else if(index == -1)
				JOptionPane.showMessageDialog(null, "Error: book ISBN is not exists in the current database.");
			
		}
		
	}
	
	public void searchBook(JTextField isbnInput, JTextField titleInput,  MyTableModel model, MyLinkedList<Book> bookList) {
		
		//int index = bookIndex(isbn, bookList);
		
		String isbn = isbnInput.getText();
		String title = titleInput.getText();
		
		if(!(isbn.isBlank() && title.isBlank())) {
			
			model.clearAll();
			for(int i = 0; i < bookList.size(); i++) {
				
				Book currentBook = bookList.get(i);
				Object[] newRow = {currentBook.getISBN(), currentBook.getTitle(), currentBook.isAvailable()};
				if((currentBook.getISBN().contains(isbn) 
						&& !isbn.isBlank())
						|| (currentBook.getTitle().contains(title)
								&& !title.isBlank()) )
					model.addRecord(newRow);

			}
			
		}
		
		isbnInput.setText("");
		titleInput.setText("");
		
	}
	
	// method to show all the records without sorting
	public void displayAll(MyTableModel model, MyLinkedList<Book> bookList) {
		
		model.clearAll();
		model.showAll(bookList);
		
	}
	
	
	// method to display the record orderly
	public void displayBy(String type, MyTableModel model, MyLinkedList<Book> bookList) {
		
		Book[] bookArr = bookList.toArray(new Book[bookList.size()]);
		
		// perform sorting first & switch the asc
		if(type == "ISBN") {
			
			if(isIsbnAsc)
				Arrays.sort(bookArr, Comparator.comparing(Book::getISBN));
			else
				Arrays.sort(bookArr, Comparator.comparing(Book::getISBN).reversed());
			//bookList = sortBookList(bookList, "ISBN");
			isIsbnAsc = !isIsbnAsc;
			isTitleAsc = true;
			
		}
		if(type == "Title") {
			
			if(isTitleAsc)
				Arrays.sort(bookArr, Comparator.comparing(Book::getTitle));
			else
				Arrays.sort(bookArr, Comparator.comparing(Book::getTitle).reversed());
			//bookList = sortBookList(bookList, "Title");
			isTitleAsc = !isTitleAsc;
			isIsbnAsc = true;
			
		}
		
		// clear all row before show the record orderly
		model.clearAll();
		model.showAll(bookArr);
		
		
	}
	
	public String openFile(String filePath, MyTableModel model, MyLinkedList<Book> bookList) {
		
		String tempPath = "";
		String fileName = "";
		
		try {
						
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(null);
			File file = chooser.getSelectedFile();
			tempPath = file.getAbsolutePath();
			fileName = file.getName();
			
		}catch(Exception e){
			
			JOptionPane.showMessageDialog(null, e);
			
		}
		
		
		if(fileName.substring(fileName.length()-4, fileName.length()).equals(".csv")) {
			
			BufferedReader reader = null;
			
			try {
				
				System.out.println("opening");
				
				MyLinkedList<Book> newBookList = new MyLinkedList<>();
				String record = "";
				reader = new BufferedReader(new FileReader(tempPath));
				reader.readLine();
				
				while((record = reader.readLine()) != null) {
					
					String[] col = record.split(",");
					String waitingList = col[4].length() > 8 ? col[4].substring(8, col[4].length()-1):"";
					System.out.println(waitingList);
					String[] waitingArr = waitingList.split(", ");
					MyQueue<String> reservedQ = new MyQueue<>();
					for(int i = 0; i < waitingArr.length; i++) 
						reservedQ.enqueue(waitingArr[i]);
					
					Book newBook = new Book(col[1], col[0], (col[2].equals("true")), col[3], reservedQ);
					model.addRecord(newBook, bookIndex(newBook.getISBN(), bookList));
					bookList.add(newBook); 
					
				}
				//System.out.println(bookList.get(0));
				displayAll(model, bookList);
				
				reader.close();
				
				filePath = tempPath;
				
				return filePath;
				
				
			}catch(Exception e) {
				// error 
				JOptionPane.showMessageDialog(null, e);
				
			}
			
		}else {
			
			JOptionPane.showMessageDialog(null, "Error: File type is invalid. ");
			
		}		
		
		return filePath;
		
	}
	
	public String saveFile(String filePath, MyLinkedList<Book> bookList) {
		
		String tempPath = "";
		
		if(filePath == "") {
			
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("csv", "csv");
			chooser.setFileFilter(filter);
			chooser.showSaveDialog(null);
			File file = chooser.getSelectedFile();
			tempPath = file.getAbsolutePath() + ".csv";
			filePath = tempPath;
						
		}else {
			tempPath = filePath;
		}

		FileWriter fileWriter = null;
		try {
			
			fileWriter = new FileWriter(tempPath);
			fileWriter.append("ISBN, Title, Available, ImgPath, WaitinQueue\n");
			for(int i = 0; i < bookList.size(); i++) {
				
				Book currentBook = bookList.get(i);
				fileWriter.append(currentBook.getISBN());
				fileWriter.append(",");
				fileWriter.append(currentBook.getTitle());
				fileWriter.append(",");
				fileWriter.append(String.valueOf(currentBook.isAvailable()));
				fileWriter.append(",");
				fileWriter.append(currentBook.getPath());
				fileWriter.append(",");
				fileWriter.append(currentBook.getReservedQueue().toString());
				System.out.println(currentBook.getReservedQueue().toString());
				fileWriter.append("\n");

			}
			
			fileWriter.flush();
			fileWriter.close();
			
			filePath = tempPath;
			
			JOptionPane.showMessageDialog(null, "Message: File saved.");
			
			return filePath;
			
		}catch (Exception e) {
			
			// error 
			JOptionPane.showMessageDialog(null, e);
			
		}
		
		return filePath;
					
	}
	
	public void more(String isbn, MyTableModel model, MyLinkedList<Book> bookList) {
		
		int index = bookIndex(isbn, bookList);
		
		if(!(isbn.isBlank() || index == -1)) {
			
			new MoreFrame(bookList.get(index), bookList, model);
			
		}else {
			
			// show error message 
			if(isbn.isBlank())
				JOptionPane.showMessageDialog(null, "Error: Please select a book or a ISBN.");
			else
				JOptionPane.showMessageDialog(null, "Error: book ISBN is not exists in the current database.");
			
		}
		
	}
	
	// method to get the index of book within bookList
	public int bookIndex(String isbn, MyLinkedList<Book> bookList) {
		
		// check whether it is exits first
		for(int i = 0; i < bookList.size(); i++) {
			
			if((bookList.get(i)).getISBN().equals(isbn)) 
				return i;
			
		}
		
		return -1;
		
	}	

}
