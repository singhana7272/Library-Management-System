package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.bean.Book;
import com.bean.CustomerCard;
import com.bean.IssuedBook;
import com.exception.IssuedBookNotFound;

@Repository
public class IssueBookDaoImpl implements IssueBookDao {
	
	private static final String url="jdbc:mysql://localhost:3306/librarydb";
	private static final String user="root";
	private static final String password="admin";

	@Override
	public boolean issueBook(String cardId,String bookId,String issueDate) throws SQLException, ClassNotFoundException, ParseException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection(url, user, password);
		String query="insert into issuedbook(card_id,book_id,issued_date,expected_return_date) values(?,?,?,?)";
		
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setString(1, cardId);
		preparedStatement.setString(2, bookId);
		preparedStatement.setString(3, issueDate);
		Date date=new SimpleDateFormat("yyyy-MM-dd").parse(issueDate);  
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 7); 
		Date dateWith7Days = cal.getTime();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String returnDate = formatter.format(dateWith7Days);     
		preparedStatement.setString(4, returnDate);
		int row=preparedStatement.executeUpdate();
		connection.close();
		if(row>0)
			return true;
		else
			return false;
	}

	@Override
	public void listOfBooksIssued(String cardId) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection(url, user, password);
		String query="select * from issuedbook where card_id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setString(1, cardId);
		ResultSet resultSet=preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			System.out.println("Book Id:"+ resultSet.getString("book_id"));
			System.out.println("Issued date:"+ resultSet.getString("issued_date"));
			System.out.println("Expected Return Date:"+resultSet.getString("expected_return_date"));
			System.out.println();
		}
		connection.close();
	}


	@Override
	public boolean updateNoOfCopies(String bookId, int noOfCopies) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection(url, user, password);
		String query="Update books set number_of_copies_available=? where book_id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setInt(1, noOfCopies);
		preparedStatement.setString(2, bookId);
		int rows=preparedStatement.executeUpdate();
		connection.close();
		if(rows>0)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean updateNoOfBooksIssued(String cardId,int noOfBooksIssued) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url,user,password);
		String query="update customercard set no_of_books_issued=? where card_id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setInt(1, noOfBooksIssued);
		preparedStatement.setString(2,cardId);
		int row=preparedStatement.executeUpdate();
		connection.close();
		if(row>0)
			return true;
		else
			return false;
	
	}

	@Override
	public boolean returnBook(String cardId, String bookId) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url,user,password);
		String query="delete from issuedbook where card_id=? and book_id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setString(1, cardId);
		preparedStatement.setString(2, bookId);
		int rows=preparedStatement.executeUpdate();
		connection.close();
		if(rows>0)
			return true;
		else
			return false;
	}

	@Override
	public IssuedBook searchIssuedBookById(String bookId) throws ClassNotFoundException, SQLException, IssuedBookNotFound {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url,user,password);
		String query="select * from issuedbook where book_id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setString(1, bookId);
		ResultSet resultSet=preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			String cardId=resultSet.getString("card_id");
			String bId=resultSet.getString("book_id");
			String issued_date=resultSet.getString("issued_date");
			String expected_return_date=resultSet.getString("expected_return_date");
			IssuedBook issuedBook=new IssuedBook(cardId, bookId, issued_date, expected_return_date);
			connection.close();
			return issuedBook;
		}
		throw new IssuedBookNotFound("Issued Book with bookId:"+bookId+" not found in your card");
	}

}
