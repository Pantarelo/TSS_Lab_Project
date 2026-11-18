package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class McdcTest {
    @ParameterizedTest(name = "w={0}, v={1}, premium={2}, express={3} -> cost={4}, colete={5}")
    @CsvSource({
            "10, 100, false, false, 25,  1",   // V1: all false
            "3,  250, false, false, 0,   1",   // G1: A=F, B=T, C=T
            "20, 50,  false, false, 40,  1",   // G2: A=F, B=F, C=T
            "70, 250, false, true,  125, 3",   // G3: A=F, B=T, C=F
            "3,  50,  true,  false, 0,   1"    // G4: A=T, B=F, C=T
    })
    void mcdcValid(int w, int v, boolean premium, boolean express, int cost, int parcels) {
        assertEquals(new TransportService.Result(cost, parcels),
                TransportService.transportCostCalculation(w, v, premium, express));
    }

    @ParameterizedTest(name = "invalid: w={0}, v={1}")
    @CsvSource({
            "0,   100",   // V2: A=T
            "101, 100",   // V3: B=T
            "10,  -1"     // V4: C=T
    })
    void mcdcInvalid(int w, int v) {
        assertThrows(IllegalArgumentException.class,
                () -> TransportService.transportCostCalculation(w, v, false, false));
    }
}
