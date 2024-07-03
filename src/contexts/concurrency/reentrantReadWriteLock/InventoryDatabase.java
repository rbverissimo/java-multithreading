package src.contexts.concurrency.reentrantReadWriteLock;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InventoryDatabase {

    private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
    //private ReentrantLock lock = new ReentrantLock();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private Lock readLock = rwLock.readLock();
    private Lock writeLock = rwLock.writeLock();


    public int getNumberOfItensInPriceRange(int lowerBound, int upperBound){
        try{
            readLock.lock();

            Integer fromKey = priceToCountMap.ceilingKey(lowerBound);
            Integer toKey = priceToCountMap.floorKey(upperBound);

            if(fromKey == 0 || toKey == 0){
                return 0;
            }

            NavigableMap<Integer, Integer> rangeOfPrices = priceToCountMap.subMap(fromKey, true, toKey, true);

            int sum = 0;
            for(int numberOfItemsForPrice: rangeOfPrices.values()){
                sum += numberOfItemsForPrice;
            }

            return sum;
        } finally {
            readLock.unlock();
        }
    }

    public void addItem(int price){
        Integer numberOfItemsForPrice = priceToCountMap.get(price);
        try{
            writeLock.lock();
            if(numberOfItemsForPrice == null){
                priceToCountMap.put(price, 1);
            } else {
                priceToCountMap.put(price, numberOfItemsForPrice + 1);
            }
        } finally {
            writeLock.unlock();
        }
    }

    public void removeItem(int price){
        try{
            writeLock.lock();
            Integer numberOfItemsForPrice = priceToCountMap.get(price);
            if(numberOfItemsForPrice <= 1 || numberOfItemsForPrice == null){
                priceToCountMap.remove(price);
            } else {
                priceToCountMap.put(price, numberOfItemsForPrice - 1);
            }
        } finally {
            writeLock.unlock();
        }
    }
}
