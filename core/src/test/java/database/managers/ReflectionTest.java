package database.managers;

import org.junit.Assert;
import org.junit.Test;
import database.annotations.load.LoadPackage;
import exception.ReflectionException;
import database.managers.db_factory.TypeDatabase;

import java.io.IOException;

public class ReflectionTest {

    @Test
    public void searchDirWithFileTest() throws IOException, ClassNotFoundException {
        Reflection reflection = new Reflection(TypeDatabase.MYSQL);
        String expectedDir = System.getProperty("user.dir") + "\\target\\test-classes\\database\\managers\\package_with_annotation";
        String actualDir = reflection.searchDirWithFile(LoadPackage.class);
        Assert.assertEquals(expectedDir, actualDir);
    }

    @Test(expected = ReflectionException.class)
    public void addAllFailData() {
        Reflection reflection = new Reflection(TypeDatabase.MYSQL);
        reflection.readClassesRuntime(String.class, String.class);
    }
}
