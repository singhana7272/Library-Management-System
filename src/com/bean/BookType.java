package com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookType {
	private String bookTypeId;
	private String bookType;
	private int finePerDay;
}
