package dataLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Main.Main;
import transferObject.BookTO;

public class BookDAL implements IBookDAL {

	Connection connection;

	private static final Logger logger = LogManager.getLogger(BookDAL.class.getName());

	public BookDAL() {
		try {
			connection = DatabaseConnection.getInstance().getConnection();
		} catch (Exception e) {
			logger.error("A database connection error occurred");
		}

	}

	@Override
	public void addBooks(BookTO book) {
		Statement statement;
		try {
			statement = connection.createStatement();
			logger.info("Adding a new book: " + book.getTitle() + " by " + book.getAuthor());
			String insertQuery = "INSERT INTO book (title, author, date) VALUES ('" + book.getTitle() + "','"
					+ book.getAuthor() + "','" + book.getDate() + "')";
			int rowsAffected = statement.executeUpdate(insertQuery);
			logger.info("Rows affected: " + rowsAffected);
		} catch (SQLException e) {
			logger.error("An SQL exception occurred in adding the book", e);
		}

	}

	@Override
	public void deleteBook(String book) {
		Statement statement;
		try {
			statement = connection.createStatement();
			logger.info("Deleting book with title: " + book);
			String deleteQuery = "DELETE FROM book WHERE title = '" + book + "'";
			int rowsAffected = statement.executeUpdate(deleteQuery);
			logger.info("Rows affected: " + rowsAffected);
		} catch (SQLException e) {
			logger.error("An SQL exception occurred in deleting the book", e);
		}

	}

	@Override
	public void updateBook(BookTO books, String book) {
		Statement statement;
		try {
			statement = connection.createStatement();
			logger.info("Updating book with title: " + book);
			String updateQuery = "UPDATE book " + "SET title = '" + books.getTitle() + "', " + "author = '"
					+ books.getAuthor() + "', " + "date = '" + books.getDate() + "' " + "WHERE title = '" + book + "'";
			int rowsAffected = statement.executeUpdate(updateQuery);
			logger.info("Rows affected: " + rowsAffected);
		} catch (SQLException e) {
			logger.error("An SQL exception occurred in updating the book", e);
		}

	}

	@Override
	public ArrayList<BookTO> getAllBooks() {
		ArrayList<BookTO> books = new ArrayList<>();
		String selectQuery = "SELECT * FROM book";
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(selectQuery);
			while (resultSet.next()) {
				BookTO book = new BookTO();
				book.setBookID(String.valueOf(resultSet.getInt("bookID")));
				book.setTitle(resultSet.getString("title"));
				book.setAuthor(resultSet.getString("author"));
				book.setDate(resultSet.getString("date"));
				books.add(book);
			}
		} catch (SQLException e) {
			logger.error("An SQL exception occurred in getting the book", e);
		}

		return books;
	}

	@Override
	public ArrayList<String> getPoemByBook(String bookTitle) {
		ArrayList<String> poems = new ArrayList<>();
		String selectQuery = "SELECT poem.Poem_Title " + "FROM poem " + "JOIN book ON poem.Book_ID = book.bookID "
				+ "WHERE book.title = '" + bookTitle + "'";

		Statement statement;
		try {
			statement = connection.createStatement();
			logger.info("Retrieving poems for book with title: " + bookTitle);
			ResultSet resultSet = statement.executeQuery(selectQuery);
			while (resultSet.next()) {
				String poemTitle = resultSet.getString("Poem_Title");
				poems.add(poemTitle);
			}
			logger.info("Number of poems retrieved: " + poems.size());
		} catch (SQLException e) {
			logger.error("An SQL exception occurred in adding the poem", e);
		}

		return poems;
	}

	@Override
	public ArrayList<BookTO> getBookByID(String ID) {
		ArrayList<BookTO> books = new ArrayList<>();
		String selectQuery = "SELECT * FROM book WHERE BookID = " + ID;

		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(selectQuery)) {

			while (resultSet.next()) {
				BookTO book = new BookTO(); // Create a new BookTO object for each iteration
				book.setBookID(String.valueOf(resultSet.getInt("bookID")));
				book.setAuthor(resultSet.getString("author"));
				book.setTitle(resultSet.getString("title"));
				book.setDate(resultSet.getString("date"));
				books.add(book);
			}
			logger.info("Number of books retrieved: " + books.size());
		} catch (SQLException e) {
			logger.error("An SQL exception occurred in fetching the book", e);
		}

		return books;
	}

}
