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

public interface IDALFacade extends IRootDAL,IpoemFileDAL,IPoemDAL,IBookDAL {

	public void removeRoot(String name);

	public void addBooks(BookTO book);

	public void deleteBook(String title);

	public void updateBook(BookTO book, String title);

	public ArrayList<BookTO> getAllBooks();

	public void submitToDatabase(DefaultTableModel tableModel, int index);
	
	public ArrayList<String> tokenSubmitToData(int tokenID, String tokenName, String Pos);

	public void insertPoem(PoemTO poem, String bookID);

	public void insertVerse(VerseTO verse, MisraTO misra);

	void generateRoot() throws SQLException;

	public void updatePoem(PoemTO poems, String iD) throws SQLException;

	public ArrayList<PoemTO> getAllPoems(int bookID) throws SQLException;

	public void deletePoem(String book) throws SQLException;

	public ArrayList<MisraTO> getAllMisra(MisraTO id) throws SQLException;

	void addMisra(MisraTO poem);

	void deleteMisra(String ID);

	void updateMisra(MisraTO misra, String id);

	public ArrayList<BookTO> getBookByID(String ID);

	public List<String> getVersesForRoot(String rootName);

	public List<RootTO> getAllRootsWithVersesCount();


	public void verifyRoots(String selectedRoot);


	public String getVerificationStatus(String selectedRoot);


	public void saveRoot(String rootName, String id);


	public List<String> getStemmedRootsForVerse(String id);


	public String getPoemIdFromVerse(String verse);

}
