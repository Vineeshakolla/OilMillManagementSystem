package model;

public class DeliverySchedule {
    private int batchId;
    private String customerName;
    private String deliveryDate;

    public DeliverySchedule(int batchId, String customerName, String deliveryDate) {
        this.batchId = batchId;
        this.customerName = customerName;
        this.deliveryDate = deliveryDate;
    }

    public void display() {
        System.out.printf("Batch ID: %d | Customer: %s | Delivery Date: %s\n",
                batchId, customerName, deliveryDate);
    }
}
