package src.contexts.concurrency.reentrantReadWriteLock;

import src.interfaces.ContextPerformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class InventoryDatabaseRWContext implements ContextPerformer {


    private final InventoryDatabase inventoryDatabase = new InventoryDatabase();
    private static final int HIGHEST_PRICE = 1000;
    private final int NUMBER_OF_READER_THREADS = 7;

    @Override
    public void perform() throws InterruptedException {

        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
        }

        Thread writer = new Thread(() -> {
           while (true){
               inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
               inventoryDatabase.removeItem(random.nextInt(HIGHEST_PRICE));

               try{
                   Thread.sleep(12);
               } catch (InterruptedException e){

               }
           }
        });

        writer.setDaemon(true);
        writer.start();


        List<Thread> readers = new ArrayList<>();
        for (int readerIndex = 0; readerIndex < NUMBER_OF_READER_THREADS; readerIndex++) {
            Thread reader = new Thread(() -> {
                for (int i = 0; i < 100000; i++) {
                    int upperBoundPrice = random.nextInt(HIGHEST_PRICE);
                    int lowerBoundPrice = upperBoundPrice > 0 ? random.nextInt(upperBoundPrice) : 0;
                    inventoryDatabase.getNumberOfItensInPriceRange(lowerBoundPrice, upperBoundPrice);
                }
            });
            reader.setDaemon(true);
            readers.add(reader);
        }

        long startReadingTime = System.currentTimeMillis();

        for(Thread reader: readers){
            reader.start();
        }

        for(Thread reader: readers){
            reader.join();
        }

        long endReadingTime = System.currentTimeMillis();

        System.out.println(String.format("Reading took %d ms", endReadingTime - startReadingTime));
    }
}
