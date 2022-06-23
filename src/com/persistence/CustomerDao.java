package com.persistence;

import java.sql.SQLException;

import com.bean.Customer;
import com.bean.CustomerCard;
import com.exception.CardNotFound;

public interface CustomerDao {
	void CustomerAccountDetails(String cardId) throws ClassNotFoundException, SQLException;
    CustomerCard searchCustomerCard(String cardId) throws ClassNotFoundException, SQLException, CardNotFound;
	String addNewRecord(String customerName,String collegeName,String address) throws ClassNotFoundException, SQLException;
	String getLastCustomerId() throws SQLException, ClassNotFoundException;
	String addNewCard(String customerId) throws SQLException, ClassNotFoundException;
}
