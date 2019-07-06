package database.printers.mysql;

import database.annotations.printer.Printer;
import database.loaders.mysql.TypeMeta;
import database.printers.Printers;
import org.apache.log4j.Logger;
import strategy.Constants;
import structures.TreeNode;

import java.util.*;
import java.util.function.Predicate;

/**
 * Class for formed DDL for FOREIGN_KEY.
 */
@Printer(typeMeta = TypeMeta.FOREIGN_KEY)
public class ForeignKeys implements Printers {

    public static final Logger LOG = Logger.getLogger(ForeignKeys.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    @Override
    public String print(TreeNode node, Map<String, Printers> mapInstance) {
        LOG.debug(bundle.getString("createDdlFor") + this.getClass().getName() + " " + node.getNameElement());

        StringBuilder fkDDL = new StringBuilder();
        HashMap<String, String> attr = node.getAttributes();

        fkDDL.append("ALTER TABLE ").append(node.getParent().getParent().getNameElement());
        fkDDL.append(" ADD CONSTRAINT ").append(attr.get("CONSTRAINT_NAME"));
        fkDDL.append(" FOREIGN KEY").append(" (").append(attr.get("COLUMN_NAME")).append(")");
        fkDDL.append(" REFERENCES ").append(attr.get("REFERENCED_TABLE_NAME"));

        //Found FK from node in this tree.
        Predicate<TreeNode> predicateName = nodeSearch -> nodeSearch.getNameElement().equals(attr.get("REFERENCED_TABLE_NAME"));
        TreeNode root = node.getRootTree();
        ArrayList<TreeNode> resutNodeList = root.searchDepth(predicateName);
        if (resutNodeList.size() > 0) {
            ArrayList<TreeNode> listForFkTables = resutNodeList.get(0).getListChild();
            if (listForFkTables.size() > 0) {
                for (TreeNode index: listForFkTables.get(1).getListChild()) {
                    if (index.getNameElement().equals("PRIMARY"))
                        fkDDL.append(" (").append(index.getAttributes().get("Column_name")).append(") ");
                        break;
                }
            } else {
                return "Please do a full load of the " + resutNodeList.get(0).getNameElement() + " table because they are related";
            }
        }
        fkDDL.append(";").append(System.lineSeparator());

        return fkDDL.toString();
    }
}
