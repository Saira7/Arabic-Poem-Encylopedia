
package businessLayer;

import dataLayer.DALFacadeClass;
import dataLayer.IDALFacade;
import dataLayer.IRootDAL;
import transferObject.RootTO;

import java.sql.SQLException;
import java.util.List;

public class RootBLL {

	private IDALFacade rootDAL;

	public RootBLL() throws SQLException {
		this.rootDAL = new DALFacadeClass();
	}
    public RootBLL(IDALFacade rootDAL) {
    	this.rootDAL=rootDAL;
    }
	public void removeRoot(String name) {
		rootDAL.removeRoot(name);
	}

	public void generateroot() throws SQLException {
		rootDAL.generateRoot();
	}

	public List<String> getVersesForRoot(String rootName) {
		return rootDAL.getVersesForRoot(rootName);
	}

	public List<RootTO> getAllRootsWithVersesCount() {
		return rootDAL.getAllRootsWithVersesCount();
	}

	public void verifyRoots(String selectedRoot) {
		rootDAL.verifyRoots(selectedRoot);

	}

	public String getVerificationStatus(String selectedRoot) {
		return rootDAL.getVerificationStatus(selectedRoot);
	}

	public void saveRoot(String rootName, String id) {
		rootDAL.saveRoot(rootName, id);

	}

	public List<String> getStemmedRootsForVerse(String id) {

		return rootDAL.getStemmedRootsForVerse(id);
	}
}
