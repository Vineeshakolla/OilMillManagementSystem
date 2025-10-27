package thread;

import java.util.*;
import model.OilBatch;

public class EfficiencyMonitor extends Thread {
    private List<OilBatch> batches;

    public EfficiencyMonitor(List<OilBatch> batches) { this.batches = batches; }

    @Override
    public void run() {
        System.out.println("\n[Thread] Checking batch efficiencies...");
        for (OilBatch batch : batches) {
            if (batch.getEfficiency() < 30) {
                System.out.printf("[ALERT] Batch %d (%s) has low efficiency: %.2f%%\n",
                        batch.getBatchId(), batch.getSeedType(), batch.getEfficiency());
            }
        }
    }
}
