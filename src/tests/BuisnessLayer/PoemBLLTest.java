package BuisnessLayer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import businessLayer.PoemBLL;
import dataLayer.DALStubFacade;
import dataLayer.IDALFacade;
import transferObject.MisraTO;
import transferObject.PoemTO;
import transferObject.VerseTO;

class PoemBLLTest {
	private IDALFacade poemDALStub;
	private PoemBLL poemBLL;

	PoemBLLTest() {
		// Initialize the stub and BLL
		poemDALStub = new DALStubFacade();
		poemBLL = new PoemBLL(poemDALStub);
	}

	@Test
	@DisplayName("Getting All Poems from Database ")
	void testGetPoems() throws SQLException {
		List<PoemTO> actualPoems = poemBLL.getPoems(1);

		// Assert
		// Check the number of poems
		assertEquals(2, actualPoems.size());

		// Assuming the stub returns specific data, verify that the expected data is
		// present
		PoemTO poem1 = actualPoems.get(0);
		assertEquals(1, poem1.getPoemId());
		assertEquals("Poem 1", poem1.getPoemTitle());

		PoemTO poem2 = actualPoems.get(1);
		assertEquals(2, poem2.getPoemId());
		assertEquals("Poem 2", poem2.getPoemTitle());

		// Additional assertions for demonstrating assertNotNull
		assertNotNull(actualPoems); // Check that the list is not null
	}

	@Test
	@DisplayName("Get all Misras for each poem")
	void testGetAllMisras() throws SQLException {
		
		MisraTO sampleMisra = new MisraTO();
		ArrayList<MisraTO> result = poemBLL.getAllMisras(sampleMisra);
		
		// Assert the result
		assertEquals(1, result.size()); // Expecting one MisraTO in the result list

		// Check MisraTO details
		MisraTO resultMisra = result.get(0);
		
		assertEquals(1, resultMisra.getMisraId());
		assertEquals("SampleMisra1", resultMisra.getMisra1());
		assertEquals("SampleMisra2", resultMisra.getMisra2());
	}

	@Test
	@DisplayName("Getting Poem ID from Verse")
	void testGetPoemIdFromVerse() {

		PoemTO poemTo = new PoemTO(); //Creating Transfer Objects
		
		poemTo.setPoemTitle("SampleVerse1");
		poemTo.setPoemId(123);
		
		String actualPoemId = poemBLL.getPoemIdFromVerse(poemTo.getPoemTitle());
		int expectedPoemId = poemTo.getPoemId();
		
		assertNotNull(actualPoemId); // Assert that the result is not null
		assertEquals(Integer.toString(expectedPoemId), actualPoemId); // Assert equals
	
		
		// Test case 2: Invalid verse with no result
		poemTo.setPoemTitle("NonExistentVerse");
		String actualPoemId2 = poemBLL.getPoemIdFromVerse(poemTo.getPoemTitle());
		
		assertNull(actualPoemId2); // Assert that the result is null

		// Test case 3: Invalid verse with debugging statement
		poemTo.setPoemTitle("DebuggingVerse");
		String actualPoemId3 = poemBLL.getPoemIdFromVerse(poemTo.getPoemTitle());
		
		assertNull(actualPoemId3);
	}

}
