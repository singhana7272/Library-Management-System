package com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class IssuedBook {
	private String cardId;
	private String bookId;
	private String issueDate;
	private String expectedReturnDate;
}
