package BuisnessLayer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import businessLayer.RootBLL;
import dataLayer.DALStubFacade;
import dataLayer.IDALFacade;
import transferObject.RootTO;

class RootBLLTest {
	IDALFacade rootDALStub;
	RootBLL rootBll;
	public RootBLLTest(){
		 rootDALStub = new DALStubFacade();
		 rootBll=new RootBLL(rootDALStub);
	}

	@Test
	@DisplayName("Getting all verses for roots")
	void testGetVersesForRoot() {
		List<String> actualVerses = rootBll.getVersesForRoot("ءبن");

		// Assert
		// Check equality with expected values
		assertEquals("فآبُوا بِالرِّماحِ مُكَسَّراتٍ"+"وَأُبنَا بِالسُّيُوفِ قَدِ انْحنَيْنَا", actualVerses.get(0));
		

		// Check if the list contains expected values
		assertTrue(actualVerses.contains("فآبُوا بِالرِّماحِ مُكَسَّراتٍ"+"وَأُبنَا بِالسُّيُوفِ قَدِ انْحنَيْنَا"));

		// Check if the list does not contain unexpected values
		assertFalse(actualVerses.contains(""));

		// Check if the list is not null
		assertNotNull(actualVerses);

		// Check if an element in the list is not null
		assertNotNull(actualVerses.get(0));

		

	}

	@Test
	@DisplayName("Verse Count for which root is being used")
	void testGetAllRootsWithVersesCount() {
		 List<RootTO> actualRoots = rootBll.getAllRootsWithVersesCount();

	        // Assert
	        assertEquals(1, actualRoots.size());

	        // Assuming the stub returns specific data, verify that the expected data is present
	        RootTO root1 = actualRoots.get(0);
	        assertEquals("ءبن", root1.getRootName());
	        assertEquals(1, root1.getVersesCount());
	        
	}


	@Test
	@DisplayName("Stemming Roots for each Verse")
	void testGetStemmedRootsForVerse() {
		 List<String> actualRoots = rootBll.getStemmedRootsForVerse("فآبُوا بِالرِّماحِ مُكَسَّراتٍ"+"وَأُبنَا بِالسُّيُوفِ قَدِ انْحنَيْنَا");

	        // Assert
	        // Check the number of roots
	       assertEquals(1, actualRoots.size());

	        // Assuming the stub returns specific data, verify that the expected data is present
	        assertTrue(actualRoots.contains("ءبن"));
	       

	        // Additional assertions for demonstrating assertTrue, assertFalse, assertNull, and assertNotNull
	        assertFalse(actualRoots.isEmpty()); // Check that the list is not empty
	        assertNotNull(actualRoots); // Check that the list is not null
	}

}
