package driverfactory;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class WebDriverFactory {

    public static WebDriver create(String browser) {
        WebDriver driver;

        if(browser.equalsIgnoreCase(Browsers.CHROME.toString())){
            WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
            driver = new ChromeDriver();
        }
        else if(browser.equalsIgnoreCase(Browsers.FIREFOX.toString())){
            WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
            driver = new FirefoxDriver();
/*        } else if(browser.equalsIgnoreCase(Browsers.EDGE.toString())){
            WebDriverManager.getInstance(DriverManagerType.EDGE).setup();
            driver = new EdgeDriver();*/
        } else {
            throw new IllegalStateException("Unexpected value: " + browser);
        }

        return driver;
    }
}
