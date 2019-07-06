package database.printers.mysql;

import database.annotations.printer.Printer;
import database.loaders.mysql.TypeMeta;
import database.printers.Printers;
import org.apache.log4j.Logger;
import strategy.Constants;
import structures.TreeNode;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class for formed DDL for INDEX.
 */
@Printer(typeMeta = TypeMeta.INDEX)
public class IndexPrinter implements Printers {

    private static final Logger LOG = Logger.getLogger(IndexPrinter.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    @Override
    public String print(TreeNode node, Map<String, Printers> mapInstance) {
        LOG.debug(bundle.getString("createDdlFor") + this.getClass().getName() + " " + node.getNameElement());

        HashMap<String, String> attr = node.getAttributes();
        StringBuilder indexDDL = new StringBuilder();

        if (node.getNameElement().equalsIgnoreCase("primary")) {
            indexDDL.append("ALTER TABLE ").append(attr.get("Table")).
                    append(" ADD PRIMARY KEY ").append("(").append(attr.get("Column_name")).append(")");
        } else {
            indexDDL.append("CREATE").append(" INDEX ")
                    .append(node.getNameElement()).append(" ON ").append(attr.get("Table"))
                    .append(" (").append(attr.get("Column_name")).append(")");
        }
        indexDDL.append(";").append(System.lineSeparator());

        return indexDDL.toString();
    }
}
