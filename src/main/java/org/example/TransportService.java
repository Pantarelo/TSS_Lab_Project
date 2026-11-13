package org.example;

public class TransportService {
    public record Result(int cost, int parcels) {}

    public static Result transportCostCalculation(int weightKg, int orderValue,
                                               boolean premiumClient, boolean express) {
        if (weightKg < 1 || weightKg > 100 || orderValue < 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        int cost = 0;
        int parcels = 0;
        int remaining = weightKg;
        while (remaining > 0) {
            int parcel = Math.min(remaining, 30);

            if (parcel <= 5) {
                cost += 15;
            }
            else if (parcel <= 15) {
                cost += 25;
            }
            else {
                cost += 40;
            }

            parcels++;
            remaining -= parcel;
        }

        if (express) {
            cost += 20;
        }
        if (premiumClient || (orderValue >= 200 && !express)) {
            cost = 0;
        }

        return new Result(cost, parcels);
    }
}
