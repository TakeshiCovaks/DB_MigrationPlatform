package commands;

import context.ConsoleContext;
import exception.CommandException;
import exception.ValidateException;
import org.apache.log4j.Logger;
import structures.TreeNode;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * Class for realization command Search.
 * Search command in the tree structure by the name of the element or the key/value attribute
 */
public class SearchCommand implements Command {
    private String argumentCommand;

    private static final Logger LOG = Logger.getLogger(SearchCommand.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(strategy.Constants.MESSAGES_FILE, Locale.US);

    public static final String TYPE_SEARCH_WIDTH = "-w";
    public static final String TYPE_SEARCH_DEPTH = "-d";


    public SearchCommand(String argumentCommand) {
        this.argumentCommand = argumentCommand;

    }

    @Override
    public void executeCommand(ConsoleContext context) throws ValidateException {

        LOG.info("Start SearchCommand");

        if (context.getTreeNode() == null) {
            throw new CommandException(bundle.getString("emptyTree"));
        }

        String[] args = argumentCommand.split("\\s+");

        Predicate<TreeNode> predicate = createPredicate(args);
        ArrayList<TreeNode> result = null;

        String typeSearch = args[args.length - 1].toLowerCase();
        switch (typeSearch) {
            case TYPE_SEARCH_WIDTH:
                LOG.info(bundle.getString("startSearchWidth"));
                result = context.getTreeNode().searchWidth(predicate);
                LOG.info(bundle.getString("endSearchWidth"));
                break;

            case TYPE_SEARCH_DEPTH:
                LOG.info(bundle.getString("startSearchDepth"));
                result = context.getTreeNode().searchDepth(predicate);
                LOG.info(bundle.getString("endSearchDepth"));
                break;

            default:
                LOG.info(bundle.getString("unknownSearchType") + typeSearch);
                throw new ValidateException(bundle.getString("unknownSearchType") + typeSearch);
        }
        displayResultSearch(result);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    /**
     * Method creates the necessary Predicate to search.
     *
     * @param argsSearch arguments command line.
     * @return the predicate depending on the name of the element or the key attribute value.
     */
    private Predicate<TreeNode> createPredicate(String[] argsSearch) throws ValidateException {
        Predicate<TreeNode> predicateForSearch = null;

        int countArgs = argsSearch.length;
        switch (countArgs) {

            case (2):
                String name = argsSearch[0];
                predicateForSearch = node -> node.getNameElement().equals(name);
                break;

            case (3):
                predicateForSearch = node -> {
                    String keyAttr = argsSearch[0];
                    String valueAttr = argsSearch[1];
                    String attrValue = node.getAttributes().get(keyAttr);
                    return attrValue != null && attrValue.equals(valueAttr);
                };
                break;

            default:
                LOG.info(bundle.getString("notCorrectCountSearch"));
                throw new ValidateException(bundle.getString("notCorrectCountSearch"));
        }
        return predicateForSearch;
    }

    private void displayResultSearch(ArrayList<TreeNode> result) {
        if (result.isEmpty()) {
            LOG.info(bundle.getString("notFoundElement"));
        } else {
            result.forEach(treeNode -> LOG.info(treeNode.toString()));
        }
    }
}
