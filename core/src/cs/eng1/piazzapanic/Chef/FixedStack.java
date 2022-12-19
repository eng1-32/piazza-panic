package cs.eng1.piazzapanic.Chef;
import java.util.Stack;

public class FixedStack<T> extends Stack<T> {
    private int maxSize;

    public FixedStack(int size){
        super();
        this.maxSize = size;
    }

    @Override
    public T push(T item) {
        if(this.size() == maxSize){
            return null;
        }
        return super.push(item);
    }
}
