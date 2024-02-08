package org.swaglabs.base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class TestBase {

    public static WebDriver driver;
    public static Properties prop;
    private FileInputStream confiFile;
    private final int waitDuration = 15;
    public static JavascriptExecutor jse;
    private final String configFilePath = "src/test/java/org/swaglabs/config/config.properties";
    private String browser;
    public String baseUrl;

    public TestBase() {

        System.out.println("TestBase Initialized");
        try {
            prop = new Properties();
            confiFile = new FileInputStream(configFilePath);
            prop.load(confiFile);
            jse = (JavascriptExecutor) driver;
        } catch (FileNotFoundException e) {
            System.out.println("File NOT Found " + e);
        } catch (IOException e) {
            System.out.println("File Input Output Issue " + e);
        }
    }

    @BeforeSuite()
    public void setup() {
        System.out.println("BEFORE SUITE METHOD");
        baseUrl = prop.getProperty("base_url");
        browser = prop.getProperty("browser");

        if (browser.equals("chrome")) {
            try {
                System.out.println(browser + "Web Driver create Start");
                driver = new ChromeDriver();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitDuration));
                System.out.println(browser+" Web Driver created");
            } catch (Exception e) {
                System.out.println(browser + " Web Driver Initializing Issue " + e);
            }
        } else if (browser.equalsIgnoreCase("firefox")) {
            try {
                driver = new FirefoxDriver();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitDuration));
                System.out.println(browser+" Web Driver created");
            } catch (Exception e) {
                System.out.println(browser + " Web Driver Initializing Issue " + e);
            }
        } else if (browser.equalsIgnoreCase("edge")) {
            try {
                driver = new EdgeDriver();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitDuration));
                System.out.println(browser+" Web Driver created");
            } catch (Exception e) {
                System.out.println(browser + " Web Driver Initializing Issue " + e);
            }
        }
        driver.manage().window().maximize();
    }


    @AfterSuite
    public void teardown() throws Exception {
        if (driver!=null) {
            System.out.println(browser+" Driver Closed");
            driver.quit();
        }
        confiFile.close();
    }

}
