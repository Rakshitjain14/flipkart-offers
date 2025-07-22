package com.flipkart.offers.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SectionDetails{
    @JsonProperty("PREFERRED")
    public PREFERRED pREFERRED;
    @JsonProperty("OTHERS") 
    public OTHERS oTHERS;
    @JsonProperty("NEW_VOUCHERS") 
    public NEWVOUCHERS nEW_VOUCHERS;
    @JsonProperty("LINKED_VOUCHERS") 
    public LINKEDVOUCHERS lINKED_VOUCHERS;
}
