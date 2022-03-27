package ru.ibs.ozon_framework.managers;

import ru.ibs.ozon_framework.pages.BasePage;

import java.util.HashMap;

public class PageManager {

    private static PageManager pageManager = null;
    private HashMap<String, BasePage> pagesBox = new HashMap<>();

    private PageManager() {

    }

    public static PageManager getInstance() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    public <T extends BasePage> T getPage(Class<T> page) {
        if (pagesBox.isEmpty() || pagesBox.get(page.getName()) == null) {
            try {
                pagesBox.put(page.getName(), page.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.getMessage();
            }
        }
        return (T) pagesBox.get(page.getName());
    }

    public void clearPagesBox() {
        pagesBox.clear();
    }
}
