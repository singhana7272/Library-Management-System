package com.presentation;

import java.sql.SQLException;
import java.text.ParseException;

import com.exception.BookNotFound;
import com.exception.BookTypeNotFound;
import com.exception.CardNotFound;
import com.exception.InvalidInputException;
import com.exception.IssuedBookNotFound;

public interface CustomerPresentation {
	void showLoginMenu();
	void performLoginMenu(int choice,String cardId) throws ClassNotFoundException, SQLException, BookNotFound, CardNotFound, IssuedBookNotFound, BookTypeNotFound, ParseException, InvalidInputException;
}
