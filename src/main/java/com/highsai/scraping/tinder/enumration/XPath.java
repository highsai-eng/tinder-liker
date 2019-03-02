package com.highsai.scraping.tinder.enumration;

public enum XPath {

    LOGIN_FACEBOOK("//*[@id=\"modal-manager\"]/div/div/div[2]/div/div[3]/div[1]/button"),
    LOGIN_FACEBOOK_PHONE_NUMBER("//*[@id=\"email\"]"),
    LOGIN_FACEBOOK_PASSWORD("//*[@id=\"pass\"]"),
    LOGIN_FACEBOOK_AUTHENTICATION("//*[@id=\"u_0_0\"]"),
    ALERT_SETTING("//*[@id=\"content\"]/span/div/div[2]/div/div/div[3]/button[1]"),
    INFO("//*[@id=\"content\"]/span/div/div[1]/div/main/div[1]/div/div/div[1]/div/div[1]/div[3]/div[6]/div[2]"),
    LIKE("//*[@id=\"content\"]/span/div/div[1]/div/main/div[1]/div/div/div[1]/div/div[2]/button[4]");

    private final String path;

    XPath(final String path) {
        this.path = path;
    }

    public final String getPath() {
        return this.path;
    }
}
