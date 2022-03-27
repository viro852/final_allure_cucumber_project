package ru.ibs.ozon_framework;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "ru.ibs.ozon_framework.utils.MyListener",
        features = "src/test/resources",
        glue = "ru.ibs.ozon_framework.steps",
        tags = "@products"
)
public class CucumberRunnerTest {
}
