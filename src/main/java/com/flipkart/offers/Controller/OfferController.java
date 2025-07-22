
package com.flipkart.offers.Controller;

import com.flipkart.offers.DTO.CountDTO;
import com.flipkart.offers.Service.OfferService;
import com.flipkart.offers.model.PayLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class OfferController {

   @Autowired
   private  final OfferService service;
    private static final Logger logger = LoggerFactory.getLogger(OfferController.class);

    public OfferController(OfferService service) {

        this.service = service;

    }

    @PostMapping("offer")
        public ResponseEntity<CountDTO> getOffer(@RequestBody PayLoad payLoad) {
        long initialCount = service.count();

        try {
            service.saveContent(payLoad);
        } catch (Exception e) {
            // Optionally log error or return a bad request
            logger.error("ERROR IS: " +e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CountDTO(initialCount, 0L));
        }

        long finalCount = service.count();
        long added = finalCount - initialCount;

        CountDTO countDTO = new CountDTO(finalCount, added);
        return ResponseEntity.ok(countDTO);
        }


    @GetMapping("/highest-discount")
    public ResponseEntity<Map<String, Object>> getHighestDiscount(
            @RequestParam double amountToPay,
            @RequestParam String bankName) {

        try {
            double highestDiscount = service.calculateBestOffer(amountToPay, bankName);
            Map<String, Object> response = new HashMap<>();
            response.put("highestDiscountAmount", (int) highestDiscount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // You can replace this with proper logging
            Map<String, Object> errorResponse = new HashMap<>();
            logger.error("ERROR IS: " +e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



    @GetMapping("/v2/highest-discount")
    public ResponseEntity<Map<String, Object>> getHighestDiscount(
            @RequestParam double amountToPay,
            @RequestParam String bankName,
            @RequestParam String paymentInstrument) {

        double highestDiscount = service.calculateBestOfferV2(amountToPay, bankName, paymentInstrument);
        Map<String, Object> response = new HashMap<>();
        response.put("highestDiscountAmount", (int) highestDiscount);
        return ResponseEntity.ok(response);
    }


}
