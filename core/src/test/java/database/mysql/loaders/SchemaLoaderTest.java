package database.mysql.loaders;

import database.mysql.TestDB;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import database.loaders.mysql.SchemaLoader;
import database.loaders.mysql.TypeMeta;
import database.managers.Reflection;
import database.managers.db_factory.TypeDatabase;
import structures.TreeNode;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class SchemaLoaderTest {
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
//    public void lazyTest() throws SQLException {
//
//        Reflection reflection = new Reflection(TypeDatabase.MYSQL);
//        SchemaLoader schemaLoader = new SchemaLoader();
//        schemaLoader.setConnection(testDB.getConnection());
//
//        TreeNode expectedNode = createExpectedNode();
//        TreeNode initNode = new TreeNode(testDB.getNameTestDB(), TypeMeta.DATABASE);
//        TreeNode actualNode = schemaLoader.lazy(initNode);
//        Assert.assertEquals(expectedNode, actualNode);
//    }

    private TreeNode createExpectedNode() {
        TreeNode node = new TreeNode(testDB.getNameTestDB(), TypeMeta.DATABASE);
        node.addChild(new TreeNode(TypeMeta.TABLES, TypeMeta.TABLES));
        node.addChild(new TreeNode(TypeMeta.VIEWS, TypeMeta.VIEWS));
        node.addChild(new TreeNode(TypeMeta.STORED_PROCEDURES, TypeMeta.STORED_PROCEDURES));
        node.addChild(new TreeNode(TypeMeta.FUNCTIONS, TypeMeta.FUNCTIONS));
        return node;
    }
}
