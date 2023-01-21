package cs.eng1.piazzapanic.chef;

import java.util.Stack;

/**
 * A wrapper over java's builtin Stack for type T
 *
 * @param <T> The class that should be used for all the stack elements
 */
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
