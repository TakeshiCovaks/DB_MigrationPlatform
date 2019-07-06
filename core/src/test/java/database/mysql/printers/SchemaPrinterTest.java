package database.mysql.printers;

import org.junit.Assert;
import org.junit.Test;
import database.loaders.mysql.TypeMeta;
import database.printers.mysql.SchemaPrinter;
import structures.TreeNode;

import java.sql.SQLException;

public class SchemaPrinterTest {

    @Test
    public void printTest() throws SQLException {

        TreeNode node = new TreeNode("sushko_proj_test", TypeMeta.DATABASE);
        node.getAttributes().put("Create Database", "DDL for SCHEMA");
        SchemaPrinter schemaPrinter = new SchemaPrinter();
        String expectedDDL = "DROP SCHEMA IF EXISTS sushko_proj_test;\r\n" +
                "CREATE SCHEMA sushko_proj_test;";
        String actualDDL = schemaPrinter.print(node, null);
        Assert.assertEquals(expectedDDL, actualDDL);
    }
}
