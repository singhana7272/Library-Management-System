package com.client;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.exception.CardNotFound;
import com.exception.InvalidInputException;
import com.presentation.LoginPresentation;

public class Main {
	public static void main(String[] args){
		
		ApplicationContext springContainer = new ClassPathXmlApplicationContext("login.xml");
		LoginPresentation loginPresentation = (LoginPresentation) springContainer.getBean("loginPresentationImpl");

		Scanner scanner = new Scanner(System.in);

		while (true) {
			loginPresentation.showMenu();
			System.out.println("Enter your choice");
			try {
				int choice = Integer.parseInt(scanner.nextLine());
				loginPresentation.performMenu(choice);
			} catch (NumberFormatException e) {
				System.out.println("Please input integer value: " + e.getMessage());
			}catch(InvalidInputException | CardNotFound e) {
				System.out.println(e.getMessage());
			}
		}

	}
}
