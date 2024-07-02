package src;

import src.exercicios.concurrency.MinMaxMetricsContext;
import src.interfaces.ContextPerformer;

public class Application {

    public static void main(String[] args) {

        ContextPerformer context = new MinMaxMetricsContext(4, 10);

        try {
            context.perform();
        } catch (InterruptedException e){
            System.err.println(e.getMessage());
        }

    }

}