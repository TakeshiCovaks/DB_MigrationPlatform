package database.mysql;

import org.apache.ibatis.jdbc.ScriptRunner;
import database.managers.db_factory.DBFactory;
import database.managers.db_factory.Database;

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
public class TestDB  {
    private String nameTestDB = "sushko_proj_test";
    private String scriptFileSQL = "/create_meta_mysql_test.sql";

    private static DBFactory factory;
    private static Database database;
    private static Connection connection;


    public static String testDB = "MySQL localHost:3306 root 12345";

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

//        Reflection reflection = new Reflection(TypeDatabase.MYSQL);
        factory = new DBFactory();
        database = factory.getDatabase(testDB);
        connection = database.getConnection();

        if (checkDB() == false) {
            createDB();
        }
        System.out.println(connection.isClosed());
    }

    /**
     * Method to create a database.
     *
     * @throws FileNotFoundException
     */
    private void createDB() throws SQLException {

        if (!connection.isClosed()) {
            ScriptRunner scriptExecutor = new ScriptRunner(connection);
            InputStream in = getClass().getResourceAsStream(scriptFileSQL);
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
            if (rs.getString(1).equals(nameTestDB)) {
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
        statement.execute("DROP SCHEMA IF EXISTS " + nameTestDB);
    }

    public static Connection getConnection() {
        return connection;
    }

    public String getNameTestDB() {
        return nameTestDB;
    }
}
