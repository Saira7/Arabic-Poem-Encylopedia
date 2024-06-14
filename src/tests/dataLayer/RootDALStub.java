package dataLayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import dataLayer.objectException;
import com.github.msarhan.lucene.ArabicRootExtractorStemmer;

import transferObject.MisraTO;
import transferObject.RootTO;

public class RootDALStub implements IRootDAL {

	// Create a list of dummy data
	private List<MisraTO> verses = new ArrayList<>();
	List<RootTO> roots = new ArrayList<>();
	HashMap<String,String>verse_root=new HashMap<>();
	
	RootDALStub() {

		verses.add(new MisraTO("فآبُوا بِالرِّماحِ مُكَسَّراتٍ", "وَأُبنَا بِالسُّيُوفِ قَدِ انْحنَيْنَا"));
		roots.add(new RootTO("ءبن", 1));
		verse_root.put("ءبن", "فآبُوا بِالرِّماحِ مُكَسَّراتٍ"+"وَأُبنَا بِالسُّيُوفِ قَدِ انْحنَيْنَا");
	}

	@Override
	public void removeRoot(String name) {

		Iterator<RootTO> iterator = roots.iterator();
		while (iterator.hasNext()) {
			RootTO existingRoot = iterator.next();
			if (existingRoot.getRootName().equals(name)) {
				iterator.remove();
				break;
			}
		}
	}

	@Override
	public void generateRoot() throws SQLException {
		ArabicRootExtractorStemmer stemmerRoot = new ArabicRootExtractorStemmer();
		for (MisraTO verse : verses) {
			String misra1 = verse.getMisra1();
			String misra2 = verse.getMisra2();
			for (String word : misra1.split("\\s+")) {
				if (!word.isEmpty()) {
					Set<String> roots = stemmerRoot.stem(word);
					if (!roots.isEmpty()) {
						String root = roots.iterator().next(); // Get the first element of the set
						int rootId = getOrInsertRootId(root);
						verse_root.put(root, misra1+misra2);
					}

				}
			}
			for (String word : misra2.split("\\s+")) {
				if (!word.isEmpty()) {
					Set<String> roots = stemmerRoot.stem(word);
					if (!roots.isEmpty()) {
						String root = roots.iterator().next(); // Get the first element of the set
						int rootId = getOrInsertRootId(root);
						verse_root.put(root, misra1+misra2);
					}

				}
			}
		}

	}

	private int getOrInsertRootId(String root) {
		for (RootTO roots : roots) {
			if (roots.getRootName() == root) {
				int count = roots.getVersesCount();
				count = count + 1;
				roots.setVerseCount(count);
				return 1;
			}
		}
		roots.add(new RootTO(root, 1));
		return 0;
	}

	@Override
	public List<String> getVersesForRoot(String rootName) {
		List<String>verseRoot=new ArrayList();
		for (HashMap.Entry<String, String> set :
			verse_root.entrySet())
		if(set.getKey()==rootName) {
			verseRoot.add(set.getValue());
		}
		return verseRoot;
	}

	@Override
	public List<RootTO> getAllRootsWithVersesCount() {

		return roots;
	}

	@Override
	public void verifyRoots(String selectedRoot) {
		for (RootTO roots : roots) {
			if (roots.getRootName() == selectedRoot) {
				String status = "verified";
			}
		}

	}

	@Override
	public String getVerificationStatus(String selectedRoot) {

		return "auto";
	}

	@Override
	public List<String> getStemmedRootsForVerse(String id)  {
		
		List<String>verseRoot=new ArrayList();
		for (HashMap.Entry<String, String> set :
			verse_root.entrySet())
		if(set.getValue()==id) {
			verseRoot.add(set.getKey());
		}
		return verseRoot;
	}

	@Override
	public void saveRoot(String rootName, String id) {

		getOrInsertRootId(rootName);
		
	}

}
