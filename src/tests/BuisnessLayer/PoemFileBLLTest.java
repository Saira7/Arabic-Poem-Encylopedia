package BuisnessLayer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import businessLayer.PoemFileBLL;
import dataLayer.DALStubFacade;
import dataLayer.IDALFacade;

class PoemFileBLLTest {

	private IDALFacade poemFileDALStub;
	private PoemFileBLL poemFileBll;
	private DefaultTableModel tableModel;

	PoemFileBLLTest() throws SQLException {
		poemFileDALStub = new DALStubFacade();
		poemFileBll = new PoemFileBLL(poemFileDALStub);
		tableModel = new DefaultTableModel(new String[] { "Verse", "Title", "Misra #1", "Misra #2" }, 0);
	}
	
    
	@Test
	@DisplayName("Tokenize the verse")
	void testSplitTextFields_tokenize() {

		String actualTokeize1 = "Hello there ladies and gentlemen";
		String actualTokenize2 = "My name is scooby";

		String[] expectedTokenize1 = { "Hello", "there", "ladies", "and", "gentlemen" };
		String[] expectedTokenize2 = { "My", "name", "is", "scooby" };

		List<String[]> output = poemFileBll.splitTextFields_tokenize(tableModel, actualTokeize1, actualTokenize2);

		assertArrayEquals(expectedTokenize1, output.get(0));
		assertArrayEquals(expectedTokenize2, output.get(1));
	}
}
