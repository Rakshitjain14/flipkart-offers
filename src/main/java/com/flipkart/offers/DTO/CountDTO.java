package com.flipkart.offers.DTO;

public class CountDTO {

    private Long noOfOffersIdentified;
    private Long noOfNewOffersCreated;

    public Long getNoOfOffersIdentified() {
        return noOfOffersIdentified;
    }

    public void setNoOfOffersIdentified(Long noOfOffersIdentified) {
        this.noOfOffersIdentified = noOfOffersIdentified;
    }

    public Long getNoOfNewOffersCreated() {
        return noOfNewOffersCreated;
    }

    public void setNoOfNewOffersCreated(Long noOfNewOffersCreated) {
        this.noOfNewOffersCreated = noOfNewOffersCreated;
    }

    public CountDTO(Long noOfOffersIdentified, Long noOfNewOffersCreated) {
        this.noOfOffersIdentified = noOfOffersIdentified;
        this.noOfNewOffersCreated = noOfNewOffersCreated;
    }
}
