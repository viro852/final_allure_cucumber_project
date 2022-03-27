package ru.ibs.ozon_framework.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.ibs.ozon_framework.entity.Product;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.openqa.selenium.By.xpath;

public class ProductSelectionPage extends BasePage {

    private By toCartButtonLocator = xpath("//a[@href=\"/cart\"]");


    private By NFCCheckboxLocator = xpath("//div[contains(text(),\"Беспроводные\")]/following-sibling::div/div/span[3]");

    private By priceLimiterFieldLocator = xpath("//aside[@data-widget=\"searchResultsFilters\"]//div[contains(text(),\"Цена\")]/following-sibling::div/div[2]/div[2]//input");

    private By categoryLocator = xpath("//header[contains(text(),'Категория')]");

    private By allProductsPricesLocator = xpath("//div[@data-widget=\"searchResultsV2\"]/div/div/div/following-sibling::div/following-sibling::div/div[not(@style)]/span[1]");

    private By seeAllBrandsButtonLocator = xpath("//div[contains(text(),'Бренды')]/following-sibling::div//span[contains(text(),'Посмотреть все')]");

    private List<WebElement> productsFromSearchList = new ArrayList<>();


    public void limitByPrice(String price) {
        waitUntilProductPricesListIsLoaded();
        WebElement categoryTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(categoryLocator));
        System.out.println(categoryTitle);

        for (int i = 0; i < 10; i++) {
            try {
                assertEquals("Категория", categoryTitle.getAttribute("innerText"), "Мы не перешли на страницу выбора товара");
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("поймали " + e + " продолжаем попытки");
            }
        }


        for (int i = 0; i < 10; i++) {
            try {
                WebElement priceLimiterField = wait.until(ExpectedConditions.visibilityOfElementLocated(priceLimiterFieldLocator));
                priceLimiterField.sendKeys(Keys.CONTROL, "a");
                priceLimiterField.sendKeys(Keys.DELETE);
                priceLimiterField.sendKeys(price);
                assertEquals(price, priceLimiterField.getAttribute("value"), "Фильтрация по цене не произошла!");
                priceLimiterField.sendKeys(Keys.ENTER);
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("поймано " + e + ",продолжаем попытки");
            }
        }
    }

    public void activateBrandsCheckboxes(String brandCheckboxToBeSelected) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(seeAllBrandsButtonLocator));
        refreshElementAndClick(seeAllBrandsButtonLocator);

        WebElement brandFiltrationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Бренды')]/following-sibling::div//input[@type=\"text\"]")));
        brandFiltrationInput.sendKeys(brandCheckboxToBeSelected);
        assertEquals(brandCheckboxToBeSelected, brandFiltrationInput.getAttribute("value"), "value отсутствует/не соответствует " + brandCheckboxToBeSelected + "");

        By brandCheckboxToBeSelectedLocator = xpath("//following-sibling::div/div/a[1]//span[text()='" + brandCheckboxToBeSelected + "']");
        refreshElementAndClick(brandCheckboxToBeSelectedLocator);

        wait.until(ExpectedConditions.visibilityOfElementLocated(priceLimiterFieldLocator));
    }

    public void activateCheckbox(String checkboxName) {
        By NFCCheckboxLocator = xpath("//div[contains(text(),\"Беспроводные\")]/following-sibling::div/div/span/label//span[text()=\"" + checkboxName + "\"]");
        By highRatingCheckboxLocator = xpath("//div[@value=\"Высокий рейтинг\"]//label/div[1]");
        switch (checkboxName) {
            case "Высокий рейтинг":
                for (int i = 0; i < 10; i++) {
                    try {
                        WebElement highRatingCheckbox = driverManager.getWebDriver().findElement(highRatingCheckboxLocator);
                        highRatingCheckbox.click();
                        System.out.println("Высокий рейтинг -выбран");
                        break;
                    } catch (StaleElementReferenceException e) {
                        System.out.println("поймали " + e + ", попробуем еще раз");
                    }
                }
                break;
            case "NFC":
                for (int i = 0; i < 10; i++) {
                    try {
                        WebElement NFCCheckbox = driverManager.getWebDriver().findElement(NFCCheckboxLocator);
                        NFCCheckbox.click();
                        System.out.println("NFC -выбран");

                        break;
                    } catch (StaleElementReferenceException e) {
                        System.out.println("поймали " + e + ", попробуем еще раз");
                    }
                }
                break;
            default:
                fail("Чек-бокса + '" + checkboxName + "' нет на странице!");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitUntilProductPricesListIsLoaded();
    }


    private void waitUntilProductPricesListIsLoaded() {
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                productsFromSearchList = webDriver.findElements(allProductsPricesLocator);
                System.out.println("количество товаров в корзине: " + productsFromSearchList.size() + "");
                System.out.println(productsFromSearchList.size());
                if (productsFromSearchList.size() > 30) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    private void refreshElementAndClickAddToCartButton(WebElement currentProduct) {
        By AddToCartButtonLocator = By.xpath("./../following-sibling::div//span[contains(text(),'доставит')]/../../div/div");
        int attempt = 0;
        while (attempt < 10) {
            try {
                WebElement addToCartButton = currentProduct.findElement(AddToCartButtonLocator);
                addToCartButton.click();
                return;
            } catch (StaleElementReferenceException e) {
                System.out.println("Поймали " + e + ",продолжаем попытки");
            }
            ++attempt;
        }
        fail("Не удалось обработать исключение в методе: 'refreshElementAndClickAddToCartButton' ");
    }


    public void addLimitedCountOfProductsToCartIfTheirPricesIsEven(int countLimit) {
        int finalCurrentProductPrice = 0;
        String currentProductName = null;
        WebElement currentProduct = null;
        int counter = 0;
        int i = 0;
        for (i = 0; i < productsFromSearchList.size(); i++) {
            System.out.println("зашли в цикл, " + i + " итерация");
            currentProduct = productsFromSearchList.get(i);
            finalCurrentProductPrice = getCurrentProductPrice(currentProduct);//выпадает staleElement

            if (finalCurrentProductPrice % 2 == 0 && counter < countLimit) {
                System.out.println("попали в if");
                currentProductName = getCurrentProductName(currentProduct);

                /*
                проверяем на наличие синей кнопки "В корзину",
                если такойкнопки нет, то попадаем в блок catch
                и сразу переходим к следующему товару
                */
                try {
                    System.out.println("попали в try");
                    refreshElementAndClickAddToCartButton(currentProduct);
                    System.out.println("Добавляем " + currentProductName + " с ценой " + finalCurrentProductPrice + " и нажимаем на кнопку в корзину");
                } catch (NoSuchElementException e) {
                    System.out.println("Чёрт! У продукта " + currentProductName + " с ценой " + finalCurrentProductPrice + " не было такой кнопки,а ну ладно перейдем к следующему");
                    continue;
                }


                myInnerCart.loadInnerCart(new Product(currentProductName, finalCurrentProductPrice));
                System.out.println("Загружаем внутреннюю корзину новым продуктом");

                counter++;
                System.out.println("добавили " + counter + " товаров в корзину");

                if (counter == countLimit) {
                    System.out.println("закончили добавление " + countLimit + " товаров в корзину");
                    refreshElementAndClick(toCartButtonLocator);
                    return;
                }

            } else {
                System.out.println("Цена " + finalCurrentProductPrice + " нечетная, переходим к следующему товару");
            }

        }
        System.out.println(("вышли из цикла на позиции " + i + ",не хватило товаров по указанным в if условиям, добавляем сколько есть"));
        refreshElementAndClick(toCartButtonLocator);
    }


    public void addLimitedCountOfProductsToCartIfTheirPricesIsOdd(int countLimit) {
        int finalCurrentProductPrice = 0;
        String currentProductName = null;
        int counter = 0;
        int i = 0;
        for (i = 0; i < productsFromSearchList.size(); i++) {
            //вызываем метод в цикле,чтобы productsFromSearchList всегда состоял из свежих элементов на момент обращения к нему
            waitUntilProductPricesListIsLoaded();

            System.out.println("зашли в цикл");

            finalCurrentProductPrice = getCurrentProductPrice(productsFromSearchList.get(i));//выпадает staleElement

            if (finalCurrentProductPrice % 2 != 0 && counter < countLimit) {
                System.out.println("попали в if");

                try {
                    System.out.println("попали в try");

                    currentProductName = getCurrentProductName(productsFromSearchList.get(i));
                    refreshElementAndClickAddToCartButton(productsFromSearchList.get(i));

                    System.out.println("Добавляем " + currentProductName + " с ценой " + finalCurrentProductPrice + " и нажимаем на кнопку в корзину");

                    myInnerCart.loadInnerCart(new Product(currentProductName, finalCurrentProductPrice));

                    System.out.println("Загружаем внутреннюю корзину новым продуктом");

                    counter++;
                    System.out.println("добавили " + counter + " товаров в корзину");

                    if (counter == countLimit) {
                        System.out.println("закончили добавление " + countLimit + " товаров в корзину");

                        refreshElementAndClick(toCartButtonLocator);
                        return;
                    }

                } catch (NoSuchElementException e) {
                    System.out.println("Чёрт! У продукта " + currentProductName + " с ценой " + finalCurrentProductPrice + " не было такой кнопки,а ну ладно перейдем к следующему");
                }

            } else {
                System.out.println("Цена " + finalCurrentProductPrice + " четная, переходим к следующему товару");
            }
        }
        System.out.println(("вышли из цикла на позиции " + i + ",не хватило товаров по указанным в if условиям, добавляем сколько есть"));
        refreshElementAndClick(toCartButtonLocator);
    }
}