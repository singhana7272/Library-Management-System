package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.bean.Book;
import com.bean.BookType;
import com.bean.CustomerCard;
import com.exception.BookNotFound;
import com.exception.BookTypeNotFound;

@Repository
public class BookDaoImpl implements BookDao{
	
	private static final String url="jdbc:mysql://localhost:3306/librarydb";
	private static final String user="root";
	private static final String password="admin";

	@Override
	public ArrayList<Book> listOfBookAvailable() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url,user,password);
		String query="Select * from books";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		ResultSet resultSet=preparedStatement.executeQuery();
		ArrayList<Book> books=new ArrayList<>();
		
		while(resultSet.next()) {
			String bookId=resultSet.getString("book_id");
			String bookTypeId=resultSet.getString("book_type_id");
			String bookName=resultSet.getString("book_name");
			String authorName=resultSet.getString("author_name");
			int noOfCopiesAvailable=resultSet.getInt("number_of_copies_available");
			Book book=new Book(bookId,bookTypeId,bookName,authorName,noOfCopiesAvailable);
			books.add(book);
		}
	connection.close();	
	return books;
	}

	@Override
	public Book searchBookById(String bookId) throws ClassNotFoundException, SQLException, BookNotFound {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url,user,password);
		String query="select * from books";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		ResultSet resultSet=preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			String bId=resultSet.getString("book_id");
			String bookTypeId=resultSet.getString("book_type_id");
			String bookName=resultSet.getString("book_name");
			String authorName=resultSet.getString("author_name");
			int numberOfCopiesAvailable=resultSet.getInt("number_of_copies_available");
			Book book=new Book(bId,bookTypeId,bookName,authorName,numberOfCopiesAvailable);
			if(bId.equals(bookId)) {
				return book;
			}
		}
		throw new BookNotFound("Book with "+ bookId+" not found");
	}

	@Override
	public BookType searchBookTypeById(String bookId) throws SQLException, ClassNotFoundException, BookTypeNotFound {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url,user,password);
		String query="select * from bookType where book_type_id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setString(1,bookId);
		ResultSet resultSet=preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			String bookTypeId=resultSet.getString("book_type_id");
			String book_type=resultSet.getString("book_type");
			int fine_per_day=resultSet.getInt("fine_per_day");
			BookType bookType=new BookType(bookTypeId, bookTypeId, fine_per_day);
			return bookType;
		}
		connection.close();
		throw new BookTypeNotFound("Book Type with "+ bookId + "is not available");
	}
}
