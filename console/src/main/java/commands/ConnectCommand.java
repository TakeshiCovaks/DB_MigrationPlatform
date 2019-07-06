package commands;

import context.ConsoleContext;
import exception.ConnectionException;
import org.apache.log4j.Logger;
import strategy.Constants;
import database.loaders.mysql.TypeMeta;
import database.managers.db_factory.DBFactory;
import database.managers.db_factory.Database;
import structures.TreeNode;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConnectCommand implements Command {
    private String argumentCommand;

    public static final Logger LOG = Logger.getLogger(ConnectCommand.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);

    public ConnectCommand(String argumentCommand) {
        this.argumentCommand = argumentCommand;
    }

    @Override
    public void executeCommand(ConsoleContext context){
        LOG.info("Start ConnectCommand");

        DBFactory dbFactory = new DBFactory();
        Database database = dbFactory.getDatabase(argumentCommand);
        Connection connection = database.getConnection();

        if (connection == null) {
            LOG.info("Connection failed");
            throw new ConnectionException(bundle.getString("connectFailed"));
        }

        String nameDB = "";
        try {
            nameDB = database.getConnection().getCatalog();
        } catch (SQLException e) {
            throw new ConnectionException(bundle.getString("connectFailed"), e);
        }
        context.setTreeNode(new TreeNode(nameDB, TypeMeta.DATABASE));
        context.setDatabase(database);
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
