package ru.ibs.ozon_framework.managers;

public class FrameworkManager {

    private static FrameworkManager frameworkManager;

    private FrameworkManager() {

    }

    public static FrameworkManager getInstance() {
        if (frameworkManager == null) {
            frameworkManager = new FrameworkManager();
        }
        return frameworkManager;
    }

    public void startFramework() {
        WebDriverManager.getInstance().getWebDriver();
    }

    public void finishFramework() {
        WebDriverManager.getInstance().quitWebDriver();
    }
}
