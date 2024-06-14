package businessLayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import dataLayer.DALFacadeClass;
import dataLayer.IDALFacade;
import net.oujda_nlp_team.entity.Result;
import transferObject.MisraTO;

public class PoemFileBLL {
	private DefaultTableModel tableModel;
	IDALFacade poemFile_FacadeDll;

	public PoemFileBLL() throws SQLException {
		poemFile_FacadeDll = new DALFacadeClass();
	}

	public PoemFileBLL(IDALFacade poemFile_FacadeDll) {
		this.poemFile_FacadeDll = poemFile_FacadeDll;
	}

	public DefaultTableModel parseCSV(String filePath) {
		DefaultTableModel tableModel = new DefaultTableModel(new String[] { "Verse", "Title", "Misra #1", "Misra #2" },
				0);

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String val;
			boolean check = false;
			String misra1 = "";
			String misra2 = "";
			int verseCount = 1;
			String currentTitle = "";

			while ((val = reader.readLine()) != null) {
				if (val.contains("_________")) {
					check = true;
				} else if (check) {
					if (val.contains("==========")) {
						check = false;
					}
				} else if (val.contains("[")) {
					currentTitle = val.replaceAll("\\[|\\]", "");
					verseCount = 1;
				} else {
					if (val.contains("(")) {
						val = val.replace("(", "");
					}
					if (val.contains(")")) {
						val = val.replace(")", "");
						if (verseCount == 1) {
							Object[] rowData = { verseCount, currentTitle, misra1, misra2 };
							tableModel.addRow(rowData);
						} else {
							Object[] rowData = { verseCount, " ", misra1, misra2 };
							tableModel.addRow(rowData);
						}
						verseCount++;
						misra1 = "";
						misra2 = "";
					}
					if (val.contains("...")) {
						String[] misraLines = val.split("\\.\\.\\.");
						int misraCount = 1;
						for (String misraLine : misraLines) {
							if (misraCount == 1) {
								misra1 = misraLine.replaceAll("\\[|\\]", "");
							} else if (misraCount == 2) {
								misra2 = misraLine.replaceAll("\\[|\\]", "");
							}
							misraCount++;
						}
					}
				}
			}
		} catch (IOException ex) {
			System.out.println("Error reading the file: " + ex.getMessage());
		}
		return tableModel;
	}

	public void submitPoemDataToDatabase(int index) throws SQLException {

		poemFile_FacadeDll.submitToDatabase(tableModel, index);
		System.out.println("Submitted reading the file");
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	// ------- Phase 2 -------------

	public void saveWordsToDatabase_tokenize(DefaultTableModel tableModel) throws SQLException {

		int tokenID = 0;
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			String token = (String) tableModel.getValueAt(i, 0);

			String Pos = createPOS(token); // creates a part of speech of the word
			poemFile_FacadeDll.tokenSubmitToData(tokenID, token, Pos);
		}
	}

	public List<String[]> splitTextFields_tokenize(DefaultTableModel tableModel, String text1, String text2) {
		String[] misra1 = text1.split("\\s+");
		for (String Misra1 : misra1) {
			tableModel.addRow(new Object[] { Misra1 });
		}

		String[] misra2 = text2.split("\\s+");
		for (String Misra2 : misra2) {
			tableModel.addRow(new Object[] { Misra2 });
		}
		return  Arrays.asList(misra1, misra2);
		
	}

	String createPOS(String tokenzedVerse) {
		List<Result> pos = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken(tokenzedVerse)
				.getAllResults();

		if (!pos.isEmpty()) {
			return pos.get(0).getPartOfSpeech();
		} else {
			// Handle the case where pos is empty, e.g., return a default value or throw an
			// exception
			return "Unknown";
		}
	}

	public void submitToken(MisraTO misraTO) {

		String[] misra1 = misraTO.getMisra1().split("\\s+"); // Spliting Misra 1 into words
		String[] misra2 = misraTO.getMisra2().split("\\s+"); // Splitting Misra 2 into words

		System.out.println("Tokenized Misra 1: ");
		for (int i = 0; i < misra1.length; i++) {
			System.out.print(misra1[i] + '\n');
		}
		System.out.println('\n' + "Tokenized Misra 2: ");
		for (int i = 0; i < misra2.length; i++) {
			System.out.print(misra2[i] + '\n');
		}
		int tokenID = 0;
		for (int i = 0; i < misra1.length; i++) {
			String token = misra1[i];
			List<Result> Alkhalil_pos_maker = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken(token)
					.getAllResults();
			if (!Alkhalil_pos_maker.isEmpty()) {
				String Pos = Alkhalil_pos_maker.get(0).getPartOfSpeech();
				poemFile_FacadeDll.tokenSubmitToData(tokenID, token, Pos);
			} else {
				// Handle the case where pos is empty, e.g., return a default value or throw an
				// exception
				String Pos = "Unknown";
				poemFile_FacadeDll.tokenSubmitToData(tokenID, token, Pos);
			}
		}

	}

}
