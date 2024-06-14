package dataLayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import transferObject.BookTO;
import transferObject.PoemTO;

public class BookDALStub implements IBookDAL {

	private List<BookTO> books = new ArrayList();
	private ArrayList<String> poems = new ArrayList();

	public BookDALStub() {
		books.add(new BookTO("1", "Book1", "Author", "2-2-21"));
		poems.add("Alumnu");
		poems.add("Frito");
		poems.add("gogo");
	}

	@Override
	public void addBooks(BookTO book) {
		books.add(book);
	}

	@Override
	public void deleteBook(String book) {

		// Iterate through the books to find the book with the specified title
		Iterator<BookTO> iterator = books.iterator();
		while (iterator.hasNext()) {
			BookTO existingBook = iterator.next();
			if (existingBook.getTitle().equals(book)) {
				// Remove the book with the specified title
				iterator.remove();
				break;
			}
		}

	}

	@Override
	public void updateBook(BookTO book, String oldTitle) {
		boolean bookExists = false;

		// Check if the book with the old title exists
		for (BookTO existingBook : books) {
			if (existingBook.getTitle().equals(oldTitle)) {
				bookExists = true;
				break;
			}
		}

		// If the book with the old title exists
		if (bookExists) {
			// Check if the new title is not null or empty
			if (book.getTitle() != null && !book.getTitle().isEmpty()) {
				// Delete the book with the old title
				deleteBook(oldTitle);

				// Add the updated book with the new title
				addBooks(book);
			}
		}
	}

	@Override
	public ArrayList<BookTO> getAllBooks() {
		return (ArrayList<BookTO>) (books);
	}

	@Override
	public ArrayList<String> getPoemByBook(String bookTitle) {
		for (BookTO existingBook : books) {
			if (bookTitle.equals(existingBook.getTitle()))
				return poems;
		}
		return null;
	}

	@Override
	public ArrayList<BookTO> getBookByID(String ID) {

		return (ArrayList<BookTO>) books;
	}
}
