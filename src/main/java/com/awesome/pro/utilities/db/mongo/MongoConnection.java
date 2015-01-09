package com.awesome.pro.utilities.db.mongo;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.awesome.pro.utilities.PropertyFileUtility;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Takes care of negotiating a connection with the Mongo host.
 * @author siddharth.s
 */
public final class MongoConnection {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(MongoConnection.class);

	private static final String PARAMETER_MONGO_URI = "MongoURI";
	private static final String DEFAULT_MONGO_URI = "mongodb://localhost:27017";
	
	/**
	 * Mongo client instance.
	 */
	private static MongoClient MONGO;

	/**
	 * Initializes MongoDB client.
	 * @param configFile Name of the configuration file to be referred to.
	 * @throws IOException When there is an error in reading the configuration
	 * file or there is a problem in connecting to the specified MongoDB host.
	 */
	public static synchronized final void initialize(final String configFile)
			throws IOException {
		try {
			final PropertyFileUtility config = new PropertyFileUtility(
					configFile);
			MONGO = new MongoClient(new MongoClientURI(config.getStringValue(
					PARAMETER_MONGO_URI, DEFAULT_MONGO_URI)));
		} catch (UnknownHostException e) {
			LOGGER.error("ERROR: Unable to connect to Mongo DB.", e);
			throw e;
		}
		LOGGER.info("Initialized Mongo client.");
	}

	/**
	 * Closes the Mongo connection.
	 */
	public static final synchronized void closeConnection() {
		MONGO.close();
		MONGO = null;
	}

	/**
	 * @param database Name of database to query.
	 * @param collection Name of collection to query.
	 * @param query Matching criteria for values.
	 * @return DB cursor for fetched documents.
	 */
	public static final DBCursor getDocuments(final String database,
			final String collection, final DBObject query) {
		return getDatabase(database)
				.getCollection(collection)
				.find(query);
	}

	/**
	 * @param database Name of database to query.
	 * @param collection Name of collection to query.
	 * @return DB cursor for fetched documents.
	 */
	public static final DBCursor getDocuments(final String database,
			final String collection) {
		return getDatabase(database)
				.getCollection(collection)
				.find();
	}

	/**
	 * @param database Name of the database to query.
	 * @param collection Name of the collection to query.
	 * @param query Matching criteria for values.
	 * @param filterKeys Keys to be returned.
	 * @return DB cursor for fetched documents.
	 */
	public static final DBCursor getDocuments(final String database,
			final String collection, final DBObject query,
			final DBObject filterKeys) {
		return getDatabase(database)
				.getCollection(collection)
				.find(query, filterKeys);
	}

	/**
	 * @param database Name of the database.
	 * @return Database reference object.
	 */
	private static final DB getDatabase(String database) {
		LOGGER.info("Connecting to database: " + database);
		return MONGO.getDB(database);
	}

	// Private constructor.
	private MongoConnection() { }
}