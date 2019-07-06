package processingXML;

import exception.SerialzableException;
import org.junit.Assert;
import org.junit.Test;
import strategy.processingXML.SerializableXML;
import structures.Methods;
import structures.TreeNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SerializableXMLTest {
    private static final String ACTUAL_FILE_NAME = "src\\test\\resources\\inputTestFile.xml";
    private static final String RESULT_FILE_NAME = "src\\test\\resources\\resultSerialization.xml";
    private static final String TEXT_WITH_CDATA = "< Test text with CDATA elements >";
    private static final String TEXT_WITH_OUT_CDATA = "Chop your own wood,№ and it will: warm you twice % ?";

    @Test(expected= SerialzableException.class)
    public void serializableTreeInMXLTestAppException() throws SerialzableException {

        TreeNode nodeFail = new TreeNode();
        SerializableXML serializableFail = new SerializableXML();
        serializableFail.createXMLFileFromTheTree(nodeFail, RESULT_FILE_NAME);
    }

    @Test
    public void serializableTreeInMXLTestCorrectWriteFile() throws SerialzableException {

        TreeNode nodeCorrect = Methods.createExpectedNode();
        SerializableXML serializableCorrect = new SerializableXML();
        serializableCorrect.createXMLFileFromTheTree(nodeCorrect, RESULT_FILE_NAME);

        File resultFile = new File(RESULT_FILE_NAME);
        if(resultFile.exists()){
            String actualString = readFile(RESULT_FILE_NAME);
            String expectedString = readFile(ACTUAL_FILE_NAME);
            resultFile.delete();

            Assert.assertEquals(actualString, expectedString);
        } else {
            Assert.fail();
        }
    }

    @Test
    public void haveCDATATest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        SerializableXML serializer = new SerializableXML();
        Method method = SerializableXML.class.getDeclaredMethod("haveCDATA", String.class);
        method.setAccessible(true);

        boolean resultPositive = (boolean) method.invoke(serializer, TEXT_WITH_CDATA);
        Assert.assertTrue(resultPositive);

        boolean resultNegative = (boolean) method.invoke(serializer, TEXT_WITH_OUT_CDATA);
        Assert.assertFalse(resultNegative);
    }

    private String readFile(String pathFile) {
        byte[] str;
        try(FileInputStream inputFile = new FileInputStream(pathFile)) {
            str = new byte[inputFile.available()];
            inputFile.read(str);
        } catch (IOException e) {
            return "";
        }
        return new String(str);//.replaceAll("\\s+","");
    }
}
