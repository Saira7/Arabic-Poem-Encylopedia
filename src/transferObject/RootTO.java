package transferObject;

public class RootTO {

	private String rootName;
	private int versesCount;

	public RootTO(String rootName, int versesCount) {

		this.rootName = rootName;
		this.versesCount = versesCount;
	}

	public String getRootName() {
		//return rootName
		return rootName;
	}

	public void setRootName(String rootName) {
		//set rootName
		this.rootName = rootName;
	}
	public void setVerseCount(int verseCount) {
		//set verses for roots
		this.versesCount = verseCount;
	}
	public int getVersesCount() {
		//return verses
		return versesCount;
	}



}
