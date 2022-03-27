package ru.ibs.ozon_framework.steps;

import io.cucumber.java.ru.Допустим;
import org.openqa.selenium.WebDriver;
import ru.ibs.ozon_framework.managers.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlCheckStep {

    @Допустим("^мы перешли на сервис: \"(.+)\"$")
    public void checkWeOpenCorrectUrl(String url) {
        WebDriver driver = WebDriverManager.getInstance().getWebDriver();
        driver.get("https://www.ozon.ru/");
        assertEquals(url, driver.getCurrentUrl(), "url не совпадают! Проверьте сайт на котором находитесь!");
    }

}
