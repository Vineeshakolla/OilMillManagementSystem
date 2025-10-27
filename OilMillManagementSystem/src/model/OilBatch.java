package model;

public class OilBatch {
    private static int batchCounter = 0;
    private int batchId;
    private String seedType;
    private double weight;
    private double moisture;
    private double impurity;
    private double oilExtracted;
    private double extractionTime;
    private double efficiency;
    private double cost;

    public OilBatch(String seedType, double weight, double moisture, double impurity,
                    double oilExtracted, double extractionTime, double cost) {
        this.batchId = ++batchCounter;
        this.seedType = seedType;
        this.weight = weight;
        this.moisture = moisture;
        this.impurity = impurity;
        this.oilExtracted = oilExtracted;
        this.extractionTime = extractionTime;
        this.cost = cost;
        calculateEfficiency();
    }

    private void calculateEfficiency() {
        this.efficiency = (oilExtracted / weight) * 100;
        if (efficiency < 0) efficiency = 0; // prevent negative
    }

    public int getBatchId() { return batchId; }
    public String getSeedType() { return seedType; }
    public double getEfficiency() { return efficiency; }
    public double getOilExtracted() { return oilExtracted; }
    public double getCost() { return cost; }
    public double getWeight() { return weight; }

    public void displayBatch() {
        System.out.printf(
            "Batch ID: %d | Seed: %-10s | Weight: %.2fkg | Oil: %.2fL | Time: %.2fhr | Eff: %.2f%% | Moist: %.2f%% | Imp: %.2f%% | Cost: ₹%.2f\n",
            batchId, seedType, weight, oilExtracted, extractionTime, efficiency, moisture, impurity, cost);
    }
}
