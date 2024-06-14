package transferObject;

public class MisraTO {
	 private int misraId;
	    private int PoemId;
	    private String misra1;
	    private String misra2;

	    public MisraTO() {
	    	
	    }
	    public MisraTO(String misra1, String misra2) {
	    	this.misra1=misra1;
	    	this.misra2=misra2;
	    }
	    public int getMisraId() {
	        return misraId;
	    }

	    public void setMisraId(int misraId) {
	        this.misraId = misraId;
	    }

	    public int getPoemeId() {
	        return PoemId;
	    }

	    public void setPoemId(int PoemId) {
	        this.PoemId = PoemId;
	    }

	    public String getMisra1() {
	        return misra1;
	    }

	    public void setMisra1(String misra1) {
	        this.misra1 = misra1;
	    }

	    public String getMisra2() {
	        return misra2;
	    }

	    public void setMisra2(String misra2) {
	        this.misra2 = misra2;
	    }
}
