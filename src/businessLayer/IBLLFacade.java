package businessLayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import transferObject.BookTO;
import transferObject.MisraTO;
import transferObject.PoemTO;
import transferObject.RootTO;
import transferObject.VerseTO;

public interface IBLLFacade {

	public void addBook(BookTO book) throws SQLException;

	public void deleteBook(String book) throws SQLException;

	public void updateBook(BookTO book, String title) throws SQLException;

	public ArrayList<BookTO> getBooks() throws SQLException;

	public DefaultTableModel parseCSV_(String filePath);

	public void submitPoemDataToDatabase_(int index) throws SQLException;

	public void setTableModel_(DefaultTableModel tableModel);

	public void savePoem_(PoemTO poem, VerseTO verse, MisraTO misra, String bookID);

	public void saveWordsToDatabase_tokenize_(DefaultTableModel tableModel) throws SQLException;

	public void splitTextFields_tokenize_(DefaultTableModel tableModel, String text1, String text2);

	public void generateroot() throws SQLException;

	public void updatePoem_(PoemTO poems, String string) throws SQLException;

	public void deletePoem(String ID) throws SQLException;

	public void insertPoem(PoemTO poem, String bookID) throws SQLException;

	public ArrayList<PoemTO> getPoems(int bookID) throws SQLException;

	public ArrayList<MisraTO> getMisras(MisraTO id) throws SQLException;

	public void addMisra_(MisraTO misra) throws SQLException;

	public void deleteMisra(String ID) throws SQLException;

	public void updateMisra_(MisraTO misra, String id);

	public ArrayList<BookTO> getBookByID(String ID) throws SQLException;

	public List<String> getVersesForRoot(String rootName);

	public List<RootTO> getAllRootsWithVersesCount();

	public void verifyRoots(String selectedRoot);

	public String getVerificationStatus(String selectedRoot);

	public void saveRoot(String rootName, String id);

	public List<String> getStemmedRootsForVerse(String id);

	public void submitToken(MisraTO obj);

	public String getPoemIdFromVerse(String verse);

	public void removeRoot(String name);

}
