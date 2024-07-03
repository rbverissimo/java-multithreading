package src.contexts.concurrency.reentrantLock;

import java.util.Random;

public class PriceUpdater extends Thread {

    private final PricesContainer pricesContainer;
    private Random random = new Random();

    public PriceUpdater(PricesContainer pricesContainer) {
        this.pricesContainer = pricesContainer;
    }

    @Override
    public void run() {
        while(true){
            pricesContainer.getLockObject().lock();
            try {
                pricesContainer.setBitcoinPrice(random.nextInt());
                pricesContainer.setEtherPrice(random.nextInt());
                pricesContainer.setLitecoinPrice(random.nextInt());
                pricesContainer.setBitcoinCashPrice(random.nextInt());
                pricesContainer.setRipplePrice(random.nextDouble());

            } finally {
                pricesContainer.getLockObject().unlock();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
