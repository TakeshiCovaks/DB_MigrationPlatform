package back_end.back.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TreePage {
    private WebDriver driver;

    public TreePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//label[@title='Database']/input")
    private WebElement databaseChechBox;

    @FindBy(id = "full-button")
    private WebElement buttonFullLoad;

    @FindBy(xpath = "//li[@class='rct-node rct-node-parent rct-node-collapsed']/span/button")
    private WebElement openDatabaseElement;


    @FindBy(xpath = "//li/ol/li/span/button")
    private List<WebElement> buttonsMetaDatabase;

    @FindBy(xpath = "//span[@class='rct-title'][text()='"+ Constants.NAME_TABLE + "']")
    private WebElement table;


    @FindBy(id = "search-key-attr")
    private WebElement inputKey;

    @FindBy(id = "search-value-attr")
    private WebElement inputValue;

    @FindBy(id = "button-search")
    private WebElement buttonSearch;

    @FindBy(id = "button-next-step")
    private WebElement showResultSesarch;

    @FindBy(id = "button-out")
    private WebElement buttonOut;

    public void marckDb() {
        databaseChechBox.click();
    }

    public void fullLoadClick() {
        buttonFullLoad.click();
    }

    public void openDbNode() {
        openDatabaseElement.click();
    }

    public void openTablesNode() {
        if(buttonsMetaDatabase.size() == 0) {
            Assert.fail();
        } else {
            buttonsMetaDatabase.get(0).click();
        }
    }

    public void clickTable() {
        table.click();
    }

    public void inputKey(String key) {
        inputKey.sendKeys(key);
    }

    public void inputValue(String value) {
        inputValue.sendKeys(value);
    }

    public void clickSearch() {
        buttonSearch.click();
    }

    public void showResultSesarch() {
        showResultSesarch.click();
    }

    public void closeSession() {
        buttonOut.click();
    }

    public void checkStartPage() {
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='StartPage']")).isDisplayed(), true);
    }
}
