package commands;

import context.ConsoleContext;
import database.managers.PrinterManager;
import exception.CommandException;
import org.apache.log4j.Logger;
import strategy.Constants;
import structures.TreeNode;

import java.util.Locale;
import java.util.ResourceBundle;

public class PrinterCommand implements Command {
    private String [] nodePath;
    private PrinterManager printerManager;

    public static final Logger LOG = Logger.getLogger(PrinterCommand.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);



    public PrinterCommand(String argumentCommand) {
        this.nodePath = argumentCommand.split("\\.");;

    }
    @Override
    public void executeCommand(ConsoleContext context) {
        LOG.info("PrinterCommand");
        TreeNode node = null;
        if (context.getPrinterManager() == null) {
            context.setPrinterManager(new PrinterManager(context.getDatabase()));
        }

        try {
            printerManager = context.getPrinterManager();
            node = context.getTreeNode().searchNodeByPath(nodePath);
            printerManager.executePrinter(node);
        } catch (Exception e) {
            LOG.warn("Cant print tree.", e);
            throw new CommandException(bundle.getString("cantExecutePrinter"));
        }
    }

    @Override
    public int getPriority() {
        return 3;
    }
}
