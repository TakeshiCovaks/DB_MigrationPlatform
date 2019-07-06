package commands;

import context.ConsoleContext;
import exception.ParserException;
import exception.ValidateException;
import org.apache.log4j.Logger;
import strategy.Constants;
import strategy.DeserializeManager;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Class for realization command Input.
 * The execution of this command consists in creates
 * the tree structure from the specified data source.
 */
public class InputCommand implements Command {
    private String fileName;

    private static final Logger LOG = Logger.getLogger(InputCommand.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    public InputCommand(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void executeCommand(ConsoleContext context) throws ValidateException, ParserException {
        LOG.info(bundle.getString("startInput"));

        DeserializeManager strategy = new DeserializeManager();
        context.setTreeNode(strategy.executeStrategy(context.getTreeNode(), fileName));

        LOG.info(bundle.getString("endInput"));
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputCommand that = (InputCommand) o;
        return Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName);
    }
}