package dataLayer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseConnection {
	// JDBC variable for managing connection
	private static Connection connection;
	private static final Logger logger = LogManager.getLogger(DatabaseConnection.class.getName());
	// Singleton instance
	private static DatabaseConnection instance;

	// Private constructor to prevent instantiation
	private DatabaseConnection() {
	    try {
	        // Load database properties from file
	        Properties properties = new Properties();
	        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
	            if (input == null) {
	                throw new FileNotFoundException("db.properties file not found");
	            }
	            properties.load(input);
	        }

	        String DB_URL = properties.getProperty("database.url");
	        String DB_USER = properties.getProperty("database.username");
	        String DB_PASSWORD = properties.getProperty("database.password");

	        // Establish a connection
	        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	    } catch (FileNotFoundException e) {
	        logger.error("db.properties file not found. Make sure it is in the classpath.", e);
	    } catch (IOException e) {
	        logger.error("Error reading db.properties file", e);
	    } catch (SQLException e) {
	        logger.error("Error establishing the database connection", e);
	    }
	}


	// Get the singleton instance
	public static synchronized DatabaseConnection getInstance() {
		if (instance == null) {
			instance = new DatabaseConnection();
		}
		return instance;
	}

	// Get the connection
	public Connection getConnection() {
		return connection;
	}

	// Close the connection
	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {

			throw new RuntimeException("Error closing the database connection", e);
		}
	}
}
