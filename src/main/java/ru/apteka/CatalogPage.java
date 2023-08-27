package ru.apteka;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;

public class CatalogPage {
    public SelenideElement Title = $("1");
    public SelenideElement pageTitle = $("h1");
    public ElementsCollection itemsBlockList = $$x("//div[@class=\"catalog_block items block_list\"]");
    public SelenideElement header = $("h1");
   public ElementsCollection itemsBreadcrumb = $$x("//span[@itemprop=\"name\"]");
    public ElementsCollection GetSubCurrentCat(SelenideElement subCat) {
        return subCat.$$("ul li");
    }
}