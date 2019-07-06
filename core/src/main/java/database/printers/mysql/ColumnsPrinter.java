package database.printers.mysql;

import database.annotations.printer.Printer;
import database.loaders.mysql.TypeMeta;
import database.printers.Printers;
import org.apache.log4j.Logger;
import strategy.Constants;
import structures.TreeNode;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class for formed DDL for COLUMNS.
 */
@Printer(typeMeta = TypeMeta.COLUMNS)
public class ColumnsPrinter implements Printers {

    private static final Logger LOG = Logger.getLogger(ColumnsPrinter.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    @Override
    public String print(TreeNode node, Map<String, Printers> mapInstance) {
        LOG.debug("Columns " + bundle.getString("notHaveDDL"));
        return "Columns " + bundle.getString("notHaveDDL");
    }
}
