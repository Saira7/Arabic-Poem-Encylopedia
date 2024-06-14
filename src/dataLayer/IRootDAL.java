package dataLayer;

import java.sql.SQLException;
import java.util.List;

import transferObject.RootTO;

public interface IRootDAL {

	void removeRoot(String name);

	void generateRoot() throws SQLException;

	public List<String> getVersesForRoot(String rootName);

	List<RootTO> getAllRootsWithVersesCount();



	void verifyRoots(String selectedRoot);



	String getVerificationStatus(String selectedRoot);



	List<String> getStemmedRootsForVerse(String id);



	void saveRoot(String rootName, String id);
}
