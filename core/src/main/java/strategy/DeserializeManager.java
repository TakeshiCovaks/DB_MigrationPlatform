package strategy;

import exception.ParserException;
import exception.ValidateException;
import org.apache.log4j.Logger;
import strategy.processingXML.DomParserXML;
import structures.TreeNode;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A class for performing work depending on an instance of the DeserializeStrategy class.
 */
public class DeserializeManager {
    DeserializeStrategy strategy;

    private ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);
    private static final Logger LOG = Logger.getLogger(DeserializeManager.class);

    public static final String TYPE_XML = ".xml";

    /**
     * Execution of the necessary strategy depending on the type of structure from where the data will be read.
     *
     * @param node     root node for data storage.
     * @param fileName file name from which information will be read.
     * @return root node tree.
     */
    public TreeNode executeStrategy(TreeNode node, String fileName) throws ValidateException, ParserException {

        String typeFile = fileName.substring(fileName.indexOf(".")).trim();

        switch (typeFile) {

            case TYPE_XML:
                this.strategy = new DomParserXML();
                break;

            default:
                LOG.info(bundle.getString("unknownDataInput"));
                throw new ValidateException(bundle.getString("unknownDataInput"));
        }
        return strategy.execute(node, fileName);
    }
}
