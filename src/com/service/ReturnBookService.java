package com.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import com.bean.BookType;
import com.bean.IssuedBook;

public interface ReturnBookService {
	boolean returnBook(String cardId,String bookId) throws ClassNotFoundException, SQLException;
	void calculateFine(BookType bookType,IssuedBook issuedBook) throws ParseException;
}
