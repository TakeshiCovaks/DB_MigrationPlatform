package commands;

import context.ConsoleContext;
import exception.ParserException;
import exception.SerialzableException;
import exception.ValidateException;

/**
 * CommandManager who is able to perform a class command that implements the Command interface.
 */
public class CommandManager {
    ConsoleContext context;

    public CommandManager(ConsoleContext context) {
        this.context = context;
    }

    /**
     * Command execution method on the corresponding instance of the class.
     *
     * @param command to perform the corresponding operation on the root node.
     */
    public void runCommand(Command command) throws ValidateException, ParserException, SerialzableException {
        command.executeCommand(context);
    }
}