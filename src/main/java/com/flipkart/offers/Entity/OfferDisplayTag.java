package com.flipkart.offers.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "offer_display_tags",schema = "Flipkart_offer_schema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferDisplayTag {

    @EmbeddedId
    private OfferStringKey id;

    @ManyToOne
    @MapsId("offerId")
    @JoinColumn(name = "offer_id")
    private Offer offer;
}