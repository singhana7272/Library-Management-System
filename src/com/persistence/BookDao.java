package com.persistence;

import java.sql.SQLException;
import java.util.ArrayList;

import com.bean.Book;
import com.bean.BookType;
import com.bean.CustomerCard;
import com.exception.BookNotFound;
import com.exception.BookTypeNotFound;

public interface BookDao {
	Book searchBookById(String bookId) throws ClassNotFoundException, SQLException, BookNotFound;
	ArrayList<Book> listOfBookAvailable() throws ClassNotFoundException, SQLException;
	BookType searchBookTypeById(String bookId) throws SQLException, ClassNotFoundException, BookTypeNotFound;
	
}
