# Oil Mill Management System

## Overview

The **Oil Mill Management System** is a Java-based console application developed to automate and streamline oil mill operations.
It manages seed batches, monitors oil extraction efficiency, schedules deliveries, and generates customer bills.
The project focuses on reducing manual work, minimizing errors, and improving productivity through digital management.

---

## Project Inspiration

The idea for this project was inspired by my family’s traditional oil mill.
Manual tracking of seed batches, oil output, and efficiency required significant time and effort.
This motivated the creation of a software solution to automate these processes and bring efficiency to small-scale oil mill operations.

---

## Features

* Batch Management – Add, remove, and monitor seed batches.
* Efficiency Monitoring – Uses a separate thread to check low-efficiency batches.
* Delivery Scheduling – Schedule and track batch deliveries to customers.
* Customer Billing – Generate automatic bills with total oil and cost.
* Waste and Cost Analysis – Analyze total waste, production cost, and cost per liter.
* Data Tracking – Maintain records of batches, customers, and delivery history.

---

## System Architecture

```
src/
 ├─ model/
 │   ├─ OilBatch.java
 │   ├─ Customer.java
 │   └─ DeliverySchedule.java
 │
 ├─ thread/
 │   └─ EfficiencyMonitor.java
 │
 └─ service/
     └─ OilMillManagementSystem.java
```

* **model/** – Contains data models for core entities such as `OilBatch`, `Customer`, and `DeliverySchedule`.
* **thread/** – Contains `EfficiencyMonitor`, which runs efficiency checks concurrently.
* **service/** – Contains the main application logic and user interface.

---

## UML Class Diagram

```
+----------------------+
|     OilBatch         |
+----------------------+
| - batchId: int       |
| - seedType: String   |
| - weight: double     |
| - oilExtracted: double |
| - efficiency: double |
+----------------------+
| + displayBatch()     |
+----------------------+

+----------------------+
|     Customer         |
+----------------------+
| - name: String       |
| - purchasedBatches: List<OilBatch> |
+----------------------+
| + addPurchase()      |
| + displayBill()      |
+----------------------+

+----------------------+
|  DeliverySchedule    |
+----------------------+
| - batchId: int       |
| - customerName: String |
| - deliveryDate: String |
+----------------------+
| + display()          |
+----------------------+

+----------------------+
| EfficiencyMonitor    |
+----------------------+
| - batches: List<OilBatch> |
+----------------------+
| + run()              |
+----------------------+

+----------------------+
| OilMillManagementSystem |
+----------------------+
| - currentStock: List<OilBatch> |
| - extractedBatches: List<OilBatch> |
| - deliveryHistory: List<DeliverySchedule> |
+----------------------+
| + addBatch()         |
| + removeBatch()      |
| + showStock()        |
| + addDelivery()      |
| + generateCustomerBill() |
| + wasteAndCostAnalysis() |
+----------------------+
```

---

## Technologies Used

* **Programming Language:** Java (JDK 17 or later)
* **Development Environment:** IntelliJ IDEA / Eclipse / VS Code
* **Core Concepts:**

  * Object-Oriented Programming (OOP)
  * Threading and Concurrency
  * Exception Handling
  * Java Collections Framework
  * Modular Package Design

---

## How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/<your-username>/OilMillManagementSystem.git
   ```
2. Open the project in your preferred IDE.
3. Navigate to:

   ```
   src/service/OilMillManagementSystem.java
   ```
4. Compile and run the project:

   ```bash
   javac -d . src/service/OilMillManagementSystem.java
   java service.OilMillManagementSystem
   ```

---

## Sample Output

```
=== OIL MILL MANAGEMENT SYSTEM ===
1. Add New Batch
2. Remove Batch After Extraction
3. Show Current Stock
4. Add Delivery Schedule
5. View Dispatch History
6. Customer Billing
7. Check Efficiency Alerts
8. Waste & Cost Analysis
9. Exit
Enter your choice: 1
Seed Type: Groundnut
Weight (kg): 50
Moisture (%): 10
Impurity (%): 2
Oil Extracted (L): 18
Extraction Time (hr): 3
Cost (₹): 1500
Batch added successfully.
```

---

## Future Enhancements

* Integration with a database (MySQL / SQLite).
* Development of a graphical user interface (GUI).
* Addition of data visualization for performance analytics.
* Cloud storage integration for real-time access and backups.

---

## Author

**Vineesha Kolla**
Email: [kollavineesha@gmail.com]
LinkedIn: [www.linkedin.com/in/kolla-vineesha-3a2549351]
