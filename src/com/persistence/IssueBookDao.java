package com.persistence;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import com.bean.Book;
import com.bean.CustomerCard;
import com.bean.IssuedBook;
import com.exception.IssuedBookNotFound;

public interface IssueBookDao {
	
	boolean returnBook(String cardId,String bookId) throws SQLException, ClassNotFoundException;
	boolean issueBook(String cardId,String bookId,String issueDate) throws SQLException, ClassNotFoundException, ParseException;
	boolean updateNoOfCopies(String bookId,int noOfCopies) throws ClassNotFoundException, SQLException;
	void listOfBooksIssued(String cardId) throws ClassNotFoundException, SQLException;
	boolean updateNoOfBooksIssued(String cardId,int noOfBooksIssued) throws ClassNotFoundException, SQLException;
	IssuedBook searchIssuedBookById(String bookId) throws ClassNotFoundException, SQLException, IssuedBookNotFound;
}
