package com.highsai.scraping.tinder;

import com.highsai.scraping.tinder.operator.BrowserOperator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;

public final class Executor {

    private static final Logger log = Logger.getLogger(Executor.class);

    static {
        DOMConfigurator.configure("./log4j.xml");
    }

    public static void main(final String[] args) {

        final BrowserOperator operator = BrowserOperator.getInstance();

        operator.login();
        operator.setting();

        try {
            operator.likes();
        } catch (final TimeoutException e) {
            log.error(e);
            operator.shutdown();
            System.exit(1);

        } catch (final WebDriverException e) {
            log.info(e);
            operator.shutdown();
            System.exit(0);
        }
    }
}
