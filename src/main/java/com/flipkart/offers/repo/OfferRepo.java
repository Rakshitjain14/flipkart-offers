package com.flipkart.offers.repo;

import com.flipkart.offers.Entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepo extends JpaRepository<Offer, String> {

    @Query("SELECT o FROM Offer o JOIN o.banks b WHERE b.id.value = :bankName")
    List<Offer> findOffersByBank(@Param("bankName") String bankName);

    @Query("""
           SELECT o FROM Offer o
           JOIN o.banks b
           JOIN o.paymentInstruments pi
           WHERE b.id.value = :bankName
             AND pi.id.value = :paymentInstrument
           """)
    List<Offer> findOffersByBankAndInstrument(@Param("bankName") String bankName,
                                              @Param("paymentInstrument") String paymentInstrument);
}
