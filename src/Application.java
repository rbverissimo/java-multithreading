package src;

import src.contexts.concurrency.reentrantLock.PricesUpdaterContext;
import src.contexts.concurrency.reentrantReadWriteLock.InventoryDatabaseRWContext;
import src.interfaces.ContextPerformer;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Application {

    public static void main(String[] args) {

        //ContextPerformer context = new MinMaxMetricsContext(4, 1000);
        //ContextPerformer context = new PricesUpdaterContext();
        ContextPerformer context = new InventoryDatabaseRWContext();
        try {
            context.perform();
        } catch (InterruptedException e){
            System.err.println(e.getMessage());
        }

    }

}