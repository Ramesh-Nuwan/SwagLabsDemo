package org.swaglabs.testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.swaglabs.base.TestBase;
import org.swaglabs.pages.LoginPage;
import org.swaglabs.utils.LoggerHelper;
import org.swaglabs.utils.ReadExcelFile;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginPageTest extends TestBase {

    LoginPage loginPage;
    ReadExcelFile excelFileRead;
    private final String loginLogoText = "Swag Labs";
    private final String loginDataFile = "src/test/java/org/swaglabs/testdata/testdata.xlsx";
    private String homeUrl;
    private static final Logger logger = LogManager.getLogger(LoginPageTest.class);


    public LoginPageTest(){
        super();
    }

    @BeforeClass
    public void loginSetup(){
        loginPage = new LoginPage(driver);
        excelFileRead = new ReadExcelFile(loginDataFile);
        homeUrl = baseUrl+"inventory.html";
    }
    @BeforeMethod
    public void setUp() {
        driver.manage().deleteAllCookies();
        driver.get(baseUrl);
    }

    @Test(testName = "TC01", priority = 1, description = "Check login ui ready to test")
    public void loginPageVisibilityTest() {

        //System.out.println("The login page visibility test");
        logger.info("The login page visibility test start");

        assertTrue(loginPage.isLoginLogoVisible(),"Login logo not visible");
        assertEquals(loginPage.getLoginLogoText(), loginLogoText, "Login logo text has changed");
        assertTrue(loginPage.isUsernameFieldVisible(), "Username Field not visible");
        assertTrue(loginPage.isPasswordFieldVisible(), "Password Field not visible");
        assertTrue(loginPage.isLoginButtonVisible(), "Login button not visible");

        logger.info("The login page visibility test end");

    }

    @Test(testName ="TC02" ,priority = 2, dataProvider = "loginData", description = "Perform valid logins")
    public void validLoginTest(String username, String password, String status) {

        logger.info("Valid login test start");

        try {
            if (status.equalsIgnoreCase("TRUE")){
                loginPage.performLogin(username,password);
                assertEquals(driver.getCurrentUrl(),homeUrl,"Home url incorrect after login success.");
                logger.info("Login Success USERNAME="+username+"and PASSWORD="+password);
            }
        }catch (Exception e){
            logger.error("Login Fail USERNAME="+username+"and PASSWORD="+password);
            e.printStackTrace();
        }
        logger.info("Valid login test start");
    }

    @Test(testName ="TC03", priority = 3, dataProvider = "loginData", description = "Perform invalid logins")
    public void inValidLoginTest(String username, String password, String status) {

        logger.info("Invalid login test start");

        try {
            if (status.equalsIgnoreCase("FALSE")){

                if (username.equalsIgnoreCase("null")){
                    loginPage.setPassword(password);
                    loginPage.clickLoginButton();
                    assertEquals(driver.getCurrentUrl(), baseUrl, "Incorrect url after login null username");
                    assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username is required");
                } else if (password.equalsIgnoreCase("null")) {
                    loginPage.setUsername(username);
                    loginPage.clickLoginButton();
                    assertEquals(driver.getCurrentUrl(), baseUrl, "Incorrect url after login null password");
                    assertEquals(loginPage.getErrorMessage(), "Epic sadface: Password is required");
                } else if (username.isEmpty()||username.isBlank()) {
                    loginPage.performLogin(username,password);
                    assertEquals(driver.getCurrentUrl(), baseUrl, "Incorrect url after login empty username");
                    assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username is required");
                } else if (password.isEmpty() || password.isBlank()) {
                    loginPage.performLogin(username,password);
                    assertEquals(driver.getCurrentUrl(), baseUrl, "Incorrect url after login empty password");
                    assertEquals(loginPage.getErrorMessage(), "Epic sadface: Password is required");
                } else {
                    loginPage.performLogin(username,password);
                    assertEquals(driver.getCurrentUrl(), baseUrl, "Incorrect url after invalid login credential");
                    assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username and password do not match any user in this service");
                }

                logger.info("Invalid login try USERNAME="+username+"and PASSWORD="+password);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Invalid login try USERNAME="+username+"and PASSWORD="+password);
        }
        logger.info("Invalid login test end");
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginInputData() {
        return excelFileRead.getTestData("login");
    }

}
