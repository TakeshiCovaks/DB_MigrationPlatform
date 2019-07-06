package database.managers.db_factory;

import exception.DBException;
import org.apache.log4j.Logger;
import strategy.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class for work with MySQL for getting connection, type database, etc.
 */
public class MySQL implements Database {
    private String url = "jdbc:mysql://";
    private String login;
    private String pass;

    public static final Logger LOG = Logger.getLogger(MySQL.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    public MySQL() {
    }

    public MySQL(String[] args) {
        validateARGS(args);
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            String timeZone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url + timeZone, login, pass);
            if (!connection.isClosed()) {
                LOG.debug("Connection successfully");
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOG.debug(bundle.getString("cantConnect") + e.toString());
            throw new DBException(bundle.getString("cantConnect"), e);
        }
        return connection;
    }

    /**
     * Method check correct count arguments for MySQL in command line.
     *
     * @param args args command line for MySQL
     * @return
     */
    private boolean validateARGS(String[] args) {
        if (args.length < 4) {
            throw new RuntimeException("Not correct count args for Connection");
        }
        this.url += args[1];
        this.login = args[2];
        this.pass = args[3];

        return true;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public TypeDatabase getType() {
        return TypeDatabase.MYSQL;
    }
}
