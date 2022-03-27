package ru.ibs.ozon_framework.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import ru.ibs.ozon_framework.entity.MyInnerCart;
import ru.ibs.ozon_framework.managers.FrameworkManager;

public class Hooks {

    @Before


    public void before() {
        FrameworkManager.getInstance().startFramework();
    }

    @After
    public void after() {
        FrameworkManager.getInstance().finishFramework();
        MyInnerCart.getInstance().getCartInnerlist().clear();
    }
}
