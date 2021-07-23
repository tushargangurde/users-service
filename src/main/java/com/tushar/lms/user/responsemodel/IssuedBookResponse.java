package com.tushar.lms.user.responsemodel;

public class IssuedBookResponse {

	private String bookId;
	private String bookName;
	private String author;

	public IssuedBookResponse(String bookId, String bookName, String author) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.author = author;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

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
