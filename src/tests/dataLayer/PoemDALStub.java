package dataLayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import transferObject.MisraTO;
import transferObject.PoemTO;
import transferObject.VerseTO;

public class PoemDALStub implements IPoemDAL {

	private PoemTO poemTo;
	private PoemTO poem1;
	private PoemTO poem2;
	ArrayList<MisraTO> misras = new ArrayList<>();
	List<PoemTO> poems = new ArrayList<>();

	String bookID_;

	PoemDALStub() {
		poemTo = new PoemTO();

		poemTo.setPoemId(123);
		poemTo.setPoemTitle("SampleVerse1");

		MisraTO misraTo = new MisraTO();

		misraTo.setMisraId(1);
		misraTo.setMisra1("SampleMisra1");
		misraTo.setMisra2("SampleMisra2");

		misras.add(misraTo);

		// Simulate data as it might be returned from a database query
		poem1 = new PoemTO();
		poem1.setPoemId(1);
		poem1.setPoemTitle("Poem 1");

		poem2 = new PoemTO();
		poem2.setPoemId(2);
		poem2.setPoemTitle("Poem 2");

		poems.add(poem1);
		poems.add(poem2);

	}

	@Override
	public String getPoemIdFromVerse(String verse) {
		if (verse.equals(poemTo.getPoemTitle())) {
			return String.valueOf(poemTo.getPoemId());
		} else {
			return null;
		}
	}

	@Override
	public ArrayList<MisraTO> getAllMisra(MisraTO id) throws SQLException {

		return misras;
	}

	@Override
	public ArrayList<PoemTO> getAllPoems(int bookID) throws SQLException {

		return (ArrayList<PoemTO>) poems;

	}

	@Override
	public void updateMisra(MisraTO misra, String ID) {
		

	}

	@Override
	public void deleteMisra(String ID) {
	
	}

	@Override
	public void addMisra(MisraTO misra) {
		

	}

	@Override
	public void updatePoem(PoemTO poems, String ID) throws SQLException {
	

	}

	@Override
	public void deletePoem(String poemID) throws SQLException {
	

	}

	@Override
	public void insertVerse(VerseTO verse, MisraTO misra) {
	
	}

	@Override
	public void insertPoem(PoemTO poem, String bookID) {
		
	}

}
