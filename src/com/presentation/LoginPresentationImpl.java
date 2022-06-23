package com.presentation;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bean.Customer;
import com.bean.CustomerCard;
import com.exception.BookNotFound;
import com.exception.BookTypeNotFound;
import com.exception.CardNotFound;
import com.exception.InvalidInputException;
import com.exception.IssuedBookNotFound;
import com.service.CustomerService;

@Component
public class LoginPresentationImpl implements LoginPresentation {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerPresentation customerPresentation;

	@Override
	public void showMenu() {
		System.out.println("1.Create new account:");
		System.out.println("2.Login into your existing account:");
		System.out.println("3.Exit.");

	}

	@Override
	public void performMenu(int choice) throws CardNotFound, InvalidInputException {
		Scanner scanner = new Scanner(System.in);
		try {
			switch (choice) {

			case 1:
				System.out.println("Enter Customer Name:");
				String customerName = scanner.nextLine();
				System.out.println("Enter College Name:");
				String collegeName = scanner.nextLine();
				System.out.println("Enter Address");
				String address = scanner.nextLine();
				String customerId = customerService.addNewCustomer(customerName, collegeName, address);
				String cardId = customerService.generateNewCard(customerId);
				if (!cardId.equals(null))
					System.out.println("Customer Registered Successfully, CardId: " + cardId);
				else
					System.out.println("Customer Registration Failed");
				break;

			case 2:
				System.out.println("Enter your cardId");
				cardId = scanner.nextLine();
				CustomerCard card=customerService.searchCustomerCard(cardId);
				if(!card.equals(null)) {	
				while (true) {
					customerPresentation.showLoginMenu();
					System.out.println("Enter your choice");
					try {
					int customerChoice = Integer.parseInt(scanner.nextLine());
					if(customerChoice==6)
						return;
					customerPresentation.performLoginMenu(customerChoice, cardId);
					}
					catch(BookNotFound | CardNotFound | BookTypeNotFound | IssuedBookNotFound | ParseException | SQLException | ClassNotFoundException | InvalidInputException | NumberFormatException e ) {
						System.out.println(e.getMessage());
					}
				}
				}
				else {
					throw new CardNotFound("Card with cardId:"+cardId +" not available");
				}
				
				
			case 3:
				System.out.println("Thank you for using our application.Kindly visit us again");
				System.exit(0);
				break;
				

			default:
				throw new InvalidInputException("Warning! Please enter choice between 1-3");

			}
		} catch (SQLException | ClassNotFoundException | InputMismatchException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
