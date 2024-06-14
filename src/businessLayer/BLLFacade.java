package businessLayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import dataLayer.BookDAL;
import transferObject.BookTO;
import transferObject.MisraTO;
import transferObject.PoemTO;
import transferObject.RootTO;
import transferObject.VerseTO;

public class BLLFacade implements IBLLFacade {

	private RootBLL rootbl;
	private PoemFileBLL poemFileBLL;
	private PoemBLL poemBLL;
	private BookDAL bookBLL;

	public BLLFacade() throws SQLException {
		this.poemFileBLL = new PoemFileBLL();
		this.bookBLL = new BookDAL();
		this.rootbl = new RootBLL();
		poemBLL = new PoemBLL();
	}

	@Override
	public void addBook(BookTO book) throws SQLException {
		bookBLL.addBooks(book);
	}

	@Override
	public void deleteBook(String book) throws SQLException {
		bookBLL.deleteBook(book);
	}

	@Override
	public void updateBook(BookTO book, String title) throws SQLException {
		bookBLL.updateBook(book, title);
	}

	@Override
	public ArrayList<BookTO> getBooks() throws SQLException {
		return bookBLL.getAllBooks();
	}

	@Override
	public void savePoem_(PoemTO poem, VerseTO verse, MisraTO misra, String bookID) {
		poemBLL.savePoem(poem, verse, misra, bookID);

	}

	@Override
	public DefaultTableModel parseCSV_(String filePath) {
		return poemFileBLL.parseCSV(filePath);
	}

	@Override
	public void submitPoemDataToDatabase_(int index) throws SQLException {
		poemFileBLL.submitPoemDataToDatabase(index);

	}

	@Override
	public void setTableModel_(DefaultTableModel tableModel) {
		poemFileBLL.setTableModel(tableModel);

	}

	@Override
	public void saveWordsToDatabase_tokenize_(DefaultTableModel tableModel) throws SQLException {
		poemFileBLL.saveWordsToDatabase_tokenize(tableModel);
	}

	@Override
	public void splitTextFields_tokenize_(DefaultTableModel tableModel, String text1, String text2) {
		poemFileBLL.splitTextFields_tokenize(tableModel, text1, text2);
	}

	@Override
	public void removeRoot(String name) {

		rootbl.removeRoot(name);
	}

	@Override
	public void generateroot() throws SQLException {
		rootbl.generateroot();

	}

	@Override
	public void updatePoem_(PoemTO poems, String string) throws SQLException {
		poemBLL.updatePoem_(poems, string);

	}

	@Override
	public void deletePoem(String string) throws SQLException {
		poemBLL.deletePoem(string);
	}

	@Override
	public void insertPoem(PoemTO poem, String bookID) throws SQLException {
		poemBLL.insertPoem(poem, bookID);
	}

	@Override
	public ArrayList<PoemTO> getPoems(int bookID) throws SQLException {
		return poemBLL.getPoems(bookID);
	}

	public ArrayList<MisraTO> getMisras(MisraTO id) throws SQLException {
		return poemBLL.getAllMisras(id);

	}

	@Override
	public void addMisra_(MisraTO misra) throws SQLException {
		poemBLL.addMisra_(misra);

	}

	@Override
	public void deleteMisra(String ID) throws SQLException {
		poemBLL.deleteMisra(ID);
	}

	@Override
	public void updateMisra_(MisraTO misra, String id) {
		poemBLL.updateMisra(misra, id);
	}

	@Override
	public ArrayList<BookTO> getBookByID(String ID) throws SQLException {
		return bookBLL.getBookByID(ID);

	}

	@Override
	public List<String> getVersesForRoot(String rootName) {
		// Implement the logic to retrieve verses associated with the given root
		return rootbl.getVersesForRoot(rootName);
	}

	@Override
	public List<RootTO> getAllRootsWithVersesCount() {
		return rootbl.getAllRootsWithVersesCount();
	}

	@Override
	public void verifyRoots(String selectedRoot) {

		rootbl.verifyRoots(selectedRoot);
	}

	@Override
	public String getVerificationStatus(String selectedRoot) {
		return rootbl.getVerificationStatus(selectedRoot);
	}

	@Override
	public void saveRoot(String rootName, String id) {
		rootbl.saveRoot(rootName, id);
	}

	@Override
	public List<String> getStemmedRootsForVerse(String id) {

		return rootbl.getStemmedRootsForVerse(id);
	}

	@Override
	public void submitToken(MisraTO obj) {
		poemFileBLL.submitToken(obj);

	}

	@Override
	public String getPoemIdFromVerse(String verse) {
		return poemBLL.getPoemIdFromVerse(verse);
	}

}
