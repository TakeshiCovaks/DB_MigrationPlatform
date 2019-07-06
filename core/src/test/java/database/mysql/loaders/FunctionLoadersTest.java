package database.mysql.loaders;

import database.mysql.TestDB;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import database.loaders.mysql.FunctionLoader;
import database.loaders.mysql.TypeMeta;
import database.managers.Reflection;
import database.managers.db_factory.TypeDatabase;
import structures.TreeNode;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;

public class FunctionLoadersTest {
    private static TestDB testDB;

    @BeforeClass
    public static void initDatabase() throws FileNotFoundException, SQLException {
        testDB = new TestDB();
        testDB.createDB_forTest();

    }

    @AfterClass
    public static void dropDB() throws SQLException {
        testDB.dropDB();
    }

//    @Test
//    public void detailsTest() throws SQLException {
//        Reflection reflection = new Reflection(TypeDatabase.MYSQL);
//        FunctionLoader loader = new FunctionLoader();
//        loader.setConnection(testDB.getConnection());
//
//        TreeNode root = new TreeNode(testDB.getNameTestDB(), TypeMeta.DATABASE);
//
//        TreeNode expected = createExpectedNode();
//        TreeNode initNode = new TreeNode("minus", TypeMeta.FUNCTION);
//        root.addChild(initNode);
//        TreeNode actual = loader.details(initNode);
//        resetTime(actual);
//        Assert.assertEquals(expected, actual);
//    }

    /**
     * Creates a complete expected node structure for the function.
     *
     * @return
     */
    private TreeNode createExpectedNode() {
        TreeNode root = new TreeNode(testDB.getNameTestDB(), TypeMeta.DATABASE);

        TreeNode node = new TreeNode("minus", TypeMeta.FUNCTION);
        HashMap<String, String> attributes = new HashMap<>();

        attributes.put("EXTERNAL_LANGUAGE", null);
        attributes.put("ROUTINE_NAME", "minus");
        attributes.put("NUMERIC_PRECISION", "10");
        attributes.put("SECURITY_TYPE", "DEFINER");
        attributes.put("CREATED", "");//Empty time
        attributes.put("COLLATION_NAME", null);
        attributes.put("DEFINER", "root@localhost");
        attributes.put("ROUTINE_CATALOG", "def");
        attributes.put("ROUTINE_BODY", "SQL");
        attributes.put("PARAMETER_STYLE", "SQL");
        attributes.put("CHARACTER_SET_CLIENT", "utf8");
        attributes.put("SQL_DATA_ACCESS", "CONTAINS SQL");
        attributes.put("CHARACTER_MAXIMUM_LENGTH", null);
        attributes.put("DATA_TYPE", "int");
        attributes.put("CHARACTER_SET_NAME", null);
        attributes.put("CHARACTER_OCTET_LENGTH", null);
        attributes.put("LAST_ALTERED", "");//Empty time
        attributes.put("ROUTINE_COMMENT", "");
        attributes.put("DATETIME_PRECISION", null);
        attributes.put("SPECIFIC_NAME", "minus");
        attributes.put("NUMERIC_SCALE", "0");
        attributes.put("ROUTINE_TYPE", "FUNCTION");
        attributes.put("COLLATION_CONNECTION", "utf8_general_ci");
        attributes.put("ROUTINE_SCHEMA", "sushko_proj_test");
        attributes.put("DTD_IDENTIFIER", "int(11)");
        attributes.put("EXTERNAL_NAME", null);
        attributes.put("IS_DETERMINISTIC", "NO");
        attributes.put("SQL_PATH", null);
        attributes.put("SQL_MODE", "STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION");
        attributes.put("ROUTINE_DEFINITION", "RETURN x - y");
        node.setAttributes(attributes);

        TreeNode node0 = new TreeNode("0", TypeMeta.FUNCTION_PARAMETER);
        addAttr(node0, "0", null);

        TreeNode node1 = new TreeNode("1", TypeMeta.FUNCTION_PARAMETER);
        addAttr(node1, "1", "x");

        TreeNode node2 = new TreeNode("2", TypeMeta.FUNCTION_PARAMETER);
        addAttr(node2, "2", "y");

        root.addChild(node);
        node.addChild(node0);
        node.addChild(node1);
        node.addChild(node2);
        return node;
    }

    private void addAttr(TreeNode node, String position, String parName) {
        HashMap<String, String> attr = new HashMap<>();
        attr.put("PARAMETER_NAME", parName);
        attr.put("ORDINAL_POSITION", position);
        attr.put("DTD_IDENTIFIER", "int(11)");
        node.getAttributes().putAll(attr);
    }

    /**
     * The method resets the time of the node so that these fields are identical when comparing.
     *
     * @param node
     */
    private void resetTime(TreeNode node) {
        node.getAttributes().put("CREATED", "");//Empty time
        node.getAttributes().put("LAST_ALTERED", "");//Empty time
    }
}
