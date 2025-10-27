package service;

import java.util.*;
import model.*;
import thread.EfficiencyMonitor;

public class OilMillManagementSystem {
    private static Scanner sc = new Scanner(System.in);
    private static List<OilBatch> currentStock = new ArrayList<>();
    private static List<OilBatch> extractedBatches = new ArrayList<>();
    private static List<DeliverySchedule> deliveryHistory = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static Map<String, Double> wasteTracker = new HashMap<>();

    public static void main(String[] args) {
        int choice = 0;
        do {
            System.out.println("\n=== OIL MILL MANAGEMENT SYSTEM ===");
            System.out.println("1. Add New Batch");
            System.out.println("2. Remove Batch After Extraction");
            System.out.println("3. Show Current Stock");
            System.out.println("4. Add Delivery Schedule");
            System.out.println("5. View Dispatch History");
            System.out.println("6. Customer Billing");
            System.out.println("7. Check Efficiency Alerts");
            System.out.println("8. Waste & Cost Analysis");
            System.out.println("9. Exit");

            try {
                System.out.print("Enter your choice: ");
                choice = sc.nextInt(); sc.nextLine();

                switch(choice) {
                    case 1 -> addBatch();
                    case 2 -> removeBatch();
                    case 3 -> showStock();
                    case 4 -> addDelivery();
                    case 5 -> viewDispatch();
                    case 6 -> generateCustomerBill();
                    case 7 -> {
                        EfficiencyMonitor monitor = new EfficiencyMonitor(extractedBatches);
                        monitor.start();
                        try { monitor.join(); } catch (InterruptedException e) {
                            System.out.println("Efficiency check interrupted!");
                        }
                    }
                    case 8 -> wasteAndCostAnalysis();
                    case 9 -> System.out.println("Exiting system...\nThank You!");
                    default -> System.out.println("Invalid choice!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
                choice = 0;
            }
        } while(choice != 9);
    }

    private static void addBatch() {
        try {
            System.out.print("Seed Type: "); String seed = sc.nextLine();
            System.out.print("Weight (kg): "); double weight = sc.nextDouble();
            System.out.print("Moisture (%): "); double moisture = sc.nextDouble();
            System.out.print("Impurity (%): "); double impurity = sc.nextDouble();
            System.out.print("Oil Extracted (L): "); double oil = sc.nextDouble();
            System.out.print("Extraction Time (hr): "); double time = sc.nextDouble();
            System.out.print("Cost (₹): "); double cost = sc.nextDouble(); sc.nextLine();

            OilBatch batch = new OilBatch(seed, weight, moisture, impurity, oil, time, cost);
            currentStock.add(batch);

            double waste = Math.max(weight - oil, 0);
            wasteTracker.put(seed, wasteTracker.getOrDefault(seed, 0.0) + waste);

            System.out.println("Batch added successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Batch not added.");
            sc.nextLine();
        }
    }

    private static void removeBatch() {
        if(currentStock.isEmpty()) { System.out.println("No batches in stock."); return; }
        currentStock.forEach(OilBatch::displayBatch);
        try {
            System.out.print("Enter Batch ID to remove: "); int id = sc.nextInt(); sc.nextLine();
            OilBatch batch = currentStock.stream().filter(b -> b.getBatchId() == id).findFirst().orElse(null);
            if(batch != null) {
                currentStock.remove(batch);
                extractedBatches.add(batch);
                System.out.println("Batch removed and logged!");
            } else System.out.println("Batch ID not found!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            sc.nextLine();
        }
    }

    private static void showStock() {
        if(currentStock.isEmpty()) System.out.println("No batches in stock.");
        else currentStock.forEach(OilBatch::displayBatch);
    }

    private static void addDelivery() {
        if(extractedBatches.isEmpty()) { System.out.println("No extracted batches."); return; }
        extractedBatches.forEach(OilBatch::displayBatch);
        try {
            System.out.print("Enter Batch ID for delivery: "); int id = sc.nextInt(); sc.nextLine();
            OilBatch batch = extractedBatches.stream().filter(b -> b.getBatchId() == id).findFirst().orElse(null);
            if(batch != null) {
                System.out.print("Customer Name: "); String customer = sc.nextLine();
                System.out.print("Delivery Date (DD/MM/YYYY): "); String date = sc.nextLine();
                deliveryHistory.add(new DeliverySchedule(id, customer, date));

                Customer c = customers.stream()
                        .filter(x -> x.getName().equalsIgnoreCase(customer))
                        .findFirst()
                        .orElseGet(() -> {
                            Customer newC = new Customer(customer);
                            customers.add(newC);
                            return newC;
                        });
                c.addPurchase(batch);

                System.out.println("Delivery scheduled!");
            } else System.out.println("Batch ID not found!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            sc.nextLine();
        }
    }

    private static void viewDispatch() {
        if(deliveryHistory.isEmpty()) System.out.println("No dispatch history.");
        else deliveryHistory.forEach(DeliverySchedule::display);
    }

    private static void generateCustomerBill() {
        System.out.print("Enter Customer Name: "); String name = sc.nextLine();
        Customer c = customers.stream().filter(x -> x.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        if(c != null) c.displayBill();
        else System.out.println("Customer not found or no purchases.");
    }

    private static void wasteAndCostAnalysis() {
        System.out.println("\n--- Waste and Cost Analysis ---");

        double totalCost = 0, totalOil = 0, totalWeight = 0;

        for (OilBatch b : extractedBatches) {
            totalCost += b.getCost();
            totalOil += b.getOilExtracted();
            totalWeight += b.getWeight();
        }

        if (extractedBatches.isEmpty()) {
            System.out.println("No extracted batches found. Please remove a batch after extraction first.");
            return;
        }

      
        double totalWaste = totalWeight - totalOil;
        boolean unrealistic = false;

        if (totalWaste < 0) {
            unrealistic = true;
            totalWaste = 0;
        }

        System.out.printf("Total Oil Produced: %.2f L\n", totalOil);
        System.out.printf("Total Seed Weight: %.2f kg\n", totalWeight);
        System.out.printf("Total Waste Generated: %.2f kg\n", totalWaste);
        System.out.printf("Total Production Cost: ₹%.2f\n", totalCost);

        if (totalOil > 0)
            System.out.printf("Cost per Liter of Oil: ₹%.2f\n", totalCost / totalOil);

        if (totalWeight > 0)
            System.out.printf("Waste Percentage: %.2f%%\n", (totalWaste / totalWeight) * 100);

        if (unrealistic)
            System.out.println("Warning: Oil extracted exceeds input seed weight — please verify input data.");
    }


}
