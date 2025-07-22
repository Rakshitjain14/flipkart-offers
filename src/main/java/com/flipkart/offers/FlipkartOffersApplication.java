
package com.flipkart.offers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.offers.model.AvailableOffer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FlipkartOffersApplication {
    public static void main(String[] args) throws JsonProcessingException {

//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        //headers.set("Cookie","T=TI173503783785900169739496819267225342649421522149897292842153008476; AMCV_17EB401053DAF4840A490D4C%40AdobeOrg=-227196251%7CMCIDTS%7C20289%7CMCMID%7C44392917662778377042768828783167362398%7CMCAAMLH-1753553933%7C12%7CMCAAMB-1753553933%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1752956333s%7CNONE%7CMCAID%7C31CF8A46B0AD824A-400010882034E3B0; ULSN=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb29raWUiLCJhdWQiOiJmbGlwa2FydCIsImlzcyI6ImF1dGguZmxpcGthcnQuY29tIiwiY2xhaW1zIjp7ImdlbiI6IjEiLCJ1bmlxdWVJZCI6IlVVSTI1MDcxOTIzNTExNTYyNUwzSVMyMUYiLCJma0RldiI6bnVsbH0sImV4cCI6MTc2ODcyOTI3NSwiaWF0IjoxNzUyOTQ5Mjc1LCJqdGkiOiI0MDIxN2Y5Yi01Yjk4LTQ1YjQtOWZjNC04MTc0MTdhY2U3NTAifQ.ylsBt0rMMUzSVpHo2ESk8mQHmQuQspte4daukoQsv5A; vh=702; dpr=2; at=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjNhNzdlZTgxLTRjNWYtNGU5Ni04ZmRlLWM3YWMyYjVlOTA1NSJ9.eyJleHAiOjE3NTI5OTk3NzYsImlhdCI6MTc1Mjk5Nzk3NiwiaXNzIjoia2V2bGFyIiwianRpIjoiNjFlZmYxMWItZWZkNy00YTc1LWEwMGEtYzk3MTdjNzRjYTExIiwidHlwZSI6IkFUIiwiZElkIjoiVEkxNzM1MDM3ODM3ODU5MDAxNjk3Mzk0OTY4MTkyNjcyMjUzNDI2NDk0MjE1MjIxNDk4OTcyOTI4NDIxNTMwMDg0NzYiLCJiSWQiOiJNWk5KWVYiLCJrZXZJZCI6IlZJQjJDMzAzMDJFN0FFNDVDMDgwNzE0RTU4QTcyNDE5QzIiLCJ0SWQiOiJtYXBpIiwiZWFJZCI6IlVvYnFCQzJZcXJkN0I4bk5zbUpQeXB4bjlkS0lhekQtIiwidnMiOiJMSSIsInoiOiJIWUQiLCJtIjp0cnVlLCJnZW4iOjR9.-oudoi8gZfW76E995MAtYxm_qIrGaw0oeED8jq3Z0Uo; rt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjNhNzdlZTgxLTRjNWYtNGU5Ni04ZmRlLWM3YWMyYjVlOTA1NSJ9.eyJleHAiOjE3Njg4OTU1NzYsImlhdCI6MTc1Mjk5Nzk3NiwiaXNzIjoia2V2bGFyIiwianRpIjoiYzY0MmRjMTUtZTY1Zi00Mzc5LTlhOTMtODdhYzYxNGM0YWU5IiwidHlwZSI6IlJUIiwiZElkIjoiVEkxNzM1MDM3ODM3ODU5MDAxNjk3Mzk0OTY4MTkyNjcyMjUzNDI2NDk0MjE1MjIxNDk4OTcyOTI4NDIxNTMwMDg0NzYiLCJiSWQiOiJNWk5KWVYiLCJrZXZJZCI6IlZJQjJDMzAzMDJFN0FFNDVDMDgwNzE0RTU4QTcyNDE5QzIiLCJ0SWQiOiJtYXBpIiwibSI6eyJ0eXBlIjoibiJ9LCJ2IjoiQ0lMQjRTIn0.ErNDzaKyI4Ac_gONWTynDuQLfrTkXlJjzaDUAT2Ilvk; K-ACTION=null; vd=VIB2C30302E7AE45C080714E58A72419C2-1752949133741-2.1752998598.1752995914.151099178; vw=1223; Network-Type=4g; ud=5.jwsPHQLe4VbH0z-UzxwYNoIJvAGM12DmB-ha2p5uTXsTKv2ikanVSjxuY7yb1yqdLczl1_WfK3nv-Lpv3_z-_tV43-6zaeG60DRK1eRH-D7y5SDzuDK60X8rVioELLtfOfYn-Ckg_DWjF8znlmaxMDbyAMGiHqfcg5pp_MVayv4CNKt6YuSZSq-SC-CkEHAoM5pOEOe7nB6L1TFUBr4Rb_e4yskRTEr3VNb_KZM1IXtYr-mylaJmqm24PtZMrP51Wyfh7fUL1TYriEgIrkWh540xHiU_3yhX8sn7fIxEzMtWSehP2KLOoWPDh0SCaOb_cXdYMTAxxBf3bZC4eCUz4JLVhS9kucMqfH4Y_bemUXmbb8nXBGte_eEE16Q8JWx5DfZmiEFsfLYx2Xis8sS13Yd49AJRx2p8YxhCZZbGmWSqSzb_8emDYSaGbN3k7smwXkCrb0m7mMsNpeaqQiIVzma4KNdAlC0CG09Sxa8gFZaPd1DQnkmjm0cI9x2IjlpdpJ6e7H9_ITsvMMQtVaLgtTSzLCc1JyWqsG7fxiYbqwDY_g2KgsIsVAKMiK_eZTzPbj3KeQkzRTU2Tk-kzFD0zCNkEYv12rq9btfKhZlnEmeZWw0hqn9LHdSaTBqw2ypW8vFpagWfh24AulJ8m9zMO1qXPBavh3c3rHzsR0a5hr-Nz186SPkfls9SYVVtDYXtLK9gqmUAipnVvK6w60MCmk4lcfRB-c0TfG8tMJLle9NumpO6B-JdzYUyOyVB545NfWu4dddgNymSxFSjtr1rPw; s_sq=flipkart-prd%3D%2526pid%253Dwww.flipkart.com%25253Aboult-z60-60hr-battery-quad-mic-enc-50ms-ultra-low-latency-made-india-5-3-bluetooth%25253Ap%25253Aitmf2872be099354%2526pidt%253D1%2526oid%253DfunctionJr%252528%252529%25257B%25257D%2526oidt%253D2%2526ot%253DSUBMIT; S=d1t13Pz8/MD8/Og4/cj8/Pz8/PwD/Vpk56dVixCfVQzbEpozPY/kbXfONqeR40HHc3X2zJxYyNQczyMfaXOToJ+l3qQ==; SN=VIB2C30302E7AE45C080714E58A72419C2.TOK258498A3948D41398F856A01B70CDD53.1752998640674.LI");
//        headers.set("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36");
//        headers.set("Accept", "*/*");
//        headers.set("Connection","keep-alive");
//        headers.set("Content-Type","application/json");
//        headers.set("Origin","https://www.flipkart.com");
//        headers.set("Referer","https://www.flipkart.com/");
//        headers.set("Sec-Fetch-Dest","empty");
//        headers.set("Sec-Fetch-Mode","cors");
//        headers.set("Sec-Fetch-Site","same-site");
//        headers.set("X-User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36 FKUA/website/42/website/Desktop");
//        //headers.set(sec-ch-ua:"Not)A;Brand";v="8", "Chromium";v="138", "Google Chrome";v="138");
//      //  headers.set();
//        //headers.set();
//        String requestJson = "{\"token\":\"PN2507211448201046f069bcb7279b71357cabd13c39d240ebc51e7b017ce913c5f93fd13c47d01_v3_2\"}";
//
//
//
//        HttpEntity<String> entity = new HttpEntity<>(requestJson,headers);
//
//        try {
//            ResponseEntity<Root> response = restTemplate.exchange(
//                    "https://2.payments.flipkart.com/fkpay/api/v3/payments/options",
//                    HttpMethod.POST,
//                    entity,
//                    Root.class
//            );
//            ObjectMapper objectMapper = new ObjectMapper();
////            objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
//            //objectMapper.readValue(response.getBody().toString(), AvailableOffer.class);
//            System.out.println(response.getBody().available_offers.size());

        SpringApplication.run(FlipkartOffersApplication.class, args);
//        } catch (RestClientException e) {
//            throw new RuntimeException(e);
//        }
    }
}
//Accept:*/*
//Accept-Language:en-GB,en-US;q=0.9,en;q=0.8
//Connection:keep-alive
//Content-Type:application/json
//Origin:https://www.flipkart.com
//Referer:https://www.flipkart.com/
//Sec-Fetch-Dest:empty
//Sec-Fetch-Mode:cors
//Sec-Fetch-Site:same-site
//User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36

//X-User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36 FKUA/website/42/website/Desktop

//sec-ch-ua:"Not)A;Brand";v="8", "Chromium";v="138", "Google Chrome";v="138"
//sec-ch-ua-mobile:?0
//sec-ch-ua-platform:"macOS"
//Cookie:T=TI173503783785900169739496819267225342649421522149897292842153008476; AMCV_17EB401053DAF4840A490D4C%40AdobeOrg=-227196251%7CMCIDTS%7C20289%7CMCMID%7C44392917662778377042768828783167362398%7CMCAAMLH-1753553933%7C12%7CMCAAMB-1753553933%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1752956333s%7CNONE%7CMCAID%7C31CF8A46B0AD824A-400010882034E3B0; ULSN=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb29raWUiLCJhdWQiOiJmbGlwa2FydCIsImlzcyI6ImF1dGguZmxpcGthcnQuY29tIiwiY2xhaW1zIjp7ImdlbiI6IjEiLCJ1bmlxdWVJZCI6IlVVSTI1MDcxOTIzNTExNTYyNUwzSVMyMUYiLCJma0RldiI6bnVsbH0sImV4cCI6MTc2ODcyOTI3NSwiaWF0IjoxNzUyOTQ5Mjc1LCJqdGkiOiI0MDIxN2Y5Yi01Yjk4LTQ1YjQtOWZjNC04MTc0MTdhY2U3NTAifQ.ylsBt0rMMUzSVpHo2ESk8mQHmQuQspte4daukoQsv5A; vh=702; dpr=2; at=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjNhNzdlZTgxLTRjNWYtNGU5Ni04ZmRlLWM3YWMyYjVlOTA1NSJ9.eyJleHAiOjE3NTI5OTk3NzYsImlhdCI6MTc1Mjk5Nzk3NiwiaXNzIjoia2V2bGFyIiwianRpIjoiNjFlZmYxMWItZWZkNy00YTc1LWEwMGEtYzk3MTdjNzRjYTExIiwidHlwZSI6IkFUIiwiZElkIjoiVEkxNzM1MDM3ODM3ODU5MDAxNjk3Mzk0OTY4MTkyNjcyMjUzNDI2NDk0MjE1MjIxNDk4OTcyOTI4NDIxNTMwMDg0NzYiLCJiSWQiOiJNWk5KWVYiLCJrZXZJZCI6IlZJQjJDMzAzMDJFN0FFNDVDMDgwNzE0RTU4QTcyNDE5QzIiLCJ0SWQiOiJtYXBpIiwiZWFJZCI6IlVvYnFCQzJZcXJkN0I4bk5zbUpQeXB4bjlkS0lhekQtIiwidnMiOiJMSSIsInoiOiJIWUQiLCJtIjp0cnVlLCJnZW4iOjR9.-oudoi8gZfW76E995MAtYxm_qIrGaw0oeED8jq3Z0Uo; rt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjNhNzdlZTgxLTRjNWYtNGU5Ni04ZmRlLWM3YWMyYjVlOTA1NSJ9.eyJleHAiOjE3Njg4OTU1NzYsImlhdCI6MTc1Mjk5Nzk3NiwiaXNzIjoia2V2bGFyIiwianRpIjoiYzY0MmRjMTUtZTY1Zi00Mzc5LTlhOTMtODdhYzYxNGM0YWU5IiwidHlwZSI6IlJUIiwiZElkIjoiVEkxNzM1MDM3ODM3ODU5MDAxNjk3Mzk0OTY4MTkyNjcyMjUzNDI2NDk0MjE1MjIxNDk4OTcyOTI4NDIxNTMwMDg0NzYiLCJiSWQiOiJNWk5KWVYiLCJrZXZJZCI6IlZJQjJDMzAzMDJFN0FFNDVDMDgwNzE0RTU4QTcyNDE5QzIiLCJ0SWQiOiJtYXBpIiwibSI6eyJ0eXBlIjoibiJ9LCJ2IjoiQ0lMQjRTIn0.ErNDzaKyI4Ac_gONWTynDuQLfrTkXlJjzaDUAT2Ilvk; K-ACTION=null; vd=VIB2C30302E7AE45C080714E58A72419C2-1752949133741-2.1752998598.1752995914.151099178; vw=1223; Network-Type=4g; ud=5.jwsPHQLe4VbH0z-UzxwYNoIJvAGM12DmB-ha2p5uTXsTKv2ikanVSjxuY7yb1yqdLczl1_WfK3nv-Lpv3_z-_tV43-6zaeG60DRK1eRH-D7y5SDzuDK60X8rVioELLtfOfYn-Ckg_DWjF8znlmaxMDbyAMGiHqfcg5pp_MVayv4CNKt6YuSZSq-SC-CkEHAoM5pOEOe7nB6L1TFUBr4Rb_e4yskRTEr3VNb_KZM1IXtYr-mylaJmqm24PtZMrP51Wyfh7fUL1TYriEgIrkWh540xHiU_3yhX8sn7fIxEzMtWSehP2KLOoWPDh0SCaOb_cXdYMTAxxBf3bZC4eCUz4JLVhS9kucMqfH4Y_bemUXmbb8nXBGte_eEE16Q8JWx5DfZmiEFsfLYx2Xis8sS13Yd49AJRx2p8YxhCZZbGmWSqSzb_8emDYSaGbN3k7smwXkCrb0m7mMsNpeaqQiIVzma4KNdAlC0CG09Sxa8gFZaPd1DQnkmjm0cI9x2IjlpdpJ6e7H9_ITsvMMQtVaLgtTSzLCc1JyWqsG7fxiYbqwDY_g2KgsIsVAKMiK_eZTzPbj3KeQkzRTU2Tk-kzFD0zCNkEYv12rq9btfKhZlnEmeZWw0hqn9LHdSaTBqw2ypW8vFpagWfh24AulJ8m9zMO1qXPBavh3c3rHzsR0a5hr-Nz186SPkfls9SYVVtDYXtLK9gqmUAipnVvK6w60MCmk4lcfRB-c0TfG8tMJLle9NumpO6B-JdzYUyOyVB545NfWu4dddgNymSxFSjtr1rPw; s_sq=flipkart-prd%3D%2526pid%253Dwww.flipkart.com%25253Aboult-z60-60hr-battery-quad-mic-enc-50ms-ultra-low-latency-made-india-5-3-bluetooth%25253Ap%25253Aitmf2872be099354%2526pidt%253D1%2526oid%253DfunctionJr%252528%252529%25257B%25257D%2526oidt%253D2%2526ot%253DSUBMIT; S=d1t13Pz8/MD8/Og4/cj8/Pz8/PwD/Vpk56dVixCfVQzbEpozPY/kbXfONqeR40HHc3X2zJxYyNQczyMfaXOToJ+l3qQ==; SN=VIB2C30302E7AE45C080714E58A72419C2.TOK258498A3948D41398F856A01B70CDD53.1752998640674.LI