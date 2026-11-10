### 1. Inputs and Output
**Inputs:** `weightKg`, `orderValue`, `premiumClient`, `express`.

**Output:** `Result(cost, parcels)`.

### 2. Input Validation
If `weightKg` is not within the range [1, 100] or `orderValue < 0`, an `IllegalArgumentException` is thrown.

### 3. Parcel Calculation
The order is divided into parcels weighing a maximum of 30 kg each.

Therefore:

`parcels = ⌈weightKg / 30⌉`

### 4. Shipping Fee per Parcel
The shipping fee depends on the parcel's weight:

- Up to 5 kg → 15 RON
- 6–15 kg → 25 RON
- 16–30 kg → 40 RON

### 5. Express Delivery
Express delivery adds 20 RON to the total cost, only once per order.

### 6. Free Shipping Conditions
The shipping cost becomes 0 if either of the following conditions is met:

- The customer is a premium client.
- `orderValue >= 200` and express delivery is not selected.

The number of parcels remains unchanged, even when shipping is free.

### 7. Formula for Manual Verification

The formula used to manually verify any input value is:

`cost = 40 · ⌊w / 30⌋ + tariff(w mod 30)`

Where `tariff(0) = 0`.

Then:
- Add 20 RON if express delivery is selected.
- Set the cost to 0 if one of the free shipping conditions applies.

**Example:**

For an order weighing 70 kg:

`40 · 2 + tariff(10) = 80 + 25 = 105 RON`