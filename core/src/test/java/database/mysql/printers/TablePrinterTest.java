package database.mysql.printers;

import org.junit.Assert;
import org.junit.Test;
import database.loaders.mysql.TypeMeta;
import database.managers.PrinterManager;
import database.managers.db_factory.MySQL;
import structures.TreeNode;

public class TablePrinterTest {

    @Test
    public void printTest() {

        PrinterManager manager = new PrinterManager(new MySQL());

        String expectedDDL = "DROP TABLE IF EXISTS animals;" + System.lineSeparator() +
                "CREATE TABLE animals ( " + System.lineSeparator() +
                "nameAnimal varchar(45)," + System.lineSeparator() +
                "population int(11) DEFAULT 0," + System.lineSeparator() +
                "habitat varchar(45) DEFAULT 'All planet');" + System.lineSeparator();
        TreeNode testNode = createTable();
        String actualDDL = manager.executePrinter(testNode);
        Assert.assertEquals(expectedDDL, actualDDL);
    }

    /**
     * Create correct Table node for DDL.
     *
     * @return
     */
    private TreeNode createTable() {
        TreeNode node = new TreeNode("animals", TypeMeta.TABLE);

        node.addChild(new TreeNode(TypeMeta.COLUMNS, TypeMeta.COLUMNS));
        node.addChild(new TreeNode(TypeMeta.INDEXES, TypeMeta.INDEXES));
        node.addChild(new TreeNode(TypeMeta.FOREIGN_KEYS, TypeMeta.FOREIGN_KEYS));
        node.addChild(new TreeNode(TypeMeta.TRIGGERS, TypeMeta.TRIGGERS));

        TreeNode columns = node.getListChild().get(0);
        TreeNode column1 = new TreeNode("nameAnimal", TypeMeta.COLUMN);
        column1.getAttributes().put("Type", "varchar(45)");
        column1.getAttributes().put("Null", "");
        column1.getAttributes().put("Default", null);
        columns.addChild(column1);

        TreeNode column2 = new TreeNode("population", TypeMeta.COLUMN);
        column2.getAttributes().put("Type", "int(11)");
        column2.getAttributes().put("Null", "");
        column2.getAttributes().put("Default", "0");
        columns.addChild(column2);

        TreeNode column3 = new TreeNode("habitat", TypeMeta.COLUMN);
        column3.getAttributes().put("Type", "varchar(45)");
        column3.getAttributes().put("Null", "");
        column3.getAttributes().put("Default", "All planet");
        columns.addChild(column3);

        return node;
    }
}
