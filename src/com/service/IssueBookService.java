package com.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import com.bean.Book;
import com.bean.CustomerCard;
import com.bean.IssuedBook;
import com.exception.IssuedBookNotFound;

public interface IssueBookService {
	boolean issueBook(String cardId,String bookId,String issueDate) throws ClassNotFoundException, SQLException, ParseException;
	void listOfBooksIssued(String cardId) throws ClassNotFoundException, SQLException;
	boolean updateNoOfCopies(String bookId,int noOfCopies) throws ClassNotFoundException, SQLException;
	boolean updateNoOfBooksIssued(String cardId,int noOfBooksIssued) throws ClassNotFoundException, SQLException;
	IssuedBook searchIssuedBookById(String bookId) throws ClassNotFoundException, SQLException, IssuedBookNotFound;
}
