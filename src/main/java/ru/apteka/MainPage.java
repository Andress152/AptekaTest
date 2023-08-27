package ru.apteka;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {
    ElementsCollection itemsBlock =
            $$x("//div[@class=\"catalog_block items row margin0 ajax_load block\"]//div[@class=\"" +
                    "col-m-20 col-lg-3 col-md-4 col-sm-6 item item_block\"]");
    public SelenideElement Likeicons = $("#bx_3966226736_114168_HIT .like_icons");
    public  SelenideElement PriceProduct = $("#bx_3966226736_114168_HIT .price_value");
    public   SelenideElement buttonDelayed = $("[class='wrap_icon inner-table-block baskets big-padding'] " +
            "a[href='/basket/#delayed']");

    public ElementsCollection tabs = $$("nav[class='mega-menu sliced ovisible'] tr td");

    public void hoverCatalog(String nameCatalog) {
        SelenideElement dropdownToggle = $x("//div[contains(text(),\"" + nameCatalog + "\")]");
        dropdownToggle.shouldBe(Condition.visible).hover();
    }

    public void inputSearchText(String searchText) {
        SelenideElement searchField = $("//[id=\"title-search-input_fixed\"]");
        searchField.sendKeys(searchText);
        SelenideElement magnifier = $x("//i[@class=\"svg svg-search svg-black\"]");
        magnifier.click();
    }
    public ElementsCollection getSubTabs(SelenideElement tab) {

        return tab.$$("ul li");
    }
    public void clickSubCatalog(String nameCatalog, String nameSubCatalog) {
        SelenideElement elementSubCatalog =
                $x("//div[contains(text(),\"" + nameCatalog + "\")]/ancestor::div[@class=\"wrap\"]" +
                        "//li//a[@title=\"" + nameSubCatalog + "\"]")
                        .shouldBe(Condition.visible).shouldNotBe(Condition.disabled);
        elementSubCatalog.click();
    }
}
