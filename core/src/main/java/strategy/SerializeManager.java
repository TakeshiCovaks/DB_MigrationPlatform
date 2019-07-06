package strategy;

import exception.SerialzableException;
import exception.ValidateException;
import org.apache.log4j.Logger;
import strategy.processingXML.SerializableXML;
import structures.TreeNode;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A class for performing work depending on an instance of the SerializableStrategy.
 */
public class SerializeManager {
    SerializableStrategy strategy;

    private ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);
    private static final Logger LOG = Logger.getLogger(DeserializeManager.class);

    public static final String TYPE_XML = ".xml";

    /**
     * Execution of the necessary strategy depending on the type of structure in which the data will be saved
     *
     * @param node     node from which information will be read.
     * @param filename the name of the file to which the information will be saved.
     */
    public void executeStrategy(TreeNode node, String filename) throws ValidateException, SerialzableException {
        String typeFile = filename.substring(filename.indexOf(".")).trim();
        switch (typeFile) {
            case TYPE_XML:
                this.strategy = new SerializableXML();
                break;

            default:
                LOG.warn(bundle.getString("unknownDataOutput"));
                throw new ValidateException(bundle.getString("unknownDataOutput"));
        }
        strategy.execute(node, filename);
    }
}
