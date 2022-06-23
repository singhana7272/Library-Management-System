package com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Customer {
	private String customerId;
	private String customerName;
	private String collegeName;
	private String address;
}
