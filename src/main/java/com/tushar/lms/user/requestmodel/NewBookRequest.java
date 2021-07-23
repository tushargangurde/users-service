package com.tushar.lms.user.requestmodel;

import javax.validation.constraints.NotEmpty;

public class NewBookRequest {

	@NotEmpty(message = "book name should not be empty")
	private String bookName;
	@NotEmpty(message = "auhor required for book")
	private String author;

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
