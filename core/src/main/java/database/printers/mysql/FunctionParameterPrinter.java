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
 * Class for formed DDL for FUNCTION_PARAMETER.
 */
@Printer(typeMeta = TypeMeta.FUNCTION_PARAMETER)
public class FunctionParameterPrinter implements Printers {

    private static final Logger LOG = Logger.getLogger(FunctionParameterPrinter.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    @Override
    public String print(TreeNode node, Map<String, Printers> mapInstance) {
        LOG.debug("Function parameter " + bundle.getString("notHaveDDL"));
        return "Function parameter " + bundle.getString("notHaveDDL");
    }
}
