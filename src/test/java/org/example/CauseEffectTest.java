package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CauseEffectTest {
    @ParameterizedTest(name = "w={0}, v={1}, premium={2}, express={3} -> cost={4}, colete={5}")
    @CsvSource({
            "3,  50,  false, false, 15,  1",   // W1
            "10, 50,  false, false, 25,  1",   // W2
            "20, 50,  false, false, 40,  1",   // W3
            "70, 50,  false, false, 105, 3",   // W4
            "3,  50,  false, true,  35,  1",   // W1 + expres
            "10, 50,  false, true,  45,  1",   // W2 + expres
            "20, 50,  false, true,  60,  1",   // W3 + expres
            "70, 50,  false, true,  125, 3",   // W4 + expres
            "10, 50,  true,  false, 0,   1",   // premium
            "10, 50,  true,  true,  0,   1",   // premium bate expres
            "70, 50,  true,  false, 0,   3",   // premium, dar coletele se numara
            "10, 250, false, false, 0,   1",   // valoare mare, fara expres
            "20, 250, false, true,  60,  1"    // valoare mare + expres: NU e gratuit
    })
    void validRules(int w, int v, boolean premium, boolean express, int cost, int parcels) {
        assertEquals(new TransportService.Result(cost, parcels),
                TransportService.transportCostCalculation(w, v, premium, express));
    }

    @ParameterizedTest(name = "invalid: w={0}, v={1}")
    @CsvSource({
            "0,  50",   // C1 falsa (greutate invalida)
            "10, -1"    // C2 falsa (valoare negativa)
    })
    void invalidRules(int w, int v) {
        assertThrows(IllegalArgumentException.class,
                () -> TransportService.transportCostCalculation(w, v, false, false));
    }
}
