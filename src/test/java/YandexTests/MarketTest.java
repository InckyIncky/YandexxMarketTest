package YandexTests;

import java.time.Duration;
import java.util.*;

import Driverfactory.Browsers;
import Driverfactory.WebDriverFactory;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MarketTest {
    private static WebDriver driver;
    private static final Logger LOGGER = LogManager.getLogger("ChromeTestLog");


    private static final By XIAOMI_CHECK_BOX_LOCATOR = By.id("7893318_7701962");
    private static final By HONOR_CHECK_BOX_LOCATOR = By.id("7893318_15292504");
    private static final By SORT_BY_PRICE_BTN_LOCATOR = By.cssSelector("div[data-bem*='id\":\"aprice\"");

    private static final By FIRST_XIAOMI_PHONE_BTN_LOCATOR = By.cssSelector("div[data-id='model-401338434']");
    private static final By ADD_TO_CART_XIAOMI_BTN_LOCATOR = By.cssSelector("div[data-bem*='id\":\"401338434\"']");
    private static final By ADD_TO_CART_HONOR_BTN_LOCATOR = By.cssSelector("div[data-bem*='id\":\"85840670\"']");
    private static final By PHONE_ADDED_BAR_LOCATOR = By.cssSelector("div[class='popup-informer__title']");

    private static final By COMPARE_BTN_LOCATOR = By.cssSelector("a[class='button button_size_m button_theme_action i-bem button_js_inited']");
    private static final By ITEMS_TO_COMPARE_LOCATOR = By.cssSelector("div[class*='n-compare-cell n-compare-'");
    private static final By ALL_PARAMS_LOCATOR = By.cssSelector("span[class='link n-compare-show-controls__all']");
    private static final By OPERATION_SYSTEM_LOCATOR = By.xpath("//div[text()= 'Операционная система']");
    private static final By DIFFERENT_PARAMS_LOCATOR = By.cssSelector("span[class='link n-compare-show-controls__diff']");



    @BeforeClass
    public static void setUp() {
        driver = WebDriverFactory.create(Browsers.CHROME);
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        LOGGER.info("webdriver configured");
    }

    @Test
    public void yaMarketTest() {
        driver.get("https://ya.ru/");
        driver.get("https://market.yandex.ru/catalog--mobilnye-telefony/54726/list?hid=91491");


        WebElement xiaomi = driver.findElement(XIAOMI_CHECK_BOX_LOCATOR);
        WebElement honor = driver.findElement(HONOR_CHECK_BOX_LOCATOR);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", xiaomi);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", honor);
        driver.findElement(SORT_BY_PRICE_BTN_LOCATOR).click();

        Actions action = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 30, 200);

        wait.withTimeout(Duration.ofSeconds(30L))
                .pollingEvery(Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(FIRST_XIAOMI_PHONE_BTN_LOCATOR));

        WebElement xiaomiPhone = driver.findElement(FIRST_XIAOMI_PHONE_BTN_LOCATOR);
        WebElement addToCartXiaomi = driver.findElement(ADD_TO_CART_XIAOMI_BTN_LOCATOR);

        wait.withTimeout(Duration.ofSeconds(30L))
                .pollingEvery(Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(FIRST_XIAOMI_PHONE_BTN_LOCATOR));
        action.moveToElement(xiaomiPhone).perform();

        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(ADD_TO_CART_XIAOMI_BTN_LOCATOR)));
        action.moveToElement(addToCartXiaomi).perform();

        wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART_XIAOMI_BTN_LOCATOR));
        driver.findElement(ADD_TO_CART_XIAOMI_BTN_LOCATOR).click();

        WebElement xiaomiAdded = driver.findElement(PHONE_ADDED_BAR_LOCATOR);
        LOGGER.info(xiaomiAdded.getText());

        Assert.assertEquals("Bar shows wrong text", "Товар Смартфон Xiaomi Redmi Go 1/8GB добавлен к сравнению", xiaomiAdded.getText());

        WebElement addToCartHonor = driver.findElement(ADD_TO_CART_HONOR_BTN_LOCATOR);
        action.moveToElement(addToCartHonor).click(addToCartHonor).build().perform();

        wait.until(ExpectedConditions.elementToBeClickable(PHONE_ADDED_BAR_LOCATOR));
        WebElement honorAdded = driver.findElement(PHONE_ADDED_BAR_LOCATOR);
        LOGGER.info(honorAdded.getText());
        Assert.assertEquals("Bar shows wrong text", "Товар Смартфон Honor 7A добавлен к сравнению", honorAdded.getText());

        driver.findElement(COMPARE_BTN_LOCATOR).click();

        List<WebElement> itemsToCompare = driver.findElements(ITEMS_TO_COMPARE_LOCATOR);
        LOGGER.info("First item: " + itemsToCompare.get(0).getText() + ". Second item: " + itemsToCompare.get(1).getText() + ".");
        String comparationPageActual = "Смартфон Honor 7AСмартфон Xiaomi Redmi Go 1/8GB";
        String comparationPageExpectation = itemsToCompare.get(0).getText() + itemsToCompare.get(1).getText();

        Assert.assertEquals("These are not the phones I'd like to compare",comparationPageActual,
                comparationPageExpectation);
        driver.findElement(ALL_PARAMS_LOCATOR).click();

        boolean isOperationSystemVisible = driver.findElement(OPERATION_SYSTEM_LOCATOR).isDisplayed();

        if (isOperationSystemVisible) {

            LOGGER.info("Operation system is present, as expected");
        } else {
            LOGGER.info("Operation system is not present, it's unexpected");
        }
        driver.findElement(DIFFERENT_PARAMS_LOCATOR).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(OPERATION_SYSTEM_LOCATOR));

        isOperationSystemVisible =  driver.findElement(OPERATION_SYSTEM_LOCATOR).isDisplayed();

        if (isOperationSystemVisible) {
            LOGGER.info("Operation system is present, it's unexpected");
        } else {
            LOGGER.info("Operation system is not present, as expected");
        }

        Assert.assertFalse("Operation system is not present, it's unexpected", isOperationSystemVisible);
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null)
            driver.quit();
    }
}
