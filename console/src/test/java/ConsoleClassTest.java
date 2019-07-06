import commands.Command;
import commands.InputCommand;
import commands.OutputCommand;
import context.ConsoleClass;
import exception.InputException;
import exception.ParserException;
import exception.SerialzableException;
import exception.ValidateException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class ConsoleClassTest {
    ConsoleClass console = new ConsoleClass();

    @Test(expected = InputException.class)
    public void startReceiverExceptionTest() throws ValidateException, ParserException, SerialzableException {
        console = new ConsoleClass();
        console.startReceiver(new String[]{});
    }

    @Test
    public void getCommandsTest() throws ValidateException {
        ConsoleClass console = new ConsoleClass();
        Queue<Command> commandsActual = console.getCommands(new String[]{"output", "result.xml", "input", "workspace.xml"});
        PriorityQueue<Command> commandsExpected = new PriorityQueue<>(commandComparator);
        commandsExpected.add(new InputCommand("workspace.xml"));
        commandsExpected.add(new OutputCommand("result.xml"));

        Assert.assertEquals(commandsExpected.size(), commandsActual.size());
    }

    public static Comparator<Command> commandComparator = new Comparator<Command>(){

        @Override
        public int compare(Command command1, Command command2) {
            if(command1 instanceof InputCommand){
                return -1;
            }
            else if(!(command1 instanceof InputCommand) && command2 instanceof InputCommand){
                return 1;
            }
            return 0;
        }
    };
}
