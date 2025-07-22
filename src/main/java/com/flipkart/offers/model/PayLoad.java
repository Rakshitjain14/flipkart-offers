package com.flipkart.offers.model;

import java.util.ArrayList;

public class PayLoad{
    public String response_status;
    public ArrayList<Object> messages;
    public ArrayList<NotifyMessage> notify_messages;
    public String response_type;
    public String token_version;
    public boolean disable_pay_button;
    public int disable_pay_timeout;
    public ArrayList<Option> options;
    public PriceSummary price_summary;
    public SlaSummary sla_summary;
    public ArrayList<ReservationDetail> reservation_details;
    public ReservationExpiryAction reservation_expiry_action;
    public BackAction back_action;
    public ArrayList<AvailableOffer> available_offers;
    public String merchant_id;
    public SectionDetails section_details;
}
