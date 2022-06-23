package com.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.BookType;
import com.bean.IssuedBook;
import com.persistence.IssueBookDao;

@Service
public class ReturnBookServiceImpl implements ReturnBookService{
	@Autowired
	private IssueBookDao issueBookDao;

	@Override
	public boolean returnBook(String cardId, String bookId) throws ClassNotFoundException, SQLException {
		return issueBookDao.returnBook(cardId, bookId);
	}

	@Override
	public void calculateFine(BookType bookType,IssuedBook issuedBook) throws ParseException {
		Date currentDate=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date issuedDate=sdf.parse(issuedBook.getIssueDate());
		long difference_In_Time=  currentDate.getTime() - issuedDate.getTime();
		long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
		int diff=(int)difference_In_Days;
		if(difference_In_Days>7) {
			System.out.println("Fine Charged:"+ bookType.getFinePerDay()*diff);
		}
		else {
			System.out.println("No charge for you!");
		}
	}
}
