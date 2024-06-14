package businessLayer;

import java.sql.SQLException;
import java.util.ArrayList;

import dataLayer.DALFacadeClass;
import dataLayer.IDALFacade;
import transferObject.BookTO;


public class BookBLL {
	public IDALFacade dal;

	public BookBLL() throws SQLException
	{

		this.dal = new DALFacadeClass();
	}

	public BookBLL(IDALFacade dal) {
		this.dal = dal;
	}

	public void addBook(BookTO book) throws SQLException {
		dal.addBooks(book);
	}

	public void deleteBook(String book) throws SQLException {
		dal.deleteBook(book);
	}

	public void updateBook(BookTO books, String book) throws SQLException {
		dal.updateBook(books, book);
	}

	public ArrayList<BookTO> getBooks() throws SQLException {
		return dal.getAllBooks();

	}

	public ArrayList<String> getlistofpoems(String title) throws SQLException {
		return dal.getPoemByBook(title);
	}

	public ArrayList<BookTO> getBookByID(String string) {
		return dal.getBookByID(string);
	}

}
