package com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Book {
	private String bookId;
	private String bookTypeId;
	private String bookName;
	private String authorName;
	private int noOfCopiesAvailable;
}
