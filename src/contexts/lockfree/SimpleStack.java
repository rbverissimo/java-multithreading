package src.contexts.lockfree;

public interface SimpleStack<T> {

    void push(T value);
    T pop();
    int getCounter();
}
