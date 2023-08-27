package ru.apteka;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;
import static io.qameta.allure.Allure.step;

public class Aptekaeconom extends WebTest{
    MainPage mainPage = new MainPage();
    CityPopup cityPopup = new CityPopup();
    CatalogPage catalogPage = new CatalogPage();
    SearchPage searchPage = new SearchPage();
    BasketPage basketPage = new BasketPage();

    @BeforeEach
    public void setSelenide() {
        open("https://aptekaeconom.com");
        Selenide.webdriver().
                driver().
                getWebDriver().
                manage()
                .addCookie(new Cookie("current_region", "103006"));
        refresh();
        cityPopup.el.shouldNotBe(visible);
    }

    @Test
    @DisplayName("Поиск товаров")
    public void ShouldProduct() {

        step("Ввести \"аскорбинка\" в поле поиска",
                () -> mainPage.inputSearchText("аскорбинка"));
    }

    @Test
    @DisplayName("Переход по подкатегориям в каталоге товаров")
    public void ShouldOpenCatalog() {
        SelenideElement tab = mainPage.tabs.filter(text("Гигиена")).get(0);

        step("Навести курсор на вкладку", () -> {
            tab.hover();
        });

        step("Кликнуть на появившуюся подкатегорию", () -> {
            ElementsCollection subtabs = mainPage.getSubTabs(tab);
            subtabs.filter(text("Предметы женской гигиены")).get(0).click();
        });

        step("Проверить, что произошел переход на страницу товаров категории", () -> {
            catalogPage.header.shouldHave(text("Предметы женской гигиены"));
        });
    }
    @Test
    @DisplayName("Проверка добавления отложенного товара к заказу")
    public void ShouldDelayed() {
        step("Нажать кнопку отложить на иконке товара",
                () -> mainPage.Likeicons.click());
        String price = mainPage.PriceProduct.getText();

        step("Нажать на \'отложенные товары\' ",
                () -> mainPage.buttonDelayed.click());

        step("Нажать на \'Добавить к заказу\' ",
                () -> { basketPage.buttonAdd.shouldBe(visible);
                    basketPage.buttonAdd.click();
                });

        step("Проверить. что товар добавился", () -> {
            basketPage.filterProduct.shouldHave(text("В корзине 1 товар"));
            basketPage.filterDelayed.shouldNotBe(visible);
        });

        step("Проверить, что стоимость изменилась на цену отложенного товара", () -> {
            basketPage.currentPrice.shouldBe(text(price));
        });
    }
    @Test
    @DisplayName("Переход по подкатегориям в каталоге товаров")
    public void shouldOpenCatalogTab() {

        step("Навести курсор на каталог \"Гигиена\"", () -> mainPage.hoverCatalog("Гигиена"));

        step("Кликнуть на подкатегорию \"Детская косметика, гигиена, уход\" в каталоге \"Гигиена\"", () ->
                mainPage.clickSubCatalog("Гигиена", "Детская косметика, гигиена, уход"));

        step("Проверить, что произошел переход на страницу товаров подкатегории \"Детская косметика, гигиена, " +
                "уход\" в каталоге \"Гигиена\" (в влевой верхней части страницы текущая подкатегория отображается " +
                "корректно)", () -> {
            catalogPage.pageTitle.shouldHave(text("Детская косметика, гигиена, уход"));
        });

        step("Проверить, что есть хотя бы 1 товар в списке товаров категории", () -> {
            catalogPage.itemsBlockList.shouldBe(CollectionCondition.size(1));
        });

        step("Проверить, что хлебные крошки отображаются корректно", () -> {
            ElementsCollection itemsBreadcrumb = catalogPage.itemsBreadcrumb;

            itemsBreadcrumb.shouldBe(CollectionCondition.size(4));

            itemsBreadcrumb.get(0).shouldHave(text("Главная"));
            itemsBreadcrumb.get(1).shouldHave(text("Каталог"));
            itemsBreadcrumb.get(2).shouldHave(text("Гигиена"));
            itemsBreadcrumb.get(3).shouldHave(text("Детская косметика, гигиена, уход"));
        });

    }
}
