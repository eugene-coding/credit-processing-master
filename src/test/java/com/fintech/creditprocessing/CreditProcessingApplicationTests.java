package com.fintech.creditprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.creditprocessing.constant.Status;
import com.fintech.creditprocessing.services.CreditService;
import com.fintech.creditprocessing.services.TariffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreditProcessingApplicationTests {
    ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    public void areThereThreeTariffs() throws IOException {
       var tariffs = TariffService.getAll();

       assertEquals(3, tariffs.length);
    }

    @ParameterizedTest(name = "{index} - {0} is interest rate of the first tariff")
    @ValueSource(strings = {"11%", "7%", "8%"})
    public void IsFirstTariffInterestRateEqualsValue(String interestRate){
        var tariff = TariffService.getFirst();

        assertEquals(interestRate, tariff.getInterestRate());
    }

    @ParameterizedTest(name = "{index} - {0} is added")
    @ValueSource(longs = {120356894755L, 153356894755L, 120356124755L})
    public void isNewRequestAdded(long userId){
        try {
            CreditService.apply(userId, TariffService.getFirst());
        } catch (Exception e){
            fail(e.getMessage());
        }
    }

    @ParameterizedTest(name = "{index} - {0} is orderId")
    @ValueSource(strings = {"dedffbb7-8336-49ab-98c8-7ee1f016bd67", "d7b47d91-d346-46aa-b1d8-2f014d2954d4", "ab596b16-44a4-47fe-9c80-44ef65dcab7d"})
    public void isStatusCodeApproved(String orderId){
        try {
            var status = CreditService.getStatusOrder(orderId);

            assertEquals(Status.APPROVED, status);
        } catch (Exception e){
            fail(e.getMessage());
        }
    }

}
