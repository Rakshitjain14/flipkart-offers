package com.flipkart.offers.Service;

import com.flipkart.offers.Entity.*;
import com.flipkart.offers.model.AvailableOffer;
import com.flipkart.offers.model.PayLoad;
import com.flipkart.offers.repo.OfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class OfferService {

    @Autowired
    private OfferRepo offerRepo;

    public void storeOffer(PayLoad payLoad) {

    }

    public long count() {
        return offerRepo.count();
    }

    public List<Offer> saveContent(PayLoad payLoad) {

        List<Offer> offers = convertToEntity(payLoad);
        return offerRepo.saveAll(offers);

    }
    public double calculateBestOffer(double amountToPay, String bankName) {
        List<Offer> offers = offerRepo.findOffersByBank(bankName.toUpperCase());
        System.out.println("size: " + offers.size());
        double maxDiscount = 0;
        for (Offer offer : offers) {
            double discount = extractDiscount(offer.getSummary(), amountToPay);
            System.out.println("discount: " + discount);
            maxDiscount = Math.max(maxDiscount, discount);
        }
        return maxDiscount;
    }

    private double extractDiscount(String summary, double amountToPay) {
        if (summary == null) return 0;

        try {
            Pattern pattern = Pattern.compile("(\\d+)%.*?up\\s*to\\s*₹?([\\d,]+)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(summary);

            if (matcher.find()) {
                int percent = Integer.parseInt(matcher.group(1));
                String capStr = matcher.group(2).replaceAll(",", "");
                int cap = Integer.parseInt(capStr);
                return Math.min((percent / 100.0) * amountToPay, cap);
            }
        } catch (Exception e) {
            // Log or handle error
        }

        return 0;
    }

    public double calculateBestOfferV2(double amountToPay, String bankName, String paymentInstrument) {
        // Normalize input
        String bank = bankName.toUpperCase();
        String instrument = paymentInstrument.toUpperCase();

        List<Offer> offers = offerRepo.findOffersByBankAndInstrument(bank, instrument);

        double maxDiscount = 0;
        for (Offer offer : offers) {
            double discount = extractDiscountV2(offer.getSummary(), amountToPay);
            maxDiscount = Math.max(maxDiscount, discount);
        }

        return maxDiscount;
    }

    private double extractDiscountV2(String summary, double amountToPay) {
        if (summary == null) return 0;

        try {
            Pattern pattern = Pattern.compile("(\\d+)%.*?up\\s*to\\s*₹?([\\d,]+)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(summary);

            if (matcher.find()) {
                int percent = Integer.parseInt(matcher.group(1));
                int cap = Integer.parseInt(matcher.group(2).replaceAll(",", ""));
                return Math.min((percent / 100.0) * amountToPay, cap);
            }
        } catch (Exception e) {
            // Optional: log the error
        }

        return 0;
    }

    private List<Offer> convertToEntity(PayLoad payLoad) {
        List<Offer> offers = new ArrayList<>();
        List<AvailableOffer> payLoad_offers = payLoad.available_offers;
        for (AvailableOffer payload_offer : payLoad_offers) {
            Offer newOffer = new Offer();
            newOffer.setOfferId(payload_offer.getAdjustment_id());
            newOffer.setTitle(payload_offer.getTitle());
            newOffer.setSummary(payload_offer.getSummary());
            newOffer.setAdjustmentType(payload_offer.getAdjustment_type());
            newOffer.setAdjustmentSubType(payload_offer.getAdjustment_sub_type());
            newOffer.setIsDiscover(payload_offer.isIs_discover());

            List<OfferBank> offerBanks = new ArrayList<>();
            for (String bank : payload_offer.getContributors().banks) {
                OfferBank offerBank = new OfferBank();
                OfferStringKey id = new OfferStringKey();
                id.setOfferId(payload_offer.getAdjustment_id());
                id.setValue(bank);
                offerBank.setId(id);
                offerBank.setOffer(newOffer);
                offerBanks.add(offerBank);
            }
            newOffer.setBanks(offerBanks);

            List<OfferPaymentInstrument> OPIs = new ArrayList<>();
            for (String opi : payload_offer.getContributors().payment_instrument) {
                OfferPaymentInstrument offerpaymentinstru = new OfferPaymentInstrument();
                OfferStringKey id = new OfferStringKey();
                id.setOfferId(payload_offer.getAdjustment_id());
                id.setValue(opi);
                offerpaymentinstru.setId(id);
                offerpaymentinstru.setOffer(newOffer);
                OPIs.add(offerpaymentinstru);
            }
            newOffer.setPaymentInstruments(OPIs);

            List<OfferEmiMonth> OEMs = new ArrayList<>();
            for (String oem : payload_offer.getContributors().emi_months) {
                OfferEmiMonth offeremi = new OfferEmiMonth();
                OfferStringKey id = new OfferStringKey();
                id.setOfferId(payload_offer.getAdjustment_id());
                id.setValue(oem);
                offeremi.setId(id);
                offeremi.setOffer(newOffer);
                OEMs.add(offeremi);
            }
            newOffer.setEmiMonths(OEMs);

            List<OfferCardNetwork> OCNs = new ArrayList<>();
            for (String ocn : payload_offer.getContributors().card_networks) {
                OfferCardNetwork offercardnet = new OfferCardNetwork();
                OfferStringKey id = new OfferStringKey();
                id.setOfferId(payload_offer.getAdjustment_id());
                id.setValue(ocn);
                offercardnet.setId(id);
                offercardnet.setOffer(newOffer);
                OCNs.add(offercardnet);
            }
            newOffer.setCardNetworks(OCNs);


            List<OfferDisplayTag> ODTs = new ArrayList<>();
            for (String odt : payload_offer.display_tags) {
                OfferDisplayTag offerdistag = new OfferDisplayTag();
                OfferStringKey id = new OfferStringKey();
                id.setOfferId(payload_offer.getAdjustment_id());
                id.setValue(odt);
                offerdistag.setId(id);
                offerdistag.setOffer(newOffer);
                ODTs.add(offerdistag);
            }
            newOffer.setDisplayTags(ODTs);


            offers.add(newOffer);


        }
        return offers;
    }



}
