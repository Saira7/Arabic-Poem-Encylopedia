package BuisnessLayer;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import businessLayer.BookBLL;
import dataLayer.DALStubFacade;
import dataLayer.IDALFacade;
import transferObject.BookTO;

class BookBLLTest {
	IDALFacade bookDAL;
	BookBLL bookBll;
	public BookBLLTest(){
		 bookDAL = new DALStubFacade();
		bookBll=new BookBLL(bookDAL);
	}
	@Test
	@DisplayName("Addition of book sucessful")
	void testAddBook() throws SQLException {
	    BookTO mockBook = new BookTO();
        mockBook.setTitle("Mock Title");
        mockBook.setAuthor("Mock Author");
        mockBook.setDate("Mock Date");

        // Call the method to be tested
        bookBll.addBook(mockBook);

        // Verify that the book was added
        ArrayList<BookTO> allBooks = bookBll.getBooks();
        assertEquals(2, allBooks.size());
        assertEquals("Mock Title", allBooks.get(1).getTitle());
	}

	@Test
	@DisplayName("Deletion Sucessful")
	void testDeleteBook() throws SQLException {
        bookBll.deleteBook("Mock Title");
        ArrayList<BookTO> allBooksAfterDelete = bookBll.getBooks();
        assertEquals(1, allBooksAfterDelete.size());
	}

	@Test
	@DisplayName("Updation sucessful")
	void testUpdateBook() throws SQLException {
		
		// Create the updated book
        BookTO updatedBook = new BookTO("ID","New Title", "Author", "2023-01-01");

        // Call the updateBook method
        bookBll.updateBook(updatedBook, "1");

        // Get the updated book from the collection
        ArrayList<BookTO> resultBook = bookBll.getBookByID("ID");

        // Verify that the updated book is present
        assertNotNull(resultBook);

       
	}

	@Test
	@DisplayName("Getting All Books")
	void testGetBooks() throws SQLException {
		  ArrayList<BookTO> Books = bookBll.getBooks();

	        // Verify that the result is not null
	        assertNotNull(Books);
	        // Verify that the result is an empty list
	        assertFalse(Books.isEmpty());
	}

	@Test
	@DisplayName("Getting Poems from book")
	void testGetlistofpoems() throws SQLException {
		ArrayList<String>Poems=bookBll.getlistofpoems("Book1");
		  // Verify that the result is not null
        assertNotNull(Poems);
        // Verify that the result is an empty list
        assertFalse(Poems.isEmpty());
        assertTrue(Poems.contains("Alumnu"));
        assertTrue(Poems.contains("Frito"));
	}

}
