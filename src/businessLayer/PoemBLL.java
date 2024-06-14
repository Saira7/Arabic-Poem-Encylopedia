package businessLayer;

import transferObject.MisraTO;
import transferObject.VerseTO;
import transferObject.PoemTO;

import dataLayer.DALFacadeClass;
import dataLayer.IDALFacade;

import java.sql.SQLException;
import java.util.ArrayList;

public class PoemBLL {
	private IDALFacade poemFacadeDAL;

	public PoemBLL() throws SQLException {
		poemFacadeDAL = new DALFacadeClass();
	}

	public PoemBLL(IDALFacade poemFacadeDAL) {
		this.poemFacadeDAL = poemFacadeDAL;
	}

	public void savePoem(PoemTO poem, VerseTO verse, MisraTO misra, String bookID) {
		if (verse.getNoOfVerses() == 1) {
			poemFacadeDAL.insertPoem(poem, bookID);
		}
		poemFacadeDAL.insertVerse(verse, misra);
	}

	// Iteration 2 ___________________________________________________________

	public void updatePoem_(PoemTO poems, String ID) throws SQLException {
		poemFacadeDAL.updatePoem(poems, ID);
	}

	public ArrayList<PoemTO> getPoems(int bookID) throws SQLException {
		return poemFacadeDAL.getAllPoems(bookID);

	}

	public void deletePoem(String book) throws SQLException {
		poemFacadeDAL.deletePoem(book);
	}

	public void insertPoem(PoemTO poem, String bookID) throws SQLException {
		poemFacadeDAL.insertPoem(poem, bookID);
	}

	public ArrayList<MisraTO> getAllMisras(MisraTO id) throws SQLException {
		return poemFacadeDAL.getAllMisra(id);
	}

	public void addMisra_(MisraTO poem) throws SQLException {
		poemFacadeDAL.addMisra(poem);
	}

	public void deleteMisra(String ID) throws SQLException {
		poemFacadeDAL.deleteMisra(ID);

	}

	public void updateMisra(MisraTO misra, String id) {
		poemFacadeDAL.updateMisra(misra, id);
	}

	public String getPoemIdFromVerse(String verse) {

		return poemFacadeDAL.getPoemIdFromVerse(verse);
	}

}
