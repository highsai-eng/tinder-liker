package com.highsai.scraping.tinder.operator;

import com.highsai.scraping.tinder.enumration.XPath;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public final class BrowserOperator {

    private static final Logger log = Logger.getLogger(BrowserOperator.class);

    private static final String SYSTEM_PROPERTY_CHROME_DRIVER_KEY = "webdriver.chrome.driver";
    private static final String SYSTEM_PROPERTY_CHROME_DRIVER_VALUE = "./exe/chromedriver";
    private static final String TINDER_URL = "https://tinder.com/?lang=ja";
    private static final String PHONE_NUMBER = "090xxxxxxxx";
    private static final String PASSWORD = "xxxxxxxxxxxxxxx";

    private final WebDriver driver;
    private final WebDriverWait wait;

    private BrowserOperator() {

        log.info("settings system property. key: " + SYSTEM_PROPERTY_CHROME_DRIVER_KEY +
                ", value: " + SYSTEM_PROPERTY_CHROME_DRIVER_VALUE);
        System.setProperty(SYSTEM_PROPERTY_CHROME_DRIVER_KEY, SYSTEM_PROPERTY_CHROME_DRIVER_VALUE);

        this.driver = new ChromeDriver();
        this.driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        this.driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);

        this.wait = new WebDriverWait(this.driver, 20);
    }

    public static BrowserOperator getInstance() {
        return BrowserOperatorHolder.INSTANCE;
    }

    public void login() throws TimeoutException {

        log.info("login start.");

        log.info("get url. url: " + TINDER_URL);
        this.driver.get(TINDER_URL);

        final String mainHandle = this.driver.getWindowHandle();
        log.info("state window handle main:" + mainHandle);

        this.wait.until(ExpectedConditions.elementToBeClickable(
                this.driver.findElement(By.xpath(XPath.LOGIN_FACEBOOK.getPath())))).click();

        this.driver.getWindowHandles().forEach(handle -> {
            log.info("handle: " + handle);
            if (!handle.equals(mainHandle)) {
                this.driver.switchTo().window(handle);
            }
        });

        this.sleep(1000);

        this.driver.findElement(By.xpath(XPath.LOGIN_FACEBOOK_PHONE_NUMBER.getPath()))
                .sendKeys(PHONE_NUMBER);
        this.driver.findElement(By.xpath(XPath.LOGIN_FACEBOOK_PASSWORD.getPath()))
                .sendKeys(PASSWORD);
        this.driver.findElement(By.xpath(XPath.LOGIN_FACEBOOK_AUTHENTICATION.getPath()))
                .submit();

        this.driver.switchTo().window(mainHandle);

        log.info("login end.");
    }

    public void setting() throws TimeoutException {

        log.info("setting start.");

        this.driver.findElement(By.xpath(XPath.ALERT_SETTING.getPath())).click();

        try {
            this.driver.findElement(By.xpath(XPath.ALERT_SETTING.getPath())).click();
        } catch (final StaleElementReferenceException e) {
            this.driver.findElement(By.xpath(XPath.ALERT_SETTING.getPath())).click();
        }

        log.info("setting end.");
    }

    public void likes() throws TimeoutException, WebDriverException {

        log.info("likes start.");

        while (true) {

            // TODO:個人の情報取得
//            this.wait.until(ExpectedConditions.elementToBeClickable(
//                    this.driver.findElement(By.xpath(XPath.INFO.getPath())))).click();

            try {
                this.wait.until(ExpectedConditions.elementToBeClickable(
                        this.driver.findElement(By.xpath(XPath.LIKE.getPath())))).click();
            } catch (final WebDriverException e) {
                log.info("likes end.");
                throw e;
            }
            this.sleep(1000);
        }
    }

    public void shutdown() {
        this.driver.close();
        this.driver.quit();
    }

    private void sleep(final long millis) {

        try {
            Thread.sleep(millis);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class BrowserOperatorHolder {
        private static final BrowserOperator INSTANCE = new BrowserOperator();
    }
}
