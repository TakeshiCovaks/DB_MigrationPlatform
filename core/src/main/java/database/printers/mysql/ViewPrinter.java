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

@Printer(typeMeta = TypeMeta.VIEW)
public class ViewPrinter implements Printers {

    private static final Logger LOG = Logger.getLogger(ViewPrinter.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    @Override
    public String print(TreeNode node, Map<String, Printers> mapInstance){
        LOG.debug(bundle.getString("createDdlFor")+ this.getClass().getName() + " "+ node.getNameElement());

        StringBuilder sbDDL = new StringBuilder();
        sbDDL.append("DROP VIEW IF EXISTS ").append(node.getNameElement()).append(";").append(System.lineSeparator());
        sbDDL.append("CREATE VIEW ").append(node.getNameElement()).append(" AS ");
        sbDDL.append(node.getAttributes().get("VIEW_DEFINITION")).append(";").append(System.lineSeparator());

        return sbDDL.toString();
    }
}
