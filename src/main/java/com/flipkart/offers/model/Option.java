package com.flipkart.offers.model;

import java.util.ArrayList;

public class Option{
    public boolean applicable;
    public boolean selected;
    public String payment_instrument;
    public String display_text;
    public ArrayList<Message> messages;
    public String status_code;
    public String status_message;
    public String section;
    public int priority;
    public Information information;
    public int wallet_balance;
    public String provider;
    public Callout callout;
    public int redeemed;
    public ArrayList<Object> egv_payments;
}
