package ru.apteka;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class BasketPage {
    public SelenideElement filterProduct = $(".basket-items-list-header-filter");
    public SelenideElement filterDelayed = $("[class='basket-items-list-header-filter-item active']");

    public SelenideElement buttonAdd = $("[class='alert alert-warning text-center']" +
            " [data-entity='basket-item-remove-delayed']");
    public SelenideElement currentPrice = $(".basket-coupon-block-total-price-current");

}