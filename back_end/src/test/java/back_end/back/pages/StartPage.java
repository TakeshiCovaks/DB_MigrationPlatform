package back_end.back.pages;

import database.managers.db_factory.TypeDatabase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class StartPage {
    private WebDriver driver;

    public StartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @FindBy(id = "typeDbID")
    private WebElement typeDatabase;

    @FindBy(id = "id-url")
    private WebElement url;

    @FindBy(id = "id-port")
    private WebElement port;

    @FindBy(id = "id-name-database")
    private WebElement nameDatabase;

    @FindBy(id = "id-name-user")
    private WebElement username;

    @FindBy(id = "id-pass")
    private WebElement password;

    @FindBy(id = "button-submit")
    private WebElement buttonConnect;

    @FindBy(id = "closeAuthor")
    private WebElement buttonAuthor;

    @FindBy(id = "button-help")
    private WebElement buttonHelp;

    @FindBy(id = "button-author")
    private WebElement buttonCloseAuthor;

    @FindBy(id = "closeHelp")
    private WebElement buttonCloseHelp;

    public void selectTypeDB(TypeDatabase type) {
        typeDatabase.sendKeys(type.name());
    }

    public void inputUrl(String urlText) {
        url.sendKeys(urlText);
    }

    public void inputPort(String portText) {
        port.sendKeys(portText);
    }

    public void inputNameDB(String nameDbText) {
        nameDatabase.sendKeys(nameDbText);
    }

    public void inputUsername(String userName) {
        username.sendKeys(userName);
    }

    public void inputPass(String pass) {
        password.sendKeys(pass);
    }

    public void connect() {
        buttonConnect.click();
    }

    public TreePage goToAnotherPage() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new TreePage(driver);
    }
}
