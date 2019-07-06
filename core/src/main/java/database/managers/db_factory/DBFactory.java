package database.managers.db_factory;

import org.apache.log4j.Logger;
import strategy.Constants;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class to create a specific database implementation depending on type database.
 */
public class DBFactory {
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);
    public static final Logger LOG = Logger.getLogger(DBFactory.class);

    /**
     * The method of obtaining the instance of the database.
     *
     * @param argsLine arguments command line for database.
     * @return
     */
    public Database getDatabase(String argsLine) {
        if (argsLine.isEmpty()) {
            throw new RuntimeException("Arguments line is empty");
        }
        String [] args = argsLine.split("\\s");

        TypeDatabase typeDB = getEnumTypeDB(args[0]);

        switch (typeDB) {
            case MYSQL:
                return new MySQL(args);

            default:
                return null;
        }
    }

    /**
     * Method of getting an enumeration from command line parameters.
     *
     * @param typeDbInput type DB from console.
     * @return
     */
    private TypeDatabase getEnumTypeDB(String typeDbInput) {

        TypeDatabase typeDatabase = null;
        try {
            typeDatabase = TypeDatabase.valueOf(typeDbInput.toUpperCase());
        } catch (Exception ex) {
            throw new RuntimeException(bundle.getString("cantgetEnumDB"), ex);
        }
        return typeDatabase;
    }
}
