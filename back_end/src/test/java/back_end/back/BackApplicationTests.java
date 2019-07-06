package back_end.back;

import back_end.back.pages.db_for_test.TestDB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class BackApplicationTests {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static TestDB testDB;

//    @BeforeClass
//    public static void setUp() throws FileNotFoundException, SQLException {
//        testDB = new TestDB();
//        testDB.createDB_forTest();
//
//        File file = new File(Constants.DRIVER_PATH);
//        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
//
//        driver = new ChromeDriver();
//        wait = new WebDriverWait(driver, 3);
//        driver.manage().window().maximize();
//        driver.get(Constants.URL + ":3000");
//    }
//
//    @AfterClass
//    public static void tearDown() throws SQLException {
//        driver.close();
//        testDB.dropDB();
//    }

    @Test
    public void testApp() {
    }


//    @Test
//    public void testApp() {
//
//        StartPage startPage = new StartPage(driver);
//
//        startPage.selectTypeDB(TypeDatabase.MYSQL);
//        startPage.inputUrl(Constants.URL);
//        startPage.inputPort(Constants.PORT);
//        startPage.inputNameDB(Constants.NAME_DB);
//        startPage.inputUsername(Constants.USERNAME);
//        startPage.inputPass(Constants.PASS);
//        startPage.connect();
//
//        TreePage treePage = startPage.goToAnotherPage();
//
//        treePage.marckDb();
//        treePage.fullLoadClick();
//        treePage.openDbNode();
//        treePage.openTablesNode();
//        treePage.clickTable();
//        treePage.inputKey(Constants.SEARCH_KEY);
//        treePage.inputValue(Constants.SEARCH_VALUE);
//        treePage.clickSearch();
//        treePage.showResultSesarch();
//        treePage.closeSession();
//
//        treePage.checkStartPage();
//    }
}
