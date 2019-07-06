package back_end.back.pages.db_for_test;

import back_end.back.pages.Constants;
import database.managers.db_factory.DBFactory;
import database.managers.db_factory.Database;
import database.managers.db_factory.TypeDatabase;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for creating testing database of not exist
 */
public class TestDB {


    private static DBFactory factory;
    private static Database database;
    private static Connection connection;


    public static void main(String[] args) throws SQLException, FileNotFoundException {

        TestDB testDB = new TestDB();
        testDB.createDB_forTest();
        testDB.dropDB();
    }

    /**
     * Method prepare database for tests.
     *
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public void createDB_forTest() throws SQLException, FileNotFoundException {

        factory = new DBFactory();
        String args = TypeDatabase.MYSQL + " " + Constants.URL + ":" + Constants.PORT + " " + Constants.USERNAME + " " + Constants.PASS;
        database = factory.getDatabase(args);
        connection = database.getConnection();

        if (checkDB() == false) {
            createDB();
        }
//        System.out.println(connection.isClosed());
    }

    /**
     * Method to create a database.
     *
     * @throws FileNotFoundException
     */
    private void createDB() throws SQLException {

        if (!connection.isClosed()) {
            ScriptRunner scriptExecutor = new ScriptRunner(connection);
            InputStream in = getClass().getResourceAsStream(Constants.MYSQL_CREATE_TEST_DB_SCRIPT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            scriptExecutor.runScript(reader);
        } else {
            throw new SQLException("No connection");
        }
    }

    /**
     * Method check execute database
     *
     * @return
     * @throws SQLException
     */
    private boolean checkDB() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SHOW DATABASES");
        while (rs.next()) {
            if (rs.getString(1).equals(Constants.NAME_DB)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method drop database.
     *
     * @throws SQLException
     */
    public void dropDB() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP SCHEMA IF EXISTS " + Constants.NAME_DB);
    }
}
