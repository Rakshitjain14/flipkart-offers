package com.flipkart.offers.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferStringKey implements Serializable {

    @Column(name = "offer_id")
    private String offerId;

    private String value;
}