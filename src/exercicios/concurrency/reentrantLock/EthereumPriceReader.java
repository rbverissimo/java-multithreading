package src.exercicios.concurrency.reentrantLock;

import java.util.concurrent.TimeUnit;

public class EthereumPriceReader extends Thread implements PriceReader{

    private PricesContainer pricesContainer;

    public EthereumPriceReader(PricesContainer pricesContainer){
        this.pricesContainer = pricesContainer;
    }

    @Override
    public void run() {
        while(true){
            try{
                if(pricesContainer.getLockObject().tryLock(1, TimeUnit.SECONDS)){
                    try{
                        System.out.println("Ethereum price updated! New price " + pricesContainer.getEtherPrice());

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
