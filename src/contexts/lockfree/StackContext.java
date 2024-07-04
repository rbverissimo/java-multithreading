package src.contexts.lockfree;

import src.interfaces.ContextPerformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StackContext implements ContextPerformer {

    Random random = new Random();
    SimpleStack<Integer> stack;

    public StackContext(SimpleStack<Integer> stack) {
        this.stack = stack;
    }

    @Override
    public void perform() throws InterruptedException {
        for (int i = 0; i < 100000; i++) {
            stack.push(random.nextInt());
        }

        List<Thread> threads = new ArrayList<>();

        int pushingThreads = 2;
        int poppingThreads = 2;

        for (int i = 0; i < pushingThreads; i++) {
            Thread thread = new Thread(() -> {
               while (true){
                   stack.push(random.nextInt());
               }
            });
            thread.setDaemon(true);
            threads.add(thread);
        }

        for (int i = 0; i < poppingThreads; i++) {
            Thread thread = new Thread(() -> {
                while (true){
                    stack.pop();
                }
            });
            thread.setDaemon(true);
            threads.add(thread);
        }

        for(Thread thread: threads){
            thread.start();
        }

        Thread.sleep(10000);

        System.out.printf("%,d operations made on the stack in 10 seconds", stack.getCounter());
    }
}
