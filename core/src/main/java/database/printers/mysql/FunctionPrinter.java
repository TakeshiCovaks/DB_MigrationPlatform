package database.printers.mysql;

import database.annotations.printer.Printer;
import database.loaders.mysql.TypeMeta;
import database.printers.Printers;
import org.apache.log4j.Logger;
import strategy.Constants;
import structures.TreeNode;

import java.util.*;

/**
 * Class for formed DDL for FUNCTION
 */
@Printer(typeMeta = TypeMeta.FUNCTION)
public class FunctionPrinter implements Printers {

    public static final Logger LOG = Logger.getLogger(FunctionPrinter.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    @Override
    public String print(TreeNode node, Map<String, Printers> mapInstance) {
        LOG.debug(bundle.getString("createDdlFor")+ this.getClass().getName() + " "+ node.getNameElement());

        if(node.getParent().getData().equals(TypeMeta.FUNCTION)){
            LOG.debug("This element do not have a DDL.");
            return "This element do not have a DDL.";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("DROP FUNCTION IF EXISTS ").append(node.getNameElement()).append(";").append(System.lineSeparator());
        builder.append("DELIMITER $$").append(System.lineSeparator());
        builder.append("CREATE FUNCTION ").append(node.getNameElement());
        builder.append(" (").append(getParameters(node)).append(System.lineSeparator());
        builder.append(node.getAttributes().get("ROUTINE_DEFINITION")).append(System.lineSeparator());
        builder.append(";$$\nDELIMITER ;").append(System.lineSeparator());

        return builder.toString();
    }

    /**
     * The method forms the function parameter string and the returned parameter.
     *
     * @param node the node for which the DDL will be formed.
     * @return
     */
    private String getParameters(TreeNode node) {
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
            if (listParam.get(i).getNameElement().equals("0")) {
                returnParam += "RETURNS " + attr.get("DTD_IDENTIFIER");
            } else {
                parameters.append(attr.get("PARAMETER_NAME")).append(' ').append(attr.get("DTD_IDENTIFIER"));
                if (i < listParam.size() - 1) {
                    parameters.append(", ");
                }
            }
        }
        parameters.append(")").append(System.lineSeparator());
        parameters.append(returnParam);

        return parameters.toString();
    }
}
