package dataLayer;

import java.util.ArrayList;

import transferObject.BookTO;

public interface IBookDAL {

	ArrayList<BookTO> getBookByID(String ID);

	ArrayList<String> getPoemByBook(String bookTitle);

	ArrayList<BookTO> getAllBooks();

	void updateBook(BookTO books, String book);

	void deleteBook(String book);

	void addBooks(BookTO book);

}
