package com.presentation;

import java.sql.SQLException;

import com.exception.CardNotFound;
import com.exception.InvalidInputException;

public interface LoginPresentation {
	void showMenu();
	void performMenu(int choice) throws InvalidInputException, CardNotFound;
}
