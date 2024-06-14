package dataLayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.msarhan.lucene.ArabicRootExtractorStemmer;

import transferObject.RootTO;

public class RootDAL implements IRootDAL {
	private Connection Connection;

	private static final Logger logger = LogManager.getLogger(RootDAL.class.getName());

	RootDAL() {
		Connection = DatabaseConnection.getInstance().getConnection();
	}

	@Override
	public void removeRoot(String name) {
		String deleteRootSQL = "DELETE FROM roots WHERE root_name = ?";
		String deleteVerseRootSQL = "DELETE FROM verse_root WHERE root_id IN (SELECT root_id FROM roots WHERE root_name = ?)";

		try {
			Connection.setAutoCommit(false);

			// Delete from verse_root table
			try (PreparedStatement deleteVerseRootStatement = Connection.prepareStatement(deleteVerseRootSQL)) {
				deleteVerseRootStatement.setString(1, name);
				deleteVerseRootStatement.executeUpdate();
			} catch (SQLException e) {
				Connection.rollback();
				throw e; // Rethrow the exception
			}

			// Delete from roots table
			try (PreparedStatement deleteRootStatement = Connection.prepareStatement(deleteRootSQL)) {
				deleteRootStatement.setString(1, name);
				deleteRootStatement.executeUpdate();
			} catch (SQLException e) {
				Connection.rollback();
				throw e; // Rethrow the exception
			}

			Connection.commit();
			Connection.setAutoCommit(true);

		} catch (SQLException e) {
			logger.error("An SQL exception occurred", e);
		}
	}

	public void generateRoot() throws SQLException {
		String checkQuery = "SELECT COUNT(*) AS count FROM verse_root";
		String selectQuery = "SELECT verse_id, misra_1, misra_2 FROM verse";
		String insertRootQuery = "INSERT INTO roots (root_name) VALUES (?)";
		String insertVerseRootQuery = "INSERT INTO verse_root (verse_id, root_id) VALUES (?, ?)";

		PreparedStatement checkStatement = Connection.prepareStatement(checkQuery);
		PreparedStatement selectStatement = Connection.prepareStatement(selectQuery);
		PreparedStatement insertRootStatement = Connection.prepareStatement(insertRootQuery,
				Statement.RETURN_GENERATED_KEYS);
		PreparedStatement insertVerseRootStatement = Connection.prepareStatement(insertVerseRootQuery);

		ResultSet checkResultSet = checkStatement.executeQuery();
		checkResultSet.next();
		int count = checkResultSet.getInt("count");

		ArabicRootExtractorStemmer stemmerRoot = new ArabicRootExtractorStemmer();

		ResultSet resultSet = selectStatement.executeQuery();
		while (resultSet.next()) {
			int verseId = resultSet.getInt("verse_id");
			String misra1 = resultSet.getString("misra_1");
			String misra2 = resultSet.getString("misra_2");

			for (String word : misra1.split("\\s+")) {
				if (count < 1 && !word.isEmpty()) {
					Set<String> roots = stemmerRoot.stem(word);
					if (!roots.isEmpty()) {
						String root = roots.iterator().next(); // Get the first element of the set
						int rootId = getOrInsertRootId(root, insertRootStatement); // Get or insert root_id into root
																					// table
						insertVerseRootStatement.setInt(1, verseId);
						insertVerseRootStatement.setInt(2, rootId);
						insertVerseRootStatement.executeUpdate();
					}
				}
			}

			for (String word : misra2.split("\\s+")) {
				if (count < 1 && !word.isEmpty()) {
					Set<String> roots = stemmerRoot.stem(word);
					if (!roots.isEmpty()) {
						String root = roots.iterator().next(); // Get the first element of the set
						int rootId = getOrInsertRootId(root, insertRootStatement); // Get or insert root_id into root
																					// table
						insertVerseRootStatement.setInt(1, verseId);
						insertVerseRootStatement.setInt(2, rootId);
						insertVerseRootStatement.executeUpdate();
					}
				}
			}
		}
	}

	private int getOrInsertRootId(String rootName, PreparedStatement insertRootStatement) throws SQLException {
		int rootId = getRootIdByName(rootName);
		if (rootId == -1) {
			// If root does not exist, insert it and get the generated root_id
			insertRootStatement.setString(1, rootName);
			insertRootStatement.executeUpdate();
			try (ResultSet generatedKeys = insertRootStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					rootId = generatedKeys.getInt(1);
				}
			}
		}
		return rootId;
	}

	private int getRootIdByName(String rootName) throws SQLException {
		String selectRootIdQuery = "SELECT root_id FROM roots WHERE root_name = ?";
		try (PreparedStatement selectRootIdStatement = Connection.prepareStatement(selectRootIdQuery)) {
			selectRootIdStatement.setString(1, rootName);
			try (ResultSet resultSet = selectRootIdStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("root_id");
				}
			}
		}
		// Return a default value if not found
		return -1;
	}

	public List<String> getVersesForRoot(String rootName) {
		List<String> verses = new ArrayList<>();
		String selectVersesQuery = "SELECT v.Misra_1, v.Misra_2, r.root_name FROM roots r "
				+ "JOIN verse_root vr ON r.root_id = vr.root_id " + "JOIN verse v ON vr.verse_id = v.Verse_ID "
				+ "WHERE r.root_name = ?";

		try (PreparedStatement selectVersesStatement = Connection.prepareStatement(selectVersesQuery)) {
			selectVersesStatement.setString(1, rootName);
			try (ResultSet resultSet = selectVersesStatement.executeQuery()) {
				while (resultSet.next()) {
					String misra1 = resultSet.getString("Misra_1");
					String misra2 = resultSet.getString("Misra_2");
					// String rootNameInVerse = resultSet.getString("root_name");
					verses.add(misra1 + "//" + misra2);
				}
			}
		} catch (SQLException e) {
			logger.error("An SQL exception occurred ", e);
		}

		return verses;
	}

	public List<RootTO> getAllRootsWithVersesCount() {
		List<RootTO> roots = new ArrayList<>();
		String selectRootsQuery = "SELECT r.root_name, COUNT(vr.verse_id) AS verses_count " + "FROM roots r "
				+ "LEFT JOIN verse_root vr ON r.root_id = vr.root_id " + "GROUP BY r.root_name";

		try (PreparedStatement selectRootsStatement = Connection.prepareStatement(selectRootsQuery);
				ResultSet resultSet = selectRootsStatement.executeQuery()) {

			while (resultSet.next()) {
				String rootName = resultSet.getString("root_name");
				int versesCount = resultSet.getInt("verses_count");
				roots.add(new RootTO(rootName, versesCount));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return roots;
	}

	public void verifyRoots(String verse) {
	    String verseId = getVerseIdFromVerse(verse);

	    if (verseId != null) {
	        String updateQuery = "UPDATE verse_root SET verification_status = 'Verified' WHERE verse_id = ?";

	        try (PreparedStatement preparedStatement = Connection.prepareStatement(updateQuery)) {
	            preparedStatement.setString(1, verseId);
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            logger.error("An SQL exception occurred", e);
	        }
	    } else {
	        // Handle the case where verseId is null (no matching verse found)
	        logger.error("No matching verse found for the given verse: " + verse);
	    }
	}

	private String getVerseIdFromVerse(String verse) {
	    String selectQuery = "SELECT verse_id FROM verse WHERE misra_1 = ? OR misra_2 = ?";

	    try (PreparedStatement preparedStatement = Connection.prepareStatement(selectQuery)) {
	        preparedStatement.setString(1, verse);
	        preparedStatement.setString(2, verse);

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                return String.valueOf(resultSet.getInt("verse_id"));
	            }
	        }
	    } catch (SQLException e) {
	        logger.error("An SQL exception occurred", e);
	    }

	    // Return a default value
	    return null;
	}

	public String getVerificationStatus(String verse) {
	    String verseId = getVerseIdFromVerse(verse);

	    if (verseId != null) {
	        String selectQuery = "SELECT verification_status FROM verse_root WHERE verse_id = ?";

	        try (PreparedStatement preparedStatement = Connection.prepareStatement(selectQuery)) {
	            preparedStatement.setString(1, verseId);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    return resultSet.getString("verification_status");
	                }
	            }
	        } catch (SQLException e) {
	            logger.error("An SQL exception occurred", e);
	        }
	    } else {
	        // Handle the case where verseId is null (no matching verse found)
	        logger.error("No matching verse found for the given verse: " + verse);
	    }

	    // Return a default value
	    return "Auto";
	}

	@Override
	public List<String> getStemmedRootsForVerse(String id) {
		List<String> roots = new ArrayList<>();

		String sql = "SELECT r.root_name FROM verse_root vr " + "JOIN roots r ON vr.root_id = r.root_id "
				+ "WHERE vr.verse_id = ?";

		try (PreparedStatement preparedStatement = Connection.prepareStatement(sql)) {
			preparedStatement.setString(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					roots.add(resultSet.getString("root_name"));
				}
			} catch (SQLException e) {
				logger.error("An SQL exception occurred", e);
			}

		} catch (SQLException e1) {
			logger.error("An SQL exception occurred", e1);
		}

		return roots;
	}

	@Override
	public void saveRoot(String rootName, String verseId) {
		String insertRootQuery = "INSERT INTO roots (root_name) VALUES (?)";
		String selectRootIdQuery = "SELECT root_id FROM roots WHERE root_name = ?";
		String insertVerseRootQuery = "INSERT INTO verse_root (root_id, verse_id,verification_status) VALUES (?, ?, 'verified')";

		try (PreparedStatement insertRootStatement = Connection.prepareStatement(insertRootQuery);
				PreparedStatement selectRootIdStatement = Connection.prepareStatement(selectRootIdQuery);
				PreparedStatement insertVerseRootStatement = Connection.prepareStatement(insertVerseRootQuery)) {

			// Insert into roots if root is not already added
			if (getRootIdByName(rootName) != -1) {
				insertRootStatement.setString(1, rootName);
				insertRootStatement.executeUpdate();
			}

			// Get the root_id for the given rootName
			selectRootIdStatement.setString(1, rootName);
			ResultSet rootIdResultSet = selectRootIdStatement.executeQuery();

			if (rootIdResultSet.next()) {
				int rootId = rootIdResultSet.getInt("root_id");

				// Insert into verse_root
				insertVerseRootStatement.setInt(1, rootId);
				insertVerseRootStatement.setString(2, verseId);
				insertVerseRootStatement.setString(3, "verified");
				insertVerseRootStatement.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error("An SQL exception occurred", e);
		}
	}

}
