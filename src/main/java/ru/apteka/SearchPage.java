package ru.apteka;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$x;

public class SearchPage {
    public ElementsCollection itemsSearch =
            $$x("//div[@class=\"catalog_block items block_list\"]");
}
