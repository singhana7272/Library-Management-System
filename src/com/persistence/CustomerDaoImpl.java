package com.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.bean.Customer;
import com.bean.CustomerCard;
import com.exception.CardNotFound;


@Repository
public class CustomerDaoImpl implements CustomerDao {

	private static final String url = "jdbc:mysql://localhost:3306/librarydb";
	private static final String user = "root";
	private static final String password = "admin";


	@Override
	public void CustomerAccountDetails(String cardId) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection(url, user, password);
		String query = "select c1.*,c2.card_id,c2.no_of_books_issued from customers c1 join customercard c2 on c1.customer_id=c2.customer_id where card_id=?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, cardId);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			String custId = resultSet.getString("customer_id");
			String customerName = resultSet.getString("customer_name");
			String collegeName = resultSet.getString("college_name");
			String address = resultSet.getString("address");
			String cId = resultSet.getString("card_id");
			int noOfBooksIssued = resultSet.getInt("no_of_books_issued");
			System.out.println("Your account details:");
			System.out.println("*****************************");
			System.out.println("Customer Id:" + custId);
			System.out.println("Customer Name:" + customerName);
			System.out.println("College Name:" + collegeName);
			System.out.println("Address:" + address);
			System.out.println("Card Id:" + cId);
			System.out.println("noOfBooksIssued:" + noOfBooksIssued);
		}
		connection.close();
	}
	
	@Override
	public CustomerCard searchCustomerCard(String cardId) throws ClassNotFoundException, SQLException, CardNotFound {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url,user,password);
		String query="select * from customercard where card_id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setString(1, cardId);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			String cId=resultSet.getString("card_id");
			String customerId=resultSet.getString("customer_id");
			int noOfBooksIssued=resultSet.getInt("no_of_books_issued");
			CustomerCard customerCard=new CustomerCard(cId, customerId, noOfBooksIssued);
			connection.close();
			return customerCard;
		}
		throw new CardNotFound("Card with "+ cardId + " is not available");
	}

	@Override
	public String addNewRecord(String customerName,String collegeName,String address)throws ClassNotFoundException, SQLException  {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url,user,password);
	    String lastCustomerId=getLastCustomerId();
	    String subStr=lastCustomerId.substring(1, lastCustomerId.length());
	    int str=Integer.parseInt(subStr);
	    str+=1;
	    String customerId=null;
	    if(str<=9) {
	    customerId="C0"+Integer.toString(str);
	    }
	    else {
	    	customerId="C"+Integer.toString(str);
	    }
		String query="insert into customers(customer_id,customer_name,college_name,address) values(?,?,?,?)";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setString(1, customerId);
		preparedStatement.setString(2, customerName);
		preparedStatement.setString(3, collegeName);
		preparedStatement.setString(4, address);
		int rows= preparedStatement.executeUpdate();
	    connection.close();
		return customerId;
	}

	@Override
	public String getLastCustomerId() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url,user,password);
		String query="select * from customers";
		Statement statement=connection.createStatement();
		ResultSet resultSet=statement.executeQuery(query);
		String customerId=null;
		while(resultSet.next()) {
			if(resultSet.isLast()) {
				customerId=resultSet.getString("customer_id");
			}
		}
		connection.close();
		return customerId;
	}


	@Override
	public String addNewCard(String customerId) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url,user,password);
		String cardId="C"+customerId;
		String query="insert into customercard(card_id,customer_id,no_of_books_issued) VALUES(?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, cardId);
		preparedStatement.setString(2, customerId);
		preparedStatement.setInt(3, 0);
		int rows=preparedStatement.executeUpdate();
		connection.close();
		return cardId;
	}

}
