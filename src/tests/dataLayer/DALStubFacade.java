package dataLayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import transferObject.BookTO;
import transferObject.MisraTO;
import transferObject.PoemTO;
import transferObject.RootTO;
import transferObject.VerseTO;

public class DALStubFacade implements IDALFacade {

	private IPoemDAL poemDAL;
	private IRootDAL rootDAL;
	private IBookDAL bookDAL;
	private IpoemFileDAL poemFileDAL;

	public DALStubFacade() {
		rootDAL = new RootDALStub();
		bookDAL = new BookDALStub();
		poemFileDAL = new PoemFileDALStub();
		poemDAL = new PoemDALStub();
	}
	@Override
	public void insertVerse(VerseTO verse, MisraTO misra) {
		poemDAL.insertVerse(verse, misra);

	}

	@Override
	public void submitToDatabase(DefaultTableModel tableModel, int id) {
		poemFileDAL.submitToDatabase(tableModel, id);
	}

	@Override
	public ArrayList<String> tokenSubmitToData(int tokenID, String tokenName, String Pos) {
		return poemFileDAL.tokenSubmitToData(tokenID, tokenName, Pos);
	}

	@Override
	public void removeRoot(String name) {
		rootDAL.removeRoot(name);
	}

	@Override
	public void addBooks(BookTO book) {
		bookDAL.addBooks(book);
	}

	@Override
	public void deleteBook(String title) {
		bookDAL.deleteBook(title);
	}

	@Override
	public void updateBook(BookTO book, String title) {
		bookDAL.updateBook(book, title);
	}

	@Override
	public ArrayList<BookTO> getAllBooks() {
		return bookDAL.getAllBooks();
	}

	@Override
	public void generateRoot() throws SQLException {
		rootDAL.generateRoot();

	}

	@Override
	public void updatePoem(PoemTO poems, String iD) {
		try {
			poemDAL.updatePoem(poems, iD);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}

	@Override
	public ArrayList<PoemTO> getAllPoems(int bookID) {
		try {
			return poemDAL.getAllPoems(bookID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deletePoem(String book) {
		try {
			poemDAL.deletePoem(book);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void insertPoem(PoemTO poem, String bookID) {
		poemDAL.insertPoem(poem, bookID);
	}

	@Override
	public ArrayList<MisraTO> getAllMisra(MisraTO id) {
		try {
			return poemDAL.getAllMisra(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addMisra(MisraTO poem) {
		poemDAL.addMisra(poem);
	}

	@Override
	public void deleteMisra(String ID) {
		poemDAL.deleteMisra(ID);

	}

	@Override
	public void updateMisra(MisraTO misra, String id) {
		poemDAL.updateMisra(misra, id);
	}

	@Override
	public ArrayList<BookTO> getBookByID(String ID) {
		return bookDAL.getBookByID(ID);
	}

	@Override
	public List<String> getVersesForRoot(String rootName) {
		return rootDAL.getVersesForRoot(rootName);
	}

	@Override
	public List<RootTO> getAllRootsWithVersesCount() {
		return rootDAL.getAllRootsWithVersesCount();
	}

	@Override
	public void verifyRoots(String selectedRoot) {
		rootDAL.verifyRoots(selectedRoot);

	}

	@Override
	public String getVerificationStatus(String selectedRoot) {
		return rootDAL.getVerificationStatus(selectedRoot);
	}

	@Override
	public void saveRoot(String rootName, String id) {
		rootDAL.saveRoot(rootName, id);

	}

	@Override
	public List<String> getStemmedRootsForVerse(String id) {

		return rootDAL.getStemmedRootsForVerse(id);
	}

	@Override
	public String getPoemIdFromVerse(String verse) {

		return poemDAL.getPoemIdFromVerse(verse);
	}

	@Override
	public ArrayList<String> getPoemByBook(String bookTitle) {
		
		return bookDAL.getPoemByBook(bookTitle);
	}

}
