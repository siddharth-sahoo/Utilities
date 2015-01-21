package com.awesome.pro.utilities.db.mongo;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;

import com.awesome.pro.utilities.PropertyFileUtility;
import com.mongodb.DB;
import com.mongodb.DBCollection;
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
		if (database == null || database.length() == 0) {
			LOGGER.warn("Illegal database name.");
			return null;
		}

		if (collection == null || collection.length() == 0) {
			LOGGER.warn("Illegal collection name.");
			return null;
		}

		final DB db = getDatabase(database);
		if (db == null) {
			LOGGER.warn("Unable to find database: " + database);
			return null;
		}

		final DBCollection dbCollection = db.getCollection(collection);
		if (dbCollection == null) {
			LOGGER.warn("Unable to find collection: " + collection
					+ " in database: " + database);
			return null;
		}

		return dbCollection.find(query);
	}

	/**
	 * @param database Name of database to query.
	 * @param collection Name of collection to query.
	 * @return DB cursor for fetched documents.
	 */
	public static final DBCursor getDocuments(final String database,
			final String collection) {
		if (database == null || database.length() == 0) {
			LOGGER.warn("Illegal database name.");
			return null;
		}

		if (collection == null || collection.length() == 0) {
			LOGGER.warn("Illegal collection name.");
			return null;
		}

		final DB db = getDatabase(database);
		if (db == null) {
			LOGGER.warn("Unable to find database: " + database);
			return null;
		}

		final DBCollection dbCollection = db.getCollection(collection);
		if (dbCollection == null) {
			LOGGER.warn("Unable to find collection: " + collection
					+ " in database: " + database);
			return null;
		}
		
		return dbCollection.find();
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
		if (database == null || database.length() == 0) {
			LOGGER.warn("Illegal database name.");
			return null;
		}

		if (collection == null || collection.length() == 0) {
			LOGGER.warn("Illegal collection name.");
			return null;
		}

		final DB db = getDatabase(database);
		if (db == null) {
			LOGGER.warn("Unable to find database: " + database);
			return null;
		}

		final DBCollection dbCollection = db.getCollection(collection);
		if (dbCollection == null) {
			LOGGER.warn("Unable to find collection: " + collection
					+ " in database: " + database);
			return null;
		}

		return dbCollection.find(query, filterKeys);
	}

	/**
	 * @param database Name of the database.
	 * @return Database reference object.
	 */
	private static final DB getDatabase(final String database) {
		if (database == null || database.length() == 0) {
			return null;
		}
		LOGGER.info("Connecting to database: " + database);
		return MONGO.getDB(database);
	}

	/**
	 * @param database Name of the database.
	 * @param collection Name of the collection.
	 * @param data Data to be inserted.
	 */
	public static final void insertDocument(final String database,
			final String collection, final List<DBObject> data) {
		if (database == null || database.length() == 0) {
			LOGGER.warn("Illegal database name.");
			return;
		}

		if (collection == null || collection.length() == 0) {
			LOGGER.warn("Illegal collection name.");
			return;
		}

		final DB db = getDatabase(database);
		if (db == null) {
			LOGGER.warn("Unable to find database: " + database);
			return;
		}

		final DBCollection dbCollection = db.getCollection(collection);
		if (dbCollection == null) {
			LOGGER.warn("Unable to find collection: " + collection
					+ " in database: " + database);
			return;
		}

		dbCollection.insert(data);
	}

	/**
	 * @param database Name of the database.
	 * @param collection Name of the collection.
	 * @param data Data to be inserted.
	 */
	public static final void insertDocument(final String database,
			final String collection, final DBObject... data) {
		if (database == null || database.length() == 0) {
			LOGGER.warn("Illegal database name.");
			return;
		}

		if (collection == null || collection.length() == 0) {
			LOGGER.warn("Illegal collection name.");
			return;
		}

		final DB db = getDatabase(database);
		if (db == null) {
			LOGGER.warn("Unable to find database: " + database);
			return;
		}

		final DBCollection dbCollection = db.getCollection(collection);
		if (dbCollection == null) {
			LOGGER.warn("Unable to find collection: " + collection
					+ " in database: " + database);
			return;
		}

		dbCollection.insert(data);
	}

	// Private constructor.
	private MongoConnection() { }
}