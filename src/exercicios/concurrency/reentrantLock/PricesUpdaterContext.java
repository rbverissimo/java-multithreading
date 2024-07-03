package src.exercicios.concurrency.reentrantLock;

import src.interfaces.ContextPerformer;

import java.util.Arrays;
import java.util.List;

public class PricesUpdaterContext implements ContextPerformer {

    private static final PricesContainer PRICES_CONTAINER = new PricesContainer();

    @Override
    public void perform() throws InterruptedException {

        PriceUpdater priceUpdater = new PriceUpdater(PRICES_CONTAINER);
        List<Thread> readers = Arrays.asList(
                new BitcoinPriceReader(PRICES_CONTAINER),
                new EthereumPriceReader(PRICES_CONTAINER));

        readers.forEach(Thread::start);
        priceUpdater.start();


    }
}
