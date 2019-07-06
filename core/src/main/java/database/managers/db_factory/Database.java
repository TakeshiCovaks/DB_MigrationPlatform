package database.managers.db_factory;

import java.sql.Connection;

/**
 * Interface for properly implementing database instances and getting a connection, type, etc.
 */
public interface Database {
    /**
     * Method for get valid connection for database.
     *
     * @return connection for concrete database.
     */
    Connection getConnection();

    /**
     * Method for get url for database.
     *
     * @return concrete url for current database.
     */
    String getUrl();

    /**
     * Method get type database.
     *
     * @return concrete type database.
     */
    TypeDatabase getType();
}
