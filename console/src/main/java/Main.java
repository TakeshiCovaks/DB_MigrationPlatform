

import context.ConsoleClass;
import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * The main class to run the console application.
 */
public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class);
    public static final String MESSAGES_FILE = "messages";
    private static ResourceBundle bundle = ResourceBundle.getBundle(MESSAGES_FILE, Locale.US);


    /**
     * Main application launch method
     *
     * @param args arguments command line
     */
    public static void main(String[] args) {
        LOG.info(getInfoSystem());

        ConsoleClass consoleClass = new ConsoleClass();
        try {
            consoleClass.startReceiver(args);
        } catch (Exception e) {
            LOG.warn(bundle.getString("appClosed"));
            e.printStackTrace();
        }
    }

    /**
     * Get current information about User, versions OS, Java, JVM, JRE etc.
     */
    private static String getInfoSystem() {

        StringBuilder sb = new StringBuilder("");
        sb.append("OS ").append(System.getProperties().getProperty("sun.desktop")).append("; ");
        sb.append("v ").append(System.getProperties().getProperty("os.version")).append("; ");
        sb.append("Arch. ").append(System.getProperties().getProperty("sun.arch.data.model")).append("; ");
        sb.append("java v. ").append(System.getProperties().getProperty("java.version")).append("; ");
        sb.append("User ").append(System.getProperties().getProperty("user.name")).append("; ");
        sb.append("JVM ").append(System.getProperties().getProperty("java.vm.name")).append("; ");
        sb.append("JRE ").append(System.getProperties().getProperty("java.runtime.version")).append("; ");
        sb.append("Encoding ").append(System.getProperties().getProperty("sun.jnu.encoding")).append("; ");

        return new String(sb);
    }
}
