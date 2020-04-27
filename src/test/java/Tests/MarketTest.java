package Tests;

import java.time.Duration;
import java.util.*;


import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MarketTest extends BaseClass {

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
                .pollingEvery(Duration.ofSeconds(3L))
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

        Assert.assertEquals("These are not the phones I'd like to compare", comparationPageActual,
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

        isOperationSystemVisible = driver.findElement(OPERATION_SYSTEM_LOCATOR).isDisplayed();

        if (isOperationSystemVisible) {
            LOGGER.info("Operation system is present, it's unexpected");
        } else {
            LOGGER.info("Operation system is not present, as expected");
        }

        Assert.assertFalse("Operation system is not present, it's unexpected", isOperationSystemVisible);
    }

}
