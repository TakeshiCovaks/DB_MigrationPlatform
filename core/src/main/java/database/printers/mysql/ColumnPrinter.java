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
 * Class for formed DDL for COLUMN.
 */
@Printer(typeMeta = TypeMeta.COLUMN)
public class ColumnPrinter implements Printers {

    private static final Logger LOG = Logger.getLogger(ColumnPrinter.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    @Override
    public String print(TreeNode node, Map<String, Printers> mapInstance) {
        LOG.debug("Column " + bundle.getString("notHaveDDL"));
        return "Column " + bundle.getString("notHaveDDL");
    }
}
