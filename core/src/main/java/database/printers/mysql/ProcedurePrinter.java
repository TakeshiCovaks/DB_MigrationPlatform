package database.printers.mysql;

import database.annotations.printer.Printer;
import database.loaders.mysql.TypeMeta;
import database.printers.Printers;
import org.apache.log4j.Logger;
import strategy.Constants;
import structures.TreeNode;

import java.util.*;

/**
 * Class for formed DDL for STORED_PROCEDURE.
 */
@Printer(typeMeta = TypeMeta.STORED_PROCEDURE)
public class ProcedurePrinter implements Printers {

    public static final Logger LOG = Logger.getLogger(ProcedurePrinter.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    @Override
    public String print(TreeNode node, Map<String, Printers> mapInstance) {
        LOG.debug(bundle.getString("createDdlFor") + this.getClass().getName() + " " + node.getNameElement());

        if(node.getParent().getData().equals(TypeMeta.STORED_PROCEDURE)){
            LOG.debug("This element do not have a DDL.");
            return "This element do not have a DDL.";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("DROP PROCEDURE IF EXISTS ").append(node.getNameElement()).append(";").append(System.lineSeparator());
        builder.append("DELIMITER $$").append(System.lineSeparator());
        builder.append("CREATE PROCEDURE ").append(node.getNameElement()).append("(");
        builder.append(getBodyProcedure(node)).append(")").append(System.lineSeparator());
        builder.append(node.getAttributes().get("BODY_PROCEDURE"));
        builder.append(";$$").append(System.lineSeparator());
        builder.append("DELIMITER ;").append(System.lineSeparator());

        return builder.toString();
    }

    /**
     * The method forms the procedure parameter string and the returned parameter.
     *
     * @param node the node for which the DDL will be formed.
     * @return
     */
    private String getBodyProcedure(TreeNode node) {
        StringBuilder parameters = new StringBuilder();
        String returnParam = "";

        ArrayList<TreeNode> listParam = node.getListChild();
        Collections.sort(listParam, new Comparator<TreeNode>() {
            @Override
            public int compare(TreeNode o1, TreeNode o2) {
                return Integer.valueOf(o1.getNameElement()) - Integer.valueOf(o2.getNameElement());
            }
        });

        for (int i = 0; i < listParam.size(); i++) {
            HashMap<String, String> attr = listParam.get(i).getAttributes();
            parameters.append(attr.get("PARAMETER_NAME")).append(' ').append(attr.get("DTD_IDENTIFIER"));
            if (i < listParam.size() - 1) {
                parameters.append(", ");
            }
        }
        parameters.append(returnParam);

        return parameters.toString();
    }


}
