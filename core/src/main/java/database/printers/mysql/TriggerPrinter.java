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
 * Class for formed DDL for TRIGGER.
 */
@Printer(typeMeta = TypeMeta.TRIGGER)
public class TriggerPrinter implements Printers {

    private static final Logger LOG = Logger.getLogger(TriggerPrinter.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);
    private final String LINE_SEPAR = System.lineSeparator();

    @Override
    public String print(TreeNode node, Map<String, Printers> mapInstance) {
        LOG.debug(bundle.getString("createDdlFor") + this.getClass().getName() + " " + node.getNameElement());

        HashMap<String, String> attr = node.getAttributes();
        StringBuilder triggerDDL = new StringBuilder();

        triggerDDL.append("DROP TRIGGER IF EXISTS ").append(node.getNameElement()).append(";").append(LINE_SEPAR);
        triggerDDL.append("DELIMITER $$").append(LINE_SEPAR);
        triggerDDL.append("CREATE TRIGGER ").append(node.getNameElement()).append(" ").append(LINE_SEPAR);
        triggerDDL.append(attr.get("ACTION_TIMING")).append(" ").append(attr.get("EVENT_MANIPULATION")).append(" ").append(LINE_SEPAR);
        triggerDDL.append("ON ").append(attr.get("EVENT_OBJECT_TABLE")).append(" FOR EACH ROW ").append(LINE_SEPAR);
        triggerDDL.append(attr.get("ACTION_STATEMENT")).append(LINE_SEPAR);
        triggerDDL.append(";$$ DELIMITER ;").append(LINE_SEPAR);

        return triggerDDL.toString();
    }
}
