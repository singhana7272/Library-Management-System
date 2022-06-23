package com.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Customer;
import com.bean.CustomerCard;
import com.exception.CardNotFound;
import com.persistence.CustomerDao;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public void CustomerAccountDetails(String cardId) throws ClassNotFoundException, SQLException {
		customerDao.CustomerAccountDetails(cardId);
	}


	@Override
	public CustomerCard searchCustomerCard(String cardId) throws ClassNotFoundException, SQLException, CardNotFound {
		return customerDao.searchCustomerCard(cardId);
	}

	@Override
	public String addNewCustomer(String customerName,String collegeName,String address) throws ClassNotFoundException, SQLException {
		return customerDao.addNewRecord(customerName, collegeName, address);
	}

	@Override
	public String generateNewCard(String customerId) throws ClassNotFoundException, SQLException {
		return customerDao.addNewCard(customerId);
	}

	

}
