#language: ru

@products
Функция:Работа с товарами

  @scenario1
  Структура сценария: Работа с товаром "<товар>"
    Допустим мы перешли на сервис: "https://www.ozon.ru/"
    Затем выполняем поиск по "<товар>"
    И ограничиваем цену товаров до "150000"
    И отмечаем чекбокс – "Высокий рейтинг"
    И активируем чекбокс – "NFC"
    Затем из результатов поиска добавляем в корзину первые 6 товаров с четными ценами
    Затем переходим в 'корзину', убеждаемся,что все ранее добавленные товары находятся в корзине
    Когда удаляем все товары из корзины
    Затем проверяем, что корзина не содержит никаких товаров
    И прикрепляем файл с информацией о всех добавленных товарах

    Примеры:
      | товар  |
      | iphone |


  @scenario2
  Структура сценария: Работа с товаром "<товар>"
    Допустим мы перешли на сервис: "https://www.ozon.ru/"
    Затем выполняем поиск по "<товар>"
    И отмечаем чекбоксы с названиями брендов:
      | Samsung |
      | Xiaomi  |
      | Beats   |
    И ограничиваем цену товаров до "20000"
    И отмечаем чекбокс – "Высокий рейтинг"
    Затем из результатов поиска добавляем в корзину первые 8 товаров с нечетными ценами
    Затем переходим в 'корзину', убеждаемся,что все ранее добавленные товары находятся в корзине
    Затем удаляем все товары из корзины
    И проверяем, что корзина не содержит никаких товаров
    Затем прикрепляем файл с информацией о всех добавленных товарах

    Примеры:
      | товар                 |
      | Беспроводные наушники |

