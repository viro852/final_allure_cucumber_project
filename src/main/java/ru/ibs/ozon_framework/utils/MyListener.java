package ru.ibs.ozon_framework.utils;

import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestStepFinished;
import io.qameta.allure.Allure;
import io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.ibs.ozon_framework.managers.WebDriverManager;

import java.io.ByteArrayInputStream;

public class MyListener extends AllureCucumber5Jvm {

    @Override
    public void setEventPublisher(final EventPublisher publisher) {

        EventHandler<TestStepFinished> eventHandler = new EventHandler<TestStepFinished>() {
            @Override
            public void receive(TestStepFinished event) {
                if (event.getResult().getStatus().is(Status.FAILED)) {
                    Allure.addAttachment("ScreenShotOnFail", new ByteArrayInputStream(((TakesScreenshot) WebDriverManager.getInstance().getWebDriver()).getScreenshotAs(OutputType.BYTES)));
                }
            }
        };
        publisher.registerHandlerFor(TestStepFinished.class, eventHandler);
        super.setEventPublisher(publisher);
    }

}
