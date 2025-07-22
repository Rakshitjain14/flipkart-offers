package com.flipkart.offers.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


    @Entity

    @Table(name = "offers", schema = "Flipkart_offer_schema")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Offer {

        @Id
        @Column(name = "offer_id")
        private String offerId; // adjustment_id

        private String title;

        @Column(columnDefinition = "TEXT")
        private String summary;

        private String adjustmentType;

        private String adjustmentSubType;

        private Boolean isDiscover;

        @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<OfferPaymentInstrument> paymentInstruments;

        @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<OfferBank> banks;

        @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<OfferEmiMonth> emiMonths;

        @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<OfferCardNetwork> cardNetworks;

        @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<OfferDisplayTag> displayTags;
    }

