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
 * Class for formed DDL for VIEW_COLUMN.
 */
@Printer(typeMeta = TypeMeta.VIEW_COLUMN)
public class ViewColumnPrinter implements Printers {

    private static final Logger LOG = Logger.getLogger(ViewColumnPrinter.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    @Override
    public String print(TreeNode node, Map<String, Printers> mapInstance) {
        LOG.debug("View column " + bundle.getString("notHaveDDL"));
        return "View column " + bundle.getString("notHaveDDL");
    }
}
