package commands;

import context.ConsoleContext;
import exception.CommandException;
import exception.SerialzableException;
import exception.ValidateException;
import org.apache.log4j.Logger;
import strategy.Constants;
import strategy.SerializeManager;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Class for realization command Output.
 * Execution of this command consists in serializing the tree structure to a file, database etc
 */
public class OutputCommand implements Command {
    private String fileName;

    private static final Logger LOG = Logger.getLogger(OutputCommand.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    public OutputCommand(String argumentCommand) {
        this.fileName = argumentCommand;
    }

    @Override
    public void executeCommand(ConsoleContext context) throws ValidateException, SerialzableException {
        LOG.info(bundle.getString("startOutput"));

        SerializeManager strategy = new SerializeManager();

        if (context.getTreeNode() == null) {
            throw new CommandException(bundle.getString("emptyTree"));
        }

        strategy.executeStrategy(context.getTreeNode(), fileName);

        LOG.info(bundle.getString("endOutput"));
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutputCommand that = (OutputCommand) o;
        return Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName);
    }

}
