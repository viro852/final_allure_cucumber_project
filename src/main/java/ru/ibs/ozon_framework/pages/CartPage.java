package ru.ibs.ozon_framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.ibs.ozon_framework.entity.Product;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.By.xpath;

public class CartPage extends BasePage {

    private By buyLikeCompanyLocator = xpath("//a[text()=\"Подробнее о покупках для юридических лиц\"]/../../../following-sibling::div/div");

    private By allProductsInOzonCartLocator = xpath("//div[@data-widget=\"split\"]/div/following-sibling::div/div[3]/div[1]");

    private By myOzonCartSummaryTextLocator = xpath("//section[@data-widget=\"total\"]/div/div/following-sibling::div/div");

    private By selectAllCheckboxLocator = xpath("//div[@data-widget=\"controls\"]/label");

    private By deleteSelectedButtonLocator = xpath("//span[contains(text(),'Удалить выбранные')]");

    private By attentionMessageDeleteButtonLocator = xpath("//div[contains(text(),\"Удаление\")]/following-sibling::div/following-sibling::div//button");

    private By cartIsEmptyTitleLocator = xpath("//h1[contains(text(),'Корзина пуста')]");


    public void makeSureThatAllPreviouslyAddedProductsAreInTheBasket() {
        checkTitle();
        clickBuyLikeCompanyButton();

        List<Product> innerCart = myInnerCart.getCartInnerlist();
        List<WebElement> ozonCart = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allProductsInOzonCartLocator));

        waitUntilSizeOfTwoCartsBeTheSame(innerCart, ozonCart);
        assertTrue(checkThatCartsAreTheSame(innerCart, ozonCart));
        checkCartsSummary();
    }

    private void waitUntilSizeOfTwoCartsBeTheSame(List<Product> inner, List<WebElement> myOzonCart) {
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return inner.size() == myOzonCart.size();
            }
        });
    }


    private boolean checkThatCartsAreTheSame(List<Product> innerCart, List<WebElement> ozonCart) {
        assertEquals(innerCart.size(), ozonCart.size(), "Количество товаров во 'внутренней корзине' не совпадает с количеством в 'ozon-корзине'");
        List<Integer> myOzonCartRemastered = new ArrayList<>();
        List<Integer> myInnerCartRemastered = new ArrayList<>();

        for (WebElement currentProduct : ozonCart) {
            String currentProductPrice = currentProduct.getText().replaceAll("\\s", "").replaceAll("₽", "");
            int finalPrice = Integer.parseInt(currentProductPrice);
            myOzonCartRemastered.add(finalPrice);
        }

        for (Product currentProduct : innerCart) {
            myInnerCartRemastered.add(currentProduct.getPrice());
        }
        return myInnerCartRemastered.containsAll(myOzonCartRemastered);
    }


    private void clickBuyLikeCompanyButton() {
        WebElement buyLikeCompanyButton = wait.until(ExpectedConditions.visibilityOfElementLocated(buyLikeCompanyLocator));
        buyLikeCompanyButton.click();
        wait.until(ExpectedConditions.invisibilityOf(buyLikeCompanyButton));
    }


    private void checkTitle() {
        wait.until(ExpectedConditions.titleIs("OZON.ru - Моя корзина"));
        assertEquals("OZON.ru - Моя корзина", driverManager.getWebDriver().getTitle(), "Тайтлы не совпали");
    }


    private void checkCartsSummary() {
        WebElement myOzonCartSummaryText = driverManager.getWebDriver().findElement(myOzonCartSummaryTextLocator);
        int result = myOzonCartSummaryText.getText().indexOf(" т");
        assertEquals("Ваша корзина\n" + myInnerCart.getCartInnerlist().size(), myOzonCartSummaryText.getText().substring(0, result), "Summary-текст не прошел проверку");
    }


    public void deleteAllProductsFromCart() {
        System.out.println("перешли в метод: 'deleteAllProductsFromCart'");

        refreshElementAndClick(deleteSelectedButtonLocator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(attentionMessageDeleteButtonLocator));
        refreshElementAndClick(attentionMessageDeleteButtonLocator);
    }

    public void checkThatCartIsEmptyAfterDeletingProducts() {
        WebElement cartIsEmptyTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(cartIsEmptyTitleLocator));
        assertEquals("Корзина пуста", cartIsEmptyTitle.getText());
    }
}