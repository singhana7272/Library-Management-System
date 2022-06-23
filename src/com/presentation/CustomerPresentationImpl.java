package com.presentation;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bean.Book;
import com.bean.BookType;
import com.bean.CustomerCard;
import com.bean.IssuedBook;
import com.exception.BookNotFound;
import com.exception.BookTypeNotFound;
import com.exception.CardNotFound;
import com.exception.InvalidInputException;
import com.exception.IssuedBookNotFound;
import com.service.BookService;
import com.service.CustomerService;
import com.service.IssueBookService;
import com.service.ReturnBookService;
import com.service.ReturnBookServiceImpl;

@Component
public class CustomerPresentationImpl implements CustomerPresentation {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private BookService bookService;
	@Autowired
	private IssueBookService issueBookService;
	@Autowired
	private ReturnBookService returnBookService;

	@Override
	public void showLoginMenu() {
		System.out.println("1.List of books available");
		System.out.println("2.Check Your Account Details");
		System.out.println("3.List Of Books Issued");
		System.out.println("4.Issue a Book");
		System.out.println("5.Return a Book");
		System.out.println("6.Logout");
	}

	
	@Override
	public void performLoginMenu(int choice, String cardId) throws ClassNotFoundException, SQLException, BookNotFound, CardNotFound, IssuedBookNotFound, BookTypeNotFound, ParseException, InvalidInputException {
		Scanner scanner = new Scanner(System.in);
			switch (choice) {
			
			case 1:
				ArrayList<Book> books = null;
				books = bookService.listOfBookAvailable();
				System.out.println("List of Books Available");
				System.out.println("*************************");
				for (Book book : books) {
					System.out.println(book);
				}
				break;
				
			case 2:
				customerService.CustomerAccountDetails(cardId);
				break;
				
			case 3:
				System.out.println("List of Books Issued By Customer with CardId:"+ cardId);
				issueBookService.listOfBooksIssued(cardId);
				break;
				
			case 4:
				books = bookService.listOfBookAvailable();
				System.out.println("List of Books Available");
				System.out.println("*************************");
				for (Book book : books) {
					System.out.println(book);
				}
				System.out.println("Enter book id you want to issue");
				String bookId=scanner.nextLine();
				System.out.println("Enter issue-date in YYYY-MM-DD format.");
				String issueDate=scanner.nextLine();
				boolean status=issueBookService.issueBook(cardId, bookId, issueDate);
				if(status) {
					Book book=bookService.searchBookById(bookId);
					if(book.getNoOfCopiesAvailable()>0){
					boolean status1=issueBookService.updateNoOfCopies(bookId, book.getNoOfCopiesAvailable()-1);
					CustomerCard customerCard=customerService.searchCustomerCard(cardId);
					boolean status2=issueBookService.updateNoOfBooksIssued(cardId, customerCard.getNoOfBooksIssued()+1);
					if(status1 && status2) {
						System.out.println("Book Issued successfully");
					}
					}else {
						System.out.println("Book is not available");
					}
				}
			break;
			
			case 5:
				issueBookService.listOfBooksIssued(cardId);
				System.out.println("Enter the book Id which you want to return");
				bookId=scanner.nextLine();
				Book book=bookService.searchBookById(bookId);
				BookType bookType = bookService.searchBookTypeById(book.getBookTypeId());
				IssuedBook issuedBook=issueBookService.searchIssuedBookById(bookId);
				returnBookService.calculateFine(bookType,issuedBook);
				status = returnBookService.returnBook(cardId, bookId);
				if(status) {
					if(book.getNoOfCopiesAvailable()>0){	
					boolean status1=issueBookService.updateNoOfCopies(bookId, book.getNoOfCopiesAvailable()+1);
					CustomerCard customerCard=customerService.searchCustomerCard(cardId);
					boolean status2=issueBookService.updateNoOfBooksIssued(cardId, customerCard.getNoOfBooksIssued()-1);
					if(status1 && status2) {
						System.out.println("Book Returned successfully");
					}
					}
				}
				break;
			case 6:
				break;
				
			default :
				throw new InvalidInputException("Warning! Please enter choice between 1-6");
			}
		
	}

}
