package src.exercicios.concurrency;

public class MinMaxMetrics {

    private volatile long min;
    private volatile long max;

    public MinMaxMetrics() {
        // Add code here
    }

    /**
     * Adds a new sample to our metrics.
     */
    public void addSample(long newSample) {
        // Add code here
    }

    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        return this.min;
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        return this.max;
    }
}