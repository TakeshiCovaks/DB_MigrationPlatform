package database.managers;

import database.annotations.load.Load;
import database.annotations.load.LoadPackage;
import database.loaders.Loaders;
import database.loaders.TypeLoad;
import database.managers.db_factory.Database;
import exception.LoadException;
import org.apache.log4j.Logger;
import strategy.Constants;
import structures.TreeNode;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * The class is designed to run the resulting method to
 * load metadata from the database into the tree structure.
 */
public class LoadManager {
    private Database database;

    private Connection connection;
    public Map<String, Loaders> mapInstance;

    private static final Logger LOG = Logger.getLogger(LoadManager.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    public LoadManager(Database database) {
        this.database = database;
        loadMapInstance();
    }

    /**
     * Performs node loading depending on type.
     *
     * @param node     node for loading.
     * @param typeLoad type load for node.
     */
    public void executeLoad(TreeNode node, TypeLoad typeLoad) {

        Loaders clazz = mapInstance.get(node.getData());

        try {
            switch (typeLoad) {
                case LAZY:
                    clazz.lazy(node);
                    break;
                case DETAILS:
                    clazz.details(node);
                    break;
                case FULL:
                    clazz.full(node);
                    break;
                default:
                    throw new LoadException(bundle.getString("unknownTypeLoad"));
            }
        } catch (SQLException e) {
            throw new LoadException(bundle.getString("cantLoad"), e);
        }
    }

    /**
     * Method for load instance load classes in Map and set actual connection in instance
     */
    private void loadMapInstance() {
        Reflection reflection = new Reflection(database.getType());
        LOG.debug("Map generation with class instances to load");
        mapInstance = reflection.readClassesRuntime(LoadPackage.class, Load.class);
        connection = database.getConnection();

        for (Map.Entry<String, Loaders> entry: mapInstance.entrySet()) {
            Loaders value = entry.getValue();
            value.setConnection(connection);
            value.setMapInstance(mapInstance);
        }
    }
}
