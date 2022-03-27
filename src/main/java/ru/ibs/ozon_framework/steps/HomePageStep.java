package ru.ibs.ozon_framework.steps;

import io.cucumber.java.ru.Затем;
import ru.ibs.ozon_framework.managers.PageManager;
import ru.ibs.ozon_framework.pages.HomePage;

public class HomePageStep {

    private PageManager pageManager = PageManager.getInstance();

    @Затем("^выполняем поиск по \"(.+)\"$")
    public void searchByKeyWord(String keyWord) {
        pageManager.getPage(HomePage.class).searchByKeyWord(keyWord);
    }
}
