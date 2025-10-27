package model;

import java.util.*;

public class Customer {
    private String name;
    private List<OilBatch> purchasedBatches;

    public Customer(String name) {
        this.name = name;
        this.purchasedBatches = new ArrayList<>();
    }

    public String getName() { return name; }

    public void addPurchase(OilBatch batch) {
        purchasedBatches.add(batch);
    }

    public void displayBill() {
        double totalCost = 0;
        double totalOil = 0;
        System.out.println("\n--- Bill for " + name + " ---");
        for (OilBatch b : purchasedBatches) {
            System.out.printf("Batch ID: %d | Seed: %s | Oil: %.2fL | Cost: ₹%.2f\n",
                    b.getBatchId(), b.getSeedType(), b.getOilExtracted(), b.getCost());
            totalCost += b.getCost();
            totalOil += b.getOilExtracted();
        }
        System.out.printf("Total Oil: %.2fL | Total Cost: ₹%.2f\n", totalOil, totalCost);
        System.out.println("--------------------------");
    }
}
