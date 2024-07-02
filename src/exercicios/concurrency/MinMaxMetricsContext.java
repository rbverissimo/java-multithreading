package src.exercicios.concurrency;

import src.interfaces.ContextPerformer;

import java.util.Random;

public class MinMaxMetricsContext implements ContextPerformer {

    private final int NUM_THREADS;
    private final int SAMPLES_PER_THREAD;

    public MinMaxMetricsContext(int NUM_THREADS, int SAMPLES_PER_THREAD) {
        this.NUM_THREADS = NUM_THREADS;
        this.SAMPLES_PER_THREAD = SAMPLES_PER_THREAD;
    }

    public void perform() throws InterruptedException{
        MinMaxMetrics metrics = new MinMaxMetrics();
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(() -> {
                Random random = new Random();
                for (int j = 0; j < SAMPLES_PER_THREAD; j++) {
                    long sample = random.nextLong();
                    metrics.addSample(sample);
                }
            });
            threads[i].start();
        }

        for(Thread thread: threads){
            thread.join();
        }

        System.out.println("Mininum: " + metrics.getMin());
        System.out.println("Maximum: " + metrics.getMax());

    }

}
