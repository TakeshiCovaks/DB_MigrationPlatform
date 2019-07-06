package commands;

import exception.CommandException;
import exception.ValidateException;
import database.loaders.TypeLoad;

/**
 * Enumeration lists are designed to select the correct command that implements the desired command.
 * Enumerations are arranged in order of priority.
 */
public enum EnumCommands {

    INPUT("input") {
        @Override
        public Command getInstance(String parametersCommand) throws ValidateException {
            if (parametersCommand.isEmpty()) {
                throw new ValidateException("Not correct attributes Input Command");
            }
            return new InputCommand(parametersCommand);
        }
    },

    OUTPUT("output") {
        @Override
        public Command getInstance(String parametersCommand) throws ValidateException {
            if (parametersCommand.isEmpty()) {
                throw new ValidateException("Not correct attributes Output Command");
            }
            return new OutputCommand(parametersCommand);
        }
    },

    SEARCH("search") {
        @Override
        public Command getInstance(String parametersCommand) throws ValidateException {
            int countAttr = parametersCommand.split("\\s").length;
            if (countAttr != 2 & countAttr != 3) {
                throw new ValidateException("Not correct count attributes Search Command");
            }
            return new SearchCommand(parametersCommand);
        }
    },

    LOAD("load") {
        @Override
        public Command getInstance(String parametersCommand) throws ValidateException {
            if (parametersCommand.isEmpty()) {
                throw new ValidateException("Not correct count attributes Load Command");
            }
            int splitter = parametersCommand.indexOf(" ");
            TypeLoad typeLoad = getTypeLoad(parametersCommand.substring(0, splitter));
            String nodePath = parametersCommand.substring(splitter).trim();

            return new LoadCommand(typeLoad, nodePath);
        }
    },

    PRINT("print") {
        @Override
        public Command getInstance(String parametersCommand) throws ValidateException {
            if (parametersCommand.isEmpty()) {
                throw new ValidateException("Not correct count attributes Print Command");
            }
            return new PrinterCommand(parametersCommand);
        }
    },

    CONNECT("connect") {
        @Override
        public Command getInstance(String parametersCommand) throws ValidateException {
            if (parametersCommand.isEmpty()) {
                throw new ValidateException("Not correct count attributes Print Command");
            }
            return new ConnectCommand(parametersCommand);
        }
    };

    private static TypeLoad getTypeLoad(String typeFromConsole) {
        TypeLoad typeLoad = null;
        try {
            typeLoad = TypeLoad.valueOf(typeFromConsole.toUpperCase());
        } catch (Exception e) {
            throw new CommandException("Not correct type for Load", e);
        }
        return typeLoad;
    }

    EnumCommands(String nameEnum) {
    }

    /**
     * Method for getting the right strategy instance
     *
     * @param parametersCommand arguments command line.
     * @return instance command.
     */
    public abstract Command getInstance(String parametersCommand) throws ValidateException;

    /**
     * Forms a string of command names for the regular expression parameter according
     * to which the incoming parameters of the command line will be split into commands
     *
     * @return format string names EnumCommands (example "input|output|search")
     */
    public static String getListNameForRegExp() {
        StringBuilder result = new StringBuilder();
        EnumCommands[] commands = values();
        for (EnumCommands enumCommands : commands) {
            result.append(enumCommands.name().toLowerCase()).append("|");
        }

        return result.toString().substring(0, result.length() - 1);
    }
}

