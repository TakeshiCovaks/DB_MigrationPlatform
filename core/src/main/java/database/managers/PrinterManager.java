package database.managers;

import org.apache.log4j.Logger;
import strategy.Constants;
import database.annotations.printer.Printer;
import database.annotations.printer.PrinterPackage;
import database.managers.db_factory.Database;
import database.printers.Printers;
import structures.TreeNode;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * The class is intended to direct the formation of DDLs for a specific type of data DB
 */
public class PrinterManager {
    private Database database;
    private Map<String, Printers> mapInstance;

    private static final Logger LOG = Logger.getLogger(PrinterManager.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    public PrinterManager(Database database) {
        this.database = database;
        loadMapInstance();
    }

    /**
     * Generates DDL for the passed node tree.
     *
     * @param node node for DDL.
     * @return generated DDL from node.
     */
    public String executePrinter(TreeNode node) {
        Printers clazz = mapInstance.get(node.getData());
        String resultPrint = clazz.print(node, mapInstance);

        if (resultPrint.isEmpty()) {
            LOG.info("For " + clazz.getClass().getName() + bundle.getString("DdlNotFound"));
        } else {
            LOG.debug(resultPrint);
            LOG.info(bundle.getString("printerSuccessfully"));
        }

        return resultPrint;
    }

    /**
     * Method download Print instance to Map
     */
    public void loadMapInstance() {
        LOG.debug("Map generation with class instances to printer");
        Reflection reflection = new Reflection(database.getType());
        mapInstance = reflection.readClassesRuntime(PrinterPackage.class, Printer.class);
    }
}
