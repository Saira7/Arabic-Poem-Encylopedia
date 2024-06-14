package dataLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PoemFileDAL implements IpoemFileDAL {

	// ------- Phase 1 -------------
	private Connection connection;

	private static final Logger logger = LogManager.getLogger(PoemFileDAL.class.getName());

	PoemFileDAL() {
		connection = DatabaseConnection.getInstance().getConnection();
		logger.debug("PoemFileDAL instance created.");

	}

	@Override
	public void submitToDatabase(DefaultTableModel tableModel, int id) {

		int verse_id_count = 0;

		for (int row = 0; row < tableModel.getRowCount(); row++) {
			String verse = tableModel.getValueAt(row, 0).toString();
			String title = tableModel.getValueAt(row, 1).toString();
			String misra1 = tableModel.getValueAt(row, 2).toString();
			String misra2 = tableModel.getValueAt(row, 3).toString();

			try (Statement statement = connection.createStatement()) {

				// Foreign Key (Poem ID) Check
				int latestPoemID = 0; // Default value if the table is empty

				ResultSet resultSet = statement.executeQuery("SELECT Poem_ID FROM poem ORDER BY Poem_ID DESC LIMIT 1");

				if (resultSet.next()) {
					// Retrieve the latest Poem_ID from the result set
					latestPoemID = resultSet.getInt("Poem_ID");
				}

				latestPoemID++;
				String insertQuery_poem = "INSERT INTO poem (Poem_ID, Book_ID, Poem_Title) VALUES (" + latestPoemID
						+ "," + id + ",'" + title + "')";
				statement.executeUpdate(insertQuery_poem);
				logger.info("Inserted into Poem Database");

				String insertQuery_verse = "INSERT INTO verse (Verse_ID,Poem_ID,Misra_1, Misra_2) VALUES ("
						+ verse_id_count + ", '" + latestPoemID + "','" + misra1 + "', '" + misra2 + "')";
				statement.executeUpdate(insertQuery_verse);
				logger.info("Inserted into Verse Database");

			} catch (SQLException e) {
				logger.error("Error in Query: " + e.getMessage());
			}

		}

	}

	@Override
	// ------- Phase 2 -------------
	public ArrayList<String> tokenSubmitToData(int tokenID, String tokenName, String Pos) {

		if (tokenName.isEmpty()) {
			return null;
		}
		   // Split the POS string and take the first part
	    String[] posParts = Pos.split("\\|");
	    String cleanedPos = posParts[0].trim();


		try (Statement statement = connection.createStatement()) {
			// Corrected the syntax of the SQL query string
			String insertQueryToken = "INSERT INTO token (tokenID, tokenName, POS) VALUES (" + tokenID + ", '"
					+ tokenName + "', '" +cleanedPos + "')";
			statement.executeUpdate(insertQueryToken);
			logger.info("Token inserted into the database");
		} catch (SQLException e) {
			logger.error("Error in Query: " + e.getMessage());
		}
		return null;
	}
}
