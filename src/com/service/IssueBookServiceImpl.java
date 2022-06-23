package com.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Book;
import com.bean.CustomerCard;
import com.bean.IssuedBook;
import com.exception.IssuedBookNotFound;
import com.persistence.IssueBookDao;

@Service
public class IssueBookServiceImpl implements IssueBookService {
	
	@Autowired
	private IssueBookDao issueBookDao;

	@Override
	public void listOfBooksIssued(String cardId) throws ClassNotFoundException, SQLException {
		issueBookDao.listOfBooksIssued(cardId);
		return;
	}

	@Override
	public boolean issueBook(String cardId,String bookId,String issueDate) throws ClassNotFoundException, SQLException, ParseException {
		return issueBookDao.issueBook(cardId, bookId, issueDate);
	}

	@Override
	public boolean updateNoOfCopies(String bookId, int noOfCopies) throws ClassNotFoundException, SQLException {
		return issueBookDao.updateNoOfCopies(bookId, noOfCopies);
	}

	@Override
	public boolean updateNoOfBooksIssued(String cardId,int noOfBooksIssued) throws ClassNotFoundException, SQLException {
		return issueBookDao.updateNoOfBooksIssued(cardId, noOfBooksIssued);
	}

	@Override
	public IssuedBook searchIssuedBookById(String bookId) throws ClassNotFoundException, SQLException, IssuedBookNotFound {
		return issueBookDao.searchIssuedBookById(bookId);
	}

}
