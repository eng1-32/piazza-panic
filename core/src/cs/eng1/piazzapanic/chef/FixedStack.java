package cs.eng1.piazzapanic.chef;

import java.util.Stack;

public class FixedStack<T> extends Stack<T> {

  private final int maxSize;

  public FixedStack(int size) {
    super();
    this.maxSize = size;
  }

  @Override
  public T push(T item) {
    if (!hasSpace()) {
      return null;
    }
    return super.push(item);
  }

  public boolean hasSpace() {
    return this.size() != maxSize;
  }
}
