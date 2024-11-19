package net.toarupgm.skyblockertweaks.api;

import net.minecraft.client.MinecraftClient;

import java.util.LinkedList;

public class MetricsTracker {
    public static class MetricsRecord {
        public double amount;
        public long timestamp;

        MetricsRecord(double amount, long timestamp) {
            this.amount = amount;
            this.timestamp = timestamp;
        }
    }

    private final LinkedList<MetricsRecord> metricsRecords;
    private final long trackingPeriod;
    private final long period;
    private double value;
    public static MetricsTracker instance;
    public MinecraftClient client;

    public MetricsTracker(long trackingPeriodSec, long periodSec) {
        this.metricsRecords = new LinkedList<>();
        this.trackingPeriod = trackingPeriodSec * 1000;
        this.period = periodSec * 1000;
        this.client = MinecraftClient.getInstance();
        this.value = 0;
        instance = this;
    }

    public void record(double amount) {
        long tick = System.currentTimeMillis();
        metricsRecords.add(new MetricsRecord(amount,tick));
    }

    private void deleteExpiredRecords() {
        while (!metricsRecords.isEmpty() && (System.currentTimeMillis() - metricsRecords.getFirst().timestamp) > trackingPeriod) {
            metricsRecords.removeFirst();
        }
    }

    public void calculate() {
        deleteExpiredRecords();
        double sum = 0;
        for (MetricsRecord record : metricsRecords) {
            sum += record.amount;
        }
        this.value = sum / (trackingPeriod / 1000.0);
    }

    public double getValue()
    {
        return this.value;
    }

    public LinkedList<MetricsRecord> getMetricsRecords() {
        return metricsRecords;
    }
    public long getTrackingPeriod() {
        return trackingPeriod;
    }
}
