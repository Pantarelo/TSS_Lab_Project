package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoundaryTest {
    // v=100, premium=false, express=false
    @ParameterizedTest(name = "w={0} -> cost={1}, colete={2}")
    @CsvSource({
            "1,   15,  1", "2,   15,  1", "5,   15,  1",
            "6,   25,  1", "15,  25,  1", "16,  40,  1",
            "29,  40,  1", "30,  40,  1", "31,  55,  2",
            "35,  55,  2", "36,  65,  2", "45,  65,  2",
            "46,  80,  2", "60,  80,  2", "61,  95,  3",
            "90,  120, 3", "91,  135, 4", "99,  145, 4",
            "100, 145, 4"
    })
    void weightBoundary(int w, int cost, int parcels) {
        assertEquals(new TransportService.Result(cost, parcels),
                TransportService.transportCostCalculation(w, 100, false, false));
    }

    // w=10, premium=false, express=false
    @ParameterizedTest(name = "v={0} -> cost={1}")
    @CsvSource({
            "0, 25", "1, 25", "199, 25", "200, 0", "201, 0"
    })
    void valueBoundary(int v, int cost) {
        assertEquals(new TransportService.Result(cost, 1),
                TransportService.transportCostCalculation(10, v, false, false));
    }

    // granita 200 combinata cu expres: expres anuleaza gratuitatea
    @ParameterizedTest(name = "expres, v={0} -> cost={1}")
    @CsvSource({ "199, 45", "200, 45" })
    void valueBoundaryWithExpress(int v, int cost) {
        assertEquals(new TransportService.Result(cost, 1),
                TransportService.transportCostCalculation(10, v, false, true));
    }

    @ParameterizedTest(name = "invalid: w={0}, v={1}")
    @CsvSource({ "0, 100", "101, 100", "10, -1" })
    void invalidValues(int w, int v) {
        assertThrows(IllegalArgumentException.class,
                () -> TransportService.transportCostCalculation(w, v, false, false));
    }
}
