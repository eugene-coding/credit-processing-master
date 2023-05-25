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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class TariffProcessingApplicationTests {
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
}
