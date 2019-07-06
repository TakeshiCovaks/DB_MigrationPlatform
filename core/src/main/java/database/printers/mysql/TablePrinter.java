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
 * Class for formed DDL for TABLE.
 */
@Printer(typeMeta = TypeMeta.TABLE)
public class TablePrinter implements Printers {

    private static final Logger LOG = Logger.getLogger(TablePrinter.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    @Override
    public String print(TreeNode node, Map<String, Printers> mapInstance) {
        LOG.debug(bundle.getString("createDdlFor") + this.getClass().getName() + " " + node.getNameElement());

        if(node.getListChild().isEmpty()){
            return "To fill the DDL, make a full table load.";
        }

        StringBuilder tableDDL = new StringBuilder();
        tableDDL.append("DROP TABLE IF EXISTS ").append(node.getNameElement()).append(";").append(System.lineSeparator());
        tableDDL.append("CREATE TABLE ").append(node.getNameElement()).append(" ( ").append(System.lineSeparator());

        for (TreeNode metaTable : node.getListChild()) {
//            if(metaTable.getData().equals(TypeMeta.COLUMNS) && metaTable.getListChild().isEmpty()){
//                return "To fill the DDL, make a full table load.";
//            }
            int countColumns = metaTable.getListChild().size() - 1;
            int lastColumn = 0;
            for (TreeNode currentMeta : metaTable.getListChild()) {
                if (currentMeta.getData().equals(TypeMeta.COLUMN)) {

                    HashMap<String, String> attr = currentMeta.getAttributes();
                    if(attr.isEmpty()) {
                        return "To fill the DDL, make a full table load.";
                    }
                        tableDDL.append(currentMeta.getNameElement()).append(" ").append(attr.get("Type"));
                        tableDDL.append(attr.get("Null").equals("NO") ? " NOT NULL" : "");

                        if (attr.get("Default") != null) {
                            tableDDL.append(" DEFAULT ");
                            String quotes = "";
                            if (attr.get("Type").indexOf("varchar") >= 0) {
                                quotes = "\'";
                            }
                            tableDDL.append(quotes).append(attr.get("Default")).append(quotes);
                        }
                        tableDDL.append(lastColumn < countColumns ? "," : ");").append(System.lineSeparator());

                } else {// This block is for the full output of DDL for the table. Indexes, Triggers, FK.
                    Printers clazz = (Printers) mapInstance.get(currentMeta.getData());
                    tableDDL.append(clazz.print(currentMeta, mapInstance));
                }

                lastColumn++;
            }
        }
        return tableDDL.toString();
    }
}
