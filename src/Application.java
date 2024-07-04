package src;

import src.contexts.concurrency.reentrantLock.PricesUpdaterContext;
import src.contexts.concurrency.reentrantReadWriteLock.InventoryDatabaseRWContext;
import src.contexts.lockfree.LockFreeStack;
import src.contexts.lockfree.StackContext;
import src.contexts.lockfree.StandardStack;
import src.interfaces.ContextPerformer;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Application {

    public static void main(String[] args) {

        //ContextPerformer context = new MinMaxMetricsContext(4, 1000);
        //ContextPerformer context = new PricesUpdaterContext();
        //ContextPerformer context = new InventoryDatabaseRWContext();
        ContextPerformer context = new StackContext(new LockFreeStack<>());
        //ContextPerformer context = new StackContext(new StandardStack<>());
        try {
            context.perform();
        } catch (InterruptedException e){
            System.err.println(e.getMessage());
        }

    }

}