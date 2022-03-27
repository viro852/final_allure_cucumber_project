package ru.ibs.ozon_framework.steps;

import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.И;
import ru.ibs.ozon_framework.managers.PageManager;
import ru.ibs.ozon_framework.pages.ProductSelectionPage;

import java.util.List;

public class ProductSelectionPageStep {

    private PageManager pageManager = PageManager.getInstance();

    @И("^ограничиваем цену товаров до \"(.+)\"$")
    public void limitByPrice(String price) {
        pageManager.getPage(ProductSelectionPage.class).limitByPrice(price);
    }

    @И("^отмечаем чекбокс – \"(.+)\"$")
    public void activateHighRatingCheckbox(String checkboxName) {
        pageManager.getPage(ProductSelectionPage.class).activateCheckbox(checkboxName);
    }

    @И("^активируем чекбокс – \"(.*)\"$")
    public void activateNFCCheckbox(String checkboxName) {
        pageManager.getPage(ProductSelectionPage.class).activateCheckbox(checkboxName);
    }


    @Затем("^из результатов поиска добавляем в корзину первые (\\d*) товаров с четными ценами$")
    public void addLimitedCountOfProductsToCartIfTheirPricesIsEvenOrOdd(int countLimit) {
        pageManager.getPage(ProductSelectionPage.class).addLimitedCountOfProductsToCartIfTheirPricesIsEven(countLimit);
    }

    @Затем("^из результатов поиска добавляем в корзину первые (\\d*) товаров с нечетными ценами$")
    public void addLimitedCountOfProductsToCartIfTheirPricesIsOdd(int countLimit) {
        pageManager.getPage(ProductSelectionPage.class).addLimitedCountOfProductsToCartIfTheirPricesIsOdd(countLimit);
    }

    @И("^отмечаем чекбоксы с названиями брендов:$")
    public void activateBrandsCheckboxes(List<String> brandsCheckboxesToBeSelected) {
        brandsCheckboxesToBeSelected.forEach(e -> pageManager.getPage(ProductSelectionPage.class).activateBrandsCheckboxes(e));
    }
}
