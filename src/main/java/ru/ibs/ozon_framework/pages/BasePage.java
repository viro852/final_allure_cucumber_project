package ru.ibs.ozon_framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ibs.ozon_framework.entity.MyInnerCart;
import ru.ibs.ozon_framework.managers.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class BasePage {

    protected static MyInnerCart myInnerCart = MyInnerCart.getInstance();
    protected WebDriverManager driverManager = WebDriverManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getWebDriver(), 30);

    public BasePage() {
        PageFactory.initElements(driverManager.getWebDriver(), this);
    }


    public WebElement waitUntilClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void searchByKeyWord(String keyWord) {
        WebElement searchLineInput = driverManager.getWebDriver().findElement(By.xpath("//input[@placeholder=\"Искать на Ozon\"]"));
        searchLineInput.sendKeys(keyWord);
        assertEquals(keyWord, searchLineInput.getAttribute("value"), "value отсутствует/не соответствует " + keyWord + "");
        WebElement searchButton = driverManager.getWebDriver().findElement(By.xpath("//input[@placeholder=\"Искать на Ozon\"]/../../button"));
        waitUntilClickable(searchButton).click();
    }

    public void refreshElementAndClick(By locator) {
        int attempt = 0;
        while (attempt < 10) {
            try {
                WebElement element = driverManager.getWebDriver().findElement(locator);
                element.click();
                System.out.println("Мы успешно кликнули");
                return;
            } catch (StaleElementReferenceException e) {
                System.out.println("Поймали " + e + " попробуем еще");
            }
            ++attempt;
        }
        fail("Не удалось обработать исклчение в методе: 'refreshElementAndClick' ");
    }


    public String getCurrentProductName(WebElement currentProduct) {
        String finalCurrentProductName = null;
        int attempt = 0;

        while (attempt < 10) {
            try {
                WebElement currentProductName = currentProduct.findElement(By.xpath("./../../../div/following-sibling::div/div/a/span"));
                finalCurrentProductName = currentProductName.getText();
                return finalCurrentProductName;
            } catch (StaleElementReferenceException e) {
                System.out.println("Поймали " + e + " попробуем еще");
            }
            ++attempt;
        }
        fail("Не удалось обработать исключение в методе: 'getCurrentProductPrice'");
        return finalCurrentProductName;
    }


    public Integer getCurrentProductPrice(WebElement currentProduct) {
        int finalCurrentProductPrice = 0;
        int attempt = 0;
        System.out.println(currentProduct);
        while (attempt < 50) {
            try {
                String currentProductPriceNoWhiteSpaces = currentProduct.getText().replaceAll(" ", "").replaceAll("₽", "");
                finalCurrentProductPrice = Integer.parseInt(currentProductPriceNoWhiteSpaces);
                return finalCurrentProductPrice;
            } catch (StaleElementReferenceException e) {
                System.out.println("Поймали " + e + " попробуем еще");
            }
            ++attempt;
        }
        fail("Не удалось обработать исключение в методе: 'getCurrentProductPrice'");
        return finalCurrentProductPrice;
    }

}
