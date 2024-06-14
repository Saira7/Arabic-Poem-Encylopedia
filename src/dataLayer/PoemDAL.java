package dataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import transferObject.MisraTO;
import transferObject.PoemTO;
import transferObject.VerseTO;

public class PoemDAL implements IPoemDAL {

	private Connection Connection;
	private static final Logger logger = LogManager.getLogger(PoemDAL.class.getName());

	public PoemDAL() {
		Connection = DatabaseConnection.getInstance().getConnection();
	}

	@Override
	public void insertPoem(PoemTO poem, String bookID) {

		String insertQuery = "INSERT INTO poem (Poem_ID, Book_ID, Poem_Title) VALUES (?, ?, ?)";
		try (PreparedStatement statement = Connection.prepareStatement(insertQuery)) {
			logger.debug("Inside insertPoem function");
			statement.setInt(1, poem.getPoemId()); // Auto Increment
			statement.setString(2, bookID); // Assuming Book_ID is always 1
			statement.setString(3, poem.getPoemTitle());
			statement.executeUpdate();

		} catch (SQLException e) {
			logger.error("An SQL exception occurred", e);
		}
	}

	@Override
	public void insertVerse(VerseTO verse, MisraTO misra) {

		// Foreign Key (Poem ID) Check
		int latestPoemID = 1; // Default value if the table is empty

		logger.debug("Attempting to retrieve the latest Poem_ID from the 'poem' table.");
		try (PreparedStatement preparedStatement = Connection
				.prepareStatement("SELECT Poem_ID FROM poem ORDER BY Poem_ID DESC LIMIT 1")) {
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// Retrieve the latest Poem_ID from the result set
				latestPoemID = resultSet.getInt("Poem_ID");
				logger.debug("Latest Poem_ID retrieved successfully: " + latestPoemID);
			} else {
				// Logging a message if the 'poem' table is empty
				logger.warn("The 'poem' table is empty. Using default value for latestPoemID: " + latestPoemID);
			}
		} catch (SQLException e) {
			logger.error("An SQL exception occurred while retrieving the latest Poem_ID", e);
		}

		String insertQuery = "INSERT INTO verse (Verse_ID, Poem_ID, Misra_1, Misra_2) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = Connection.prepareStatement(insertQuery)) {
			statement.setInt(1, 0); // Auto Increment
			statement.setInt(2, latestPoemID); // Poem Foreign Key
			statement.setString(3, misra.getMisra1());
			statement.setString(4, misra.getMisra2());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error("An SQL exception occurred while inserting a verse", e);
		}

	}

	// Iteration 2 CRUD Poem
	// ________________________________________________________
	@Override
	public ArrayList<PoemTO> getAllPoems(int bookID) throws SQLException {
		ArrayList<PoemTO> poems = new ArrayList<>();
		String selectQuery = "SELECT * FROM poem WHERE Book_ID = " + String.valueOf(bookID);
		try (Statement statement = Connection.createStatement();
				ResultSet resultSet = statement.executeQuery(selectQuery)) {

			while (resultSet.next()) {
				PoemTO poem = new PoemTO();

				poem.setPoemId(Integer.parseInt(resultSet.getString("Poem_ID")));
				poem.setPoemTitle(resultSet.getString("Poem_Title"));
				poems.add(poem);
			}
		} catch (SQLException e) {
			logger.error("An SQL exception occurred", e);
		}

		return poems;
	}

	@Override
	public void deletePoem(String poemID) throws SQLException {

		// Deletion of Misra
		try (

				PreparedStatement preparedStatement = Connection.prepareStatement(
						// Using Poem to delete to clear all the relevent misras of that title
						"DELETE FROM verse WHERE Poem_ID = ?")) {

			preparedStatement.setString(1, poemID);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Misra/Misras deleted successfully. Rows affected: " + rowsAffected);
			} else {
				logger.info("Bug occurred in deleting the misra.");
			}

		} catch (SQLException e) {
			logger.error("An SQL exception occurred", e);
		}

		// Deletion of Title
		try (

				PreparedStatement preparedStatement = Connection
						.prepareStatement("DELETE FROM poem WHERE Poem_ID = ?")) {

			preparedStatement.setString(1, poemID);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Poem deleted successfully. Rows affected: " + rowsAffected);
			} else {
				logger.info("Bug occurred in deleting the poem.");
			}

		} catch (SQLException e) {
			logger.error("An SQL exception occurred", e);
		}

	}

	@Override
	public void updatePoem(PoemTO poems, String ID) throws SQLException {
		try (PreparedStatement preparedStatement = Connection
				.prepareStatement("UPDATE poem SET Poem_Title = ? WHERE Poem_ID = ?")) {

			preparedStatement.setString(1, poems.getPoemTitle());
			preparedStatement.setString(2, ID);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Poem updated successfully. Rows affected: " + rowsAffected);
			} else {
				System.out.println("Failed to update poem. No rows affected.");
			}

		} catch (SQLException e) {
			logger.error("An SQL exception occurred", e);
		}
	}

//Iteration 2 ViewAllPoem __________________________
	@Override
	public ArrayList<MisraTO> getAllMisra(MisraTO id) throws SQLException {
		ArrayList<MisraTO> misras = new ArrayList<>();

		String selectQuery = "SELECT * FROM verse WHERE Poem_ID = " + id.getPoemeId();

		try (Statement statement = Connection.createStatement();
				ResultSet resultSet = statement.executeQuery(selectQuery)) {

			while (resultSet.next()) {
				MisraTO misraResult = new MisraTO();
				misraResult.setMisraId(resultSet.getInt("Verse_ID"));
				misraResult.setMisra1(resultSet.getString("Misra_1"));
				misraResult.setMisra2(resultSet.getString("Misra_2"));
				misras.add(misraResult);
			}
		} catch (SQLException e) {
			logger.error("An SQL exception occurred in getting misra", e);
			System.out.println("Connection error");
		}
		return misras;
	}

	@Override
	public void addMisra(MisraTO misra) {
		try (PreparedStatement preparedStatement = Connection
				.prepareStatement("INSERT INTO verse (Verse_ID , Poem_ID, Misra_1,Misra_2) VALUES (?, ?, ?, ?)")) {

			int verseID = 0; // AUTO GENERATED VERSE ID

			preparedStatement.setInt(1, verseID); // AUTO GENERATED PRIMARY KEY ID
			preparedStatement.setInt(2, misra.getPoemeId()); // POEM ID FOR THE POEM WE ARE IN
			preparedStatement.setString(3, misra.getMisra1());
			preparedStatement.setString(4, misra.getMisra2());

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Poem added successfully. Rows affected: " + rowsAffected);
			} else {
				System.out.println("Failed to add poem. No rows affected.");
			}

		} catch (SQLException e) {
			logger.error("An SQL exception occurred", e);
		}

	}

	@Override
	public void deleteMisra(String ID) {

		try (Connection connection = Connection;
				PreparedStatement preparedStatement = connection
						.prepareStatement("DELETE FROM verse WHERE Verse_ID = ?")) {

			preparedStatement.setString(1, ID);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Misra/Misras deleted successfully. Rows affected: " + rowsAffected);
			} else {
				System.out.println("Failed to delete Misra/Misras. No rows affected.");
			}

		} catch (SQLException e) {
			logger.error("An SQL exception occurred in deleting the misra", e);
		}

	}

	@Override
	public void updateMisra(MisraTO misra, String ID) {
		try (PreparedStatement preparedStatement = Connection
				.prepareStatement("UPDATE verse SET Misra_1 = ?, Misra_2 = ? WHERE Verse_ID = ?")) {

			preparedStatement.setString(1, misra.getMisra1());
			preparedStatement.setString(2, misra.getMisra2());
			preparedStatement.setString(3, ID);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Misra updated successfully. Rows affected: " + rowsAffected);
			} else {
				System.out.println("Failed to update Misra. No rows affected.");
			}

		} catch (SQLException e) {
			logger.error("An SQL exception occurred", e);
		}

	}

	@Override
	// method to retrieve Poem_ID from the verse string
	public String getPoemIdFromVerse(String verse) {
		String selectQuery = "SELECT poem_id FROM verse WHERE misra_1 = ?";

		try (PreparedStatement preparedStatement = Connection.prepareStatement(selectQuery)) {
			preparedStatement.setString(1, verse);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					// Convert and return the poem_id as a String
					return String.valueOf(resultSet.getInt("poem_id"));
				} else {
					// Add a debugging statement
					System.out.println("No result found for verse: " + verse);
					return null;
				}
			}
		} catch (SQLException e) {
			// Print the exception for debugging purposes
			logger.error("An SQL exception occurred ", e);
			return null;
		}
	}

}
