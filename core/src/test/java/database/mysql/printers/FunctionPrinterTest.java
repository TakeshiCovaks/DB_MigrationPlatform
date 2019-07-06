package database.mysql.printers;

import database.printers.Printers;
import database.printers.mysql.SchemaPrinter;
import org.junit.Assert;
import org.junit.Test;
import database.loaders.mysql.TypeMeta;
import database.printers.mysql.FunctionPrinter;
import structures.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class FunctionPrinterTest {

    @Test
    public void printTest() {

        Map<String, Printers> mapInstance = new HashMap<>();
        mapInstance.put(TypeMeta.FUNCTION, new FunctionPrinter());

        TreeNode node = new TreeNode("minus", TypeMeta.FUNCTION);
        node.setParent(new TreeNode("Functions",TypeMeta.FUNCTIONS));
        node.getAttributes().put("ROUTINE_DEFINITION", "BEGIN\n" +
                "\tRETURN x - y;\n" +
                "END");
        FunctionPrinter functionPrinter = new FunctionPrinter();
        String expectedDDL = "DROP FUNCTION IF EXISTS minus;\r\n" +
                "DELIMITER $$\r\n" +
                "CREATE FUNCTION minus ()\r\n" +
                "\r\n" +
                "BEGIN\n\t" +
                "RETURN x - y;\n" +
                "END\r\n" +
                ";$$\n" +
                "DELIMITER ;\r\n";
        String actualDDL = functionPrinter.print(node, mapInstance);
        Assert.assertEquals(expectedDDL, actualDDL);
    }
}
