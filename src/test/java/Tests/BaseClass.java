package Tests;

import driverfactory.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public abstract class BaseClass {
    static WebDriver driver;
    static final Logger LOGGER = LogManager.getLogger("ChromeTestLog");


    static final By XIAOMI_CHECK_BOX_LOCATOR = By.id("7893318_7701962");
    static final By HONOR_CHECK_BOX_LOCATOR = By.id("7893318_15292504");
    static final By SORT_BY_PRICE_BTN_LOCATOR = By.cssSelector("div[data-bem*='id\":\"aprice\"");

    static final By FIRST_XIAOMI_PHONE_BTN_LOCATOR = By.cssSelector("div[data-id='model-401338434']");
    static final By ADD_TO_CART_XIAOMI_BTN_LOCATOR = By.cssSelector("div[data-bem*='id\":\"401338434\"']");
    static final By ADD_TO_CART_HONOR_BTN_LOCATOR = By.cssSelector("div[data-bem*='id\":\"85840670\"']");
    static final By PHONE_ADDED_BAR_LOCATOR = By.cssSelector("div[class='popup-informer__title']");

    static final By COMPARE_BTN_LOCATOR = By.cssSelector("a[class='button button_size_m button_theme_action i-bem button_js_inited']");
    static final By ITEMS_TO_COMPARE_LOCATOR = By.cssSelector("div[class*='n-compare-cell n-compare-'");
    static final By ALL_PARAMS_LOCATOR = By.cssSelector("span[class='link n-compare-show-controls__all']");
    static final By OPERATION_SYSTEM_LOCATOR = By.xpath("//div[text()= 'Операционная система']");
    static final By DIFFERENT_PARAMS_LOCATOR = By.cssSelector("span[class='link n-compare-show-controls__diff']");


    @BeforeClass
    public static void setUp() {

        String browser = System.getProperty("browser");
        driver = WebDriverFactory.create(browser);
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        LOGGER.info("webdriver configured");
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null)
            driver.quit();
    }
}
