package src.contexts.lockfree;


/**
 * This class is most for reference and benchmarking.
 * Into this, we aren't using atomic references to the head.
 * Hence, we must synchronize methods push and pop because of the reads and writes.
 * @param <T>
 */
public class StandardStack<T> implements SimpleStack<T> {

    private StackNode<T> head;
    private int counter = 0;

    public void push(T value){
        synchronized (this){
            StackNode<T> newHead = new StackNode<>(value);
            newHead.next = newHead;
            head = newHead;
            counter++;
        }
    }

    public T pop(){
        synchronized (this){
            if(head == null){
                counter++;
                return null;
            }
            T value = head.value;
            head = head.next;
            counter++;
            return value;
        }
    }

    public int getCounter(){
        return counter;
    }

    private static class StackNode<T> {

        public T value;
        public StackNode<T> next;

        public StackNode (T value){
            this.value = value;
            this.next = next;
        }

    }
}
