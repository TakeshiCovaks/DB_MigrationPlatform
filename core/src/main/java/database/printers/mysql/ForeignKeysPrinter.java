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
 * Class for formed DDL for VIEWS.
 */
@Printer(typeMeta = TypeMeta.FOREIGN_KEYS)
public class ForeignKeysPrinter implements Printers {

    private static final Logger LOG = Logger.getLogger(ForeignKeysPrinter.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    @Override
    public String print(TreeNode node, Map<String, Printers> mapInstance) {
        LOG.debug("Foreign keys " + bundle.getString("notHaveDDL"));
        return "Foreign keys " + bundle.getString("notHaveDDL");
    }
}
