package YandexTests;

import java.util.*;

import Driverfactory.Browsers;
import Driverfactory.WebDriverFactory;
import com.google.common.collect.ArrayListMultimap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;

import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.DriverManagerType;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MarketTest {
    private static WebDriver driver;
    private static final Logger LOGGER = LogManager.getLogger("ChromeTestLog");

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
        WebElement xiaomi = driver.findElement(By.id("7893318_7701962"));
        WebElement honor = driver.findElement(By.id("7893318_15292504"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", xiaomi);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", honor);
//        driver.findElement(By.xpath("/html/body/div[2]/div[6]/div[1]/div[2]/div[1]/div[1]/div[3]/a")).click();
        driver.findElement(By.cssSelector("div[data-bem*='id\":\"aprice\"")).click();
        Actions hover = new Actions(driver);
        WebElement addToCartXiomi = driver.findElement
                (By.cssSelector("div[data-bem*='id\":\"401338434\"']"));
        WebDriverWait wait = new WebDriverWait(driver, 20, 100);
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector("div[data-bem*='id\":\"401338434\"']")));
        hover.moveToElement(addToCartXiomi).perform();
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector("div[data-bem*='id\":\"401338434\"']")));
        addToCartXiomi.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='popup-informer__title']")));
        WebElement xiaomiAdded = driver.findElement(By.cssSelector("div[class='popup-informer__title']"));
        LOGGER.info(xiaomiAdded.getText());

        WebElement addToCartHonor = driver.
                findElement(By.cssSelector("div[data-bem*='id\":\"85840670\"']"));
        hover.moveToElement(addToCartHonor).perform();
        wait.until(ExpectedConditions.
                presenceOfElementLocated(By.cssSelector("div[data-bem*='id\":\"85840670\"']")));
        addToCartHonor.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='popup-informer__title']")));
        WebElement honorAdded = driver.findElement(By.cssSelector("div[class='popup-informer__title']"));
        LOGGER.info(honorAdded.getText());

        driver.findElement(By.cssSelector("a[class='button button_size_m button_theme_action i-bem button_js_inited']"))
                .click();

        List<WebElement> itemsToCompare = new ArrayList<>();
        itemsToCompare = driver.findElements(By.cssSelector("div[class*='n-compare-cell n-compare-'"));
        LOGGER.info("First item: " + itemsToCompare.get(0).getText() + ". second item: " + itemsToCompare.get(1).getText() + ".");

        driver.findElement(By.cssSelector("span[class='link n-compare-show-controls__all']")).click();

//        WebElement operationSystem = driver.findElement(By.cssSelector("div[data-bem*='Операционная система']"));
        boolean operationSystemVisible = driver.findElement(By.cssSelector("div[data-bem*='Операционная система']")).isDisplayed();
        if(operationSystemVisible) {
            LOGGER.info("Operation system is present, as expected");
        }else {
            LOGGER.info("Operation system is not present, it's unexpected");
        }
        driver.findElement(By.cssSelector("span[calss='link n-compare-show-controls__diff']")).click();
        if(operationSystemVisible) {
            LOGGER.info("Operation system is present, it's unexpected");
        }else {
            LOGGER.info("Operation system is not present, as expected");
        }
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null)
            driver.quit();
    }
}
