package com.flipkart.offers.model;

import java.util.ArrayList;

public class PriceSummary{
    public int remaining;
    public int base_price;
    public int item_count;
    public ArrayList<Object> convertible_amount;
    public ArrayList<PriceDetail> price_details;
    public ArrayList<Breakup> breakup;
    public ArrayList<YouPay> you_pay;
    public ArrayList<Object> notify_messages;
}
