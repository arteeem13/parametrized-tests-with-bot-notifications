package com.andreev.valueSourceAndCsvSource;

import com.andreev.configurationsForTests.TestBase;
import com.andreev.configurationsForTests.WebAttachments;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

@Nested
@DisplayName("Параметризованные тесты ValueSource и CsvSource для сайта Ozon")
public class WebOzonTests extends TestBase {
    private static final String URL_OZON = "https://www.ozon.ru/";

    @ValueSource(strings = {
            "Одежда и обувь",
            "Электроника",
            "Дом и сад",
    })
    @ParameterizedTest(name = "Есть заголовок раздела {0} в меню категорий на странице " + URL_OZON)
    void checkCategoriesInMenuWithValueSource(String searchQuery) {
        step("Открываем главную страницу" + URL_OZON, () -> {
            open(URL_OZON);
        });
        step("Есть категория " + searchQuery + " в меню");
        $("ul[data-widget='horizontalMenu']").shouldHave(Condition.text(searchQuery));
    }

    @CsvSource({
            "KZT, ₸",
            "RUB, ₽",
            "USD, $",
    })
    @ParameterizedTest(name = "Отображается {1} для валюты {0} в карточке товара")
    @Tag("properties")
    void checkCurrencyIconInProductCardWithCsvSource(String currency, String icon) {
        step("Открываем главную страницу" + URL_OZON, () -> {
            open(URL_OZON);
        });
        step("Кликаем по кнопке выбора валюты", () ->{
            $("div[data-widget='selectedCurrency']").click();
        });
        step("Выбираем валюту " + currency, () ->{
            $("div[role='listbox']").click();
            $("input[name='filter']").setValue(currency).pressEnter();
        });
        step("Есть " + icon + " в описании товаров", () -> {
            $("div[data-widget='skuLine']").shouldHave(Condition.text(icon));
            WebAttachments.takeScreenshot();
        });
    }
}
