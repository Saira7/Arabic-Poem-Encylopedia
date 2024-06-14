package dataLayer;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public interface IpoemFileDAL {
	public void submitToDatabase(DefaultTableModel tableModel, int id);
	public ArrayList<String> tokenSubmitToData(int tokenID, String tokenName, String Pos) ;
	
}
