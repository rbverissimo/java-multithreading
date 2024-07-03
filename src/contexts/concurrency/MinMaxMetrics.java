package src.contexts.concurrency;

public class MinMaxMetrics {

    private volatile long min;
    private volatile long max;

    public MinMaxMetrics() {
        this.min = Long.MAX_VALUE;
        this.max = Long.MIN_VALUE;
    }

    /**
     * Adds a new sample to our metrics.
     */
    public void addSample(long newSample) {
        synchronized (this){
            this.min = Math.min(this.min, newSample);
            this.max = Math.max(this.max, newSample);
        }
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