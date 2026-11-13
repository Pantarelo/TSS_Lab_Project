package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class EquivalenceTest {
    @ParameterizedTest(name = "w={0}, v={1}, premium={2}, express={3} -> cost={4}, colete={5}")
    @CsvSource({
            "3,   50,  false, false, 15,  1",   // greutate 1-5
            "10,  50,  false, true,  45,  1",   // greutate 6-15 + expres
            "20,  100, false, false, 40,  1",   // greutate 16-30
            "70,  100, false, false, 105, 3",   // greutate 31-100 (3 colete)
            "20,  300, false, false, 0,   1",   // valoare >= 200, fara expres
            "3,   50,  true,  false, 0,   1"    // premium
    })
    void equivalentClasses(int w, int v, boolean premium, boolean express, int cost, int parcels) {
        var rezultat = TransportService.transportCostCalculation(w, v, premium, express);
        assertEquals(new TransportService.Result(cost, parcels), rezultat);
    }

    @ParameterizedTest(name = "invalid: w={0}, v={1}")
    @CsvSource({
            "0,   50",    // greutate < 1
            "150, 50",    // greutate > 100
            "10,  -5"     // valoare < 0
    })
    void invalidEquivalentClasses(int w, int v) {
        assertThrows(IllegalArgumentException.class,
                () -> TransportService.transportCostCalculation(w, v, false, false));
    }
}
