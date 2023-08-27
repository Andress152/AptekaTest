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
    @DisplayName("����� �������")
    public void ShouldProduct() {

        step("������ \"����������\" � ���� ������",
                () -> mainPage.inputSearchText("����������"));
    }

    @Test
    @DisplayName("������� �� ������������� � �������� �������")
    public void ShouldOpenCatalog() {
        SelenideElement tab = mainPage.tabs.filter(text("�������")).get(0);

        step("������� ������ �� �������", () -> {
            tab.hover();
        });

        step("�������� �� ����������� ������������", () -> {
            ElementsCollection subtabs = mainPage.getSubTabs(tab);
            subtabs.filter(text("�������� ������� �������")).get(0).click();
        });

        step("���������, ��� ��������� ������� �� �������� ������� ���������", () -> {
            catalogPage.header.shouldHave(text("�������� ������� �������"));
        });
    }
    @Test
    @DisplayName("�������� ���������� ����������� ������ � ������")
    public void ShouldDelayed() {
        step("������ ������ �������� �� ������ ������",
                () -> mainPage.Likeicons.click());
        String price = mainPage.PriceProduct.getText();

        step("������ �� \'���������� ������\' ",
                () -> mainPage.buttonDelayed.click());

        step("������ �� \'�������� � ������\' ",
                () -> { basketPage.buttonAdd.shouldBe(visible);
                    basketPage.buttonAdd.click();
                });

        step("���������. ��� ����� ���������", () -> {
            basketPage.filterProduct.shouldHave(text("� ������� 1 �����"));
            basketPage.filterDelayed.shouldNotBe(visible);
        });

        step("���������, ��� ��������� ���������� �� ���� ����������� ������", () -> {
            basketPage.currentPrice.shouldBe(text(price));
        });
    }
    @Test
    @DisplayName("������� �� ������������� � �������� �������")
    public void shouldOpenCatalogTab() {

        step("������� ������ �� ������� \"�������\"", () -> mainPage.hoverCatalog("�������"));

        step("�������� �� ������������ \"������� ���������, �������, ����\" � �������� \"�������\"", () ->
                mainPage.clickSubCatalog("�������", "������� ���������, �������, ����"));

        step("���������, ��� ��������� ������� �� �������� ������� ������������ \"������� ���������, �������, " +
                "����\" � �������� \"�������\" (� ������ ������� ����� �������� ������� ������������ ������������ " +
                "���������)", () -> {
            catalogPage.pageTitle.shouldHave(text("������� ���������, �������, ����"));
        });

        step("���������, ��� ���� ���� �� 1 ����� � ������ ������� ���������", () -> {
            catalogPage.itemsBlockList.shouldBe(CollectionCondition.size(1));
        });

        step("���������, ��� ������� ������ ������������ ���������", () -> {
            ElementsCollection itemsBreadcrumb = catalogPage.itemsBreadcrumb;

            itemsBreadcrumb.shouldBe(CollectionCondition.size(4));

            itemsBreadcrumb.get(0).shouldHave(text("�������"));
            itemsBreadcrumb.get(1).shouldHave(text("�������"));
            itemsBreadcrumb.get(2).shouldHave(text("�������"));
            itemsBreadcrumb.get(3).shouldHave(text("������� ���������, �������, ����"));
        });

    }
}
