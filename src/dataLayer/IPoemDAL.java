package dataLayer;

import java.sql.SQLException;
import java.util.ArrayList;

import transferObject.MisraTO;
import transferObject.PoemTO;
import transferObject.VerseTO;

public interface IPoemDAL {

	String getPoemIdFromVerse(String verse);

	void updateMisra(MisraTO misra, String ID);

	void deleteMisra(String ID);

	void addMisra(MisraTO misra);

	ArrayList<MisraTO> getAllMisra(MisraTO id) throws SQLException;

	void updatePoem(PoemTO poems, String ID) throws SQLException;

	void deletePoem(String poemID) throws SQLException;

	ArrayList<PoemTO> getAllPoems(int bookID) throws SQLException;

	void insertVerse(VerseTO verse, MisraTO misra);

	void insertPoem(PoemTO poem, String bookID);

}
