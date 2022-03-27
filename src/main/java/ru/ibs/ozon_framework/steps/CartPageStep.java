package ru.ibs.ozon_framework.steps;

import io.cucumber.java.ru.Затем;
import ru.ibs.ozon_framework.managers.PageManager;
import ru.ibs.ozon_framework.pages.CartPage;

public class CartPageStep {
    private PageManager pageManager = PageManager.getInstance();

    @Затем("^переходим в 'корзину', убеждаемся,что все ранее добавленные товары находятся в корзине$")
    public void makeSureThatAllPreviouslyAddedProductsAreInTheBasket() {
        pageManager.getPage(CartPage.class).makeSureThatAllPreviouslyAddedProductsAreInTheBasket();
    }


    @Затем("^удаляем все товары из корзины$")
    public void deleteAllProductsFromCart() {
        pageManager.getPage(CartPage.class).deleteAllProductsFromCart();
    }

    @Затем("^проверяем, что корзина не содержит никаких товаров$")
    public void checkThatCartIsEmptyAfterDeletingProducts() {
        pageManager.getPage(CartPage.class).checkThatCartIsEmptyAfterDeletingProducts();
    }
}
