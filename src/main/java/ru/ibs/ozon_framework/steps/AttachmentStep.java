package ru.ibs.ozon_framework.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.cucumber.java.ru.Затем;
import io.qameta.allure.Allure;
import ru.ibs.ozon_framework.entity.MyInnerCart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AttachmentStep {

    @Затем("^прикрепляем файл с информацией о всех добавленных товарах$")
    public void attachFileWithInformationAboutAddedProducts() {
        MyInnerCart myInnerCart = MyInnerCart.getInstance();
        int maxPrice = 0;
        int indexOfMaxPriceProduct = 0;
        for (int i = 0; i < myInnerCart.getCartInnerlist().size(); i++) {
            if (maxPrice < myInnerCart.getCartInnerlist().get(i).getPrice()) {
                maxPrice = myInnerCart.getCartInnerlist().get(i).getPrice();
                indexOfMaxPriceProduct = i;
            }
        }
        myInnerCart.getCartInnerlist().get(indexOfMaxPriceProduct).setBiggestPrice(true);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            objectMapper.writeValue(new File("src/main/resources/cartInfo.json"), myInnerCart);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Allure.addAttachment("cartInfo", new FileInputStream("src/main/resources/cartInfo.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
