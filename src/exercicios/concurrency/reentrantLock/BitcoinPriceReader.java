package src.exercicios.concurrency.reentrantLock;

import java.util.concurrent.TimeUnit;

public class BitcoinPriceReader extends Thread implements PriceReader {

    private PricesContainer pricesContainer;

    public BitcoinPriceReader(PricesContainer pricesContainer){
        this.pricesContainer = pricesContainer;
    }

    @Override
    public void run() {
        while(true){
            try{
                if(pricesContainer.getLockObject().tryLock(1, TimeUnit.SECONDS)){
                    try{
                        System.out.println("Bitcoin price updated! New price " + pricesContainer.getBitcoinPrice());

                    } finally {
                        pricesContainer.getLockObject().unlock();
                    }
                } else {
                    System.out.println("Failed to acquire lock for price reading. ");
                }
            } catch (InterruptedException e){

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
