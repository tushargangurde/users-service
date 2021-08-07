package com.tushar.lms.user.responsemodel;

public class GetBookResponse {

	private String bookId;
	private String bookName;
	private String author;
	private boolean available;

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

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "GetBookResponse [bookId=" + bookId + ", bookName=" + bookName + ", author=" + author + ", available="
				+ available + "]";
	}

}
