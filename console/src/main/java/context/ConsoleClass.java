package context;

import commands.Command;
import commands.CommandManager;
import commands.EnumCommands;
import exception.InputException;
import exception.ParserException;
import exception.SerialzableException;
import exception.ValidateException;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Class for work with console(input, output, search commands etc.).
 */
public class ConsoleClass {
    ConsoleContext context = null;
    private static final Logger LOG = Logger.getLogger(ConsoleClass.class);
    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.US);


    /**
     * The method invokes the processing of command line arguments and initializes the command call in turn.
     */
    public void startReceiver(String[] commandLine) throws ValidateException, ParserException, SerialzableException {

        if (commandLine.length == 0) {
            throw new InputException(bundle.getString("COMMAND_LINE_EMPTY"));
        }
        LOG.info(bundle.getString("startConsole") + Arrays.toString(commandLine));

        Queue<Command> queueCommands = getCommands(commandLine);
        runCommands(queueCommands);
    }

    /**
     * The method initializes the correct sequential execution of commands.
     *
     * @param queueCommands queue of valid commands entered by the user
     */
    private void runCommands(Queue<Command> queueCommands) throws ValidateException, ParserException, SerialzableException {

        context = new ConsoleContext();
        CommandManager receiver = new CommandManager(context);
        while (!queueCommands.isEmpty()) {
            receiver.runCommand(queueCommands.poll());
        }
    }

    /**
     * Forms correct list commands from input arguments
     *
     * @param commandLine arguments command line.
     * @return list of valid commands found in the correct sequence.
     */
    public Queue<Command> getCommands(String[] commandLine) throws ValidateException {

        String argumentsAsString = String.join(" ", commandLine);
        String enumNameAsString = EnumCommands.getListNameForRegExp();
        String[] foundCommandWithParameters = argumentsAsString.split("(?=" + enumNameAsString + ")");

        Queue<Command> commandQueue = new PriorityQueue<>(commandComparator);
        try {
            for (String command : foundCommandWithParameters) {
                EnumCommands currentEnum = EnumCommands.valueOf(command.substring(0, command.indexOf(" ")).toUpperCase());
                String argsCommand = command.substring(command.indexOf(" ")).trim();
                if (currentEnum.equals(EnumCommands.LOAD) | currentEnum.equals(EnumCommands.PRINT)) {
                    addAllLoadCommand(argsCommand, commandQueue, currentEnum);
                } else {
                    commandQueue.add(currentEnum.getInstance(argsCommand));
                }
            }
        } catch (Exception e) {
            throw new ValidateException(bundle.getString("notValidEnumCommand"));
        }
        return commandQueue;
    }

    /**
     * The compares the commands so that the input command is first in line.
     */
    public static Comparator<Command> commandComparator = new Comparator<Command>() {

        @Override
        public int compare(Command command1, Command command2) {
            return command1.getPriority() - command2.getPriority();
        }
    };


    public void addAllLoadCommand(String argsCommand, Queue<Command> commandQueue, EnumCommands currentEnum) throws ValidateException {

        String[] arrayLoaders = argsCommand.split("(?=lazy|details|full)");
        int size = arrayLoaders.length-1;
        for (int i = size; i >= 0; i--) {
            commandQueue.add(currentEnum.getInstance(arrayLoaders[i]));
        }
    }

    public ConsoleContext getContext() {
        return context;
    }
}
