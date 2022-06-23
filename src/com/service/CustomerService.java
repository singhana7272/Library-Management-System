package com.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.bean.Customer;
import com.bean.CustomerCard;
import com.exception.CardNotFound;

public interface CustomerService {
	String addNewCustomer(String customerName,String collegeName,String address) throws ClassNotFoundException, SQLException;
	String generateNewCard(String customerId) throws ClassNotFoundException, SQLException;
	void CustomerAccountDetails(String cardId) throws ClassNotFoundException, SQLException;
    CustomerCard searchCustomerCard(String cardId) throws ClassNotFoundException, SQLException, CardNotFound;
    
}
