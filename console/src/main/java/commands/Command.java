package commands;


import context.ConsoleContext;
import exception.ParserException;
import exception.SerialzableException;
import exception.ValidateException;

/**
 * Interface to implement the involved commands in the application.
 */
public interface Command {

    /**
     * Executing the command in the passed instance.
     *
     * @param context containing the root of the tree.
     */
    void executeCommand(ConsoleContext context) throws ValidateException, ParserException, SerialzableException;

    /**
     * Method to get command priority.
     *
     * @return
     */
    int getPriority();
}
